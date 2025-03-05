package `in`.porter.taskbask.api.models.entities.internal

data class DeleteTaskRequest(
    val title : String,
    val email : String,
    val password: String
)