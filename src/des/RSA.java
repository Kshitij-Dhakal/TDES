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
    public RSA(ProgressWindow progressWindow) {
        String rsaMessage = "Initializing RSA : ";
        progressWindow.setVisible(true);
        progressWindow.addProgress(rsaMessage + "Generating prime numbers");
        do {
            //1024 bit necessary for working with DH. 512 bits gives inconsistent result.
            p = BigInteger.probablePrime(1024, new SecureRandom());
        } while (p.mod(e).equals(BigInteger.ONE));
        progressWindow.addProgress();
        do {
            q = BigInteger.probablePrime(1024, new SecureRandom());
        } while (q.mod(e).equals(BigInteger.ONE));
        progressWindow.addProgress(rsaMessage + "Generating modulus");
        n = p.multiply(q);
        progressWindow.addProgress(rsaMessage + "Calculating totient");
        //phi = (p-1)(q-1)
        BigInteger L = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        progressWindow.addProgress(rsaMessage + "Generating private key");
        k = (new SecureRandom()).nextInt() & Integer.MAX_VALUE;
        d = e.modInverse(L);
        progressWindow.addProgress(rsaMessage + "Complete"); //5 updates
    }

    static BigInteger getMd5(String input) {
        byte[] digest;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            digest = md5.digest();
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
    }

    public String sign(BigInteger dh_key, BigInteger public_key) {
        BigInteger signature = getMd5(dh_key.toString(16));
        signature = signature.mod(n);
        signature = signature.modPow(d, n);// signing process for authentication
        //encrypt
        dh_key = dh_key.modPow(e, public_key);
        //Encrypting signature is obsolete. Even after decrypting signature we will only be able to retrieve digest. Assuming that MD5 is cryptographically secure, it is impossible to retrieve key from digest.
//        signature = signature.modPow(e, public_key);
        return dh_key.toString(16) + " " + signature.toString(16);
    }

    public BigInteger verify(String message, BigInteger public_key) {
        String[] s1 = message.split(" ");
        BigInteger dh_key = new BigInteger(s1[0], 16);
        BigInteger signature = new BigInteger(s1[1], 16);
        //decrypt
        dh_key = dh_key.modPow(d, n);
//        signature = signature.modPow(d, n);
        signature = signature.modPow(e, public_key);
        BigInteger digest = getMd5(dh_key.toString(16));
        digest = digest.mod(public_key);
        if (digest.equals(signature)) {
            return dh_key;
        }
        System.err.println("Unable to verify signature");
        return null;
    }

    public BigInteger getPublic_variable() {
        return this.n;
    }
}
