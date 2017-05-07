import scalajs.js

object Main extends js.JSApp {

  def main() = {
    Bench.run(
      () => scala.scalajs.concurrent.QueueExecutionContext.timeouts()
    )
  }

}
