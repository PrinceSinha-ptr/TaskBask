package `in`.porter.taskbask.api.services.usecases

import `in`.porter.taskbask.api.models.entities.internal.ApiTaskResponse
import `in`.porter.taskbask.api.models.entities.internal.UpdateTaskRequest
import `in`.porter.taskbask.api.models.enums.TaskStatus
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.mapper.UpdateRequestMapper
import `in`.porter.taskbask.domain.usecases.internal.UpdateTask
import javax.inject.Inject

class UpdateTaskService
@Inject constructor(
    private val updateRequestMapper: UpdateRequestMapper,
    private val updateTask : UpdateTask
){

    suspend fun invoke(request : UpdateTaskRequest) : ApiTaskResponse{
        try{
            if(request.description == null && request.days == null && request.status == TaskStatus.PENDING){

                return ApiTaskResponse(
                    status = "success",
                    message = "Task with title ${request.title} is unchanged",
                    tasks = listOf()
                )
            }
            val req = updateRequestMapper.toDomainModelTask(request)
            val res = updateTask.invoke(req)
            val response = res.map { updateRequestMapper.toApiDomainTask(it) }
            return ApiTaskResponse(
                status = "success",
                message = "Task Updated Successfully",
                tasks = response
            )
        }catch (e: TaskBaskException){
            throw TaskBaskException(e.message , e.statusCode)
        }catch (e : Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}