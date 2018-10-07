import org.scalatest._
import battleship._

class PlayerTest extends FunSuite with DiagrammedAssertions {
  var ship1 = Ship((0,0), false, 2)
  val p1 = Human(List(ship1.get), 1)

  test("Player Grid cells having ships are equal to ship cells in fleet") {
    assert(p1.fleet(0).cells(0) == p1.primaryGrid.cells(0)(0))
  }

  test("Shooting Player should miss") {
    assert(!p1.takeShot((0,1))._2)
  }

  test("Shooting Player should hit") {
    assert(p1.takeShot((0,0))._2)
  }

  test("Player's ship is hit but not sunk") {
    assert(!p1.takeShot((0,0))._1.fleet(0).isSunk())
  }

  test("Player did not lose yet") {
    assert(!p1.takeShot((0,0))._1.lost())
  }

  test("Player did lose") {
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