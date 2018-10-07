import org.scalatest._
import battleship._

class AiTest extends FunSuite with DiagrammedAssertions {
  var ship1 = Ship((1,1), false, 2)
  var ship2 = Ship((5,0), true, 5)
  val p0 = Ai(number =  1)
  val p1 = Ai(List(ship1.get), 1, level = 1)
  val p2 = Ai(List(ship2.get), 2, level = 2)

  test("AI gives valid targets") {
    val target = p0.askForTarget()
    assert(0 <= target._1 && target._1 < Config.gridSize && 0 <= target._2 && target._2 < Config.gridSize)
  }

  test("AI places the right number of ships") {
    assert(p0.placeFleet(List(2,3,3,4,5)).fleet.size == 5)
  }

  test("AI2 adds four potential targets after a hit") {
    assert(p2.shoot(p1, (1,1))._1.asInstanceOf[Ai].targets.size == 4)
  }
  /**
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
  }*/
}