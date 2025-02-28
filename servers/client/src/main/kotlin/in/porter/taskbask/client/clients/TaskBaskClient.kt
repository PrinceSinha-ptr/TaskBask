package `in`.porter.taskbask.client.clients

import `in`.porter.taskbask.api.models.async.AsyncJob


interface TaskBaskClient {
  suspend fun publishJob(job: AsyncJob)
}
