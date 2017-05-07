import scalajs.js
import scala.compat.Platform

object Bench {

  val LEVEL = 10

  val INNER_SPAWN = 5

  val LIMIT = math.pow(LEVEL, INNER_SPAWN)

  var count = 0

  var startTime: Long = 0L

  def run(ecFun: () => scala.concurrent.ExecutionContextExecutor) = {
    implicit val ev = try {
      ecFun()
    } catch {
      case err : Throwable =>
        println("ExecutionContextExecutor unsupported")
        throw err
    }

    var called = false

    def end(time: Long) = {
      if (!called)
        println(s"Ellapsed time: ${time - startTime}")

      called = true
    }

    def next(): Unit = {
      scala.concurrent.Future {
        //js.Dynamic.global.console.log(count)

        count += 1

        if (count >= LIMIT) end(Platform.currentTime)
        else {
          var i = 0
          while ( i < INNER_SPAWN) {
            next()
            i += 1
          }
        }
      }
    }

    println(s"Limit at $LIMIT")
    startTime = Platform.currentTime
    next()
  }

}
