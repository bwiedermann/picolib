package picolib.displays

import picolib.{Map, Picobot, Rule}

/**
  * A Picobot program
  *
  * Assign values to `map` and `rules`, then call `run`.
  */
trait PicobotController {
  protected var _map: Map = _
  protected var _rules: Seq[Rule] = _
  protected var _bot: Picobot = _

  initBot()

  protected def initBot(): Unit = {
    if (this.map != null && this.rules != null) {
      this._bot = new Picobot(map, rules)
    }
  }

  def bot: Picobot = _bot

  def map: Map = _map
  def map_=(newMap: Map): Unit = {
    this._map = newMap
    initBot()
  }

  def rules: Seq[Rule] = _rules
  def rules_=(newRules: Seq[Rule]): Unit = {
    this._rules = newRules
    initBot()
  }

  /** Run the bot until it stops */
  def run(): Unit = {
    if (this.bot != null) {
      while (this.bot.canMove) {
        this.step()
      }
    }
  }

  /** Run the bot one step */
  def step(): Unit = if (this.bot != null) this.bot.step()

  /** Reset the bot */
  def reset(): Unit = if (this.bot != null) this.bot.reset()
}
