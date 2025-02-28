package `in`.porter.taskbask.domain.entites.internal

data class GetTaskRequestDomain(
    val email: String,
    val password : String,
    var title: String = "",
    var userId: Int = 0
)