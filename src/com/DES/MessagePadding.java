package com.DES;

import java.util.ArrayList;
import java.util.List;

public class MessagePadding {

	public static void main(String[] args) {

		String message = "Sweet but a psycho";
		List<String> paddedMessage = addPadding64(message);
		for (String s : paddedMessage) {
			System.out.println(s);
		}

//		System.out.println(Binary.binarytoString(binaryMessage).trim());
	}

	/**
	 * @param message
	 * @return
	 */
	public static List<String> addPadding64(String message) {
		// If ever need arise to add header for message padding
		/*
		 * int length = message.length(); message = Integer.toString(message.length() %
		 * 8 + 1) + message;
		 */
//		System.out.println(message);
		String binaryMessage = Binary.stringToBinary(message);
		while ((binaryMessage.replace(" ", "")).length() % 64 != 0) {
			binaryMessage += "0";
			if ((binaryMessage.replace(" ", "")).length() % 8 == 0
					&& (binaryMessage.replace(" ", "")).length() % 64 != 0)
				binaryMessage += " ";
//			System.out.println(binaryMessage);
		}

		List<String> paddedMessage = split64(binaryMessage);
		return paddedMessage;
	}

	/**
	 * @param binaryMessage
	 * @return
	 */
	public static List<String> split64(String binaryMessage) {
		int count = 0;
		List<String> paddedMessage = new ArrayList<String>();
		String foo = "";
		for (String byteString : binaryMessage.split(" ")) {
//			System.out.println(byteString);
			foo += byteString + " ";
//			System.out.print(foo);
//			System.out.println(count);
			if ((++count) == 8) {
				paddedMessage.add(foo.trim());
//				System.out.println();
				count = 0;
				foo = "";
			}
		}
//		System.out.println("end");
		return paddedMessage;
	}

}
