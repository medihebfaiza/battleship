import org.scalatest._
import battleship._

class MainTest extends FunSuite with DiagrammedAssertions {
  var ship1 = Ship((0,0), false, 2)
  var ship2 = Ship((5,0), true, 5)
  val p1 = Human(List(ship1.get), 1)
  val p2 = Human(List(ship2.get), 2)

  test("Cell : create cell in bounds #3") {
    assert(Cell(9,9).isDefined)
  }

  test("Cell : cannot create cells out of bounds #1") {
    assert(Cell(-1,-2).isEmpty)
  }

  test("Cell : cannot create cells out of bounds #2") {
    assert(Cell(0,10).isEmpty)
  }

  test("Ship : Ships cannot have cells out of the grid #H") {
    assert(Ship((0,9), true, 5).isEmpty)
  }

  test("Ship : Ships cannot have cells out of the grid #V") {
    assert(Ship((9,0), false, 5).isEmpty)
  }

  test("Player : Grid cell patching (cells must belong to Grid and Ship at the same time)") { // Cells are equal but not same instance
    assert(p1.fleet(0).cells(0) == p1.primaryGrid.cells(0)(0))
  }

  test("Player : Shooting Player should miss") {
    assert(!p1.takeShot((0,1))._2)
  }

  test("Player : Shooting Player should hit") {
    assert(p1.takeShot((0,0))._2) // TODO implement a isMissed isHit methods for Cell
  }

  test("P1's ship is hit but not sunk") {
    assert(!p1.takeShot((0,0))._1.fleet(0).isSunk())
  }

  test("P1 did not lose yet") {
    assert(!p1.takeShot((0,0))._1.lost())
  }

  test("P1 did lose") {
    assert(p1.takeShot((0,0))._1.takeShot((1,0))._1.lost())
  }

  test("Collision detecting when adding ship #1"){
    var newShip = Ship((0,0), false, 5)
    assert(p1.addShip(p1.fleet, newShip.get) == p1.fleet)
  }
  test("Collision detecting when adding ship #2"){
    var newShip = Ship((5,3), true, 5)
    assert(p1.addShip(p1.fleet, newShip.get) != p1.fleet)
  }
  test("Collision detecting when adding ship #3"){
    var newShip = Ship((5,3), true, 5)
    var newShip2 = Ship((4,5), false, 3)
    assert(p1.addShip(p1.addShip(p1.fleet, newShip.get), newShip2.get) == p1.addShip(p1.fleet, newShip.get))
  }
}