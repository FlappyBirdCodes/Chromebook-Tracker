// Declares ModifyChromebooks.java as part of the ChromebookManager package
package ChromebookManager;

// Imports necessary for program to run
import javax.swing.*;
import java.awt.Font;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ModifyChromebooks extends JFrame {
    // Initialization of JLabels in the ModifyChromebooks GUI
    JLabel chromebooksInSystem = new JLabel("Chromebooks in System");

    // Initialization of JButtons in the ModifyChromebooks GUI
    JButton removeLaptop = new JButton("Remove Laptop");
    JButton addLaptop = new JButton("Add Laptop");

    // Initialization of JTable and its scrollpane in the ModifyChromebooks GUI    
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);
    JScrollPane pane = new JScrollPane(table);

    // Initialization of fonts used in the ModifyChromebooks GUI
    Font bigSerif = new Font("serif", Font.PLAIN, 30);
    Font smallSerif = new Font("serif", Font.PLAIN, 18);

    // Method which configures the 'Chromebooks in System' title/JLabel in the top of the GUI
    public void top() {
      // Configures the location and font of the 'Chromebooks in System' title/JLabel
      chromebooksInSystem.setBounds(132, -20, 400, 100);
      chromebooksInSystem.setFont(bigSerif);
    }
    
    // Method which configures the table in the GUI which displays the Chromebooks in the system
    public void table() {
        // Configured so that the table is not editable, changeable or resizeable
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        
        // Configured so that only one row can be selected in the table
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adds each column to the table and configures the location of the scrollpane of the table
        model.addColumn("Cart");
        model.addColumn("Laptop");
        pane.setBounds(48, 75, 495, 465);
    }

    // Method which configures the 'Add Laptop' button and the 'Remove Laptop' button in the bottom of the GUI 
    public void bottom() {
      addLaptop.setBounds(55, 580, 225, 50);
      removeLaptop.setBounds(310, 580, 225, 50);
    }
    
    // Methods which opens the add a laptop popup menu when its respective button is clicked
    public void actionAddLaptop() {
      addLaptop.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Creates a new JFrame and elements for the add a laptop popup menu
          JFrame parent = new JFrame();
          String[] carts = {"C1", "C2", "C3", "C4"};
          JComboBox enterCartNumber = new JComboBox(carts);
          String[] laptops = {"L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11", "L12"};
          JComboBox enterLaptopNumber = new JComboBox(laptops);
          Object[] message = {
            "Enter Cart Number:", enterCartNumber,
            "Enter Laptop Number:", enterLaptopNumber
          };
                
          // Creates/opens the popup with an 'Ok' and 'Cancel' button prompting the user to add a laptop
          int option = JOptionPane.showConfirmDialog(parent, message, "Add laptop", JOptionPane.OK_CANCEL_OPTION);
          
          // If the user clicks 'Ok', the laptopp they selected is added
          try {
            if (option == JOptionPane.OK_OPTION) {
              // Retrieves the cart and laptop the user selected
              String cartSelected = enterCartNumber.getSelectedItem().toString();
              int cartNumber = Integer.parseInt(cartSelected.substring(1));
              String chromebook = enterLaptopNumber.getSelectedItem().toString();
              
              // Initialization of variables needed to add the laptop/check if it is addable
              boolean laptopIsAddable = true;
              int line = 0;  
              String[] cartOne = new String[12];
              String[] cartTwo = new String[12];
              String[] cartThree = new String[12];
              String[] cartFour = new String[12];
                    
              // Checks if the laptop the user entered is already in the system
              File systemLaptopsFile = new File("system_laptops.txt");
              Scanner systemLaptopScanner = new Scanner(systemLaptopsFile);
              while (systemLaptopScanner.hasNextLine()) {
                line++;
                String cart = systemLaptopScanner.nextLine();
                if (line == cartNumber) {
                  String[] laptopsInCart = cart.split(" ");
                  for (String laptop : laptopsInCart) {
                    // If the laptop the user entered is already in the system, they are told so and nothing happens
                    if (chromebook.equals(laptop)) {
                      laptopIsAddable = false;
                      JOptionPane.showMessageDialog(null, "This chromebook is already in the system\nPlease ensure you are adding the correct Chromebook and try again.");
                      break;
                    }
                  }
                }

                // Depending on which line the scanner is checking in the system_laptops.txt file, it saves the data into each cart based on the line number
                switch(line) {
                  case 1:
                    cartOne = cart.split(" ");
                    break;
                  case 2:
                    cartTwo = cart.split(" ");
                    break;
                  case 3:
                    cartThree = cart.split(" ");
                    break;
                  case 4:
                    cartFour = cart.split(" ");
                    break;
                }
              }
                    
              // After checking if the laptop is addable, if the laptopIsAddable variable is true the laptop is then added
              if (laptopIsAddable) {
                // Depending on which cart the laptop is being added to, the laptop is added to one of the four carts
                if (cartSelected.equals("C1")) {
                  cartOne = ChromebookManager.addLaptopToCart(cartOne, Integer.parseInt(chromebook.substring(1)));
                }
                else if (cartSelected.equals("C2")) {
                  cartTwo = ChromebookManager.addLaptopToCart(cartTwo, Integer.parseInt(chromebook.substring(1)));
                }
                else if (cartSelected.equals("C3")) {
                  cartThree = ChromebookManager.addLaptopToCart(cartThree, Integer.parseInt(chromebook.substring(1)));
                }
                else if (cartSelected.equals("C4")) {
                  cartFour = ChromebookManager.addLaptopToCart(cartFour, Integer.parseInt(chromebook.substring(1)));
                }
                
                // After the laptop has been added, the pre-existing carts (unchanged) and the cart with the newly added laptop are then saved to the text file
                FileWriter systemLaptopWriter = new FileWriter("system_laptops.txt");
                systemLaptopWriter.write(arrayToString(cartOne) + "\n" + arrayToString(cartTwo) + "\n" + arrayToString(cartThree) + "\n" + arrayToString(cartFour));
                systemLaptopWriter.close();
                
                // The data in the main ChromebookManager GUI is updated (ComboBoxes) and the table in the ModifyChromebooks menu is updated to reflect the change
                ChromebookManager.updateData();
                updateTable();
              }
              
            }
          }
          // Prevents the program from crashing due to potential error(s) with i/o to text files
          catch (IOException javaIOException) {
            javaIOException.printStackTrace();
          }
          // Prevents the program from crashing due to potential error(s)
          catch (Exception javaLangException) {
            javaLangException.printStackTrace();
          }
        }
      });
    }
    
    // Method which removes the laptop the user selected after its respective button is clicked
    public void actionRemoveLaptop() {
      removeLaptop.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            // Retrieves the row that the user selected on the table
            int row = table.getSelectedRow();
            if (row != -1) {
              // Retrieves the cart and laptop number of the row/laptop the user selected on the table
              String cart = String.valueOf(table.getModel().getValueAt(row, 0));
              String laptop = String.valueOf(table.getModel().getValueAt(row, 1));
              
              // If the chromebook is currently signed out by a student, they cannot remove it from the system until it is returned and are given an error popup message
              if (currentlySignedOut(cart, laptop)) {
                JOptionPane.showMessageDialog(null, "This chromebook is currently signed out and must be returned\nbefore it can be removed from the system.");
              }
              // If the chromebook is the only chromebook left in the system, they cannot remove it from the system until another from the same cart is added and the user is given an error popup message
              else if (lastLaptopInCart(Integer.parseInt(cart.substring(1)))) {
                JOptionPane.showMessageDialog(null, "You cannot remove this Chromebook as it is the only Chromebook left in the cart.\nPlease re-add another Chromebook before removing this specific one.");
              }
              // If the chromebook is not currently signed out and not the last one in its respective cart in the system, it is removed from the system
              else {
                // Initialization of variables needed to remove the laptop and read/write from the system_laptops text file
                String cartData = "";
                int line = 0;
                String[] cartOne = new String[12];
                String[] cartTwo = new String[12];
                String[] cartThree = new String[12];
                String[] cartFour = new String[12];
                File systemLaptopsFile = new File("system_laptops.txt");
                Scanner systemLaptopScanner = new Scanner(systemLaptopsFile);
                
                // Depending on which line the scanner is checking in the system_laptops.txt file, it saves the data into each cart based on the line number
                while (systemLaptopScanner.hasNextLine()) {
                  line++;
                  cartData = systemLaptopScanner.nextLine();
                  switch (line) {
                    case 1:
                      cartOne = cartData.split(" ");
                      break;
                    case 2:
                      cartTwo = cartData.split(" ");
                      break;
                    case 3:
                      cartThree = cartData.split(" ");
                      break;
                    case 4:
                      cartFour = cartData.split(" ");
                      break;
                  }
                }
                
                // Depending on which cart the laptop is being added to, the laptop is added to one of the four carts
                if (cart.equals("C1")) {
                  cartOne = removeLaptopFromCart(cartOne, laptop);
                }
                else if (cart.equals("C2")) {
                  cartTwo = removeLaptopFromCart(cartTwo, laptop);
                }
                else if (cart.equals("C3")) {
                  cartThree = removeLaptopFromCart(cartThree, laptop);
                }
                else if (cart.equals("C4")) {
                  cartFour = removeLaptopFromCart(cartFour, laptop);
                }
                
                // After the laptop has been removed, the pre-existing carts (unchanged) and the cart with the removed laptop are then saved to the text file
                FileWriter systemLaptopWriter = new FileWriter("system_laptops.txt");
                systemLaptopWriter.write(arrayToString(cartOne) + "\n" + arrayToString(cartTwo) + "\n" + arrayToString(cartThree) + "\n" + arrayToString(cartFour));
                systemLaptopWriter.close();
                
                // The table in the ModifyChromebooks GUI is updated by removing the row in the table
                model.removeRow(row);
                
              }
              
              // The ChromebookManager GUI's data is updated to reflect this change (in the comboboxes)
              ChromebookManager.updateData();
            }
            // If the user did not select a row in the table, they are given a popup error message
            else {
              JOptionPane.showMessageDialog(null, "You must select a laptop in the table before removing it.");
            }
          }
          // Prevents the program from crashing due to potential error(s) with i/o to text files
          catch (IOException javaIOException) {
            javaIOException.printStackTrace();
          }
          // Prevents the program from crashing due to potential error(s)
          catch (Exception javaLangException) {
            javaLangException.printStackTrace();
          }   
        }
      });
    }
    
    // Method which removes the instance of the ModifyChromebooks GUI when the window is closed by the user
    public void actionCloseModifyChromebooks() {
      addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent windowEvent) {
          AdminPanel.modifyChromebookWindows.clear();
        }
      });
    }
    
    // Method which converts a String-type array to a String with its elements concatenated
    public static String arrayToString(String[] array) {
      String convertedString = "";
      // Adds each element of the array to 'convertedString'
      for (String element : array) {
        convertedString += " " + element;
      }
      // Takes a substring of the string to remove the extra space at the first index
      convertedString = convertedString.substring(1, convertedString.length());
      return convertedString;
    }
        
    // Method which checks if a given cart and its laptops only have their last laptop available
    public static boolean lastLaptopInCart(int cartNumber) throws Exception {
      // Variable which is overwritten with true and returned if the cart only has its last laptop available
      boolean lastLaptopInCart = false;
      
      // Checks if the cart's line in the text file has a length of 3 or less, which means it only has its last laptop available
      File systemLaptopsFile = new File("system_laptops.txt");
      Scanner systemLaptopScanner = new Scanner(systemLaptopsFile);
      int line = 0;
      String lineData = "";
      while (systemLaptopScanner.hasNextLine()) {
        line++;
        lineData = systemLaptopScanner.nextLine();
        if (line == cartNumber && lineData.length() <= 3) {
          lastLaptopInCart = true;
        }
      }
      
      // Returns the lastLaptopInCart variable after performing the check
      return lastLaptopInCart;
    }
    
    // Method which removes a laptop from a given array
    public static String[] removeLaptopFromCart (String[] laptopsInCart, String laptopToRemove) {
      // Converts the array of laptops into an ArrayList and removes the given laptop which is being removed
      ArrayList<String> laptops = new ArrayList<String>(Arrays.asList(laptopsInCart));
      laptops.remove(laptopToRemove);
      
      // Converts the ArrayList back into an Array
      String[] newLaptopsInCart = new String[laptopsInCart.length - 1];
      for (int i = 0; i < laptops.size(); i++) {
        newLaptopsInCart[i] = laptops.get(i);
      }
      
      // Returns the new array with the laptop removed
      return newLaptopsInCart;
    }
    
    // Method which checks if a given chromebook is currently signed out by a student
    public static boolean currentlySignedOut(String cartNumber, String laptopNumber) throws Exception {
      // Checks every unreturned chromebook in its text file, and if the given chromebook is in the file, it is currently signed out
      boolean currentlySignedOut = false;
      File unreturnedChromebooksFile = new File("unreturned_chromebooks.txt");
      Scanner unreturnedChromebookScanner = new Scanner(unreturnedChromebooksFile);
      while (unreturnedChromebookScanner.hasNextLine()) {
        String chromebook = unreturnedChromebookScanner.nextLine();
        String[] chromebookData = chromebook.split(" ");
        if (chromebookData[0].equals(cartNumber) && chromebookData[1].equals(laptopNumber)) {
          currentlySignedOut = true;
          break;
        }
      }
      return currentlySignedOut;
    }
    
    // Method which updates the table for the chromebooks in the system when a change is made to the data or when the Modify Chromebooks window/GUI is initialized/opened
    public void updateTable() throws Exception {
      // The table is cleared of all its rows/cells by setting the row count to 0
      model.setRowCount(0);
      
      // Reads the system_laptops text file file storing the system laptops data, and then adds each row with its data
      File systemLaptopsFile = new File("system_laptops.txt");
      Scanner systemLaptopScanner = new Scanner(systemLaptopsFile);
        int line = 0;
        while (systemLaptopScanner.hasNextLine()) {
          line++;
          String cartData = systemLaptopScanner.nextLine();
          String[] laptopsInCart = cartData.split(" ");
          for (String laptop : laptopsInCart) {
            model.addRow(new Object[]{"C" + String.valueOf(line), laptop});
          }
        }
        
        // Centers all cells in table
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment( SwingConstants.CENTER );
        for (int i = 0; i < table.getColumnCount(); i++){
          table.setDefaultRenderer(table.getColumnClass(i), renderer);
        }
    }

    // Constructor method which runs when an instance/object of ModifyChromebooks is created
    ModifyChromebooks() throws Exception {
      // Puts the elements of the GUI on-screen with their correct configurations
      top();
      table();
      bottom();
      
      // Calls a method which adds an action listener for each button on the GUI as well as an action listener for when the user closes the window
      actionAddLaptop();
      actionRemoveLaptop();
      actionCloseModifyChromebooks();
      
      // Puts the titles, buttons and scroll-pane for the table on the GUI
      add(chromebooksInSystem);
      add(pane);
      add(addLaptop);
      add(removeLaptop);
      
      // Configures the JFrame's title, size, visibility and makes it an unresizeable window
      setTitle("Modify Chromebooks");
      setSize(600, 700); 
      setLayout(null);
      setResizable(false);
      setVisible(true);
      
      // Updates the table by calling this method to fill the table with its data
      updateTable();
    }
}