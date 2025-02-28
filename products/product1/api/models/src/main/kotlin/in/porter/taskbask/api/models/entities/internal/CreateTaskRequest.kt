package `in`.porter.taskbask.api.models.entities.internal

data class CreateTaskRequest(
    val email: String,
    val title: String,
    val description: String,
    val days:  Long,
    val password: String
)