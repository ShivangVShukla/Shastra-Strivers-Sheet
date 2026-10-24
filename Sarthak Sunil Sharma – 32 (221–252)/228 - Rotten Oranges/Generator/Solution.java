import java.io.*;
import java.util.*;

public class Solution {
    static class Cell {
        int r, c, t;
        Cell(int r, int c, int t) {
            this.r = r;
            this.c = c;
            this.t = t;
        }
    }

    static int solve(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        Queue<Cell> q = new ArrayDeque<>();
        int fresh = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) q.offer(new Cell(i, j, 0));
                else if (grid[i][j] == 1) fresh++;
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int ans = 0;

        while (!q.isEmpty()) {
            Cell cur = q.poll();
            ans = Math.max(ans, cur.t);

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    fresh--;
                    q.offer(new Cell(nr, nc, cur.t + 1));
                }
            }
        }

        return fresh == 0 ? ans : -1;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] grid = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(solve(grid));
    }
}
