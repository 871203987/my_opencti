package io.opencti.common.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/utils/test_queue.js
 * 队列测试
 */
class QueueTest {

    private Queue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new Queue<>();
    }

    @Test
    void testEnqueueDequeue() {
        assertTrue(queue.isEmpty());
        
        queue.enqueue("first");
        queue.enqueue("second");
        queue.enqueue("third");
        
        assertEquals(3, queue.size());
        
        assertEquals("first", queue.dequeue());
        assertEquals("second", queue.dequeue());
        assertEquals("third", queue.dequeue());
        
        assertTrue(queue.isEmpty());
    }

    @Test
    void testPeek() {
        queue.enqueue("item");
        
        assertEquals("item", queue.peek());
        assertEquals(1, queue.size());
        
        assertEquals("item", queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void testPeekEmpty() {
        assertNull(queue.peek());
    }

    @Test
    void testDequeueEmpty() {
        assertNull(queue.dequeue());
    }

    @Test
    void testClear() {
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        
        assertEquals(3, queue.size());
        
        queue.clear();
        
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void testToList() {
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        
        var list = queue.toList();
        
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    void testSize() {
        assertEquals(0, queue.size());
        
        queue.enqueue("item");
        assertEquals(1, queue.size());
        
        queue.enqueue("item2");
        assertEquals(2, queue.size());
        
        queue.dequeue();
        assertEquals(1, queue.size());
    }
}
