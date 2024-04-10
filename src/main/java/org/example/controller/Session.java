package org.example.controller;

import org.example.container.Container;
import org.example.dto.Member;

public class Session {
    private Member loginedMember;

    public Member getLoginedMember() {
        return loginedMember;
    }

    public void setLoginedMember(Member loginedMember) {
        this.loginedMember = loginedMember;
    }

    public boolean isLogined() {
        return loginedMember != null;
    }
}
