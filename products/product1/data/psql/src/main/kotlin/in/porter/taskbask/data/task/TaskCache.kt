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
        if(!taskExists(email , request.title)) {
            jedis.hset(email, request.title, taskJson)
            val size = (jedis.get("${email}-size")?.toIntOrNull() ?: 0) + 1
            jedis.set("${email}-size" , "${size}")
        }
    }

    fun getTask(email: String, title: String): List<GetTaskResponse> {
        if (!jedis.exists(email)) return emptyList()

        val taskJson = jedis.hget(email, title) ?: return emptyList()
        return listOf( Json.decodeFromString(taskJson))
    }


    fun getAllTasks(email: String): List<GetTaskResponse> {
        if (!jedis.exists(email)) return emptyList()

        val tasks = jedis.hgetAll(email)
        return tasks.values.map { Json.decodeFromString<GetTaskResponse>(it) }
    }


    fun updateTask(email: String, request: GetTaskResponse) {
        addTask(email, request)
    }

    fun deleteTask(email: String, title: String) {

        if(taskExists(email , title)) {
            val size = jedis.get("${email}-size").toInt() - 1
            jedis.set("${email}-size", "${size}")
        }

        jedis.hdel(email, title)
    }

    fun deleteAllTasks(email: String) {
        jedis.del(email)
        jedis.set("${email}-size" , "0")
    }


    fun taskExists(email: String, title: String): Boolean {
        return jedis.hexists(email, title)
    }

    fun addList(request: List<GetTaskResponse> , email: String){
        for(req in request){
            val key = req.title
            if(!taskExists(email , key)){
                val taskJson = Json.encodeToString(req)
                jedis.hset(email, req.title, taskJson)
            }
        }

        jedis.set("${email}-size" , "${request.size}")
    }

    fun getSize(email: String):Int{
        return (jedis.get("${email}-size")?.toIntOrNull() ?: 0)
    }


}