package des;

import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {
    DiffieHellman dh;
    BigInteger key;

    public KeyGenerator() {
        //TODO a user have only one private key for all connections. Check if this is less secure than having different private key for all connections.
        dh = new DiffieHellman();
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

    static String[] generateBinaryKey(BigInteger integer) {
        String key = "";
        if (integer.bitLength() <= 128) {
            key = String.format("%128s", integer.toString(2)).replace(' ', '0');
        }
//        System.out.println(key);
        return new String[]{key.substring(0, 64), key.substring(64, 128)};
    }

    public static void main(String[] args) {
        KeyGenerator alice = new KeyGenerator();
        KeyGenerator bob = new KeyGenerator();
        String Ka = alice.initializeDHKeyExchange();
        String Kb = bob.initializeDHKeyExchange();
        alice.receive(Kb);
        bob.receive(Ka);
        System.out.println("Key received by Alice : " + alice.key);
        System.out.println("Key received by Alice : " + bob.key);
        System.out.println(alice.key.bitLength());


    }

    public BigInteger getKey() {
        return key.mod((new BigInteger("2")).pow(128));
    }

    public String initializeDHKeyExchange() {
        return dh.G.pow(dh.Xa).mod(dh.P).toString(16);
    }

    public void receive(String Ka) {
        this.key = (new BigInteger(Ka, 16)).pow(dh.Xa).mod(dh.P).mod((new BigInteger("2")).pow(128));
    }
}
