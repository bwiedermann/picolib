package picobot.library.displays

import picobot.library.{Map, Picobot, Rule}

trait PicobotController {
  protected var _map: Map = null
  protected var _rules: Seq[Rule] = null
  protected var _bot: Picobot = null

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

  def run(): Unit = {
    if (this.bot != null) {
      while (this.bot.canMove) {
        this.step()
      }
    }
  }
  def step(): Unit = if (this.bot != null) this.bot.step()
  def reset(): Unit = if (this.bot != null) this.bot.reset()
}


