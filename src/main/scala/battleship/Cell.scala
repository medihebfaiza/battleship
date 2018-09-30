package battleship

/** Cell
  *
  * @constructor create a cell with given position and status
  * @param _x      the cell's row number on the grid
  * @param _y      the cell's column number on the grid
  * @param _status the cell's status
  */
class Cell(private var _x: Int = 0, private var _y: Int = 0, private var _status: Int = 0) {

  private val bound = 10
  private val statusLabels = List("empty", "has ship", "missed", "hit")

  // this will invoke setters during construction
  x = _x
  y = _y
  status = _status

  /** x getter
    *
    * @return x
    */
  def x: Int = _x

  /** x setter
    *
    * @param newValue x's new value
    */
  def x_=(newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printPositionWarning
  }

  /**
    * y getter
    *
    * @return y
    */
  def y: Int = _y

  /** y setter
    *
    * @param newValue y's new value
    */
  def y_=(newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printPositionWarning
  }

  /** status getter
    *
    * @return status
    */
  def status: Int = _status

  /** status setter
    *
    * @param newValue status' new value
    */
  def status_=(newValue: Int): Unit = {
    if (newValue < statusLabels.size) _status = newValue else printStatusWarning
  }

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

  private def printPositionWarning = println("WARNING: Cell Out of bounds")

  private def printStatusWarning = println("WARNING: Invalid Status")
}