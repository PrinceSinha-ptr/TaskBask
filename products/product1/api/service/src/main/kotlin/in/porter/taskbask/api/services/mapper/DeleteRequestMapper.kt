package `in`.porter.taskbask.api.services.mapper

import `in`.porter.taskbask.api.models.entities.internal.DeleteTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.DeleteUserRequest
import `in`.porter.taskbask.domain.entites.internal.DeleteTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.DeleteUserRequestDomain
import javax.inject.Inject

class DeleteRequestMapper @Inject constructor( ) {
    fun toDomainModelTask(request : DeleteTaskRequest) = DeleteTaskRequestDomain(
        email = request.email,
        title = request.title,
        password = request.password
    )

    fun toDomainModelUser(request : DeleteUserRequest) = DeleteUserRequestDomain(
        email = request.email,
        password = request.password,
    )
}