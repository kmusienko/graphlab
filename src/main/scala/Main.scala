import scala.collection.mutable._

object Main extends App {

  testMatrixGraph()
  testListGraph()
  testTwoListGraph()

  def testMatrixGraph(): Unit = {
    val testMatrix: Array[Array[Int]] = Array(
      Array(0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0),
      Array(1, 1, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0),
      Array(0, 0, 1, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 0)
    )

    val g1 = new MatrixGraph(testMatrix)
    val g2 = new MatrixGraph(testMatrix.map(_.clone))

    val lowerSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildLowerSeq(g1)
    val upperSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildUpperSeq(g2)

    val criticalSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildCriticalSeq(lowerSeq, upperSeq)
    println("====matrix graph: ====")
    println("S1: " + lowerSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
    println("S2: " + upperSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
    println("Intersection: " + criticalSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
  }

  def testListGraph(): Unit = {
    val list = ListBuffer((3, 2), (3, 1), (5, 3), (5, 4), (6, 4))

    val correctList = list.map(elem => (elem._1 -1, elem._2 - 1))

    val g1 = new ListGraph(6, correctList)
    val g2 = new ListGraph(6, correctList.clone())

    val loweSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildLowerSeq(g1)
    val upperSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildUpperSeq(g2)

    val criticalSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildCriticalSeq(loweSeq, upperSeq)

    println()
    println("====list graph: ====")
    println("S1: " + loweSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
    println("S2: " + upperSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
    println("Intersection: " + criticalSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
  }

  def testTwoListGraph(): Unit = {
    val listA = ListBuffer[Int](0, 0, 2, 0, 2, 1)
    val listB = ListBuffer[Int](0, 1, 2, 3, 3)

    val g1 = new TwoListGraph(listA, listB)
    val g2 = new TwoListGraph(listA, listB)

    val loweSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildLowerSeq(g1)
    val upperSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildUpperSeq(g2)

    val criticalSeq: ListBuffer[ListBuffer[Int]] = SequenceBuilder.buildCriticalSeq(loweSeq, upperSeq)

    println()
    println("====two list graph: ====")
    println("S1: " + loweSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
    println("S2: " + upperSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
    println("Intersection: " + criticalSeq.map(_.map(_ + 1).mkString("[", ",", "]")).mkString("[", ", ", "]"))
  }
}