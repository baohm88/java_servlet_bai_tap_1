package com.t2404e.baitap1.helpers;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    // Hash password với cost = 12
    public static String hash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // Kiểm tra password nhập vào có khớp với hash không
    public static boolean verify(String password, String hash) {
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified;
    }
}
