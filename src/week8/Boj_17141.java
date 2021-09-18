package week8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class Boj_17141 {

    static int N, M , answer;
    static int[][] map;
    static LinkedList<Cell_> candi = new LinkedList<>();
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static int wall = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] cmd = br.readLine().split(" ");
        N = Integer.parseInt(cmd[0]);
        M = Integer.parseInt(cmd[1]);
        answer = Integer.MAX_VALUE;

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            String[] str = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(str[j]);
                if (map[i][j] == 2) {
                    candi.add(new Cell_(i, j, 0));
                } else if (map[i][j] == 1) {
                    wall++;
                }
            }
        }

        // 벽3개 뽑기
        dfs(0, new LinkedList<Integer>());

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    static void dfs(int idx, LinkedList<Integer> l) {
        if (l.size() == M) {
            answer = Math.min(answer, bfs(l));
            return;
        }

        for (int i = idx; i < candi.size(); i++) {
            if (l.contains(i)) continue;
            l.add(i);
            dfs(i, l);
            l.removeLast();
        }
    }

    static int bfs(LinkedList<Integer> l) {
        int cnt = N*N - wall;
        int result = 0;

        Queue<Cell_> q = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        for (int e : l) {
            Cell_ virus = candi.get(e);
            q.add(virus);
            visit[virus.x][virus.y] = true;
            cnt--;
        }

        while (!q.isEmpty()) {
            Cell_ cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int tx = cur.x + dx[i];
                int ty = cur.y + dy[i];

                if (isRange(tx, ty)) {
                    if (map[tx][ty] != 1 && !visit[tx][ty]) {
                        cnt--;
                        q.add(new Cell_(tx, ty, cur.d + 1));
                        visit[tx][ty] = true;
                        result = cur.d + 1;
                    }
                }
            }
        }

        return cnt == 0 ? result : Integer.MAX_VALUE;
    }

    static boolean isRange(int x, int y) {
        return (x<0 || y<0 || x>=N || y>=N) ? false : true;
    }
}

/*class Cell_ {
    int x;
    int y;
    int d;

    public Cell_(int x, int y, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
    }
}*/