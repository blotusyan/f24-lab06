package edu.cmu.cs.cs214.rec02;

import java.util.Arrays;

public class ArrayIntQueue implements IntQueue {

    private int[] elementData; // Array holding this queue's data
    private int head; // Index of the next dequeue-able value
    private int size; // Current size of the queue
    private static final int INITIAL_SIZE = 10; // Initial size for new instances of ArrayIntQueue

    public ArrayIntQueue() {
        elementData = new int[INITIAL_SIZE];
        head = 0;
        size = 0;
    }

    /** {@inheritDoc} */
    public void clear() {
        Arrays.fill(elementData, 0); // Clear array elements
        size = 0;
        head = 0; // Reset head to 0
    }

    /** {@inheritDoc} */
    public Integer dequeue() {
        if (isEmpty()) {
            return null; // Return null if queue is empty
        }
        Integer value = elementData[head]; // Get value at the head
        head = (head + 1) % elementData.length; // Move head forward
        size--; // Decrement size
        return value;
    }

    /** {@inheritDoc} */
    public boolean enqueue(Integer value) {
        ensureCapacity(); // Ensure there is capacity to add new element
        int tail = (head + size) % elementData.length; // Calculate tail position
        elementData[tail] = value; // Add value to the tail
        size++; // Increment size
        return true; // Return true for successful enqueue
    }

    /** {@inheritDoc} */
    public boolean isEmpty() {
        return size == 0; // Check if size is zero
    }

    /** {@inheritDoc} */
    public Integer peek() {
        return isEmpty() ? null : elementData[head]; // Return null if empty, else return head element
    }

    /** {@inheritDoc} */
    public int size() {
        return size; // Return current size
    }

    private void ensureCapacity() {
        if (size == elementData.length) { // Check if array is full
            int oldCapacity = elementData.length;
            int newCapacity = 2 * oldCapacity + 1; // Double capacity
            int[] newData = new int[newCapacity];
            for (int i = 0; i < size; i++) { // Copy elements
                newData[i] = elementData[(head + i) % oldCapacity]; // Adjust index for circular array
            }
            elementData = newData; // Replace old array with new one
            head = 0; // Reset head
        }
    }
}
