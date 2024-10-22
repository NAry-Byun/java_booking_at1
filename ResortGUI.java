package ass1_nary_byun(java);

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

public class ResortGUI extends JFrame implements ActionListener {
    // ArrayLists for accommodations, customers, and packages
    private ArrayList<Accommodation> accommodations = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<TravelPackage> packages = new ArrayList<>();

    // Tabs and Panels
    private JTabbedPane tabbedPane;
    private JPanel accommodationsTab, customersTab, packagesTab;

    // Accommodations Components
    private JTextArea accommodationsDisplay;
    private JButton displayAllButton, displayAvailableButton;

    // Customers Components
    private JTextField customerNameField, customerLevelField;
    private JTextArea customersDisplay;
    private JButton addCustomerButton, listCustomersButton;

    // Packages Components
    private JTextArea packagesDisplay;
    private JButton createPackageButton, listPackagesButton;
    private JButton savePackagesButton, loadPackagesButton;

    // Constructor
    public ResortGUI() {
        setTitle("Mt Buller Winter Resort");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Tabs
        tabbedPane = new JTabbedPane();
        initAccommodationsTab();
        initCustomersTab();
        initPackagesTab();

        tabbedPane.addTab("Accommodations", accommodationsTab);
        tabbedPane.addTab("Customers", customersTab);
        tabbedPane.addTab("Packages", packagesTab);

        add(tabbedPane);
    }

    // Initialize Accommodations Tab
    private void initAccommodationsTab() {
        accommodationsTab = new JPanel(new BorderLayout());

        accommodationsDisplay = new JTextArea();
        accommodationsDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(accommodationsDisplay);

        displayAllButton = new JButton("Display All Accommodations");
        displayAvailableButton = new JButton("Display Available Accommodations");

        displayAllButton.addActionListener(this);
        displayAvailableButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(displayAllButton);
        buttonPanel.add(displayAvailableButton);

        accommodationsTab.add(scrollPane, BorderLayout.CENTER);
        accommodationsTab.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Initialize Customers Tab
    private void initCustomersTab() {
        customersTab = new JPanel(new BorderLayout());

        customerNameField = new JTextField(20);
        customerLevelField = new JTextField(20);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Customer Name:"));
        inputPanel.add(customerNameField);
        inputPanel.add(new JLabel("Skiing Level (Beginner/Intermediate/Expert):"));
        inputPanel.add(customerLevelField);

        customersDisplay = new JTextArea();
        customersDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(customersDisplay);

        addCustomerButton = new JButton("Add Customer");
        listCustomersButton = new JButton("List Customers");

        addCustomerButton.addActionListener(this);
        listCustomersButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(listCustomersButton);

        customersTab.add(inputPanel, BorderLayout.NORTH);
        customersTab.add(scrollPane, BorderLayout.CENTER);
        customersTab.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Initialize Packages Tab
    private void initPackagesTab() {
        packagesTab = new JPanel(new BorderLayout());

        packagesDisplay = new JTextArea();
        packagesDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(packagesDisplay);

        createPackageButton = new JButton("Create Package");
        listPackagesButton = new JButton("List Packages");
        savePackagesButton = new JButton("Save Packages");
        loadPackagesButton = new JButton("Load Packages");

        createPackageButton.addActionListener(this);
        listPackagesButton.addActionListener(this);
        savePackagesButton.addActionListener(this);
        loadPackagesButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createPackageButton);
        buttonPanel.add(listPackagesButton);
        buttonPanel.add(savePackagesButton);
        buttonPanel.add(loadPackagesButton);

        packagesTab.add(scrollPane, BorderLayout.CENTER);
        packagesTab.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayAllButton) {
            displayAllAccommodations();
        } else if (e.getSource() == displayAvailableButton) {
            displayAvailableAccommodations();
        } else if (e.getSource() == addCustomerButton) {
            addCustomer();
        } else if (e.getSource() == listCustomersButton) {
            listCustomers();
        } else if (e.getSource() == createPackageButton) {
            createPackage();
        } else if (e.getSource() == listPackagesButton) {
            listPackages();
        } else if (e.getSource() == savePackagesButton) {
            savePackagesToFile();
        } else if (e.getSource() == loadPackagesButton) {
            loadPackagesFromFile();
        }
    }

    private void displayAllAccommodations() {
        accommodationsDisplay.setText("");
        for (Accommodation acc : accommodations) {
            accommodationsDisplay.append(acc + "\n");
        }
    }

    private void displayAvailableAccommodations() {
        accommodationsDisplay.setText("");
        for (Accommodation acc : accommodations) {
            if (acc.isAvailable()) {
                accommodationsDisplay.append(acc + "\n");
            }
        }
    }

    private void addCustomer() {
        String name = customerNameField.getText();
        String level = customerLevelField.getText();

        if (name.isEmpty() || level.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all customer details.");
            return;
        }

        customers.add(new Customer(customers.size() + 1, name, level));
        JOptionPane.showMessageDialog(this, "Customer added successfully!");
        customerNameField.setText("");
        customerLevelField.setText("");
    }

    private void listCustomers() {
        customersDisplay.setText("");
        for (Customer customer : customers) {
            customersDisplay.append(customer + "\n");
        }
    }

    private void createPackage() {
        JOptionPane.showMessageDialog(this, "Package creation logic goes here.");
    }

    private void listPackages() {
        packagesDisplay.setText("");
        for (TravelPackage pkg : packages) {
            packagesDisplay.append(pkg + "\n");
        }
    }

    private void savePackagesToFile() {
        JOptionPane.showMessageDialog(this, "Packages saved to file.");
    }

    private void loadPackagesFromFile() {
        JOptionPane.showMessageDialog(this, "Packages loaded from file.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResortGUI gui = new ResortGUI();
            gui.populateData();
            gui.setVisible(true);
        });
    }

    private void populateData() {
        accommodations.add(new Accommodation(1, "Hotel", 150));
        accommodations.add(new Accommodation(2, "Lodge", 100));
        accommodations.add(new Accommodation(3, "Apartment", 200));

        customers.add(new Customer(1, "Alice", "Beginner"));
        customers.add(new Customer(2, "Bob", "Intermediate"));
        customers.add(new Customer(3, "Charlie", "Expert"));
    }
}