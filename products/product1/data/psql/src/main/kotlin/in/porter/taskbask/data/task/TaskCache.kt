package `in`.porter.taskbask.data.task


import `in`.porter.taskbask.domain.entites.internal.GetTaskResponse
import redis.clients.jedis.Jedis
import javax.inject.Inject
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class TaskCache @Inject constructor( private val jedis : Jedis) {

    fun addTask(email: String, request: GetTaskResponse) {
        val taskJson = Json.encodeToString(request)
        jedis.hset(email, request.title, taskJson)
    }

    fun getTask(email: String, title: String): List<GetTaskResponse> {
        val taskJson = jedis.hget(email, title) ?: return emptyList()
        return listOf( Json.decodeFromString(taskJson))
    }


    fun getAllTasks(email: String): List<GetTaskResponse> {
        val tasks = jedis.hgetAll(email)
        return tasks.values.map { Json.decodeFromString<GetTaskResponse>(it) }
    }


    fun updateTask(email: String, request: GetTaskResponse) {
        addTask(email, request)
    }

    fun deleteTask(email: String, title: String) {
        jedis.hdel(email, title)
    }

    fun deleteAllTasks(email: String) {
        jedis.del(email)
    }


    fun taskExists(email: String, title: String): Boolean {
        return jedis.hexists(email, title)
    }
}