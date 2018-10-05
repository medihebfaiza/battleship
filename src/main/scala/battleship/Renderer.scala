package battleship

class Renderer() {
  def renderTitle(): Unit = {
    clear()
    println(Console.GREEN)
    println("  ___       _   _   _        _    _      ")
    println(" | _ ) __ _| |_| |_| |___ __| |_ (_)_ __                  __/___")
    println(" | _ \\/ _` |  _|  _| / -_|_-< ' \\| | '_ \\           _____/______|")
    println(" |___/\\__,_|\\__|\\__|_\\___/__/_||_|_| .__/   _______/_____\\_______\\_____")
    println("  by Mohamed Iheb FAIZA            |_|      \\              < < <       |")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println(Console.WHITE)
  }

  def renderCell(c: Cell): Unit = {
    var sprite: String = c.status match {
      case 1 => Console.BLUE + "██" + Console.WHITE
      case 2 => Console.YELLOW + "▒▒" + Console.WHITE
      case 3 => Console.RED + "▓▓" + Console.WHITE
      case _ => Console.BLUE + "░░" + Console.WHITE
    }
    print(sprite)
  }

  def renderRow(r: List[Cell], tracking: Boolean = false): Unit = {
    if (r != Nil) {
      renderCell(r.head)
      renderRow(r.tail)
    }
  }

  def renderLabels(): Unit = {
    print(" ")
    ('A' to 'J').map((c) => {
      print(c + " ")
    })
    println
  }

  def renderGrid(g: List[List[Cell]], index: Int = 0, tracking: Boolean = false): Unit = {
    if (g != Nil) {
      print(index)
      renderRow(g.head)
      println
      renderGrid(g.tail, index + 1)
    }
  }

  def clear(): Unit = {
    print("\033[H\033[2J")
  }

  def render(gameState: GameState): Unit = {
    clear()
    println(Console.BLUE + "Player " + gameState.players._1.number + " turn" + Console.WHITE)
    println
    println("Your primary grid")
    renderLabels()
    renderGrid(gameState.players._1.primaryGrid.cells)
    println
    println("Your tracking grid")
    renderLabels()
    renderGrid(gameState.players._1.trackingGrid.cells)
    println
  }
}