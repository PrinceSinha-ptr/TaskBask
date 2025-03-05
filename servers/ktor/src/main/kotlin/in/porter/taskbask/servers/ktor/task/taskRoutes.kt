package `in`.porter.taskbask.servers.ktor.task

import `in`.porter.taskbask.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.taskRoute(httpComponent: HttpComponent){
    post("create") { httpComponent.createTaskHttpService.invoke(call) }
    post("delete") { httpComponent.deleteTaskHttpService.invoke(call) }
    post("update") { httpComponent.updateTaskHttpService.invoke(call) }
    get("get") { httpComponent.getTaskHttpService.invoke(call) }

}