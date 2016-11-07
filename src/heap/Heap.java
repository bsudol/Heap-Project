package heap;
/* Time spent on a6:  02 hours and 00 minutes.

 * Name(s): Basia Sudol and Drew Mera
 * Netid(s): bas334, dnm54
 * What I thought about this assignment:
 *
 */

import java.lang.reflect.Array;

import java.util.*;

/** Please keep up with the pinned Piazza note A6 FAQs.
 * Study slides 9..49 of Lecture 17 on Priority Queues and Heaps
 * before starting on this assignment. */

/** An instance is a min-heap of distinct values of type V with
 *  priorities of type double. Since it's a min-heap, the value
 *  with the smallest priority is at the root of the heap. */
public class Heap<V> {

    /** The contents of c represent a complete binary tree. c[0] is the root;
     * c[2i+1] and c[2i+2] are the left and right children of c[i].
     * If i is not 0, c[(i-1)/2] (using integer division) is the parent of c[i].
     *
     * Class Invariant:
     *   1. The tree is complete. It consists of c[0..size-1].
     *
     *   2. For i in 0..size-1, c[i] contains the value and its priority.
     *      For i in size..c.length-1, c[i] = null.
     *
     *   3. The values in c[0..size-1] are all different
     *
     *   4. For i in 1..size-1, (c[i]'s priority) >= (c[i]'s parent's priority)
     *
     *   map and the tree are in sync:
     *     5. The keys of map are the values in c[0..size-1]
     *     6. c[map[v]] has value v
     *
     * Note that invariant 5 implies that this.size equals map.size
     */
    protected Entry[] c;
    protected int size;
    protected HashMap<V, Integer> map;

    /** Constructor: an empty heap with capacity 10. */
    public Heap() {
        c= createEntryArray(10);
        map= new HashMap<V, Integer>();
    }

    /** An Entry contains a value and a priority. */
    class Entry {
        V value;
        double priority;

        /** An Entry with value v and priority p*/
        Entry(V v, double p) {
            value= v;
            priority= p;
        }
    }

    /** Add v with priority p to the heap.
     *  Throw an illegalArgumentException if v is already in the heap.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap. */
    public void add(V v, double p) throws IllegalArgumentException {
        // TODO #1: Write this whole method. Note that bubbleUp is not implemented,
        // so calling it will have no effect (yet). The first tests of add, using
        // test00Add, ensure that this method maintains fields c and map properly,
        // without worrying about bubbling up. Look at the spec of test00Add.

    	Entry adder = new Entry(v, p);

    	if (size == 0) {
    		c[0] = adder;
    		size +=1;
    		map.put(v, 0);
    	}
    	else if (size > 0){

            for (int k = 0; k < size; k++) {
         		if (c[k].value == v) {
           			throw new IllegalArgumentException();
            	}
            }
    		this.ensureSpace();
        	c[size] = adder;
        	map.put(v, size);
        	this.bubbleUp(size);
        	size +=1;
    	}
    }

    /** If size = length of c, double the length of array c. */
    public void ensureSpace() {
        //TODO #2. Any method that changes the size of array c needs to call
        // this method first. If you write this method correctly AND method
        // add calls this method appropriately, testing procedure
        // test10ensureSpace will not find errors.
    	if(size == c.length) {
    		Entry[] d = createEntryArray((size *2));
    		for (int k = 0; k < size; k++) {
    			d[k] = c[k];
    		}
    		c = d;
    	}
    }

    /** Return the number of values in this heap.
     *  This operation takes constant time. */
    public int size() {
        return size;
    }

    /** Swap c[h] and c[k].
     *  Precondition: 0 <= h < c.size, 0 <= k < c.size. */
    void swap(int h, int k) {
        //TODO 3: When bubbling values up and down (later on), two values,
        // say c[h] and c[k], will have to be swapped. At the same time,
        // field map has to be maintained. In order to always get this right,
        // use method swap for this. Method swap is tested by testing
        // procedure test13Add_Swap --it will find no errors if you write
        // this method properly.
    		
    		//swap the values in the map first
	    	int i = map.get(c[h].value);
	    	int j = map.get(c[k].value);
	    	map.put(c[k].value, i);
	    	map.put(c[h].value, j);
	    	
	    	//now swap them in c
	    	Entry swapper = c[h];
	    	c[h] = c[k];
	    	c[k] = swapper;
    }

    /** Bubble c[k] up in heap to its right place.
     *  Precondition: Priority of every c[i] >= its parent's priority
     *                except perhaps for c[k] */
    private void bubbleUp(int k) {
        // TODO #4 As you know, this method should be called within add in order
        // to bubble a value up to its proper place, based on its priority.
        // Do not use recursion. Use iteration.
        // If this method is written properly, testing procedure
        // test15Add_BubbleUp() will not find any errors.
    	
    	int i = (k - 1) / 2;
    	if (c[k].priority <= c[i].priority) {
	    	while ((c[i].priority > c[k].priority) && k > 0 ) {
	    		//System.out.println("" + c[i].priority +" " +c[k].priority);
	    		swap(i,k);
	    		//System.out.println("" + c[i].priority +" " +c[k].priority);
	    		k = i;
	    		i = (i - 1) / 2;
	    	}
    	}
    }

    /** Return the value of this heap with lowest priority. Do not
     *  change the heap. This operation takes constant time.
     *  Throw a NoSuchElementException if the heap is empty. */
    public V peek() {
        // TODO 5: Do peek. This is an easy one.
        //         test20Peek() will not find errors if this is correct.

    	if (size == 0) throw new NoSuchElementException();

    	//we're going to assume the heap can have priorities out of order
    	//based on the test20Peek
    	
    	double priority = c[0].priority;
    	V v = c[0].value;

        for (int k = 0; k < size; k++) {
     		if (c[k].priority < priority) {
       			v = c[k].value;
        	}
        }
        
        return v;

    }

    /** Remove and return the element of this heap with lowest priority.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap.
     *  Throw a NoSuchElementException if the heap is empty. */
    public V poll() {
        // TODO 6: Do poll and bubbleDown (#7) together. When they are
        //         written correctly, testing procedure test30Poll_BubbleDown_NoDups
        //         will not find errors.
        //
        //         Note also testing procedure test40testDuplicatePriorities
        //         This method tests to make sure that when bubbling up or down,
        //         two values with the same priority are not swapped.
    	return null;
    }

    /** Bubble c[k] down in heap until it finds the right place.
     *  If there is a choice to bubble down to both the left and
     *  right children (because their priorities are equal), choose
     *  the right child.
     *  Precondition: Each c[i]'s priority <= its childrens' priorities
     *                except perhaps for c[k] */
    private void bubbleDown(int k) {
        // TODO 7: Do poll (#6) and bubbleDown together. We also suggest
        //         implementing and using smallerChildOf, though you don't
        //         have to. Do not use recursion. Use iteration.
    	
    }

    /** Return the index of the smaller child of c[n]
     *  If the two children have the same priority, choose the right one.
     *  Precondition: left child exists: 2n+1 < size of heap */
     int smallerChildOf(int n) {
    	 //c[2i+1] and c[2i+2] are the left and right children of c[i].
    	 int left = (2*n)+1;
    	 int right = (2*n)+2;
    	 if(right >= size) return left; //if there is only a left child
		 if (c[left].priority == c[right].priority) return right;
		 
	     return (c[left].priority < c[right].priority ? left : right);
    }

    /** Change the priority of value v to p.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap.
     *  Throw an IllegalArgumentException if v is not in the heap. */
    public void changePriority(V v, double p) {
        // TODO  8: When this method is correctly implemented, testing procedure
        //          test50ChangePriority() won't find errors.
    	
    	int a = map.get(v);
    	if (a == -1) throw new IllegalArgumentException("v is not in heap");
    	
     		if(c[a].priority < p) { //increasing priority = bubbleup
	       		c[a].priority = p;
	       		this.bubbleUp(a);
     		}
     		else{ //decreasing priority = bubbledown
     			c[a].priority = p;
           		this.bubbleDown(a);
     		}
    }

    /** Create and return an Entry[] of size n.
     *  This is necessary because Generic and arrays don't interoperate nicely.
     *  A student in CS2110 would not be expected to know about the need
     *  for this method and how to write it. */
     Entry[] createEntryArray(int n) {
        return (Entry[]) Array.newInstance(Entry.class, n);
    }
}