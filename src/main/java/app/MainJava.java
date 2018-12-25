package app;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainJava {

    public static void main(String[] args) {

        int[][] testMatrix = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 0, 0}
        };

        int[][] testMatrix2 = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 0, 0}
        };

        MatrixGraph g1 = new MatrixGraph(testMatrix);
        MatrixGraph g2 = new MatrixGraph(testMatrix2);
        List<List<Integer>> l = BuildSequencing.BuildLowerSeq(g1);
        List<List<Integer>> r = BuildSequencing.BuildUpperSeq(g2);
        List<List<Integer>> criticalSeq = BuildSequencing.CriticalSeq(l, r);

        System.out.println("====matrix graph: ====");
        System.out.println("S1: " + l);
        System.out.println("S2: " + r);
        System.out.println("Intersection: " + criticalSeq);
        //=====================================

        List<Tuple<Integer, Integer>> list1 = new ArrayList<>();
        list1.add(new Tuple<>(2, 1));
        list1.add(new Tuple<>(2, 0));
        list1.add(new Tuple<>(4, 2));
        list1.add(new Tuple<>(4, 3));
        list1.add(new Tuple<>(5, 3));

        List<Tuple<Integer, Integer>> list2 = new ArrayList<>();
        list2.add(new Tuple<>(2, 1));
        list2.add(new Tuple<>(2, 0));
        list2.add(new Tuple<>(4, 2));
        list2.add(new Tuple<>(4, 3));
        list2.add(new Tuple<>(5, 3));

        ListGraph g3 = new ListGraph(6, list1);
        ListGraph g4 = new ListGraph(6, list2);
        List<List<Integer>> l2 = BuildSequencing.BuildLowerSeq(g3);
        List<List<Integer>> r2 = BuildSequencing.BuildUpperSeq(g4);
        List<List<Integer>> criticalSeq2 = BuildSequencing.CriticalSeq(l2, r2);

        System.out.println("====list graph: ====");
        System.out.println("S1: " + l2);
        System.out.println("S2: " + r2);
        System.out.println("Intersection: " + criticalSeq2);

        //=====================================

        List<Integer> listA = new ArrayList<>();
        listA.add(0);
        listA.add(0);
        listA.add(2);
        listA.add(0);
        listA.add(2);
        listA.add(1);

        List<Integer> listB = new ArrayList<>();
        listB.add(0);
        listB.add(1);
        listB.add(2);
        listB.add(3);
        listB.add(3);

        TwoListGraph g5 = new TwoListGraph(listA, listB);
        TwoListGraph g6 = new TwoListGraph(listA, listB);

        List<List<Integer>> l3 = BuildSequencing.BuildLowerSeq(g5);
        List<List<Integer>> r3 = BuildSequencing.BuildUpperSeq(g6);
        List<List<Integer>> criticalSeq3 = BuildSequencing.CriticalSeq(l3, r3);

        System.out.println("====two list graph: ====");
        System.out.println("S1: " + l3);
        System.out.println("S2: " + r3);
        System.out.println("Intersection: " + criticalSeq3);
    }
}

abstract class iGraph {
    protected int nVerticies;
    protected HashSet<Integer> deletedVerticies;

    public List<Integer> GetSources() {
        List<Integer> result = new ArrayList<>();
        for (int j = 0; j < nVerticies; j++)
            if (!deletedVerticies.contains(j) && !HasInArrows(j))
                result.add(j);
        return result;
    }

    public List<Integer> GetSinks() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nVerticies; i++) {
            if (!deletedVerticies.contains(i) && !HasOutArrows(i)) result.add(i);
        }

        return result;
    }

    public boolean isEmpty() {
        return nVerticies == deletedVerticies.size();
    }

    public void DeleteVerticies(List<Integer> verticies) {
        for (int vertex : verticies) {
            deletedVerticies.add(vertex);
            DeleteVertex(vertex);
        }
    }

    public abstract void Print();

    protected abstract boolean HasInArrows(int vertex);

    protected abstract boolean HasOutArrows(int vertex);

    protected abstract void DeleteVertex(int vertex);
}

class MatrixGraph extends iGraph {
    int[][] matrix;

    public MatrixGraph(int[][] m) {
        matrix = m.clone();
        nVerticies = matrix.length;
        System.out.println(nVerticies);
        deletedVerticies = new HashSet<Integer>();
    }

 /*   public app.MatrixGraph(String path) {
        StreamReader sr = new StreamReader(path);
        nVerticies = Convert.ToInt32(sr.ReadLine());
        deletedVerticies = new HashSet<int>();
        matrix = new int[nVerticies, nVerticies];
        for (int i = 0; i < nVerticies; i++) {
            int[] row = sr.ReadLine().Split(' ').Select(Item1 = > Convert.ToInt32(Item1)).ToArray();
            for (int j = 0; j < nVerticies; j++)
                matrix[i, j]=row[j];
        }
        sr.Close();
    }*/

    @Override
    protected void DeleteVertex(int vertex) {
        for (int i = 0; i < nVerticies; i++)
            matrix[vertex][i] = matrix[i][vertex] = 0;
    }

    @Override
    public void Print() {
        for (int i = 0; i < nVerticies; i++) {
            for (int j = 0; j < nVerticies; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    @Override
    protected boolean HasInArrows(int vertex) {
        for (int i = 0; i < nVerticies; i++)
            if (matrix[i][vertex] == 1) return true;
        return false;
    }

    @Override
    protected boolean HasOutArrows(int vertex) {
        for (int j = 0; j < nVerticies; j++)
            if (matrix[vertex][j] == 1) return true;
        return false;
    }
}

//граф, представленный списком дуг
class ListGraph extends iGraph {
    List<Tuple<Integer, Integer>> arrowList;

    public ListGraph(int n, List<Tuple<Integer, Integer>> arrList) {
        nVerticies = n;
        arrowList = new ArrayList<Tuple<Integer, Integer>>(arrList);
        deletedVerticies = new HashSet<Integer>();
    }

  /*  public app.ListGraph(String path) {
        StreamReader sr = new StreamReader(path);
        nVerticies = Convert.ToInt32(sr.ReadLine());
        if (nVerticies < 0) nVerticies = 0;
        deletedVerticies = new HashSet<int>();
        arrowList = new List<app.Tuple<int, int>>();
        while (!sr.EndOfStream) {
            int[] row = sr.ReadLine().Split(' ').Select(Item1 = > Convert.ToInt32(Item1)).ToArray();
            if (row[0] >= 0 && row[0] < nVerticies && row[1] >= 0 && row[1] < nVerticies)
                arrowList.Add(new app.Tuple<int, int>(row[0], row[1]));
        }
        sr.Close();
    }*/

    public ListGraph(ListGraph g) {
        nVerticies = g.nVerticies;
        arrowList = new ArrayList<Tuple<Integer, Integer>>(g.arrowList);
        deletedVerticies = new HashSet<Integer>();
    }

    @Override
    protected void DeleteVertex(int vertex) {
        for (int j = arrowList.size() - 1; j >= 0; j--)
            if (arrowList.get(j).Item1 == vertex || arrowList.get(j).Item2 == vertex)
                arrowList.remove(arrowList.get(j));
    }

    @Override
    protected boolean HasInArrows(int vertex) {
        for (Tuple<Integer, Integer> arrow : arrowList)
            if (arrow.Item2 == vertex)
                return true;
        return false;
    }

    @Override
    protected boolean HasOutArrows(int vertex) {
        for (Tuple<Integer, Integer> arrow : arrowList)
            if (arrow.Item1 == vertex)
                return true;
        return false;
    }

    @Override
    public void Print() {
        System.out.println(nVerticies);
        System.out.println(arrowList);
    }
}

//граф, представленный двумя списками
class TwoListGraph extends iGraph {
    List<Integer> countList;
    List<Integer> adjList;

    public TwoListGraph(List<Integer> cList, List<Integer> aList) {
        nVerticies = cList.size();
        countList = new ArrayList<Integer>(cList);
        adjList = new ArrayList<Integer>(aList);
        deletedVerticies = new HashSet<Integer>();
    }

  /*  public app.TwoListGraph(String path) {
        StreamReader sr = new StreamReader(path);
        nVerticies = Convert.ToInt32(sr.ReadLine());
        deletedVerticies = new HashSet<int>();
        countList = sr.ReadLine().Split(' ').Select(x = > Convert.ToInt32(x)).ToList();
        adjList = sr.ReadLine().Split(' ').Select(x = > Convert.ToInt32(x)).ToList();
        sr.Close();
    }*/

    @Override
    protected void DeleteVertex(int vertex) {
        int sum = 0;
        for (int i = 0; i < vertex; i++)
            sum += countList.get(i);

        adjList = Stream.concat(adjList.stream().limit(sum), adjList.stream().skip(sum + countList.get(vertex))).collect(Collectors.toList());

     /*   List<Integer> myList1 = adjList.stream().limit(sum).collect(Collectors.toList());
        myList1.addAll(
                adjList.stream().skip(sum + countList.get(vertex)).collect(Collectors.toList())
        );

        adjList = myList1;*/

        // adjList = adjList.Take(sum).Concat(adjList.Skip(sum + countList[vertex])).ToList();
        countList.set(vertex, 0);
        sum = 0;
        for (int i = 0; i < nVerticies; i++) {
            int tempCount = countList.get(i);
            for (int j = 0; j < tempCount; j++)
                if (adjList.get(sum + j) == vertex) {
                    countList.set(i, countList.get(i) - 1);
                    break;
                }
            sum += tempCount;
        }
        adjList.removeAll(adjList.stream().filter(x -> x == vertex).collect(Collectors.toList()));
    }

    @Override
    protected boolean HasInArrows(int vertex) {
        for (int v : adjList)
            if (v == vertex)
                return true;
        return false;
    }

    @Override
    protected boolean HasOutArrows(int vertex) {
        return countList.get(vertex) != 0;
    }

    @Override
    public void Print() {
        System.out.println(countList);
        System.out.println(adjList);
    }
}

//класс для построения упорядочений
class BuildSequencing {
    //построение упорядочения S с чертой внизу
    public static List<List<Integer>> BuildLowerSeq(iGraph g) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        while (!g.isEmpty()) {
            List<Integer> tempSources = g.GetSources();
            if (tempSources.size() == 0)
                return null;
            result.add(tempSources);
            g.DeleteVerticies(tempSources);
        }
        return result;
    }

    //построение упорядочения S с чертой вверху
    public static List<List<Integer>> BuildUpperSeq(iGraph g) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        while (!g.isEmpty()) {
            List<Integer> tempSinks = g.GetSinks();
            if (tempSinks.size() == 0)
                return null;
            result.add(tempSinks);
            g.DeleteVerticies(tempSinks);
        }
        Collections.reverse(result);
        return result;
    }

    //нахождение вершин на критических путях
    public static List<List<Integer>> CriticalSeq(List<List<Integer>> lowerSeq, List<List<Integer>> upperSeq) {
        if (lowerSeq == null || upperSeq == null)
            return null;
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < lowerSeq.size(); i++) {
            List<Integer> tempLayer = new ArrayList<>();
            int lPoiner = 0, uPointer = 0;
            while (lPoiner < lowerSeq.get(i).size() && uPointer < upperSeq.get(i).size()) {
                if (lowerSeq.get(i).get(lPoiner) > upperSeq.get(i).get(uPointer))
                    uPointer++;
                else if (lowerSeq.get(i).get(lPoiner) < upperSeq.get(i).get(uPointer))
                    lPoiner++;
                else {
                    tempLayer.add(lowerSeq.get(i).get(lPoiner));
                    lPoiner++;
                    uPointer++;
                }
            }
            result.add(tempLayer);
        }
        return result;
    }

   /* //вывод упорядочений
    public static void PrintSeq(List<List<Integer>> seq) {
        if (seq == null)
            System.out.println("В графе есть цикл!");
        else
            System.out.println(String.format("<{0}>", String.join(",\n",
                    seq.stream().map(Item1 -> String.format("<{0}>", String.join(", ", Item1.stream().map(el -> el.toString()).collect(Collectors.toList())))).collect(Collectors.toList()))));
    }*/
}

class Tuple<X, Y> {
    public X Item1;
    public Y Item2;

    public Tuple(X Item1, Y Item2) {
        this.Item1 = Item1;
        this.Item2 = Item2;
    }

    @Override
    public String toString() {
        return "(" + Item1 + "; " + Item2 + ")";
    }
}