package picobot.library.displays

import picobot.library.{Picobot, Position}

import scala.language.postfixOps
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, Pane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

/**
  * A class to create a GUI display for a picobot
  *
  * @constructor create a JFXApp that can display a Picobot
  *
  * Note: There seems to be something wrong with the way ScalaFX does its
  * delayed initialization, so we need this wrapper class instead of mixing it
  * in as a trait :(.
  */
abstract class PicobotGUIApp extends JFXApp with PicobotGUIController {
  stage = makeStage()
}

/**
 * GUI controls and displays for a Picobot, based on ScalaFX
 */
trait PicobotGUIController extends PicobotController {this: JFXApp =>
  import PicobotGUIController._

  private lazy val runButton = new Button("Run") {
    onAction = {_ => runFull()}
  }

  private lazy val stopButton = new Button("Stop") {
    onAction = {_ => stop()}
  }

  private lazy val stepButton = new Button("Step") {
    onAction = {_ => step()}
  }

  private lazy val resetButton = new Button("Reset") {
    onAction = {_ => reset() }
  }

  // a pane with all the buttons to control the animation
  private lazy val buttonPane =
    new HBox{children = List(runButton, stopButton, stepButton, resetButton)}

  // a pane that contains a visualization for each cell in the map
  private lazy val mapPane = new Pane{children = botboxes}

  // a pane for the controller and the map
  private lazy val botPane = new VBox {children = List(buttonPane, mapPane)}

  def makeStage(): PrimaryStage = {
    val bot = this.bot
    val mapWidth = if (this.bot == null) 0 else this.bot.map.width
    val mapHeight = if (this.bot == null) 0 else this.bot.map.height
    mapPane.children = botboxes
    new JFXApp.PrimaryStage {
      width = Math.max(buttonPane.width.value, CELL_SIZE * mapWidth)
      height = buttonPane.height.value + CELL_SIZE * (mapHeight + 2)
      scene = new Scene {
        content = botPane
      }
    }
  }

  abstract override def step(): Unit = {
    stepAnimation.cycleCount = 1
    stepAnimation.play()
  }

  abstract override def reset(): Unit = {
    stop()
    super.reset()
    mapPane.children = botboxes
  }

  override def run(): Unit = {
    this.stage = makeStage()
  }

  def runFull(): Unit = {
    stepAnimation.cycleCount = Timeline.Indefinite
    stepAnimation.play()
  }

  def stop(): Unit = stepAnimation.stop()

  /** an event that steps the bot and updates the display */
  val stepEvent = {
    _: ActionEvent ⇒
      if (this.bot.canMove) {
        super.step()
        mapPane.children = botboxes
      }
  }

  /**
   *  A timeline with one keyframe that steps the bot, then displays the results
   */
  val stepAnimation: Timeline = new Timeline {
    keyFrames = Seq(KeyFrame(10 ms, onFinished = stepEvent))
  }

   /**
   * Make a GUI version of the map
   */
  def botboxes: Seq[Rectangle] = {

    // Make a GUI cell at a given position, colored according to its contents
    def makeCell(pos: Position) =
      new Rectangle {
        x = CELL_SIZE * pos.x
        y = CELL_SIZE * pos.y
        width = CELL_SIZE
        height = CELL_SIZE
        fill = cellColor(pos)
      }

    if (this.bot == null)
      List()
    else
      this.bot.map.positions map makeCell
  }

  /**
   * What color should a cell be?
   */
  def cellColor(pos: Position): Color = {
    if (this.bot.map.isWall(pos))
      WALL_COLOR
    else if (pos == this.bot.position)
      BOT_COLOR
    else if (this.bot.visited contains pos)
      VISITED_COLOR
    else
      BLANK_COLOR
  }
}

object PicobotGUIController {
    val CELL_SIZE = 25

    val WALL_COLOR: Color    = Color.Blue
    val BOT_COLOR: Color     = Color.Black
    val VISITED_COLOR: Color = Color.Grey
    val BLANK_COLOR: Color   = Color.White
}