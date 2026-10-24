import java.io.*;
import java.util.*;

public class gen {
    static class Cell {
        int r, c;
        Cell(int r, int c) { this.r = r; this.c = c; }
    }

    static int solve(int[][] a) {
        int n = a.length, m = a[0].length;
        int fresh = 0, time = 0;
        Queue<Cell> q = new ArrayDeque<>();
        int[][] dist = new int[n][m];
        for (int[] row : dist) Arrays.fill(row, -1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 2) {
                    q.offer(new Cell(i, j));
                    dist[i][j] = 0;
                } else if (a[i][j] == 1) fresh++;
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!q.isEmpty()) {
            Cell cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d], nc = cur.c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m &&
                    a[nr][nc] == 1 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[cur.r][cur.c] + 1;
                    time = Math.max(time, dist[nr][nc]);
                    fresh--;
                    q.offer(new Cell(nr, nc));
                }
            }
        }
        return fresh == 0 ? time : -1;
    }

    static void writeCase(int idx, int[][] grid) throws Exception {
        String inName = String.format("../Testcases/Input/input%02d.txt", idx);
        String outName = String.format("../Testcases/Output/output%02d.txt", idx);

        try (PrintWriter pw = new PrintWriter(new FileWriter(inName))) {
            int n = grid.length, m = grid[0].length;
            pw.println(n + " " + m);
            for (int[] row : grid) {
                for (int j = 0; j < m; j++) {
                    if (j > 0) pw.print(" ");
                    pw.print(row[j]);
                }
                pw.println();
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(outName))) {
            pw.println(solve(grid));
        }
    }

    static int[][] copy(int[][] a) {
        int[][] b = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) b[i] = a[i].clone();
        return b;
    }

    public static void main(String[] args) throws Exception {
        Random rnd = new Random(2282026L);
        List<int[][]> cases = new ArrayList<>();

        cases.add(new int[][]{{0}});
        cases.add(new int[][]{{1}});
        cases.add(new int[][]{{2}});
        cases.add(new int[][]{{0,1,2}});
        cases.add(new int[][]{{2,1,1},{0,1,1},{1,0,1}});
        cases.add(new int[][]{{2,1,1},{1,1,0},{0,1,1}});
        cases.add(new int[][]{{1,1},{1,1}});
        cases.add(new int[][]{{2,2},{2,2}});
        cases.add(new int[][]{{2,0,1},{0,0,1},{1,1,1}});
        cases.add(new int[][]{{1,2,1},{1,1,1},{1,1,2}});

        while (cases.size() < 100) {
            int n, m;
            if (cases.size() < 35) {
                n = 1 + rnd.nextInt(8);
                m = 1 + rnd.nextInt(8);
            } else if (cases.size() < 90) {
                n = 5 + rnd.nextInt(25);
                m = 5 + rnd.nextInt(25);
            } else {
                n = 40 + rnd.nextInt(61);
                m = 40 + rnd.nextInt(61);
            }

            int[][] g = new int[n][m];
            int mode = cases.size() % 5;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int x;
                    if (mode == 0) x = rnd.nextInt(100) < 15 ? 2 : (rnd.nextInt(100) < 45 ? 1 : 0);
                    else if (mode == 1) x = rnd.nextInt(100) < 70 ? 1 : (rnd.nextInt(2) == 0 ? 0 : 2);
                    else if (mode == 2) x = rnd.nextInt(3);
                    else if (mode == 3) x = rnd.nextInt(100) < 80 ? 0 : (rnd.nextInt(2) == 0 ? 1 : 2);
                    else x = rnd.nextInt(100) < 90 ? 1 : 2;
                    g[i][j] = x;
                }
            }

            if (cases.size() % 7 == 0) g[0][0] = 2;
            cases.add(g);
        }

        for (int i = 0; i < 100; i++) {
            writeCase(i, cases.get(i));
        }
    }
}
