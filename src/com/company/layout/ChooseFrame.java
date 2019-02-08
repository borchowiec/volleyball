package com.company.layout;

import com.company.GamePlay;
import com.company.elements.Face;
import com.company.logic.FileToolkit;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ChooseFrame extends JFrame {
    private JPanel mainFrame;
    private JLabel titleLabel;
    private JPanel choosePanel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JScrollPane player1ScrollPane;
    private JScrollPane player2ScrollPane;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JButton playButton;

    private Face player1Face;
    private Face player2Face;

    public ChooseFrame() {
        super("Volleyball");
        this.setContentPane(mainFrame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        this.pack();

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);

        this.setVisible(true);
    }

    private void initComponents() {
        Face[] faces = FileToolkit.getFaces();

        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));

        ChangeListener changeListener = e -> {
            JRadioButton currentRadio = (JRadioButton)e.getSource();
            if(currentRadio.isSelected()) {
                currentRadio.setBackground(Color.GREEN);
            }
            else {
                currentRadio.setBackground(Color.white);
            }
        };

        ButtonGroup player1Group = new ButtonGroup();
        ButtonGroup player2Group = new ButtonGroup();

        for(Face f : faces) {
            JRadioButton player1RadioButton = new JRadioButton(new ImageIcon(f.MAIN_FACE));
            JRadioButton player2RadioButton = new JRadioButton(new ImageIcon(f.MAIN_FACE));

            player1RadioButton.addActionListener(e -> player1Face = f);
            player2RadioButton.addActionListener(e -> player2Face = f);

            player1RadioButton.addChangeListener(changeListener);
            player2RadioButton.addChangeListener(changeListener);

            player1Panel.add(player1RadioButton);
            player2Panel.add(player2RadioButton);

            player1Panel.setBackground(Color.white);
            player2Panel.setBackground(Color.white);

            player1Group.add(player1RadioButton);
            player2Group.add(player2RadioButton);
        }

        ((JRadioButton)player1Panel.getComponent(0)).doClick();
        ((JRadioButton)player2Panel.getComponent(1)).doClick();

        playButton.addActionListener(e -> {
            GamePlay.newGamePlay(player1Face, player2Face);
            this.dispose();
        });
    }
}
