package com.timushev.sbt.scalacrossproject

import sbt.{AutoPlugin, PluginTrigger}

object ScalaCrossprojectPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements

  object autoImport extends ScalaCross

}
