package com.app.library.utils;

import com.app.library.models.User;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UtilHelpers {

    private UtilHelpers() {
    }

    public static boolean validateContactDetails(User userRequest) {
        if (userRequest.getContactDetails() != null) {
            return userRequest.getContactDetails().getPhone() != null && validateMobile(userRequest.getContactDetails().getPhone());
        }
        return false;
    }

    public static boolean validatePassword(String password) {
        if (password.length() >= 8) {
            Pattern letter = Pattern.compile("[a-zA-Z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();
        } else
            return false;
    }

    public static boolean validateRequest(User userRequest) {
        return StringUtils.isBlank(userRequest.getUsername()) || StringUtils.isBlank(userRequest.getPassword())
                || userRequest.getContactDetails() == null;
    }

    private static boolean validateMobile(String mobile) {
        boolean valid = true;
        if (mobile.length() != 10) {
            valid = false;
        }
        for (int i = 0; i < mobile.length(); i++) {
            if (Character.isLetter(mobile.charAt(i))) {
                valid = false;
                break;
            }
        }
        return valid;
    }
}
