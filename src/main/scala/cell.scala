package battleship

class Cell(private var _x : Int = 0, private var _y : Int = 0, private var _status : Int = 0) {

  private val bound = 10
  private val statusLabels = List("empty", "has ship", "missed", "hit")

  // this will invoke setters during construction
  x = _x 
  y = _y
  status = _status 

  def x = _x
  def x_= (newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printPositionWarning
  }

  def y = _y
  def y_= (newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printPositionWarning
  }

  def status = _status
  def status_= (newValue: Int): Unit = {
    if (newValue < statusLabels.size) _status = newValue else printStatusWarning
  }

  def isEmpty: Boolean = status == 0
  def hasShip: Boolean = status == 1
  def isMissed: Boolean = status == 2
  def isHit: Boolean = status == 3
  def shoot: Cell = if (status < 2 ) new Cell(x,y,status + 2) else this

  private def printPositionWarning = println("WARNING: Cell Out of bounds")
  private def printStatusWarning = println("WARNING: Invalid Status")
}