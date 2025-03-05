package `in`.porter.taskbask.domain.entites.internal

data class UpdateUserRequestDomain (
    val email: String,
    val password: String,
    var name : String = "",
    var newPassword: String = ""
)