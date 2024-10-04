package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {
        mQueue = new ArrayIntQueue(); // Use ArrayIntQueue for testing
        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNonEmptyQueue() {
        mQueue.enqueue(1);
        assertEquals(Integer.valueOf(1), mQueue.peek());
    }

    @Test
public void testEnqueue() {
    for (int i = 0; i < testList.size(); i++) {
        Integer value = testList.get(i);
        mQueue.enqueue(value);
        
        // Peek should always return the first enqueued element
        assertEquals(testList.get(0), mQueue.peek());
        
        // Size should increment correctly
        assertEquals(i + 1, mQueue.size());
    }
}


    @Test
    public void testDequeue() {
        for (Integer value : testList) {
            mQueue.enqueue(value);
        }
        for (Integer value : testList) {
            assertEquals(value, mQueue.dequeue());
        }
        assertNull(mQueue.dequeue()); // Should return null when empty
    }

    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(result, mQueue.dequeue());
            }
            assertTrue(mQueue.isEmpty()); // Ensure the queue is empty after dequeuing all elements
        }
    }

    @Test
    public void testClear() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
    }

    @Test
    public void testSize() {
        assertEquals(0, mQueue.size());
        mQueue.enqueue(1);
        assertEquals(1, mQueue.size());
        mQueue.enqueue(2);
        assertEquals(2, mQueue.size());
        mQueue.dequeue();
        assertEquals(1, mQueue.size());
        mQueue.clear();
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testCapacityExpansion() {
        for (int i = 0; i < 11; i++) {
            mQueue.enqueue(i);
        }
        assertEquals("Size should be 11 after enqueuing 11 elements", 11, mQueue.size());
        assertEquals("Peek should return the first enqueued element", Integer.valueOf(0), mQueue.peek());

        for (int i = 0; i < 11; i++) {
            assertEquals("Dequeued element should match enqueued order", Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue("Queue should be empty after dequeuing all elements", mQueue.isEmpty());
    }
}
