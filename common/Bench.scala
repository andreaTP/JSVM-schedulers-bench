import scalajs.js

object Bench {

  val INNER_SPAWN = 10000

  var count = 0

  def run(ecFun: () => scala.concurrent.ExecutionContextExecutor) = {
    implicit val ev = try {
      ecFun()
    } catch {
      case err : Throwable =>
        println("ExecutionContextExecutor unsupported")
        throw err
    }

    def next(): Unit = {
      scala.concurrent.Future {
        js.Dynamic.global.console.log(count)

        count += 1
        var i = 0
        while ( i < INNER_SPAWN) {
          next()
          i += 1
        }
      }
    }

    next()
  }

}
