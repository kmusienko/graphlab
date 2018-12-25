import scala.collection.mutable.HashSet

class MatrixGraph(m: Array[Array[Int]]) extends Graph {

  var matrix: Array[Array[Int]] = m.clone()

  nVerticies = matrix.length

  deletedVerticies = new HashSet[Int]()

  protected override def deleteVertex(vertex: Int): Unit = {
    for (i <- 0 until nVerticies) {
      matrix(vertex)(i) = matrix(i)(vertex)
      matrix(i)(vertex) = 0
    }
  }

  override def print(): Unit = {
    for (i <- 0 until nVerticies) {
      for (j <- 0 until nVerticies) System.out.print(matrix(i)(j) + " ")
      println()
    }
  }

  protected override def hasInArrows(vertex: Int): Boolean = (0 until nVerticies).exists(matrix(_)(vertex) == 1)

  protected override def hasOutArrows(vertex: Int): Boolean = (0 until nVerticies).exists(matrix(vertex)(_) == 1)
}