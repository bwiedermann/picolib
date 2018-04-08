package picolib

/**
 * Represents a map for the Picobot to navigate
 *
 * Conceptually, a map is represented as a Cartesian coordinate system
 * where '''the origin (0,0) is in the top left corner'''. Column numbers
 * increase from left to right. Row numbers increase from top to bottom.
 *
 * @constructor Create a new Map.
 * @param width The number of columns in the map
 * @param height The number of rows in the map
 * @param wallPositions The positions in the map that correspond to walls
 * (all other positions are assumed to be open)
 */
class Map(val width: Int, val height: Int, val wallPositions: Set[Position]) {

  /** all positions in the map */
  lazy val positions: Seq[Position] =
    for (column ← 0 until width; row ← 0 until height)
      yield Position(column, row)

  /**
   * Tests a position to see if there's a wall there
   *
   *  @param pos A position to test
   *  @return `true` if the position is part of a wall
   */
  def isWall(pos: Position): Boolean = wallPositions contains pos

  /**
   * Tests a position to see if it's part of the map
   *
   *  @param pos A position to test
   *  @return `true` if the position corresponds to a place in the map
   */
  def isInBounds(pos: Position): Boolean =
    (0 <= pos.x) && (pos.x < this.width) &&
      (0 <= pos.y) && (pos.y < this.height)

  override def toString: String = {
    (0 until width).map(column ⇒
      (0 until height).map(row ⇒
        if (isWall(Position(row, column)))
          Map.WALL_CHARACTER
        else Map.NOWALL_CHARACTER)
        .mkString)
      .mkString("\n")
  }
}

/** A Map factory */
object Map {
  val WALL_CHARACTER = '+'
  val NOWALL_CHARACTER = ' '

  /**
   * Parses a map file (represented as a list of lines) and
   * results in a Map. The method will issue an error if
   * the lines are of unequal width.
   *
   *  @param data a list of strings that describe the map
   *  @return a Map instance
   */
  def apply(data: List[String]): Map = {
    val height = data.length
    val width = data.head.length

    // issue error if there are lines of unequal width
    require(!data.exists(_.length != width))

    // generate a collection of Positions -- one Position
    // for each wall character in the file
    val wall_positions = for {
      rowData ← data.zipWithIndex
      columnData ← rowData._1.zipWithIndex
      if columnData._1 == WALL_CHARACTER
    } yield Position(columnData._2, rowData._2)

    new Map(width, height, wall_positions.toSet)
  }

  /**
   * Parses a map from a file (represented as a list of lines) and
   * results in a Map. The method will issue an error if the map file
   * doesn't exist or if it contains lines are of unequal width.
   *
   *  @param filename the name of a file with a map in it
   *  @return a Map instance
   */
  def apply(filename: String): Map =
     Map(io.Source.fromFile(filename).getLines().toList)
}
