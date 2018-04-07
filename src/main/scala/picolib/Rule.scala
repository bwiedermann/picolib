package picobot.library

/**  A class that describes the contents of a location in the maze.   */
abstract class RelativeDescription(val name: Char) {
    override def toString: String = name.toString
}

/**  The location has a wall */
object Blocked extends RelativeDescription('B')
/**  The location has no wall */
object Open extends RelativeDescription('x')
/**  The location may or may not have a wall */
object Anything extends RelativeDescription('*')

/**  A class that describes a direction the robot can move. */
abstract class MoveDirection(val name: Char) {
    override def toString: String = name.toString
}

/** Move to the north */
object North extends MoveDirection('N')
/** Move to the east */
object East extends MoveDirection('E')
/** Move to the west */
object West extends MoveDirection('W') 
/** Move to the south */
object South extends MoveDirection('S') 
/** Don't move */
object StayHere extends MoveDirection('X') 

/**  A class that describes the positions to the north, east, west, and south of 
  *  some other position.
  */
case class Surroundings(north: RelativeDescription, east: RelativeDescription, 
                        west: RelativeDescription, south: RelativeDescription) {
    override def toString: String =
        north.toString + east.toString + west.toString + south.toString
}

/**  A class that represents a state in which the robot may find itself.
  */
case class State(name: String) {
    override def toString: String = name
}

/** Factory for states */
object State {
  /** create a state with a number */
  def apply(n: Int): State = State(n.toString)
}


/**  A class that represents a rule. A rule describes when and how a robot can move.
  *  If the robot is in a particular state and its surrounding positions match a 
  *  particular pattern, then the robot will move in a particular direction and
  *  transition to a (possibly new) state.
  *  
  *  @param startState The robot's current state
  *  @param surroundings A description of the positions to the north, east, west, and 
  *  south of the robot. If the description matches the current position, the rule
  *  will be activated.
  *  @param moveDirection The direction to move, if the current rule is activated.
  *  @param endState The state into which the robot should transition, if the rule 
  *  is activated
  */
case class Rule(startState:    State, 
		            surroundings:  Surroundings, 
		            moveDirection: MoveDirection, 
		            endState:      State) 
{		        
  override def toString: String =
    startState + " " + surroundings + " -> " + moveDirection + " " + endState	  			   
}
