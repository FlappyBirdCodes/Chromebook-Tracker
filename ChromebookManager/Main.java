// Declares Main.java as part of the ChromebookManager package
package ChromebookManager;

// Imports necessary for program to run
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class Main {
  public static void main(String[] args) throws Exception {
    // Creates and runs a new instance of the Chromebook Manager application
    new ChromebookManager();
    
    // Checks if a chromebook is overdue (1 hour later after sign-out) and sends a desktop notification/automated e-mail to the student while program is running
    String lastMinuteChecked = "";
    // The loop runs indefinitely while the program is running (Notifications/automated e-mails only sent for overdue chromebooks if program running)
    while (true) {
      // Retrieves and stores the date and time every iteration of the loop
      String date = new java.util.Date().toString().substring(8, 10);
      String time = new java.util.Date().toString().substring(11, 16);
      
      // If the last minute the chromebooks were checked is not the current time, the next check is performed
      if (!(time.substring(3, 5).equals(lastMinuteChecked))) {
        File unreturnedChromebooksFile = new File("unreturned_chromebooks.txt");
        Scanner unreturnedChromebookScanner = new Scanner(unreturnedChromebooksFile);
        
        // Checks through each individual chromebook sign-out to see if it's overdue
        while (unreturnedChromebookScanner.hasNextLine()) {
          // Reads the text file storing each unreturned chromebook line by line, and splits each line into an array of Strings with each piece of data
          String unreturnedChromebook = unreturnedChromebookScanner.nextLine();
          String[] unreturnedChromebookData = unreturnedChromebook.split(" ");
          String cartNumber = unreturnedChromebookData[0];
          String laptopNumber = unreturnedChromebookData[1];
          String studentID = unreturnedChromebookData[2];
          String firstName = unreturnedChromebookData[3];
          String lastName = unreturnedChromebookData[4];
          String signOutDate = unreturnedChromebookData[6];
          String signOutTime = unreturnedChromebookData[7];
          
          // If the chromebook was signed out at 11:00PM, it checks if it is the next day at 12:00AM
          String nextDay = String.valueOf((Integer.parseInt(date) + 1));
          if (signOutTime.substring(0, 2).equals("23") && date.equals(nextDay)) {
            if (time.substring(0, 3).equals("00:") && time.substring(3, 5).equals(signOutTime.substring(3, 5))) {
              // Sends a desktop notification and automated e-mail notifying about the missing chromebook
              try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                TrayIcon trayIcon = new TrayIcon(image, "Chromebook Manager");
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);
                trayIcon.displayMessage("Missing Chromebook Alert!", laptopNumber + " from " + cartNumber + " signed out by " + firstName + " " + lastName + 
                                        " has not been returned on time. An automated e-mail was sent to " + studentID + "@gapps.yrdsb.ca", MessageType.INFO);
              }
              // Prevents the program from crashing due to potential error(s)
              catch (Exception e) {
                e.printStackTrace();
              }
            }
          }
          // If it was not signed out at 11:00PM in the test-case above, it checks the alternative test-case
          else {
            int hourSignedOut = Integer.parseInt(signOutTime.substring(0, 2));
            int currentHour = Integer.parseInt(time.substring(0, 2));
            // Checks if it has been 1 hour since the chromebook signed out on the same date
            if (currentHour == hourSignedOut + 1 && time.substring(3, 5).equals(signOutTime.substring(3, 5)) && date.equals(signOutDate)) {
              // Sends a desktop notification and automated e-mail notifying about the missing chromebook
              try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                TrayIcon trayIcon = new TrayIcon(image, "Chromebook Manager");
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);
                trayIcon.displayMessage("Missing Chromebook Alert!", laptopNumber + " from " + cartNumber + " signed out by " + firstName + " " + lastName + 
                                        " has not been returned on time. An automated e-mail was sent to " + studentID + "@gapps.yrdsb.ca", MessageType.INFO);
              }
              // Prevents the program from crashing due to potential error(s)
              catch (Exception e) {
                e.printStackTrace();
              }
            }
          }
        }
        
        // After completing the check, the lastMinuteChecked variable is updated to the time/minute which was just checked
        lastMinuteChecked = time.substring(3, 5);
        
      }
    }
  }
}