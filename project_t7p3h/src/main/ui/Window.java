package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Represents elements pertaining to a window
public abstract class Window extends JFrame {

    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_WIDTH = 500;
    private final Font font = new Font("SansSerif", Font.BOLD, 14);

    protected JFrame frame;

    // EFFECTS: launches window with components
    protected abstract void launchWindow();

    // EFFECTS: adds menu bar to current window
    protected abstract void addMenuBar();

    // MODIFIES: this
    // EFFECTS: creates label with given text and x and y coordinates
    protected JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setBounds(x, y, 200, 20);
        label.setFont(font);
        frame.add(label);
        return label;
    }

    // MODIFIES: this
    // EFFECTS: creates button with given text, x and y coordinates, and ActionListener
    protected JButton createButton(String text, int x, int y, ActionListener al) {
        JButton button = new JButton();
        button.setBounds(x, y, 100, 50);
        button.addActionListener(al);
        button.setText(text);
        frame.add(button);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates text box with given text and x x and y coordinates
    protected JTextField createTextBox(String text, int x, int y) {
        JTextField textBox = new JTextField();
        textBox.setBounds(x, y, 200, 40);
        textBox.setText(text);
        textBox.setFont(font);
        frame.add(textBox);
        return textBox;
    }

    // MODIFIES: this
    // EFFECTS: creates password field with given x and y coordinates
    protected JPasswordField createPasswordField(int x, int y) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(x, y, 200, 40);
        frame.add(passwordField);
        return passwordField;
    }

    // MODIFIES: this
    // EFFECTS: centers window on screen
    protected void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    // MODIFIES: this
    // EFFECTS: contains the default settings for window with provided title and background colour
    protected void windowSettings(String text, Color color) {
        frame.setTitle(text);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setVisible(true);
        frame.getContentPane().setBackground(color);
    }
}
