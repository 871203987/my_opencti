package io.opencti.common.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 重写自: opencti-graphql/src/utils/queue.js
 * FIFO队列实现
 *
 * @param <T> 队列元素类型
 */
public class Queue<T> {

    private final LinkedList<T> queue;
    private int offset;

    public Queue() {
        this.queue = new LinkedList<>();
        this.offset = 0;
    }

    /**
     * 重写自: opencti-graphql/src/utils/queue.js - getLength
     * 获取队列长度
     *
     * @return 队列长度
     */
    public int size() {
        return queue.size() - offset;
    }

    /**
     * 重写自: opencti-graphql/src/utils/queue.js - isEmpty
     * 检查队列是否为空
     *
     * @return 如果队列为空则返回true
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * 重写自: opencti-graphql/src/utils/queue.js - enqueue
     * 入队操作
     *
     * @param item 要添加的元素
     */
    public void enqueue(T item) {
        queue.addLast(item);
    }

    /**
     * 重写自: opencti-graphql/src/utils/queue.js - dequeue
     * 出队操作
     *
     * @return 队首元素，如果队列为空则返回null
     */
    public T dequeue() {
        if (queue.isEmpty()) {
            return null;
        }
        T item = queue.get(offset);
        offset++;
        if (offset * 2 >= queue.size()) {
            for (int i = 0; i < offset; i++) {
                queue.removeFirst();
            }
            offset = 0;
        }
        return item;
    }

    /**
     * 重写自: opencti-graphql/src/utils/queue.js - peek
     * 查看队首元素（不移除）
     *
     * @return 队首元素，如果队列为空则返回null
     */
    public T peek() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.get(offset);
    }

    /**
     * 清空队列
     */
    public void clear() {
        queue.clear();
        offset = 0;
    }

    /**
     * 将队列转换为列表
     *
     * @return 包含所有元素的列表
     */
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (int i = offset; i < queue.size(); i++) {
            result.add(queue.get(i));
        }
        return result;
    }
}
