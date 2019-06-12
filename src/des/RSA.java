package des;

import dependencies.UI.ProgressWindow;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    BigInteger p;
    BigInteger q;
    BigInteger n; //public key n=p*q
    BigInteger e = BigInteger.valueOf(65537); //public key 1<e<phi(n)
    BigInteger d; //private key d=e^(-1) (mod phi)
    int k;

    //TODO add RSA to prevent man in the middle attack
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
    RSA() throws InterruptedException {
        //TODO remove Thread.sleep()
        int progress = -1;
        String rsaMessage = "Initializing RSA : ";
        ProgressWindow progressWindow = new ProgressWindow(4);
        progressWindow.setProgress(++progress, rsaMessage + "Generating prime numbers");
        Thread.sleep(1000);
        do {
            p = BigInteger.probablePrime(512, new SecureRandom());
        } while (p.gcd(e).equals(1));
        do {
            q = BigInteger.probablePrime(512, new SecureRandom());
        } while (p.gcd(e).equals(1));
        progressWindow.setProgress(++progress, rsaMessage + "Generating modulus");
        Thread.sleep(1000);
        n = p.multiply(q);
        progressWindow.setProgress(++progress, rsaMessage + "Calculating totient");
        Thread.sleep(1000);
        //phi = (p-1)(q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        progressWindow.setProgress(++progress, rsaMessage + "Generating private key");
        Thread.sleep(1000);
        k = (new SecureRandom()).nextInt() & Integer.MAX_VALUE;
        d = e.modInverse(phi);
        progressWindow.setProgress(++progress, rsaMessage + "Complete");
        Thread.sleep(100);
        progressWindow.dispose();
    }

    public static void main(String[] args) {
        RSA rsa = null;
        try {
            rsa = new RSA();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(rsa.n);
        System.out.println(rsa.d);
        System.out.println(rsa.e);

        long start = System.currentTimeMillis();
        BigInteger enc = BigInteger.valueOf(1553);
        System.out.println(enc.modPow(rsa.e, rsa.n).modPow(rsa.d, rsa.n));
        System.out.println("Time taken to encrypt and decrypt : " + (System.currentTimeMillis() - start) + " milis");
    }
}
