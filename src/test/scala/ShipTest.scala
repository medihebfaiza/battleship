import org.scalatest._
import battleship._

class ShipTest extends FunSuite with DiagrammedAssertions {

  test("Ships cannot have cells out of the grid #H") {
    assert(Ship((0,9), true, 5).isEmpty)
  }

  test("Ships cannot have cells out of the grid #V") {
    assert(Ship((9,0), false, 5).isEmpty)
  }

  test("New created ship is not sunk yet") {
    assert(!Ship((0,0), false, 5).get.isSunk())
  }

}