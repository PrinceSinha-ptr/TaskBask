package `in`.porter.taskbask.client.di

import `in`.porter.taskbask.client.clients.TaskBaskClient
import `in`.porter.taskbask.client.config.ClientConfig
import dagger.BindsInstance
import dagger.Component

@ClientScope
@Component(
  modules = [
    ClientModule::class,
    UtilsModule::class
  ]
)
interface TASKBASKClientComponent {
  val taskbaskClient: TaskBaskClient

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun config(config: ClientConfig): Builder

    fun build(): TASKBASKClientComponent
  }
}
