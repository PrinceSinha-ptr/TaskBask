package `in`.porter.taskbask.servers.ktor.user.usecases

import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.entities.internal.UpdateUserRequest
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.usecases.UpdateUserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class UpdateUserHttpService
@Inject
constructor(
    private val service: UpdateUserService
){
    suspend fun invoke(call : ApplicationCall){
        try {
            val req = buildRequest(call)
            val res = service.invoke(req)
            call.respond(HttpStatusCode.OK, res)
        }catch (e : TaskBaskException){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to update user details","${e.message}"))
        }catch (e : Exception){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to update user details","${e.message}"))
        }
    }

    private suspend fun buildRequest(call : ApplicationCall) : UpdateUserRequest {
        val req = call.receive<UpdateUserRequest>()
        val newPassword = call.request.queryParameters["newPassword"]?:""
        return UpdateUserRequest(
            newPassword , req.email , req.password , req.name
        )
    }
}