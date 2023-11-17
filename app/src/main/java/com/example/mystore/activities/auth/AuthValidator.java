package com.example.mystore.activities.auth;

import java.util.regex.Pattern;

public class AuthValidator {
    // error messages
    private static final String EMPTY_EMAIL = "Please enter your email";
    private static final String INVALID_EMAIL = "Email is invalid";
    private static final String EMPTY_FNAME = "Please enter your first name";
    private static final String EMPTY_LNAME = "Please enter your last name";
    private static final String EMPTY_USERNAME = "Please enter your username";
    private static final String EMPTY_PWD = "Please enter your password";
    private static final String WEAK_PWD = "Password should be stronger";
    private static final String EMPTY_REPEAT_PWD = "Please repeat your password";
    private static final String DIFF_PASSWORDS = "Passwords don't match";

    // regexes
    private static final Pattern PWD_REGEX = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\\w\\s]).{8,}");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^\\w+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public static class LoginErrors {
        public final String email;
        public final String password;
        public final boolean isValid;

        public LoginErrors(String email, String password) {
            this.email = email;
            this.password = password;
            this.isValid = email == null && password == null;
        }
    }

    public static class RegisterErrors {
        public final String email;
        public final String firstName;
        public final String lastName;
        public final String username;
        public final String password;
        public final String repeatPassword;
        public final boolean isValid;

        public RegisterErrors(String email, String firstName, String lastName, String username, String password, String repeatPassword) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.repeatPassword = repeatPassword;
            this.isValid = email == null && firstName == null &&
                    lastName == null && username == null &&
                    password == null && repeatPassword == null;
        }
    }

    public static LoginErrors validateLogin(String username, String password) {
        return new LoginErrors(
                (username.isEmpty()) ? EMPTY_USERNAME : null,
                (password.isEmpty()) ? EMPTY_PWD : (!validatePassword(password)) ? WEAK_PWD : null
        );
    }

    public static RegisterErrors validateRegister(
            String email, String firstName, String lastName, String username, String password, String repeatPassword) {
        return new RegisterErrors(
                (email.isEmpty()) ? EMPTY_EMAIL : (!validateEmail(email)) ? INVALID_EMAIL : null,
                (firstName.isEmpty()) ? EMPTY_FNAME : null,
                (lastName.isEmpty()) ? EMPTY_LNAME : null,
                (username.isEmpty()) ? EMPTY_USERNAME : null,
                (password.isEmpty()) ? EMPTY_PWD : null,
                (repeatPassword.isEmpty()) ? EMPTY_REPEAT_PWD :
                        (!password.equals(repeatPassword)) ? DIFF_PASSWORDS :
                                (!validatePassword(password)) ? WEAK_PWD : null
        );
    }

    private static boolean validatePassword(String password) {
        return PWD_REGEX.matcher(password).matches();
    }

    private static boolean validateEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }
}
