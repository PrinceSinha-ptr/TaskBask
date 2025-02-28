package `in`.porter.taskbask.client.di

import `in`.porter.taskbask.client.clients.TaskBaskHttpClient
import `in`.porter.taskbask.client.clients.TaskBaskClient
import dagger.Binds
import dagger.Module

@Module
internal abstract class ClientModule {

  @Binds
  abstract fun provideAsyncJobsClient(client: TaskBaskHttpClient): TaskBaskClient
}
