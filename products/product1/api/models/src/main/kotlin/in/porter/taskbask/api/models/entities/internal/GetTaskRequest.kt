package `in`.porter.taskbask.api.models.entities.internal

data class GetTaskRequest(
    val email: String,
    val password : String,
    var title: String = ""
)