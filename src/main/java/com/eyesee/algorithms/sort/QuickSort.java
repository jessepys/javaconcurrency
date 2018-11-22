package com.eyesee.algorithms.sort;

import org.junit.Test;
import java.util.Arrays;

public class QuickSort {

    @Test
    public void testQuickSort() {
//        Arrays.binarySearch()
        int[] tests = {2, 8, 3, 7, 8, 5, 9};
//        int[] tests = {2342,2, 4, 3, 9, 4, 23, 90, 23, 5, 89, 34, 234, 123, 456, 908};
        QuickSort.quickSortAll(tests, 0 , tests.length - 1);
//        Arrays.stream(tests).forEach(System.out :: println);
//        ArrayIns arrayIns = new ArrayIns(tests);
//        arrayIns.quickSort();
        Arrays.stream(tests).forEach(System.out :: println);
    }


    public static int quickSortAll(int[] arr,int start,int end) {
        if (end<=0 ||start>=end ||start<0) {
            return start;
        }else {
            int index=fastSort(arr,start,end);
            quickSortAll(arr,start,index-1);
            quickSortAll(arr,index+1,end);
            return index;
        }

    }

    //2,8,3,5,9
    public static int fastSort(int[] arr, int start, int end) {
        if (arr.length <= 0 || start < 0 || start >= arr.length || end < 0 || end >= arr.length)
            throw new IllegalArgumentException("start:"+start+" end"+end);
        int x = arr[start];
        while (start < end) {
            while (start<end && arr[end]>x) {
                end--;
            }

            if (arr[end]<x || (start!=end &&arr[end]==x)){
                arr[start]=arr[end];
                start++;
            }
            while (start<end && arr[start]<x){
                start++;
            }

            if (arr[start]>x){
                arr[end]=arr[start];
                end--;
            }

        }
        arr[start]=x;
        return start;
    }


    public static void sort(int[] arrays) {
        sort(arrays, 0, arrays.length - 1);
    }

    private static void sort(int[] arrays, int left, int right) {
        if (right <= left) {
            return;
        }
        int partition = partition(arrays, left, right, arrays[right]);
        System.out.println("left: " + left + " right:" + right + " partition:" + partition);
        sort(arrays, left, partition - 1);
        sort(arrays, partition + 1, right);
    }

    private static void mannaulSort(int[] arrays, int left, int right) {
        int size = right -left;

        if (size == 0) {
            return;
        }

        if (size == 1) {
            if (arrays[left] > arrays[right]) {
                swap(arrays, left, right);
            }
        }

        int mid = (left + right) / 2;
        if (arrays[left] > arrays[mid]) {
            swap(arrays, left, mid);
        }

        if (arrays[left] > arrays[right]) {
            swap(arrays, left, right);
        }

        if (arrays[mid] > arrays[right]) {
            swap(arrays, mid, right);
        }

    }

    private static int medianOf3(int[] arrays, int left, int right) {
        int mid = (left + right) / 2;

        if (arrays[left] > arrays[mid]) {
            swap(arrays, left, mid);
        }
        if (arrays[left] > arrays[right]) {
            swap(arrays, left, right);
        }

        if (arrays[mid] > arrays[right]) {
            swap(arrays, mid, right);
        }

        swap(arrays, mid, right - 1);

        return arrays[right - 1];
    }

    private static void sort1(int[] arrays, int left, int right) {
        if (right <= left) {
            return;
        }

        if (right - left <= 2) {
            mannaulSort(arrays, left, right);
            return;
        }

        int median = medianOf3(arrays, left, right);
        int partition = partition1(arrays, left, right, median);
        System.out.println("left: " + left + " right:" + right + " partition:" + partition);
        sort1(arrays, left, partition - 1);
        sort1(arrays, partition + 1, right);
    }

    private static int partition1(int[] arrays, int left, int right, int pivot) {
        int low = left;
        int high = right - 1;

        while (true) {
            while (arrays[++low] < pivot);
            while (arrays[--high] > pivot);
            if (low >= high) {
                break;
            } else {
                swap(arrays, low, high);
            }
        }
        System.out.println("low: " + low + " high: " + high + " pivot：" + pivot);
        swap(arrays, low, right - 1);
        Arrays.stream(arrays).forEach(System.out :: println);


        return low;
    }

    private static int partition(int[] arrays, int left, int right, int pivot) {
        int low = left - 1;
        int high = right;

        System.out.println("low: " + low + " high: " + high);
        Arrays.stream(arrays).forEach(System.out :: println);
        while (true) {
            while (arrays[++low] < pivot);
            while (arrays[--high] > pivot);
            if (low >= high) {
                break;
            } else {
                swap(arrays, low, high);
            }
        }
        System.out.println(arrays[right - 1]);
        swap(arrays, low, right);

        return low;
    }

    private static void swap(int[] arrays, int from, int to) {
        int value = arrays[from];
        arrays[from] = arrays[to];
        arrays[to] = value;
    }

    class ArrayIns
    {
        private long[] theArray;          // ref to array theArray
        private int nElems;               // number of data items

        public ArrayIns(long[] theArray) {
            this.theArray = theArray;
            this.nElems = theArray.length;
        }

        //--------------------------------------------------------------
        public ArrayIns(int max)          // constructor
        {
            theArray = new long[max];      // create the array
            nElems = 0;                    // no items yet
        }
        //--------------------------------------------------------------
        public void insert(long value)    // put element into array
        {
            theArray[nElems] = value;      // insert it
            nElems++;                      // increment size
        }
        //--------------------------------------------------------------
        public void display()             // displays array contents
        {
            System.out.print("A=");
            for(int j=0; j<nElems; j++)    // for each element,
                System.out.print(theArray[j] + " ");  // display it
            System.out.println("");
        }
        //--------------------------------------------------------------
        public void quickSort()
        {
            recQuickSort(0, nElems-1);
            // insertionSort(0, nElems-1); // the other option
        }
        //--------------------------------------------------------------
        public void recQuickSort(int left, int right)
        {
            int size = right-left+1;
            if(size < 10)                   // insertion sort if small
                insertionSort(left, right);
            else                            // quicksort if large
            {
                long median = medianOf3(left, right);
                int partition = partitionIt(left, right, median);
                recQuickSort(left, partition-1);
                recQuickSort(partition+1, right);
            }
        }  // end recQuickSort()
        //--------------------------------------------------------------
        public long medianOf3(int left, int right)
        {
            int center = (left+right)/2;
            // order left & center
            if( theArray[left] > theArray[center] )
                swap(left, center);
            // order left & right
            if( theArray[left] > theArray[right] )
                swap(left, right);
            // order center & right
            if( theArray[center] > theArray[right] )
                swap(center, right);

            swap(center, right-1);           // put pivot on right
            return theArray[right-1];        // return median value
        }  // end medianOf3()
        //--------------------------------------------------------------
        public void swap(int dex1, int dex2)  // swap two elements
        {
            long temp = theArray[dex1];        // A into temp
            theArray[dex1] = theArray[dex2];   // B into A
            theArray[dex2] = temp;             // temp into B
        }  // end swap(
        //--------------------------------------------------------------
        public int partitionIt(int left, int right, long pivot)
        {
            int leftPtr = left;             // right of first elem
            int rightPtr = right - 1;       // left of pivot
            while(true)
            {
                while( theArray[++leftPtr] < pivot )  // find bigger
                    ;                                  // (nop)
                while( theArray[--rightPtr] > pivot ) // find smaller
                    ;                                  // (nop)
                if(leftPtr >= rightPtr)      // if pointers cross,
                    break;                    //    partition done
                else                         // not crossed, so
                    swap(leftPtr, rightPtr);  // swap elements
            }  // end while(true)
            swap(leftPtr, right-1);         // restore pivot
            return leftPtr;                 // return pivot location
        }  // end partitionIt()
        //--------------------------------------------------------------
        // insertion sort
        public void insertionSort(int left, int right)
        {
            int in, out;
            //  sorted on left of out
            for(out=left+1; out<=right; out++)
            {
                long temp = theArray[out];    // remove marked item
                in = out;                     // start shifts at out
                // until one is smaller,
                while(in>left && theArray[in-1] >= temp)
                {
                    theArray[in] = theArray[in-1]; // shift item to right
                    --in;                      // go left one position
                }
                theArray[in] = temp;          // insert marked item
            }  // end for
        }  // end insertionSort()
//--------------------------------------------------------------
    }
}
