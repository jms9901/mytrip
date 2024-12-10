package com.lec.spring.mytrip.util;

import com.lec.spring.mytrip.domain.User;

public class LikeUtil {
    //유저 id 조회.
    public static int findUserId() {
        User user = U.getLoggedUser();
        if (user != null) {
            return user.getId();
        }
        return -1;
    }
}
