import java.io.*;
import java.util.*;

public class Solution {

    static class MinStack {
        private final ArrayDeque<Integer> values = new ArrayDeque<>();
        private final ArrayDeque<Integer> mins = new ArrayDeque<>();

        public void push(int val) {
            values.push(val);
            if (mins.isEmpty()) {
                mins.push(val);
            } else {
                mins.push(Math.min(val, mins.peek()));
            }
        }

        public void pop() {
            values.pop();
            mins.pop();
        }

        public int top() {
            return values.peek();
        }

        public int getMin() {
            return mins.peek();
        }

        public boolean isEmpty() {
            return values.isEmpty();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String first = br.readLine();
        if (first == null || first.trim().isEmpty()) {
            return;
        }

        int t = Integer.parseInt(first.trim());
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int q = Integer.parseInt(br.readLine().trim());
            MinStack stack = new MinStack();

            for (int i = 0; i < q; i++) {
                String line = br.readLine();
                while (line != null && line.trim().isEmpty()) {
                    line = br.readLine();
                }
                if (line == null) {
                    throw new EOFException("Not enough operations in input.");
                }

                StringTokenizer st = new StringTokenizer(line);
                String op = st.nextToken();

                switch (op) {
                    case "push":
                        stack.push(Integer.parseInt(st.nextToken()));
                        break;
                    case "pop":
                        stack.pop();
                        break;
                    case "top":
                        out.append(stack.top()).append('\n');
                        break;
                    case "getMin":
                        out.append(stack.getMin()).append('\n');
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operation: " + op);
                }
            }
        }

        System.out.print(out);
    }
}
