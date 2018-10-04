package battleship

object main extends App {
  val renderer = new Renderer()
  renderer.renderTitle()

  val mode = askForMode()
  var level = -1
  if (mode == 1 || mode == 2) {
    level = askForAILevel()
  }
  val rounds = askForRounds()

  new Game(rounds, mode, level)

  def askForMode(): Int = {
    println("Choose mode : ")
    println("0 : Human vs Human")
    println("1 : Human vs AI")
    println("2 : AI vs AI")
    println("any : Quit")
    val mode = readLine("> ")
    try {
      mode.toInt
    }
    catch {
      case e: Exception =>
        System.exit(0)
        0
    }
  }

  def askForRounds(): Int = {
    println("Enter number of rounds (from 1 to infinity)")
    val rounds = readLine("> ")
    try {
      if (0 <= rounds.toInt) {
        rounds.toInt
      }
      else {
        askForRounds()
      }
    }
    catch {
      case e: Exception =>
        println("Invalid number of rounds, please re-enter a valid one")
        askForRounds()
    }
  }

  def askForAILevel(): Int = {
    println("Choose AI Level (from 0 to 2) :")
    val level = readLine("> ")
    try {
      if (0 <= level.toInt && level.toInt < Config.maxAiLevel) {
        level.toInt
      }
      else {
        askForAILevel()
      }
    }
    catch {
      case e: Exception =>
        println("Invalid or unsupported AI level please re-enter a valid one")
        askForAILevel()
    }
  }
}