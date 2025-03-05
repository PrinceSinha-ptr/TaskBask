package `in`.porter.taskbask.servers.commons.di.factories

import `in`.porter.taskbask.data.di.DaggerPsqlDataComponent
import `in`.porter.taskbask.servers.commons.di.components.RootComponent
import `in`.porter.taskbask.data.di.PsqlDataComponent

object PsqlDataComponentFactory {

  fun build(rootComponent: RootComponent): PsqlDataComponent =
    DaggerPsqlDataComponent.builder()
      .database(rootComponent.database)
      .meterRegistry(rootComponent.meterRegistry)
      .build()

}
