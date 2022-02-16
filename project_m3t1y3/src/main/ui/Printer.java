package ui;

import model.Event;
import model.EventLog;

// prints event logs to console

public class Printer implements LogPrinter {
    @Override
    public void printLog(EventLog el) {
        for (Event next: el) {
            System.out.println(next.toString());
        }
    }
}
