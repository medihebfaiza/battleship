package battleship

/* Renderer Singleton takes charge of display */
object Renderer {

  /** Prints the game title */
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

  /** Prints a cell sprite depending on its status
    *
    * @param cell Cell to render
    */
  def renderCell(cell: Cell): Unit = {
    var sprite: String = cell.status match {
      case 1 => Console.BLUE + "██"
      case 2 => Console.YELLOW + "▒▒"
      case 3 => Console.RED + "▓▓"
      case _ => Console.BLUE + "░░"
    }
    sprite = sprite + Console.WHITE
    print(sprite)
  }

  /** Prints a List of cells
    *
    * @param row List of cells to render
    */
  def renderRow(row: List[Cell]): Unit = {
    if (row != Nil) {
      renderCell(row.head)
      renderRow(row.tail)
    }
  }

  /** Prints a series of characters from A to J */
  def renderLabels(): Unit = {
    print(" ")
    ('A' to 'J').foreach(c => {
      print(c + " ")
    })
    println
  }

  /** Render a grid row by row
    *
    * @param grid grid to render
    * @param index index of current row to render
    */
  def renderGrid(grid: List[List[Cell]], index: Int = 0): Unit = {
    if (grid != Nil) {
      print(index)
      renderRow(grid.head)
      println
      renderGrid(grid.tail, index + 1)
    }
  }

  /** Clear Console */
  def clear(): Unit = {
    print("\033[H\033[2J") // replace with Console.RESET maybe
  }

  /** Render first player's primary grid and tracking grid from a gameState
    *
    * @param gameState gamestate to retrieve player's data from
    */
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