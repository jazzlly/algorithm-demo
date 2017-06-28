package com.example.algorithm.malloc;

import com.example.algorithm.Debug;

import java.util.Arrays;

/**
 * Migrated from https://github.com/wuwenbin/buddy2
 * http://coolshell.cn/articles/10427.html
 */
public class BuddyAllocator {

    // capacity of the allocator
    private final int capacity;

    private BuddyBlock longest[];

    public int  LEFT_LEAF(int index) {
        return ((index) * 2 + 1);
    }

    public int RIGHT_LEAF(int index) {
        return ((index) * 2 + 2);
    }

    public int PARENT(int index) {
        return ( ((index) + 1) / 2 - 1);
    }

    public boolean IS_POWER_OF_2(int x) {
        if (x == 0) {
            return false;
        }

        return ((x)&((x)-1)) == 0;
    }

    public int MAX(int a, int b) {
        return Math.max(a, b);
    }


    public int fixsize(int size) {
        if (IS_POWER_OF_2(size)) {
            return size;
        }

        size |= size >> 1;
        size |= size >> 2;
        size |= size >> 4;
        size |= size >> 8;
        size |= size >> 16;
        size |= size >> 32;
        size |= size >> 64;

        return size+1;
    }

    /**
     * Create a allocator with specific size
     * @param size
     */
    public BuddyAllocator(int size) {
        int node_size;

        if (size < 1 || !IS_POWER_OF_2(size)) {
            throw new IllegalArgumentException("Size should be greater than 0 and power of 2");
        }

        capacity = size;
        longest = new BuddyBlock[size * 2 - 1];
        node_size = size * 2;

        for (int i = 0; i < 2 * size - 1; ++i) {
            if (IS_POWER_OF_2(i+1)) {
                node_size /= 2;
            }
            longest[i] = new BuddyBlock(node_size);
        }
    }

    /**
     * Allocate memory
     * @param size of memory to allocate
     * @return offset of the allocated memory from the memory base
     */
    int alloc(int size) {
        int index = 0;
        int node_size;
        int offset = 0;

        if (size <= 0) {
            size = 1;
        } else if (!IS_POWER_OF_2(size)) {
            size = fixsize(size);
        }

        if (longest[0].getSize() < size)
            return -1;

        for(node_size = capacity; node_size != size; node_size /= 2 ) {
            Debug.logVerbose("node size: " + node_size + ", index: " + index);
            if (longest[LEFT_LEAF(index)].getSize() >= size)
                index = LEFT_LEAF(index);
            else
                index = RIGHT_LEAF(index);
        }

        Debug.logVerbose("allocate index: " + index);
        longest[index].setSize(0);
        offset = (index + 1) * node_size - capacity;

        while (index > 0) {
            index = PARENT(index);
            int newSize = MAX(
                    longest[LEFT_LEAF(index)].getSize(),
                    longest[RIGHT_LEAF(index)].getSize());
            longest[index].setSize(newSize);
            Debug.logVerbose("adjust parent: " + index + " with size: " + newSize);
        }

        return offset;
    }

    /**
     * Free the memory
     * @param offset of the memory to free
     */
    void free(int offset) {
        int node_size = 0;
        int index = 0;
        int left_longest, right_longest;

        if (offset < 0 || offset > capacity) {
            throw new IllegalArgumentException("offset wrong");
        }

        node_size = 1;
        index = offset + capacity - 1;

        for (; longest[index].getSize() > 0 ; index = PARENT(index)) {
            node_size *= 2;
            if (index == 0)
                return;
        }

        longest[index].setSize(node_size);

        while (index > 0) {
            index = PARENT(index);
            node_size *= 2;

            left_longest = longest[LEFT_LEAF(index)].getSize();
            right_longest = longest[RIGHT_LEAF(index)].getSize();

            if (left_longest + right_longest == node_size)
                longest[index].setSize(node_size);
            else
                longest[index].setSize(MAX(left_longest, right_longest));
        }
    }

    public BuddyBlock[] getLongest() {
        return longest;
    }

    @Override
    public String toString() {
        return "BuddyAllocator{" +
                "capacity=" + capacity +
                ", longest=" + Arrays.toString(longest) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuddyAllocator that = (BuddyAllocator) o;

        if (capacity != that.capacity) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(longest, that.longest);
    }

    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + (longest != null ? Arrays.hashCode(longest) : 0);
        return result;
    }
}

/**
 * Internal memory block
 */
class BuddyBlock {
    int size;

    public BuddyBlock(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuddyBlock that = (BuddyBlock) o;

        return size == that.size;

    }

    @Override
    public int hashCode() {
        return size;
    }

    @Override
    public String toString() {
        return "BuddyBlock{" +
                "size=" + size +
                '}';
    }
}
