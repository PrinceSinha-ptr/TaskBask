package `in`.porter.taskbask.api.services.mapper

import `in`.porter.taskbask.api.models.entities.internal.CreateTaskRequest
import javax.inject.Inject

import `in`.porter.taskbask.api.models.entities.internal.CreateUserRequest
import `in`.porter.taskbask.api.models.entities.internal.TaskResponse
import `in`.porter.taskbask.api.models.entities.internal.UserResponse
import `in`.porter.taskbask.api.models.enums.TaskStatus
import `in`.porter.taskbask.domain.entites.internal.CreateTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.CreateUserRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import `in`.porter.taskbask.domain.entites.internal.GetUserResponse
import `in`.porter.taskbask.domain.utils.TaskUtils

class CreateRequestMapper @Inject constructor(
    private val taskUtils: TaskUtils
){

    fun toDomainModelTask(request : CreateTaskRequest) = CreateTaskRequestDomain(
        email = request.email,
        title = request.title,
        description= request.description,
        days = request.days,
        password = request.password
    )

    fun toDomainModelUser(request : CreateUserRequest) = CreateUserRequestDomain(
        email = request.email,
        name = request.name,
        password = request.password
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