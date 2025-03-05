package `in`.porter.taskbask.domain.entites.internal

data class DeleteUserRequestDomain(
    val email: String,
    val password: String,
    var userId : Int? = null
)