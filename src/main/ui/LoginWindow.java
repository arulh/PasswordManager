package ui;

import model.User;
import persistence.JsonFile;
import persistence.LoadUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Represents a login window
public class LoginWindow extends Window implements ActionListener {

    private static final int WINDOW_COMPONENTS_Y_POS = 150;
    private static final int WINDOW_COMPONENTS_X_POS = 230;

    private User user;
    private LoadUser lu;
    File[] directories;
    File file = new File(JsonFile.ROOT_DIRECTORY);

    private JButton loginButton;
    private JTextField usernameField;
    private JTextField passwordField;
    private JMenuItem createAccountMenu;

    // EFFECTS: loads file names from users folder and launches window
    public LoginWindow() {
        directories = file.listFiles();
        launchWindow();
    }

    // EFFECTS: launches login window with all the window components
    @Override
    protected void launchWindow() {
        frame = new JFrame();
        frame.setLayout(null);

        // window components
        addMenuBar();
        addKeyImage();
        loginButton = createButton("Login", WINDOW_COMPONENTS_X_POS, WINDOW_COMPONENTS_Y_POS + 125, this);
        usernameField = createTextBox("Username", WINDOW_COMPONENTS_X_POS - 50, WINDOW_COMPONENTS_Y_POS + 25);
        passwordField = createPasswordField(WINDOW_COMPONENTS_X_POS - 50, WINDOW_COMPONENTS_Y_POS + 75);
        createLabel("USERNAME:", WINDOW_COMPONENTS_X_POS - 140, WINDOW_COMPONENTS_Y_POS + 35);
        createLabel("PASSWORD:", WINDOW_COMPONENTS_X_POS - 140, WINDOW_COMPONENTS_Y_POS + 85);

        windowSettings("LOGIN WINDOW", new Color(113, 183, 110));
        centerWindow();
    }

    // MODIFIES: this
    // EFFECTS: adds menu bar to frame with menu items
    @Override
    protected void addMenuBar() {
        JMenuBar mb = new JMenuBar();
        createAccountMenu = new JMenuItem("Create Account");
        createAccountMenu.addActionListener(this);
        mb.add(createAccountMenu);
        frame.setJMenuBar(mb);
    }

    // EFFECTS: - when login button is clicked, if login credentials are valid, then loads user window
    //          - when create account menu option is clicked, creates account for user
    //          - finally, loads user window
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validLogin(username, password)) {
                user = new User(lu.getUsername(), lu.getEmail(), lu.getAccountKey(), lu.getId());
                lu.loadPasswords();
                user.setPasswordList(lu.getPasswordList());
                new UserWindow(user);
                frame.dispose();
            }
        } else if (e.getSource() == createAccountMenu) {
            createAccount();
            new UserWindow(user);
            frame.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds key image along with application title
    private void addKeyImage() {
        ImageIcon key = new ImageIcon("data/key.png");

        Image keyImage = key.getImage();
        Image scaledImage = keyImage.getScaledInstance(100, 60, Image.SCALE_SMOOTH);
        key = new ImageIcon(scaledImage);

        JLabel label = new JLabel(key);
        label.setIcon(key);
        label.setText("Password Manager");
        label.setFont(new Font("TimesRoman", Font.BOLD, 28));
        label.setForeground(Color.BLUE);
        Dimension size = label.getPreferredSize();
        label.setBounds(65, 60, size.width, size.height);
        frame.add(label);
    }

    // EFFECTS: prompts user to enter account details and creates the account
    private void createAccount() {
        String name = JOptionPane.showInputDialog("Enter username");
        String email = JOptionPane.showInputDialog("Enter email");
        String password = JOptionPane.showInputDialog("Enter password");

        user = new User(name, email, password, User.generateID());
    }

    // EFFECTS: returns true if entered login details are valid
    private boolean validLogin(String name, String key) {
        for (File f : directories) {
            if (f.getName().contains(name.toUpperCase())) {
                lu = new LoadUser(f.getPath());
                return lu.checkLoginDetails(name, key);
            }
        }
        return false;
    }
}
