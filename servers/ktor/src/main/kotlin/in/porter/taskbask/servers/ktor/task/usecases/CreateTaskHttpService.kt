package `in`.porter.taskbask.servers.ktor.task.usecases


import `in`.porter.taskbask.api.models.entities.internal.CreateTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.usecases.CreateTaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class CreateTaskHttpService @Inject constructor(
    private val service : CreateTaskService
){
    suspend fun invoke(call : ApplicationCall){
        try {
            val req = buildRequest(call)
            val res = service.invoke(req)
            call.respond(HttpStatusCode.OK , res)
        }catch (e : TaskBaskException){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to create task","${e.message}"))
        }catch (e : Exception){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to create task","${e.message}"))
        }

    }

    private suspend fun buildRequest(call : ApplicationCall): CreateTaskRequest {
        return call.receive<CreateTaskRequest>()
    }
}