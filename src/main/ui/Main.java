package ui;


import java.io.FileNotFoundException;

// represents the actual entry point into the application
public class Main {
    public static void main(String[] args) {
        try {
            new GUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}


