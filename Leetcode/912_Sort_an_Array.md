### 912. Sort an Array
### Problem Link: [Sort an Array](https://leetcode.com/problems/sort-an-array/)
### Intuition
There are several efficient sorting algorithms that can be used to solve this problem. Three common approaches are:

1. **Heap Sort**: Uses a binary heap data structure to efficiently extract the maximum element repeatedly.
2. **Merge Sort**: Divides the array into halves, sorts each half, and then merges them back together.
3. **Quick Sort**: Selects a pivot element and partitions the array around it, then recursively sorts the sub-arrays.

Each algorithm has its strengths and weaknesses in terms of time complexity, space complexity, and stability.

### Java Reference Implementation (Quick Sort)
```java
class Solution {
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pivotIndex = partition(arr, low, high);
            
            // Recursively sort the sub-arrays
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        // Choose the rightmost element as pivot
        int pivot = arr[high];
        
        // Index of smaller element
        int i = low - 1;
        
        // Traverse the array and move elements smaller than pivot to the left
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        // Place the pivot in its correct position
        swap(arr, i + 1, high);
        
        // Return the pivot index
        return i + 1;
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

### Alternative Implementation (Heap Sort)
```java
class Solution {
    private int heapSize;
    
    public int[] sortArray(int[] nums) {
        heapSort(nums);
        return nums;
    }
    
    private void heapSort(int[] arr) {
        // Build max heap
        buildMaxHeap(arr);
        
        // Extract elements from the heap one by one
        for (int i = arr.length - 1; i > 0; i--) {
            // Move current root (maximum) to the end
            swap(arr, 0, i);
            
            // Reduce heap size by 1
            heapSize--;
            
            // Restore max heap property
            maxHeapify(arr, 0);
        }
    }
    
    private void buildMaxHeap(int[] arr) {
        heapSize = arr.length;
        
        // Start from the last non-leaf node and heapify all nodes in reverse order
        for (int i = (heapSize / 2) - 1; i >= 0; i--) {
            maxHeapify(arr, i);
        }
    }
    
    private void maxHeapify(int[] arr, int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        
        // Find the largest among node i, its left child, and its right child
        if (left < heapSize && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < heapSize && arr[right] > arr[largest]) {
            largest = right;
        }
        
        // If the largest is not the current node, swap and continue heapifying
        if (largest != i) {
            swap(arr, i, largest);
            maxHeapify(arr, largest);
        }
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

### Alternative Implementation (Merge Sort)
```java
class Solution {
    public int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        // Calculate sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        // Create temp arrays
        int[] L = new int[n1];
        int[] R = new int[n2];
        
        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }
        
        // Merge the temp arrays
        int i = 0, j = 0;
        int k = left;
        
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        
        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}
```

### Requirement → Code Mapping (Quick Sort)
- **R0 (Partition the array)**: `partition(arr, low, high)` - Divide the array around a pivot element
- **R1 (Choose pivot)**: `int pivot = arr[high];` - Select the rightmost element as the pivot
- **R2 (Move elements)**: Move elements smaller than the pivot to the left side
- **R3 (Place pivot)**: Place the pivot in its correct position
- **R4 (Recursive sorting)**: Recursively sort the sub-arrays on both sides of the pivot

### Complexity Analysis
- **Time Complexity**: 
  - Quick Sort: O(n log n) average case, O(n²) worst case
  - Heap Sort: O(n log n) - Building the heap takes O(n) time, and each of the n heapify operations takes O(log n) time
  - Merge Sort: O(n log n) - Each level of recursion divides the problem in half and takes O(n) time to merge
- **Space Complexity**: 
  - Quick Sort: O(log n) average case for recursion stack, O(n) worst case
  - Heap Sort: O(1) - In-place sorting algorithm
  - Merge Sort: O(n) - Requires additional space for the temporary arrays during merging
