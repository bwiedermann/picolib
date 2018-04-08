package picolib

/**
 * Class that represents a position in the maze.
 *
 * @constructor Create a new Position.
 * @param x the column
 * @param y the row
 */
case class Position(x: Int, y: Int) {

  def northOf = Position(x, y - 1)
  def southOf = Position(x, y + 1)
  def eastOf = Position(x + 1, y)
  def westOf = Position(x - 1, y)

  override def toString: String = "(%d, %d)".format(x, y)
}
