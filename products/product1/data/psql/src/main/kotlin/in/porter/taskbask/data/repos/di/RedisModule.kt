package `in`.porter.taskbask.data.repos.di

import dagger.Module
import dagger.Provides
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import javax.inject.Singleton

@Module
object RedisModule {

    @Provides
    fun provideJedisPool(): JedisPool {
        return JedisPool(System.getenv("REDIS_HOST"), System.getenv("REDIS_PORT").toInt())
    }

    @Provides
    fun provideJedis(pool: JedisPool): Jedis {
        return pool.resource // Get a Redis connection from the pool
    }
}
