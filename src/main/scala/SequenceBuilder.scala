import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

object SequenceBuilder {

  //построение упорядочения S с чертой внизу
  def buildLowerSeq(g: Graph): ListBuffer[ListBuffer[Int]] = {
    val result: ListBuffer[ListBuffer[Int]] = new ListBuffer[ListBuffer[Int]]()
    while (!g.isEmpty) {
      val tempSources: ListBuffer[Int] = g.getSources()
      if (tempSources.isEmpty) return null
      result += tempSources
      g.deleteVerticies(tempSources)
    }
    result
  }

  //построение упорядочения S с чертой вверху
  def buildUpperSeq(g: Graph): ListBuffer[ListBuffer[Int]] = {
    val result: ListBuffer[ListBuffer[Int]] = new ListBuffer[ListBuffer[Int]]()
    while (!g.isEmpty) {
      val tempSinks: ListBuffer[Int] = g.getSinks()
      if (tempSinks.isEmpty) return null
      result += tempSinks
      g.deleteVerticies(tempSinks)
    }
    result.reverse
    //result
  }

  //нахождение вершин на критических путях
  def buildCriticalSeq(lowerSeq: ListBuffer[ListBuffer[Int]],
                       upperSeq: ListBuffer[ListBuffer[Int]]): ListBuffer[ListBuffer[Int]] = {
    if (lowerSeq == null || upperSeq == null) return null
    lowerSeq.zip(upperSeq).map(elem => elem._1.intersect(elem._2))

    /*   val result: ListBuffer[ListBuffer[Int]] = new ListBuffer[ListBuffer[Int]]()
       for (i <- lowerSeq.indices) {
         val tempLayer: ListBuffer[Int] = new ListBuffer[Int]()
         var lPoiner: Int = 0
         var uPointer: Int = 0
         while (lPoiner < lowerSeq.get(i).size && uPointer < upperSeq.get(i).size)
           if (lowerSeq.get(i).get(lPoiner) > upperSeq.get(i).get(uPointer)) uPointer += 1
           else if (lowerSeq.get(i).get(lPoiner) < upperSeq.get(i).get(uPointer)) lPoiner += 1
           else {
             tempLayer += lowerSeq.get(i).get(lPoiner)
             lPoiner += 1
             uPointer += 1
           }
         result.add(tempLayer)
       }
       result*/
  }
}