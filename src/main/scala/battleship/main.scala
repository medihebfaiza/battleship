package battleship

object main extends App {
  Renderer.renderTitle()

  val mode = Renderer.askForMode()
  if (mode < 2) {
    var level = -1
    if (mode == 1) {
      level = Renderer.askForAILevel()
    }
    val rounds = Renderer.askForRounds()
    Game(rounds, mode, level).start()
  }
  else {
    Recorder.startRecording()
    Recorder.recordAIScore(Game(100, mode, 0, ui = false).start())
    Recorder.recordAIScore(Game(100, mode, 1, ui = false).start())
    Recorder.recordAIScore(Game(100, mode, 2, ui = false).start())
    Recorder.stopRecording()
    println(Console.GREEN + "Proof saved to '" + Config.proofPathname + "' with success" + Console.WHITE)
  }

}