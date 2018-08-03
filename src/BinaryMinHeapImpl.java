import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class BinaryMinHeapImpl<V, Key extends Comparable<Key>>{
	ArrayList<Entry<V, Key>> heapArray;
	HashMap<V,Integer> valueToIndexMap = null; 
	
	public BinaryMinHeapImpl() {
		heapArray = new ArrayList<>();
		valueToIndexMap = new HashMap<>(); 
		heapArray.add(null);
	}
	
	private int getParentIndex(int i){
		return (int) Math.floor(i/2);
	}
	
	private int getLeftIndex(int i){
		return 2*i;
	}
	
	private int getRightIndex(int i){
		return 2*i + 1;
	}
	
	private boolean hasRightChild(int i){
		return 2*i + 1 <= size();
	}
	
	private boolean hasParent(int i){
		return i > 1;
	}
	
	private boolean isLeaf(int i) {
		return (i > size()/2 && i <= size());
	}
	
	private void swap (int i, int j) {
		V iValue = heapArray.get(i).getValue();
		V jValue = heapArray.get(j).getValue();
		valueToIndexMap.put(iValue, j);
		valueToIndexMap.put(jValue, i);		
		Collections.swap(heapArray, i, j);
	}
	
	//Maintains max-heap property for element at root
	void bubbleDown(int index) {
		int currIndex = index;
		while(!isLeaf(currIndex)) {
			//Setting the currIndex to the smaller of the two children 
			int smallerChild = getLeftIndex(currIndex);
			if (hasRightChild(currIndex) ) {
				 Entry<V,Key> rightChild = heapArray.get(getRightIndex(currIndex));
				 Entry<V,Key> leftChild = heapArray.get(getLeftIndex(currIndex));
				 if (leftChild.getKey().compareTo(rightChild.getKey()) > 0) {
					 smallerChild = getRightIndex(currIndex);
				 }
			}
			//Compare Parent and smallerChild. Swap if necessary 
			Entry<V,Key> parentEntry = heapArray.get(currIndex);
			Entry<V,Key> smallerChildEntry = heapArray.get(smallerChild);
			if(parentEntry.getKey().compareTo(smallerChildEntry.getKey()) > 0) {
				swap(currIndex, smallerChild);
			}
			else {
				break;
			}
			currIndex = smallerChild; 
		}
	}
	
	//Maintains min-heap property for element 
	void bubbleUp(int index) {
		int currIndex = index;
		
		while (hasParent(currIndex)) {
			Entry<V,Key> parentEntry = heapArray.get(getParentIndex(currIndex));
			Entry<V,Key> currentEntry = heapArray.get(currIndex);
			if (parentEntry.getKey().compareTo(currentEntry.getKey()) > 0) {
				swap(currIndex, getParentIndex(currIndex));
			}
			currIndex = getParentIndex(currIndex);
		}
	}
	
    /**
     * {@inheritDoc}
     */
    
    public int size() {
        return heapArray.size() - 1;
    }

    
    public boolean isEmpty() {
        return (size() == 0);
    }

    /**
     * {@inheritDoc}
     */
    
    public boolean containsValue(V value) {
    		return values().contains(value);
    }

    /**
     * {@inheritDoc}
     */
    
    public void add(V value, Key key) {
    		if (key == null || containsValue(value)) {
    			throw new IllegalArgumentException();
    		}
    		Entry<V, Key> newEntry = new Entry<V, Key>(value, key);
        heapArray.add(newEntry);
        valueToIndexMap.put(value, size());
    		bubbleUp(size());
    }

    /**
     * {@inheritDoc}
     */
    
    public void decreaseKey(V value, Key newKey) {
    		if (valueToIndexMap.get(value) == null) {
    			throw new NoSuchElementException();
    		}
    		if (newKey == null) {
    			throw new IllegalArgumentException();
    		}
		Integer index = valueToIndexMap.get(value);
		Entry<V,Key> entry = heapArray.get(index);
		if (newKey.compareTo(entry.getKey()) > 0 ) {
			throw new IllegalArgumentException();
		}
		entry.setKey(newKey);
		bubbleUp(index);
    }

    /**
     * {@inheritDoc}
     */
    
    public V peek() {
    		if (isEmpty()){
    			throw new NoSuchElementException();
    		} 
    		return heapArray.get(1).getValue();
    }

    /**
     * {@inheritDoc}
     */
    
    public V extractMin() {
    		V ret = peek();
    		swap(1, size());
    		heapArray.remove(size());
    		if (!isEmpty()) {
    			bubbleDown(1);
    		}
    		return ret;
    }

    /**
     * {@inheritDoc}
     */
    
    public Set<V> values() {
        return valueToIndexMap.keySet();
    }
    /**
     * Helper entry class for maintaining value-key pairs.
     * The underlying indexed list for your heap will contain
     * these entries.
     */
    class Entry<A, B> {

        private A value;
        private B key;

        public Entry(A value, B key) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return  the value stored in the entry
         */
        public A getValue() {
            return this.value;
        }

        /**
         * @return  the key stored in the entry
         */
        public B getKey() {
            return this.key;
        }

        /**
         * Changes the key of the entry.
         *
         * @param key  the new key
         * @return  the old key
         */
        public B setKey(B key) {
            B oldKey = this.key;
            this.key = key;
            return oldKey;
        }
    }
}
