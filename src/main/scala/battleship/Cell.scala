package battleship

/** Cell immutable data structure
  *
  * @constructor create a cell with given position and status
  * @param x      the cell's row number on the grid, should be in [0,9]
  * @param y      the cell's column number on the grid, should be in [0,9]
  * @param status the cell's status, should be in [0,3]
  */
case class Cell(x: Int, y: Int, status: Int) {

  /** Tells if the cell is empty and not missed
    *
    * @return true if the cell is empty and not missed, false if not
    */
  def isEmpty: Boolean = status == 0

  /** Tells if the cell is a part of a ship and is not hit
    *
    * @return true if the cell is a part of ship and is not hit, false if not
    */
  def hasShip: Boolean = status == 1

  /** Tells if the cell is empty and was shot by a player
    *
    * @return true if the cell was missed and false if not
    */
  def isMissed: Boolean = status == 2

  /** Tells if the cell is a part of a ship and was hit
    *
    * @return true if the cell is hit and false if not
    */
  def isHit: Boolean = status == 3

  /** Shoot a cell
    *
    * @return new cell with new status if the current cell was not shot before
    */
  def shoot: Cell = if (status < 2) new Cell(x, y, status + 2) else this

  /** Mark the cell as missed
    *
    * @return new cell with a missed status
    */
  def markMissed: Cell = copy(status = 2)

  /** Mark the cell as hit
    *
    * @return new cell with a hit status
    */
  def markHit: Cell = copy(status = 3)

}

/** Factory for [[battleship.Cell]] instances. */
object Cell {

  val bound: Int = Config.gridSize
  val nbStatus: Int = Config.nbStatus

  /** create a cell with given position and status
    *
    * @param x      the cell's row number on the grid
    * @param y      the cell's column number on the grid
    * @param status the cell's status
    * @return Some if the given position is in bounds and status is valid, None if not
    */
  def apply(x: Int = 0, y: Int = 0, status: Int = 0): Option[Cell] = {
    if ((0 <= x) && (x < bound) && (0 <= y) && (y < bound) && (0 <= status) && (status < nbStatus))
      Some(new Cell(x, y, status))
    else
      None
  }

}