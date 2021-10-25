import java.lang.reflect.Array;
import java.util.*;

import static java.text.MessageFormat.format;


/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 *
 * @param <K> Key
 * @param <V> Value
 * @author CS Staff
 * @version 2021-08-23
 */
public class SkipList<K extends Comparable<? super K>, V>
        implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element of the top level
    private int size; // number of entries in the Skip List

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     *
     * @return a random level number
     */
    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     *
     * @param key key to be searched for
     * @return ArrayList list of rectangle having the key
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        SkipNode x = head;

        // iterate the SkipList until the Node with key is found
        // or till the end of list
        for (int i = head.level; i >= 0; --i) {
            while (x.forward[i] != null
                    && x.forward[i].pair.getKey().compareTo(key) < 0) {
                x = x.forward[i];
            }
        }
        x = x.forward[0];
        ArrayList<KVPair<K, V>> result = new ArrayList<>();

        // if node with matching key is found then add the pair to result array
        while (x != null && x.pair.getKey().compareTo(key) == 0) {
            result.add(x.pair);
            x = x.forward[0];
        }
        return result;
    }


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     *
     * @param it the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        int newLevel = randomLevel();
        // if new level is greater than adjust head Node with new level
        if (newLevel > head.level) {
            adjustHead(newLevel);
        }
        SkipNode[] update = new SkipList.SkipNode[newLevel + 1];
        SkipNode x = head;
        // populate update array by iterating over the list
        for (int i = newLevel; i >= 0; i--) {
            while (x.forward[i] != null
                    && x.forward[i].pair.compareTo(it) < 0) {
                x = x.forward[i];
            }
            update[i] = x;
        }

        // create new node and set its forward pointers
        // and set update array's forward pointers to inserted node
        x = new SkipNode(it, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            x.forward[i] = update[i].forward[i];
            update[i].forward[i] = x;
        }
        // increment size
        size++;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     *
     * @param newLevel the number of levels to be added to head
     */
    @SuppressWarnings("unchecked")
    private void adjustHead(int newLevel) {
        SkipNode temp = head;
        int level = head.level;

        // create new head node and copy all forward pointer
        // from the old head to new head node
        head = new SkipNode(null, newLevel);
        for (int i = 0; i <= level; ++i) {
            head.forward[i] = temp.forward[i];
        }
    }


    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     *
     * @param key the KVPair to be removed
     * @return returns the removed pair if the pair was valid and null if not
     */

    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        SkipNode x = head;
        SkipNode[] update = new SkipList.SkipNode[head.level + 1];

        // populate update array by iterating over the list
        for (int i = update.length - 1; i >= 0; i--) {
            while (x.forward[i] != null
                    && x.forward[i].pair.getKey().compareTo(key) < 0) {
                x = x.forward[i];
            }
            update[i] = x;
        }
        x = x.forward[0];

        // if the node with matching key is found then
        // change update array's forward pointers to the next node
        if (x != null && x.pair.getKey().compareTo(key) == 0) {
            for (int i = 0; i <= x.level; i++) {
                update[i].forward[i] = x.forward[i];
            }
            // decrement size
            size--;
            return x.pair;
        }
        return null;
    }


    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
        SkipNode node = head;
        System.out.println(Constants.DUMP_HEADER_LIST);
        while (node != null) {
            System.out.println(format(Constants.DUMP_DETAIL_LIST,
                    node.level + 1, node.element() != null
                            ? node.element().getValue() : null));
            node = node.forward[0];
        }
        System.out.println("The SkipList's " + format(
                Constants.DUMP_SIZE_LIST, size));
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     *
     * @author CS Staff
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // what is this
        private SkipNode[] forward;
        // the number of levels
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         *
         * @param tempPair the KVPair to be inserted
         * @param level    the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class,
                    level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         *
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }
    }


    private class SkipListIterator implements Iterator<KVPair<K, V>> {

        private SkipNode current;

        public SkipListIterator() {
            current = head;
        }


        @Override
        public boolean hasNext() {
            // check if the forward pointer is not null
            return current.forward[0] != null;
        }


        @Override
        public KVPair<K, V> next() {
            // move current to next pointer and return current
            current = current.forward[0];
            return current.pair;
        }

    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new SkipListIterator();
    }

}
