package `in`.porter.taskbask.servers.ktor.user.usecases

import `in`.porter.taskbask.api.models.entities.internal.CreateUserRequest
import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.usecases.CreateUserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class CreateUserHttpService
@Inject
constructor( private val service: CreateUserService){
    suspend fun invoke(call : ApplicationCall){
        try {
            val req = buildRequest(call)
            val res = service.invoke(req)
            call.respond(HttpStatusCode.OK, res)
        }catch (e : TaskBaskException){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to create user","${e.message}"))
        }catch (e : Exception){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to create user","${e.message}"))
        }
    }

    private suspend fun buildRequest(call : ApplicationCall): CreateUserRequest {
        return call.receive<CreateUserRequest>()
    }
}