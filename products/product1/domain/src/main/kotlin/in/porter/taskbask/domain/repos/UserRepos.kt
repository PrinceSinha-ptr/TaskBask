package `in`.porter.taskbask.domain.repos

import `in`.porter.taskbask.domain.entites.internal.*

interface UserRepos {
    suspend fun create(request : CreateUserRequestDomain)
    suspend fun delete(request: DeleteUserRequestDomain)
    suspend fun update(request : UpdateUserRequestDomain)
    suspend fun checkUser(request: CheckUserRequest): Boolean
    suspend fun findUserId(request: UserEmailRequest) : Int?
    suspend fun get(request : UserEmailRequest): GetUserResponse
    suspend fun verifyUser(email : String , password : String) : Boolean
}