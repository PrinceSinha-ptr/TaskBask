package `in`.porter.taskbask.servers.ktor.task.usecases


import `in`.porter.taskbask.api.models.entities.internal.DeleteTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.usecases.DeleteTaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class DeleteTaskHttpService @Inject constructor(
    private val service : DeleteTaskService
){
    suspend fun invoke(call : ApplicationCall){
        try {
            val req = buildRequest(call)
            val res = service.invoke(req)
            call.respond(HttpStatusCode.OK, res)
        }catch (e : TaskBaskException){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to delete task","${e.message}"))
        }catch (e : Exception){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to delete task","${e.message}"))
        }
    }

    private suspend fun buildRequest(call : ApplicationCall): DeleteTaskRequest {
        return call.receive<DeleteTaskRequest>()
    }
}