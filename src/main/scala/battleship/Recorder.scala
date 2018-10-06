package battleship

import java.io.File
import java.io.PrintWriter

class Recorder {
  var writer: PrintWriter = null
  val levelLabels = List("Beginner", "Medium", "Hard")

  def startRecording(): Unit = {
    writer = new PrintWriter(new File("ai_proof.csv"))
    writer.write("AI Name; score; AI Name2; score2 \n")
  }

  def recordAIScore(gameState: GameState): Unit = {
    writer.write("AI " + levelLabels(gameState.players._1.asInstanceOf[Ai].level) + ";" + gameState.players._1.score + ";" +
      "AI " + levelLabels(gameState.players._2.asInstanceOf[Ai].level) + ";" + gameState.players._2.score + "\n")
  }

  def stopRecording(): Unit = {
    writer.close()
  }
}
