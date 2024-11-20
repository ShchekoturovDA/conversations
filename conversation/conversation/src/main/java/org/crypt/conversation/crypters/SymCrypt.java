package org.crypt.conversation.crypters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

//Реализован алгоритм RC4

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class SymCrypt implements Crypter{

    String key;

    @Override
    public String generate() {
        int len = (int) (Math.random() * 255) + 255;
        char[] keyCh = new char[len];

        // Случайным образом генерируются символы ключа
        for (int i = 0; i < len; i++){
            keyCh[i] = (char) ((int) (Math.random() * 256) );
        }

        key = String.valueOf(keyCh);

        return key;
    }

    @Override
    public String encrypt(String message) {
        int[] s = new int[65536];
        int j = 0;
        int t = 0;
        char[] keyCh = key.toCharArray();
        char[] mesCh = message.toCharArray();

        // Инициализация S-блока для последующего кодирования
        for (int i  = 0; i < 65536; i++){
            s[i] = i;
        }
        for (int i =0; i < 65536; i++){
            j = (j + s[i] + (int) keyCh[i % key.length()]) % 65536;
            t = s[i];
            s[i] = s[j];
            s[j] = t;
        }


        int i = 0;
        j = 0;
        int kWord;
        int midW = 0;

        // Запуск ключевого потока, с которым по средствам xor будет текст зашифрован.
        // Для расшифрования потребуется аналогично воссоздать этот поток,
        // поэтому функция вызывается и при шифровании, и при расшифровке
        for (int k = 0; k < message.length(); k += 2){
            i = (i + 1) % 65536;
            j = (j + s[i]) % 65536;
            t = s[i];
            s[i] = s[j];
            s[j] = t;
            t = (s[i] + s[j]) % 65536;
            kWord = s[t];
            if(k != message.length() - 1) {
                midW = ((int) mesCh[k] << 8) + mesCh[k + 1];
                midW = midW ^ kWord;
                mesCh[k + 1] = (char) (midW % 256);
                mesCh[k] = (char) (midW >> 8);
            } else {
                midW = ((int) mesCh[k] << 8);
                midW = midW ^ kWord;
                mesCh[k] = (char) (midW >> 8);
            }
        }

        return String.valueOf(mesCh);
    }

    @Override
    public String decrypt(String message) {
        return "";
    }
}
