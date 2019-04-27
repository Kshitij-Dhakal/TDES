package com.DES;

import java.util.Random;

public class KeyGenerator {
	public static void main(String[] args) {
		String key = getRandomKey();
		System.out.println(key);
//		System.out.println(Integer.toBinaryString(127));
	}

	public static String getRandomKey() {
		String key = "";
		for (int i = 0; i < 8; i++) {
			Random rand = new Random();
			String temp = String.format("%7s", Integer.toBinaryString(rand.nextInt(128))).replace(' ', '0');
			int count = 0;
			for (int j = 0; j < temp.length(); j++) {
				if (temp.charAt(j) == '1') {
					count++;
				}
			}
			if (count % 2 == 0) {
				key += temp + "1 ";
			} else {
				key += temp + "0 ";
			}
		}
		return key;
	}
}
