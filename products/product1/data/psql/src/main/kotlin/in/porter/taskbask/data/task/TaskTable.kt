package `in`.porter.taskbask.data.task

import `in`.porter.taskbask.data.task.enums.TaskStatus
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import `in`.porter.taskbask.data.user.UserTable

object TaskTable: Table() {
    val uuid = integer("uuid")
    val userId = integer("userId").references(UserTable.uuid, onDelete = ReferenceOption.CASCADE)
    val title = varchar("title" , 255).uniqueIndex()
    val description = text("description")
    var status = enumerationByName("status", 10, TaskStatus::class).default(TaskStatus.PENDING)
    val completionTime = long("completionTime")

    override val primaryKey = PrimaryKey(uuid)

}