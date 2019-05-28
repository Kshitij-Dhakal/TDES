package des;

/**
 * Helper class for DES
 *
 * @author dhaka
 */
class KeyExpansion {
    private static final int[] leftShift = {0, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    // Index of pc1 begins at 1 and end at 64
    // Each number in pc1 indicates the index of new bit in keys which will replace
    // current bit.
    private static final int[][] pc1 = {{57, 49, 41, 33, 25, 17, 9}, {1, 58, 50, 42, 34, 26, 18},
            {10, 2, 59, 51, 43, 35, 27}, {19, 11, 3, 60, 52, 44, 36}, {63, 55, 47, 39, 31, 23, 15},
            {7, 62, 54, 46, 38, 30, 22}, {14, 6, 61, 53, 45, 37, 29}, {21, 13, 5, 28, 20, 12, 4}};
    private static final int[] pc2 = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27,
            20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29,
            32};

    /*
     * private static final int[][] pc2 = {{14, 17, 11, 24, 1, 5}, {3, 28, 15, 6,
     * 21, 10}, {23, 19, 12, 4, 26, 8}, {16, 7, 27, 20, 13, 2}, {41, 52, 31, 37, 47,
     * 55}, {30, 40, 51, 45, 33, 48}, {44, 49, 39, 56, 34, 53}, {46, 42, 50, 36, 29,
     * 32}};
     */
    private static String[] expandedKeys = new String[16];

    public static String[] expandKeys(String binaryKeys) {
//	        checkPC1();
//	        binaryKeys = "00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001";
        String[] C = new String[17];
        String[] D = new String[17];
        binaryKeys = permutedChoice1(binaryKeys);
        C[0] = binaryKeys.substring(0, binaryKeys.length() / 2);
        // D[0] should take substring(binaryKeys.length()/2+1) but will throw error and
        // is working correctly as it is
        // this works because the binary stream is space separated
        D[0] = binaryKeys.substring(binaryKeys.length() / 2);
        for (int i = 1; i < 17; i++) {
            C[i] = Binary.leftShift(C[i - 1], leftShift[i]);
            D[i] = Binary.leftShift(D[i - 1], leftShift[i]);
        }
        permutedChoice2(C, D);
        /*
         * for (int i = 0; i < 16; i++) { System.out.println("K" + (i + 1) + "\t=\t" +
         * expandedKeys[i]); }
         */
        return expandedKeys;
    }

    private static void permutedChoice2(String[] c, String[] d) {
        for (int i = 0; i < 16; i++) {
            // C0 and D0 are initial values and therefore aren't used
            String tempKey = (c[i + 1] + d[i + 1]).replaceAll(" ", "");
            String val = "";
            for (int j = 0; j < pc2.length; j++) {
                val += tempKey.charAt(pc2[j] - 1);
            }
            expandedKeys[i] = val;
        }
    }

    /*
     * private static void checkPC1() { String binaryKeys =
     * "00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001";
     * binaryKeys = permutedChoice1(binaryKeys); //check if PC1 is working correctly
     * System.out.
     * println("1111000 0110011 0010101 0101111 0101010 1011001 1001111 0001111");
     * System.out.println(binaryKeys); System.out.println(binaryKeys.
     * equals("1111000 0110011 0010101 0101111 0101010 1011001 1001111 0001111")); }
     */

    /*
     * public static String[] permutedChoice1(String[] keys) { //Index of pc1 begins
     * at 1 and end at 64 int[][] pc1 = {{57, 49, 41, 33, 25, 17, 9}, {1, 58, 50,
     * 42, 34, 26, 18}, {10, 2, 59, 51, 43, 35, 27}, {19, 11, 3, 60, 52, 44, 36},
     * {63, 55, 47, 39, 31, 23, 15}, {7, 62, 54, 46, 38, 30, 22}, {14, 6, 61, 53,
     * 45, 37, 29}, {21, 13, 5, 28, 20, 12, 4}}; String ret = ""; for (int i = 0; i
     * < 8; i++) { for (int j = 0; j < 7; j++) { int newi = (pc1[i][j]-1) / 8; int
     * newj = (pc1[i][j]-1) % 8; ret += keys[newi].charAt(newj); } ret += " "; } ret
     * = ret.substring(0,ret.length()-1); return ret.split(" "); }
     */

    /**
     * PC1 according to DES standards
     *
     * @param keys Binary stream of keys of 64 bit length
     * @return new stream of binary keys after applying PC1 of 56 bit length
     */
    private static String permutedChoice1(String keys) {
        String ret = "";
        keys = keys.replaceAll(" ", "");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                ret += keys.charAt(pc1[i][j] - 1);
            }
            ret += " ";
        }
//	        return ret.substring(0,ret.length()-1);
        return ret;
    }

}
