package battleship

object Helper {

  /** Creates a row of empty cells recursively
    *
    * @param rowNumber Number of the row, all cells created on this row will have x = rowNumber
    * @param index     indicates the column that is being created during recursion, the cell in this column will have y = index
    * @return Some if the row of cells is created successfully, None if a problem occurred
    */
  def createCellsRow(rowNumber: Int, index: Int = 0): Option[List[Cell]] = {
    if (index < Config.gridSize) {
      val head = Cell(rowNumber, index)
      val tail = createCellsRow(rowNumber, index + 1)
      if (head.isDefined && tail.isDefined) Some(head.get :: tail.get)
      else None
    }
    else Some(Nil)
  }

  /** Creates a matrix of cells recursively
    *
    * @param index indicates the number of the row that is being created during recursion
    * @return Some if cells matrix is created successfully and None if an error occured
    */
  def createCells(index: Int = 0): Option[List[List[Cell]]] = {
    if (index < Config.gridSize) {
      val head = createCellsRow(index)
      val tail = createCells(index + 1)
      if (head.isDefined && tail.isDefined) Some(head.get :: tail.get)
      else None
    }
    else Some(Nil)
  }

  /** Create a list of successive cells from an initial position, a direction and a size.
    * The direction of creation is horizontally from left to right or vertically from up to down
    *
    * @param initialPos a tuple (x,y) of the first position of the ship on the grid
    * @param direction  the direction of creation, true for horizontal and false for vertical
    * @param size       number of cells
    * @return list of cells
    */
  def createShipCells(initialPos: (Int, Int), direction: Boolean, size: Int): List[Cell] = {
    if (direction) horizontalCells(initialPos, size)
    else verticalCells(initialPos, size)
  }

  /** Create a list of successive cells aligned horizontally.
    *
    * @param currentPos starting position
    * @param size       number of cells to create
    * @return a list of horizontal cells
    */
  def horizontalCells(currentPos: (Int, Int), size: Int = 2): List[Cell] = {
    if (size > 0) Cell(currentPos._1, currentPos._2, 1).get :: horizontalCells((currentPos._1, currentPos._2 + 1), size - 1)
    else Nil
  }

  /** Create a list of successive cells aligned vertically.
    *
    * @param currentPos starting position
    * @param size       number of cells to create
    * @return a list of horizontal cells
    */
  def verticalCells(currentPos: (Int, Int), size: Int = 2): List[Cell] = {
    if (size > 0) Cell(currentPos._1, currentPos._2, 1).get :: verticalCells((currentPos._1 + 1, currentPos._2), size - 1)
    else Nil
  }

  /** Checks if two lists of cells have at least one cell in common
    *
    * @param cl1 first list of cells
    * @param cl2 second list of cells
    * @return true if the two list have at least one cell in common and false otherwise
    */
  def cellsCollide(cl1: List[Cell], cl2: List[Cell]): Boolean = {
    if (cl1.isEmpty || cl2.isEmpty) false
    else cellInList(cl1.head, cl2) || cellsCollide(cl1.tail, cl2)
  }

  /** Checks if a cell is present in a list of cells
    *
    * @param cell the cell to look for
    * @param list the list to check
    * @return true if cell is present in list and false otherwise
    */
  def cellInList(cell: Cell, list: List[Cell]): Boolean = {
    if (list != Nil) list.head == cell || cellInList(cell, list.tail)
    else false
  }

  /** Checks if two ships don't collide meaning they have no cells sharing the same position
    *
    * @param s1 first ship
    * @param s2 second ship
    * @return true if the ships don't collide and false otherwise
    */
  def checkCollision(s1: Ship, s2: Ship): Boolean = {
    !cellsCollide(s1.cells, s2.cells)
  }

  /** Adds new potential targets to a targets Set surrounding a given position
    *
    * @param oldTargets targets Set to add to
    * @param pos        must be in bounds
    * @return new targets Set
    */
  def addTargets(oldTargets: Set[(Int, Int)], pos: (Int, Int)): Set[(Int, Int)] = {
    var newTargets = oldTargets
    if (pos._1 - 1 >= 0) newTargets = newTargets + ((pos._1 - 1, pos._2))
    if (pos._1 + 1 < Config.gridSize) newTargets = newTargets + ((pos._1 + 1, pos._2))
    if (pos._2 - 1 >= 0) newTargets = newTargets + ((pos._1, pos._2 - 1))
    if (pos._2 + 1 < Config.gridSize) newTargets = newTargets + ((pos._1, pos._2 + 1))
    newTargets
  }

}
