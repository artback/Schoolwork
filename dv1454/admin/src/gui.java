import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class gui {
    private JTable table;
    private JPanel contentPane;
    String[][] flights;
    private static JFrame frame;

    public static void main(String[] args) {
                    gui window = new gui();
                    window.frame.setVisible(true);
    }

    public gui() {
        frame = new JFrame();
        search(frame);
    }

    public void search(JFrame frame)  {
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(66, 82, 550, 321);
        frame.getContentPane().add(scrollPane);


        flights = FlightController.getFlights(true);

        String[] sort =new String[]{"Personer","Omsättning"};
        JComboBox<String> ticketClass = new JComboBox<>(sort);
        ticketClass.addActionListener(e -> {
            String s = (String)ticketClass.getSelectedItem();
            switch(s){
                case "Personer":
                    flights = FlightController.getFlights(true);
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (int i = 0; i < 5; i++) {
                        model.insertRow(i, new Object[]{flights[i][0], flights[i][1], flights[i][2],flights[i][3] });
                    }
                    break;
                case "Omsättning" :
                    flights = FlightController.getFlights(false);
                    model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (int i = 0; i < 5; i++) {
                        model.insertRow(i, new Object[]{flights[i][0], flights[i][1], flights[i][2],flights[i][3] });
                    }
                    break;
            }
        });

        ticketClass.setBounds(527, 48, 100, 23);
        frame.getContentPane().add(ticketClass);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("NrOf");
        model.addColumn("TravelID");
        model.addColumn("Departure");
        model.addColumn("Destination");
        scrollPane.setViewportView(table);
        for (int i = 0; i < 5; i++) {
                    model.insertRow(i, new Object[]{flights[i][0], flights[i][1], flights[i][2],flights[i][3] });
            }
        JButton btnMost = new JButton("Most Travels");
        btnMost.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            bestdays(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });

        btnMost.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnMost);
    }
    public void bestdays(JFrame frame)  {
        contentPane = new JPanel();
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(66, 82, 550, 321);
        frame.getContentPane().add(scrollPane);


        flights = FlightController.getBest(1);

        String[] sort =new String[]{"Dagar","Veckor","Månader"};
        JComboBox<String> ticketClass = new JComboBox<>(sort);
        ticketClass.addActionListener(e -> {
            String s = (String)ticketClass.getSelectedItem();
            switch(s){
                case "Dagar":
                    flights = FlightController.getBest(1);
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (int i = 0; i < 5; i++) {
                        model.insertRow(i, new Object[]{ flights[i][0], flights[i][1]});
                    }
                    break;
                case "Veckor" :
                    flights = FlightController.getBest(2);
                    model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (int i = 0; i < 5; i++) {
                        model.insertRow(i, new Object[]{ flights[i][0], flights[i][1]});
                    }
                    break;
                case "Månader" :
                    flights = FlightController.getBest(3);
                    model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (int i = 0; i < 5; i++) {
                        model.insertRow(i, new Object[]{ flights[i][0], flights[i][1]});
                    }
                    break;
            }
        });
        ticketClass.setBounds(527, 48, 100, 23);
        frame.getContentPane().add(ticketClass);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("");
        model.addColumn("Dept");
        scrollPane.setViewportView(table);
        for (int i = 0; i < 5; i++) {
            model.insertRow(i, new Object[]{flights[i][0], flights[i][1] });
        }

        JButton btnMost = new JButton("Analys");
        btnMost.addActionListener(arg0 -> {
            frame.getContentPane().removeAll();
            search(frame);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        });

        btnMost.setBounds(0, 2, 70, 23);
        frame.getContentPane().add(btnMost);
    }
}
