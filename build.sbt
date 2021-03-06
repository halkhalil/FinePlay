name := """fineplay"""

organization := "hiro20v"

version := "2.7.0-RC8-βc9-SNAPSHOT"

scalaVersion := "2.12.6"
//scalaVersion := "2.13.0-M3"

//lazy val fineplaySub = (project in file("sub"))
//    .enablePlugins(PlayJava)

lazy val root = (project in file("."))
    .enablePlugins(PlayJava)
//    .aggregate(fineplaySub)
//    .dependsOn(fineplaySub)

// Another Repository
resolvers += "jcenter" at "https://jcenter.bintray.com"
resolvers += "jasperreports" at "http://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/"

libraryDependencies ++= Seq(
//  "hiro20v" %% "fineplay-sub" % "2.7.0-RC8-βc9-SNAPSHOT",
  javaJdbc,
  caffeine,
  jcache,
  "org.jsr107.ri" % "cache-annotations-ri-guice" % "1.1.0",
  javaWs,
  javaJpa,
  filters,
//  openId,
//  evolutions,
  "com.typesafe.play" %% "play-mailer" % "6.0.1",						// Apache 6.0.1
  "com.typesafe.play" %% "play-mailer-guice" % "6.0.1",
  guice,
  "com.typesafe.play" %% "play-json" % "2.7.0-RC2",						// Apache 2.7.0-M1
  "com.typesafe.play" %% "play-ws" % "2.0.0-RC2",						// Apache 2.0.0-M6
  "com.h2database" % "h2" % "1.4.197",									// MPL/EPL 1.4.197
//  "com.h2database" % "h2" % "1.4.197" % Test,
  "net.jodah" % "failsafe" % "1.1.0",									// Apache 1.1.0
  "net.logstash.logback" % "logstash-logback-encoder" % "5.2",			// Apache 5.2
  "org.hibernate" % "hibernate-core" % "5.3.7.Final",					// LGPL 5.3.0
  "org.hibernate" % "hibernate-jpamodelgen" % "5.3.7.Final",			//
  "org.glassfish" % "javax.el" % "3.0.1-b09",
  "org.dom4j" % "dom4j" % "2.1.1",										// Origin 2.1.1
  "javax.json" % "javax.json-api" % "1.1.3",
  "org.glassfish" % "javax.json" % "1.1.3",
  "org.apache.commons" % "commons-text" % "1.6",						// Apache 1.4
  "org.postgresql" % "postgresql" % "42.2.5",							// BSD 2-clause 42.2.1
  "org.mockito" % "mockito-core" % "2.23.4",							// MIT 2.23.0
  "net.sf.supercsv" % "super-csv" % "2.4.0",							// Apache 2.4.0
  "net.sf.supercsv" % "super-csv-java8" % "2.4.0",
  "org.apache.poi" % "poi" % "4.0.1",									// Apache 4.0.0
  "org.apache.poi" % "poi-scratchpad" % "4.0.1",
  "org.apache.poi" % "poi-ooxml" % "4.0.1",
  "org.apache.tika" % "tika-core" % "1.19.1",							// Apache 1.17
  "com.google.zxing" % "javase" % "3.3.3",								// Apache 3.3.3
  "org.webjars.bower" % "quagga" % "0.12.1",							// MIT 0.12.1
  "org.apache.pdfbox" % "pdfbox" % "2.0.13",							// Apache 2.0.9
  "org.jsoup" % "jsoup" % "1.11.3",										// MIT 1.11.2
  "com.squareup" % "javapoet" % "1.11.1",								// Apache 1.11.1
  "org.mapstruct" % "mapstruct-jdk8" % "1.2.0.Final",					// Apache 1.2.0
  "org.mapstruct" % "mapstruct-processor" % "1.2.0.Final",				//
  "com.github.spullara.mustache.java" % "compiler" % "0.9.5",			// Apache 0.9.5
  "net.lingala.zip4j" % "zip4j" % "1.3.2",								// Apache 1.3.2
  "org.webjars.npm" % "jquery" % "3.3.1",								// MIT 3.3.1
  "org.webjars.npm" % "bootstrap" % "4.1.3",							// MIT 4.0.0
//  "org.webjars.npm" % "bootbox" % "4.4.0",							// MIT 4.4.0
  "org.webjars" % "pickadate.js" % "3.5.6",								// MIT 3.5.6
  "org.webjars.npm" % "bootstrap-slider" % "10.3.1",					// MIT 10.3.1
  "org.webjars.bower" % "hammerjs" % "2.0.8",							// MIT 2.0.8
  "org.webjars" % "jquery-ui-touch-punch" % "0.2.3-2",					// MIT/GPL V2 0.2.3
  "org.webjars" % "jquery-ui" % "1.12.1",								// MIT 1.12.1
  "org.webjars.bower" % "select2" % "4.0.5",							// MIT 4.0.4
  "org.webjars.npm" % "chart.js" % "2.7.3",								// MIT 2.7.3
  "org.webjars.bower" % "moment" % "2.22.2",							// MIT 2.22.0
  "org.webjars.bower" % "Snap.svg" % "0.5.1",							// Apache 0.5.1
  "org.webjars.bower" % "fullcalendar" % "3.9.0",						// MIT 3.8.0
  "org.webjars" % "openlayers" % "5.2.0",								// 2-Clause BSD 5.2.0
  "org.webjars.bower" % "datatables" % "1.10.19",						// MIT 1.10.19
  "org.webjars.bower" % "datatables.net-plugins" % "1.10.18",			//
  "org.webjars.bower" % "datatables.net-select" % "1.2.3",				//
  "org.webjars.bower" % "summernote" % "0.8.11",						// MIT 0.8.11
  "org.webjars.npm" % "handsontable" % "6.2.0",							// MIT/Pro 6.2.0
  "org.webjars.bower" % "slick-carousel" % "1.8.1",						// MIT 1.8.1
  "org.webjars.npm" % "cropperjs" % "1.4.3",							// MIT 1.4.3
  "org.webjars.npm" % "d3" % "5.7.0",									// BSD-3-Clause license 5.5.0
  "org.webjars.npm" % "d3-geo-projection" % "2.5.0",					// MIT 2.5.0
  "org.webjars.npm" % "jqvmap" % "1.5.1",								// MIT/GPL 1.5.1
  "org.webjars.bower" % "highlightjs" % "9.12.0",						// BSD-3-Clause license 9.12.0
  "org.webjars.npm" % "diff2html" % "2.5.0",							// MIT 2.5.0
  "org.webjars.npm" % "marked" % "0.5.2",								// MIT 0.5.2
  "org.webjars.npm" % "viz.js" % "2.1.1",								// MIT 2.1.1
  "org.webjars.bowergithub.hakimel" % "reveal.js" % "3.7.0",			// MIT 3.7.0
//  "org.webjars.npm" % "paper-css" % "0.4.1",							// MIT 0.4.1
  "org.webjars.bower" % "plotly.js" % "1.42.5",							// MIT 1.42.5
  "org.webjars.bower" % "parsleyjs" % "2.8.1",							// MIT 2.8.1
//  "org.webjars.bower" % "tether-shepherd" % "2.0.0-beta.34",			// MIT 2.0.0-beta.34
  "org.webjars.npm" % "bootstrap-colorpicker" % "3.0.3",				// MIT 3.0.3
  "org.webjars.npm" % "jqtree" % "1.4.9",								// Apache 1.4.9
//  "org.webjars.npm" % "pdfjs-dist" % "2.0.550",						// Apache 2.0.550 +patch
  "org.webjars.npm" % "jsqr" % "1.1.1",									// Apache 2018/06/24 master
  "org.webjars.npm" % "3dmol" % "1.3.7",								// BSD-3-Clause license 1.3.7
  "org.webjars.npm" % "github-com-Tencent-vConsole" % "3.2.0",			// MIT 3.2.0
  "org.webjars.bower" % "mocha" % "3.0.2",								// MIT 3.5.0
  "org.webjars.bower" % "chai" % "4.1.1"								// MIT 4.1.1
)
// TwentyTwenty															// ISC? MIT? Aug 7, 2018 master
// markdeep																// BSD archive master(Sep 10, 2018)
// Frappé Gantt															// MIT build version 0.0.7 master
// geckodriver															// Mozilla Public License 0.18.0

// Use bootstrap
libraryDependencies ++= Seq(
//  "org.webjars.bower" % "clipboard" % "2.0.0",						// MIT 2.0.0
//  "org.webjars.bower" % "holderjs" % "2.8.2",							// MIT 2.9.4
//  "org.webjars.npm" % "popper.js" % "1.13.0"							// MIT 1.13.0
)

// Polyfill
libraryDependencies ++= Seq(
  "org.webjars.npm" % "core-js" % "2.5.6",								// MIT 2.5.6
  "org.webjars.bower" % "fetch" % "2.0.4"								// MIT 2.0.4
)

// Icon library
libraryDependencies ++= Seq(
  "org.webjars" % "font-awesome" % "5.5.0",								// Icons:CC, Fonts:SIL OFL, Code: MIT/Pro 5.5.0
  "org.webjars" % "material-design-icons" % "3.0.1",					// Apache 3.0.1
  "org.webjars.npm" % "ionicons" % "4.4.7",								// MIT 4.4.7
  "org.webjars.npm" % "twemoji" % "11.2.0"								// Code:MIT, Graphics:CC 11.2.0
)
// IcoFont																// MIT 1.3

// JasperReports library
libraryDependencies ++= Seq(
  "net.sf.jasperreports" % "jasperreports" % "6.7.0",					// LGPL 6.7.0
  "net.sf.jasperreports" % "jasperreports-fonts" % "6.0.0",
  "net.sf.barcode4j" % "barcode4j" % "2.1",								// Apache 2.1
  "net.sourceforge.barbecue" % "barbecue" % "1.5-beta1",				// BSD-style 1.5-beta1
  "org.apache.xmlgraphics" % "batik-bridge" % "1.10"
)

// Batch library
libraryDependencies ++= Seq(
  "javax.enterprise" % "cdi-api" % "2.0",
  "org.jboss.weld.se" % "weld-se" % "2.4.5.Final",						// Apache 2.4.5.Final
  "org.jboss.spec.javax.batch" % "jboss-batch-api_1.0_spec" % "1.0.1.Final",
  "org.jboss.marshalling" % "jboss-marshalling" % "2.0.5.Final",		// Apache 2.0.2.Final
  "org.jboss.logging" % "jboss-logging" % "3.3.2.Final",
  "org.jberet" % "jberet-core" % "1.3.1.Final",							// Eclipse Public 1.3.0.Final
  "org.jberet" % "jberet-support" % "1.3.0.Final",
  "org.jberet" % "jberet-se" % "1.3.0.Final",
  "org.wildfly.security" % "wildfly-security-manager" % "1.1.2.Final"	// LGPL(http://www.wildfly.org footer) 1.1.2.Final
)

excludeDependencies ++= Seq(
//  "[GroupId]" % "[ArtifactId]"
)

// //////////

javaOptions in Test += "-Dconfig.file=conf/application_test.conf"
// v0.21.0 / Firefox 57
javaOptions in Test += "-Dwebdriver.gecko.driver=misc/geckodriver"

// Create JPA metamodel
javacOptions in Compile ++= Seq("-encoding", "UTF-8", "-s", "generate")
managedSourceDirectories in Compile += baseDirectory.value / "generate"
unmanagedSourceDirectories in Test += baseDirectory.value / "generate"

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)

// Java project. Don't expect Scala IDE
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
// Use .class files instead of generated .scala files for views and routes
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)

// Create javadoc
sources in (Compile, doc) ~= (_ filter (_.getName endsWith ".java"))

// Copy [Folder] on dist
// fineplay/[Folder] -> /fineplay/target/universal/fineplay-[version]-SNAPSHOT/[Folder]

// Copy prod on dist
mappings in Universal ++= {
  val prodFolder = baseDirectory(_ / "prod").value
  val prodFolderLength = prodFolder.getCanonicalPath.length
  (prodFolder ** "*").get.map { f: File =>
    f -> ("prod/" + f.getCanonicalPath.substring(prodFolderLength))
  }
}
// Copy temp on dist
mappings in Universal ++= {
  val tempFolder = baseDirectory(_ / "temp").value
  val tempFolderLength = tempFolder.getCanonicalPath.length
  (tempFolder ** "*").get.map { f: File =>
    f -> ("temp/" + f.getCanonicalPath.substring(tempFolderLength))
  }
}

// Copy public on dist
mappings in Universal ++= {
  val publicFolder = baseDirectory(_ / "public").value
  val publicFolderLength = publicFolder.getCanonicalPath.length
  (publicFolder ** "*").get.map { f: File =>
    f -> ("public/" + f.getCanonicalPath.substring(publicFolderLength))
  }
}

// mappings in Universal ++= directory(baseDirectory.value / "resources")

// playEnhancerEnabled := false

// JaCoCo
//testOptions in jacocoReportSettings += Tests.Setup( () => {
//	System.setProperty("config.file", "conf/application_test.conf");
//	System.setProperty("webdriver.gecko.driver", "misc/geckodriver");
//} )
//jacocoReportSettings := JacocoReportSettings(
//  "Jacoco Coverage Report",
//  None,
//  JacocoThresholds(),
//  Seq(JacocoReportFormats.ScalaHTML),
//  "utf-8")

// CPD
//cpdLanguage := CpdLanguage.Java
//cpdReportType := CpdReportType.XML

// CheckStyle
checkstyleConfigLocation := CheckstyleConfigLocation.File("conf/checkstyle_checks.xml")
checkstyleXsltTransformations := {
  Some(Set(CheckstyleXSLTSettings(baseDirectory(_ / "conf" / "checkstyle-noframes.xml").value, target(_ / "checkstyle-report.html").value)))
}
