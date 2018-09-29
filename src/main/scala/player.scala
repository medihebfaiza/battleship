package battleship
import battleship._

class Player(private var _grid: Grid, private var _fleet: List[Ship], private var _number: Int = 1){
    
    grid = _grid
    fleet = _fleet
    number = _number

    def grid = _grid 
    def fleet = _fleet
    def number = _number

    def grid_= (newValue: Grid): Unit = {
        _grid = newValue
    }  
    def fleet_= (newValue: List[Ship]): Unit = {
        _fleet = newValue
    }
    def number_= (newValue: Int): Unit = {
        _number = newValue
    }

    // TODO Here we modify the opponent and we should avoid that
    def shoot(pos:(Int, Int), opponent: Player ): Unit = {
        // TODO verify pos
        opponent.takeShot(pos)
    }

    def takeShot(pos:(Int,Int)): Unit = {
        grid.cells(pos._1)(pos._2).status match {
            case 0 => grid.cells(pos._1)(pos._2).status = 2
            case 1 => grid.cells(pos._1)(pos._2).status = 3 //TODO update ships in fleet
            case _ => 
        }
    }

    def lost(fleet: List[Ship] = fleet):Boolean = {
        if (fleet == Nil) {
            true
        }
        else {
            if (!fleet.head.isSunk()){
                false
            }
            lost(fleet.tail)
        }
    }
}
