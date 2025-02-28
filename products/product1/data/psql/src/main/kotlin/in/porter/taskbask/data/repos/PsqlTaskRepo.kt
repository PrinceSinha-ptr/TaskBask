package `in`.porter.taskbask.data.repos

import `in`.porter.taskbask.data.task.TaskQueries
import `in`.porter.taskbask.domain.entites.internal.*
import `in`.porter.taskbask.domain.enums.Status
import `in`.porter.taskbask.domain.exceptions.TaskBaskException
import `in`.porter.taskbask.domain.repos.TaskRepos
import io.ktor.http.*
import javax.inject.Inject

class PsqlTaskRepo @Inject
constructor(
    private val queries : TaskQueries
) : TaskRepos {

    override suspend fun create(request : CreateTaskRequestDomain) {
        queries.createTask(request)
    }

    override suspend fun delete(request : DeleteTaskRequestDomain) {
       queries.deleteTask(request)
    }

    override suspend fun update(request : UpdateTaskRequestDomain) {
        if(request.description != "") queries.updateDescription(request)
        if(request.days != null) queries.updateDate(request)
        if(request.status == Status.COMPLETED) queries.updateIsCompleted(request)
    }

    override suspend fun get(request: GetTaskRequestDomain) : List<GetTaskResponse> {
        if(request.title != ""){
            val res = queries.getTaskByTitle(request)
            if(res.isEmpty()) throw TaskBaskException("No such task with title ${request.title} for user with email ${request.email}" , HttpStatusCode.NotFound)
            return res
        }
        return queries.getAllTask(request)
    }


}