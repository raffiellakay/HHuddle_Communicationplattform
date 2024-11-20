package com.knoettner.hhuddle;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode

    @Embeddable
    public class ChatParticipantKey implements Serializable {
        @Column(name = "first_userId")
        Long firstUserId;

        @Column (name = "second_userId")
        Long secondUserId;
}
