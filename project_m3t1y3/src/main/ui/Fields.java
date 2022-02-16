package ui;

import model.Password;
import model.PwordManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// represents the declaration of fields for the GUI class
public abstract class Fields {
    protected static final String JSON_STORAGE = "./data/workroom.json";
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;
    protected JFrame frame;
    protected JPanel panel;
    protected JLabel website;
    protected JTextField webInput;
    protected JLabel user;
    protected JTextField userInput;
    protected JList passwordList;
    protected JLabel password;
    protected JPasswordField passInput;
    protected JTextField searchInput;
    protected JButton searchFor;
    protected JButton add;
    protected JButton delete;
    protected JButton test;
    protected JButton save;
    protected JButton load;
    protected PwordManager manager;
    protected DefaultListModel managerModel;
    protected JLabel message;
    protected JButton generatePassword;
    protected JScrollPane scrollPane;
    protected JCheckBox showPassword;
    protected JButton edit;
    protected ImageIcon visualComponent;
    protected JLabel image;
    protected LogPrinter lp;


}
