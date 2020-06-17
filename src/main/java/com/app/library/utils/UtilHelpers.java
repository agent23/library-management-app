package com.app.library.utils;

import com.app.library.models.Book;
import com.app.library.models.User;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UtilHelpers {

    private UtilHelpers() {
    }

    public static boolean validateContactDetails(User userRequest) {
        if (userRequest != null) {
            return userRequest.getPhone() != null && validateMobile(userRequest.getPhone());
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
                || userRequest.getEmail() == null;
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

    public static boolean isUsernameEmpty(String username) {
        return !username.trim().isEmpty() && username.length() >= 1;
    }

    public static boolean validateEmailAdd(String emailAdd) {
        boolean flag = false;
        if (emailAdd.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            flag = true;
        return flag;
    }

    public static boolean validBook(Book book) {
        boolean flag = true;
        if (StringUtils.isAllBlank(book.getAuthor(), book.getIsbn(), book.getPublisher(), book.getPublisher(),
                book.getTitle()) || !verifyYear(book.getYear()))
            flag = false;
        return flag;
    }

    public static boolean checkIsbn(String isbn) {
        boolean flag = true;
        if (StringUtils.isBlank(isbn))
            flag = false;
        return flag;
    }

    private static boolean verifyYear(int year) {
        boolean flag = true;
        if (year <= 0)
            flag = false;
        return flag;
    }
}
