package org.crypt.conversation.crypters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class ChangeCrypt implements Crypter{

    private String key;

    public int baseChar(char c){
        if ((int) c >= 65 && (int) c <= 90){
            return (int) c - 65;
        } else if ((int) c >= 97 && (int) c <= 122) {
            return (int) c - 97;
        } else {
            return -1;
        }
    }

    @Override
    public String generate() {
        int len = (int) (Math.random() * 6) + 4;
        char[] keyCh = new char[len];

        for (int i = 0; i < len; i++){
            keyCh[i] = (char) ((int) (Math.random() * (90 - 65)) + 65);
        }

        key = String.valueOf(keyCh);

        return key;
    }

    @Override
    public String encrypt(String message) {

        char[] mesChar = message.toCharArray();
        char[] incChar = new char[message.length()];
        char[] keyChar = key.toCharArray();
        int ind = 0;

        for (int i = 0; i < mesChar.length; i++) {
            int baseM = baseChar(mesChar[i]);
            if(baseM < 0){
                incChar[i] = mesChar[i];
                continue;
            }
            int baseK = baseChar(keyChar[ind]) + 1;
            incChar[i] = (char) ((baseM + baseK) % 26 + 65);

            ind++;
            if(ind == key.length()){
                ind = 0;
            }
        }

        return String.valueOf(incChar);
    }

    @Override
    public String decrypt(String message) {
        char[] mesChar = message.toCharArray();
        char[] incChar = new char[message.length()];
        char[] keyChar = key.toCharArray();
        int ind = 0;

        for (int i = 0; i < mesChar.length; i++) {
            int baseM = baseChar(mesChar[i]);
            if(baseM < 0){
                incChar[i] = mesChar[i];
                continue;
            }
            int baseK = baseChar(keyChar[ind]) + 1;
            incChar[i] = (char) ((baseM - baseK + 26) % 26 + 65);

            ind++;
            if(ind == key.length()){
                ind = 0;
            }
        }

        return String.valueOf(incChar);
    }
}
