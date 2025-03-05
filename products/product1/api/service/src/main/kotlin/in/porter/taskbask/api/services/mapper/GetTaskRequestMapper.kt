package `in`.porter.taskbask.api.services.mapper

import `in`.porter.taskbask.api.models.entities.internal.GetTaskRequest
import `in`.porter.taskbask.api.models.entities.internal.TaskResponse
import `in`.porter.taskbask.api.models.enums.TaskStatus
import `in`.porter.taskbask.domain.entites.internal.GetTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import javax.inject.Inject

class GetTaskRequestMapper
@Inject constructor( ){
    fun toDomainModelTask(request : GetTaskRequest) = GetTaskRequestDomain(
        email = request.email,
        password = request.password,
        title = request.title
    )

    fun toApiDomainTask(request: GetTaskResponse) = TaskResponse(
        title = request.title,
        description = request.description,
        completionDate = request.completionDate,
        status = TaskStatus.valueOf(request.status.name)
    )
}