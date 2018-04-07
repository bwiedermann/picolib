name := "picolib"

version := "2.0"

scalaVersion := "2.12.5"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

target in Compile in doc := baseDirectory.value / "docs"

//assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
