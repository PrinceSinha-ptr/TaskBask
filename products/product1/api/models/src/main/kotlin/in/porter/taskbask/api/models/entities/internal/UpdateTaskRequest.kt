package `in`.porter.taskbask.api.models.entities.internal

import `in`.porter.taskbask.api.models.enums.TaskStatus

data class UpdateTaskRequest (
    val title: String,
    val email: String,
    val password: String,
    val description: String? = null,
    var status: TaskStatus = TaskStatus.PENDING,
    val days: Int? = null
)