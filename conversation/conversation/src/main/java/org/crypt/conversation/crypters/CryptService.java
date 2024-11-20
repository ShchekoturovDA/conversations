package org.crypt.conversation.crypters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
@AllArgsConstructor
public class CryptService {

    private ChangeCrypt changeCrypt;

    private SymCrypt symCrypt;

    private AsymCrypt asymCrypt;

    public String generate(String method){
        return switch (method) {
            case ("change") -> changeCrypt.generate();
            case ("symmetric") -> symCrypt.generate();
            case ("asymmetric") -> asymCrypt.generate();
            default -> "";
        };
    }

    public String encrypt(String method, String message){
        return switch (method) {
            case ("change") -> changeCrypt.encrypt(message);
            case ("symmetric") -> symCrypt.encrypt(message);
            case ("asymmetric") -> asymCrypt.encrypt(message);
            default -> "";
        };
    }

    public String decrypt(String method, String message){
        return switch (method) {
            case ("change") -> changeCrypt.decrypt(message);
            case ("symmetric") -> symCrypt.encrypt(message);
            case ("asymmetric") -> asymCrypt.decrypt(message);
            default -> "";
        };
    }

    public void setKey(String method, String key) {
        switch (method){
            case("change"):
                changeCrypt.setKey(key);
                break;
            case("symmetric"):
                symCrypt.setKey(key);
                break;
            case("asymmetric"):
                asymCrypt.setKey(key);
                break;
        }
    }

    public String getKey(String method) {
        return switch (method) {
            case ("change") -> changeCrypt.getKey();
            case ("symmetric") -> symCrypt.getKey();
            case ("asymmetric") -> asymCrypt.getKey();
            default -> "";
        };
    }
}
