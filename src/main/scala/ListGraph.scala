import scala.collection.mutable.{HashSet, ListBuffer}

class ListGraph(n: Int, arrList: ListBuffer[(Int, Int)]) extends Graph {

  var arrowList: ListBuffer[(Int, Int)] = arrList.clone()

  nVerticies = n

  deletedVerticies = new HashSet[Int]()

  protected override def deleteVertex(vertex: Int): Unit = {
    var j: Int = arrowList.size - 1
    while (j >= 0) {
      if (arrowList(j)._1 == vertex || arrowList(j)._2 == vertex)
        arrowList -= arrowList(j)
      j -= 1
    }
  }

  protected override def hasInArrows(vertex: Int): Boolean = arrowList.exists(_._2 == vertex)

  protected override def hasOutArrows(vertex: Int): Boolean = arrowList.exists(_._1 == vertex)

  override def print(): Unit = {
    println(nVerticies)
    println(arrowList)
  }

}