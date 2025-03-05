package `in`.porter.taskbask.api.services.usecases

import `in`.porter.taskbask.api.models.entities.internal.ApiUserResponse
import `in`.porter.taskbask.api.models.entities.internal.UpdateTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.UpdateUserRequest
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.mapper.UpdateRequestMapper
import `in`.porter.taskbask.domain.usecases.internal.UpdateTask
import `in`.porter.taskbask.domain.usecases.internal.UpdateUser
import io.ktor.http.*
import javax.inject.Inject

class UpdateUserService
@Inject constructor(
    private val updateRequestMapper: UpdateRequestMapper,
    private val updateUser : UpdateUser
){

    suspend fun invoke(request : UpdateUserRequest): ApiUserResponse{
        try{
            println(request.name)
            println(request.newPassword)
            if(request.name == "" && request.newPassword == ""){
                throw TaskBaskException("Fail to update as no field is provided for update in user information" , HttpStatusCode.Forbidden)
            }
            val req = updateRequestMapper.toDomainModelUser(request)
            val res = updateUser.invoke(req)

            val response = updateRequestMapper.toApiDomainUser(res)
            return ApiUserResponse(
                status = "success",
                message = "Updated Successfully",
                user = response
            )

        }catch (e: TaskBaskException){
            throw TaskBaskException(e.message , e.statusCode)
        }catch (e : Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}