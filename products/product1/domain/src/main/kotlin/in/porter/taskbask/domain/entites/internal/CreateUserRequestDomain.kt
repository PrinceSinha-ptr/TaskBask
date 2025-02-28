package `in`.porter.taskbask.domain.entites.internal

data class CreateUserRequestDomain(
    val email:String,
    val name:String,
    val password:String,
)