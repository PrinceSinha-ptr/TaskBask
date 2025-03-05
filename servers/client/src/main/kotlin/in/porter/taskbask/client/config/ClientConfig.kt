package `in`.porter.taskbask.client.config

import io.ktor.http.*

data class ClientConfig(
  val protocol: URLProtocol,
  val host: String,
  val port: Int? = null
) {

  companion object {
    val Staging = ClientConfig(
      protocol = URLProtocol.HTTPS,
      host = "taskbask-staging-ktor.porter.in"
    )

    val Production = ClientConfig(
      protocol = URLProtocol.HTTP,
      host = "taskbask-prod-ktor.internal.porter.in"
    )
  }
}
