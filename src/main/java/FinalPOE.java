/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.regex.Pattern;
/**
 *
 * @author RC_Student_lab
 */
public class FinalPOE {
    // Arrays to store task data
    private ArrayList<String> developers = new ArrayList<>();
    private ArrayList<String> taskNames = new ArrayList<>();
    private ArrayList<String> taskIDs = new ArrayList<>();
    private ArrayList<Integer> taskDurations = new ArrayList<>();
    private ArrayList<String> taskStatuses = new ArrayList<>();

    // User details
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String registeredUsername;
    private String registeredPassword;

    // Constructor
    public FinalPOE(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Method to check username validity (Part 1)
    public boolean checkUserName() {
        return username != null && !username.isEmpty() && username.contains("_") && username.length() <= 5;
    }

    // Method to check password complexity (Part 1)
    public boolean checkPasswordComplexity() {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    // Method to register the user (Part 1)
    public String registerUser() {
        // Trim whitespace from input values
        username = username.trim();
        password = password.trim();

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

    // Method to log in the user (Part 1)
    public boolean loginUser(String inputUsername, String inputPassword) {
        // Trim whitespace and make comparison case-insensitive
        inputUsername = inputUsername.trim();
        inputPassword = inputPassword.trim();
        
        return inputUsername.equalsIgnoreCase(registeredUsername) &&
               inputPassword.equals(registeredPassword);
    }

    // Method to return login status (Part 1)
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Add tasks method (Part 2)
    public void addTask(int numTasks) {
        for (int i = 0; i < numTasks; i++) {
            String taskName = JOptionPane.showInputDialog("Enter Task Name:");
            String taskDescription = JOptionPane.showInputDialog("Enter Task Description (max 50 characters):");

            while (taskDescription.length() > 50) {
                taskDescription = JOptionPane.showInputDialog("Please enter a task description of less than 50 characters");
            }

            String developerFirstName = JOptionPane.showInputDialog("Enter Developer First Name:");
            String developerLastName = JOptionPane.showInputDialog("Enter Developer Last Name:");

            String durationStr = JOptionPane.showInputDialog("Enter Task Duration (in hours):");
            int taskDuration = Integer.parseInt(durationStr);

            String taskID = createTaskID(taskName, i, developerLastName);
            String taskStatus = JOptionPane.showInputDialog("Enter Task Status (To Do, Doing, Done):");

            // Save task details into arrays
            developers.add(developerFirstName + " " + developerLastName);
            taskNames.add(taskName);
            taskIDs.add(taskID);
            taskDurations.add(taskDuration);
            taskStatuses.add(taskStatus);

            // Display task details
            JOptionPane.showMessageDialog(null, "Task Details:\n" + printTaskDetails(i));
        }
    }

    // Generate Task ID (Part 2)
    public String createTaskID(String taskName, int taskNumber, String developerLastName) {
        String taskPrefix = taskName.substring(0, 2).toUpperCase();
        String taskSuffix = developerLastName.substring(developerLastName.length() - 3).toUpperCase();
        return taskPrefix + ":" + taskNumber + ":" + taskSuffix;
    }

    // Return full task details (Part 2)
    public String printTaskDetails(int taskIndex) {
        return "Task Status: " + taskStatuses.get(taskIndex) + "\n" +
               "Developer: " + developers.get(taskIndex) + "\n" +
               "Task Description: " + taskNames.get(taskIndex) + "\n" +
               "Task Number: " + taskIndex + "\n" +
               "Task Name: " + taskNames.get(taskIndex) + "\n" +
               "Task ID: " + taskIDs.get(taskIndex) + "\n" +
               "Duration: " + taskDurations.get(taskIndex) + " hours";
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Registering the user
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");

        FinalPOE user = new FinalPOE(username, password, firstName, lastName);

        // Register user
        JOptionPane.showMessageDialog(null, user.registerUser());

        // Login process
        boolean loginSuccess = false;
        while (!loginSuccess) {
            String inputUsername = JOptionPane.showInputDialog("Enter your username to log in:");
            String inputPassword = JOptionPane.showInputDialog("Enter your password:");
            loginSuccess = user.loginUser(inputUsername, inputPassword);
            JOptionPane.showMessageDialog(null, user.returnLoginStatus(loginSuccess));
        }

        // After successful login, allow user to add tasks
        String menu = "1) Add tasks\n2) Show report\n3) Quit";
        String choice = JOptionPane.showInputDialog(menu);

        while (!choice.equals("3")) {
            if (choice.equals("1")) {
                String numTasksStr = JOptionPane.showInputDialog("How many tasks would you like to enter?");
                int numTasks = Integer.parseInt(numTasksStr);
                user.addTask(numTasks);
            } else if (choice.equals("2")) {
                user.displayReport();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
            choice = JOptionPane.showInputDialog(menu);
        }

        JOptionPane.showMessageDialog(null, "Goodbye!");
    }

    // Display a report of all tasks (Part 3)
    public void displayReport() {
        StringBuilder report = new StringBuilder("Task Report:\n");
        for (int i = 0; i < taskNames.size(); i++) {
            report.append(printTaskDetails(i)).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }
}