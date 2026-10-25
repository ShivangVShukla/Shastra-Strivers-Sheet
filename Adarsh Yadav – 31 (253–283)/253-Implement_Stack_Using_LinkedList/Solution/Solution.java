import java.io.*;
import java.util.*;

public class Solution {

    static class LinkedListStack {
        private static class Node {
            int val;
            Node next;

            Node(int val, Node next) {
                this.val = val;
                this.next = next;
            }
        }

        private Node head;

        void push(int x) {
            head = new Node(x, head);
        }

        int pop() {
            int v = head.val;
            head = head.next;
            return v;
        }

        int top() {
            return head.val;
        }

        boolean isEmpty() {
            return head == null;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        LinkedListStack stack = new LinkedListStack();
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();

            switch (op) {
                case "push":
                    stack.push(Integer.parseInt(st.nextToken()));
                    sb.append("null");
                    break;
                case "pop":
                    sb.append(stack.pop());
                    break;
                case "top":
                    sb.append(stack.top());
                    break;
                default:
                    sb.append(stack.isEmpty());
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }
}
