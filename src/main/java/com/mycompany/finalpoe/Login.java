/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalpoe;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
/**
 *
 * @author RC_Student_lab
 */
public class Login {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private String registeredUsername;
    private String registeredPassword;

    // Constructor
    public Login(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Validates the username.
     * @return true if username contains an underscore and is no more than 5 characters long.
     */
    public boolean checkUserName() {
        return username != null && !username.isEmpty() && username.contains("_") && username.length() <= 5;
    }

    /**
     * Validates the password for complexity.
     * @return true if password meets complexity requirements.
     */
    public boolean checkPasswordComplexity() {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(regex, password);
    }

    /**
     * Registers the user if username and password are valid.
     * @return appropriate registration message.
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }

        registeredUsername = username;
        registeredPassword = password;
        return "Username and password successfully captured. User registered.";
    }

    /**
     * Verifies login details.
     * @param inputUsername provided username.
     * @param inputPassword provided password.
     * @return true if login details match.
     */
    public boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername != null && inputPassword != null &&
               inputUsername.equals(registeredUsername) &&
               inputPassword.equals(registeredPassword);
    }

    /**
     * Returns login status message.
     * @param loginSuccessful whether login was successful.
     * @return appropriate login message.
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public static void main(String[] args) {
        // Registration process
        String username = JOptionPane.showInputDialog("Enter a username (must contain an underscore and no more than 5 characters):");
        String password = JOptionPane.showInputDialog("Enter a password (must contain 8 characters, a capital letter, a number, and a special character):");
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");

        Login user = new Login(username, password, firstName, lastName);
        JOptionPane.showMessageDialog(null, user.registerUser());

        // After registration, prompt for login
        boolean loginSuccess = false;
        while (!loginSuccess) {
            String inputUsername = JOptionPane.showInputDialog("Enter your username to log in:");
            String inputPassword = JOptionPane.showInputDialog("Enter your password:");

            loginSuccess = user.loginUser(inputUsername, inputPassword);
            JOptionPane.showMessageDialog(null, user.returnLoginStatus(loginSuccess));
        }
    }
}