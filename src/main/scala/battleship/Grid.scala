package battleship
import battleship._

// _ means default default uninitialized value
case class Grid(cells: List[List[Cell]]) {

    // TODO add ship without modifying current grid cells. hint use options
    def addFleet(fleet : List[Ship]): Grid = {
        println("add fleet called")
        if (fleet != Nil) {
          addShipCells(fleet.head.cells).addFleet(fleet.tail)
        }
        else {
          this
        }
    }

    def addShipCells(shipCells: List[Cell]): Grid = {

      if (shipCells != Nil) {
        // This is just a hell
        copy(cells = cells.patch(
          shipCells.head.x,
          Seq(cells(shipCells.head.x).patch(
            shipCells.head.y,
            Seq(shipCells.head),
            1)),
          1)).addShipCells(shipCells.tail)

      }
      else {
        this
      }
    }

}

object Grid {

  private val size = Config.gridSize

  def apply(cells : List[List[Cell]] = Nil) : Grid = {
    if (cells == Nil) {
      new Grid(createCells().get) // In this case we are a hundred percent sure that createCells will return a Some
    }
    else {
      new Grid(cells) // In this case we are a hundred percent sure that createCells will return a Some
    }
  }

  def createRow(rowNumber: Int, index:Int = 0): Option[List[Cell]] = {
    if (index < size) {
      val head = Cell(rowNumber, index)
      val tail = createRow(rowNumber, index + 1)
      if (head.isDefined && tail.isDefined) {
        Some(head.get::tail.get)
      }
      else {
        println("Error creating row") //TEST
        if (head.isDefined){
          println("Tail is the problem")
        }
        None
      }
    }
    else {
      Some(Nil)
    }
  }

  def createCells(index:Int = 0): Option[List[List[Cell]]] = {
    if (index < size) {
      val head = createRow(index)
      val tail = createCells(index + 1)
      if (head.isDefined && tail.isDefined) {
        Some(head.get::tail.get)
      }
      else {
        println("Error creating cells") //TEST
        None
      }
    }
    else {
      Some(Nil)
    }
  }
}