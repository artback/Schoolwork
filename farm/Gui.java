package com.project.farm;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;


public class Gui {
    private Game game;
    private JButton playButton;
    private JButton readFromFile;
    private JButton saveToFile;
    private JButton resetScore;
    private Vector<JButton> animalButtons;
    private JFileChooser fc;
    private JLabel pointsLabel;
    private JFrame frame;

    private void createbuttons(){
        animalButtons = new Vector<>();
        animalButtons.add(new JButton("Hen"));
        animalButtons.add(new JButton("Cow"));
        animalButtons.add(new JButton("Sheep"));
        animalButtons.add(new JButton("Pig"));
        animalButtons.add(new JButton("Dog"));
        playButton = new JButton("Play");
        resetScore = new JButton("Reset");
        readFromFile = new JButton("Load");
        saveToFile = new JButton("Save");
        addbuttonsListeners();
    }
    private void addbuttonsListeners(){
        ButtonListener butt = new ButtonListener();
        for (int i = 0; i <animalButtons.size() ; i++) {
           animalButtons.elementAt(i).addActionListener(butt);
        }
        playButton.addActionListener(butt);
        resetScore.addActionListener(butt);
        readFromFile.addActionListener(butt);
        saveToFile.addActionListener(butt);
    }
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == "Pig" || e.getActionCommand() == "Cow" || e.getActionCommand() == "Dog" ||
                    e.getActionCommand() == "Hen" || e.getActionCommand() == "Sheep") {
                    game.stopSound();
                boolean rightbutton = game.animalclicked(e.getActionCommand());
                if (rightbutton) {
                    pointsLabel.setText(String.valueOf(game.getHighscore()));
                }
            } else if (e.getActionCommand() == "Save") {
                fc.showSaveDialog(null);
                File file = fc.getSelectedFile();
                game.saveHighscore(file);
            } else if (e.getActionCommand() == "Load") {
                fc.showOpenDialog(null);
                File file = fc.getSelectedFile();
                game.readHighscore(file);
                pointsLabel.setText(String.valueOf(game.getHighscore()));
            } else if (e.getActionCommand() == "Reset") {
                game.setHighscore(0);
                pointsLabel.setText(String.valueOf(game.getHighscore()));
            } else if (e.getActionCommand() == "Play") {
                game.playSound();
            }

        }
    }

    public Gui() {
        createbuttons();
        game = new Game();
        pointsLabel = new JLabel(String.valueOf(game.getHighscore()));
        fc = new JFileChooser();
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);
        pointsLabel.setVerticalAlignment(JLabel.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 25));
        frame = new JFrame("Guess the Animal");
    }

    public JFrame init() {
        Container content = frame.getContentPane();
        content.add(buttonPanel(), BorderLayout.WEST);
        content.add(playerPanel());
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JComponent buttonPanel() {
        JPanel animalPanel = new JPanel();
        for (int i = 0; i < animalButtons.size() ; i++) {
           animalPanel.add(animalButtons.elementAt(i));
        }
        animalPanel.setLayout((new GridLayout(animalButtons.size(),1)));
        animalPanel.setBounds(0, 150, 150, 300);
        return animalPanel;
    }
    private JComponent playerPanel() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(60);
        splitPane.setTopComponent(pointsLabel);
        splitPane.setBottomComponent(splitPane2);
        splitPane2.setTopComponent(playButton);
        JPanel bottomPanel = new JPanel();
        splitPane2.setDividerLocation(200);
        bottomPanel.setLayout(new GridLayout(1,3));
        bottomPanel.add(resetScore);
        bottomPanel.add(readFromFile);
        bottomPanel.add(saveToFile);
        splitPane2.setBottomComponent(bottomPanel);
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        return splitPane;
    }
}
