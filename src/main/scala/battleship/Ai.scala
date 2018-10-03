package battleship

import scala.util.Random

// TODO : solve case class inheritance
case class Ai(primaryGrid: Grid,
              trackingGrid: Grid,
              fleet: List[Ship],
              number: Int, level: Int)
  extends Player {

  val random = new Random()

  def updatePlayer(primaryGrid: Grid = primaryGrid, trackingGrid: Grid = trackingGrid, fleet: List[Ship] = fleet, number: Int = number): Player = {
    copy(primaryGrid, trackingGrid, fleet, number)
  }

  override def askForTarget(): (Int, Int) = {
    level match {
      case 0 => (random.nextInt(10), random.nextInt(10)) //TODO this is not completely random
      case _ => (0, 0)
    }
  }

  override def askForDirection(): Boolean = {
    random.nextInt(2) match { // TODO Error because this gave -1 WTF
      case 0 => false
      case _ => true
    }
  }

  def printLevelWarning(): Unit = println("WARNING: Level unsupported")
}

object Ai {

  def apply(fleet: List[Ship] = Nil, number: Int, level: Int = 0): Ai = {
    if (fleet == Nil) {
      new Ai(Grid(), Grid(), Nil, number, level)
    }
    else {
      new Ai(Grid().addFleet(fleet), Grid(), fleet, number, level)
    }
  }
}