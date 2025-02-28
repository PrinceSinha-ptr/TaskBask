package `in`.porter.taskbask.api.models.entities.internal

data class CreateUserRequest(
    val email:String,
    val name:String,
    val password:String,
)