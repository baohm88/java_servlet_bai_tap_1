package com.t2404e.baitap1.repository;

import com.t2404e.baitap1.entity.Account;
import com.t2404e.baitap1.entity.helper.AccountStatusEnum;
import com.t2404e.baitap1.helpers.MySqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccountRepository implements AccountRepository {
    private static final String TABLE_NAME = "accounts";

    @Override
    public Account save(Account account) {
        String sql = "INSERT INTO " + TABLE_NAME + " (username, email, password_hash, status, avatar_url, bio ) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = MySqlHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPasswordHash());
            ps.setInt(4, account.getStatus().getCode());
            ps.setString(5, account.getAvatarUrl());
            ps.setString(6, account.getBio());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) account.setId(rs.getInt(1));
                System.out.println("✅ Account saved: " + account.getUsername());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error saving account: " + e.getMessage());
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account update(int id, Account account) {
        String sql = "UPDATE " + TABLE_NAME + " SET username=?, email=?, password_hash=?, status=?, avatar_url=?, bio=? , updated_at=NOW() WHERE id=?";
        try (Connection conn = MySqlHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPasswordHash());
            ps.setInt(4, account.getStatus().getCode());
            ps.setString(5, account.getAvatarUrl());
            ps.setString(6, account.getBio());
            ps.setInt(7, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Account updated: " + account.getUsername());
                return account;
            } else {
                System.out.println("⚠️ Account with id " + id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating account: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        try (Connection conn = MySqlHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error deleting account: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account findById(int id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        try (Connection conn = MySqlHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToAccount(rs);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error finding account by id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findByUsername(String username) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username=?";
        try (Connection conn = MySqlHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToAccount(rs);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error finding account by username: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = MySqlHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapResultSetToAccount(rs));
            System.out.println("✅ Found " + list.size() + " accounts.");
        } catch (SQLException e) {
            System.out.println("❌ Error finding all accounts: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account acc = new Account();
        acc.setId(rs.getInt("id"));
        acc.setUsername(rs.getString("username"));
        acc.setEmail(rs.getString("email"));
        acc.setPasswordHash(rs.getString("password_hash"));
        acc.setStatus(AccountStatusEnum.fromCode(rs.getInt("status")));
        acc.setAvatarUrl(rs.getString("avatar_url"));
        acc.setBio(rs.getString("bio"));
        acc.setCreatedAt(rs.getTimestamp("created_at"));
        acc.setUpdatedAt(rs.getTimestamp("updated_at"));
        return acc;
    }
}
