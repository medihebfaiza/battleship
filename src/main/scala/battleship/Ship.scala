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
        Some(new Ship(Helper.createShipCells(initialPos, direction, size)))
      }
    }
    else {
      None
    }
  }

}