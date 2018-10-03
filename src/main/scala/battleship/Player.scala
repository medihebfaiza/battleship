package battleship

/** A Player who plays the game
  *
  * @constructor create a new player with a grid, fleet and a number.
  * @param primaryGrid player's grid
  * @param fleet       player's ships
  * @param number      player's number
  */
trait Player {

  val primaryGrid: Grid
  val trackingGrid: Grid
  val fleet: List[Ship]
  val number: Int

  def updatePlayer(primaryGrid: Grid = primaryGrid, trackingGrid: Grid = trackingGrid, fleet: List[Ship] = fleet, number: Int=number): Player

  def takeFleetDamage(pos : (Int, Int)): Player = {
    updatePlayer(
      fleet = fleet.map(
        s => {Ship(s.cells.map(
          c => {
            if (c.x == pos._1 && c.y == pos._2){
              Cell(c.x,c.y,3).get
            }
            else {
              c
            }
    }))}))
  }

  def takeShot(pos: (Int, Int)): (Player, Boolean) = {
    // TODO improve this, it's just a hell ...
    var newPlayer = updatePlayer(primaryGrid = Grid(primaryGrid.cells.patch(pos._1, Seq(primaryGrid.cells(pos._1).patch(pos._2, Seq(primaryGrid.cells(pos._1)(pos._2).shoot), 1)), 1)))
    val cellIsHit = newPlayer.primaryGrid.cells(pos._1)(pos._2).isHit
    if (cellIsHit) {
      newPlayer = newPlayer.takeFleetDamage(pos)
    }
    (newPlayer, cellIsHit)
  }

  def updateTracking(pos: (Int, Int), hit: Boolean): Player = {
    if (hit) {
      updatePlayer(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markHit), 1)), 1)))
    }
    else {
      updatePlayer(trackingGrid = Grid(trackingGrid.cells.patch(pos._1, Seq(trackingGrid.cells(pos._1).patch(pos._2, Seq(trackingGrid.cells(pos._1)(pos._2).markMissed), 1)), 1)))
    }
  }

  def lost(fleet: List[Ship] = fleet): Boolean = {
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

  def placeFleet(shipSizes: List[Int], fleet: List[Ship] = Nil): Player = {
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

  def askForTarget(): (Int, Int)

  def askForDirection(): Boolean

  // TODO should return option
  // returns new fleet or same fleet in case of collision
  def addShip(f: List[Ship], s: Ship): List[Ship] = {
    if (f == Nil) {
      s :: Nil
    }
    else if (checkCollision(f.head, s)) {
      f.head :: addShip(f.tail, s)
    }
    else {
      f
    }
  }

  def cellInList(cell: Cell, list: List[Cell]): Boolean = {
    if (list != Nil) {
      list.head == cell || cellInList(cell, list.tail)
    }
    else {
      false
    }
  }

  // True if ships don't collide
  def checkCollision(s1: Ship, s2: Ship): Boolean = {

    def cellsCollide(cl1: List[Cell], cl2: List[Cell]): Boolean = {

      if (cl1 == Nil || cl2 == Nil) {
        false
      }
      else {
        cellInList(cl1.head, cl2) || cellsCollide(cl1.tail, cl2)
      }
    }

    !cellsCollide(s1.cells, s2.cells)
  }

  def shoot(p2: Player, pos: (Int, Int)): (Player, Player) = {
    val result = p2.takeShot(pos) //return new p2 and true if hit, false if missed
    val newP2 = result._1
    val newP1 = updateTracking(pos, result._2) //return new p1
    (newP1, newP2)
  }
}

