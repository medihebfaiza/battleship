package battleship
import battleship._

case class GameState(players: (Player, Player))

class Game(){
  val shipSizes = List(2,3,3,4,5)
  val renderer = new Renderer // TODO should use companion object
  var p1 = new Player(new Grid(), List(), 1)
  var p2 = new Player(new Grid(), List(), 2)

  println("Player 1 place fleet")
  p1.placeFleet(shipSizes)

  println("Player 2 place fleet")
  p2.placeFleet(shipSizes)

  // TODO gameLoop should not by any mean modify state
  //@tailrec //TODO solve tailrec
  def gameLoop(gameState : GameState):Unit = {
    // TODO create currentPlayer and opponent vals
    renderer.render(gameState)
    val pos = gameState.players._1.askForTarget()
    gameState.players._1.shoot(pos, gameState.players._2)
    
    if (!gameState.players._2.lost()) {
      val newGameState = new GameState((gameState.players._2, gameState.players._1))// TODO this is not good must create new players and stuff 
      gameLoop(newGameState)
    }
    else {
      println(Console.GREEN + "Hurray ! Player " + gameState.players._1.number + " won !")
    } 
  }
  
  gameLoop(GameState((p1,p2)))
}
