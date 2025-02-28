package `in`.porter.taskbask.domain.usecases.internal

import `in`.porter.taskbask.domain.entites.internal.DeleteTaskRequestDomain
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.repos.CacheRepo
import `in`.porter.taskbask.domain.repos.TaskRepos
import `in`.porter.taskbask.domain.repos.UserRepos
import io.ktor.http.*
import javax.inject.Inject

class DeleteTask
@Inject
constructor( private val taskRepos: TaskRepos , private val cacheRepo: CacheRepo , private val userRepos: UserRepos){

    suspend fun invoke(request : DeleteTaskRequestDomain){
        try {

            if(!userRepos.verifyUser(request.email , request.password)){
                throw TaskBaskException("Email or Password is wrong" , HttpStatusCode.Forbidden)
            }

            taskRepos.delete(request)
            cacheRepo.deleteTask(request)
        }catch (e: TaskBaskException){
            throw e
        }catch (e : Exception){
            throw TaskBaskException("${e.message}" , HttpStatusCode.InternalServerError)
        }
    }
}