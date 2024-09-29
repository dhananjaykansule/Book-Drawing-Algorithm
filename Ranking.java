package BookDrawing;
import java.util.*;

public class Ranking {

    static void Sort(List<Edge> edges) {
        Collections.sort(edges, (e1, e2) -> {
            if(e1.diff != e2.diff) {
                return Long.compare(e2.diff, e1.diff);
            }
            return Long.compare(e1.u , e2.u);
        });
    }

    public void RankFun() {

        BookAlgo book = new BookAlgo();
        long n = book.n;
        HashSet<Edge> edges = book.edges;
        ArrayList<Edge> rankEdges = book.rankEdges;

        List<List<Edge>> rankList = new ArrayList<>();

        for(long i=0 ; i< Math.ceil(n/2)-2 ; i++){
            List<Edge> page = new ArrayList<>();
            if(i==0){
                for(long j=0 ; j<n-1 ; j++){

                    Edge edge = new Edge(j, j+1);
                    edge.rank=0;
                    if(edges.contains(edge)){
                        rankEdges.add(edge);
                    }

                }

                Edge edge = new Edge(0, n-1);
                edge.rank=0;
                if(edges.contains(edge)){
                    rankEdges.add(edge);
                }
            }

            if(edges.contains(new Edge(0, (n/2-1)-i)))
                page.add(new Edge(0, (n/2-1)-i));

            if(edges.contains(new Edge((n/2-1)-i, (n-1)-i)))
                page.add(new Edge((n/2-1)-i, (n-1)-i));

            for(long j=n/2-i ; j<n-i-2 ; j++){

                Edge edge = new Edge(j, (n-1)-i);
                if(edges.contains(edge))
                    page.add(edge);
            }
            for(long j=1 ; j<n/2-i-2 ; j++){

                Edge edge = new Edge(j, (n/2-1)-i);
                if(edges.contains(edge))
                    page.add(edge);
            }
            for(long j=n-i+1 ; j<=n ; j++){

                Edge edge = new Edge(n/2-i-1, j-1);
                if(edges.contains(edge))
                    page.add(edge);
            }
            if(!page.isEmpty())
                rankList.add(page);
        }

        List<Edge> page1 = new ArrayList<>();
        for(long j=(n+1)/2+1 ; j<n ; j++){

            Edge edge = new Edge(0, j-1);
            if(edges.contains(edge))
                page1.add(edge);
        }
        for(long j=2;j<(n+1)/2; j++){

            Edge edge = new Edge(j-1 , (n+1)/2+1-1);
            if(edges.contains(edge))
                page1.add(edge);
        }

        if(!page1.isEmpty())
            rankList.add(page1);

        List<Edge> page2 = new ArrayList<>();
        for(long j=(n+1)/2+2 ; j<n+1 ; j++){

            Edge edge = new Edge(2-1, j-1);
            if(edges.contains(edge))
                page2.add(edge);
        }
        for(long j=3;j<(n+1)/2+1; j++){

            Edge edge = new Edge(j-1 , (n+1)/2+2-1);
            if(edges.contains(edge))
                page2.add(edge);
        }

        if(!page2.isEmpty())
            rankList.add(page2);

        if(n%2==1){
            List<Edge> page3 = new ArrayList<>();
            for(long j=1 ; j<n/2 ; j++){

                Edge edge = new Edge(j-1 , n/2+1-1);
                if(edges.contains(edge))
                    page3.add(edge);
            }
            if(!page3.isEmpty())
                rankList.add(page3);
        }

//        Ranking to Edges
        Collections.sort(rankList, new Comparator<List<Edge>>() {
            @Override
            public int compare(List<Edge> list1, List<Edge> list2) {
                return Integer.compare(list2.size(), list1.size()); // Descending order
            }
        });

        for(long i=0 ; i<rankList.size(); i++){
            Sort(rankList.get((int)i));
        }

//        for(int j=0 ; j<rankList.get(0).size(); j++){
//            Edge edgeToRank = rankList.get(0).get(j);
//            edgeToRank.rank=0;
//            rankEdges.add(edgeToRank);
//        }
        long rank=1;
        for(int i=0 ; i<rankList.size(); i++){
            for(int j=0 ; j<rankList.get(i).size(); j++){
                Edge edgeToRank = rankList.get(i).get(j);
                edgeToRank.rank=rank;
                rankEdges.add(edgeToRank);
                rank++;
            }
        }
//
        int count =0;
        for(int i=0 ; i< rankList.size();i++) {
            System.out.println("Edges on page " + (i+1) + ": ");
            for(int j=0 ; j< rankList.get(i).size();j++) {
                System.out.println((rankList.get(i).get(j).u+1) + " " + (rankList.get(i).get(j).v+1) + " Rank: " + (rankList.get(i).get(j).rank));
                count++;
            }
        }
//
        System.out.println("Total edges: " + count);

    }
}
