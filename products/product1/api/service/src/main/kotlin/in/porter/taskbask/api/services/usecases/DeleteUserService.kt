package `in`.porter.taskbask.api.services.usecases

import `in`.porter.taskbask.api.models.entities.internal.CreateUserRequest
import `in`.porter.taskbask.api.models.entities.internal.DeleteUserRequest
import `in`.porter.taskbask.api.models.entities.internal.Response
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.mapper.DeleteRequestMapper
import `in`.porter.taskbask.domain.usecases.internal.DeleteTask
import `in`.porter.taskbask.domain.usecases.internal.DeleteUser
import javax.inject.Inject

class DeleteUserService
@Inject constructor(
    private val deleteUserRequest : DeleteRequestMapper,
    private val deleteUser: DeleteUser
){

    suspend fun invoke(request : DeleteUserRequest):Response{
        try {
            val req = deleteUserRequest.toDomainModelUser(request)
            deleteUser.invoke(req)

            return Response(
                status = "success",
                message = "User with email ${request.email} is successfully delete"
            )
        }catch (e: TaskBaskException){
            throw TaskBaskException(e.message , e.statusCode)
        }catch (e : Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}