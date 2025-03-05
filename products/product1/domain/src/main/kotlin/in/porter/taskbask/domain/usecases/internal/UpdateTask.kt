package `in`.porter.taskbask.domain.usecases.internal

import `in`.porter.taskbask.domain.entites.internal.GetTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import `in`.porter.taskbask.domain.entites.internal.UpdateTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.UserEmailRequest
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.mapper.TaskMapper
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.TaskRepos
import `in`.porter.taskbask.domain.repos.UserRepos
import io.ktor.http.*
import javax.inject.Inject

class UpdateTask
@Inject
constructor(
    private val taskRepos: TaskRepos,
    private val userRepos: UserRepos,
    private val taskMapper: TaskMapper,
    private val cacheRepo: CacheRepo
){
    suspend fun invoke(request: UpdateTaskRequestDomain): List<GetTaskResponse> {
        try {
            val req = taskMapper.toDomainData(request)

            val userId = findId(req)
                ?: throw TaskBaskException(
                    "No user found for task with title ${request.title}",
                    HttpStatusCode.Forbidden
                )

            if(!userRepos.verifyUser(request.email , request.password)){
                throw TaskBaskException("Email or Password is wrong" , HttpStatusCode.Forbidden)
            }

            if (request.description.isNullOrEmpty() || request.description == null || request.description == "" || request.description == "null") {
                println("Description is either null or empty")
            }

            taskRepos.update(request)
            val updatedRequest = GetTaskRequestDomain(request.email, request.password,request.title, userId)
            val resp = taskRepos.get(updatedRequest)

            cacheRepo.updateTask(request.email , resp[0])
            return resp
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