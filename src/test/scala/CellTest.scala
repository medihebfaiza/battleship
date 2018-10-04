import org.scalatest._
import battleship._

class CellTest extends FunSuite with DiagrammedAssertions {

  test("Create cell in bounds #3") {
    assert(Cell(9,9).isDefined)
  }

  test("Cannot create cells out of bounds #1") {
    assert(Cell(-1,-2).isEmpty)
  }

  test("Cannot create cells out of bounds #2") {
    assert(Cell(0,10).isEmpty)
  }

  test("Cell is empty by default") {
    assert(Cell(0,0).get.isEmpty)
  }

  test("markedHit") {
    assert(Cell(0,0).get.markHit.isHit)
  }

  test("markedMissed") {
    assert(Cell(0,0).get.markMissed.isMissed)
  }

  test("Shot empty cell should have status missed") {
    assert(Cell(0,0,0).get.shoot.isMissed)
  }

  test("Shot cell with a boat should have status hit") {
    assert(Cell(0,0,1).get.shoot.isHit)
  }
}