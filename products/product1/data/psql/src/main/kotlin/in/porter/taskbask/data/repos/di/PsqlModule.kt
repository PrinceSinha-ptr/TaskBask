package `in`.porter.taskbask.data.repos.di

import dagger.Binds
import dagger.Module
import `in`.porter.taskbask.data.di.PsqlDataScope
import `in`.porter.taskbask.data.repos.PsqlTaskRepo
import `in`.porter.taskbask.data.repos.PsqlUserRepo
import `in`.porter.taskbask.data.repos.RedisCacheRepo
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.TaskRepos
import `in`.porter.taskbask.domain.repos.UserRepos

@Module
abstract class PsqlModule {


    @Binds
    @PsqlDataScope
    abstract fun providePsqlTaskRepo(repo : PsqlTaskRepo) : TaskRepos

    @Binds
    @PsqlDataScope
    abstract fun providePsqlUserRepo(repo : PsqlUserRepo) : UserRepos


    @Binds
    @PsqlDataScope
    abstract fun provideRedisCacheRepo(repo: RedisCacheRepo): CacheRepo
}