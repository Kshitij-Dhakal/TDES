package des;

class BinaryException extends Exception {

    /**
     * Any exceptions in Binary Class
     *
     * @param message Exception Message
     */
    public BinaryException(String message) {
        super(message);
    }
}

public class Binary {
    /**
     * Convert given text into binary without creating any binary object
     *
     * @param message String that will be converted to binary
     * @return (String) Binary of the message passed as argument
     */
    public static String stringToBinary(String message) {
        byte[] bytes = message.getBytes();
        String binary = "";
        for (byte b : bytes) {
            binary += String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
            binary += " ";
        }
        return binary;
    }

    /**
     * Convert binary into string without creating binary object
     *
     * @param binary String of binary to be converted into String
     * @return String after converting binary passed as argument
     */
    public static String binarytoString(String binary) {
        String string = "";
        String[] binaryList = binary.split(" ");
        for (String s : binaryList) {
            char c = (char) Integer.parseInt(s, 2);
            string += c;

        }
        return string;
    }

    /**
     * Move each bit one place to the left, except for the first bit, which is
     * cycled to the end of the block.
     *
     * @param binaryKeys 8 bit binary stream to be left shifted.
     * @return 8 bit binary stream obtained after left shifted.
     */
    private static String leftShift(String binaryKeys) {
        String ret = "";
        ret += binaryKeys.substring(1);
        ret += binaryKeys.charAt(0);
        return ret;
    }

    /**
     * Move each bit n place to the left, except for the first bit, which is cycled
     * to the end of the block.
     *
     * @param binaryKeys Stream of binary keys
     * @param n          number of places to be shifted
     * @return binaryKeys after applying left shift by n places
     */
    public static String leftShift(String binaryKeys, int n) {
        binaryKeys = binaryKeys.replaceAll(" ", "");
        for (int i = 0; i < n; i++) {
            binaryKeys = leftShift(binaryKeys);
        }
        String ret = "";
        for (int i = 0; i < binaryKeys.length(); i++) {
            ret += binaryKeys.charAt(i);
            if ((i + 1) % 8 == 0) {
                ret += " ";
            }
        }
        return ret;
    }

    public static String binaryToHex(String binary) {
        String[] split = binary.split(" ");
        String hex = "";
        for (String string : split) {
            hex += Integer.toHexString(Integer.parseInt(string, 0, 4, 2));
            hex += Integer.toHexString(Integer.parseInt(string, 4, 8, 2));
        }

        return hex.toUpperCase();
    }

    public static String hexToBinary(String hex) {
        String binary = "";
        for (int i = 0; i < hex.length(); i++) {
            binary += String
                    .format("%4s", Integer.toBinaryString(Integer.parseInt(Character.toString(hex.charAt(i)), 16)))
                    .replace(" ", "0");
            if (i % 2 == 1) {
                binary += " ";
            }
        }
        return binary;
    }

    public static String XOR(String lm, String rm) {
        // Test Complete
        /*
         * if (lm.length() != rm.length()) { throw new
         * BinaryException("XOR operators must be of same lenghts"); }
         */
        String ret = "";
        lm = lm.replaceAll(" ", "");
        rm = rm.replaceAll(" ", "");
        for (int i = 0; i < lm.length(); i++) {
            if (lm.charAt(i) == rm.charAt(i)) {
                ret += "0";
            } else {
                ret += "1";
            }
            if ((i + 1) % 6 == 0) {
                ret += " ";
            }
        }
        return ret;
    }

}
