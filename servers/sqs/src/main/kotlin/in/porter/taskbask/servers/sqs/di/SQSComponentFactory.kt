package `in`.porter.taskbask.servers.sqs.di

import `in`.porter.taskbask.servers.commons.di.factories.ComponentsFactory

object SQSComponentFactory {

  fun build(): SQSComponent =
    DaggerSQSComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()
}
