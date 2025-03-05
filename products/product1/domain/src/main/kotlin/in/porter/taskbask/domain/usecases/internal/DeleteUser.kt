package `in`.porter.taskbask.domain.usecases.internal

import `in`.porter.taskbask.domain.entites.internal.DeleteUserRequestDomain
import `in`.porter.taskbask.domain.entites.internal.UserEmailRequest
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.mapper.UserMapper
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.UserRepos
import io.ktor.http.*
import javax.inject.Inject

class DeleteUser
@Inject constructor(private val userRepos: UserRepos,
                    private val userMapper: UserMapper,
                    private val cacheRepo: CacheRepo
){
    suspend fun invoke(request: DeleteUserRequestDomain) {
        try {
            val req = userMapper.toDomainDataDelete(request)
            val id = findId(req) ?: throw TaskBaskException("This email doesn't exist", HttpStatusCode.NotFound)

            val updatedRequest = request.copy(userId = id)
            userRepos.delete(updatedRequest)
            cacheRepo.deleteUser(request)

        } catch (e: TaskBaskException) {
            throw e
        } catch (e: Exception) {
            throw TaskBaskException(e.message ?: "Unknown error", HttpStatusCode.InternalServerError)
        }
    }


    private suspend fun findId(request: UserEmailRequest) : Int?{
        return userRepos.findUserId(request)

    }
}