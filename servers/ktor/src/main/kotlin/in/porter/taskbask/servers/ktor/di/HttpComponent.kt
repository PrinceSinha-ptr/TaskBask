package `in`.porter.taskbask.servers.ktor.di

import `in`.porter.taskbask.servers.commons.di.components.RootComponent
import `in`.porter.taskbask.servers.commons.usecases.external.Run
import `in`.porter.taskbask.data.di.PsqlDataComponent

import dagger.Component
import `in`.porter.taskbask.servers.ktor.task.usecases.CreateTaskHttpService
import `in`.porter.taskbask.servers.ktor.task.usecases.DeleteTaskHttpService
import `in`.porter.taskbask.servers.ktor.task.usecases.GetTaskHttpService
import `in`.porter.taskbask.servers.ktor.task.usecases.UpdateTaskHttpService
import `in`.porter.taskbask.servers.ktor.user.usecases.CreateUserHttpService
import `in`.porter.taskbask.servers.ktor.user.usecases.DeleteUserHttpService
import `in`.porter.taskbask.servers.ktor.user.usecases.UpdateUserHttpService

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ]
)
interface HttpComponent {
  val run: Run

  val createUserHttpService : CreateUserHttpService
  val deleteUserHttpService : DeleteUserHttpService
  val updateUserHttpService : UpdateUserHttpService
  val createTaskHttpService : CreateTaskHttpService
  val deleteTaskHttpService : DeleteTaskHttpService
  val updateTaskHttpService : UpdateTaskHttpService
  val getTaskHttpService : GetTaskHttpService
}
