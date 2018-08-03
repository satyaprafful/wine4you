import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


public class DoubleWeightedDirectedGraphImpl<V> {
	private ArrayList<HashSet<V>> adjList = null; 
	private ArrayList<HashSet<V>> inNeighbourAdjList = null; 
	private HashMap<V,Integer> nodeToIntNodeMap = null;
	private HashMap<V, Set<Edge>> nodeToEdgeMap = null; 
	 
	class Edge {
	    public final V u;
	    public final V v;
	    public Double weight;
	    
	    public Edge(V u, V v) {
	    		this(u, v, null);
	    }
	    
	    public Edge(V u, V v, Double weight) {
	        this.u = u; 
	        this.v = v;
	        this.weight = weight;
	    }

	    
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        Edge edge = (Edge)o;
	        return Objects.equals(u, edge.u) &&
	                Objects.equals(v, edge.v);
	    }

	    
	    public int hashCode() {
	        return Objects.hash(u, v, weight);
	    }

	    public Double getWeight() {
	    		return weight; 
	    }
	    	    
	    public void setWeight(Double weight) {
	    		this.weight = weight; 
	    }
	    
	}
	
    public DoubleWeightedDirectedGraphImpl(int n, HashMap<V,Integer> nodeToIntMapping) {
        if (n < 0) {
        		throw new IllegalArgumentException();
        }
        adjList = new ArrayList<>();
        inNeighbourAdjList = new ArrayList<>();
        for (int i = 0; i < n + 1 ; i++) {
        		adjList.add(new HashSet<>()); 
        		inNeighbourAdjList.add(new HashSet<>());
        }
        nodeToIntNodeMap = nodeToIntMapping;
		nodeToEdgeMap = new HashMap<>();
		for (V vertex: vertexSet()) {
			nodeToEdgeMap.put(vertex, new HashSet<Edge>());
		}
    }
    
    public void addEdge(V u, V v, Double weight) {
    		if (u == null || v == null || weight == null) {
			throw new IllegalArgumentException();
		}
		if (adjList.get(nodeToIntNodeMap.get(u)).contains(v)) {
			return;
		}
    		adjList.get(nodeToIntNodeMap.get(u)).add(v);
    		inNeighbourAdjList.get(nodeToIntNodeMap.get(v)).add(u);
    		Edge newEdge = new Edge(u,v,weight);
    		nodeToEdgeMap.get(u).add(newEdge);    
    	}
    
    
	
	public Optional<Double> getWeight(V source, V target) {
		if (source == null || target == null) {
			throw new IllegalArgumentException();
		}
		if (!(adjList.get(nodeToIntNodeMap.get(source)).contains(target))) {
			return Optional.empty();
		}
		else {
			Edge retEdge = null; 
			for (Edge e: nodeToEdgeMap.get(source)){
				if (e.v.equals(target)) {
					retEdge = e; 
				}
			} 
			Double ret = retEdge.getWeight();
			return Optional.of(ret);
		}
	}
 
	
	public Set<V> neighbors(V arg0) {
		if (arg0 == null) {
			throw new IllegalArgumentException();
		}
		HashSet<V> ret = new HashSet<>();
		ret.addAll(outNeighbors(arg0));
		ret.addAll(inNeighbors(arg0));
		return Collections.unmodifiableSet(ret);
	}

	
	public Set<V> vertexSet() {
		return Collections.unmodifiableSet(nodeToIntNodeMap.keySet());
	}
 
	
	public Set<V> inNeighbors(V vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException();
		}
		return Collections.unmodifiableSet(inNeighbourAdjList.get(nodeToIntNodeMap.get(vertex)));
	}
  
	
	public Set<V> outNeighbors(V vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException();
		}
		return Collections.unmodifiableSet(adjList.get(nodeToIntNodeMap.get(vertex)));
	}
}
