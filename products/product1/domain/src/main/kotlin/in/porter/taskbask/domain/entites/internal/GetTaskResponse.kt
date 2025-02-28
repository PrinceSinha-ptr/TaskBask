package `in`.porter.taskbask.domain.entites.internal


import `in`.porter.taskbask.domain.enums.Status
import kotlinx.serialization.Serializable

@Serializable
data class GetTaskResponse(
    val completionDate : String,
    val title : String,
    val description : String,
    val status : Status
)