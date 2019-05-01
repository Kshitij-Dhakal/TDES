package com.DES;

import java.util.ArrayList;
import java.util.List;

public class Des {
	// All the following constants for permutation have index starting from 1..n
	// instead of 0..n-1
	// So when using these constants there should be -1 added to them
	// Except for S[0..7]
	private static final int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30,
			22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61,
			53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
	private static final int[] E = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17,
			16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
	private static final int[][][] S = {
			{ { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
					{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
					{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
					{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
			{ { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
					{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
					{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
					{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
			{ { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
					{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
					{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
					{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
			{ { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
					{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
					{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
					{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
			{ { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
					{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
					{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
					{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
			{ { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
					{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
					{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
					{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
			{ { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
					{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
					{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
					{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
			{ { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
					{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
					{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
					{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };
	private static final int[] P = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27,
			3, 9, 19, 13, 30, 6, 22, 11, 4, 25 };
	private static final int[] F = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54,
			22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34,
			2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25 };

	/**
	 * Round Function F
	 *
	 * @param r   right half of the message
	 * @param key key
	 */
	private static String roundFunction(String r, String key) {
		// Test Complete
		// apply expansion permutation of round function
		r = applyPermutation(r, E, PERMUTATION.E);
		String xor = Binary.XOR(r, key);
		String[] retArr = xor.split(" ");
		for (int i = 0; i < retArr.length; i++) {
			retArr[i] = substitutionBox(retArr[i], i);
		}
		String ret = "";
		for (String s : retArr) {
			ret += s;
		}
		// apply permutation box of round function
		return applyPermutation(ret, P, PERMUTATION.P);
	}

	/**
	 * The input message is permuted according to the permutationTable.
	 *
	 * @param message          Input bits
	 * @param permutationTable Permutation Table
	 * @param spacing          (misc) For adding spacing at regular interval in
	 *                         return value. -1 if no spacing required.
	 * @return the value obtained after permuting message using permutationTable
	 */
	private static String applyPermutation(String message, int[] permutationTable, PERMUTATION spacing) {
		String ret = "";
		message = message.replaceAll(" ", "");
		for (int i = 0; i < permutationTable.length; i++) {
			ret += message.charAt(permutationTable[i] - 1);
			if (spacing.numValue != -1) {
				if ((i + 1) % spacing.numValue == 0) {
					ret += " ";
				}
			}
		}
		return ret;
	}

	/**
	 * Substitution Box S[0..7] used in round function f
	 *
	 * @param location 6 bits where first and last bit represents row in
	 *                 substitution table and all the remaining bits represents
	 *                 column in substitution table.
	 * @param i        Represents which substitution box will be used
	 * @return 4 bits obtained after substitution
	 */
	private static String substitutionBox(String location, int i) {
		// Test completed
		String rowS = "";
		rowS += location.charAt(0) + "" + location.charAt(5);
		String colS = "";
		colS += location.substring(1, 5);
		// converting rowS->row and colS->col (Binary to Integer)
		int row = Integer.parseInt(rowS, 2);
		int col = Integer.parseInt(colS, 2);
		return String.format("%4s", Integer.toBinaryString(S[i][row][col])).replace(' ', '0');
	}

	/*
	 * private static void performEncryptCheck(PERFORM perform, String m, String
	 * key) { String ret = encrypt(perform, m, key); System.out.println("Encrypt");
	 * System.out.
	 * println("Expected :\t10000101 11101000 00010011 01010100 00001111 00001010 10110100 00000101"
	 * ); System.out.println("Result :\t" + ret); perform = PERFORM.DECRYPTION; ret
	 * = encrypt(perform, ret, key); System.out.println("Decrypt"); System.out.
	 * println("Expected :\t00000001 00100011 01000101 01100111 10001001 10101011 11001101 11101111"
	 * ); System.out.println("Result :\t" + ret); }
	 */

	
	/*
	 * public static void main(String[] args) { String message; String[] tdesKey = {
	 * KeyGenerator.getRandomKey(), KeyGenerator.getRandomKey() }; String key =
	 * "00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001";
	 * message = "Hello World";
	 * 
	 * { String enc = encrypt(tdesKey, message); System.out.println("Encrypted :\t"
	 * + Binary.binaryToHex(enc)); String dec = decrypt(tdesKey, enc);
	 * System.out.println("Original :\t" +
	 * Binary.binaryToHex(Binary.stringToBinary(message)));
	 * System.out.println("Decrypted :\t" + Binary.binaryToHex(dec));
	 * System.out.println("Decrypted :\t" +
	 * Binary.binarytoString(Binary.hexToBinary(Binary.binaryToHex(dec))));
	 * System.out.println("Message :\t" + Binary.binarytoString(dec).trim()); }
	 * 
	 * }
	 */
	 

	/**
	 * Use TDES algorithm to decrypt message using key[2]
	 * 
	 * @param key
	 * @param message
	 * @return decrypted string
	 */
	public static String decrypt(String[] key, String message) {
		String dec = "";
		for (String s : MessagePadding.split64(message)) {
			dec += tdes(PERFORM.DECRYPTION, s, key) + " ";
		}
		return dec;
	}

	/**
	 * Use TDES algorithm to encrypt message using key[2]
	 * 
	 * @param key
	 * @param message
	 * @return encrypted string
	 */
	public static String encrypt(String[] key, String message) {
		String enc = "";
		for (String s : MessagePadding.addPadding64(message)) {
			enc += tdes(PERFORM.ENCRYPTION, s, key) + " ";
		}
		return enc;
	}

	/**
	 * Triple-DES is just DES with two 64-bit keys applied. Given a plaintext
	 * message, the first key is used to DES- encrypt the message. The second key is
	 * used to DES-decrypt the encrypted message. (Since the second key is not the
	 * right key, this decryption just scrambles the data further.) The
	 * twice-scrambled message is then encrypted again with the first key to yield
	 * the final ciphertext. This three-step procedure is called triple-DES.
	 * 
	 * @param perform
	 * @param message
	 * @param tdesKey
	 * @return encrypted text
	 */
	private static String tdes(PERFORM perform, String message, String[] tdesKey) {
		String encryptedM = message;
		if (perform == PERFORM.ENCRYPTION) {
//			System.out.println("Encrypting");
			encryptedM = des(PERFORM.ENCRYPTION, encryptedM, tdesKey[0]);
			encryptedM = des(PERFORM.DECRYPTION, encryptedM, tdesKey[1]);
			encryptedM = des(PERFORM.ENCRYPTION, encryptedM, tdesKey[0]);
		} else {
//			System.out.println("Decrypting");
			encryptedM = des(PERFORM.DECRYPTION, encryptedM, tdesKey[0]);
			encryptedM = des(PERFORM.ENCRYPTION, encryptedM, tdesKey[1]);
			encryptedM = des(PERFORM.DECRYPTION, encryptedM, tdesKey[0]);

		}
		return encryptedM;
	}

	/**
	 * Perform Encryption or Decryption
	 *
	 * @param perform   Specifies whether to perform encryption or decryption
	 * @param message   message
	 * @param binaryKey 64 bit Binary Key
	 * @return Encrypted or Decrypted message using provided keys
	 */
	private static String des(PERFORM perform, String message, String binaryKey) {
		// Encryption Function is independent of whether message is separated every 8
		// bits by white space
		// Applying Initial Permutation
		String[] keys = KeyExpansion.expandKeys(binaryKey);
		message = applyPermutation(message, IP, PERMUTATION.IP);
		String L = "";
		String R = "";
		L = message.substring(0, message.length() / 2 - 1).replace(" ", "");
		R = message.substring(message.length() / 2).replace(" ", "");
		switch (perform) {
		case ENCRYPTION:
			for (int i = 0; i < 16; i++) {
				String temp = L;
				L = R;
				R = Binary.XOR(temp, roundFunction(R, keys[i])).replaceAll(" ", "");
			}
			break;
		case DECRYPTION:
			for (int i = 0; i < 16; i++) {
				String temp = L;
				L = R;
				R = Binary.XOR(temp, roundFunction(R, keys[16 - i - 1])).replaceAll(" ", "");
			}
			break;
		}

		String ret = R + L;
		ret = applyPermutation(ret, F, PERMUTATION.F);
		return ret.trim();
	}

	/**
	 * This is lookup variables for applyPermutation function
	 */
	private enum PERMUTATION {
		IP(8), E(6), P(-1), F(8);

		private int numValue;

		PERMUTATION(int i) {
			this.numValue = i;
		}
	}

	private enum PERFORM {
		ENCRYPTION, DECRYPTION
	}

	/**
	 * Helper Class for DES.
	 * 
	 * @author dhaka
	 *
	 */
	private static class MessagePadding {

		/*
		 * public static void main(String[] args) {
		 * 
		 * String message = "Sweet but a psycho"; List<String> paddedMessage =
		 * addPadding64(message); for (String s : paddedMessage) {
		 * System.out.println(s); }
		 * 
		 * System.out.println(Binary.binarytoString(binaryMessage).trim()); }
		 */

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
//			System.out.println(message);
			String binaryMessage = Binary.stringToBinary(message);
			while ((binaryMessage.replace(" ", "")).length() % 64 != 0) {
				binaryMessage += "0";
				if ((binaryMessage.replace(" ", "")).length() % 8 == 0
						&& (binaryMessage.replace(" ", "")).length() % 64 != 0)
					binaryMessage += " ";
//				System.out.println(binaryMessage);
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
//				System.out.println(byteString);
				foo += byteString + " ";
//				System.out.print(foo);
//				System.out.println(count);
				if ((++count) == 8) {
					paddedMessage.add(foo.trim());
//					System.out.println();
					count = 0;
					foo = "";
				}
			}
//			System.out.println("end");
			return paddedMessage;
		}

	}

	/**
	 * Helper class for DES
	 * 
	 * @author dhaka
	 *
	 */
	private static class KeyExpansion {
		private static final int[] leftShift = { 0, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
		// Index of pc1 begins at 1 and end at 64
		// Each number in pc1 indicates the index of new bit in keys which will replace
		// current bit.
		private static final int[][] pc1 = { { 57, 49, 41, 33, 25, 17, 9 }, { 1, 58, 50, 42, 34, 26, 18 },
				{ 10, 2, 59, 51, 43, 35, 27 }, { 19, 11, 3, 60, 52, 44, 36 }, { 63, 55, 47, 39, 31, 23, 15 },
				{ 7, 62, 54, 46, 38, 30, 22 }, { 14, 6, 61, 53, 45, 37, 29 }, { 21, 13, 5, 28, 20, 12, 4 } };
		private static final int[] pc2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27,
				20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29,
				32 };

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

}
