package `in`.porter.taskbask.domain.repos

import `in`.porter.taskbask.domain.entites.internal.*

interface CacheRepo {
    fun userExist(email: String):Boolean
    fun cacheUser(request: CreateUserRequestDomain)
    fun deleteUser(request: DeleteUserRequestDomain)
    fun updateUser(request : UpdateUserRequestDomain)
    fun getPassword(email: String):String
    fun taskExist(request : CreateUserRequestDomain):Boolean
    fun cacheTask(email : String , request: GetTaskResponse)
    fun deleteTask(request: DeleteTaskRequestDomain)
    fun updateTask(email: String , request : GetTaskResponse)
    fun getTask(request: GetTaskRequestDomain):List<GetTaskResponse>

}