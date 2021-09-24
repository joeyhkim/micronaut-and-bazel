package ai.zira

class Application {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      io.micronaut.runtime.Micronaut.build(*args).packages("ai.zira").start()
      println("Hello world!")
    }
  }
}
