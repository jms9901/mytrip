package com.lec.spring.mytrip.domain;

public class FriendshipUserResultMap {
    private Friendship friendship;
    private User user;

        // getters and setters
    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
