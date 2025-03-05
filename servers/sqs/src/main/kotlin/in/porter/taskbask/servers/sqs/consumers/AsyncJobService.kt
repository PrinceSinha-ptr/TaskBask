package `in`.porter.taskbask.servers.sqs.consumers

import `in`.porter.taskbask.api.models.async.AsyncJob
import javax.inject.Inject

class AsyncJobService
@Inject
constructor(
) {

  suspend fun invoke(job: AsyncJob) {
     TODO()
  }
}
