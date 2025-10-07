//package com.t2404e.baitap1.helpers;
//
//import com.t2404e.baitap1.entity.Account;
//import com.t2404e.baitap1.repository.AccountRepository;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Centralized input validation helper for servlet forms
// * Used for Register, Login, and Edit Account
// */
//public class InputValidator {
//
//    private final AccountRepository repo;
//
//    public InputValidator(AccountRepository repo) {
//        this.repo = repo;
//    }
//
//    // ---------------- Register validation ---------------- //
//    public Map<String, String> validateRegisterInput(String username, String email, String password, String confirmPassword) {
//        Map<String, String> errors = new HashMap<>();
//
//        // Username
//        if (isBlank(username)) {
//            errors.put("username", "Username is required");
//        } else if (repo.findByUsername(username) != null) {
//            errors.put("username", "Username already exists");
//        }
//
//        // Email
//        if (isBlank(email)) {
//            errors.put("email", "Email is required");
//        } else if (!isValidEmail(email)) {
//            errors.put("email", "Invalid email format");
//        }
//
//        // Password
//        if (isBlank(password)) {
//            errors.put("password", "Password is required");
//        } else if (password.length() < 6) {
//            errors.put("password", "Password must be at least 6 characters");
//        }
//
//        // Confirm password
//        if (isBlank(confirmPassword) || !confirmPassword.equals(password)) {
//            errors.put("confirmPassword", "Passwords do not match");
//        }
//
//        return errors;
//    }
//
//    // ---------------- Edit account validation ---------------- //
//    public Map<String, String> validateEditInput(Account currentUser, String username, String email, String password) {
//        Map<String, String> errors = new HashMap<>();
//
//        if (isBlank(username)) {
//            errors.put("username", "Username cannot be empty");
//        } else {
//            Account existing = repo.findByUsername(username);
//            if (existing != null && existing.getId() != currentUser.getId()) {
//                errors.put("username", "Username already taken by another user");
//            }
//        }
//
//        if (isBlank(email)) {
//            errors.put("email", "Email cannot be empty");
//        } else if (!isValidEmail(email)) {
//            errors.put("email", "Invalid email format");
//        }
//
//        // Password
//        if (!isBlank(password) && password.length() < 6) {
//            errors.put("password", "Password must be at least 6 characters");
//        }
//
//
//        return errors;
//    }
//
//    // ---------------- Login validation ---------------- //
//    public Map<String, String> validateLoginInput(String username, String password) {
//        Map<String, String> errors = new HashMap<>();
//
//        if (isBlank(username)) {
//            errors.put("username", "Username is required");
//        }
//
//        if (isBlank(password)) {
//            errors.put("password", "Password is required");
//        }
//
//        return errors;
//    }
//
//    // ---------------- Utility helpers ---------------- //
//    private boolean isBlank(String str) {
//        return str == null || str.trim().isEmpty();
//    }
//
//    private boolean isValidEmail(String email) {
//        return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$");
//    }
//}


package com.t2404e.baitap1.helpers;

import com.t2404e.baitap1.entity.Account;
import com.t2404e.baitap1.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

public class InputValidator {
    private final AccountRepository repo;

    public InputValidator(AccountRepository repo) {
        this.repo = repo;
    }

    public Map<String, String> validateRegisterInput(
            String username, String email, String password, String confirmPassword,
            String avatarUrl, String bio) {
        Map<String, String> errors = new HashMap<>();

        // username
        if (isBlank(username)) {
            errors.put("username", "Username is required");
        } else if (repo.findByUsername(username) != null) {
            errors.put("username", "Username already exists");
        }

        // email
        if (isBlank(email)) {
            errors.put("email", "Email is required");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", "Invalid email format");
        }

        // password
        if (isBlank(password)) {
            errors.put("password", "Password is required");
        } else if (password.length() < 6) {
            errors.put("password", "Password must be at least 6 characters");
        }

        // confirm
        if (isBlank(confirmPassword) || !confirmPassword.equals(password)) {
            errors.put("confirmPassword", "Passwords do not match");
        }

        // avatarUrl (optional)
        if (!isBlank(avatarUrl) && !avatarUrl.startsWith("http")) {
            errors.put("avatarUrl", "Avatar URL must be http(s)");
        }

        // bio (optional) – giới hạn dung lượng tuỳ ý
        if (!isBlank(bio) && bio.length() > 10000) {
            errors.put("bio", "Bio is too long (max 10,000 chars)");
        }
        return errors;
    }

    public Map<String, String> validateEditInput(
            Account current, String username, String email, String password,
            String avatarUrl, String bio) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(username)) {
            errors.put("username", "Username cannot be empty");
        } else if (!username.equals(current.getUsername())
                && repo.findByUsername(username) != null) {
            errors.put("username", "Username already exists");
        }

        if (isBlank(email)) {
            errors.put("email", "Email cannot be empty");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", "Invalid email format");
        }

        if (!isBlank(password) && password.length() < 6) {
            errors.put("password", "New password must be at least 6 characters");
        }

        if (!isBlank(avatarUrl) && !avatarUrl.startsWith("http")) {
            errors.put("avatarUrl", "Avatar URL must be http(s)");
        }

        if (!isBlank(bio) && bio.length() > 10000) {
            errors.put("bio", "Bio is too long (max 10,000 chars)");
        }
        return errors;
    }

    public static String sanitizeBio(String bio) {
        if (bio == null) return null;
        String sanitized = bio
                // loại <script>...</script>
                .replaceAll("(?is)<script.*?>.*?</script>", "")
                // loại on* event handlers (onload=, onclick=, …)
                .replaceAll("(?i)on\\w+\\s*=", "x-removed=");
        return sanitized;
    }


        // ---------------- Login validation ---------------- //
    public Map<String, String> validateLoginInput(String username, String password) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(username)) {
            errors.put("username", "Username is required");
        }

        if (isBlank(password)) {
            errors.put("password", "Password is required");
        }

        return errors;
    }

    // ---------------- Utility helpers ---------------- //
    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
