import scala.collection.mutable.{HashSet, ListBuffer}
import scala.collection.JavaConversions._

class TwoListGraph(cList: ListBuffer[Int], aList: ListBuffer[Int]) extends Graph {

  var countList: ListBuffer[Int] = cList.clone()

  var adjList: ListBuffer[Int] = aList.clone()

  nVerticies = cList.size

  deletedVerticies = new HashSet[Int]()

  protected override def deleteVertex(vertex: Int): Unit = {
    var sum: Int = 0
    for (i <- 0 until vertex) sum += countList.get(i)
    adjList = adjList.take(sum) ++= adjList.drop(sum + countList(vertex))
    countList.set(vertex, 0)
    sum = 0
    for (i <- 0 until nVerticies) {
      val tempCount: Int = countList.get(i)
      for (j <- 0 until tempCount if adjList.get(sum + j) == vertex) {
        countList.set(i, countList.get(i) - 1)
        //break
      }
      sum += tempCount
    }
    adjList = adjList.filter(_ != vertex)
  }

  protected override def hasInArrows(vertex: Int): Boolean =
    adjList.contains(vertex)

  protected override def hasOutArrows(vertex: Int): Boolean =
    countList.get(vertex) != 0

  override def print(): Unit = {
    println(countList)
    println(adjList)
  }

}