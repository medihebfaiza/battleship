package battleship
import battleship._

/** A Player who plays the game
  *
  * @constructor create a new player with a grid, fleet and a number.
  * @param _primaryGrid player's grid
  * @param _fleet player's ships
  * @param _number player's number
  */
class Player(private var _primaryGrid: Grid = new Grid(), private var _trackingGrid: Grid = new Grid(), private var _fleet: List[Ship] = Nil, private var _number: Int = 1){

    primaryGrid = _primaryGrid
    trackingGrid = _trackingGrid
    fleet = _fleet
    number = _number

    def primaryGrid = _primaryGrid
    def trackingGrid = _trackingGrid
    def fleet = _fleet
    def number = _number

    def primaryGrid_= (newValue: Grid): Unit = {
        _primaryGrid = newValue
    }
    def trackingGrid_= (newValue: Grid): Unit = {
      _trackingGrid = newValue
    }
    def fleet_= (newValue: List[Ship]): Unit = {
        _fleet = newValue
    }
    def number_= (newValue: Int): Unit = {
        _number = newValue
    }

    // TODO Here we modify the opponent and we should avoid that
    // TODO in this function we should also update the tracking grid
    def shoot(pos:(Int, Int), opponent: Player ): Unit = {
        // TODO verify pos
        opponent.takeShot(pos)
    }

    def takeShot(pos:(Int,Int)): Unit = {
        // TODO this is just a hell ...
        primaryGrid.cells = primaryGrid.cells.patch(pos._1, Seq(primaryGrid.cells(pos._1).patch(pos._2, Seq(primaryGrid.cells(pos._1)(pos._2).shoot), 1)), 1)
    }

    def lost(fleet: List[Ship] = fleet):Boolean = {
        if (fleet == Nil) {
            return true
        }
        else {
            if (!fleet.head.isSunk()){
                return false
            }
            lost(fleet.tail)
        }
    }

    // TODO should return new Player
    // TODO should be recursive 
    def placeFleet(shipSizes:List[Int], fleet: List[Ship] = Nil): Unit = {
        if (shipSizes != Nil) {
            println("Place ship of size " + shipSizes.head)
            val pos = askForTarget()
            val dir = askForDirection()
            val ship = new Ship(pos, dir, shipSizes.head)
            val newFleet = addShip(fleet, ship) 
            if (newFleet != Nil) {
                placeFleet(shipSizes.tail, newFleet)
            }
            else {
                println("Couldn't place ship with given coordinates. Please re-enter.")
                placeFleet(shipSizes,fleet)
            }
        }
        else {
            this.fleet = fleet
            this.primaryGrid.addFleet(fleet)
        }
    }

    // TODO verify user input
    def askForTarget(): (Int, Int) = {
        println("target :")
        val x = readLine("x > ")
        val y = readLine("y > ")
        try {
            (x.toInt , y.toInt)
        }
        catch {
            case e : Exception => 
                println("Invalid target please retype it :") 
                askForTarget()
        }
    }

    def askForDirection(): Boolean = {
      readLine("direction (H/V) > ") match {
        case "h" | "H" => true
        case _ => false
      }
    }

    // TODO should return option
    // returns new fleet or same fleet in case of collision
    def addShip(f: List[Ship], s: Ship): List[Ship] = {
        if (f == Nil) {
            s::Nil
        }
        else if (checkCollision(f.head, s)) {
            f.head::addShip(f.tail, s)
        }
        else {
            f
        }
    }

    // TODO find another way to detect collision without recursivity or do it dynamically
    // True if ships don't collide
    def checkCollision(s1: Ship, s2: Ship): Boolean = {
        
        def cellsCollide(cl1: List[Cell], cl2: List[Cell]): Boolean = {
            
            if (cl1 == Nil || cl2 == Nil) {
                return false
            }
            else {
                val c1 = cl1.head
                val c2 = cl2.head
                if ((c1.x == c1.x) && (c1.y == c2.y)) {
                    return true
                }
                else {
                    return cellsCollide(cl1, cl2.tail) || cellsCollide(cl1.tail, cl2)
                }
            }
            
        }   

        !cellsCollide(s1.cells, s2.cells)
    }
}
