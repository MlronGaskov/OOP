package ru.nsu.gaskov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.gaskov.HeapSort.heapsort;

import java.util.Comparator;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Tests. */
class HeapSortTest {

    @Test
    public void testIntArray() {
        int[] array = {5, 3, 9, 1, 4};
        heapsort(array);
        int[] expectedArray = {1, 3, 4, 5, 9};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testAlreadySortedIntArray() {
        int[] array = {1, 10, 12, 46, 77};
        heapsort(array);
        int[] expectedArray = {1, 10, 12, 46, 77};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testByteArray() {
        byte[] array = {5, 3, 9, 1, 4};
        heapsort(array);
        byte[] expectedArray = {1, 3, 4, 5, 9};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testShortArray() {
        short[] array = {5, 3, 9, 1, 4};
        heapsort(array);
        short[] expectedArray = {1, 3, 4, 5, 9};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testCharArray() {
        char[] array = {'e', 'c', 'i', 'a', 'd'};
        heapsort(array);
        char[] expectedArray = {'a', 'c', 'd', 'e', 'i'};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLongArray() {
        long[] array = {5L, 3L, 9L, 1L, 4L};
        heapsort(array);
        long[] expectedArray = {1L, 3L, 4L, 5L, 9L};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testFloatArray() {
        float[] array = {5.0f, 3.0f, 9.0f, 1.0f, 4.0f};
        heapsort(array);
        float[] expectedArray = {1.0f, 3.0f, 4.0f, 5.0f, 9.0f};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testDoubleArray() {
        double[] array = {5.0, 3.0, 9.0, 1.0, 4.0};
        heapsort(array);
        double[] expectedArray = {1.0, 3.0, 4.0, 5.0, 9.0};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testObjectArray() {
        String[] array = {"banana", "apple", "orange", "grape", "cherry"};
        heapsort(array);
        String[] expectedArray = {"apple", "banana", "cherry", "grape", "orange"};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testWithNullArray() {
        String[] array = {"banana", "apple", "orange", "grape", null};
        assertThrows(NullPointerException.class, () -> heapsort(array));
    }

    @Test
    public void testDifferentTypesArray() {
        Object[] array = {"banana", "apple", "orange", "grape", 4};
        assertThrows(ClassCastException.class, () -> heapsort(array));
    }

    @Test
    public void testNotComparableArray() {
        Object[] array = {new Object(), new Object()};
        assertThrows(ClassCastException.class, () -> heapsort(array));
    }

    @Test
    public void testNullArray() {
        Object[] array = null;
        assertThrows(NullPointerException.class, () -> heapsort(array));
    }

    @Test
    public void testNegativeIntArray() {
        int[] array = {-5, -3, -9, -1, -4};
        heapsort(array);
        int[] expectedArray = {-9, -5, -4, -3, -1};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyIntArray() {
        int[] array = {};
        heapsort(array);
        int[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalIntArray() {
        int[] array = {7, 7, 7, 7, 7};
        heapsort(array);
        int[] expectedArray = {7, 7, 7, 7, 7};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeIntArray() {
        int[] array = {5, 3, 9, 1, 4, 10, 2, 8, 6, 0, -1, -3, 12, 15, 14};
        heapsort(array);
        int[] expectedArray = {-3, -1, 0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 14, 15};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testNegativeByteArray() {
        byte[] array = {-5, -3, -9, -1, -4};
        heapsort(array);
        byte[] expectedArray = {-9, -5, -4, -3, -1};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyByteArray() {
        byte[] array = {};
        heapsort(array);
        byte[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalByteArray() {
        byte[] array = {7, 7, 7, 7, 7};
        heapsort(array);
        byte[] expectedArray = {7, 7, 7, 7, 7};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeByteArray() {
        byte[] array = {5, 3, 9, 1, 4, 10, 2, 8, 6, 0, -1, -3, 12, 15, 14};
        heapsort(array);
        byte[] expectedArray = {-3, -1, 0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 14, 15};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testNegativeShortArray() {
        short[] array = {-5, -3, -9, -1, -4};
        heapsort(array);
        short[] expectedArray = {-9, -5, -4, -3, -1};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyShortArray() {
        short[] array = {};
        heapsort(array);
        short[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalShortArray() {
        short[] array = {7, 7, 7, 7, 7};
        heapsort(array);
        short[] expectedArray = {7, 7, 7, 7, 7};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeShortArray() {
        short[] array = {5, 3, 9, 1, 4, 10, 2, 8, 6, 0, -1, -3, 12, 15, 14};
        heapsort(array);
        short[] expectedArray = {-3, -1, 0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 14, 15};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyCharArray() {
        char[] array = {};
        heapsort(array);
        char[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalCharArray() {
        char[] array = {'a', 'a', 'a', 'a', 'a'};
        heapsort(array);
        char[] expectedArray = {'a', 'a', 'a', 'a', 'a'};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeCharArray() {
        char[] array = {'e', 'c', 'i', 'a', 'd', 'g', 'f', 'h', 'b', 'j'};
        heapsort(array);
        char[] expectedArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testNegativeLongArray() {
        long[] array = {-5L, -3L, -9L, -1L, -4L};
        heapsort(array);
        long[] expectedArray = {-9L, -5L, -4L, -3L, -1L};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyLongArray() {
        long[] array = {};
        heapsort(array);
        long[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalLongArray() {
        long[] array = {7L, 7L, 7L, 7L, 7L};
        heapsort(array);
        long[] expectedArray = {7L, 7L, 7L, 7L, 7L};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeLongArray() {
        long[] array = {5L, 3L, 9L, 1L, 4L, 10L, 2L, 8L, 6L, 0L, -1L, -3L, 12L, 15L, 14L};
        heapsort(array);
        long[] expectedArray = {-3L, -1L, 0L, 1L, 2L, 3L, 4L, 5L, 6L, 8L, 9L, 10L, 12L, 14L, 15L};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testNegativeFloatArray() {
        float[] array = {-5.0f, -3.0f, -9.0f, -1.0f, -4.0f};
        heapsort(array);
        float[] expectedArray = {-9.0f, -5.0f, -4.0f, -3.0f, -1.0f};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyFloatArray() {
        float[] array = {};
        heapsort(array);
        float[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalFloatArray() {
        float[] array = {7.0f, 7.0f, 7.0f, 7.0f, 7.0f};
        heapsort(array);
        float[] expectedArray = {7.0f, 7.0f, 7.0f, 7.0f, 7.0f};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeFloatArray() {
        float[] array = {
            5.0f, 3.0f, 9.0f, 1.0f, 4.0f,
            10.0f, 2.0f, 8.0f, 6.0f, 0.0f,
            -1.0f, -3.0f, 12.0f, 15.0f, 14.0f
        };
        heapsort(array);
        float[] expectedArray = {
            -3.0f, -1.0f, 0.0f, 1.0f, 2.0f,
            3.0f, 4.0f, 5.0f, 6.0f, 8.0f,
            9.0f, 10.0f, 12.0f, 14.0f, 15.0f
        };
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testNegativeDoubleArray() {
        double[] array = {-5.0, -3.0, -9.0, -1.0, -4.0};
        heapsort(array);
        double[] expectedArray = {-9.0, -5.0, -4.0, -3.0, -1.0};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyDoubleArray() {
        double[] array = {};
        heapsort(array);
        double[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalDoubleArray() {
        double[] array = {7.0, 7.0, 7.0, 7.0, 7.0};
        heapsort(array);
        double[] expectedArray = {7.0, 7.0, 7.0, 7.0, 7.0};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeDoubleArray() {
        double[] array = {
            5.0, 3.0, 9.0, 1.0, 4.0,
            10.0, 2.0, 8.0, 6.0, 0.0,
            -1.0, -3.0, 12.0, 15.0, 14.0
        };
        heapsort(array);
        double[] expectedArray = {
            -3.0, -1.0, 0.0, 1.0, 2.0,
            3.0, 4.0, 5.0, 6.0, 8.0,
            9.0, 10.0, 12.0, 14.0, 15.0
        };
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyObjectArray() {
        String[] array = {};
        heapsort(array);
        String[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalObjectArray() {
        String[] array = {"apple", "apple", "apple", "apple", "apple"};
        heapsort(array);
        String[] expectedArray = {"apple", "apple", "apple", "apple", "apple"};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeObjectArray() {
        String[] array = {
            "banana", "apple", "orange", "grape", "cherry",
            "kiwi", "mango", "peach", "melon", "berry"
        };
        heapsort(array);
        String[] expectedArray = {
            "apple", "banana", "berry", "cherry", "grape",
            "kiwi", "mango", "melon", "orange", "peach"
        };
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIntArrayWithRange() {
        int[] array = {5, 3, 9, 1, 4};
        heapsort(array, 1, 5);
        int[] expectedArray = {5, 1, 3, 4, 9};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testByteArrayWithRange() {
        byte[] array = {5, 3, 9, 1, 4};
        heapsort(array, 2, 5);
        byte[] expectedArray = {5, 3, 1, 4, 9};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testShortArrayWithRange() {
        short[] array = {5, 3, 9, 1, 4};
        heapsort(array, 2, 5);
        short[] expectedArray = {5, 3, 1, 4, 9};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testCharArrayWithRange() {
        char[] array = {'e', 'c', 'i', 'a', 'd'};
        heapsort(array, 1, 5);
        char[] expectedArray = {'e', 'a', 'c', 'd', 'i'};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLongArrayWithRange() {
        long[] array = {5L, 3L, 9L, 1L, 4L};
        heapsort(array, 1, 4);
        long[] expectedArray = {5L, 1L, 3L, 9L, 4L};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testFloatArrayWithRange() {
        float[] array = {5.0f, 3.0f, 9.0f, 1.0f, 4.0f};
        heapsort(array, 0, 4);
        float[] expectedArray = {1.0f, 3.0f, 5.0f, 9.0f, 4.0f};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testDoubleArrayWithRange() {
        double[] array = {5.0, 3.0, 9.0, 1.0, 4.0};
        heapsort(array, 2, 5);
        double[] expectedArray = {5.0, 3.0, 1.0, 4.0, 9.0};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testObjectArrayWithRange() {
        String[] array = {"banana", "apple", "orange", "grape", "cherry"};
        heapsort(array, 1, 5);
        String[] expectedArray = {"banana", "apple", "cherry", "grape", "orange"};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testEmptyIntArrayWithRange() {
        int[] array = {};
        heapsort(array, 0, 0);
        int[] expectedArray = {};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testNegativeIntArrayWithRange() {
        int[] array = {-5, -3, -9, -1, -4};
        heapsort(array, 4, 5);
        int[] expectedArray = {-5, -3, -9, -1, -4};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIdenticalIntArrayWithRange() {
        int[] array = {7, 7, 7, 7, 7};
        heapsort(array, 0, 5);
        int[] expectedArray = {7, 7, 7, 7, 7};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeShortArrayWithRange() {
        short[] array = {5, 3, 9, 1, 4, 10, 2, 8, 6, 0, -1, -3, 12, 15, 14};
        heapsort(array, 5, 11);
        short[] expectedArray = {5, 3, 9, 1, 4, -1, 0, 2, 6, 8, 10, -3, 12, 15, 14};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeObjectArrayWithRange() {
        String[] array = {
            "banana", "apple", "orange", "grape", "cherry",
            "kiwi", "mango", "peach", "melon", "berry"
        };
        heapsort(array, 0, 3);
        String[] expectedArray = {
            "apple", "banana", "orange", "grape", "cherry",
            "kiwi", "mango", "peach", "melon", "berry"
        };
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testLargeObjectArrayWithRangeAndComparatorReversedReading() {
        String[] array = {
            "banana", "apple", "orange", "grape", "cherry",
            "kiwi", "mango", "peach", "melon", "berry"
        };

        Comparator<String> reverseReadingComparator = (s1, s2) -> {
            String reversedS1 = new StringBuilder(s1).reverse().toString();
            String reversedS2 = new StringBuilder(s2).reverse().toString();
            return reversedS1.compareTo(reversedS2);
        };
        heapsort(array, 0, 4, reverseReadingComparator);
        String[] expectedArray = {
            "banana", "orange", "apple", "grape", "cherry",
            "kiwi", "mango", "peach", "melon", "berry"
        };
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testIntegerArrayEvenOdd() {
        Integer[] array = {5, 3, 8, 10, 44, 2, 9, 1, 4};

        Comparator<Integer> evenFirstComparator = (a, b) -> {
            if (a % 2 == 0 && b % 2 != 0) {
                return -1;
            }
            if (a % 2 != 0 && b % 2 == 0) {
                return 1;
            }
            return a.compareTo(b);
        };

        heapsort(array, evenFirstComparator);
        Integer[] expectedArray = {2, 4, 8, 10, 44, 1, 3, 5, 9};
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public  void testRandomIntArrays() {
        Random random = new Random();
        double constantNlogN = 0;
        double constantNsquare = 0;

        for (int size = 5000000; size <= 20000000; size *= 2) {
            int[] array = random.ints(size, 0, size).toArray();
            long startTime = System.nanoTime();
            heapsort(array);
            long finishTime = System.nanoTime();

            boolean isArraySorted = true;
            for (int i = 0; i < size - 1; ++i) {
                if (array[i] > array[i + 1]) {
                    isArraySorted = false;
                    break;
                }
            }

            long timeInMs = (finishTime - startTime) / 1000000;
            double operationsNlogN = size * Math.log(size);
            double operationsNsquare = ((double) size) * size;

            if (size == 5000000) {
                constantNlogN = timeInMs / operationsNlogN;
                constantNsquare = timeInMs / operationsNsquare;
            }


            assertTrue(isArraySorted);
            System.out.printf(
                "Size: %d, Time: %d ms, Expected time: %f, N^2 expected time: %f\n",
                size,
                timeInMs,
                operationsNlogN * constantNlogN,
                operationsNsquare * constantNsquare
            );
        }
    }
}
