package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBook {

    private int guestBookId;

    private int toUserId;

    private int fromUserId;

    private String guestBookContent;

    private LocalDate guestBookDate;

}
