package battleship

import scala.annotation.tailrec

case class Ship(cells: List[Cell]) {

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

object Ship {

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

  def createCells(initialPos: (Int, Int), direction: Boolean, size: Int): List[Cell] = {
    if (direction) {
      horizontalCells(initialPos, size)
    }
    else {
      verticalCells(initialPos, size)
    }
  }

  def horizontalCells(currentPos: (Int, Int), size: Int = 2): List[Cell] = {
    if (size > 0) {
      Cell(currentPos._1, currentPos._2, 1).get :: horizontalCells((currentPos._1, currentPos._2 + 1), size - 1)
    }
    else {
      Nil
    }
  }

  def verticalCells(currentPos: (Int, Int), size: Int = 2): List[Cell] = {
    if (size > 0) {
      Cell(currentPos._1, currentPos._2, 1).get :: verticalCells((currentPos._1 + 1, currentPos._2), size - 1)
    }
    else {
      Nil
    }
  }
}