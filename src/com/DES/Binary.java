package com.DES;

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
        for (byte b :
                bytes) {
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
        for (String s :
                binaryList) {
            char c = (char) Integer.parseInt(s, 2);
            string += c;

        }
        return string;
    }

}
