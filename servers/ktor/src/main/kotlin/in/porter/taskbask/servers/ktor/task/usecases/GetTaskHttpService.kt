package `in`.porter.taskbask.servers.ktor.task.usecases

import `in`.porter.taskbask.api.models.entities.internal.GetTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.usecases.GetTaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class GetTaskHttpService @Inject constructor(
    private val service: GetTaskService
){
    suspend fun invoke(call : ApplicationCall){
        try {
            val req = buildRequest(call)
            val res = service.invoke(req)
            call.respond(HttpStatusCode.OK, res)
        }catch (e : TaskBaskException){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to Fetch Task","${e.message}"))
        }catch (e : Exception){
            call.respond(HttpStatusCode.Forbidden , Response("Fail to Fetch Task","${e.message}"))
        }
    }

    private suspend fun buildRequest(call : ApplicationCall): GetTaskRequest {
        return call.receive<GetTaskRequest>()
    }
}