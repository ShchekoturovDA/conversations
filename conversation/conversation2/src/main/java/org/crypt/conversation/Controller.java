package org.crypt.conversation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.crypt.conversation.crypters.CryptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crypt")
@Setter
@Getter
@AllArgsConstructor
public class Controller {

    private Client client;

    private CryptService cryptService;

    @PostMapping("/generate")
    public String generate(@RequestBody RequestDto requestDto){
        String key = cryptService.generate(requestDto.getMethod());
        System.out.println("Generated: " + key);

        return key;
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody RequestDto requestDto){
        String enc = cryptService.encrypt(requestDto.getMethod(), requestDto.getMessage());
        System.out.println("Encrypted: " + enc);

        return enc;
    }

    @PostMapping("/get_public_key")
    public ResponseEntity<?> getPubKey(@RequestBody RequestDto requestDto){
        System.out.println("Got key: " + requestDto.getKey());
        cryptService.setKey(requestDto.getMethod(), requestDto.getKey());

        return ResponseEntity.ok("Got It!");
    }

    @PostMapping("/send_public_key")
    public ResponseEntity<String> sendPubKey(@RequestBody RequestDto requestDto){
        String key = cryptService.getKey(requestDto.getMethod());
        System.out.println("Send key: " + key);
        requestDto.setKey(key);

        return client.getPubKey(requestDto);
    }

    @PostMapping("/get_encrypted_msg")
    public ResponseEntity<String> getEncMes(@RequestBody RequestDto requestDto){
        String dec = cryptService.decrypt(requestDto.getMethod(), requestDto.getMessage());
        System.out.println("Pure Text: " + dec);

        return ResponseEntity.ok("Decrypted");
    }

    @PostMapping("/send_enc_msg")
    public ResponseEntity<String> sendEncMes(@RequestBody RequestDto requestDto){
        System.out.println("Sending: " + requestDto.getMessage());

        return client.getEncMes(requestDto);
    }

    @PostMapping("/encrypt_and_send")
    public ResponseEntity<String> encryptAndSend(@RequestBody RequestDto requestDto){
        String enc = cryptService.encrypt(requestDto.getMethod(), requestDto.getMessage());
        System.out.println("Encrypting and sending: " + enc);
        requestDto.setMessage(enc);
        return client.getEncMes(requestDto);
    }

}
