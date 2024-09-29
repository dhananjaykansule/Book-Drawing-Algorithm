package BookDrawing;
import java.util.*;

class Edge  implements Comparable<Edge> {
    long u, v;
    long diff;
    long rank;
    long crossingCount;

    public Edge(long u, long v) {
        this.u = u;
        this.v = v;
        this.rank = Long.MAX_VALUE;  // Initialize rank with a placeholder value
        this.crossingCount = 0;
    }

    public int compareTo(Edge other) {
        // Sort in descending order of difference (j - i)
        return Long.compare(other.crossingCount, this.crossingCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return u == edge.u && v == edge.v;
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + u +
                ", end=" + v +
                '}';
    }
}

public class BookAlgo {

    static void Sort(List<Edge> edges) {
        Collections.sort(edges, (e1, e2) -> {
            if (e1.crossingCount != e2.crossingCount) {
                return Long.compare(e2.crossingCount, e1.crossingCount);
            }
            return Long.compare(e2.rank, e1.rank);

//            return Long.compare(e2.crossingCount* e2.rank, e1.crossingCount* e1.rank);
        });
    }

    static boolean crossing(Edge e1, Edge e2) {

        if (e1.u < e2.u && e2.u < e1.v && e1.v < e2.v) {
            return true;
        }
        if (e2.u < e1.u && e1.u < e2.v && e2.v < e1.v) {
            return true;
        }
        return false;
    }

    static void fixUpper(List<Edge> upperEdges, Edge removedEdge) {
        for (Edge e : upperEdges) {
            if (e.u < removedEdge.u && removedEdge.u < e.v && e.v < removedEdge.v) {
                e.crossingCount--;
            }
            if (removedEdge.u < e.u && e.u < removedEdge.v && removedEdge.v < e.v) {
                e.crossingCount--;
            }
        }
    }

    static boolean isSafeOnPage(List<Edge> page , Edge removedEdge) {
        for(Edge e : page) {
            if (e.u < removedEdge.u && removedEdge.u < e.v && e.v < removedEdge.v) {
                return false;
            }
            if (removedEdge.u < e.u && e.u < removedEdge.v && removedEdge.v < e.v) {
                return false;
            }
        }
        return true;
    }

   
    public static long n;
    public  static HashSet<Edge> edges = new HashSet<Edge>();
    public  static ArrayList<Edge> rankEdges = new ArrayList<Edge>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Graph creation
        System.out.println("Enter the number of nodes in the graph: ");
        n = sc.nextLong();
        System.out.println("Enter the number of edges in the graph: ");
        int m=sc.nextInt();
        System.out.println("Enter the  edges in the graph: ");
        for(int i=0; i<m; i++) {
            edges.add(new Edge(sc.nextInt(), sc.nextInt()));
        }

//        kn

//          

          Ranking ranking = new Ranking();
          ranking.RankFun();

          for (Edge e : rankEdges) {
              for (Edge e2 : rankEdges) {
                  if (crossing(e, e2))
                      e.crossingCount++;
              }
          }


          Sort(rankEdges);
//
        System.out.println("****************************************************************************");
        for(Edge e: rankEdges){
            System.out.println((e.u+1) + " " + (e.v+1) + "------ " + e.crossingCount + "------" + e.rank);
        }

          List<List<Edge>> pagelist = new ArrayList<>();

          List<Edge> page1 = new ArrayList<>();
          page1.addAll(rankEdges);
          pagelist.add(page1);

          long page1Crossing = 0;
          for (Edge e : page1) {
              page1Crossing += e.crossingCount;
          }

          while (page1Crossing > 0) {
                int removeInd=0;
//                while (page1.get(removeInd).rank==0){
//                    removeInd++;
//
//                }
              Edge removedEdge = page1.remove(removeInd);
              page1Crossing -= 2 * removedEdge.crossingCount;
              fixUpper(page1, removedEdge);

              int flag = 0;
              for (long i = 1; i < pagelist.size(); i++) {
                  if (isSafeOnPage(pagelist.get((int) i), removedEdge)) {
                      pagelist.get((int) i).add(removedEdge);
                      flag = 1;
                      break;
                  } else {
                      if (allHasLessRank(pagelist.get((int) i), removedEdge)) {
                          List<Edge> tempList = new ArrayList<>(pagelist.get((int) i));
                          for (Edge e : tempList) {
                              if (crossing(e, removedEdge)) {
                                  fixedEdge(pagelist, e, (int) i + 1);
                                  pagelist.get((int) i).remove(e);
                              }
                          }
                          pagelist.get((int) i).add(removedEdge);
                          flag = 1;
                          break;
                      }
                  }
              }
              if (flag == 0) {
                  List<Edge> page = new ArrayList<>();
                  page.add(removedEdge);
                  pagelist.add(page);
              }
              Sort(page1);
            System.out.println("****************************************************************************");
            for(Edge e: page1){
                System.out.println((e.u+1) + " " + (e.v+1) + "------ " + e.crossingCount + "------" + e.rank);
            }

//            System.out.println(pagelist.size());
          }

          System.out.println("N:-" + (n) + " -------" + " Page number: " + pagelist.size() );

        for(long i=0 ; i< pagelist.size();i++) {
            System.out.println("Edges on page " + (i+1) + ": ");
            for(long j=0 ; j< pagelist.get((int)i).size();j++) {
                System.out.println((pagelist.get((int)i).get((int)j).u+1) + " " + (pagelist.get((int)i).get((int)j).v+1));
            }
        }

    }
}
