package `in`.porter.taskbask.domain.entites.internal

import `in`.porter.taskbask.domain.enums.Status


data class CreateTaskResponse(
    val title : String,
    val description : String,
    val status : Status
)