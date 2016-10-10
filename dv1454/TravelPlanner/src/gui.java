import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class gui {
    private JTable table;
    private JPanel contentPane;
    private static JFrame frame;
    private JTextField txtCardNr;
    private JTextField txtOrigin;
    private JTextField txtDestination;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField txtDepartureTime;
    private JTextField txtTravelTime;
    private JTextField txtPricePerSeat;
    private JTextField txtNrOfSeats;
    private final SystemController sc = new SystemController();
    
    public static void main(String[] args) {
                    gui window = new gui();
                    window.frame.setVisible(true);
    }

    public gui() {
        frame = new JFrame();
        initialize(frame,false);
    }

    private void initialize(JFrame frame,boolean login) {
        frame.setBounds(100, 100, 723, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        

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
        Date todaysDate = new Date();
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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = format.format(dateString1);

                if (origin.equals("") || destination.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Not valid search params",
                        "Couldn't search", JOptionPane.ERROR_MESSAGE);
                } else if (dateString1.before(todaysDate)){
                    JOptionPane.showMessageDialog(frame, "You can't search for old flights",
                        "Couldn't search", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[][] flights = sc.getFlights(origin, destination, formatedDate);
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

        JCheckBox chckbxReturnFlight = new JCheckBox("Return Flight");
        chckbxReturnFlight.setBounds(376, 200, 100, 23);
        frame.getContentPane().add(chckbxReturnFlight);

        JDateChooser dateReturn = new JDateChooser();
        dateReturn.setEnabled(false);
        dateReturn.setBounds(376, 159, 95, 20);
        frame.getContentPane().add(dateReturn);
    }


    public void register(JFrame frame){
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);
        
        nameField = new JTextField();
        nameField.setBounds(173, 71, 156, 20);
        frame.getContentPane().add(nameField);
        nameField.setColumns(10);

        lastNameField = new JTextField();
        lastNameField.setColumns(10);
        lastNameField.setBounds(173, 121, 156, 20);
        frame.getContentPane().add(lastNameField);

        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(173, 175, 156, 20);
        frame.getContentPane().add(emailField);


        JLabel lblName = new JLabel("Name");
        lblName.setBounds(173, 48, 46, 14);
        frame.getContentPane().add(lblName);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(173, 102, 70, 14);
        frame.getContentPane().add(lblLastName);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(173, 150, 46, 14);
        frame.getContentPane().add(lblEmail);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(173, 206, 70, 14);
        frame.getContentPane().add(lblPassword);


        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(e -> {
                String email= emailField.getText();
                String name= nameField.getText();
                String lastName= lastNameField.getText();
                if (!email.isEmpty()  && !name.isEmpty() && !lastName.isEmpty()) {
                            frame.getContentPane().removeAll();
                            initialize(frame, false);
                            frame.getContentPane().validate();
                            frame.getContentPane().repaint();
    }
    btnRegister.setBounds(208, 334, 89, 23);
    frame.getContentPane().add(btnRegister);
    });
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
        Date todaysDate = new Date();

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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = format.format(dateString);

                if (origin1.equals("") || destination1.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Not valid search parameters",
                        "Couldn't search", JOptionPane.ERROR_MESSAGE);
                } else if (dateString.before(todaysDate)) {
                    JOptionPane.showMessageDialog(frame, "You can't search for old flights",
                        "Couldn't search", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[][] flights1 = sc.getFlights(origin1, destination1, formatedDate);
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
                String[] flight = flights[table.getSelectedRow()];
                frame.getContentPane().removeAll();
                book(frame, flight);
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
            initialize(frame,false);
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
    
    public void book(JFrame frame,String[] flight) {
        contentPane = new JPanel();

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JLabel txtOrigin = new JLabel(flight[1]);
        txtOrigin.setBounds(81, 87, 176, 20);
        frame.getContentPane().add(txtOrigin);

        JLabel txtDestination = new JLabel(flight[2]);
        txtDestination.setBounds(81, 144, 176, 20);
        frame.getContentPane().add(txtDestination);

        JLabel txtPrice = new JLabel(flight[6]);
        txtPrice.setBounds(81, 261, 58, 20);
        frame.getContentPane().add(txtPrice);

        JLabel txtDate = new JLabel(flight[3]);
        txtDate.setBounds(399, 87, 86, 20);
        frame.getContentPane().add(txtDate);

        JLabel txtTime = new JLabel(flight[4]);
        txtTime.setBounds(559, 87, 86, 20);
        frame.getContentPane().add(txtTime);

        JLabel txtDate_2 = new JLabel(flight[3]);
        txtDate_2.setBounds(399, 144, 86, 20);
        frame.getContentPane().add(txtDate_2);

        String arrTime = flight[4].substring(0, 2);
        int time = Integer.parseInt(arrTime) + Integer.parseInt(flight[5].substring(0, 2));
        String arrivalTime = Integer.toString(time) + ":" + flight[4].substring(3, 5);
        
        JLabel txtTime_2 = new JLabel(arrivalTime);
        txtTime_2.setBounds(559, 144, 86, 20);
        frame.getContentPane().add(txtTime_2);

        JLabel lblOrigin = new JLabel("Origin");
        lblOrigin.setBounds(81, 62, 46, 14);
        frame.getContentPane().add(lblOrigin);

        JLabel lblDestination = new JLabel("Destination");
        lblDestination.setBounds(81, 118, 136, 14);
        frame.getContentPane().add(lblDestination);

        JLabel lblEnterPassengers = new JLabel("Enter Nr Of Passengers");
        lblEnterPassengers.setBounds(81, 179, 136, 14);
        frame.getContentPane().add(lblEnterPassengers);

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
        
        int max = 6;
        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, max, 1);
        JSpinner spPassengers = new JSpinner(model1);
        spPassengers.setValue(1);
        spPassengers.addChangeListener(arg0 -> {
            int price = (Integer)spPassengers.getValue() * Integer.parseInt(flight[6]);
            txtPrice.setText(Integer.toString(price));
        });
        
        spPassengers.setEnabled(true);
        spPassengers.setBounds(81, 204, 29, 20);
        frame.getContentPane().add(spPassengers);
        
        JButton btnBook = new JButton("Book");
        btnBook.addActionListener(e -> {
            int nrOfPassengers = (int)spPassengers.getValue();
            frame.getContentPane().removeAll();
            pay(frame,flight,txtPrice.getText(), nrOfPassengers);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        
        btnBook.setBounds(278, 362, 89, 23);
        frame.getContentPane().add(btnBook);
        
        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame,false);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        
        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);
    }

    public void pay(JFrame frame,String[] flight,String price, int nrOfPassengers) {
        contentPane = new JPanel();

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(77, 126, 46, 14);
        frame.getContentPane().add(lblPrice);

        JLabel lblDisplayPrice = new JLabel(price);
        lblDisplayPrice.setBounds(161, 126, 162, 14);
        frame.getContentPane().add(lblDisplayPrice);

        JLabel lblCardNr = new JLabel("Card Nr:");
        lblCardNr.setBounds(77, 172, 46, 14);
        frame.getContentPane().add(lblCardNr);

        txtCardNr = new JTextField();
        txtCardNr.setBounds(161, 169, 342, 20);
        frame.getContentPane().add(txtCardNr);
        txtCardNr.setColumns(10);

        JLabel lblReceipt = new JLabel("Receipt sent to: ");
        lblReceipt.setBounds(77, 222, 150, 14);
        frame.getContentPane().add(lblReceipt);


        JButton btnPay = new JButton("Pay");
        btnPay.addActionListener(e -> {
            int id = Integer.parseInt(flight[0]);
            String cardNr = txtCardNr.getText();

            if (cardNr.equals("")) {
                JOptionPane.showMessageDialog(frame, "Card number cannot be empty",
                    "No card number supplied", JOptionPane.ERROR_MESSAGE);
            } else {
                try{
                    Long.parseLong(cardNr, 10);
                    sc.bookFlight(id, nrOfPassengers, cardNr, Integer.parseInt(price));

                    frame.getContentPane().removeAll();
                    initialize(frame,true);
                    frame.getContentPane().validate();
                    frame.getContentPane().repaint();

                    JOptionPane.showMessageDialog(frame, "Payment went successfully",
                        "Payment successfully", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid card number",
                    "Couldn't search", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnPay.setBounds(77, 328, 89, 23);
        frame.getContentPane().add(btnPay);
        
        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            initialize(frame,false);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });
        btnHome.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnHome);
    }
    
}
