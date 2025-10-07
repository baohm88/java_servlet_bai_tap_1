package com.t2404e.baitap1.entity;
import com.t2404e.baitap1.entity.helper.AccountStatusEnum;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Account {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private AccountStatusEnum status;
    private String avatarUrl;
    private String bio;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
