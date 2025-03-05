package `in`.porter.taskbask.api.services.usecases

import `in`.porter.kotlinutils.commons.extensions.ifPresentOrElse
import `in`.porter.taskbask.api.models.entities.internal.ApiUserResponse
import `in`.porter.taskbask.api.models.entities.internal.CreateUserRequest
import `in`.porter.taskbask.api.models.exception.TaskBaskException
import `in`.porter.taskbask.api.services.mapper.CreateRequestMapper
import `in`.porter.taskbask.domain.usecases.internal.CreateUser
import javax.inject.Inject

class CreateUserService @Inject constructor(
    private val createRequestMapper: CreateRequestMapper,
    private val createUser: CreateUser
){
    suspend fun invoke(request : CreateUserRequest): ApiUserResponse{
        try {
            val req = createRequestMapper.toDomainModelUser(request)
            val res = createUser.invoke(req)

            val response = createRequestMapper.toApiDomainUser(res)
            return ApiUserResponse(
                status = "success",
                message = "User with email ${response.email} is created",
                user = response
            )

        }catch (e: TaskBaskException){
            throw TaskBaskException(e.message , e.statusCode)
        }catch (e : Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}