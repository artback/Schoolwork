import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;


public class gui {
    private JTable table;
    private JPanel contentPane;
    private static JFrame frame;
    private JTextField txtOrigin;
    private JTextField txtDestination;
    private JTextField txtlastName;
    private JTextField txtphoneNr;
    private JTextField nameField;
    private JTextField adressField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private String[] flight;
    private int price;
    private int seatClass;

    public static void main(String[] args) {
                    gui window = new gui();
                    window.frame.setVisible(true);
    }

    public gui() {
        frame = new JFrame();
        price=-1;
        initialize(frame);
    }

    private void initialize(JFrame frame) {
        frame.setBounds(100, 100, 723, 540);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnRebook = new JButton("Rebook");
        btnRebook.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            rebook(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        btnRebook.setBounds(0, 2, 100, 23);
        frame.getContentPane().add(btnRebook);

        txtOrigin = new JTextField();
        txtOrigin.setBounds(94, 107, 144, 20);
        frame.getContentPane().add(txtOrigin);
        txtOrigin.setColumns(10);

        txtDestination = new JTextField();
        txtDestination.setColumns(10);
        txtDestination.setBounds(376, 107, 144, 20);
        frame.getContentPane().add(txtDestination);
        
        JDateChooser dateOrigin = new JDateChooser();
        dateOrigin.setDateFormatString("yyyy-MM-dd");
        dateOrigin.setBounds(94, 159, 95, 20);
        frame.getContentPane().add(dateOrigin);
        Date dateString = new Date();
        dateOrigin.setDate(dateString);

        JLabel lblOrigin = new JLabel("Origin");
        lblOrigin.setBounds(94, 82, 46, 14);
        frame.getContentPane().add(lblOrigin);

        JLabel lblDestination = new JLabel("Destination");
        lblDestination.setBounds(376, 82, 138, 14);
        frame.getContentPane().add(lblDestination);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(arg0 -> {
            String origin = txtOrigin.getText();
            String destination = txtDestination.getText();

            Date dateString1 = dateOrigin.getDate();
            try {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
                String formatedDate = format.format(dateString1);

                if (origin.equals("") || destination.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Not valid search params",
                        "Couldn't search", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[][] flights = FlightController.getFlights(origin, destination, formatedDate);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().revalidate();
                    search(frame, flights, origin, destination, dateString1);
                    frame.getContentPane().repaint();
                }
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(frame, "Invalid date",
                    "Invalid date", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnSearch.setBounds(253, 304, 89, 23);
        frame.getContentPane().add(btnSearch);

    }
    public void register(JFrame frame){
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);




        JLabel lblName = new JLabel("First Name");
        lblName.setBounds(173, 48, 156, 14);
        frame.getContentPane().add(lblName);
        nameField = new JTextField();
        nameField.setBounds(173, 67, 156, 20);
        frame.getContentPane().add(nameField);
        nameField.setColumns(10);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(173, 90, 156, 14);
        frame.getContentPane().add(lblLastName);
        lastNameField = new JTextField();
        lastNameField.setColumns(10);
        lastNameField.setBounds(173, 109, 156, 20);
        frame.getContentPane().add(lastNameField);

        JLabel lbladdress = new JLabel("Adress");
        lbladdress.setBounds(173, 132, 156, 14);
        frame.getContentPane().add(lbladdress);
        adressField = new JTextField();
        adressField.setColumns(10);
        adressField.setBounds(173, 193, 156, 20);
        frame.getContentPane().add(adressField);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(173, 174, 156, 14);
        frame.getContentPane().add(lblEmail);
        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(173, 151, 156, 20);
        frame.getContentPane().add(emailField);


        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setBounds(173, 216, 156, 14);
        frame.getContentPane().add(lblPhone);
        phoneField = new JTextField();
        phoneField.setColumns(10);
        phoneField.setBounds(173, 235, 156, 20);
        frame.getContentPane().add(phoneField);

        JButton btnRegister = new JButton("Add User");
        btnRegister.addActionListener(e -> {
                            frame.getContentPane().removeAll();
                            if(!lastNameField.getText().equals("") || !phoneField.getText().equals("")) {
                                String lastName = lastNameField.getText();
                                String phoneNr = phoneField.getText();
                                Boolean success = AccountController.addUserToDB(nameField.getText(), lastName,
                                        adressField.getText(), emailField.getText(), phoneNr);
                                if (success) {
                                    String[][] users = AccountController.searchUsers(lastName, phoneNr);
                                    searchUser(frame, users, lastName, phoneNr);
                                    frame.getContentPane().validate();
                                    frame.getContentPane().repaint();
                                } else {
                                JOptionPane.showMessageDialog(frame, "Something went wrong!",
                                "duplicate keys", JOptionPane.ERROR_MESSAGE);
                                }
                            }
    });
        btnRegister.setBounds(208, 334, 89, 23);
        frame.getContentPane().add(btnRegister);
    }
    public void searchUser(JFrame frame,String[][] users,String LastName,String PhoneNumber) {
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(66, 82, 550, 321);
        frame.getContentPane().add(scrollPane);

        DefaultTableModel model = new DefaultTableModel();

        table = new JTable(model);

        model.addColumn("FirstName");
        model.addColumn("LastName");
        model.addColumn("Email");
        model.addColumn("PhoneNumber");

        scrollPane.setViewportView(table);

        txtOrigin = new JTextField(LastName);
        txtOrigin.setBounds(86, 49, 161, 20);
        frame.getContentPane().add(txtOrigin);
        txtOrigin.setColumns(10);

        JLabel lblOrigin = new JLabel("LastName");
        lblOrigin.setBounds(86, 24, 100, 14);
        frame.getContentPane().add(lblOrigin);

        txtDestination = new JTextField(PhoneNumber);
        txtDestination.setColumns(10);
        txtDestination.setBounds(257, 49, 164, 20);
        frame.getContentPane().add(txtDestination);

        JLabel lblDestination = new JLabel("PhoneNumber");
        lblDestination.setBounds(257, 24, 100, 14);
        frame.getContentPane().add(lblDestination);
JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String origin1 = txtOrigin.getText();
            String destination1 = txtDestination.getText();
                    String[][] users2 = AccountController.searchUsers(origin1,destination1);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().revalidate();
                    searchUser(frame, users2, origin1, destination1);
                    frame.getContentPane().repaint();
        });
        btnSearch.setBounds(527, 48, 89, 23);
        frame.getContentPane().add(btnSearch);

        JButton btnBook = new JButton("Book");
        btnBook.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                String user = users[table.getSelectedRow()][0];
                System.out.print(price);
                System.out.print(flight[0]);
                if(flight != null && price != -1) {
                    int id = Integer.parseInt(flight[0]);
                    int cId = Integer.parseInt(user);
                    boolean booked = TicketController.makePayment(cId,seatClass,id, price);
                    if(booked) {
                        frame.getContentPane().removeAll();
                        initialize(frame);
                        frame.getContentPane().revalidate();
                        frame.getContentPane().repaint();
                        JOptionPane.showMessageDialog(frame, "the booking have been made" ,
                                "the booking have been made", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Something went wrong!",
                        "Please select a row", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnBook.setBounds(250, 424, 89, 23);
        frame.getContentPane().add(btnBook);

        JButton btnAdd = new JButton("Add User");
        btnAdd.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            register(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        btnBook.setBounds(350, 424, 89, 23);
        frame.getContentPane().add(btnAdd);

        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });

        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);


        if(users[0][0] != null){
            for (int i = 0; i <= 10; i++) {
                if (users[i][0] != null) {
                    model.insertRow(i, new Object[]{users[i][1], users[i][2], users[i][4],users[i][5]});
                } else {
                    i = 10;
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No matches for your search",
                    "No matches", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void searchUser(JFrame frame) {
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(66, 82, 550, 321);
        frame.getContentPane().add(scrollPane);

        DefaultTableModel model = new DefaultTableModel();

        table = new JTable(model);

        model.addColumn("FirstName");
        model.addColumn("LastName");
        model.addColumn("Email");
        model.addColumn("PhoneNumber");

        scrollPane.setViewportView(table);

        txtOrigin = new JTextField("");
        txtOrigin.setBounds(86, 49, 161, 20);
        frame.getContentPane().add(txtOrigin);
        txtOrigin.setColumns(10);

        JLabel lblOrigin = new JLabel("LastName");
        lblOrigin.setBounds(86, 24, 100, 14);
        frame.getContentPane().add(lblOrigin);

        txtDestination = new JTextField("");
        txtDestination.setColumns(10);
        txtDestination.setBounds(257, 49, 164, 20);
        frame.getContentPane().add(txtDestination);

        JLabel lblDestination = new JLabel("PhoneNumber");
        lblDestination.setBounds(257, 24, 100, 14);
        frame.getContentPane().add(lblDestination);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String origin1 = txtOrigin.getText();
            String destination1 = txtDestination.getText();
                    String[][] users2 = AccountController.searchUsers(origin1,destination1);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().revalidate();
                    searchUser(frame, users2, origin1, destination1);
                    frame.getContentPane().repaint();
        });
        btnSearch.setBounds(527, 48, 89, 23);
        frame.getContentPane().add(btnSearch);
        JButton btnAdd = new JButton("Add User");
        btnAdd.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            register(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        btnAdd.setBounds(280, 424, 89, 23);
        frame.getContentPane().add(btnAdd);

        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });

        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);

    }
    public void search(JFrame frame, String[][] flights, String origin, String destination, Date date) {
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(66, 82, 550, 321);
        frame.getContentPane().add(scrollPane);

        DefaultTableModel model = new DefaultTableModel();

        table = new JTable(model);

        model.addColumn("Origin");
        model.addColumn("Destination");
        model.addColumn("Date");

        scrollPane.setViewportView(table);

        txtOrigin = new JTextField(origin);
        txtOrigin.setBounds(66, 49, 161, 20);
        frame.getContentPane().add(txtOrigin);
        txtOrigin.setColumns(10);

        txtDestination = new JTextField(destination);
        txtDestination.setColumns(10);
        txtDestination.setBounds(237, 49, 164, 20);
        frame.getContentPane().add(txtDestination);

        JDateChooser dateOrigin = new JDateChooser();
        dateOrigin.setDateFormatString("yyyy-MM-dd");
        dateOrigin.setDate(date);
        dateOrigin.setBounds(411, 49, 95, 20);
        frame.getContentPane().add(dateOrigin);

        JLabel lblOrigin = new JLabel("Origin");
        lblOrigin.setBounds(66, 24, 46, 14);
        frame.getContentPane().add(lblOrigin);

        JLabel lblDestination = new JLabel("Destination");
        lblDestination.setBounds(237, 24, 100, 14);
        frame.getContentPane().add(lblDestination);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String origin1 = txtOrigin.getText();
            String destination1 = txtDestination.getText();
            Date dateString = dateOrigin.getDate();
            try {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
                String formatedDate = format.format(dateString);

                if (origin1.equals("") || destination1.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Not valid search parameters",
                        "Couldn't search", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[][] flights1 = FlightController.getFlights(origin1, destination1, formatedDate);
                    System.out.print(formatedDate);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().revalidate();
                    search(frame, flights1, origin1, destination1, dateString);
                    frame.getContentPane().repaint();
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date",
                    "Invalid date", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnSearch.setBounds(527, 48, 89, 23);
        frame.getContentPane().add(btnSearch);

        JButton btnBook = new JButton("Book");
        btnBook.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                flight = flights[table.getSelectedRow()];
                frame.getContentPane().removeAll();
                book(frame);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Something went wrong!",
                    "Please select a row", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnBook.setBounds(280, 424, 89, 23);
        frame.getContentPane().add(btnBook);
        
        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        
        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);
        

        if(flights[0][0] != null){
            for (int i = 0; i <= 10; i++) {
                if (flights[i][0] != null) {
                    model.insertRow(i, new Object[]{flights[i][1], flights[i][2], flights[i][3]});
                } else {
                    i = 10;
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No matches for your search",
                "No matches", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void book(JFrame frame) {
        contentPane = new JPanel();

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JLabel txtOrigin = new JLabel(flight[1]);
        txtOrigin.setBounds(81, 87, 176, 20);
        frame.getContentPane().add(txtOrigin);

        JLabel txtDestination = new JLabel(flight[2]);
        txtDestination.setBounds(81, 144, 176, 20);
        frame.getContentPane().add(txtDestination);

        JLabel txtPrice = new JLabel(flight[5]);
        txtPrice.setBounds(81, 261, 58, 20);
        frame.getContentPane().add(txtPrice);
        String[] tickets=new String[]{"Business","Coach","FirstClass"};
        JComboBox<String> ticketClass = new JComboBox<>(tickets);
        ticketClass.addActionListener(e -> {
           String s = (String)ticketClass.getSelectedItem();
            switch(s){
                case "Business":
                    txtPrice.setText(flight[5]);
                    seatClass = 1;
                    break;
                case "Coach" :
                    txtPrice.setText(flight[6]);
                    seatClass = 0;
                    break;
                case "FirstClass":
                    txtPrice.setText(flight[7]);
                    seatClass = 2;
                    break;
            }
        });
        ticketClass.setBounds(81, 295 ,100,20);
        frame.getContentPane().add(ticketClass);
        JLabel txtDate = new JLabel(flight[3]);
        txtDate.setBounds(399, 87, 86, 20);
        frame.getContentPane().add(txtDate);

        JLabel txtTime = new JLabel(flight[4]);
        txtTime.setBounds(559, 87, 86, 20);
        frame.getContentPane().add(txtTime);

        JLabel txtDate_2 = new JLabel(flight[3]);
        txtDate_2.setBounds(399, 144, 86, 20);
        frame.getContentPane().add(txtDate_2);


        JLabel lblOrigin = new JLabel("Origin");
        lblOrigin.setBounds(81, 62, 46, 14);
        frame.getContentPane().add(lblOrigin);

        JLabel lblDestination = new JLabel("Destination");
        lblDestination.setBounds(81, 118, 136, 14);
        frame.getContentPane().add(lblDestination);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setBounds(81, 235, 46, 14);
        frame.getContentPane().add(lblPrice);

        JLabel lblDate = new JLabel("Date");
        lblDate.setBounds(399, 62, 46, 14);
        frame.getContentPane().add(lblDate);

        JLabel lblDate_2 = new JLabel("Date");
        lblDate_2.setBounds(399, 118, 46, 14);
        frame.getContentPane().add(lblDate_2);

        JLabel lblTime = new JLabel("Time");
        lblTime.setBounds(559, 62, 46, 14);
        frame.getContentPane().add(lblTime);

        JLabel lblTime_2 = new JLabel("Time");
        lblTime_2.setBounds(559, 119, 46, 14);
        frame.getContentPane().add(lblTime_2);

        JButton btnBook = new JButton("Book");
        btnBook.addActionListener(e -> {
            price = Integer.parseInt(txtPrice.getText());
            frame.getContentPane().removeAll();
            searchUser(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        
        btnBook.setBounds(278, 362, 89, 23);
        frame.getContentPane().add(btnBook);
        
        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        
        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);
    }
    public void rebook(JFrame frame,String[][] tickets, String lastName, String phoneNr){
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(66, 82, 550, 321);
        frame.getContentPane().add(scrollPane);

        DefaultTableModel model = new DefaultTableModel();

        table = new JTable(model);

        model.addColumn("TicketID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("price");
        model.addColumn("Date");
        model.addColumn("Departure");
        model.addColumn("Destination");

        scrollPane.setViewportView(table);

        txtlastName = new JTextField(lastName);
        txtlastName.setColumns(10);
        txtlastName.setBounds(66, 49, 161, 20);
        frame.getContentPane().add(txtlastName);

        txtphoneNr = new JTextField(phoneNr);
        txtphoneNr.setColumns(10);
        txtphoneNr.setBounds(237, 49, 164, 20);
        frame.getContentPane().add(txtphoneNr);

        JLabel lblLastname = new JLabel("LastName");
        lblLastname.setBounds(66, 24, 46, 14);
        frame.getContentPane().add(lblLastname);

        JLabel lblPhoneNr = new JLabel("PhoneNr");
        lblPhoneNr.setBounds(237, 24, 100, 14);
        frame.getContentPane().add(lblPhoneNr);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String lastNameText  = txtlastName.getText();
            String phoneNrtext = txtphoneNr.getText();
            frame.getContentPane().removeAll();
            frame.getContentPane().revalidate();
            String[][] tickets2 = TicketController.getTickets(lastNameText,phoneNrtext);
            rebook(frame,tickets2,lastNameText,phoneNrtext);
            frame.getContentPane().repaint();
        });

        btnSearch.setBounds(527, 48, 89, 23);
        frame.getContentPane().add(btnSearch);

        JButton btnBook = new JButton("Cancel");
        btnBook.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                int ticket = Integer.parseInt(tickets[table.getSelectedRow()][1]);
                TicketController.removeTicket(ticket);
                JOptionPane.showMessageDialog(frame, "the travel is now removed from db",
                        "the travel is now remove from ", JOptionPane.ERROR_MESSAGE);
                frame.getContentPane().removeAll();
                initialize(frame);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Something went wrong!",
                        "Please select a row", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnBook.setBounds(280, 424, 89, 23);
        frame.getContentPane().add(btnBook);

        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });

        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);




        if(tickets[0][0] != null){
            for (int i = 0; i <= 10; i++) {
                if (tickets[i][0] != null) {
                    model.insertRow(i, new Object[]{tickets[i][1], tickets[i][2], tickets[i][3],tickets[i][4],tickets[i][5],tickets[i][6],tickets[i][7],tickets[i][7]});
                } else {
                    i = 10;
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No matches for your search",
                    "No matches", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void rebook(JFrame frame){
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(66, 82, 550, 321);
        frame.getContentPane().add(scrollPane);

        DefaultTableModel model = new DefaultTableModel();

        table = new JTable(model);


        model.addColumn("TicketID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("price");
        model.addColumn("Email");

        scrollPane.setViewportView(table);

        txtlastName = new JTextField("");
        txtlastName.setBounds(66, 49, 161, 20);
        frame.getContentPane().add(txtlastName);
        txtOrigin.setColumns(10);

        txtphoneNr = new JTextField("");
        txtphoneNr.setColumns(10);
        txtphoneNr.setBounds(237, 49, 164, 20);
        frame.getContentPane().add(txtphoneNr);

        JLabel lblLastname = new JLabel("LastName");
        lblLastname.setBounds(66, 24, 46, 14);
        frame.getContentPane().add(lblLastname);

        JLabel lblPhoneNr = new JLabel("PhoneNr");
        lblPhoneNr.setBounds(237, 24, 100, 14);
        frame.getContentPane().add(lblPhoneNr);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String lastName = txtlastName.getText();
            String phoneNr = txtphoneNr.getText();
                frame.getContentPane().removeAll();
                frame.getContentPane().revalidate();
                String[][] tickets2 = TicketController.getTickets(lastName,phoneNr);
                rebook(frame,tickets2,lastName,phoneNr);
                frame.getContentPane().repaint();
        });
        btnSearch.setBounds(527, 48, 89, 23);
        frame.getContentPane().add(btnSearch);

        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });

        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);


    }
}
