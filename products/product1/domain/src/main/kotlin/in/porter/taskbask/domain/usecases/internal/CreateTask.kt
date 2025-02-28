package `in`.porter.taskbask.domain.usecases.internal

import `in`.porter.taskbask.domain.entites.internal.CreateTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import `in`.porter.taskbask.domain.entites.internal.UserEmailRequest
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.mapper.TaskMapper
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.TaskRepos
import `in`.porter.taskbask.domain.repos.UserRepos
import `in`.porter.taskbask.domain.utils.TaskUtils
import io.ktor.http.*
import java.time.Instant
import javax.inject.Inject

class CreateTask @Inject constructor(
    private val taskUtils: TaskUtils,
    private val taskRepos: TaskRepos,
    private val userRepos: UserRepos,
    private val cacheRepo: CacheRepo,
    private val taskMapper: TaskMapper
){

    suspend fun invoke(request: CreateTaskRequestDomain): List<GetTaskResponse> {

        if (request.days < 0) {
            throw TaskBaskException("Invalid Days in input!", HttpStatusCode.NotAcceptable)
        }

        if (!checkEmail(request.email)) {
            throw TaskBaskException("Invalid email in input!", HttpStatusCode.NotAcceptable)
        }

        val userId = userRepos.findUserId(UserEmailRequest(request.email))
            ?: throw TaskBaskException("User with this email doesn't exist", HttpStatusCode.BadRequest)

        return try {
            val dateInMillis = taskUtils.addDaysToDate(Instant.now().toEpochMilli(), request.days)
            val updatedRequest = request.copy(timestamp = dateInMillis, userId = userId)
            val cacheRequest = taskMapper.cacheRequest(request , dateInMillis)

            if(!userRepos.verifyUser(request.email , request.password)){
                throw TaskBaskException("Email or Password is wrong" , HttpStatusCode.Forbidden)
            }
            cacheRepo.cacheTask(request.email , cacheRequest)


            taskRepos.create(updatedRequest)
            taskRepos.get(GetTaskRequestDomain(request.email, request.password,request.title, userId))
        } catch (e: Exception) {
            throw TaskBaskException("Failed to create task: ${e.message}", HttpStatusCode.InternalServerError)
        }
    }


    private fun checkEmail(email : String) : Boolean {
        val regex = Regex("^[a-zA-Z0-9_.+-]+@gmail\\.com$")
        return regex.matches(email)
    }



}