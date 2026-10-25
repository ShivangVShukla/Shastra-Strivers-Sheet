import java.io.*;
import java.util.*;

public class Solution {

    static int[] nextSmallerElements(int[] arr) {
        int n = arr.length;
        int[] answer = new int[n];
        int[] stack = new int[n];
        int top = 0;

        for (int i = n - 1; i >= 0; i--) {
            while (top > 0 && stack[top - 1] >= arr[i]) {
                top--;
            }

            answer[i] = (top == 0) ? -1 : stack[top - 1];
            stack[top++] = arr[i];
        }

        return answer;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = br.readLine();
        if (firstLine == null || firstLine.trim().isEmpty()) {
            return;
        }

        int t = Integer.parseInt(firstLine.trim());
        StringBuilder output = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] arr = new int[n];
            int filled = 0;

            while (filled < n) {
                String line = br.readLine();
                if (line == null) {
                    throw new EOFException("Not enough array values in input.");
                }
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens() && filled < n) {
                    arr[filled++] = Integer.parseInt(st.nextToken());
                }
            }

            int[] answer = nextSmallerElements(arr);
            for (int i = 0; i < answer.length; i++) {
                if (i > 0) output.append(' ');
                output.append(answer[i]);
            }
            output.append('\n');
        }

        System.out.print(output);
    }
}
