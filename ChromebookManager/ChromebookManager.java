// Declares ChromebookManager.java as part of the ChromebookManager package
package ChromebookManager;

// Module imports needed for the program
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class ChromebookManager extends JFrame {
  // Initialization of the labels used in the GUI
  JLabel programTitle = new JLabel("Chromebook Sign-Out Manager");
  JLabel selectLaptopCart = new JLabel("Select Laptop Cart");
  JLabel selectLaptopNumber = new JLabel("Select Laptop Number");
  JLabel enterFirstName = new JLabel("Enter First Name");
  JLabel enterLastName = new JLabel("Enter Last Name");
  JLabel enterStudentID = new JLabel("Enter Student ID");

  // Initialization of the buttons used in the GUI
  JButton adminPanel = new JButton("Admin Panel");
  JButton borrowChromebook = new JButton("Borrow Chromebook");
  JButton returnChromebook = new JButton("Return Chromebook");

  // Initialization of the textboxes used in the GUI
  JTextField firstName = new JTextField(16);
  JTextField lastName = new JTextField(16);
  JTextField studentID = new JTextField(16);

  // Initialization of the comboboxes used in the GUI with their contents/elements (Cart/Laptop numbers)
  private static String[] carts = {"C1", "C2", "C3", "C4"};
  private static JComboBox laptopCarts = new JComboBox(carts);
  
  // Initialization of the laptop numbers in each cart 
  public static String[] numbers = {"L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11", "L12"};
  public static String[] numbers2 = {"L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11", "L12"};
  public static String[] numbers3 = {"L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11", "L12"};
  public static String[] numbers4 = {"L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11", "L12"};
  public static JComboBox laptopNumbers = new JComboBox(numbers);

  // Initialization of the table used in the GUI (For unreturned chromebooks)
  public static DefaultTableModel model = new DefaultTableModel();
  private static JTable table = new JTable(model);
  JScrollPane pane = new JScrollPane(table);

  // Setting fonts needed
  Font bigSerif = new Font("serif", Font.PLAIN, 30);
  Font smallSerif = new Font("serif", Font.PLAIN, 18);

  // Initialization of ArrayList storing number of Admin Panel menus opened (limited to just 1 instace)
  public static ArrayList<AdminPanel> adminPanelMenus = new ArrayList<AdminPanel>();
  
  // Creates title and adminPanel button (top section of GUI)
  public void top() {
    programTitle.setBounds(50, -20, 400, 100);
    programTitle.setFont(bigSerif);
    adminPanel.setBounds(630, 5, 150, 40);
  }

  // Creates form (left section of GUI)
  public void form() {
    // Creates dropdowns
    selectLaptopCart.setBounds(50, 35, 400, 100);
    selectLaptopCart.setFont(smallSerif);
    laptopCarts.setBounds(50, 110, 205, 30);
    selectLaptopNumber.setBounds(50, 120, 400, 100);
    selectLaptopNumber.setFont(smallSerif);
    laptopNumbers.setBounds(50, 195, 205, 30);

    // Creates text fields for input
    enterStudentID.setBounds(50, 205, 400, 100);
    enterStudentID.setFont(smallSerif);
    studentID.setBounds(50, 280, 205, 30);
    enterFirstName.setBounds(50, 290, 400, 100);
    enterFirstName.setFont(smallSerif);
    firstName.setBounds(50, 365, 205, 30);
    enterLastName.setBounds(50, 375, 400, 100);
    enterLastName.setFont(smallSerif);
    lastName.setBounds(50, 450, 205, 30);

    // Creates submit button 
    borrowChromebook.setBounds(73, 500, 155, 40);
  }

  // Creates table of unreturned chromebooks (right section of GUI)
  public void table() {
    // Sets the elements of the table to be uneditable by the user  
    table.setDefaultEditor(Object.class, null);
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setResizingAllowed(false);
    
    // Makes it so that only one row is selectable in the table
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    // Adding columns to the table
    model.addColumn("Cart");
    model.addColumn("Laptop");
    model.addColumn("Student ID");
    model.addColumn("First Name");
    model.addColumn("Last Name");
    model.addColumn("Date");
    model.addColumn("Time");

    // Setting width of columns
    table.getColumnModel().getColumn(0).setPreferredWidth(56);
    table.getColumnModel().getColumn(1).setPreferredWidth(59);
    table.getColumnModel().getColumn(5).setPreferredWidth(62);

    // Centers all cells in table
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment( SwingConstants.CENTER );
    for (int i = 0; i < table.getColumnCount(); i++){
      table.setDefaultRenderer(table.getColumnClass(i), renderer);
    }

    // Creates table and makes it scrollable
    table.setRowHeight(35);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    pane.setBounds(270, 90, 495, 395);

    // Creates return chromebook button
    returnChromebook.setBounds(445, 500, 150, 40);
  }

  // Checks if cart has been changed when user clicks on the 'Select Laptop Cart' combobox
  public void actionChangeCart() {
    laptopCarts.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String cart = laptopCarts.getSelectedItem().toString();

        // Changes laptops if cart has been changed
        String[] currentNumbers = numbers;
        if (cart.equals("C1")) {
          currentNumbers = numbers;
        } 
        else if (cart.equals("C2")) {
          currentNumbers = numbers2;
        }
        else if (cart.equals("C3")) {
          currentNumbers = numbers3;
        }
        else {
          currentNumbers = numbers4;
        }
        
        // Removes all the current numbers in the laptop numbers list, and replaces them with the new current numbers of the newly selected cart
        laptopNumbers.removeAllItems();
        for (String number : currentNumbers) {
          laptopNumbers.addItem(number);
        }
      } 
    });
  }

  // Method which borrows a chromebook when the user enters their information and clicks the borrow chromebook button
  public void actionBorrowChromebook() {
    // Borrows chromebook from the cart
    borrowChromebook.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Checks if the user has filled out the necessary info to borrow the chromebook
        try {
          // If the user did not fill in every textfield they are given an error popup message
          if (studentID.getText().length() <= 0 || firstName.getText().length() <= 0 || lastName.getText().length() <= 0) {
            JOptionPane.showMessageDialog(null, "You did not fill in all the required fields.");
          }
          // If the user's entered data contains spaces, they are given an error popup message
          else if (studentID.getText().contains(" ") || firstName.getText().contains(" ") || lastName.getText().contains(" ")) {
            JOptionPane.showMessageDialog(null, "The required fields may not contain spaces.");
          }
          // If the user attempts to sign out a chromebook that has already been signed out, they are given an error popup message
          else if (hasChromebookSignedOut(studentID.getText())) {
            JOptionPane.showMessageDialog(null, "You already have a chromebook signed out.\nPlease return it before signing out another.");
          }
          // If the user/student cannot be found in the system's database, they are given an error popup message
          else if (!(validateStudent(studentID.getText(), firstName.getText(), lastName.getText()))) {
            JOptionPane.showMessageDialog(null, "Student could not be found in the database.");
          }
          // If the user passed the above checks, they borow/signout the chromebook if they can be validated in the system's database
          else if (validateStudent(studentID.getText(), firstName.getText(), lastName.getText())) {
            // Confirms borrow of chromebook
            int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to borrow this chromebook?", "Confirm Borrow",
                                                          JOptionPane.YES_NO_OPTION,
                                                          JOptionPane.QUESTION_MESSAGE);
            
            // If the user click 'Yes' the chromebook sign-out is fulfilled
            if (confirmed == 0) {
              // Retrieves all necessary info for sign-out information and stores it in variables
              String cart = laptopCarts.getSelectedItem().toString();
              String laptop = laptopNumbers.getSelectedItem().toString();
              String student_ID = studentID.getText();
              String first_Name = firstName.getText();
              String last_Name = lastName.getText();
              java.util.Date dateTime = new java.util.Date();
              String currentDate = dateTime.toString().substring(4, 10);
              String currentTime = dateTime.toString().substring(11, 16);
              
              // Adds a row to the table with the retrieved information above
              model.addRow(new Object[]{cart, laptop, student_ID, first_Name, last_Name, currentDate, currentTime});
              
              // Adds the chromebook being signed out to the log of all chromebooks signed out
              File logFile = new File("log.txt.");
              Scanner logScanner = new Scanner(logFile);
              
              // Reads the log prior to chromebook being added and saves it to a String variable
              String currentLog = "";
              while (logScanner.hasNextLine()) {
                currentLog += logScanner.nextLine() + "\n";
              }
              
              // The log prior to the chromebook being added and a new line with the newly signed-out chromebook's information is written into the text file
              FileWriter logWriter = new FileWriter("log.txt");
              logWriter.write(currentLog + cart + " " + laptop + " " + student_ID + " " + first_Name + " " + last_Name + " " + currentDate + " " + currentTime + " " + "Unreturned");
              logWriter.close();
              
              // Reads the text file prior to chromebook being added and saves it to a String variable
              File unreturnedChromebooks = new File("unreturned_chromebooks.txt");
              Scanner unreturnedChromebookScanner = new Scanner(unreturnedChromebooks);
              String currentText = "";
              while (unreturnedChromebookScanner.hasNextLine()) {
                currentText += unreturnedChromebookScanner.nextLine() + "\n";
              }
              
              // Adds the chromebook being signed out to the text file storing all unreturned chromebooks
              FileWriter myWriter = new FileWriter("unreturned_chromebooks.txt");
              myWriter.write(currentText + cart + " " + laptop + " " + student_ID + " " + first_Name + " " + last_Name + " " + currentDate + " " + currentTime);
              myWriter.close();
              
              // Finds out which cart the laptop was taken from and removes the laptop from its respective cart's array of numbers
              cart = cart.substring(cart.length() - 1);
              if (cart.equals("1")) {
                numbers = removeLaptopFromCart(numbers, laptop);
              }
              else if (cart.equals("2")) {
                numbers2 = removeLaptopFromCart(numbers2, laptop);
              }
              else if (cart.equals("3")) {
                numbers3 = removeLaptopFromCart(numbers3, laptop);
              }
              else if (cart.equals("4")) {
                numbers4 = removeLaptopFromCart(numbers4, laptop);
              }
              
              // Resets all JComboboxes to original state
              laptopCarts.setSelectedIndex(0);
              laptopNumbers.removeAllItems();
              for (String number : numbers) {
                laptopNumbers.addItem(number);
              }
              
              // Resets all text fields to empty strings
              studentID.setText("");
              firstName.setText("");
              lastName.setText("");
              
              // Updates the table (log) in the admin panel with the new sign-out data
              if (adminPanelMenus.size() == 1) {
                adminPanelMenus.get(0).updateTable();
              }
            }
          }
        }
        // Prevents the program from crashing due to potential error(s) with i/o to text files
        catch (IOException javaIOexception) {
          javaIOexception.printStackTrace();
        }
        // Prevents the program from crashing due to potential error(s)
        catch (Exception javaLangException) {
          javaLangException.printStackTrace();
        }
      }
    });
  }

  // Method which removes a laptop from a given array
  public static String[] removeLaptopFromCart(String[] currentNumbers, String laptop) {
    // Removes laptop from cart with the specific index by converting it to an ArrayList, removing the laptop then converting it back to an Array and returning it
    ArrayList<String> currentNumbers2 = new ArrayList<String>(Arrays.asList(currentNumbers));
    int index = currentNumbers2.indexOf(laptop);
    currentNumbers2.remove(index);
    currentNumbers = currentNumbers2.toArray(new String[0]);
    return currentNumbers;
  }

  // Method which opens the password prompt and admin panel when the user clicks its respective button
  public void actionOpenAdminPanel() {
    // Opens the Admin Panel GUI when the user clicks the Admin Panel button
    adminPanel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          // Creates a popup prompting the user to enter the password
          String passwordEntered = "";
          String password = "";
          if (adminPanelMenus.size() <= 0) {
            JFrame parent = new JFrame();
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {
                "Enter password here:", passwordField
            };
            int option = JOptionPane.showConfirmDialog(parent, message, "Login", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                passwordEntered = new String(passwordField.getPassword());
                password = new Scanner(new File("password.txt")).nextLine();
            }
            // If the password entered is correct and the number of admin panels open is 0, then a new admin panel instance/window is created
            if (passwordEntered.equals(password) && !(option == JOptionPane.CANCEL_OPTION) && option == JOptionPane.OK_OPTION) {
              adminPanelMenus.add(new AdminPanel());
            }
            else if (option == JOptionPane.OK_OPTION) {
              JOptionPane.showMessageDialog(null, "Incorrect Password.");
            }
          }
        }
        // Prevents the program from crashing due to potential error(s) with i/o to text files
        catch (IOException javaIOexception) {
          javaIOexception.printStackTrace();
        }
        // Prevents the program from crashing due to potential error(s)
        catch (Exception javaLangException) {
          javaLangException.printStackTrace();
        }
      }
    });
  }
  
  // Method which returns a chromebook back to the system after the user clicks its respective button
  public void actionReturnChromebook() {
    // Returns chromebook back to the cart
    returnChromebook.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        // Retrieves the row the user selected on the table
        int row = table.getSelectedRow();
        if (row != -1) {

          // Confirms return of chromebook
          int confirmed = JOptionPane.showConfirmDialog(null,"Are you sure you want to return this chromebook?", "Confirm Return",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE);
          
          // If the user clicks 'Yes' then the chromebook is returned
          if (confirmed == 0) {

            // Getting values of the cart and the laptop
            Object cart = table.getModel().getValueAt(row, 0);
            String cart2 = String.valueOf(cart);
            cart2 = cart2.substring(cart2.length() - 1);
            Object laptop = table.getModel().getValueAt(row, 1);
            String laptop2 = String.valueOf(laptop);
            laptop2 = laptop2.substring(laptop2.length() - 1);
            int laptopInteger = Integer.parseInt(laptop2);

            // Adds laptop back to corresponding cart
            if (cart2.equals("1")) {
              numbers = addLaptopToCart(numbers, laptopInteger);
            }
            else if (cart2.equals("2")) {
              numbers2 = addLaptopToCart(numbers2, laptopInteger);
            }
            else if (cart2.equals("3")) {
              numbers3 = addLaptopToCart(numbers3, laptopInteger);
            }
            else if (cart2.equals("4")) {
              numbers4 = addLaptopToCart(numbers4, laptopInteger);
            }

            // Resets all JComboboxes to original state
            laptopCarts.setSelectedIndex(0);
            laptopNumbers.removeAllItems();
            for (String number : numbers) {
              laptopNumbers.addItem(number);
            }         
            
            // Marks the chromebook as returned in the log of all chromebooks signed out
            try {
              // Reads the current log file and saves it to a String variable
              File logFile = new File("log.txt");
              Scanner logScanner = new Scanner(logFile);
              String currentLog = "";
              while (logScanner.hasNextLine()) {
                currentLog += "\n" + logScanner.nextLine();
              }
              
              // Creates a substring of the currentLog to remove the extra new line at the first index
              currentLog = currentLog.substring(1, currentLog.length());
              
              // Retrieves the laptop data of the chromebook being returned from the table using the row value retrieved above
              String laptopData = String.valueOf(table.getValueAt(row, 0)) + " " + 
                String.valueOf(table.getValueAt(row, 1)) + " " + String.valueOf(table.getValueAt(row, 2)) + " " +
                String.valueOf(table.getValueAt(row, 3)) + " " + String.valueOf(table.getValueAt(row, 4)) + " " + 
                String.valueOf(table.getValueAt(row, 5)) + " " + String.valueOf(table.getValueAt(row, 6));
              
              // Adds the 'Returned' status to the chromebook's data so it can be added to the log
              String updatedLaptopData = laptopData + " Returned";
              int startIndexOfChromebook = currentLog.indexOf(laptopData);
              
              // Overwrites the chromebook's status (returned/unreturned) data in the log as returned
              FileWriter logWriter = new FileWriter("log.txt");
              // Depending on the location of the chromebook's data in the log, the log is written to its file differently
              if (startIndexOfChromebook == 0 && currentLog.contains("\n")) {
                logWriter.write(updatedLaptopData + currentLog.substring(updatedLaptopData.length() + 2, currentLog.length()));
              }
              else if (!currentLog.contains("\n")) {
                logWriter.write(updatedLaptopData);
              }
              else if (currentLog.contains("\n")) {
                logWriter.write(currentLog.substring(0, startIndexOfChromebook - 1) + "\n" + updatedLaptopData + currentLog.substring(startIndexOfChromebook + updatedLaptopData.length() + 2, currentLog.length()));
              }
              logWriter.close();
            }
            // Prevents the program from crashing due to potential error(s)
            catch (IOException exception) {
              exception.printStackTrace();
            }
            
            // Removes the chromebook from the text file storing its data
            try {
              // Retrieves the laptop data of the chromebook being returned from the table using the row value retrieved above
              File unreturnedChromebooks = new File("unreturned_chromebooks.txt");
              Scanner unreturnedChromebookScanner = new Scanner(unreturnedChromebooks);
              String currentText = "";
              while (unreturnedChromebookScanner.hasNextLine()) {
                currentText += "\n" + unreturnedChromebookScanner.nextLine();
              }
              
              // Creates a substring of the currentLog to remove the extra new line at the first index
              currentText = currentText.substring(1, currentText.length());
              
              // Retrieves the laptop data of the chromebook being returned from the table using the row value retrieved above
              String laptopData = String.valueOf(table.getValueAt(row, 0)) + " " + 
                String.valueOf(table.getValueAt(row, 1)) + " " + String.valueOf(table.getValueAt(row, 2)) + " " +
                String.valueOf(table.getValueAt(row, 3)) + " " + String.valueOf(table.getValueAt(row, 4)) + " " + 
                String.valueOf(table.getValueAt(row, 5)) + " " + String.valueOf(table.getValueAt(row, 6));
              
              // Finds the index at which the chromebook's data first starts/appears in the unreturned chromebooks file
              int startIndexOfChromebook = currentText.indexOf(laptopData);
              
              // Overwrites the unreturned chromebooks file with the chromebook's data having been removed
              FileWriter myWriter = new FileWriter("unreturned_chromebooks.txt");
              // Depending on the location of the chromebook's data in the text file, the text file is written to differently
              if (startIndexOfChromebook == 0 && currentText.contains("\n")) {
                myWriter.write(currentText.substring(0 + laptopData.length() + 1, currentText.length()));
              }
              else if (currentText.contains("\n")) {
                myWriter.write(currentText.substring(0, startIndexOfChromebook - 1) + currentText.substring(startIndexOfChromebook + laptopData.length(), currentText.length()));
              }
              else if (!currentText.contains("\n")) {
                myWriter.write("");
              }
              myWriter.close();
              
              // Updates the table (log) in the admin panel
              if (adminPanelMenus.size() == 1) {
                adminPanelMenus.get(0).updateTable();
              }
            }
            // Prevents the program from crashing due to potential error(s)
            catch (IOException javaIOException) {
              javaIOException.printStackTrace();
            }
            // Prevents the program from crashing
            catch (Exception javaLangException) {
              javaLangException.printStackTrace();
            }
            
            // Removes row from table
            model.removeRow(row);
            
          }
        }
        // If the user did not select a row from the table, they are given an error popup message and no laptop is removed
        else {
          JOptionPane.showMessageDialog(null, "You must select the chromebook which you would\nlike to return from the table above first.");
        }
      }
    });
  }

  // Checks if a student already has a chromebook signed out in the system
  public static boolean hasChromebookSignedOut(String studentID) throws Exception {
    // Reads the text fille, and checks the student ID for each chromebook's data to see if the student has already signed out a chromebook
    boolean hasChromebookSignedOut = false;
    File unreturnedChromebooksFile = new File("unreturned_chromebooks.txt");
    Scanner unreturnedChromebookScanner = new Scanner(unreturnedChromebooksFile);
    while (unreturnedChromebookScanner.hasNextLine()) {
      String unreturnedChromebook = unreturnedChromebookScanner.nextLine();
      String[] unreturnedChromebookInfo = unreturnedChromebook.split(" ");
      if (unreturnedChromebookInfo[2].equals(studentID)) {
        hasChromebookSignedOut = true;
        break;
      }
    }
    return hasChromebookSignedOut;
  }
  
  // Method which checks if a student exists in the system's database
  public static boolean validateStudent(String studentID, String firstName, String lastName) throws Exception {
    // Reads the system's database file and checks if the student's entered info matches any of the identities in the database
    boolean studentExists = false;
    File databaseFile = new File("students_database.txt");
    Scanner databaseScanner = new Scanner(databaseFile);
    while (databaseScanner.hasNextLine()) {
      String student = databaseScanner.nextLine();
      String[] studentInformation = student.split(" ");
      if (studentInformation[0].equals(studentID) && studentInformation[1].equals(firstName) && studentInformation[2].equals(lastName)) {
        studentExists = true;
        break;
      }
    }
    return studentExists;
  }
  
  // Method which adds a laptop to a given array of numbers of a cart
  public static String[] addLaptopToCart(String[] numbers, int laptopInteger) {
    // Removes the letter "L" at the start of each laptop in the array and turns the numbers into an int-type array
    int[] newNumbers = new int[numbers.length + 1];
    for (int i = 0; i < numbers.length; i++) {
      newNumbers[i] = Integer.parseInt(numbers[i].substring(1));
    }
    
    // Adds the laptop to the array and sorts it
    newNumbers[newNumbers.length - 1] = laptopInteger;
    newNumbers = sortArray(newNumbers);

    // Converts the int-type array back to a String-type array by adding the "L" in front of each number
    String[] stringNumbers = new String[newNumbers.length];
    for (int i = 0; i < stringNumbers.length; i++) {
      stringNumbers[i] = "L" + newNumbers[i];
    }

    return stringNumbers;
  }

  // Method which sorts an array of numbers using a bubble-sort algorithm
  public static int[] sortArray (int[] givenArray) {
    int n = givenArray.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (givenArray[j] > givenArray[j+1]) {
          int temp = givenArray[j];
          givenArray[j] = givenArray[j+1];
          givenArray[j+1] = temp; 
        }
      }
    }
    return givenArray;
  }
  
  // Method which updates the ChromebookManager's data (unreturned chromebooks & system's available laptops)
  public static void updateData() throws Exception {
    // The table is cleared of all its rows/cells by setting the row count to 0
    model.setRowCount(0);

    // Overwrites current cart's numbers's data with the data from the chromebooks in the system
    File systemLaptopsFile = new File("system_laptops.txt");
    Scanner systemLaptopScanner = new Scanner(systemLaptopsFile);
    int line = 0;
    while (systemLaptopScanner.hasNextLine()) {
      line++;
      if (line == 1) {
        numbers = systemLaptopScanner.nextLine().split(" ");
      }
      else if (line == 2) {
        numbers2 = systemLaptopScanner.nextLine().split(" ");
      }
      else if (line == 3) {
        numbers3 = systemLaptopScanner.nextLine().split(" ");
      }
      else if (line == 4) {
        numbers4 = systemLaptopScanner.nextLine().split(" ");
      }
    }

    // Removes the laptops which are currently signed out from the combobox and adds the currently signed out chromebooks to the table in the GUI
    File unreturnedChromebooks = new File("unreturned_chromebooks.txt");
    Scanner unreturnedChromebookScanner = new Scanner(unreturnedChromebooks);
    while (unreturnedChromebookScanner.hasNextLine()) {
      String unreturnedChromebook = unreturnedChromebookScanner.nextLine();
      String[] unreturnedChromebookData = unreturnedChromebook.split(" ");

      // Retrieves the chromebook sign-out's information
      String cart = unreturnedChromebookData[0];
      String laptop = unreturnedChromebookData[1];
      String studentID = unreturnedChromebookData[2];
      String firstName = unreturnedChromebookData[3];
      String lastName = unreturnedChromebookData[4];
      String date = unreturnedChromebookData[5] + " " + unreturnedChromebookData[6];
      String time = unreturnedChromebookData[7];
      
      // Adds the chromebook to the table with the data retrieved from above
      model.addRow(new Object[]{cart, laptop, studentID, firstName, lastName, date, time});
      
      // Removes the laptop found in the unreturned chromebooks file from its respective cart's numbers
      if (cart.equals("C1")) {
        numbers = removeLaptopFromCart(numbers, laptop);
      }
      else if (cart.equals("C2")) {
        numbers2 = removeLaptopFromCart(numbers2, laptop);
      }
      else if (cart.equals("C3")) {
        numbers3 = removeLaptopFromCart(numbers3, laptop);
      }
      else if (cart.equals("C4")) {
        numbers4 = removeLaptopFromCart(numbers4, laptop);
      }
    }
    
    // Updates the combobox with new data
    laptopNumbers.removeAllItems();
    for (String number : numbers) {
      laptopNumbers.addItem(number);
    }
    laptopCarts.setSelectedIndex(0);
    
  }
  
  // Method which adds each component of the GUI onto the JFrame/window
  public void addToScreen() {
    add(programTitle);
    add(adminPanel);          
    add(selectLaptopCart);
    add(laptopCarts);
    add(selectLaptopNumber);
    add(laptopNumbers);
    add(enterStudentID);
    add(studentID);
    add(enterFirstName);
    add(firstName);
    add(enterLastName);
    add(lastName);
    add(borrowChromebook);
    add(pane, BorderLayout.CENTER);
    add(returnChromebook);
  }

  // Constructor method which runs when an instance/object of ChromebookManager is created
  public ChromebookManager() throws Exception {   
    // Puts the elements of the GUI on-screen with their correct configurations
    top();
    form();
    table();
    
    // Calls a method which adds an action listener for each button on the GUI as well as an action listener for when the user closes the window
    actionChangeCart();
    actionBorrowChromebook();
    actionReturnChromebook();
    actionOpenAdminPanel();
    
    // Adds each component of the GUI to the JFrame/screen
    addToScreen();
    
    // Configures the JFrame's title, size, visibility and makes it an unresizeable window
    setTitle("Chromebook Manager");
    setSize(800, 600); 
    setLayout(null);
    setResizable(false);
    setVisible(true);
    
    // Updates the table by calling this method to fill the table with its data and also updates the laptops in the system
    updateData();
  }
}