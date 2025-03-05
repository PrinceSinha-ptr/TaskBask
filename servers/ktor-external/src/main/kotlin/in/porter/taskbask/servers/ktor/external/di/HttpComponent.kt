package `in`.porter.taskbask.servers.ktor.external.di

import `in`.porter.taskbask.servers.commons.di.components.RootComponent
import `in`.porter.taskbask.servers.commons.usecases.external.Run
import `in`.porter.taskbask.data.di.PsqlDataComponent
import `in`.porter.taskbask.servers.ktor.di.HttpScope

import dagger.Component

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ]
)
interface HttpComponent {
  val run: Run

}
