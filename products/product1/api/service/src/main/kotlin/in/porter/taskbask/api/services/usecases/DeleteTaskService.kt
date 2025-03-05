package `in`.porter.taskbask.api.services.usecases


import `in`.porter.taskbask.api.models.entities.internal.DeleteTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.mapper.DeleteRequestMapper
import `in`.porter.taskbask.domain.usecases.internal.DeleteTask
import javax.inject.Inject

class DeleteTaskService @Inject constructor(
    private val deleteRequestMapper: DeleteRequestMapper,
    private val deleteUser : DeleteTask
){

    suspend fun invoke(request : DeleteTaskRequest):Response{
        try {
            val req = deleteRequestMapper.toDomainModelTask(request)
            deleteUser.invoke(req)
            return Response(
                status = "success",
                message = "Task Successfully Deleted"
            )
        }catch (e: TaskBaskException){
            throw TaskBaskException(e.message , e.statusCode)
        }catch (e : Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}