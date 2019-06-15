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
        } while (p.mod(e).equals(BigInteger.ONE));
        progressWindow.setProgress(++progress);
        do {
            q = BigInteger.probablePrime(512, new SecureRandom());
        } while (q.mod(e).equals(BigInteger.ONE));
        progressWindow.setProgress(++progress, rsaMessage + "Generating modulus");
        n = p.multiply(q);
        progressWindow.setProgress(++progress, rsaMessage + "Calculating totient");
        //phi = (p-1)(q-1)
        BigInteger L = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        progressWindow.setProgress(++progress, rsaMessage + "Generating private key");
        k = (new SecureRandom()).nextInt() & Integer.MAX_VALUE;
        d = e.modInverse(L);
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
        //FIXME inconsistency with encryption and decryption process
        BigInteger plaintext = new BigInteger("7f9919e85ef4adf5baf3cff5431ca54f60149d4bde82164a112bf18e908638a47899f5f5384c51237bffb909f92813ba0452612ff3c5d067891240dedb6605c5db54f0cdc71ea1ddf81aa77c05051c77a3f3b60bb581feac4fc837d6b3c5e80d8daadd9e09dc0b3ab011c5a559096dca7f5ac38a91dab03c91e6aa0c4cdc8f76", 16);
        System.out.println(plaintext.toString(16));
        for (int i = 0; i < 100; i++) {
            RSA alice = new RSA();
            BigInteger decrypted = plaintext.modPow(alice.e, alice.n).modPow(alice.d, alice.n);
            if (!decrypted.equals(plaintext)) {
                System.out.println(i + " " + decrypted.toString(16));
            }
        }
    }

    public String sign(BigInteger dh_key, BigInteger public_key) {
        BigInteger signature = getMd5(dh_key.toString(16));
        signature = signature.mod(n);
        signature = signature.modPow(d, n);// signing process
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
        signature = signature.modPow(e, public_key);
        BigInteger digest = getMd5(dh_key.toString(16));
        digest = digest.mod(public_key);
        if (digest.equals(signature)) {
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
