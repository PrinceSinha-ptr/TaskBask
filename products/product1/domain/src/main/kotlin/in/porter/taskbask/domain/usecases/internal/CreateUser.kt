package `in`.porter.taskbask.domain.usecases.internal

import `in`.porter.taskbask.domain.entites.internal.CheckUserRequest
import `in`.porter.taskbask.domain.entites.internal.CreateUserRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetUserResponse
import `in`.porter.taskbask.domain.entites.internal.UserEmailRequest
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.UserRepos
import io.ktor.http.*

import javax.inject.Inject

class CreateUser
@Inject
constructor(  private val userRepos: UserRepos, private val cacheRepo: CacheRepo){

    suspend fun invoke(request: CreateUserRequestDomain): GetUserResponse {
        try{
            if (!emailValidation(request.email)) {
               throw TaskBaskException("Invalid email in input!", HttpStatusCode.NotAcceptable)
            }

            if (userRepos.checkUser(CheckUserRequest(request.email))) {
               throw TaskBaskException("This email ${request.email} is already exist", HttpStatusCode.Forbidden)
            }

            if(cacheRepo.userExist(request.email)){
                throw TaskBaskException("User with email ${request.email} already exist" ,HttpStatusCode.Forbidden)
            }

            userRepos.create(request)

            cacheRepo.cacheUser(request)
            return userRepos.get(UserEmailRequest(request.email))
        } catch (e: Exception) {
            throw TaskBaskException("Failed to create user: ${e.message}", HttpStatusCode.InternalServerError)
        }
    }

    private fun emailValidation(email : String) : Boolean {
        val regex = Regex("^[a-zA-Z0-9_.+-]+@gmail\\.com$")
        return regex.matches(email)
    }
}