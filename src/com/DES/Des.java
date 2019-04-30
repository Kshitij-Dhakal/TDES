package com.DES;

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

	public static void main(String[] args) {
		PERFORM perform = PERFORM.ENCRYPTION;
		String message = "00000001 00100011 01000101 01100111 10001001 10101011 11001101 11101111";
		// Encryption Function is independent of whether message is separated every 8
		// bits by white space

		String[] tdesKey = { KeyGenerator.getRandomKey(), KeyGenerator.getRandomKey() };

		String key = "00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001";
		String enc = "", dec = "";
		message = "sweet but a psycho";
		System.out.println(Binary.stringToBinary(message));
		List<String> messageList = MessagePadding.addPadding64(message);
		for (String s : messageList) {
			enc += tdes(PERFORM.ENCRYPTION, s, tdesKey) + " ";
		}
		System.out.println(enc);
		for (String s : MessagePadding.split64(enc)) {
			dec += tdes(PERFORM.DECRYPTION, s, tdesKey) + " ";
		}
		System.out.println(dec);
		System.out.println(Binary.binarytoString(dec));

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
			encryptedM = encrypt(PERFORM.ENCRYPTION, encryptedM, tdesKey[0]);
			encryptedM = encrypt(PERFORM.DECRYPTION, encryptedM, tdesKey[1]);
			encryptedM = encrypt(PERFORM.ENCRYPTION, encryptedM, tdesKey[0]);
		} else {
//			System.out.println("Decrypting");
			encryptedM = encrypt(PERFORM.DECRYPTION, encryptedM, tdesKey[0]);
			encryptedM = encrypt(PERFORM.ENCRYPTION, encryptedM, tdesKey[1]);
			encryptedM = encrypt(PERFORM.DECRYPTION, encryptedM, tdesKey[0]);

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
	private static String encrypt(PERFORM perform, String message, String binaryKey) {
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

}
