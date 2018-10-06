package battleship

/** Grid immutable data structure composed of a matrix (list of lists) of cells
  *
  * @param cells cells matrix
  */
case class Grid(cells: List[List[Cell]]) {

  /** Adds a fleet to the grid
    *
    * @param fleet list of Ships to add to the grid
    * @return a new Grid with ships
    */
  def addFleet(fleet: List[Ship]): Grid = {
    if (fleet != Nil) {
      addShipCells(fleet.head.cells).addFleet(fleet.tail)
    }
    else {
      this
    }
  }

  /** Adds a list of non empty (has ship) cells to the grid
    *
    * @param shipCells list of cells containing a ship to add to grid
    * @return new Grid with ship cells added to it
    */
  def addShipCells(shipCells: List[Cell]): Grid = {
    if (shipCells != Nil) {
      // TODO : This is just a hell, maybe use map instead
      copy(cells = cells.patch(
        shipCells.head.x,
        Seq(cells(shipCells.head.x).patch(
          shipCells.head.y,
          Seq(shipCells.head),
          1)),
        1)).addShipCells(shipCells.tail)

    }
    else {
      this
    }
  }

}

/** Factory for [[battleship.Grid]] instances. */
object Grid {

  private val size = Config.gridSize

  /** Creates a new Grid from a cells matrix
    * If the cells matrix is empty it creates a new one
    *
    * @param cells cells matrix
    * @return new Grid with given cells matrix
    */
  def apply(cells: List[List[Cell]] = Nil): Grid = {
    if (cells == Nil) {
      new Grid(createCells().get) // In this case we are a hundred percent sure that createCells will return a Some
    }
    else {
      new Grid(cells) // In this case we are a hundred percent sure that createCells will return a Some
    }
  }

  /** Creates a row of empty cells recursively
    *
    * @param rowNumber Number of the row, all cells created on this row will have x = rowNumber
    * @param index     indicates the column that is being created during recursion, the cell in this column will have y = index
    * @return Some if the row of cells is created successfully, None if a problem occurred
    */
  def createRow(rowNumber: Int, index: Int = 0): Option[List[Cell]] = {
    if (index < size) {
      val head = Cell(rowNumber, index)
      val tail = createRow(rowNumber, index + 1)
      if (head.isDefined && tail.isDefined) {
        Some(head.get :: tail.get)
      }
      else {
        None
      }
    }
    else {
      Some(Nil)
    }
  }

  /** Creates a matrix of cells recursively
    *
    * @param index indicates the number of the row that is being created during recursion
    * @return Some if cells matrix is created successfully and None if an error occured
    */
  def createCells(index: Int = 0): Option[List[List[Cell]]] = {
    if (index < size) {
      val head = createRow(index)
      val tail = createCells(index + 1)
      if (head.isDefined && tail.isDefined) {
        Some(head.get :: tail.get)
      }
      else {
        None
      }
    }
    else {
      Some(Nil)
    }
  }
}