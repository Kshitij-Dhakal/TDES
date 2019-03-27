package com.DES;

public class Des {
    private static final int[] IP = {58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    /**
     * Round Function F
     *
     * @param r   right half of the message
     * @param key key
     */
    public static String roundFunction(String r, String key) {
        //TODO Round Function
        return null;
    }

    public static String initialPermutation(String message) {
        String ret = "";
        message = message.replaceAll(" ", "");
        for (int i = 0; i < 64; i++) {
            ret += message.charAt(IP[i] - 1);
            if ((i + 1) % 8 == 0) {
                ret += " ";
            }
        }
        return ret;
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
        String m = "0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111";
        m = initialPermutation(m);
        String[] L = new String[17];
        String[] R = new String[17];
        L[0] = m.substring(0, m.length() / 2 - 1);
        R[0] = m.substring(m.length() / 2);
        System.out.println(L[0]);
        System.out.println(R[0]);
        for (int i = 1; i < 17; i++) {
            L[i] = R[i - 1];
//            R[i]= Binary.XOR(L[i-1]
        }

/*
        for (int i = 0; i < 16; i++) {
            System.out.println("K" + (i + 1) + "\t=\t" + keys[i]);
        }
*/
    }

}
