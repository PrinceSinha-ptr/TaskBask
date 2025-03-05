package `in`.porter.taskbask.domain.repos


import `in`.porter.taskbask.domain.entites.internal.*

interface TaskRepos {
    suspend fun create(request : CreateTaskRequestDomain)
    suspend fun delete(request : DeleteTaskRequestDomain)
    suspend fun update(request : UpdateTaskRequestDomain)
    suspend fun get(request : GetTaskRequestDomain): List<GetTaskResponse>
}