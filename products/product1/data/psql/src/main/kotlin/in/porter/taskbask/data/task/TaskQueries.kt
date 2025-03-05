package `in`.porter.taskbask.data.task


import `in`.porter.taskbask.data.task.enums.TaskStatus
import `in`.porter.taskbask.data.task.mapper.GetTaskResponseMapper
import `in`.porter.taskbask.domain.entites.internal.*
import `in`.porter.taskbask.domain.utils.TaskUtils
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*
import javax.inject.Inject

class TaskQueries @Inject constructor(
    private val getTaskResponseMapper: GetTaskResponseMapper,
    private val taskUtils: TaskUtils
) {

    suspend fun createTask(request: CreateTaskRequestDomain) = transaction {
        TaskTable.insert{
            it[uuid] = UUID.randomUUID().hashCode()
            it[title] = request.title
            it[description] = request.description
            it[completionTime] = request.timestamp!!.toLong()
            it[userId] = request.userId!!.toInt()
        }
    }
    
    suspend fun deleteTask(request : DeleteTaskRequestDomain) = transaction {
        TaskTable.deleteWhere {
            TaskTable.title.eq(request.title)
        }>0
    }



    suspend fun getAllTask(request : GetTaskRequestDomain) : List<GetTaskResponse> =
        transaction {
            TaskTable.select {
                TaskTable.userId.eq(request.userId)
            }.mapNotNull { row ->
                row?.let {
                    GetTaskResponse(
                        completionDate = taskUtils.formatDate(it[TaskTable.completionTime]),
                        title = it[TaskTable.title],
                        status = getTaskResponseMapper.toDataDomain(it[TaskTable.status]),
                        description = it[TaskTable.description].toString()
                    )
                }
            }
        }



    suspend fun getTaskByTitle(request : GetTaskRequestDomain): List<GetTaskResponse> =
        transaction {
            TaskTable.select {
                TaskTable.userId.eq(request.userId) and TaskTable.title.eq(request.title)
            }.mapNotNull { row ->
                GetTaskResponse(
                    completionDate = taskUtils.formatDate(row[TaskTable.completionTime]),
                    title = row[TaskTable.title],
                    status = getTaskResponseMapper.toDataDomain(row[TaskTable.status]),
                    description = row[TaskTable.description].toString()
                )
            }
        }



    suspend fun updateDescription(request: UpdateTaskRequestDomain){
        transaction {
            TaskTable.update(
                where = {
                    TaskTable.title.eq(request.title)
                }
            ){
                it[description] = request.description.toString()
            }
        }
    }

    suspend fun updateDate(request: UpdateTaskRequestDomain){
        transaction {
                TaskTable.update(
                    where = {
                        TaskTable.title.eq(request.title)
                    }
                ) {
                    it[completionTime] = taskUtils.addDaysToDate(Instant.now().toEpochMilli(), request.days!!.toLong())
                }
        }
    }

    suspend fun updateIsCompleted(request: UpdateTaskRequestDomain){
        transaction {
            TaskTable.update(
                where = {
                    TaskTable.title.eq(request.title)
                }
            ){
                it[status] = TaskStatus.COMPLETED
            }
        }
    }



}