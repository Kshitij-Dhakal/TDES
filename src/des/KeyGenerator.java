package des;

import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {
    DiffieHellman dh;
    BigInteger key;

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
        System.out.println("Ka :\t\t\t\t\t" + Ka);
        String Kb = bob.replyDHKey(Ka);
        System.out.println("Kb :\t\t\t\t\t" + Kb);
        alice.receiveDHKey(Kb);
        System.out.println("Alice's secret key :\t" + alice.getKey());
        System.out.println("Bob's secret key :\t\t" + bob.getKey());
//        System.out.println((new BigInteger("F518AA8781A8DF278ABA4E7D64B7CB9D49462353",16).bitLength()));

    }

    public BigInteger getKey() {
        return key.mod((new BigInteger("2")).pow(128));
    }

    /**
     * Alice initializes key exchange. She sends Bob Ka=g^Xa mod P.
     *
     * @return
     */
    public String initializeDHKeyExchange() {
        dh = new DiffieHellman();
        return dh.G.pow(dh.Xa).mod(dh.P).toString(16);
    }

    /**
     * When Bob recieves Ka from Alice he saves his key as K=Ka^Xb mod P and sends Alice Kb=g^Xb mod P
     *
     * @param Ka
     * @return
     */
    public String replyDHKey(String Ka) {
        BigInteger K = new BigInteger(Ka, 16);
        dh = new DiffieHellman();
        this.key = K.pow(dh.Xa).mod(dh.P);
        return dh.G.pow(dh.Xa).mod(dh.P).toString(16);
    }

    /**
     * When Alice receives reply Kb from Bob she saves her key as K=Kb^Xa mod P
     *
     * @param Kb
     * @return
     */
    public void receiveDHKey(String Kb) {
        BigInteger K = new BigInteger(Kb, 16);
        this.key = K.pow(dh.Xa).mod(dh.P);
    }

}
