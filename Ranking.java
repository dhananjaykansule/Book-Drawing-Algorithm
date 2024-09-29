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
