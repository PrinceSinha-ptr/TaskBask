package `in`.porter.taskbask.servers.ktor.user

import `in`.porter.taskbask.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.userRoute(httpComponent : HttpComponent){
    post("create") { httpComponent.createUserHttpService.invoke(call) }
    post("delete") { httpComponent.deleteUserHttpService.invoke(call) }
    post("update") { httpComponent.updateUserHttpService.invoke(call) }
}