import org.scalatest._
import battleship._

class GridTest extends FunSuite with DiagrammedAssertions {

  val s1 = Ship((0,0), false, 5).getOrElse(null)
  val s2 = Ship((0,1), true, 5).getOrElse(null)

  test("Cannot create row out of bounds #1") {
    assert(Grid.createRow(10).isEmpty)
  }

  test("Cannot create row out of bounds #2") {
    assert(Grid.createCells(10).getOrElse(Nil).isEmpty)
  }

  test("addShipCells") {
    val g1 = Grid().addShipCells(s1.cells)
    assert(!g1.cells(0)(0).isEmpty)
  }

  test("addFleet") {
    val g2 = Grid().addFleet(List(s1,s2))
    assert(!g2.cells(0)(0).isEmpty && !g2.cells(0)(1).isEmpty)
  }

}