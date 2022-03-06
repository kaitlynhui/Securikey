package ui;

import model.EventLog;
import model.LogException;
import model.Password;
import model.PwordManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

// Represents application's main window frame.
public class GUI extends Fields implements ActionListener {

    // MODIFIES: this
    // EFFECTS: constructs a password manager and runs the application
    public GUI() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);
        setUpGUI();
        configureFields();
        setUpPanel();
        buttonFunctionality();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: constructs the basic visual components of the applications
    public void setUpGUI() {
        passwordList = new JList();
        frame = new JFrame();
        frame.setTitle("your password manager");
        panel = new JPanel();
        frame.setSize(800, 700);
        panel.setSize(800, 700);
        panel.setLayout(null);
        manager = new PwordManager("Your Password Manager");
        managerModel = new DefaultListModel();
        passwordList.setModel(managerModel);
        frame.add(panel);
        passwordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passwordList.setVisibleRowCount(10);
        scrollPane = new JScrollPane(passwordList);
    }

    // MODIFIES: this
    // EFFECTS: Helper method to create and set up buttons, inputs and labels on JFrame
    public void configureFields() {
        image = new JLabel();
        visualComponent = new ImageIcon("visual component.png");
        message = new JLabel("");
        searchFor = new JButton("search");
        add = new JButton("add");
        delete = new JButton("delete");
        test = new JButton("test");
        save = new JButton("save");
        load = new JButton("load");
        website = new JLabel("Website");
        searchInput = new JTextField();
        user = new JLabel("Username");
        password = new JLabel("Password");
        passInput = new JPasswordField(20);
        webInput = new JTextField(20);
        userInput = new JTextField(20);
        generatePassword = new JButton("generate");
        showPassword = new JCheckBox("show");
        edit = new JButton("edit");
        passInput.setEchoChar('*');
        image.setIcon(visualComponent);
        assignBounds();
    }

    // MODIFIES: this
    // EFFECTS: sets bounds for all elements on the JFrame
    public void assignBounds() {
        showPassword.setBounds(452, 490, 90, 28);
        generatePassword.setBounds(650, 448, 110, 28);
        save.setBounds(570, 70, 70, 28);
        load.setBounds(662, 70, 70, 28);
        searchFor.setBounds(312, 135, 70, 28);
        add.setBounds(392, 620, 70, 28);
        delete.setBounds(480, 620, 70, 28);
        edit.setBounds(568, 620, 70, 28);
        test.setBounds(662, 620, 70, 28);
        passwordList.setBounds(40, 174, 250, 474);
        website.setBounds(452, 174, 175, 25);
        webInput.setBounds(452, 215, 175, 25);
        user.setBounds(452, 291, 175, 25);
        userInput.setBounds(452, 332, 175, 25);
        password.setBounds(452, 408, 175, 25);
        passInput.setBounds(452, 449, 175, 25);
        searchInput.setBounds(40, 133, 250, 33);
        message.setBounds(452, 540, 500, 14);
        image.setBounds(350, 13, 100, 100);

    }

    // MODIFIES: this
    // EFFECTS: adds buttons and inputs to visible JPanel
    public void setUpPanel() {
        panel.add(searchInput);
        panel.add(add);
        panel.add(webInput);
        panel.add(passwordList);
        panel.add(website);
        panel.add(password);
        panel.add(passInput);
        panel.add(user);
        panel.add(userInput);
        panel.add(searchFor);
        panel.add(delete);
        panel.add(test);
        panel.add(save);
        panel.add(load);
        panel.add(message);
        panel.add(scrollPane);
        panel.add(generatePassword);
        panel.add(showPassword);
        panel.add(edit);
        panel.add(image);
    }

    // MODIFIES: this
    // EFFECTS: updates the visible password JList
    public void refreshPasswordList() {
        managerModel.removeAllElements();
        for (Password p : manager.getPasswords()) {
            managerModel.addElement(p.getWebsite());
        }
        clearFields();
    }

    // MODIFIES: this
    // EFFECTS: initializes the functionality of all menu buttons
    public void buttonFunctionality() {
        listFunctionality();
        addFunctionality();
        deleteFunctionality();
        editFunctionality();
        searchFunctionality();
        saveFunctionality();
        loadFunctionality();
        generateFunctionality();
        showPassFunctionality();
        testFunctionality();
        windowClosedFunctionality();
    }

    // MODIFIES: this
    // EFFECTS: when the JFrame is closed, the event log is printed to the console
    public void windowClosedFunctionality() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                lp = new Printer();
                try {
                    lp.printLog(EventLog.getInstance());
                } catch (LogException ex) {
                    System.out.println("System Error");
                }
                super.windowClosing(e);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: when a password is selected from the list, the appropriate information is displayed in the
    //          website, password and username fields.
    public void listFunctionality() {
        passwordList.setToolTipText("houses and displays all your passwords stored in the manager");
        passwordList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int passNum = passwordList.getSelectedIndex(); // # of the item you've clicked on
                if (passNum >= 0) {
                    Password p = manager.getPasswords().get(passNum);
                    webInput.setText(p.getWebsite());
                    userInput.setText(p.getUsername());
                    passInput.setText(p.getPassword());
                }

            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Highlights the corresponding password object in JList and displays
    //          the pertaining fields if the website is found in password manager.
    private void searchFunctionality() {
        searchFor.setToolTipText("searches for a password matching the inputted website");
        searchFor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String website = searchInput.getText().toLowerCase(Locale.ROOT);

                if (website == null) {
                    return;
                }
                if (manager.containsPassword(website)) {
                    Password p = manager.returnThePassword(website);
                    int foundIndex = managerModel.indexOf(p.getWebsite());

                    if (foundIndex >= 0) {
                        passwordList.setSelectedIndex(foundIndex);
                        webInput.setText(p.getWebsite());
                        userInput.setText(p.getUsername());
                        passInput.setText(p.getPassword());
                    }
                } else {
                    message.setText("Could not find any passwords for " + website + " " + "in manager");
                }
                searchInput.setText(null);
            }
        });

    }

    // REQUIRES: non-empty fields for the website, username or password
    // MODIFIES: this
    // EFFECTS: adds a password to the password manager when the add button is clicked
    public void addFunctionality() {
        add.setToolTipText("adds a new password to the manger");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String website = webInput.getText().toLowerCase(Locale.ROOT);
                String pass = passInput.getText();
                String username = userInput.getText();
                if (website.length() > 0 && pass.length() > 0 && username.length() > 0) {
                    Password p = new Password(website, pass, username);
                    manager.addPassword(p);
                    refreshPasswordList();
                } else {
                    message.setText("fields cannot be left empty.");
                }
            }
        });
    }

    // REQUIRES: a non-empty password manager as you require a password to delete
    // MODIFIES: this
    // EFFECTS: deletes a password from the password bank when the delete button is clicked
    public void deleteFunctionality() {
        delete.setToolTipText("deletes selected password");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int passNum = passwordList.getSelectedIndex();
                if (passNum >= 0) {
                    Password p = manager.getPasswords().get(passNum);
                    manager.deletePassword(p);
                    managerModel.removeElementAt(passNum);
                    clearFields();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: updates information for an existing password
    public void editFunctionality() {
        edit.setToolTipText("allows you to update an existing password in the manager");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int passNum = passwordList.getSelectedIndex();
                if (passNum >= 0) {
                    Password p = manager.getPasswords().get(passNum);
                    p.setUser(userInput.getText());
                    p.setWebsite(webInput.getText());
                    p.setPassword(passInput.getText());
                    refreshPasswordList();
                }
            }
        });
    }

    // EFFECTS: saves your password manager to file
    public void saveFunctionality() {
        save.setToolTipText("allows you to save your passwords to your files");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.openWriter();
                    jsonWriter.write(manager);
                    jsonWriter.closeWriter();
                    message.setText("Your passwords were successfully saved" + " to " + JSON_STORAGE);
                } catch (FileNotFoundException ex) {
                    message.setText("Unable to write to file: " + JSON_STORAGE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: opens and then loads password manager from file
    public void loadFunctionality() {
        load.setToolTipText("load an existing password manager from file");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    manager = jsonReader.read();
                    message.setText("Loaded password manager from " + " " + JSON_STORAGE);
                    refreshPasswordList();
                } catch (IOException ex) {
                    message.setText("Unable to read from file: " + JSON_STORAGE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: generates a random password of n characters
    public void generateFunctionality() {
        generatePassword.setToolTipText("generates a strong random character password for you");
        generatePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = (int) (Math.floor(Math.random() * (20 - 13 + 1)) + 13);
                String newPass = generatePassword(length);
                passInput.setText(newPass);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: reveals your password when show password is clicked
    public void showPassFunctionality() {
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPassword.isSelected()) {
                    passInput.setEchoChar((char) 0);
                } else {
                    passInput.setEchoChar('*');
                }
            }
        });
    }

    // EFFECTS: indicates the strength of your password based on the number of characters
    public void testFunctionality() {
        test.setToolTipText("allows you to test the strength of your current password");
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = passInput.getText();
                if (pass.length() > 13) {
                    message.setText("your password is very strong");
                } else {
                    message.setText("your password is weak, generate a new one");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: clears all text fields
    public void clearFields() {
        webInput.setText(null);
        userInput.setText(null);
        passInput.setText(null);
        searchInput.setText(null);
        message.setText(null);
    }

    // EFFECTS: Generates a random character by generating a random number, then converting it to it's ASCII value
    public static char randomCharacter() {
        int rand = (int) (Math.random() * 62);

        if (rand <= 9) {
            int ascii = rand + 48; // 0 - 9 are 48 - 57 in ASCII
            return (char) (ascii);

        } else if (rand <= 35) {
            int ascii = rand + 55; // uppercase letters 10-35 and 65-90 in ASCII
            return (char) (ascii);
        } else {
            int ascii = rand + 61; // lowercase letters 36-61 and 97-122 in ASCII
            return (char) (ascii);
        }
    }

    //EFFECTS: creates a new password by adding a random character to an empty string
    public String generatePassword(int length) {
        String randomPassword = "";
        for (int i = 0; i < length; i++) {
            randomPassword = randomPassword + randomCharacter();
        }
        return randomPassword;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}