import java.io.*;
import java.util.*;

public class Solution {
    public static int totalFruit(int[] fruits) {
        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int answer = 0;

        for (int right = 0; right < fruits.length; right++) {
            int fruit = fruits[right];
            freq.put(fruit, freq.getOrDefault(fruit, 0) + 1);

            while (freq.size() > 2) {
                int leftFruit = fruits[left];
                freq.put(leftFruit, freq.get(leftFruit) - 1);

                if (freq.get(leftFruit) == 0) {
                    freq.remove(leftFruit);
                }

                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] fruits = new int[n];

        for (int i = 0; i < n; i++) {
            fruits[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(totalFruit(fruits));
    }
}
