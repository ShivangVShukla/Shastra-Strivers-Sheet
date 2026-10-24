import java.io.*;
import java.util.*;

public class gen {
    static class RefNode {
        int value, freq;
        long time;
        RefNode(int value, int freq, long time) {
            this.value = value;
            this.freq = freq;
            this.time = time;
        }
    }

    static class ReferenceLFU {
        int cap;
        long clock = 0;
        Map<Integer, RefNode> map = new HashMap<>();

        ReferenceLFU(int cap) { this.cap = cap; }

        int get(int key) {
            RefNode x = map.get(key);
            if (x == null) return -1;
            x.freq++;
            x.time = ++clock;
            return x.value;
        }

        void put(int key, int value) {
            if (cap <= 0) return;

            RefNode x = map.get(key);
            if (x != null) {
                x.value = value;
                x.freq++;
                x.time = ++clock;
                return;
            }

            if (map.size() == cap) {
                int victim = -1;
                int bestFreq = Integer.MAX_VALUE;
                long bestTime = Long.MAX_VALUE;

                for (Map.Entry<Integer, RefNode> e : map.entrySet()) {
                    RefNode n = e.getValue();
                    if (n.freq < bestFreq ||
                        (n.freq == bestFreq && n.time < bestTime)) {
                        bestFreq = n.freq;
                        bestTime = n.time;
                        victim = e.getKey();
                    }
                }
                map.remove(victim);
            }

            map.put(key, new RefNode(value, 1, ++clock));
        }
    }

    static class Op {
        int type, key, value;
        Op(int type, int key, int value) {
            this.type = type;
            this.key = key;
            this.value = value;
        }
    }

    static void writeCase(int idx, int cap, List<Op> ops) throws Exception {
        String in = String.format("../Testcases/Input/input%02d.txt", idx);
        String out = String.format("../Testcases/Output/output%02d.txt", idx);

        ReferenceLFU ref = new ReferenceLFU(cap);
        StringBuilder expected = new StringBuilder();
        boolean first = true;

        try (PrintWriter pw = new PrintWriter(new FileWriter(in))) {
            pw.println(cap + " " + ops.size());
            for (Op op : ops) {
                if (op.type == 1) {
                    pw.println("1 " + op.key + " " + op.value);
                    ref.put(op.key, op.value);
                } else {
                    pw.println("2 " + op.key);
                    int ans = ref.get(op.key);
                    if (!first) expected.append(' ');
                    expected.append(ans);
                    first = false;
                }
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println(first ? "EMPTY" : expected.toString());
        }
    }

    static void addBasic(List<List<Op>> all) {
        all.add(Arrays.asList(
            new Op(1,1,1), new Op(1,2,2), new Op(2,1,0),
            new Op(1,3,3), new Op(2,2,0), new Op(2,3,0),
            new Op(1,4,4), new Op(2,1,0), new Op(2,3,0),
            new Op(2,4,0), new Op(2,2,0)
        ));

        all.add(Arrays.asList(
            new Op(1,1,10), new Op(2,1,0), new Op(1,2,20),
            new Op(2,1,0), new Op(2,2,0)
        ));

        all.add(Arrays.asList(
            new Op(1,1,1), new Op(1,2,2), new Op(1,3,3),
            new Op(1,4,4), new Op(2,1,0), new Op(2,2,0),
            new Op(2,3,0), new Op(2,4,0)
        ));

        all.add(Arrays.asList(
            new Op(1,1,1), new Op(2,1,0), new Op(2,1,0),
            new Op(2,1,0), new Op(1,2,2), new Op(1,3,3),
            new Op(2,1,0), new Op(2,2,0), new Op(2,3,0)
        ));

        all.add(Arrays.asList(
            new Op(1,5,7), new Op(1,4,6), new Op(1,3,5),
            new Op(1,2,4), new Op(1,1,3),
            new Op(2,1,0), new Op(2,2,0), new Op(2,3,0),
            new Op(2,4,0), new Op(2,5,0)
        ));
    }

    public static void main(String[] args) throws Exception {
        Random rnd = new Random(2302026L);
        List<List<Op>> all = new ArrayList<>();
        addBasic(all);

        // No-get case
        all.add(Arrays.asList(
            new Op(1,1,1), new Op(1,2,2), new Op(1,3,3),
            new Op(1,2,20)
        ));

        while (all.size() < 100) {
            int idx = all.size();
            int cap;
            int q;

            if (idx < 30) {
                cap = 1 + rnd.nextInt(5);
                q = 5 + rnd.nextInt(40);
            } else if (idx < 80) {
                cap = 1 + rnd.nextInt(20);
                q = 30 + rnd.nextInt(250);
            } else {
                cap = 1 + rnd.nextInt(100);
                q = 1000 + rnd.nextInt(2500);
            }

            List<Op> ops = new ArrayList<>();

            // Ensure a mixture of gets and puts.
            for (int i = 0; i < q; i++) {
                int choice = rnd.nextInt(100);

                if (choice < 55) {
                    int key = rnd.nextInt(Math.max(3, cap * 3 + 10));
                    int value = rnd.nextInt(100001);
                    ops.add(new Op(1, key, value));
                } else {
                    int key = rnd.nextInt(Math.max(3, cap * 3 + 10));
                    ops.add(new Op(2, key, 0));
                }
            }

            // Force some existing-key updates and accesses.
            if (cap >= 1 && q >= 8) {
                ops.set(0, new Op(1, 1, 100));
                ops.set(1, new Op(1, 2, 200));
                ops.set(2, new Op(2, 1, 0));
                ops.set(3, new Op(1, 1, 101));
            }

            all.add(ops);
        }

        for (int i = 0; i < 100; i++) {
            int cap;
            if (i == 0) cap = 2;
            else if (i == 1) cap = 1;
            else cap = 1 + rnd.nextInt(100);

            writeCase(i, cap, all.get(i));
        }
    }
}
