package battleship
import battleship._

class Grid() {
    private var _cells: List[List[Cell]] = _ // _ means default default uninitialized value
    private val size = 10

    def cells = _cells
    def cells_= (newValue: List[List[Cell]]): Unit = {
        if (newValue.size == size) _cells = newValue else printWarning
    }

    cells = createCells()

    def createRow(rowNumber: Int, index:Int = 0): List[Cell] = {
        if (index < size) {
            new Cell(rowNumber, index)::createRow(rowNumber, index + 1)
        } 
        else {
            Nil
        }
    }

    def createCells(index:Int = 0): List[List[Cell]] = {
        if (index < size) {
            createRow(index)::createCells(index + 1)
        } 
        else {
            Nil
        }
    }
    
    def addFleet(fleet : List[Ship]):Unit = {
        // TODO add ship without modifying current grid cells. hint use options
        fleet.map(ship =>{
            ship.cells.map(cell => {
                cells = cells.patch(cell.x, Seq(cells(cell.x).patch(cell.y, Seq(cell), 1)),1) // TODO this is not good must create new cell and use assign
            })
        })
    }
    

    private def printWarning = println("WARNING: Unvalid grid size")
}