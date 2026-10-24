import java.io.*;
import java.util.*;

public class gen {
    static class Op {
        int type, key, value;
        Op(int type, int key, int value) {
            this.type = type;
            this.key = key;
            this.value = value;
        }
    }

    static List<Integer> solve(int capacity, List<Op> ops) {
        LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>(16, 0.75f, true);
        List<Integer> out = new ArrayList<>();

        for (Op op : ops) {
            if (op.type == 1) {
                if (cache.containsKey(op.key)) {
                    cache.put(op.key, op.value);
                } else {
                    if (cache.size() == capacity) {
                        Iterator<Integer> it = cache.keySet().iterator();
                        it.next();
                        it.remove();
                    }
                    cache.put(op.key, op.value);
                }
            } else {
                out.add(cache.getOrDefault(op.key, -1));
            }
        }
        return out;
    }

    static void writeCase(File inDir, File outDir, int idx, int capacity, List<Op> ops) throws Exception {
        String id = String.format("%02d", idx);

        try (PrintWriter w = new PrintWriter(new File(inDir, "input" + id + ".txt"))) {
            w.println(capacity + " " + ops.size());
            for (Op op : ops) {
                if (op.type == 1) {
                    w.println("1 " + op.key + " " + op.value);
                } else {
                    w.println("2 " + op.key);
                }
            }
        }

        List<Integer> ans = solve(capacity, ops);
        try (PrintWriter w = new PrintWriter(new File(outDir, "output" + id + ".txt"))) {
            if (ans.isEmpty()) {
                w.println("EMPTY");
            } else {
                for (int i = 0; i < ans.size(); i++) {
                    if (i > 0) w.print(' ');
                    w.print(ans.get(i));
                }
                w.println();
            }
        }
    }

    static List<Op> fixed(int[][] rows) {
        List<Op> ops = new ArrayList<>();
        for (int[] r : rows) {
            if (r[0] == 1) ops.add(new Op(1, r[1], r[2]));
            else ops.add(new Op(2, r[1], 0));
        }
        return ops;
    }

    public static void main(String[] args) throws Exception {
        File base = new File("../Testcases");
        File inDir = new File(base, "Input");
        File outDir = new File(base, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rnd = new Random(2292026L);
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<List<Op>> all = new ArrayList<>();

        capacities.add(2);
        all.add(fixed(new int[][]{
            {1,1,1},{1,2,2},{2,1,0},{1,3,3},{2,2,0},
            {1,4,4},{2,1,0},{2,3,0},{2,4,0},{2,2,0}
        }));

        capacities.add(1);
        all.add(fixed(new int[][]{
            {1,1,1},{1,2,2},{2,1,0},{1,3,3},{2,2,0},{1,4,4},{2,3,0}
        }));

        capacities.add(3);
        all.add(fixed(new int[][]{
            {2,1,0},{1,1,10},{2,1,0},{1,1,20},{2,1,0},
            {1,2,30},{2,1,0},{2,2,0}
        }));

        capacities.add(2);
        all.add(fixed(new int[][]{
            {1,1,10},{1,2,20},{2,1,0},{1,3,30},{2,2,0},{2,1,0}
        }));

        capacities.add(4);
        all.add(fixed(new int[][]{
            {2,5,0},{2,0,0},{2,9999,0},{1,9999,100000},
            {2,9999,0},{1,0,0},{2,0,0}
        }));

        for (int c = all.size(); c < 30; c++) {
            int cap = 1 + rnd.nextInt(10);
            int q = 10 + rnd.nextInt(90);
            List<Op> ops = new ArrayList<>();
            for (int i = 0; i < q; i++) {
                if (rnd.nextInt(100) < 60) {
                    ops.add(new Op(1, rnd.nextInt(20), rnd.nextInt(100001)));
                } else {
                    ops.add(new Op(2, rnd.nextInt(20), 0));
                }
            }
            capacities.add(cap);
            all.add(ops);
        }

        int[] sizes = {100, 250, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};
        while (all.size() < 100) {
            int q = sizes[(all.size() - 30) % sizes.length];
            int cap;
            int pattern = (all.size() - 30) % 5;

            if (pattern == 0) cap = 1;
            else if (pattern == 1) cap = 2;
            else if (pattern == 2) cap = Math.max(1, q / 10);
            else if (pattern == 3) cap = Math.max(1, q / 2);
            else cap = Math.min(3000, Math.max(1, q));

            List<Op> ops = new ArrayList<>(q);

            for (int i = 0; i < q; i++) {
                int key;
                if (pattern == 0) key = i % 2;
                else if (pattern == 1) key = i % 5;
                else key = rnd.nextInt(Math.min(10001, Math.max(10, cap * 2)));

                if (i % 7 == 0 || rnd.nextInt(100) < 62) {
                    int value = rnd.nextInt(100001);
                    ops.add(new Op(1, key, value));
                } else {
                    ops.add(new Op(2, key, 0));
                }
            }

            capacities.add(cap);
            all.add(ops);
        }

        for (int i = 0; i < 100; i++) {
            writeCase(inDir, outDir, i, capacities.get(i), all.get(i));
        }
    }
}
