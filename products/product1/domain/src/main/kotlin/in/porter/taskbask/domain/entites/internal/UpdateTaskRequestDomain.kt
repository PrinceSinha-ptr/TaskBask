package `in`.porter.taskbask.domain.entites.internal

import `in`.porter.taskbask.domain.enums.Status

data class UpdateTaskRequestDomain (
    val title: String,
    val email: String,
    val password: String,
    val description: String? = "",
    val status: Status = Status.PENDING,
    val days: Int? = null
)