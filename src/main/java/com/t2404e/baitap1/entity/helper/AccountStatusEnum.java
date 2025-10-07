package com.t2404e.baitap1.entity.helper;

public enum AccountStatusEnum {
    ACTIVE(1),
    INACTIVE(0),
    DELETED(-1);

    private final int code;

    AccountStatusEnum(int code) {
        this.code = code;
    }

    // Trả về giá trị int để lưu vào DB
    public int getCode() {
        return code;
    }

    // Chuyển int (từ DB) thành enum
    public static AccountStatusEnum fromCode(int code) {
        for (AccountStatusEnum e : AccountStatusEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return INACTIVE; // mặc định
    }
}
