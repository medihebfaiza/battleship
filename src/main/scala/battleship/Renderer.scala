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

  /** Prompt User for mode
    *
    * @return chosen mode number
    */
  def askForMode(): Int = {
    println("Choose mode : ")
    println("0 : Human vs Human")
    println("1 : Human vs AI")
    println("2 : Proof")
    println("any : Quit")
    val mode = readLine("> ")
    try {
      if (0 <= mode.toInt && mode.toInt < 3) {
        mode.toInt
      }
      else {
        println("Invalid mode, please re-enter a valid one")
        askForMode()
      }
    }
    catch {
      case e: Exception =>
        System.exit(0)
        0
    }
  }

  /** Prompt user for the number of rounds to play
    *
    * @return number of rounds
    */
  def askForRounds(): Int = {
    println("Enter number of rounds (from 1 to infinity)")
    val rounds = readLine("> ")
    try {
      if (0 <= rounds.toInt) {
        rounds.toInt
      }
      else {
        println("Invalid number of rounds, please re-enter a valid one")
        askForRounds()
      }
    }
    catch {
      case e: Exception =>
        println("Invalid number of rounds, please re-enter a valid one")
        askForRounds()
    }
  }

  /** Prompt user for AI level to play against
    *
    * @return ai level
    */
  def askForAILevel(): Int = {

    println("Choose AI level (from 0 to 2) :")

    val level = readLine("> ")
    try {
      if (0 <= level.toInt && level.toInt < Config.maxAiLevel) {
        level.toInt
      }
      else {
        println("Invalid or unsupported AI level please re-enter a valid one")
        askForAILevel()
      }
    }
    catch {
      case e: Exception =>
        println("Invalid or unsupported AI level please re-enter a valid one")
        askForAILevel()
    }
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

  /** Display a matrix of cells row by row
    *
    * @param cells grid to render
    * @param index index of current row to render
    */
  def renderCells(cells: List[List[Cell]], index: Int = 0): Unit = {
    if (cells != Nil) {
      print(index)
      renderRow(cells.head)
      println
      renderCells(cells.tail, index + 1)
    }
  }

  /** Display a grid
    *
    * @param grid the grid to render
    */
  def renderGrid(grid: Grid): Unit = {
    renderCells(grid.cells)
  }

  /** Clear Console */
  def clear(): Unit = {
    print("\033[H\033[2J") // replace with Console.RESET maybe
  }

  /** Render Player's primary grid when he's placing his fleet
    *
    * @param player Player placing fleet
    */
  def renderPlaceFleet(player: Player): Unit = {
    Renderer.clear()
    println(Console.BLUE + "Player " + player.number + " place fleet" + Console.WHITE)
    Renderer.renderLabels()
    Renderer.renderGrid(player.primaryGrid) // renderGrid should take grid as parameter
    println
  }

  /** Display Player's primary grid and tracking grid
    *
    * @param player Player to render his turn
    */
  def renderTurn(player: Player): Unit = {
    clear()
    println(Console.BLUE + "Player " + player.number + " turn" + Console.WHITE)
    println
    println("Your primary grid")
    renderLabels()
    renderGrid(player.primaryGrid)
    println
    println("Your tracking grid")
    renderLabels()
    renderGrid(player.trackingGrid)
    println
  }
}