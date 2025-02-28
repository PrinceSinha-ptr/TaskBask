package `in`.porter.taskbask.domain.entites.internal

data class DeleteTaskRequestDomain (
    val title : String,
    val email : String,
    val password: String
)