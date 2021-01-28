// Declares AdminPanel.java as part of the ChromebookManager package
package ChromebookManager;

// Imports necessary for program to run
import javax.swing.*;
import java.awt.Font;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class AdminPanel extends JFrame {
  // Initialization of JLabels in the AdminPanel GUI
  JLabel programTitle = new JLabel("Admin Panel");
  JLabel tableTitle = new JLabel("Log of Chromebooks Signed Out & Returned");
  
  // Initialization of JButtons in the AdminPanel GUI
  JButton changePassword = new JButton("Change Password");
  JButton modifyChromebooks = new JButton("Modify Chromebooks");

  // Initialization of JTable and its scrollpane in the AdminPanel GUI
  DefaultTableModel model = new DefaultTableModel();
  JTable table = new JTable(model);
  JScrollPane pane = new JScrollPane(table);

  // Initialization of fonts used in the AdminPanel GUI
  Font bigSerif = new Font("serif", Font.PLAIN, 30);
  Font smallSerif = new Font("serif", Font.PLAIN, 18);

  // Number of ModifyChromebook GUI windows open (Program does not let its size exceed 1)
  public static ArrayList<ModifyChromebooks> modifyChromebookWindows = new ArrayList<ModifyChromebooks>();
    
  // Method which configures the 'Admin Panel' title/JLabel in the top of the GUI
  public void top() {
    // Configures the location and font of the 'Admin Panel' title/JLabel
    programTitle.setBounds(30, -20, 400, 100);
    programTitle.setFont(bigSerif);
  }

  // Method which configures the table in the GUI which displays the log of Chromebooks signed out
  public void table() {
    // Configured so that the table is not editable, changeable or resizeable
    table.setDefaultEditor(Object.class, null);
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setResizingAllowed(false);
      
    // Configured so that the rows/columns/cells in the table are unselectable since the user does not need to be able to select them
    table.setFocusable(false);
    table.setRowSelectionAllowed(false);
      
    // Configures the location and font of the JLabel 'Log of Chromebooks Signed Out & Returned' above the table
    tableTitle.setFont(smallSerif);
    tableTitle.setBounds(230, 22, 400, 100);

    // Adds each column to the table
    model.addColumn("Cart");
    model.addColumn("Laptop");
    model.addColumn("Student ID");
    model.addColumn("First Name");
    model.addColumn("Last Name");
    model.addColumn("Date");
    model.addColumn("Time");
    model.addColumn("Status");

    // Configures the widths of each column
    table.getColumnModel().getColumn(0).setPreferredWidth(50);
    table.getColumnModel().getColumn(1).setPreferredWidth(50);
    table.getColumnModel().getColumn(2).setPreferredWidth(85);
    table.getColumnModel().getColumn(3).setPreferredWidth(80);
    table.getColumnModel().getColumn(4).setPreferredWidth(80);
    table.getColumnModel().getColumn(5).setPreferredWidth(75);
    table.getColumnModel().getColumn(6).setPreferredWidth(60);
    table.getColumnModel().getColumn(7).setPreferredWidth(87);

    // Centers the text in every cell in table
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment( SwingConstants.CENTER );
    for (int i = 0; i < table.getColumnCount(); i++){
      table.setDefaultRenderer(table.getColumnClass(i), renderer);
    }

    // Configures the table's location and row height, and makes it so that it does not automatically resize itself
    table.setRowHeight(35);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    pane.setBounds(90, 90, 585, 385);
  }

  // Method which configures the 'Change Password' button and the 'Modify Chromebooks' button in the bottom of the GUI 
  public void bottom() {
    changePassword.setBounds(120, 493, 225, 50);
    modifyChromebooks.setBounds(420, 493, 225, 50);
  }

  // Method which opens the Modify Chromebooks window when its respective button is pressed
  public void actionModifyChromebooks() {
    modifyChromebooks.addActionListener(new ActionListener() {
      public void actionPerformed (ActionEvent e) {
        try {
          // If there are no other ModifyChromebook windows open, then a new one is created/opened and added to the array storing each instance (limited to 1)
          if (modifyChromebookWindows.size() == 0) {
            modifyChromebookWindows.add(new ModifyChromebooks());
          }
        }
        // Prevents the program from crashing due to potential error(s)
        catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    });
  }
  
  // Method which opens up the change password popup when its respective button is pressed
  public void actionChangePassword() {
    changePassword.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          // Retrieves the password of the program
          String password = new Scanner(new File("password.txt")).nextLine();
          // Tnitializes 3 strings which hold the values of the 3 JPasswordFields in the popup with empty String values which will be overwritten by the user's entered values
          String currentPassword = "";
          String newPassword = "";
          String confirmNewPassword = "";
          
          // Creates the JFrame for the popup and its respective JPasswordField components
          JFrame parent = new JFrame();
          JPasswordField currentPasswordField = new JPasswordField();
          JPasswordField newPasswordField = new JPasswordField();
          JPasswordField confirmNewPasswordField = new JPasswordField();
          
          // Creates an object which stores the layout/prompts of the change password popup
          Object[] message = {
            "Current password:", currentPasswordField,
            "New password:", newPasswordField,
            "Confirm new password:", confirmNewPasswordField,
          };
          
          // Creates/opens the popup with an 'Ok' and 'Cancel' button prompting the user to change the password
          int option = JOptionPane.showConfirmDialog(parent, message, "Set new password", JOptionPane.OK_CANCEL_OPTION);
          
          // If the user clicks the 'Ok' button, the pre-initialized strings above are overwritten with the user's entered values from each JPasswordField
          if (option == JOptionPane.OK_OPTION) {
            currentPassword = new String (currentPasswordField.getPassword());
            newPassword = new String(newPasswordField.getPassword());
            confirmNewPassword = new String (confirmNewPasswordField.getPassword());
          }
          
          // If the user clicks 'Ok' and entered valid and correct data in the 3 required fields, the passworld is sucessfully changed
          if (currentPassword.equals(password) && newPassword.equals(confirmNewPassword) && !(newPassword.equals("")) && !(newPassword.equals(password))) {
            JOptionPane.showMessageDialog(null, "Password was successfully changed.");
            FileWriter passwordWriter = new FileWriter("password.txt");
            passwordWriter.write(newPassword);
            passwordWriter.close();
          }
          // If the new passwor identical to the current password, the password remains the same and the user is given an error popup message
          else if (option == JOptionPane.OK_OPTION && newPassword.equals(password)) {
            JOptionPane.showMessageDialog(null, "Your new password matches your current password already.");
          }
          // If the user clicked 'Ok' but the data they entered was invalid or incorrect, the user is given an error popup message
          else if (option == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "You did not fill in the required fields correctly.\nPlease try again.");
          }
        }
        // Prevents the program from crashing due to potential error(s)
        catch (IOException exception) {
          exception.printStackTrace();
        }
      }
    });
  }

  // Method which removes the AdminPanel object in the ChromebookManager class when the user closes the window/Admin Panel
  public void actionCloseAdminPanel() {
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        ChromebookManager.adminPanelMenus.clear();
      }
    });
  }

  // Method which updates the table for the log of chromebooks when a change is made to the data or when the Admin Panel is initialized/opened
  public void updateTable() throws Exception {
    // The table is cleared of all its rows/cells by setting the row count to 0
    model.setRowCount(0);
     
    // Reads the log file storing the log data, then adds each row with its data
    File logFile = new File("log.txt");
    Scanner logScanner = new Scanner(logFile);
    while (logScanner.hasNextLine()) {
      String loggedChromebook = logScanner.nextLine();
      String[] loggedChromebookInfo = loggedChromebook.split(" ");
      model.addRow(new Object[]{loggedChromebookInfo[0], 
        loggedChromebookInfo[1], loggedChromebookInfo[2], 
        loggedChromebookInfo[3], loggedChromebookInfo[4], 
        loggedChromebookInfo[5] + " " + loggedChromebookInfo[6], 
        loggedChromebookInfo[7], loggedChromebookInfo[8]});
    }
  }
    
  // Constructor method which runs when an instance/object of AdminPanel is created
  public AdminPanel() throws Exception {
    // Puts the elements of the GUI on-screen with their correct configurations
    top();
    table();
    bottom();
    
    // Calls a method which adds an action listener for each button on the GUI as well as an action listener for when the user closes the Admin Panel
    actionModifyChromebooks();
    actionChangePassword();
    actionCloseAdminPanel();
    
    // Puts the titles, buttons and scroll-pane for the table on the GUI
    add(programTitle);
    add(tableTitle);
    add(changePassword);
    add(modifyChromebooks);
    add(pane);
    
    // Configures the JFrame's title, size, visibility and makes it an unresizeable window
    setTitle("Admin Panel");
    setSize(800, 600); 
    setLayout(null);
    setResizable(false);
    setVisible(true);
    
    // Updates the table by calling this method to fill the table with its data
    updateTable();
  }
}
