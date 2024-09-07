package ru.nsu.gaskov;

import java.util.Comparator;

/**
 * This class provides static methods for performing heap sort on arrays of various types.
 *
 * <p>Heap sort is a comparison-based sorting algorithm that uses a binary heap data structure.
 * It is efficient for large datasets and has a time complexity of O(n log n) in the average
 * and worst cases. The space complexity is O(1) since it sorts the array in place.</p>
 *
 * <p>This class includes methods for sorting arrays of primitive types (such as int,
 * double, and char) as well as object arrays. It also supports custom sorting through
 * comparators for user-defined types.</p>
 *
 * <p>Note: The input arrays are modified in place, and the original order of equal elements
 * is not necessarily preserved (unstable sort).</p>
 */
public class HeapSort {

    /**
     * Swaps two elements in the specified array type.
     *
     * @param array the array in which to swap elements
     * @param a     the index of the first element
     * @param b     the index of the second element
     *
     * <p>This method can be used with arrays of various types including:</p>
     * <ul>
     *     <li>Object[]</li>
     *     <li>byte[]</li>
     *     <li>short[]</li>
     *     <li>int[]</li>
     *     <li>long[]</li>
     *     <li>float[]</li>
     *     <li>double[]</li>
     *     <li>char[]</li>
     * </ul>
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
     * <p>Note: the heap starts not from the zero index in general</p>
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
     * <p>Note: the heap starts not from the zero index in general</p>
     *
     * @param fromIndex the starting index of the heap
     * @param index     the index of the parent node
     * @return the index of the right child node
     */
    private static int getRightNodeIndex(int fromIndex, int index) {
        return fromIndex + 2 * (index - fromIndex) + 2;
    }

    /**
     * Pushes the specified element in the array down to its appropriate position in an
     * Object heap, using the provided comparator if it's not null. This operation ensures
     * that the heap property is maintained for the subtree rooted at the given index.
     *
     * <p>Time Complexity: O(log n).</p>
     *
     * @param array     the array to heapify
     * @param fromIndex the heap starting index
     * @param toIndex   the heap end index (exclusive)
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

    /**
     * Pushes the specified element in the array down to its appropriate position in an
     * Object heap, using the natural ordering of the elements. This operation ensures
     * that the heap property is maintained for the subtree rooted at the given index.
     *
     * <p>Time Complexity: O(log n).</p>
     *
     * @param array     the array to heapify
     * @param fromIndex the starting index
     * @param toIndex   the end index (exclusive)
     * @param index     the index to heapify
     */
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

    /**
     * Pushes the specified element in the array down to its appropriate position in a heap
     * of the specified type, ensuring that the heap property is maintained for the subtree
     * rooted at the given index.
     *
     * <p>This method operates in place and assumes that the elements can be compared using
     * the natural ordering of their respective types.</p>
     *
     * <p>Time Complexity: O(log n).</p>
     *
     * <p>This method can be used with arrays of various types including:</p>
     * <ul>
     *     <li>byte[]</li>
     *     <li>short[]</li>
     *     <li>int[]</li>
     *     <li>long[]</li>
     *     <li>float[]</li>
     *     <li>double[]</li>
     *     <li>char[]</li>
     * </ul>
     *
     * @param array the array to heapify
     * @param fromIndex the starting index of the heap (inclusive)
     * @param toIndex the end index of the heap (exclusive)
     * @param index the index of the element to heapify
     */
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
     * This method transforms the specified range of the array into a valid heap by
     * repeatedly applying the heapify operation.
     *
     * @param array the array to initialize
     * @param fromIndex the starting index of the heap (inclusive)
     * @param toIndex the end index of the heap (exclusive)
     * @param c the comparator to compare array elements
     */
    @SuppressWarnings({"rawtypes"})
    private static void heapInit(Object[] array, int fromIndex, int toIndex, Comparator c) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i, c);
        }
    }

    /**
     * Initializes the heap structure for the specified array.
     * This method transforms the specified range of the array into a valid heap by
     * repeatedly applying the heapify operation.
     *
     * @param array the array to initialize
     * @param fromIndex the starting index of the heap (inclusive)
     * @param toIndex the end index of the heap (exclusive)
     */
    private static void heapInit(Object[] array, int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            heapify(array, fromIndex, toIndex, i);
        }
    }

    /**
     * Initializes the heap structure for the primitive types array.
     * This method transforms the specified range of the array into a valid heap by
     * repeatedly applying the heapify operation.
     *
     * <p>This method can be used with arrays of various types including:</p>
     * <ul>
     * <li>byte[]</li>
     * <li>short[]</li>
     * <li>int[]</li>
     * <li>long[]</li>
     * <li>float[]</li>
     * <li>double[]</li>
     * <li>char[]</li>
     * </ul>
     *
     * @param array the array to initialize
     * @param fromIndex the starting index of the heap (inclusive)
     * @param toIndex the end index of the heap (exclusive)
     */
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
     * This method transforms the array into a heap and then extracts the largest elements
     * in order to sort the array.
     *
     * <p>The array is sorted in-place, meaning no additional arrays are created during
     * the sorting process.</p>
     *
     * @param a the array to sort
     * @param fromIndex the starting index of the portion of the array to sort (inclusive)
     * @param toIndex the end index of the portion of the array to sort (exclusive)
     * @param c the comparator used to compare array elements
     */
    public static <T> void heapsort(T[] a, int fromIndex, int toIndex, Comparator<? super T> c) {
        heapInit(a, fromIndex, toIndex, c);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex, c);
        }
    }

    /**
     * Sorts the specified array using the heap sort algorithm.
     * This method transforms the array into a heap and then extracts the largest elements
     * in order to sort the array using the natural ordering of elements.
     *
     * <p>The array is sorted in-place, meaning no additional arrays are created during
     * the sorting process.</p>
     *
     * @param a the array to sort
     * @param fromIndex the starting index of the portion of the array to sort (inclusive)
     * @param toIndex the end index of the portion of the array to sort (exclusive)
     */
    public static void heapsort(Object[] a, int fromIndex, int toIndex) {
        heapInit(a, fromIndex, toIndex);
        for (int i = 0; i < toIndex - fromIndex; ++i) {
            swap(a, fromIndex, toIndex - 1 - i);
            heapify(a, fromIndex, toIndex - 1 - i, fromIndex);
        }
    }

    /**
     * Sorts the segment of the array using the heap sort algorithm.
     * This method transforms the array into a heap and
     * then extracts the largest elements in order to sort the array.
     *
     * <p>The array is sorted in-place, meaning no additional arrays are created
     * during the sorting process.</p>
     *
     * <p>
     * The specific implementation of this method depends on the type of the array:
     * it can handle byte, short, int, long, float, double, and char arrays.
     * </p>
     *
     * @param a the array to sort
     * @param fromIndex the starting index of the portion of the array to sort (inclusive)
     * @param toIndex the end index of the portion of the array to sort (exclusive)
     */
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

    /**
     * Sorts the whole specified array using the heap sort algorithm and a custom comparator.
     * This method initializes the sorting process on the entire array, allowing
     * for custom ordering of the elements.
     *
     * <p>The sorting is performed in-place without using additional storage.</p>
     *
     * @param a the array to sort
     * @param c the comparator used to compare array elements
     */
    public static <T> void heapsort(T[] a, Comparator<? super T> c) {
        heapsort(a, 0, a.length, c);
    }

    /**
     * Sorts the whole specified array using the heap sort algorithm.
     * This method initializes the sorting process on the entire array,
     * relying on the natural ordering of the elements.
     *
     * <p>The sorting is performed in-place without using additional storage.</p>
     *
     * @param a the array to sort
     */
    public static void heapsort(Object[] a) {
        heapsort(a, 0, a.length);
    }

    /**
     * Sorts the whole array using the heap sort algorithm.
     * This method sorts the entire array in-place, meaning no additional arrays
     * are created during the sorting process.
     *
     * <p>The specific implementation of this method depends on the type of the array,
     * which can be byte, short, int, long, float, double, or char.</p>
     *
     * @param a        the array to sort
     */
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
