package des;

import dependencies.UI.ProgressWindow;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RSA {
    BigInteger p;
    BigInteger q;
    BigInteger n; //public key n=p*q
    BigInteger e = BigInteger.valueOf(65537); //public key 1<e<phi(n)
    BigInteger d; //private key d=e^(-1) (mod phi)
    int k;

    /*
        Select a value of e from 3,5,17,257,65537
        repeat
        p ← genprime(k/2)
        until (pmode)≠1
        repeat
        q ← genprime(k - k/2)
        until (qmode)≠1
        N ← pq
        L ← (p-1)(q-1)
        d ← modinv(e, L)
        return (N,e,d)
    */
    public RSA() {
        int progress = -1;
        String rsaMessage = "Initializing RSA : ";
        ProgressWindow progressWindow = new ProgressWindow(5);
        progressWindow.setProgress(++progress, rsaMessage + "Generating prime numbers");
        do {
            p = BigInteger.probablePrime(512, new SecureRandom());
        } while (p.gcd(e).equals(1));
        progressWindow.setProgress(++progress);
        do {
            q = BigInteger.probablePrime(512, new SecureRandom());
        } while (p.gcd(e).equals(1));
        progressWindow.setProgress(++progress, rsaMessage + "Generating modulus");
        n = p.multiply(q);
        progressWindow.setProgress(++progress, rsaMessage + "Calculating totient");
        //phi = (p-1)(q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        progressWindow.setProgress(++progress, rsaMessage + "Generating private key");
        k = (new SecureRandom()).nextInt() & Integer.MAX_VALUE;
        d = e.modInverse(phi);
        progressWindow.setProgress(++progress, rsaMessage + "Complete");
        progressWindow.dispose();
    }

    static BigInteger getMd5(String input) {
        byte[] digest = new byte[0];
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            digest = md5.digest();
//            System.out.println(digest);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            buffer.append(Integer.toHexString(0xFF & digest[i]));
        }
        return new BigInteger(buffer.toString(), 16);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            RSA aliceRsa = new RSA();
            RSA bobRsa = new RSA();
            KeyGenerator alice = new KeyGenerator();
            KeyGenerator bob = new KeyGenerator();
            String sign = aliceRsa.sign(new BigInteger(alice.initializeDHKeyExchange(), 16), bobRsa.getPublic_variable());
            bobRsa.verify(sign, aliceRsa.getPublic_variable());
        }
    }

    public String sign(BigInteger dh_key, BigInteger public_key) {
        BigInteger signature = getMd5(dh_key.toString(16));
        signature = signature.mod(n).modPow(d, n);// signing process
        System.out.println("Sent signature : " + signature.toString(16));
        //encrypt
        dh_key = dh_key.modPow(e, public_key);
        signature = signature.modPow(e, public_key);
        return dh_key.toString(16) + " " + signature.toString(16);
    }

    public BigInteger verify(String message, BigInteger public_key) {
        String[] s1 = message.split(" ");
        BigInteger dh_key = new BigInteger(s1[0], 16);
        BigInteger signature = new BigInteger(s1[1], 16);
        //decrypt
        dh_key = dh_key.modPow(d, n);
        signature = signature.modPow(d, n);
        //FIXME after decryption signature inconsistently doesn't match sent signature
        System.out.println("Received signature : " + signature.toString(16));
        signature = signature.modPow(e, public_key);
        BigInteger digest = getMd5(dh_key.toString(16));
        if (digest.mod(public_key).equals(signature)) {
            System.out.println("Verified");
            return dh_key;
        }
        System.err.println("Unable to verify signature");
        return null;
    }

    public BigInteger getPublic_variable() {
        return this.n;
    }
}
