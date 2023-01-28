package com.example.visit;

import lombok.Getter;
import lombok.Setter;

public class Message {
    @Getter @Setter
    private String content;

    public Message(){}
    public Message(String content) {
        this.content = content;
    }
}
