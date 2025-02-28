package `in`.porter.taskbask.domain.usecases.internal


import `in`.porter.taskbask.domain.entites.internal.GetTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import `in`.porter.taskbask.domain.entites.internal.UserEmailRequest
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.TaskRepos
import `in`.porter.taskbask.domain.repos.UserRepos
import io.ktor.http.*
import javax.inject.Inject


class GetTask @Inject constructor(
    private val userRepos: UserRepos,
    private val taskRepos: TaskRepos,
    private val cacheRepo: CacheRepo
) {
    suspend fun invoke(request: GetTaskRequestDomain): List<GetTaskResponse> {
        try {
            val id = findId(request.email)
                ?: throw TaskBaskException("No user exists with email ${request.email}", HttpStatusCode.NotFound)

            val verify = cacheRepo.userExist(request.email) && (cacheRepo.getPassword(request.email) == request.password)
            if(!verify){
                if(!userRepos.verifyUser(request.email , request.password)){
                    throw TaskBaskException("Email or Password is wrong" , HttpStatusCode.Forbidden)
                }
            }

            val cacheResponse = cacheRepo.getTask(request)

            if(!(cacheResponse.isEmpty())){
                return cacheResponse
            }

            val updatedRequest = request.copy(userId = id)
            return taskRepos.get(updatedRequest)

        } catch (e: TaskBaskException) {
            throw e
        } catch (e: Exception) {
            println("Unexpected error in GetTask: ${e.message}")
            throw TaskBaskException("Error retrieving tasks: ${e.message}", HttpStatusCode.InternalServerError)
        }
    }

    private suspend fun findId(email: String): Int? {
        return userRepos.findUserId(UserEmailRequest(email))
    }
}
