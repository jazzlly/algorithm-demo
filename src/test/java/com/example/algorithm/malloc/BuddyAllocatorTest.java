package com.example.algorithm.malloc;

import com.example.algorithm.Debug;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class BuddyAllocatorTest extends TestCase {

    public void testPowerOf2() throws Exception {
        BuddyAllocator buddyAllocator = new BuddyAllocator(32);

        assertTrue(buddyAllocator.IS_POWER_OF_2(1));
        assertTrue(buddyAllocator.IS_POWER_OF_2(2));
        assertTrue(buddyAllocator.IS_POWER_OF_2(4));
        assertTrue(buddyAllocator.IS_POWER_OF_2(8));
        assertTrue(buddyAllocator.IS_POWER_OF_2(2048));

         assertFalse(buddyAllocator.IS_POWER_OF_2(0));
        assertFalse(buddyAllocator.IS_POWER_OF_2(3));
        assertFalse(buddyAllocator.IS_POWER_OF_2(6));
        assertFalse(buddyAllocator.IS_POWER_OF_2(48));
    }

    public void testFixSize() throws Exception {
        BuddyAllocator buddyAllocator = new BuddyAllocator(32);

         assertEquals(buddyAllocator.fixsize(1), 1);
        assertEquals(buddyAllocator.fixsize(2), 2);
        assertEquals(buddyAllocator.fixsize(4), 4);
        assertEquals(buddyAllocator.fixsize(8), 8);
        assertEquals(buddyAllocator.fixsize(1024), 1024);

        assertEquals(buddyAllocator.fixsize(0), 1);
        assertEquals(buddyAllocator.fixsize(3), 4);
        assertEquals(buddyAllocator.fixsize(6), 8);
        assertEquals(buddyAllocator.fixsize(15), 16);
        assertEquals(buddyAllocator.fixsize(27), 32);
        assertEquals(buddyAllocator.fixsize(514), 1024);

    }

    public void testConstructor() throws Exception {
        BuddyAllocator allocator = new BuddyAllocator(1);

        for (BuddyBlock block : allocator.getLongest()) {
            System.out.println(block.toString());
        }
    }


    public void testParent() throws Exception {
        BuddyAllocator buddyAllocator = new BuddyAllocator(32);

        assertEquals(buddyAllocator.PARENT(0), 1);
        assertEquals(buddyAllocator.PARENT(1), 0);
        assertEquals(buddyAllocator.PARENT(2), 0);
        assertEquals(buddyAllocator.PARENT(3), 1);
    }

    public void testAlloc() throws Exception {
        BuddyAllocator allocator = new BuddyAllocator(16);
        System.out.println(allocator.alloc(1));
        System.out.println(allocator.alloc(1));
        System.out.println(allocator.alloc(4));
        System.out.println(allocator.alloc(8));
    }

    public void testFree() throws Exception {
        BuddyAllocator allocator = new BuddyAllocator(64);
        ArrayList<Integer> offsetArray = new ArrayList<Integer>();

        offsetArray.add(allocator.alloc(1));
        offsetArray.add(allocator.alloc(2));
        offsetArray.add(allocator.alloc(16));
        offsetArray.add(allocator.alloc(8));
        offsetArray.add(allocator.alloc(4));
        offsetArray.add(allocator.alloc(2));

        for (Integer integer : offsetArray) {
            allocator.free(integer);
        }

        offsetArray = new ArrayList<Integer>();
        offsetArray.add(allocator.alloc(1));
        offsetArray.add(allocator.alloc(2));
        offsetArray.add(allocator.alloc(16));
        offsetArray.add(allocator.alloc(8));
        offsetArray.add(allocator.alloc(4));
        offsetArray.add(allocator.alloc(2));

        for (Integer integer : offsetArray) {
            allocator.free(integer);
        }

        assertEquals(allocator, new BuddyAllocator(64));
    }

    public void testRandom() throws Exception {
        final int totalSize = 4096;

        BuddyAllocator allocator = new BuddyAllocator(totalSize);

        for (int i = 0; i < 1000; i ++) {
            // Random allocation test
            int offset = -1;
            ArrayList<Integer> offsetArray = new ArrayList<Integer>();
            Random random = new Random((new Date()).getTime());
            while (true) {
                int memSize = random.nextInt(totalSize / 8 - 1) + 1;
                offset = allocator.alloc(memSize);

                Debug.log("mem size is: " + memSize);
                Debug.log("offset is: " + offset);
                if (offset < 0) {
                    Debug.log("allocate time: " + offsetArray.size());
                    break;
                }
                offsetArray.add(offset);
            }

            // free all memory
            for (Integer o : offsetArray) {
                allocator.free(o);
            }
            assertEquals(allocator, new BuddyAllocator(totalSize));
        }
    }
}