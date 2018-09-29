package battleship
import battleship._

class Renderer(){
  def renderCell(c: Cell, tracking: Boolean=false): Unit = {
    var sprite : String = ""
    c.status match {
      case 1 => if (tracking == false) {sprite =  Console.BLUE + "██" + Console.WHITE}
      case 2 => sprite = Console.YELLOW + "▒▒" + Console.WHITE
      case 3 => sprite = Console.RED + "▓▓" + Console.WHITE
      case _ => sprite = Console.BLUE + "░░" + Console.WHITE
    }
    print(sprite)
  } 

  def renderRow(r: List[Cell], tracking: Boolean=false): Unit = {
    if(r != Nil){
      renderCell(r.head)
      renderRow(r.tail)
    }
  }

  def renderLabels(): Unit = {
    print(" ")
    ('A' to 'J').map((c) => {print(c + " ")})
    println
  }

  def renderGrid(g: List[List[Cell]], index: Int = 0, tracking: Boolean=false): Unit = {
    if(g != Nil){
      print(index)
      renderRow(g.head)
      println 
      renderGrid(g.tail, index + 1)
    }
  }

  def clear():Unit = {
    print("\033[H\033[2J")
  }
  
  def render(gameState: GameState): Unit = {
    clear()
    println(Console.BLUE + "Player " + gameState.players._1.number + " turn" + Console.WHITE)
    println
    println("Your grid")
    renderLabels()
    renderGrid(gameState.players._1.grid.cells, tracking = false) 
    println
    println("Your tracking grid")
    renderLabels()
    renderGrid(gameState.players._2.grid.cells, tracking = true) // TODO fix tracking not working
    println
  }
}