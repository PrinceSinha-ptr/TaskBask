package `in`.porter.taskbask.api.services.usecases

import `in`.porter.taskbask.api.models.entities.internal.ApiTaskResponse
import `in`.porter.taskbask.api.models.entities.internal.CreateTaskRequest

import `in`.porter.taskbask.api.services.mapper.CreateRequestMapper
import `in`.porter.taskbask.domain.usecases.internal.CreateTask
import `in`.porter.taskbask.domain.utils.TaskUtils
import javax.inject.Inject

class CreateTaskService
@Inject
constructor(
    private val createRequestMapper : CreateRequestMapper,
    private val createTask : CreateTask,
    private val taskUtils: TaskUtils
){

    suspend fun invoke(request : CreateTaskRequest):ApiTaskResponse {
        try {
            val req = createRequestMapper.toDomainModelTask(request)
            val res = createTask.invoke(req)
            val response = res.map { createRequestMapper.toApiDomainTask(it) }
            return ApiTaskResponse(
                status = "success",
                message = "Task Successfully Created",
                tasks = response
            )

        }catch (e : `in`.porter.taskbask.domain.exceptions.TaskBaskException){
            throw `in`.porter.taskbask.domain.exceptions.TaskBaskException(e.message, e.statusCode)
        }catch (e : Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}