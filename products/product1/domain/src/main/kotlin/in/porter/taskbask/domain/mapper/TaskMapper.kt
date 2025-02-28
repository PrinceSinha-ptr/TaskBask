package `in`.porter.taskbask.domain.mapper


import `in`.porter.taskbask.domain.entites.internal.CreateTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import `in`.porter.taskbask.domain.entites.internal.UpdateTaskRequestDomain
import `in`.porter.taskbask.domain.entites.internal.UserEmailRequest
import `in`.porter.taskbask.domain.enums.Status
import `in`.porter.taskbask.domain.utils.TaskUtils
import java.sql.Timestamp
import javax.inject.Inject

class TaskMapper
@Inject constructor(
    private val taskUtils: TaskUtils
){
    fun toDomainData(request : UpdateTaskRequestDomain)= UserEmailRequest(
        email = request.email
    )

    fun cacheRequest(request: CreateTaskRequestDomain , timestamp: Long) = GetTaskResponse(
        completionDate = taskUtils.formatDate(timestamp),
        title = request.title,
        description = request.description,
        status = Status.PENDING,

    )


}