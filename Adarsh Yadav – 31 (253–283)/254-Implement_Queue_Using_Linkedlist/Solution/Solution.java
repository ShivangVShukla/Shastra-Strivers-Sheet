import java.io.*;
import java.util.*;

public class Solution {

    static class LinkedListQueue {
        private static class Node {
            int val;
            Node next;

            Node(int val) {
                this.val = val;
            }
        }

        private Node head;
        private Node tail;

        void push(int x) {
            Node node = new Node(x);
            if (tail == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }

        int pop() {
            int v = head.val;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            return v;
        }

        int peek() {
            return head.val;
        }

        boolean isEmpty() {
            return head == null;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        LinkedListQueue queue = new LinkedListQueue();
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();

            switch (op) {
                case "push":
                    queue.push(Integer.parseInt(st.nextToken()));
                    sb.append("null");
                    break;
                case "pop":
                    sb.append(queue.pop());
                    break;
                case "peek":
                    sb.append(queue.peek());
                    break;
                default:
                    sb.append(queue.isEmpty());
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }
}
