val pluginVersion = sys.props.get("plugin.version").get
addSbtPlugin("com.timushev.sbt" % "sbt-scala-crossproject" % pluginVersion)
