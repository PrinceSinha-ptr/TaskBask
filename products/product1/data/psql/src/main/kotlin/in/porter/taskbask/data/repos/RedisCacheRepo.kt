package `in`.porter.taskbask.data.repos

import `in`.porter.kotlinutils.commons.extensions.ifPresentOrElse
import `in`.porter.taskbask.data.task.TaskCache
import `in`.porter.taskbask.data.user.UserCache
import `in`.porter.taskbask.domain.entites.internal.*
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.repos.CacheRepo
import io.ktor.http.*
import javax.inject.Inject

class RedisCacheRepo @Inject
constructor( private val userCache: UserCache,
    private val taskCache: TaskCache
) : CacheRepo {
    override fun userExist(email: String):Boolean{
        return userCache.userExists(email)
    }
    override fun cacheUser(request: CreateUserRequestDomain){
        userCache.storeUser(request.email , request.password)
    }
    override fun getPassword(email: String):String{
        return userCache.getUserPassword(email).toString()
    }
    override fun deleteUser(request: DeleteUserRequestDomain){
        userCache.deleteUser(request.email)
        taskCache.deleteAllTasks(request.email)
    }

    override fun updateUser(request: UpdateUserRequestDomain) {
        userCache.updateUser(request.email , request.newPassword!!)
    }
    override fun taskExist(request : CreateUserRequestDomain):Boolean{
        return taskCache.taskExists(request.email , request.password)
    }
    override fun cacheTask(email : String , request: GetTaskResponse){
        taskCache.addTask(email , request)
    }
    override fun deleteTask(request: DeleteTaskRequestDomain){
        taskCache.deleteTask(request.email , request.title)
    }
    override fun updateTask(email: String , request : GetTaskResponse) {
        taskCache.updateTask(email , request)
    }
    override fun getTask(request : GetTaskRequestDomain): List<GetTaskResponse>{
        if(request.title != ""){
            val res = taskCache.getTask(request.email , request.title)
            if(res.isEmpty()) throw TaskBaskException("No such task with title ${request.title} for user with email ${request.email}" , HttpStatusCode.NotFound)
            return res
        }
        return taskCache.getAllTasks(request.email)
    }



}