package `in`.porter.taskbask.client.clients

import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.taskbask.api.models.async.AsyncJob
import javax.inject.Inject

class TaskBaskHttpClient
@Inject
constructor(
  private val client: SQSClient
) : TaskBaskClient {

  override suspend fun publishJob(job: AsyncJob) {
    TODO()
  }

}
