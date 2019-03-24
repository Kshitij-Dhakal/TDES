package com.DES;

public class Des {

    public static void roundFunction(String r, String key) {
        String s = expansionPermutationBox(r);
    }

    public static void xor() {

    }

    public static void initialPermutation() {
        int[][] k = {{58, 50, 42, 34, 26, 18, 10, 2},
                {60, 52, 44, 36, 28, 20, 12, 4},
                {62, 54, 46, 38, 30, 22, 14, 6},
                {64, 56, 48, 40, 32, 24, 16, 8},
                {57, 49, 41, 33, 25, 17, 9, 1},
                {59, 51, 43, 35, 27, 19, 11, 3},
                {61, 53, 45, 37, 29, 21, 13, 5},
                {63, 55, 47, 39, 31, 23, 15, 7}};


    }

    public static void finalPermutation() {

    }

    public static void swap() {

    }

    public static String expansionPermutationBox(String m) {
        //TODO add expansionPermutation functionality
        return null;
    }

    public static void substitutionBox() {

    }

    public static void main(String[] args) {
        String[] keys = KeyExpansion.expandKeys("00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001");
/*
        for (int i = 0; i < 16; i++) {
            System.out.println("K" + (i + 1) + "\t=\t" + keys[i]);
        }
*/
    }

}
