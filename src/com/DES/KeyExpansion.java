package com.DES;

public class KeyExpansion {
    public static void main(String[] args) {
        String binaryKeys = "00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001";
        String[] keys = binaryKeys.split(" ");

        keys = permutedChoice1(keys);
        for (String s :
                keys) {
            System.out.printf("%s ",s);
            //Should print 1111000 0110011 0010101 0101111 0101010 1011001 1001111 0001111
            //for some reason prints out same output be when comparing gives false
        }
    }

    public static String[] permutedChoice1(String[] keys) {
        //Index of pc1 begins at 1 and end at 64
        int[][] pc1 = {{57, 49, 41, 33, 25, 17, 9},
                {1, 58, 50, 42, 34, 26, 18},
                {10, 2, 59, 51, 43, 35, 27},
                {19, 11, 3, 60, 52, 44, 36},
                {63, 55, 47, 39, 31, 23, 15},
                {7, 62, 54, 46, 38, 30, 22},
                {14, 6, 61, 53, 45, 37, 29},
                {21, 13, 5, 28, 20, 12, 4}};
        String ret = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                int newi = (pc1[i][j]-1) / 8;
                int newj = (pc1[i][j]-1) % 8;
                ret += keys[newi].charAt(newj);
            }
            ret += " ";
        }
        ret = ret.substring(0,ret.length()-1);
        return ret.split(" ");
    }

    public static String[] permutedChoice2(String[] keys) {
        //TODO PC2 functionality
        return null;
    }

}
