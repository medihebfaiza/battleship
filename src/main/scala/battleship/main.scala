package battleship

object main extends App {
  val renderer = new Renderer()
  renderer.renderTitle()

  val mode = askForMode()
  if (mode < 2) {
    var level = -1
    if (mode == 1) {
      level = askForAILevel()
    }
    val rounds = askForRounds()
    Game(rounds, mode, level).start()
  }
  else {
    Game(100, mode, 0).start()
    Game(100, mode, 1).start()
    Game(100, mode, 2).start()
  }

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

  /**
    *
    * @param mode
    * @return
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
}