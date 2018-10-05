package battleship

/** Human Player requires user interaction to take decision
  *
  * @param primaryGrid  Human Player's primary grid
  * @param trackingGrid Human Player's tracking grid
  * @param fleet        Human Player's ships
  * @param number       Human Player's number
  */
case class Human(primaryGrid: Grid,
                 trackingGrid: Grid,
                 fleet: List[Ship],
                 number: Int,
                 score: Int) extends Player {

  /** Create a copy of Human Player with updated parameters
    *
    * @param primaryGrid  Player's Primary Grid
    * @param trackingGrid Player's Tracking Grid
    * @param fleet        Player's Ships
    * @param number       Player's Number
    * @param score        Player's Score
    * @return new Player with updated parameters
    */
  def updatePlayer(primaryGrid: Grid = primaryGrid, trackingGrid: Grid = trackingGrid, fleet: List[Ship] = fleet, number: Int = number, score: Int = score): Player = {
    copy(primaryGrid, trackingGrid, fleet, number)
  }

  /** Ask Human Player for a target cell coordinates
    *
    * TODO verify user input
    *
    * @return a Tuple containing the coordinates of the chosen cell
    */
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

  /** Ask Human Player for a direction
    *
    * @return true if the given direction is horizontal and false if it's vertical
    */
  def askForDirection(): Boolean = {
    readLine("direction (H/V) > ") match {
      case "h" | "H" => true
      case _ => false
    }
  }
}

/** Factory for [[battleship.Human]] instances. */
object Human {

  /** Create a new Human Player with a fleet and a number.
    *
    * @param fleet
    * @param number
    * @return
    */
  def apply(fleet: List[Ship] = Nil, number: Int = 1, score: Int = 0): Player = {
    if (fleet == Nil) {
      new Human(Grid(), Grid(), Nil, number, score)
    }
    else {
      new Human(Grid().addFleet(fleet), Grid(), fleet, number, score)
    }
  }
}
