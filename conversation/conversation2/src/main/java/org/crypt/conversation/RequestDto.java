package org.crypt.conversation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class RequestDto {
    private String method;

    private String message;

    private String key;
}
