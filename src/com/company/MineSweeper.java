package com.company;

import java.util.*;

public class MineSweeper {

    private int n, m;
    private int count;
    private int[][] board;
    private int[][] visited;
    private Set<Point> bombs;

    private int isBomb = -1;
    private int[] dx = new int[]{-1, -1, 1, 1, 0, 0, -1, 1};
    private int[] dy = new int[]{-1, 1, -1, 1, -1, 1, 0, 0};

    public MineSweeper(int n, int m, int count) {
        this.n = n;
        this.m = m;
        this.count = count;
        board = new int[n][m];
        visited = new int[n][m];
        bombs = new HashSet<>();
    }

    public void setBombs(Point init) {

        Random random = new Random();

        int i = 0;
        while (i < count) {
            int x = random.nextInt(n);
            int y = random.nextInt(m);
            Point next = new Point(x, y);

            if (bombs.contains(next)) {
                continue;
            }

            if (isClose(init, next)) {
                continue;
            }

            bombs.add(next);
            board[x][y] = isBomb;
            i++;

        }
    }

    private boolean isClose(Point a, Point b) {

        if (Math.abs(a.x - b. x) < 3 && Math.abs(a.y - b.y) < 3) {
            return true;
        }

        return false;
    }

    public void buildBoard() {

        for (Point p : bombs) {
            for (int i = 0; i < 8; ++i) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && board[nx][ny] != isBomb) {
                    board[nx][ny]++;
                }
            }
        }
    }

    public boolean playGame(Point pos, int action) {
        //If action == -1, right click, mark pos as bomb or remove the mark on pos
        if (action == -1) {
            if (visited[pos.x][pos.y] == 0) {
                visited[pos.x][pos.y] = -1;
            } else if (visited[pos.x][pos.y] == -1) {
                visited[pos.x][pos.y] = 0;
            }

            return true;
        }

        //If action == 1, left click
        if (action == 1) {
            //Hit the bomb, game over!
            if (board[pos.x][pos.y] == isBomb) {
                System.out.println("Hit the bomb! Game Over!");
                return false;
            }

            visited[pos.x][pos.y] = 1;
            //Find a number, return
            if (board[pos.x][pos.y] > 0) {
                return true;
            }

            //Find a space, bfs
            bfs(pos);
        }
        return true;
    }

    private void bfs(Point p) {
        Queue<Point> q = new LinkedList<>();
        q.offer(p);

        while (!q.isEmpty()) {
            Point curt = q.poll();
            for (int i = 0; i < 8; ++i) {
                int nx = curt.x + dx[i];
                int ny = curt.y + dy[i];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && visited[nx][ny] == 0) {
                    visited[nx][ny] = 1;
                    if (board[nx][ny] == 0) {
                        q.offer(new Point(nx, ny));
                    }
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (visited[i][j] == 0) {
                    System.out.printf(" +");
                } else if (visited[i][j] == -1) {
                    System.out.printf(" B");
                } else {
                    System.out.printf(" " + board[i][j]);
                }
            }
            System.out.println();
        }
    }

    public void showBoard() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (visited[i][j] == 1) {
                    System.out.printf(" " + board[i][j]);
                } else if (visited[i][j] == -1 || board[i][j] == isBomb) {
                    System.out.printf(" B");
                } else {
                    System.out.printf(" +");
                }
            }
            System.out.println();
        }
    }
}
