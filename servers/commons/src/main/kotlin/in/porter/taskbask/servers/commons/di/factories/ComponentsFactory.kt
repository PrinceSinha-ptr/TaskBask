package `in`.porter.taskbask.servers.commons.di.factories

import  `in`.porter.taskbask.servers.commons.di.components.DaggerRootComponent

object ComponentsFactory {

  val rootComponent = DaggerRootComponent.create()
  val psqlDataComponent = PsqlDataComponentFactory.build(rootComponent)

}
