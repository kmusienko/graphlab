import scala.collection.mutable.{HashSet, ListBuffer}

abstract class Graph {

  protected var nVerticies: Int = _

  protected var deletedVerticies: HashSet[Int] = _

  def getSources(): ListBuffer[Int] = {
    val result: ListBuffer[Int] = ListBuffer[Int]()
    for (j <- 0 until nVerticies if !deletedVerticies.contains(j) && !hasInArrows(j)) result += j
    result
  }

  def getSinks(): ListBuffer[Int] = {
    val result: ListBuffer[Int] = new ListBuffer[Int]()
    for (i <- 0 until nVerticies if !deletedVerticies.contains(i) && !hasOutArrows(i)) result += i
    result
  }

  def isEmpty(): Boolean = nVerticies == deletedVerticies.size

  def deleteVerticies(verticies: ListBuffer[Int]): Unit = {
    for (vertex <- verticies) {
      deletedVerticies.add(vertex)
      deleteVertex(vertex)
    }
  }

  def print(): Unit

  protected def hasInArrows(vertex: Int): Boolean

  protected def hasOutArrows(vertex: Int): Boolean

  protected def deleteVertex(vertex: Int): Unit

}