package `in`.porter.taskbask.api.services.usecases

import `in`.porter.taskbask.api.models.entities.internal.ApiTaskResponse
import `in`.porter.taskbask.api.models.entities.internal.GetTaskRequest
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.mapper.GetTaskRequestMapper
import `in`.porter.taskbask.domain.usecases.internal.GetTask
import io.ktor.http.*
import javax.inject.Inject

class GetTaskService @Inject constructor(
    private val getTask : GetTask,
    private val getTaskMapper : GetTaskRequestMapper
) {
    suspend fun invoke(request: GetTaskRequest): ApiTaskResponse {
        try {
            val req = getTaskMapper.toDomainModelTask(request)
            val res = getTask.invoke(req)
            val response = res.map { getTaskMapper.toApiDomainTask(it) }

            if(response.isEmpty()){
                return ApiTaskResponse(
                    status = "success",
                    message = "No tasks found for the email: ${request.email}.",
                    tasks = response
                )
            }

            return ApiTaskResponse(
                status = "success",
                message = "Successfully fetched tasks",
                tasks = response
            )
        } catch (e: TaskBaskException) {
            throw e
        } catch (e: Exception) {
            println("Unexpected error in GetTask: ${e.message}")
            throw TaskBaskException("Error retrieving tasks: ${e.message}", HttpStatusCode.InternalServerError)
        }
    }

}