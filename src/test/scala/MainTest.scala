import org.scalatest._
import battleship._

class MainTest extends FunSuite with DiagrammedAssertions {
  var grid1 = new Grid()
  var grid2 = new Grid()
  var ship1 = new Ship((0,0), false, 5)
  var ship2 = new Ship((5,0), true, 6) // TODO size verif is not working
  var p1 = new Player(grid1, List(ship1), 1)
  var p2 = new Player(grid2, List(ship2), 1) 
  p1.grid.addFleet(p1.fleet) // TODO do this inside player
  p2.grid.addFleet(p2.fleet) // TODO do this inside player

  test("Grid cell patching (ship cells must belong to Grid)") {
    p1.grid.addFleet(p1.fleet)
    assert(p1.fleet(0).cells(0) == p1.grid.cells(0)(0))
  }
  test("P2 Shooting P1 should miss") {
    p2.shoot((0,1), p1)
    assert(p1.grid.cells(0)(1).status == 2) // TODO implement a isMissed isHit methods for Cell
  }
  test("P2 Shooting P1 should hit") {
    p2.shoot((0,0), p1)
    assert(p1.grid.cells(0)(0).status == 3)
  }
  test("P1's ship is hit but not sunk") {
    assert(p1.fleet(0).isSunk() == false)
  }
  test("P1 did not lose yet") {
    assert(p1.lost() == false)
  }
}