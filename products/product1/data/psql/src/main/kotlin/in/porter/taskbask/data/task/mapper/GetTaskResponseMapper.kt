package `in`.porter.taskbask.data.task.mapper

import `in`.porter.taskbask.data.task.enums.TaskStatus
import `in`.porter.taskbask.domain.enums.Status
import javax.inject.Inject


class GetTaskResponseMapper
@Inject
constructor(){
    fun toDataDomain(status: TaskStatus): Status {
        return Status.valueOf(status.name)
    }
}