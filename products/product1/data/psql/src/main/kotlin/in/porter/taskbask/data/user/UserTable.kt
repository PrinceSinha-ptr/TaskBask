package `in`.porter.taskbask.data.user

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
    val uuid = integer("uuid").autoIncrement()
    val name = varchar("name", 255)
    val email = varchar("email" , 255).uniqueIndex()
    var password = varchar("password" , 255)

    override val primaryKey = PrimaryKey(uuid)
}