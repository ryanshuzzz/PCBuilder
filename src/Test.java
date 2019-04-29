import java.util.*;
import Component.*;


    public class Test {
        private static ArrayList<String> theList = new ArrayList<>();

        private void swap(int i, int j) {
            String temp = theList.get(i);
            theList.set(i,theList.get(j));
            theList.set(j,temp);
        }

        void maxHeapify(String Type, ArrayList theList, Map<String, Component> map ,int n, int i)
        {
            int largest = i;
            int l = 2*i + 1;
            int r = 2*i + 2;

            if (l < n && map.get(theList.get(l)).getField(Type) > map.get(theList.get(largest)).getField(Type) )
                largest = l;
            if (r < n && map.get(theList.get(r)).getField(Type)  > map.get(theList.get(largest)).getField(Type) )
                largest = r;
            if (largest != i)
            {

                swap(i,largest);
                maxHeapify(Type,theList, map ,n,largest);
            }
        }

        public void BuildMaxHeap(String Type, ArrayList theList, Map<String, Component> map)
        {
            int n = theList.size();
            for (int i = n / 2 - 1; i >= 0; i--)
                maxHeapify(Type, theList, map ,n,i);
        }
        public void heapSort(String Type, ArrayList list, Map<String, Component> map)
        {
            this.theList = list;
            int n = theList.size();
            BuildMaxHeap(Type, theList, map);
            for (int i=n-1; i>=0; i--)
            {
                swap(0,i);
                maxHeapify(Type, list,map, i, 0);
            }
        }

        // Driver program
/*        public static void main(String args[])
        {
            int arr[] = {6,5,3,1,8,7,2,4};
            //print unsorted array using Arrays.toString()
            System.out.print("Unsorted array: ");
            System.out.println(Arrays.toString(arr));

            Test ob = new Test();
            ob.heapSort(arr);

            System.out.print("Sorted array: ");
            //print sorted array
            System.out.println(Arrays.toString(arr));
        }*/
    }
