package battleship

class Ship(private var _initialPos: (Int, Int), private var _direction: Boolean = false, private var _size: Int){

    private val maximumSize = 5
    private val gridSize = 10
    private var _cells: List[Cell] = _

    initialPos = _initialPos
    direction = _direction
    size = _size
    cells = createCells

    def initialPos = _initialPos
    def direction = _direction
    def size = _size
    def cells = _cells

    def initialPos_= (newValue: (Int, Int)): Unit = {
        if (newValue._1 < gridSize && newValue._2 < gridSize) _initialPos = newValue else printPositionWarning
    }
    // TODO size verif is not working
    def size_= (newValue: Int): Unit = {
        if (newValue <= maximumSize) _size = newValue else printSizeWarning
    } 
    def direction_= (newValue: Boolean): Unit = {
        _direction = newValue
    }
    def cells_= (newValue: List[Cell]) = {
        // TODO try to do verifications here
        _cells = newValue
    }

    def createCells(): List[Cell] = {
        direction match {
            case true => horizontalCells(initialPos)
            case false => verticalCells(initialPos)
        }
    }

    def horizontalCells(currentPos: (Int, Int), size: Int = size): List[Cell] = {
        if (size > 0) {
            new Cell(currentPos._1, currentPos._2, 1) :: horizontalCells((currentPos._1, currentPos._2 + 1), size - 1) 
        }
        else {
            Nil
        }
    }

    def verticalCells(currentPos: (Int, Int), size: Int = size): List[Cell] = {
        if (size > 0) {
            new Cell(currentPos._1, currentPos._2, 1) :: verticalCells((currentPos._1 + 1, currentPos._2), size - 1) 
        }
        else {
            Nil
        }
    }

    def isSunk(cells : List[Cell] = cells): Boolean = {
        if (cells == Nil) {
            return true
        }
        else {
            if (cells.head.status != 3){
                return false
            }
            isSunk(cells.tail)
        }
    }

    private def printPositionWarning = println("WARNING: Position out of bounds")
    private def printSizeWarning = println("WARNING: Invalid size")
}