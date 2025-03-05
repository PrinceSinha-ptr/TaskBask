package `in`.porter.taskbask.domain.entites.internal

import java.sql.Timestamp

data class CreateTaskRequestDomain (
    val email: String,
    val title: String,
    val description: String,
    val days:  Long,
    val password: String,
    var timestamp: Long? = null,
    var userId : Int? = null
)