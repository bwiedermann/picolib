package picolib.displays

/** An app that displays the picobot in the console */
abstract class PicobotConsoleApp extends App with PicobotConsoleController

trait PicobotConsoleController extends PicobotController {

  abstract override def step(): Unit = {
    println()
    super.step()
    println(this.bot)
  }
  
  abstract override def run(): Unit = {
    println(this.bot)
    super.run()
  }

  abstract override def reset(): Unit = {
    super.reset()
    println(this.bot)
  }
  
}
