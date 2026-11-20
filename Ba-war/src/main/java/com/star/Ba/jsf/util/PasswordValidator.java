package com.star.Ba.jsf.util;

public class PasswordValidator {
    public static boolean isStrongPassword(String password) {
        // Password strength criteria
        int minLength = 12;
        boolean containsUppercase = false;
        boolean containsLowercase = false;
        boolean containsDigit = false;
        boolean containsSpecialCharacter = false;

        
        
        if (password.length() < minLength) {
            return false;
        }

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                containsUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                containsLowercase = true;
            } else if (Character.isDigit(ch)) {
                containsDigit = true;
            } else {
                containsSpecialCharacter = true;
            }
        }

        return containsUppercase && containsLowercase && containsDigit && containsSpecialCharacter;
        
        
        
//        return true;
    }
    
    
    public static boolean isStrongPasswordForAdmin(String password) {
        // Password strength criteria
        int minLength = 20;
        int minUppercase = 5;
        int minLowercase = 5;
        int minDigits = 5;
        int minSpecialCharacters = 5;

        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;
        int specialCharacterCount = 0;

       
        if (password.length() < minLength) {
            return false;
        }

       
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(ch)) {
                lowercaseCount++;
            } else if (Character.isDigit(ch)) {
                digitCount++;
            } else {
                specialCharacterCount++;
            }
        }

        
        return uppercaseCount >= minUppercase &&
               lowercaseCount >= minLowercase &&
               digitCount >= minDigits &&
               specialCharacterCount >= minSpecialCharacters;
        
//        return true;
    }
}
