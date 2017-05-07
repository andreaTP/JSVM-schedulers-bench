import scalajs.js
import scala.compat.Platform

object Bench {

  val LEVEL = 10

  val INNER_SPAWN = 7//5

  val LIMIT = math.pow(LEVEL, INNER_SPAWN)

  val MAX_TIME_FROM_LAST = 100

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

    def end(time: Long) = {
      println(s"Ellapsed time: ${time - startTime}")

      System.exit(0)
    }

    var last = 0L

    def next(): Unit = {
      scala.concurrent.Future {
        //js.Dynamic.global.console.log(count)

        count += 1

        var now = Platform.currentTime
        if (now - last > MAX_TIME_FROM_LAST) {
          println("Too much time since last increment stopping")
          println(count)
          System.exit(0)
        }
        last = now

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
    last = Platform.currentTime
    startTime = Platform.currentTime
    next()
  }

}
