package com.project.farm;


import javafx.scene.control.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class Gui {
    private Player play;
    private JFileChooser fc;
    private Vector<Animals> animals;
    private JLabel textName;
    private JFrame frame;
    private Animals selected;

    private void addAnimals() {
        animals = new Vector<>();
        selected = animals.elementAt(0);
        animals.add(new Pig());
        animals.add(new Cow());
        animals.add(new Dog());
    }

    public Gui() {
        addAnimals();
        textName = new JLabel();
        fc = new JFileChooser();
        textName.setHorizontalAlignment(JLabel.CENTER);
        textName.setVerticalAlignment(JLabel.CENTER);
        textName.setFont(new Font("Arial", Font.BOLD, 25));
        play = new Player();
        frame = new JFrame("Virtual Farm");
    }

    public JFrame CreateGui() {
        Container content = frame.getContentPane();
        content.add(buttonPanel(), BorderLayout.WEST);
        content.add(rightPanel(), BorderLayout.EAST);
        content.add(playerPanel());
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JComponent buttonPanel() {
        JPanel animalButtons = new JPanel();
        animalButtons.setLayout((new GridLayout(3, 1)));
        animalButtons.setBounds(0, 150, 150, 300);
        for (int i = 0; i < animals.size(); i++) {
            animalButtons.add(animals.elementAt(i).button);
        }
        animalAction();
        return animalButtons;
    }

    private JComponent playerPanel() {
        JPanel playPanel = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(60);
        splitPane.setTopComponent(textName);
        playPanel.setLayout(new BorderLayout());
        play.button.setHorizontalAlignment(SwingConstants.CENTER);
        playPanel.add(play.button);
        splitPane.setBottomComponent(playPanel);
        return splitPane;
    }

    private JComponent rightPanel() {
        JPanel rightButtons = new JPanel();
        JButton changeName = new JButton("Rename");
        JButton saveToFile = new JButton("Save");
        JButton readFromFile = new JButton("Load");
        rightButtons.setLayout((new GridLayout(3, 1)));
        rightButtons.setBounds(200, 300, 100, 200);
        rightButtons.add(changeName);
        rightButtons.add(saveToFile);
        rightButtons.add(readFromFile);
        changeName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selected != null) {
                    String name = JOptionPane.showInputDialog(null, "Set The name on the " + selected.animal, "Enter Name", JOptionPane.QUESTION_MESSAGE);
                    if (name != "") {
                        selected.name = name;
                    }
                    textName.setText("the " + selected.animal +"'s name is " + selected.name);
                } else {
                    JOptionPane.showMessageDialog(null, "No animal Selected");
                }
            }
        });
        saveToFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                int returnVal = fc.showSaveDialog(null);
                File file = fc.getSelectedFile();
                try {
                    FileWriter fstream = new FileWriter(file);
                    BufferedWriter out = new BufferedWriter(fstream);
                    for (int i = 0; i < animals.size(); i++) {
                        out.write(animals.elementAt(i).name + "\n");
                    }
                    out.close();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        });

        readFromFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                int returnVal = fc.showOpenDialog(null);
                File file = fc.getSelectedFile();
                try (BufferedReader br = new BufferedReader(new FileReader(file)))
                {
                    for(int i = 0; i < animals.size(); i++) {
                      animals.elementAt(i).name = br.readLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        return rightButtons;
    }
    private void animalAction() {
        for (int i = 0; i < animals.size(); i++) {
            final int localI = i;
            animals.elementAt(i).button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    play.stop();
                    play.setSound(animals.elementAt(localI).sound);
                    textName.setText("the " + animals.elementAt(localI).animal +"'s name is " + animals.elementAt(localI).name);
                    selected = animals.elementAt(localI);
                }
            });
        }
    }
}
