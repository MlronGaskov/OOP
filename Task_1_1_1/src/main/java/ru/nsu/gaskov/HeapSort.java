package ru.nsu.gaskov;

import java.util.Comparator;

/**
 * This class provides static methods for performing heap sort on arrays of various types.
 */
public class HeapSort {

    /**
     * Swaps two elements in the specified Object array.
     *
     * @param array the array in which to swap elements
     * @param a     the index of the first element
     * @param b     the index of the second element
     */
    private static void swap(Object[] array, int a, int b) {
        Object tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void swap(byte[] array, int a, int b) {
        byte tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void swap(short[] array, int a, int b) {
        short tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void swap(long[] array, int a, int b) {
        long tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void swap(float[] array, int a, int b) {
        float tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void swap(double[] array, int a, int b) {
        double tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void swap(char[] array, int a, int b) {
        char tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    /**
     * Gets the left child node index.
     *
     * @param fromIndex the starting index of the heap
     * @param index     the index of the parent node
     * @return the index of the left child node
     */
    private static int getLeftNodeIndex(int fromIndex, int index) {
        return fromIndex + 2 * (index - fromIndex) + 1;
    }

    /**
     * Gets the right child node index.
     *
     * @param fromIndex the starting index of the heap
     * @param index     the index of the parent node
     * @return the index of the right child node
     */
    private static int getRightNodeIndex(int fromIndex, int index) {
        return fromIndex + 2 * (index - fromIndex) + 2;
    }

    /**
     * Pushes the array element down to a heap.
     *
     * @param array     the array to heapify
     * @param fromIndex the starting index
     * @param toIndex   the end index (exclusive)
     * @param index     the index to heapify
     * @param c         the comparator to compare array elements
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void heapify(
            Object[] array,
            int fromIndex,
            int toIndex,
            int index,
            Comparator c
    ) {
        if (c == null) {
            heapify(array, fromIndex, toIndex, index);
            return;
        }
        int maxIndex = index;
        Object max = array[index];
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex) {
            Object left = array[leftIndex];
            if (c.compare(max, left) < 0) {
                maxIndex = leftIndex;
                max = array[maxIndex];
            }
        }
        if (rightIndex < toIndex) {
            Object right = array[rightIndex];
            if (c.compare(max, right) < 0) {
                maxIndex = rightIndex;
            }
        }
        if (maxIndex == index) {
            return;
        }
        swap(array, index, maxIndex);
        heapify(array, fromIndex, toIndex, maxIndex, c);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void heapify(
            Object[] array,
            int fromIndex,
            int toIndex,
            int index
    ) {
        int maxIndex = index;
        Comparable max = (Comparable) array[index];
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex) {
            Comparable left = (Comparable) array[leftIndex];
            if (max.compareTo(left) < 0) {
                maxIndex = leftIndex;
                max = (Comparable) array[maxIndex];
            }
        }
        if (rightIndex < toIndex) {
            Comparable right = (Comparable) array[rightIndex];
            if (max.compareTo(right) < 0) {
                maxIndex = rightIndex;
            }
        }
        if (maxIndex == index) {
            return;
        }
        swap(array, index, maxIndex);
        heapify(array, fromIndex, toIndex, maxIndex);
    }

    private static void heapify(byte[] array, int fromIndex, int toIndex, int index) {
        int maxNodeIndex = index;
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex && array[maxNodeIndex] < array[leftIndex]) {
            maxNodeIndex = leftIndex;
        }
        if (rightIndex < toIndex && array[maxNodeIndex] < array[rightIndex]) {
            maxNodeIndex = rightIndex;
        }
        if (maxNodeIndex == index) {
            return;
        }
        swap(array, index, maxNodeIndex);
        heapify(array, fromIndex, toIndex, maxNodeIndex);
    }

    private static void heapify(short[] array, int fromIndex, int toIndex, int index) {
        int maxNodeIndex = index;
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex && array[maxNodeIndex] < array[leftIndex]) {
            maxNodeIndex = leftIndex;
        }
        if (rightIndex < toIndex && array[maxNodeIndex] < array[rightIndex]) {
            maxNodeIndex = rightIndex;
        }
        if (maxNodeIndex == index) {
            return;
        }
        swap(array, index, maxNodeIndex);
        heapify(array, fromIndex, toIndex, maxNodeIndex);
    }

    private static void heapify(int[] array, int fromIndex, int toIndex, int index) {
        int maxNodeIndex = index;
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex && array[maxNodeIndex] < array[leftIndex]) {
            maxNodeIndex = leftIndex;
        }
        if (rightIndex < toIndex && array[maxNodeIndex] < array[rightIndex]) {
            maxNodeIndex = rightIndex;
        }
        if (maxNodeIndex == index) {
            return;
        }
        swap(array, index, maxNodeIndex);
        heapify(array, fromIndex, toIndex, maxNodeIndex);
    }

    private static void heapify(long[] array, int fromIndex, int toIndex, int index) {
        int maxNodeIndex = index;
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex && array[maxNodeIndex] < array[leftIndex]) {
            maxNodeIndex = leftIndex;
        }
        if (rightIndex < toIndex && array[maxNodeIndex] < array[rightIndex]) {
            maxNodeIndex = rightIndex;
        }
        if (maxNodeIndex == index) {
            return;
        }
        swap(array, index, maxNodeIndex);
        heapify(array, fromIndex, toIndex, maxNodeIndex);
    }

    private static void heapify(float[] array, int fromIndex, int toIndex, int index) {
        int maxNodeIndex = index;
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex && array[maxNodeIndex] < array[leftIndex]) {
            maxNodeIndex = leftIndex;
        }
        if (rightIndex < toIndex && array[maxNodeIndex] < array[rightIndex]) {
            maxNodeIndex = rightIndex;
        }
        if (maxNodeIndex == index) {
            return;
        }
        swap(array, index, maxNodeIndex);
        heapify(array, fromIndex, toIndex, maxNodeIndex);
    }

    private static void heapify(double[] array, int fromIndex, int toIndex, int index) {
        int maxNodeIndex = index;
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex && array[maxNodeIndex] < array[leftIndex]) {
            maxNodeIndex = leftIndex;
        }
        if (rightIndex < toIndex && array[maxNodeIndex] < array[rightIndex]) {
            maxNodeIndex = rightIndex;
        }
        if (maxNodeIndex == index) {
            return;
        }
        swap(array, index, maxNodeIndex);
        heapify(array, fromIndex, toIndex, maxNodeIndex);
    }

    private static void heapify(char[] array, int fromIndex, int toIndex, int index) {
        int maxNodeIndex = index;
        int leftIndex = getLeftNodeIndex(fromIndex, index);
        int rightIndex = getRightNodeIndex(fromIndex, index);

        if (leftIndex < toIndex && array[maxNodeIndex] < array[leftIndex]) {
            maxNodeIndex = leftIndex;
        }
        if (rightIndex < toIndex && array[maxNodeIndex] < array[rightIndex]) {
            maxNodeIndex = rightIndex;
        }
        if (maxNodeIndex == index) {
            return;
        }
        swap(array, index, maxNodeIndex);
        heapify(array, fromIndex, toIndex, maxNodeIndex);
    }

    /**
     * Initializes the heap structure for the specified array using the given comparator.
     *
     * @param array     the array to initialize
     * @param fromIndex the starting index
     * @param toIndex   the end index (exclusive)
     * @param c         the comparator to compare array elements
     */
    @SuppressWarnings({"rawtypes"})
    private static void heapInit(Object[] array, int fromIndex, int toIndex, Comparator c) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i, c);
        }
    }

    private static void heapInit(Object[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    private static void heapInit(byte[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    private static void heapInit(short[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    private static void heapInit(int[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    private static void heapInit(long[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    private static void heapInit(float[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    private static void heapInit(double[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    private static void heapInit(char[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    /**
     * Sorts the specified array using the heap sort algorithm with a custom comparator.
     *
     * @param a        the array to sort
     * @param fromIndex the starting index
     * @param toIndex   the end index (exclusive)
     * @param c        the comparator used to compare array elements
     */
    @SuppressWarnings({"rawtypes"})
    public static void heapsort(Object[] a, int fromIndex, int toIndex, Comparator c) {
        heapInit(a, fromIndex, toIndex, c);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex, c);
        }
    }

    public static void heapsort(Object[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    public static void heapsort(byte[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    public static void heapsort(short[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    public static void heapsort(int[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    public static void heapsort(long[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    public static void heapsort(float[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    public static void heapsort(double[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    public static void heapsort(char[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public static void heapsort(Object[] a, Comparator c) {
        heapsort(a, 0, a.length, c);
    }

    public static void heapsort(Object[] a) {
        heapsort(a, 0, a.length);
    }

    public static void heapsort(byte[] a) {
        heapsort(a, 0, a.length);
    }

    public static void heapsort(short[] a) {
        heapsort(a, 0, a.length);
    }

    public static void heapsort(int[] a) {
        heapsort(a, 0, a.length);
    }

    public static void heapsort(long[] a) {
        heapsort(a, 0, a.length);
    }

    public static void heapsort(float[] a) {
        heapsort(a, 0, a.length);
    }

    public static void heapsort(double[] a) {
        heapsort(a, 0, a.length);
    }

    public static void heapsort(char[] a) {
        heapsort(a, 0, a.length);
    }
}
