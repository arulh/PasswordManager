package ui;

import exceptions.NotInListException;
import model.Event;
import model.EventLog;
import model.Password;
import model.User;
import persistence.SaveUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a window specific to the user
public class UserWindow extends Window implements ActionListener {

    private User currentUser;
    private SaveUser json;
    private EventLog events;


    private int spacing = 20;
    private JButton addPassword;
    private JButton removePassword;
    private JMenuItem logoutMenu;

    // EFFECTS: sets current user to the given user and launches user
    public UserWindow(User user) {
        events = EventLog.getInstance();
        currentUser = user;
        launchWindow();
    }

    // EFFECTS: launches user window with all necessary components
    @Override
    protected void launchWindow() {
        frame = new JFrame();
        frame.setLayout(null);

        // window components
        addMenuBar();
        viewPasswords();
        addPassword = createButton("ADD", WINDOW_WIDTH / 4, WINDOW_WIDTH - 100, this);
        removePassword = createButton("REMOVE", WINDOW_WIDTH / 2, WINDOW_WIDTH - 100, this);

        windowSettings("USER WINDOW", new Color(169, 211, 167));
        centerWindow();
    }

    // MODIFIES: this
    // EFFECTS: adds menu bar to frame with menu items
    @Override
    protected void addMenuBar() {
        JMenuBar mb = new JMenuBar();
        logoutMenu = new JMenuItem("Logout");
        logoutMenu.addActionListener(this);
        mb.add(logoutMenu);
        frame.setJMenuBar(mb);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates labels for each password in user with account and key
    private void viewPasswords() {
        for (Password p : currentUser.getPasswordList()) {
            createLabel(p.getAccountName(), WINDOW_WIDTH / 4, spacing);
            createLabel(p.getAccountPassword(), WINDOW_WIDTH / 2, spacing);
            spacing += 20;
        }
    }

    // EFFECTS: - when add password button is clicked, adds password to user
    //          - when remove password button is clicked, removes password from user
    //          - when logout menu is clicked, saves user and exits application
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPassword) {
            addPassword();
        } else if (e.getSource() == removePassword) {
            removePassword();
        } else if (e.getSource() == logoutMenu) {
            logout();
        }
    }

    // MODIFIES: user
    // EFFECTS: adds password to user with given account name and password
    private void addPassword() {
        String name = JOptionPane.showInputDialog("Enter account name:");
        String key = JOptionPane.showInputDialog("Enter password");
        currentUser.addPassword(name, key);
        spacing = 20;
        frame.dispose();
        launchWindow();
    }

    // MODIFIES: user
    // EFFECTS: removes password from user based on account name
    private void removePassword() {
        String name = JOptionPane.showInputDialog("Enter account name to be removed:");
        try {
            currentUser.removePassword(name);
            spacing = 20;
            frame.dispose();
            launchWindow();
        } catch (NotInListException error) {
            // do nothing
        }
    }

    // EFFECTS: saves user to file and exits application
    private void logout() {
        json = new SaveUser(currentUser);
        json.write();
        frame.dispose();

        printEvents();

        System.exit(0);
    }

    private void printEvents() {
        for (Event e : events) {
            System.out.println(e.toString() + "\n");
        }
    }
}
