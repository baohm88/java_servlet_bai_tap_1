package com.t2404e.baitap1.repository;

import com.t2404e.baitap1.entity.Account;

import java.util.List;

public interface AccountRepository {
    Account save(Account account);
    Account update(int id, Account account);
    boolean deleteById(int id);
    Account findById(int id);
    Account findByUsername(String username);
    List<Account> findAll();
}
