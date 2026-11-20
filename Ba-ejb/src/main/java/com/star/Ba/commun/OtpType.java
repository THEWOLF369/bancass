package com.star.Ba.commun;

public enum OtpType {

    FORGOT_PASSWORD("forgot", "Mot de passe oublié"),
    EXPIRED_PASSWORD("expired", "Mot de passe expiré"),
    LOCKED_ACCOUNT("locked", "Compte bloqué");

    private final String urlParam;
    private final String label;

    OtpType(String urlParam, String label) {
        this.urlParam = urlParam;
        this.label = label;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public String getLabel() {
        return label;
    }

    public static OtpType fromString(String param) {

        if (param == null || param.trim().isEmpty()) {
            return FORGOT_PASSWORD;
        }

        for (OtpType type : values()) {
            if (type.urlParam.equalsIgnoreCase(param.trim())) {
                return type;
            }
        }

        return FORGOT_PASSWORD;
    }

    public boolean isForgotPassword() {
        return this == FORGOT_PASSWORD;
    }

    public boolean isExpiredPassword() {
        return this == EXPIRED_PASSWORD;
    }

    public boolean isLockedAccount() {
        return this == LOCKED_ACCOUNT;
    }
}