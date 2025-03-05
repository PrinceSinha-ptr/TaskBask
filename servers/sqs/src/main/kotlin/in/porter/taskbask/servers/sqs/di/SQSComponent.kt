package `in`.porter.taskbask.servers.sqs.di

import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainer
import `in`.porter.taskbask.api.models.async.AsyncJob
import `in`.porter.taskbask.data.di.PsqlDataComponent
import `in`.porter.taskbask.servers.commons.di.components.RootComponent
import `in`.porter.taskbask.servers.commons.usecases.external.Run
import dagger.Component

@SQSScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ],
  modules = [
    SQSModule::class
  ]
)
interface SQSComponent {
  val run: Run
  val drainer: StreamQueueDrainer<AsyncJob>
}
