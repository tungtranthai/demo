pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
}
rootProject.name = "demo"
include("demo-rpc")
include("demo-springboot")
include("demo-common")
include("demo-client-graphql")
