package com.eyesee.algorithms.leetcode;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code WordSearch79} class represents .
 *
 * @author jessepi on 11/22/18
 */
public class WordSearch79 {

    @Test
    public void testExist() {
        char[][] borad = {{'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'}};
        String test1 = "ABCCED";
        assertThat(exist(borad, test1)).isTrue();

        String test2 = "SEE";
        assertThat(exist(borad, test2)).isTrue();

        String test3 = "ABCB";
        assertThat(exist(borad, test3)).isFalse();

        char[][] borad1 = {{'a','a','a','a'},{'a','a','a', 'a'},{'a','a','a','a'}};
        assertThat(exist(borad1, "aaaaaaaaaaaaa")).isFalse();

        char[][] borad2 = {{'A','B','C','E'},{'S','F','E', 'S'},{'A','D','E','E'}};
        assertThat(exist(borad2, "ABCEFSADEESE")).isTrue();
    }

//      ['A','A','A','A'],
//      ['A','A','A','A'],
//      ['A','A','A','A']
    public boolean exist(char[][] board, String word) {
        if (null == board || null == word || word.length() == 0) {
            return false;
        }

        if (word.length() > board.length * board[0].length) {
            return false;
        }

        int v = board.length;
        int h = board[0].length;

        boolean[][] visit = new boolean[v][h];
        int index = 0;
        for (int vo = 0; vo < v; vo ++) {
            for (int ho = 0; ho < h; ho ++) {
                if (compareSubString(board, v, h, vo , ho, word, visit, index)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean compareSubString(char[][] board,
                                     int v, int h ,
                                     int i , int j, String word, boolean visit[][], int index) {
        if ( i < 0 || j >= board[0].length || i >= board.length || j < 0 || visit[i][j] || board[i][j] != word.charAt(index)) {
            return false;
        }

        visit[i][j] = true;
        if (index + 1 == word.length()) {
            return true;
        }

        boolean result = false;
        if (compareSubString(board, v, h, i - 1, j, word, visit, index + 1) ||
                compareSubString(board, v, h, i, j + 1, word, visit, index + 1)
                || compareSubString(board, v, h, i + 1, j, word, visit, index + 1)
                || compareSubString(board, v, h, i, j - 1, word, visit, index + 1)
        ) {
            return true;
        }

        if (!result) {
            visit[i][j] = false;
        }
        return result;
    }


//    boolean[][] visited;
//    public boolean exist(char[][] board, String word) {
//        visited = new boolean[board.length][board[0].length];
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                if (word.charAt(0) == board[i][j] && search(board, word, i, j, 0)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean search(char[][] board, String word, int i, int j, int index) {
//        if (index == word.length()) return true;
//
//        if (i >= board.length || i < 0 || j >= board[i].length || j < 0 || board[i][j] != word.charAt(index) || visited[i][j]) {
//            return false;
//        }
//        visited[i][j] = true;
//        if (search(board, word, i + 1, j, index + 1) || search(board, word, i - 1, j, index + 1) ||
//                search(board, word, i, j + 1, index + 1) || search(board, word, i, j - 1, index + 1)) {
//            return true;
//        }
//        visited[i][j] = false;
//        return false;
//    }
}


//["A","B","C","E"]
//["S","F","E","S"],
//["A","D","E","E"]
// "ABCE FSAD EESE"