import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Dijkstras {
	
	HashMap<Wine, Double> vertexToDistEstimateMap = new HashMap<>();
	HashMap<Wine,Wine> vertexToParentMap = new HashMap<>();
	
	void relax(Wine u, Wine v, Optional<Double> weight, BinaryMinHeapImpl<Wine, Double> priQueue) {
		if(vertexToDistEstimateMap.get(v) > vertexToDistEstimateMap.get(u) + weight.get()) {
			vertexToDistEstimateMap.put(v, vertexToDistEstimateMap.get(u) + weight.get());
			vertexToParentMap.put(v, u);	
			priQueue.decreaseKey(v, vertexToDistEstimateMap.get(v));
		}
	}
	
    /**
     * {@inheritDoc} 
     */
    
    public List<Wine> getShortestPath(DoubleWeightedDirectedGraphImpl<Wine> G, Wine src, Wine tgt) {
        if (G == null || src == null || tgt == null || 
        		!G.vertexSet().contains(src) || !G.vertexSet().contains(tgt)) {
        		throw new IllegalArgumentException();
        } 
        if (src.equals(tgt)) {
        		return new LinkedList<Wine>();
        }
         
        LinkedList<Wine> ret = new LinkedList<Wine>();
        
    		//Initialize - single source
        for (Wine vertex: G.vertexSet()) {
        		vertexToDistEstimateMap.put(vertex, Double.MAX_VALUE);
        		vertexToParentMap.put(vertex, null);
        }        
		vertexToDistEstimateMap.put(src, 0.0);
		Set<Wine> discovered = new HashSet<Wine>();
		BinaryMinHeapImpl<Wine, Double> priQueue = new BinaryMinHeapImpl<>();
		for (Wine vertex: G.vertexSet()) {
			priQueue.add(vertex, vertexToDistEstimateMap.get(vertex));
		} 
		while (!priQueue.isEmpty()) {
			Wine vertex = priQueue.extractMin();
			discovered.add(vertex);
			for (Wine neighbour : G.outNeighbors(vertex)) {
				relax(vertex, neighbour, G.getWeight(vertex, neighbour), priQueue);
			}
		}
		Wine currentVertex = tgt; 
		while (!currentVertex.equals(src)) {
			if (vertexToParentMap.get(currentVertex) == null) {
	    			return new LinkedList<Wine>();
			}
			ret.addFirst(currentVertex);
			currentVertex = vertexToParentMap.get(currentVertex);
		}
		ret.addFirst(src);
		return ret; 
    }
}
