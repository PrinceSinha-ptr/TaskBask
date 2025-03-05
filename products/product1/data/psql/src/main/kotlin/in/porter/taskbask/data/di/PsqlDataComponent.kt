package `in`.porter.taskbask.data.di

import dagger.BindsInstance
import dagger.Component
import `in`.porter.taskbask.data.repos.di.PsqlModule
import `in`.porter.taskbask.data.repos.di.RedisModule
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.TaskRepos
import `in`.porter.taskbask.domain.repos.UserRepos
import io.micrometer.core.instrument.MeterRegistry
import org.jetbrains.exposed.sql.Database

@PsqlDataScope
@Component(
  modules =
  [
    UtilsModule::class,
    PsqlModule::class,
    RedisModule::class
  ]
)
interface PsqlDataComponent {
  val taskRepo : TaskRepos
  val userRepo : UserRepos
  val cacheRepo: CacheRepo

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun database(db: Database): Builder

    @BindsInstance
    fun meterRegistry(meterRegistry: MeterRegistry): Builder

    fun build(): PsqlDataComponent
  }

}
