package org.crypt.conversation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "client1", url = "http://localhost:8080")
public interface Client {

    @PostMapping("/crypt/get_public_key")
    ResponseEntity<String> getPubKey(@RequestBody RequestDto requestDto);

    @PostMapping("/crypt/get_encrypted_msg")
    ResponseEntity<String> getEncMes(@RequestBody RequestDto requestDto);

}
