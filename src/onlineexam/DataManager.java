package onlineexam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple file-based persistence using Java serialization.
 * Keeps users and questions in files users.dat and questions.dat in working dir.
 */
public class DataManager {
    private static final String USERS_FILE = "users.dat";
    private static final String QUESTIONS_FILE = "questions.dat";

    public static List<User> users = new ArrayList<>();
    public static List<Question> questions = new ArrayList<>();

    public static void init() {
        loadUsers();
        loadQuestions();
        if (users.isEmpty()) {
            // create default user
            users.add(new User("student", "1234", "Default Student", "student@example.com"));
            saveUsers();
        }
        if (questions.isEmpty()) {
            createSampleQuestions();
            saveQuestions();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadUsers() {
        File f = new File(USERS_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            users = (List<User>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Failed to load users: " + e.getMessage());
            users = new ArrayList<>();
        }
    }

    public static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (Exception e) {
            System.err.println("Failed to save users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadQuestions() {
        File f = new File(QUESTIONS_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            questions = (List<Question>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Failed to load questions: " + e.getMessage());
            questions = new ArrayList<>();
        }
    }

    public static void saveQuestions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(QUESTIONS_FILE))) {
            oos.writeObject(questions);
        } catch (Exception e) {
            System.err.println("Failed to save questions: " + e.getMessage());
        }
    }

    private static void createSampleQuestions() {
        questions.add(new Question(1, "What is the size of int in Java (approximately)?",
                new String[]{"16 bits", "32 bits", "64 bits", "Depends on platform"}, 'B'));
        questions.add(new Question(2, "Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "uses"}, 'B'));
        questions.add(new Question(3, "Which interface must be implemented to create a thread?",
                new String[]{"Runnable", "Serializable", "Cloneable", "Comparable"}, 'A'));
        questions.add(new Question(4, "Which collection allows duplicate elements in Java?",
                new String[]{"Set", "Map", "List", "None"}, 'C'));
        questions.add(new Question(5, "Which method is the entry point of a Java application?",
                new String[]{"start()", "main()", "init()", "run()"}, 'B'));
    }

    // find user by username + password
    public static User findUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // find user by username
    public static User findByUsername(String username) {
        for (User u : users) if (u.getUsername().equals(username)) return u;
        return null;
    }

    // update user in list and persist
    public static void updateUser(User updated) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updated.getUsername())) {
                users.set(i, updated);
                saveUsers();
                return;
            }
        }
        // if not found, add new
        users.add(updated);
        saveUsers();
    }
}
