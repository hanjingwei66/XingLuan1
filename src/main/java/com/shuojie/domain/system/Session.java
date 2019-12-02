package com.shuojie.domain.system;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
    // 用户唯一性标识
    private Long userId;
    private String userName;

    public Session(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }

}