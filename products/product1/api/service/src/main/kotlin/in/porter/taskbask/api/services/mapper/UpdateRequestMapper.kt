package `in`.porter.taskbask.api.services.mapper

import `in`.porter.taskbask.api.models.entities.internal.TaskResponse
import `in`.porter.taskbask.api.models.entities.internal.UpdateTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.UpdateUserRequest
import `in`.porter.taskbask.api.models.entities.internal.UserResponse
import `in`.porter.taskbask.api.models.enums.TaskStatus
import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import `in`.porter.taskbask.domain.entites.internal.GetUserResponse
import `in`.porter.taskbask.domain.entites.internal.UpdateTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.UpdateUserRequestDomain
import `in`.porter.taskbask.domain.enums.Status
import javax.inject.Inject

class UpdateRequestMapper @Inject constructor( ) {
    fun toDomainModelTask(request : UpdateTaskRequest) = UpdateTaskRequestDomain(
        email = request.email,
        title = request.title,
        description= request.description,
        days = request.days,
        status = Status.valueOf("${request.status}"),
        password = request.password
    )

    fun toDomainModelUser(request : UpdateUserRequest) = UpdateUserRequestDomain(
        email = request.email,
        password = request.password,
        name = request.name,
        newPassword = request.newPassword
    )

    fun toApiDomainTask(request: GetTaskResponse) = TaskResponse(
        title = request.title,
        description = request.description,
        completionDate = request.completionDate,
        status = TaskStatus.valueOf(request.status.name)
    )

    fun toApiDomainUser(request: GetUserResponse) = UserResponse(
        name = request.name,
        email = request.email,
        password = request.password
    )
}