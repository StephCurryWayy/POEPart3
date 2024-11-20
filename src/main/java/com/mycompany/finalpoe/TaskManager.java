/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalpoe;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.regex.Pattern;
/**
 *
 * @author RC_Student_lab
 */
public class TaskManager {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String registeredUsername;
    private String registeredPassword;

    // Task data storage
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskManager(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName() {
        return username != null && !username.isEmpty() && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(regex, password);
    }

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

    public boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername != null && inputPassword != null &&
               inputUsername.equals(registeredUsername) &&
               inputPassword.equals(registeredPassword);
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public void addTask(int numTasks) {
        for (int i = 0; i < numTasks; i++) {
            // Get task name
            String taskName = JOptionPane.showInputDialog("Enter Task Name:");
            
            // Task description validation
            String taskDescription = JOptionPane.showInputDialog("Enter Task Description (max 50 characters):");
            while (taskDescription.length() > 50) {
                taskDescription = JOptionPane.showInputDialog("Please enter a task description of less than 50 characters");
            }

            // Get developer details
            String developerFirstName = JOptionPane.showInputDialog("Enter Developer First Name:");
            String developerLastName = JOptionPane.showInputDialog("Enter Developer Last Name:");

            // Task duration
            String durationStr = JOptionPane.showInputDialog("Enter Task Duration (in hours):");
            int taskDuration = Integer.parseInt(durationStr);

            // Generate Task ID
            String taskID = createTaskID(taskName, i, developerLastName);
            
            // Task status selection
            String[] statusOptions = {"To Do", "Doing", "Done"};
            String taskStatus = (String) JOptionPane.showInputDialog(null, 
                "Select Task Status", "Task Status", 
                JOptionPane.QUESTION_MESSAGE, null, 
                statusOptions, statusOptions[0]);
            
            // Create and store the task
            Task task = new Task(taskName, i, taskDescription, developerFirstName + " " + developerLastName, 
                                 taskDuration, taskID, taskStatus);
            tasks.add(task);

            // Show task details
            JOptionPane.showMessageDialog(null, task.printTaskDetails());
        }
        
        // Calculate total hours
        JOptionPane.showMessageDialog(null, "Total Task Duration: " + returnTotalHours() + " hours.");
    }

    // Method to generate Task ID
    public String createTaskID(String taskName, int taskNumber, String developerLastName) {
        String taskPrefix = taskName.substring(0, 2).toUpperCase();
        String taskSuffix = developerLastName.substring(developerLastName.length() - 3).toUpperCase();
        return taskPrefix + ":" + taskNumber + ":" + taskSuffix;
    }

    // Method to calculate total task hours
    public int returnTotalHours() {
        int totalHours = 0;
        for (Task task : tasks) {
            totalHours += task.getDuration();
        }
        return totalHours;
    }

    public static void main(String[] args) {
        // Registering and logging in
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");

        TaskManager user = new TaskManager(username, password, firstName, lastName);
        JOptionPane.showMessageDialog(null, user.registerUser());

        // Login process
        boolean loginSuccess = false;
        while (!loginSuccess) {
            String inputUsername = JOptionPane.showInputDialog("Enter your username to log in:");
            String inputPassword = JOptionPane.showInputDialog("Enter your password:");
            loginSuccess = user.loginUser(inputUsername, inputPassword);
            JOptionPane.showMessageDialog(null, user.returnLoginStatus(loginSuccess));
        }

        // Welcome message
        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");

        // Menu for options
        String menu = "1) Add tasks\n2) Show report (Coming soon)\n3) Quit";
        String choice = JOptionPane.showInputDialog(menu);
        
        while (!choice.equals("3")) {
            if (choice.equals("1")) {
                String numTasksStr = JOptionPane.showInputDialog("How many tasks would you like to enter?");
                int numTasks = Integer.parseInt(numTasksStr);
                user.addTask(numTasks);
            } else if (choice.equals("2")) {
                JOptionPane.showMessageDialog(null, "Coming Soon");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
            choice = JOptionPane.showInputDialog(menu);
        }

        JOptionPane.showMessageDialog(null, "Goodbye!");
    }
}

// Task class to represent tasks
class Task {
    private String taskName;
    private int taskNumber;
    private String taskDescription;
    private String developerDetails;
    private int taskDuration;
    private String taskID;
    private String taskStatus;

    public Task(String taskName, int taskNumber, String taskDescription, String developerDetails, 
                int taskDuration, String taskID, String taskStatus) {
        this.taskName = taskName;
        this.taskNumber = taskNumber;
        this.taskDescription = taskDescription;
        this.developerDetails = developerDetails;
        this.taskDuration = taskDuration;
        this.taskID = taskID;
        this.taskStatus = taskStatus;
    }

    public int getDuration() {
        return taskDuration;
    }

    public String printTaskDetails() {
        return "Task Status: " + taskStatus + "\n" +
               "Developer: " + developerDetails + "\n" +
               "Task Description: " + taskDescription + "\n" +
               "Task Number: " + taskNumber + "\n" +
               "Task Name: " + taskName + "\n" +
               "Task ID: " + taskID + "\n" +
               "Duration: " + taskDuration + " hours";
    }
}