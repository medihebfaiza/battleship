package battleship

import java.io.File
import java.io.PrintWriter

/** Recorder component takes charge of data persistence */
object Recorder {
  var writer: PrintWriter = new PrintWriter(new File(Config.proofPathname))
  val levelLabels = List("Beginner", "Medium", "Hard")

  /** Opens a file with given path and initiates it with csv header
    *
    */
  def startRecording(): Unit = writer.write("AI Name; score; AI Name2; score2 \n")

  /** Writes the two players scores on a the
    *
    * @param gameState
    */
  def recordAIScore(gameState: GameState): Unit = {
    writer.write("AI " + levelLabels(gameState.players._1.asInstanceOf[Ai].level) + ";" + gameState.players._1.score + ";" +
      "AI " + levelLabels(gameState.players._2.asInstanceOf[Ai].level) + ";" + gameState.players._2.score + "\n")
  }

  /** Stops recording and closes file
    *
    */
  def stopRecording(): Unit =  writer.close()
}
