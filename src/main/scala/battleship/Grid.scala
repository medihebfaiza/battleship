package battleship

/** Grid immutable data structure composed of a matrix (list of lists) of cells
  *
  * @param cells cells matrix
  */
case class Grid(cells: List[List[Cell]]) {

  /** Adds a fleet to the grid
    *
    * @param fleet list of Ships to add to the grid
    * @return a new Grid with ships added
    */
  def addFleet(fleet: List[Ship]): Grid = {
    if (fleet != Nil) addShip(fleet.head).addFleet(fleet.tail)
    else this
  }

  /** Add a ship to the grid
    *
    * @param ship ship to add to the grid
    * @return a new Grid with ship added
    */
  def addShip(ship: Ship): Grid = {
    addShipCells(ship.cells)
  }

  /** Adds a list of non empty (has ship) cells to the grid
    *
    * @param shipCells list of cells containing a ship to add to grid
    * @return new Grid with ship cells added to it
    */
  def addShipCells(shipCells: List[Cell]): Grid = {
    if (shipCells != Nil) {
      // TODO : This is just a hell, maybe use map or updated instead
      copy(cells = cells.patch(
        shipCells.head.x,
        Seq(cells(shipCells.head.x).patch(
          shipCells.head.y,
          Seq(shipCells.head),
          1)),
        1)).addShipCells(shipCells.tail)
    }
    else this
  }

}

/** Factory for [[battleship.Grid]] instances. */
object Grid {

  /** Creates a new Grid from a cells matrix
    * If the cells matrix is empty it creates a new one
    *
    * @param cells cells matrix
    * @return new Grid with given cells matrix
    */
  def apply(cells: List[List[Cell]] = Nil): Grid = {
    if (cells.isEmpty) new Grid(Helper.createCells().get) // In this case we are a hundred percent sure that createCells will return a Some
    else new Grid(cells) // In this case we are a hundred percent sure that createCells will return a Some
  }

}
