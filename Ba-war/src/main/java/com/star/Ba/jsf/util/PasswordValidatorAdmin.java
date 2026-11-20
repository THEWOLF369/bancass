package com.star.Ba.jsf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidatorAdmin {
	
    public static boolean isValidPassword(String password, int minUppercase, int minLowercase, int minDigits, int minSpecialChars, int minLength) {
        if (password.length() < minLength) {
            return false; // Check for minimum length requirement
        }

        // Define regular expressions for each character type
        String uppercaseRegex = "(.*[A-Z]{" + minUppercase + ",}.*)";
        String lowercaseRegex = "(.*[a-z]{" + minLowercase + ",}.*)";
        String digitsRegex = "(.*\\d{" + minDigits + ",}.*)";
        String specialCharsRegex = "(.*[@#$%^&*()_+!-]{" + minSpecialChars + ",}.*)";

        // Combine the regular expressions into a single pattern
        String combinedRegex = uppercaseRegex + lowercaseRegex + digitsRegex + specialCharsRegex;

        // Create a pattern object and matcher
        Pattern pattern = Pattern.compile(combinedRegex);
        Matcher matcher = pattern.matcher(password);

        // Check if the password matches the combined regex pattern
        return matcher.matches();
    }

}
