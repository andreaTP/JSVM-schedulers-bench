import scalajs.js

object Main extends js.JSApp {

  final class ImmediatesExecutionContext extends
      scala.concurrent.ExecutionContextExecutor {
    def execute(runnable: Runnable): Unit = {
      js.Dynamic.global.setImmediate({ () =>
        try {
          runnable.run()
        } catch {
          case t: Throwable => reportFailure(t)
        }
      })
    }

    def reportFailure(t: Throwable): Unit =
      t.printStackTrace()
  }

  def main() = {
    Bench.run(
      () => new ImmediatesExecutionContext
    )
  }

}
