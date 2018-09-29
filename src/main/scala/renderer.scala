package battleship
import battleship._

class Renderer(){
  def renderCell(c: Cell, tracking: Boolean=false): Unit = {
    var sprite : String = Console.BLUE + "░░" + Console.WHITE
    c.status match {
      case 0 => sprite = Console.BLUE + "░░" + Console.WHITE
      case 1 => if (tracking == false) sprite =  Console.BLUE + "██" + Console.WHITE
      case 2 => sprite = Console.YELLOW + "▒▒" + Console.WHITE
      case 3 => sprite = Console.RED + "▓▓" + Console.WHITE
      case _ =>
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
  
  def render(game: Game): Unit = {

  }
}