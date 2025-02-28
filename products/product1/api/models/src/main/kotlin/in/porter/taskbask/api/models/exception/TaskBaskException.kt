package `in`.porter.taskbask.api.models.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.ktor.http.*

@JsonIgnoreProperties("stack_trace", "cause", "suppressed", "localized_message")
open class TaskBaskException(override val message: String , val statusCode : HttpStatusCode) : Exception(message)
