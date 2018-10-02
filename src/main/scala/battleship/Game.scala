package battleship
import battleship._

case class GameState(players: (Player, Player))

class Game(ai : Boolean = false){
  val shipSizes = List(2,3,3,4,5)
  val renderer = new Renderer // TODO should use companion object

  println(Console.BLUE + "Player 1 place fleet" + Console.WHITE)
  var p1 = Player().placeFleet(shipSizes) // TODO Problem here, grid is not updated
  println(p1)
  renderer.clear()
  println(Console.BLUE + "Player 2 place fleet" + Console.WHITE)
  var p2 = ai match {
    case false => Player(number = 2).placeFleet(shipSizes)
    case true => null//Ai(number = 2, level = 0)
  }

  //@tailrec //TODO solve tailrec
  def gameLoop(gameState : GameState):Unit = {
    // TODO create currentPlayer and opponent vals
    renderer.render(gameState)
    val pos = gameState.players._1.askForTarget()
    val newPlayers = Player.shoot(gameState.players._1, gameState.players._2, pos)
    
    if (!newPlayers._2.lost()) {
      val newGameState = new GameState((newPlayers._2, newPlayers._1))
      gameLoop(newGameState)
    }
    else {
      println(Console.GREEN + "Hurray ! Player " + gameState.players._1.number + " won !")
    } 
  }
  
  gameLoop(GameState((p1,p2)))
}
