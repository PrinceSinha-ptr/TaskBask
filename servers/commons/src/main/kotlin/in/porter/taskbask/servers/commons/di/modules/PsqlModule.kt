package `in`.porter.taskbask.servers.commons.di.modules

import `in`.porter.taskbask.servers.commons.extensions.loadResource
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dagger.Module
import dagger.Provides
import `in`.porter.taskbask.data.task.TaskTable
import `in`.porter.taskbask.data.user.UserTable
import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.DEFAULT_REPETITION_ATTEMPTS
import org.jetbrains.exposed.sql.transactions.IGNORE_ISOLATION_LEVEL
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.inject.Singleton

@Module
class PsqlModule {

  @Provides
  @Singleton
  fun provideHikariDataSource(meterRegistry: MeterRegistry): HikariDataSource {
    meterRegistry.config().meterFilter(
      object : MeterFilter {
        private val distributionMetrics = setOf(
          "hikaricp.connections.acquire",
          "hikaricp.connections.usage"
        )

        override fun configure(id: Meter.Id, config: DistributionStatisticConfig): DistributionStatisticConfig? {
          if (distributionMetrics.none { id.name.startsWith(it) }) return config

          return DistributionStatisticConfig.builder()
            .percentiles(0.5, 0.95, 0.99)
            .build().merge(config)
        }
      }
    )

    // Load database credentials from a properties file or environment variables
    val properties = Properties().loadResource(this, "psql.properties")
      .loadResource(this, "psql_secrets.properties")

    // Fetch NeonDB credentials
    val dbUrl = properties.getProperty("DB_URL") ?: System.getenv("DB_URL")
    val dbUser = properties.getProperty("DB_USER") ?: System.getenv("DB_USER")
    val dbPassword = properties.getProperty("DB_PASSWORD") ?: System.getenv("DB_PASSWORD")

    // Configure HikariCP for NeonDB
    val hikariConfig = HikariConfig().apply {
      jdbcUrl = dbUrl
      username = dbUser
      password = dbPassword
      driverClassName = "org.postgresql.Driver"

      // Enable SSL (NeonDB requires SSL connections)
      addDataSourceProperty("ssl", "true")
      addDataSourceProperty("sslmode", "require")

      // Connection pool settings
      maximumPoolSize = 10
      minimumIdle = 2
      idleTimeout = 30000
      connectionTimeout = 30000
      leakDetectionThreshold = 5000

      // Monitor with MeterRegistry
      metricRegistry = meterRegistry
    }



    return HikariDataSource(hikariConfig)
  }

  @Provides
  @Singleton
  fun provideDB(dataSource: HikariDataSource): Database {
    val db = Database.connect(
      datasource = dataSource,
      manager = {
        ThreadLocalTransactionManager(it, IGNORE_ISOLATION_LEVEL, DEFAULT_REPETITION_ATTEMPTS)
      }
    )

    transaction{
      SchemaUtils.create(UserTable)
      SchemaUtils.create(TaskTable)
    }

    return db
  }

}
