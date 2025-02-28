package `in`.porter.taskbask.servers.ktor.task.usecases

import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.entities.internal.UpdateTaskRequest
import `in`.porter.taskbask.api.models.enums.TaskStatus
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.usecases.UpdateTaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class UpdateTaskHttpService @Inject constructor(
    private val service: UpdateTaskService
){
    suspend fun invoke(call : ApplicationCall){
        try {
            val req = buildRequest(call)
            val res = service.invoke(req)
            call.respond(HttpStatusCode.OK, res)
        }catch (e : TaskBaskException){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to update task details","${e.message}"))
        }catch (e : Exception){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to update task details","${e.message}"))
        }
    }

    private suspend fun buildRequest(call : ApplicationCall): UpdateTaskRequest {
        val req = call.receive<UpdateTaskRequest>()
        req.status = TaskStatus.valueOf("${req.status}")
        return req
    }
}