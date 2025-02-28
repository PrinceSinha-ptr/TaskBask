package `in`.porter.taskbask.api.models.entities.internal

import `in`.porter.taskbask.api.models.enums.TaskStatus

data class TaskResponse(
    val title: String,
    val description: String,
    val completionDate:  String,
    val status: TaskStatus
)

data class ApiTaskResponse(
    val status: String,
    val message : String,
    val tasks : List<TaskResponse>
)