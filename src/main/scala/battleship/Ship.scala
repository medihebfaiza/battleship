package battleship

import scala.annotation.tailrec

/** Ship immutable data structure made of a list of Cells
  *
  * @param cells list of ship's cells
  */
case class Ship(cells: List[Cell]) {

  /** Checks if the ship is sunk meaning all of it's cells are hit
    *
    * @param cells Ship's cells to check
    * @return true if all the cells are hit, false if not
    */
  @tailrec
  final def isSunk(cells: List[Cell] = cells): Boolean = {
    if (cells == Nil) {
      true
    }
    else {
      if (!cells.head.isHit) {
        return false
      }
      isSunk(cells.tail)
    }
  }

}

/** Factory for [[battleship.Ship]] instances. */
object Ship {

  /** Create a new Ship with an initial position a direction and a size
    *
    * @param initialPos Ship's farthest upper left cell position
    * @param direction  Ship's direction, true if it's horizontal and false if it's vertical
    * @param size       Ship's size
    * @return Some if
    */
  def apply(initialPos: (Int, Int) = (0, 0), direction: Boolean = false, size: Int): Option[Ship] = {
    if (0 <= initialPos._1 && initialPos._1 < Config.gridSize && 0 <= initialPos._2 && initialPos._2 < Config.gridSize && size <= Config.maxShipSize) {
      if (direction && (initialPos._2 + size) > Config.gridSize) {
        None
      }
      else if (!direction && (initialPos._1 + size) > Config.gridSize) {
        None
      }
      else {
        Some(new Ship(createCells(initialPos, direction, size)))
      }
    }
    else {
      None
    }
  }

  /** Create a list of successive cells from an initial position, a direction and a size.
    * The direction of creation is horizontally from left to right or vertically from up to down
    *
    * @param initialPos a tuple (x,y) of the first position of the ship on the grid
    * @param direction  the direction of creation, true for horizontal and false for vertical
    * @param size       number of cells
    * @return list of cells
    */
  def createCells(initialPos: (Int, Int), direction: Boolean, size: Int): List[Cell] = {
    if (direction) {
      horizontalCells(initialPos, size)
    }
    else {
      verticalCells(initialPos, size)
    }
  }

  /** Create a list of successive cells aligned horizontally.
    *
    * @param currentPos starting position
    * @param size       number of cells to create
    * @return a list of horizontal cells
    */
  def horizontalCells(currentPos: (Int, Int), size: Int = 2): List[Cell] = {
    if (size > 0) {
      Cell(currentPos._1, currentPos._2, 1).get :: horizontalCells((currentPos._1, currentPos._2 + 1), size - 1)
    }
    else {
      Nil
    }
  }

  /** Create a list of successive cells aligned vertically.
    *
    * @param currentPos starting position
    * @param size       number of cells to create
    * @return a list of horizontal cells
    */
  def verticalCells(currentPos: (Int, Int), size: Int = 2): List[Cell] = {
    if (size > 0) {
      Cell(currentPos._1, currentPos._2, 1).get :: verticalCells((currentPos._1 + 1, currentPos._2), size - 1)
    }
    else {
      Nil
    }
  }
}