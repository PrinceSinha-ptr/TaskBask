package `in`.porter.taskbask.data.user

import `in`.porter.taskbask.domain.entites.internal.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class UserQueries @Inject constructor( ) {

    suspend fun createUser(request: CreateUserRequestDomain) = transaction {
        UserTable.insert {
            it[email] = request.email
            it[name] = request.name
            it[password] = request.password
        }
    }

    suspend fun deleteUser(request: DeleteUserRequestDomain):Boolean = transaction {
        UserTable.deleteWhere {
            UserTable.password.eq(request.password) and UserTable.email.eq(request.email)
        } > 0
    }

    suspend fun updateName(request : UpdateUserRequestDomain){
        transaction {
            UserTable.update(
                where = {
                    UserTable.email.eq(request.email) and UserTable.password.eq(request.password)
                }
            ){
                it[name] = request.name
            }
        }
    }

    suspend fun updatePassword(request: UpdateUserRequestDomain){
        transaction {
            UserTable.update(
                where = {
                    UserTable.email.eq(request.email) and UserTable.password.eq(request.password)
                }
            ){
                it[password] = request.newPassword
            }
        }
    }

    suspend fun userExist(request: CheckUserRequest): Boolean = transaction {
        UserTable.select { UserTable.email.eq(request.email) }.count() > 0
    }

    suspend fun checkUser(email : String , password : String): Boolean = transaction {
        UserTable.select { UserTable.email.eq(email) and UserTable.password.eq(password) }.count() > 0
    }

    suspend fun findUserUuid(request : UserEmailRequest): Int? = transaction {
        UserTable
            .select { UserTable.email eq request.email }
            .map { it[UserTable.uuid] }
            .firstOrNull()
    }

    suspend fun get(request: UserEmailRequest): GetUserResponse = transaction {
        UserTable.select {
            UserTable.email.eq(request.email)
        }.mapNotNull { row ->
            GetUserResponse(
                name = row[UserTable.name],
                password = row[UserTable.password],
                email = row[UserTable.email]
            )
        }.single()
    }



}