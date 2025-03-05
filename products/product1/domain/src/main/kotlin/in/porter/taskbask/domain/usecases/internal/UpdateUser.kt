package `in`.porter.taskbask.domain.usecases.internal

import `in`.porter.taskbask.domain.entites.internal.*
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.mapper.UserMapper
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.UserRepos
import io.ktor.http.*
import javax.inject.Inject

class UpdateUser
@Inject
constructor(
    private val userMapper: UserMapper,
    private val userRepos: UserRepos,
    private val cacheRepo: CacheRepo
){
    suspend fun invoke(request: UpdateUserRequestDomain): GetUserResponse {
        try {
            val req = userMapper.toDomainDataUpdate(request)
            val userId = findId(req)

            if(userId == null) {
                throw TaskBaskException(
                    "No user found with email ${request.email}",
                    HttpStatusCode.Forbidden
                )
            }

            userRepos.update(request)
            val res = userRepos.get(UserEmailRequest(request.email))
            cacheRepo.updateUser(request)

            return res
        } catch (e: TaskBaskException) {
            throw e
        } catch (e: Exception) {
            println("Unexpected error in UpdateTask: ${e.message}")
            throw TaskBaskException("Error updating task: ${e.message}", HttpStatusCode.InternalServerError)
        }
    }


    private suspend fun findId(request: UserEmailRequest) : Int?{
        return userRepos.findUserId(request)
    }


}