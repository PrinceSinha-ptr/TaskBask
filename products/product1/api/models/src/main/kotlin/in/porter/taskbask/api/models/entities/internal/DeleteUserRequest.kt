package `in`.porter.taskbask.api.models.entities.internal

data class DeleteUserRequest(
    val email: String,
    val password: String
)