package org.crypt.conversation.crypters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.crypt.conversation.MathUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class AsymCrypt implements Crypter{

    private String privateKey;

    private String publicKey;

    private String mod;


    @Override
    public String generate() {
        BigInteger q = MathUtils.generatePrimes(1024);
        BigInteger p = MathUtils.generatePrimes(1024);
        BigInteger n = q.multiply(p);
        mod = n.toString();
        BigInteger phi = (q.subtract(BigInteger.ONE)).multiply(p.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537);
        publicKey = e.toString();
        BigInteger d;
        do {
            d = new BigInteger(20, new Random());
        } while (!phi.multiply(d).add(BigInteger.ONE).mod(e).equals(BigInteger.ZERO) || d.equals(BigInteger.ZERO));
        d = ((phi.multiply(d)).add(BigInteger.ONE)).divide(e);
        privateKey = d.toString();
        System.out.println("Private: " + privateKey);
        System.out.println(d.multiply(e).mod(phi));
        return publicKey;
    }

    @Override
    public String encrypt(String message) {
        BigInteger pubKey = new BigInteger(publicKey);
        BigInteger modKey = new BigInteger(mod);
        char[] mesCh = message.toCharArray();
        String enc = "";

        for(char ch : mesCh){
            BigInteger crypt = MathUtils.fastPower(BigInteger.valueOf((int)ch), pubKey, modKey);
            enc = enc + crypt.toString() + " ";
        }
        return enc;
    }

    @Override
    public String decrypt(String message) {
        BigInteger privKey = new BigInteger(privateKey);
        BigInteger modKey = new BigInteger(mod);
        String[] encs = message.split(" ");
        String dec = "";

        for (String enc : encs){
            BigInteger decInt = MathUtils.fastPower(new BigInteger(enc), privKey, modKey);
            //System.out.println(decInt);
            dec += (char) decInt.intValue();
        }

        return dec;
    }

    public void setKey(String key) {
        String[] keys = key.split(" ");
        publicKey = keys[0];
        mod = keys[1];
    }

    public String getKey() {
        return publicKey + " " + mod;
    }
}
