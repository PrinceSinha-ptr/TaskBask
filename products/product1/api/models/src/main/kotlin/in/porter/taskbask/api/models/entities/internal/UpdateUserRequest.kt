package `in`.porter.taskbask.api.models.entities.internal

import kotlinx.serialization.Serializable


@Serializable
data class UpdateUserRequest (
    val newPassword: String = "",
    val email: String,
    val password: String,
    var name : String = ""
)