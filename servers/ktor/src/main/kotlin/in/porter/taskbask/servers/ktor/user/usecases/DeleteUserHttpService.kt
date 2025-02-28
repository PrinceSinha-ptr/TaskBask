package `in`.porter.taskbask.servers.ktor.user.usecases

import `in`.porter.taskbask.api.models.entities.internal.DeleteUserRequest
import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.usecases.DeleteUserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class DeleteUserHttpService
@Inject
constructor( private val service: DeleteUserService){
    suspend fun invoke(call : ApplicationCall){
        try {
            val req = buildRequest(call)
            val res = service.invoke(req)
            call.respond(HttpStatusCode.OK, res)
        }catch (e : TaskBaskException){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to delete user","${e.message}"))
        }catch (e : Exception){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to delete user","${e.message}"))
        }
    }

    private suspend fun buildRequest(call : ApplicationCall) : DeleteUserRequest {
        return call.receive<DeleteUserRequest>()
    }
}