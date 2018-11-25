import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    static void swap(int[] prices, int index1, int index2) {
        int temp = prices[index1];
        prices[index1] = prices[index2];
        prices[index2] = temp;
    }

    static void quickSort(int[] prices, int minIndex, int maxIndex) {
        if (minIndex < maxIndex) {
            int p = partition(prices, minIndex, maxIndex);
            quickSort(prices, minIndex, p - 1);
            quickSort(prices, p + 1, maxIndex);
        }
    }


    static int partition(int[] prices, int minIndex, int maxIndex) {
        int pivot = prices[maxIndex];
        int i = minIndex - 1;
        for (int j = minIndex; j <  maxIndex; j++) {
            if (prices[j] < pivot) {
                if (i != j) {
                    i += 1;
                    swap(prices, i, j);
                }
            }
        }
        i += 1;
        swap(prices, i, maxIndex);
        return i;
    }



    static int maximumToys(int[] prices, int k) {
        // sort prices
        quickSort(prices, 0, prices.length-1);
        // count lowest priced items until money spent
        int totalSpent = 0;
        int totalItems = 0;
        for (int i=0; i < prices.length; i++) {
            if (prices[i] + totalSpent < k) {
                totalSpent += prices[i];
                totalItems += 1;
            } else {
                break;
            }
        }
        return totalItems;
    }


    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(new File("src/main/resources/prices/test1.txt"));

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        int[] prices = new int[n];

        String[] pricesItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int pricesItem = Integer.parseInt(pricesItems[i]);
            prices[i] = pricesItem;
        }

        int result = maximumToys(prices, k);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
