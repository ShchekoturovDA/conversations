package org.crypt.conversation.crypters;

public interface Crypter {

    String generate();

    String encrypt(String message);

    String decrypt(String message);
}
