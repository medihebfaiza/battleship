package battleship

import scala.util.Random

// TODO : solve case class inheritance
case class Ai(primaryGrid: Grid,
              trackingGrid: Grid,
              fleet: List[Ship],
              number: Int, level: Int,
              targets: Set[(Int, Int)]) //targets is a stack of potential targets we use sets to allow no duplicates but it allows no order so it can be problematic
  extends Player {

  val random = new Random()

  def updatePlayer(primaryGrid: Grid = primaryGrid, trackingGrid: Grid = trackingGrid, fleet: List[Ship] = fleet, number: Int = number): Player = {
    copy(primaryGrid, trackingGrid, fleet, number)
  }

  def askForTarget(): (Int, Int) = {
    var target = (random.nextInt(10), random.nextInt(10)) //TODO this is not completely random
    if (level == 1) {
      if (!trackingGrid.cells(target._1)(target._2).isEmpty){ // If we already shot this cell we try again
        askForTarget() // TODO should also remove the target from the target list
      }
    }
    if (level == 2) {
      if (targets.nonEmpty) {
        target = targets.head
      }
      if (!trackingGrid.cells(target._1)(target._2).isEmpty){ // If we already shot this cell we try again
        askForTarget() // TODO should also remove the target from the target list
      }
    }
    target
  }

  def askForDirection(): Boolean = {
    random.nextInt(2) match {
      case 0 => false
      case _ => true
    }
  }

  // Updates target grid and potential targets list
  override def updateTracking(pos: (Int, Int), hit: Boolean): Ai = {
    var newTargets : Set[(Int, Int)] = targets - pos
    if (level == 2) {// TEST
      println(newTargets)
    }
    if (hit) {
      newTargets = cleanTargets(addTargets(newTargets, pos))
      copy(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markHit), 1)), 1)), targets = newTargets)
    }
    else {
      copy(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markMissed), 1)), 1)), targets = newTargets)
    }
  }

  // pos must be in bounds
  def addTargets(oldTargets: Set[(Int, Int)] ,pos: (Int, Int)): Set[(Int, Int)] = {
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

  def cleanTargets(oldTargets: Set[(Int,Int)]): Set[(Int,Int)] = {
    oldTargets.filter{case (x, y) => trackingGrid.cells(x)(y).isEmpty}
  }


  def printLevelWarning(): Unit = println("WARNING: Level unsupported")
}

object Ai {

  def apply(fleet: List[Ship] = Nil, number: Int, level: Int = 0, targets: Set[(Int,Int)] = Set()): Ai = {
    if (fleet == Nil) {
      new Ai(Grid(), Grid(), fleet, number, level, targets)
    }
    else {
      new Ai(Grid().addFleet(fleet), Grid(), fleet, number, level, targets)
    }
  }
}