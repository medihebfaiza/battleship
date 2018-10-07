package battleship

import scala.util.Random

/** AI Player that takes decisions according to it's level of intelligence
  *
  * @param primaryGrid  AI Player's primary grid
  * @param trackingGrid AI Player's tracking grid
  * @param fleet        AI Player's fleet
  * @param number       AI's number
  * @param score        AI's score
  * @param level        AI's level of intelligence must be in [0,2]
  * @param targets      stack of potential targets (the use of sets allows no duplicates but having no order can be problematic)
  */
case class Ai(primaryGrid: Grid,
              trackingGrid: Grid,
              fleet: List[Ship],
              number: Int,
              score: Int,
              level: Int,
              targets: Set[(Int, Int)])
  extends Player {

  val random = new Random()

  /** Create a copy of AI Player with updated parameters
    *
    * @param primaryGrid  AI Player's Primary Grid
    * @param trackingGrid AI Player's Tracking Grid
    * @param fleet        AI Player's Ships
    * @param number       AI Player's Number
    * @param score        AI Player's Score
    * @return new Player with updated parameters
    */
  def updatePlayer(primaryGrid: Grid = primaryGrid,
                   trackingGrid: Grid = trackingGrid,
                   fleet: List[Ship] = fleet,
                   number: Int = number,
                   score: Int = score): Player = {
    copy(primaryGrid, trackingGrid, fleet, number, score)
  }

  /** Resets all AI player's parameters except number, score and level
    *
    * @return new AI player with reset parameters
    */
  override def reset(): Player = {
    copy(Grid(), Grid(), Nil, number, score, level, Set())
  }

  /** Updates tracking grid and potential targets list
    *
    * @param pos position of the cell to mark
    * @param hit Boolean equals true to mark as hit and false to mark as missed
    * @return copy Player with an updated tracking grid
    */
  override def updateTracking(pos: (Int, Int), hit: Boolean): Ai = {
    var newTargets: Set[(Int, Int)] = targets - pos // remove the shot cell from targets list
    if (hit) {
      newTargets = cleanTargets(addTargets(newTargets, pos))
      copy(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markHit), 1)), 1)), targets = newTargets)
    }
    else {
      copy(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markMissed), 1)), 1)), targets = newTargets)
    }
  }

  /** Generates a target randomly or picks a potential target depending on the AI's level
    *
    * @return a Tuple containing the coordinates of the chosen target cell
    */
  def askForTarget(): (Int, Int) = {
    var target = (random.nextInt(10), random.nextInt(10)) //TODO this is not completely random
    if (level == 1) {
      if (!trackingGrid.cells(target._1)(target._2).isEmpty) { // If we already shot this cell we try again
        askForTarget()
      }
    }
    if (level == 2) {
      if (targets.nonEmpty) {
        target = targets.head
      }
      if (!trackingGrid.cells(target._1)(target._2).isEmpty) { // If we already shot this cell we try again
        askForTarget()
      }
    }
    target
  }

  /** Gives a randomly generated direction
    *
    * @return true if the generated direction is horizontal and false if it's vertical
    */
  def askForDirection(): Boolean = {
    random.nextInt(2) match {
      case 0 => false
      case _ => true
    }
  }

  /** Adds new potential targets to a targets Set surrounding a given position
    *
    * @param oldTargets targets Set to add to
    * @param pos        must be in bounds
    * @return new targets Set
    */
  def addTargets(oldTargets: Set[(Int, Int)], pos: (Int, Int)): Set[(Int, Int)] = {
    var newTargets = oldTargets
    if (pos._1 - 1 >= 0) {
      newTargets = newTargets + ((pos._1 - 1, pos._2))
    }
    if (pos._1 + 1 < Config.gridSize) {
      newTargets = newTargets + ((pos._1 + 1, pos._2))
    }
    if (pos._2 - 1 >= 0) {
      newTargets = newTargets + ((pos._1, pos._2 - 1))
    }
    if (pos._2 + 1 < Config.gridSize) {
      newTargets = newTargets + ((pos._1, pos._2 + 1))
    }
    newTargets
  }

  /** Remove already shot cell positions from the targets Set
    *
    * @param oldTargets
    * @return new cleaned Set of targets
    */
  def cleanTargets(oldTargets: Set[(Int, Int)]): Set[(Int, Int)] = {
    oldTargets.filter { case (x, y) => trackingGrid.cells(x)(y).isEmpty }
  }

}

/** Factory for [[battleship.Ai]] instances. */
object Ai {

  /** Create a new AI Player with a fleet, number, level and list of potential targets.
    *
    * @param fleet   AI Player's fleet
    * @param number  AI's number
    * @param level   AI's level of intelligence must be in [0,2]
    * @param targets Set of potential targets (empty by default)
    * @return
    */
  def apply(fleet: List[Ship] = Nil, number: Int, score: Int = 0, level: Int = 0, targets: Set[(Int, Int)] = Set()): Ai = {
    if (fleet == Nil) {
      new Ai(Grid(), Grid(), fleet, number, score, level, targets)
    }
    else {
      new Ai(Grid().addFleet(fleet), Grid(), fleet, number, score, level, targets)
    }
  }
}