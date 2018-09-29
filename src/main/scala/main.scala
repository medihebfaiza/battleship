package battleship
import scala.annotation.tailrec
import battleship._

class GameState(turn: Boolean = false, users: (Player, Player)){

}

class Game(gameState: GameState){
  val input = readLine("head(h), tail(t), quit(q) > ")
}

object main extends App {
  var grid1 = new Grid()
  var grid2 = new Grid()
  var ship = new Ship((0,0), false, 5)
  var ship2 = new Ship((5,0), true, 6) // TODO size verif is not working
  var p1 = new Player(grid1, List(ship), 1)
  var p2 = new Player(grid2, List(ship2), 1) 
  p1.grid.addFleet(p1.fleet) // TODO do this inside player
  p2.grid.addFleet(p2.fleet) // TODO do this inside player
  var r = new Renderer()
  println("Player 1")
  r.renderLabels()
  r.renderGrid(p1.grid.cells)
  println("Player 2")
  r.renderLabels()
  r.renderGrid(p2.grid.cells, tracking = true) // TODO fix tracking not working 

  //TEST 
  p2.shoot((0,1), p1)
  println("Player 1 Got hit ")
  r.renderLabels()
  r.renderGrid(p1.grid.cells, tracking = true)
}