package battleship

case class Human(primaryGrid: Grid,
                 trackingGrid: Grid,
                 fleet: List[Ship],
                 number: Int)
  extends Player {

  def updatePlayer(primaryGrid: Grid = primaryGrid, trackingGrid: Grid = trackingGrid, fleet: List[Ship] = fleet, number: Int = number): Player = {
    copy(primaryGrid, trackingGrid, fleet, number)
  }

  // TODO verify user input
  def askForTarget(): (Int, Int) = {
    println("target :")
    val x = readLine("x > ")
    val y = readLine("y > ")
    try {
      (x.toInt, y.toInt)
    }
    catch {
      case e: Exception =>
        println("Invalid target please retype it :")
        askForTarget()
    }
  }

  def askForDirection(): Boolean = {
    readLine("direction (H/V) > ") match {
      case "h" | "H" => true
      case _ => false
    }
  }
}

object Human {

  def apply(fleet: List[Ship] = Nil, number: Int = 1): Player = {
    if (fleet == Nil) {
      new Human(Grid(), Grid(), Nil, number)
    }
    else {
      new Human(Grid().addFleet(fleet), Grid(), fleet, number)
    }
  }
}
