package `in`.porter.taskbask.domain.mapper

import `in`.porter.taskbask.domain.entites.internal.*
import `in`.porter.taskbask.domain.enums.Status
import javax.inject.Inject

class UserMapper
@Inject constructor( ){
    fun toDomainDataDelete(request : DeleteUserRequestDomain)= UserEmailRequest(
        email = request.email
    )

    fun toDomainDataUpdate(request : UpdateUserRequestDomain)= UserEmailRequest(
        email = request.email
    )


}