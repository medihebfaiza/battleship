package battleship

import scala.annotation.tailrec

/* Player is a Human or an AI who plays the game. */
trait Player {

  val primaryGrid: Grid
  val trackingGrid: Grid
  val fleet: List[Ship]
  val number: Int
  val score: Int

  /** Create a copy of Player with updated parameters
    *
    * @param primaryGrid  Player's Primary Grid
    * @param trackingGrid Player's Tracking Grid
    * @param fleet        Player's Ships
    * @param number       Player's Number
    * @param score        Player's Score
    * @return new Player with updated parameters
    */
  def updatePlayer(primaryGrid: Grid = primaryGrid, trackingGrid: Grid = trackingGrid, fleet: List[Ship] = fleet, number: Int = number, score: Int = score): Player

  /** Resets all player's parameters except number and score
    *
    * @return new player with reset parameters
    */
  def reset(): Player = {
    updatePlayer(primaryGrid = Grid(), trackingGrid = Grid(), fleet = Nil)
  }

  /** Updates the tracking grid by marking a cell as hit or missed
    *
    * @param pos position of the cell to mark
    * @param hit Boolean equals true to mark as hit and false to mark as missed
    * @return copy Player with an updated tracking grid
    */
  def updateTracking(pos: (Int, Int), hit: Boolean): Player = {
    if (hit) {
      updatePlayer(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markHit), 1)), 1)))
    }
    else {
      updatePlayer(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markMissed), 1)), 1)))
    }
  }

  /** Asks the Player to create a number of ships with different sizes given in a list
    * and add them to the Player's fleet
    *
    * @param shipSizes List of the ship sizes to add to the fleet
    * @param fleet     initial fleet (empty by default)
    * @return copy of Player with an updated tracking grid and a new fleet
    */
  @tailrec
  final def placeFleet(shipSizes: List[Int], fleet: List[Ship] = Nil): Player = {
    if (shipSizes != Nil) {
      println("Place ship of size " + shipSizes.head)
      val pos = askForTarget()
      val dir = askForDirection()
      val ship = Ship(pos, dir, shipSizes.head)
      if (ship.isDefined) {
        val newFleet = addShip(fleet, ship.get)
        if (newFleet != fleet) {
          placeFleet(shipSizes.tail, newFleet)
        }
        else {
          println("Couldn't place ship with given coordinates. Please re-enter.")
          placeFleet(shipSizes, fleet)
        }
      }
      else {
        println("Couldn't place ship with given coordinates. Please re-enter.")
        placeFleet(shipSizes, fleet)
      }
    }
    else {
      updatePlayer(primaryGrid = primaryGrid.addFleet(fleet), fleet = fleet)
    }
  }

  /** Ask the player for a target cell coordinates
    *
    * @return a Tuple containing the coordinates of the chosen cell
    */
  def askForTarget(): (Int, Int)

  /** Ask the player for a direction
    *
    * @return true if the given direction is horizontal and false if it's vertical
    */
  def askForDirection(): Boolean

  /** Adds a ship to a fleet (which is the player's fleet by default)
    *
    * TODO should return option
    *
    * @param fleet list of ships
    * @param ship  ship to add to the list
    * @return new fleet or same fleet in case of collision
    */
  def addShip(fleet: List[Ship] = fleet, ship: Ship): List[Ship] = {
    if (fleet == Nil) {
      ship :: Nil
    }
    else if (Helper.checkCollision(fleet.head, ship)) {
      fleet.head :: addShip(fleet.tail, ship)
    }
    else {
      fleet
    }
  }

  /** Shoot a player and update the tracking grid
    *
    * @param p2  the targeted player
    * @param pos position of the cell to shoot
    * @return a Tuple containing the actual player with an updated tracking grid and the targeted player with an updated primary grid
    */
  def shoot(p2: Player, pos: (Int, Int)): (Player, Player) = {
    val result = p2.takeShot(pos) //return new p2 and true if hit, false if missed
    val newP2 = result._1
    val newP1 = updateTracking(pos, result._2) //return new p1
    (newP1, newP2)
  }

  /** Take a shot on a given cell position
    *
    * @param pos position of the cell to shoot
    * @return Tuple containing a copy of Player with an updated primary grid and a Boolean telling if the shot cell was hit or missed
    */
  def takeShot(pos: (Int, Int)): (Player, Boolean) = {
    // TODO improve this, it's just a hell ...
    var newPlayer = updatePlayer(primaryGrid = Grid(primaryGrid.cells.patch(pos._1, Seq(primaryGrid.cells(pos._1).patch(pos._2, Seq(primaryGrid.cells(pos._1)(pos._2).shoot), 1)), 1)))
    val cellIsHit = newPlayer.primaryGrid.cells(pos._1)(pos._2).isHit
    if (cellIsHit) {
      newPlayer = newPlayer.takeFleetDamage(pos)
    }
    (newPlayer, cellIsHit)
  }

  /** Update the players fleet after a ship has taken damage on a given cell position
    *
    * @param pos position of the damaged cell that belongs to one of the player's ships
    * @return copy of Player with updated fleet
    */
  def takeFleetDamage(pos: (Int, Int)): Player = {
    updatePlayer(
      fleet = fleet.map(
        s => {
          Ship(s.cells.map(
            c => {
              if (c.x == pos._1 && c.y == pos._2) {
                Cell(c.x, c.y, 3).get
              }
              else {
                c
              }
            }))
        }))
  }

  /** Increments the Player's score by one
    *
    * @return Player with new score
    */
  def incrementScore: Player = {
    updatePlayer(score = score + 1)
  }

  /** Tells if the player has lost meaning that all of his ships have sunk
    *
    * @param fleet List of ships to check (player's ships by default)
    * @return true if all of the ships have sunk and false otherwise
    */
  @tailrec
  final def lost(fleet: List[Ship] = fleet): Boolean = {
    if (fleet == Nil) {
      true
    }
    else {
      if (!fleet.head.isSunk()) {
        return false
      }
      lost(fleet.tail)
    }
  }


}

