package `in`.porter.taskbask.api.models.entities.internal

import `in`.porter.taskbask.api.models.enums.TaskStatus

data class UserResponse(
    val email: String,
    val name: String,
    val password:  String,
)

data class ApiUserResponse(
    val status: String,
    val message : String,
    val user : UserResponse
)