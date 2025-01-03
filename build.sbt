ThisBuild / version := sys.env.getOrElse("LIB_VERSION", "0.7.1-SNAPSHOT").replace("release/", "")
ThisBuild / scalaVersion := "2.13.11"
ThisBuild / organization := "io.dockovpn"
ThisBuild / githubWorkflowJavaVersions += JavaSpec.temurin("11")
ThisBuild / githubWorkflowPublishTargetBranches := Seq(RefPredicate.StartsWith(Ref.Branch("release")))
ThisBuild / githubWorkflowEnv ++= Map("LIB_VERSION" -> "${{ github.ref_name }}")

lazy val root = (project in file("."))
  .settings(
    name := "metastore",
    scalacOptions ++= Seq(
      //"-deprecation",
      "-encoding", "utf-8",                // Specify character encoding used by source files.
      "-explaintypes",                     // Explain type errors in more detail.
      "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
      "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
      //"-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
      "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
      "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
      "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
      "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
      "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
      "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
      "-Ywarn-dead-code",                  // Warn when dead code is identified.
      "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
      "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
      "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
      //"-Ywarn-unused:locals",              // Warn if a local definition is unused.
      //"-Ywarn-unused:params",              // Warn if a value parameter is unused.
      "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
      "-Ywarn-unused:privates",            // Warn if a private member is unused.
      //"-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
    ),
    githubOwner := "dockovpn",
    githubRepository := "metastore",
    githubTokenSource := TokenSource.GitConfig("github.token") || TokenSource
      .Environment("GITHUB_TOKEN") || TokenSource
      .Environment("TOKEN"),
    libraryDependencies ++= Seq(
      scalaOrganization.value % "scala-reflect" % scalaVersion.value,
      "com.typesafe.slick" %% "slick" % "3.4.1",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
      "org.mariadb.jdbc" % "mariadb-java-client" % "3.1.2",
      "org.slf4j" % "slf4j-simple" % "1.7.36",
      "org.typelevel" %% "cats-core" % "2.12.0",
      "org.typelevel" %% "cats-effect" % "3.5.4",
      "org.testcontainers" % "testcontainers" % "1.20.0" % Test,
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.scalamock" %% "scalamock" % "5.2.0" % Test,
      "com.h2database" % "h2" % "2.2.224" % Test
    )
  )
