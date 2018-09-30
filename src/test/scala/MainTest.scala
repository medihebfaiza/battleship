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
    assert(p1.grid.cells(0)(1).isMissed) // TODO implement a isMissed isHit methods for Cell
  }
  test("P2 Shooting P1 should hit") {
    p2.shoot((0,0), p1)
    assert(p1.grid.cells(0)(0).isHit)
  }
  test("P1's ship is hit but not sunk") {
    assert(p1.fleet(0).isSunk() == false)
  }
  test("P1 did not lose yet") {
    assert(p1.lost() == false)
  }
  test("Collision detecting when adding ship #1"){
    var newShip = new Ship((0,0), false, 5)
    assert(p1.addShip(p1.fleet, newShip) == p1.fleet)
  }
  test("Collision detecting when adding ship #2"){
    var newShip = new Ship((5,3), true, 5)
    assert(p1.addShip(p1.fleet, newShip) != p1.fleet)
  }
  test("Collision detecting when adding ship #3"){
    var newShip = new Ship((5,3), true, 5)
    var newShip2 = new Ship((4,5), false, 3)
    assert(p1.addShip(p1.addShip(p1.fleet, newShip), newShip2) == p1.addShip(p1.fleet, newShip))
  }
}