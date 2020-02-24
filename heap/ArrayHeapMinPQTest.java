package heap;

import org.junit.Test;

import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */
    @Test
    public void heapMainTest() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        assertEquals(heap.size(), 0);
        heap.add("test101Add", 101);
        heap.add("test1.01Add", 1.01);
        heap.add("testSamePriority", 1);
        for (int i = 0; i < 100; i++) {
            heap.add(i + " Number object", i);
        }
        assertEquals("0 Number object", heap.getSmallest());
        assertEquals("0 Number object", heap.removeSmallest());
        assertEquals("testSamePriority", heap.getSmallest());
        assertEquals(heap.size(), 102);
        heap.changePriority("99 Number object", 0);
        assertEquals("99 Number object", heap.getSmallest());
    }

    @Test
    public void increasePriority() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        for (int i = 100; i >= 0; i--) {
            heap.add(i, i);
        }
        assertEquals(0, heap.getSmallest());
        heap.changePriority(0, 101);
        assertEquals(1, heap.getSmallest());
    }

    @Test
    public void decreasePriority() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        for (int i = 100; i >= 0; i--) {
            heap.add(i, i);
        }
        assertEquals(0, heap.getSmallest());
        heap.changePriority(100, -1);
        assertEquals(100, heap.getSmallest());
    }

    @Test
    public void addAndRemoveAll() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        for (int i = 100; i >= 0; i--) {
            heap.add(i, i);
            assertEquals(heap.getSmallest(), i);
        }
        for (int i = 0; i < heap.size(); i++) {
            assertEquals(heap.removeSmallest(), i);
        }
    }

    @Test
    public void increasingOrderTest() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        for (int i = 0; i < 10; i++) {
            heap.add(i, i);
        }
        assertEquals(0, heap.removeSmallest());
        assertEquals(1, heap.removeSmallest());
    }

    @Test
    public void decreasingOrderTest() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        for (int i = 9; i >= 0; i--) {
            heap.add(i, i);
        }
        assertEquals(0, heap.removeSmallest());
        assertEquals(1, heap.removeSmallest());
    }

    @Test(expected = NoSuchElementException.class)
    public void nullTests() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        heap.removeSmallest();
        heap.changePriority("testing", 0);
        heap.getSmallest();
        heap.contains("shouldn't work");
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgTest() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        heap.add("0 Number object", 0);
        heap.add("0 Number object", 0);
    }
}
