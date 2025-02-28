package `in`.porter.taskbask.data.user

import redis.clients.jedis.Jedis
import javax.inject.Inject

class UserCache
@Inject constructor( private val jedis: Jedis){

    fun storeUser(email : String , password : String){
        jedis.hset("user_credentials", email, password)
    }

    fun getUserPassword(email: String): String? {
        return jedis.hget("user_credentials", email)
    }

    fun userExists(email: String): Boolean {
        return jedis.hexists("user_credentials", email)

    }

    fun updateUser(email: String, newPassword: String) {
        jedis.hset("user_credentials", email, newPassword)

    }

    fun deleteUser(email: String) {
        jedis.hdel("user_credentials", email)

    }
}