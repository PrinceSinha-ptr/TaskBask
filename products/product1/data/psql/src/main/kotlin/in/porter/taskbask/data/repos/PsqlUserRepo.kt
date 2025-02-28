package `in`.porter.taskbask.data.repos

import `in`.porter.taskbask.data.user.UserQueries
import `in`.porter.taskbask.domain.entites.internal.*
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.repos.UserRepos
import io.ktor.http.*
import javax.inject.Inject

class PsqlUserRepo @Inject
constructor( private val queries: UserQueries ) : UserRepos {
    override suspend fun create(request : CreateUserRequestDomain) {
        queries.createUser(request)
    }

    override suspend fun delete(request : DeleteUserRequestDomain) {
        print(1)
        if(!queries.deleteUser(request)) throw TaskBaskException("Either email or password is wrong" , HttpStatusCode.Forbidden)
    }

    override suspend fun update(request : UpdateUserRequestDomain){
        print(request.newPassword)
        if(!queries.checkUser(request.email , request.password)) throw TaskBaskException("Either email or password is wrong" , HttpStatusCode.Forbidden)
        if(request.name != "") queries.updateName(request)
        if(request.newPassword != "") queries.updatePassword(request)
    }

    override suspend fun checkUser(request: CheckUserRequest): Boolean {
        return queries.userExist(request)
    }

    override suspend fun findUserId(request: UserEmailRequest): Int? {
        return queries.findUserUuid(request)
    }

    override suspend fun get(request: UserEmailRequest): GetUserResponse {
        return queries.get(request)
    }

    override suspend fun verifyUser(email : String , password : String) : Boolean{
        return queries.checkUser(email,password)
    }
}