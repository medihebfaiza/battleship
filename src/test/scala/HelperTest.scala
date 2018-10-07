import org.scalatest._
import battleship._

class HelperTest extends FunSuite with DiagrammedAssertions {

  test("Cannot create row out of bounds #1") {
    assert(Helper.createCellsRow(10).isEmpty)
  }

  test("Cannot create row out of bounds #2") {
    assert(Helper.createCells(10).getOrElse(Nil).isEmpty)
  }

}