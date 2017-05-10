/*
* William Clayton
*
* GUI desplay of the knights tour algorithm
*
*/


import java.awt.event.*;
import javax.swing.event.*;


public class KnightsTourFrame extends javax.swing.JFrame 
{    
    private KnightsTour tour, thread_tour;
    private Thread current_thread;
    private int size=5, delay = 50;
    private boolean Warns_display = false, pause = false;
    
    public KnightsTourFrame() 
    {
        initComponents();
        components();
        tour = new KnightsTourBruteForce((BoardDisplay)boardPanel,(StackDisplay)StackPanel, delay*10);
    }
    
    private void start()
    {
        try
        {
            int x = Integer.parseInt(x_textbox.getText());
            int y = Integer.parseInt(y_textbox.getText());
            int boardSize = boardSizes.getSelectedIndex()+5;
            if(x < boardSize && y < boardSize)
            { 
                ((BoardDisplay)boardPanel).paintComponent(boardPanel.getGraphics(),boardSizes.getSelectedIndex()+5,x,y);
                ((StackDisplay)StackPanel).clearAll(StackPanel.getGraphics());
                textErrorLabel.setText("");
                Thread tourThread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        thread_tour = tour;
                        thread_tour.run();
                        if(!(Brute.isSelected()) && Warns_display)
                            ((KnightsTourWarnsdorf)tour).display_moves = true;
                        thread_tour.tour(boardSizes.getSelectedIndex()+5,Integer.parseInt(x_textbox.getText()), Integer.parseInt(y_textbox.getText()));
                    }
                };
                tourThread.start();
                current_thread = tourThread;
            }
            else
            {
                textErrorLabel.setText("Error, values must be between 0 and " + (boardSize-1));
                x_textbox.setText("0");
                y_textbox.setText("0");
                ((BoardDisplay)boardPanel).paintComponent(boardPanel.getGraphics(),boardSizes.getSelectedIndex()+5,0,0);
            }
            
        } catch (NumberFormatException e)
        {
            textErrorLabel.setText("Error, values must be numbers");
            x_textbox.setText("0");
            y_textbox.setText("0");
            ((BoardDisplay)boardPanel).paintComponent(boardPanel.getGraphics(),boardSizes.getSelectedIndex()+5,0,0);
        }
    }
    
    private void components()
    {
        ButtonListener listener = new ButtonListener();
        
        Brute.addActionListener(listener);
        Brute.setActionCommand("Brute");
        Warnsdorf.addActionListener(listener);
        Warnsdorf.setActionCommand("Warnsdorf");
        
        displayMovesCheckbox.addActionListener(listener);
        displayMovesCheckbox.setActionCommand("check");
        
        startButton.addActionListener(listener);
        startButton.setActionCommand("Start");
        
        pauseButton.addActionListener(listener);
        pauseButton.setActionCommand("Pause");
                
        stopButton.addActionListener(listener);
        stopButton.setActionCommand("stop");
           
        boardSizes.addActionListener(listener);
        boardSizes.setActionCommand("board");
        
        SliderListener sliderListener = new SliderListener();
        delaySlider.addChangeListener(sliderListener);
    }
    
    private class ButtonListener implements ActionListener
    {	
        @Override
	public void actionPerformed(ActionEvent evt) 
	{
            //Buttons
            switch(evt.getActionCommand())
            {
            case "Brute":
                Brute.setSelected(true);
                Warnsdorf.setSelected(false);
                Warns_display = false;
                displayMovesCheckbox.setSelected(false);
                tour = new KnightsTourBruteForce((BoardDisplay)boardPanel,(StackDisplay)StackPanel, delay*10);
                tour.updateTime(delay*10);
                break;
            case "Warnsdorf":
                Brute.setSelected(false);
                Warnsdorf.setSelected(true);
                tour = new KnightsTourWarnsdorf((BoardDisplay)boardPanel,(StackDisplay)StackPanel,delay*10);
                tour.updateTime(delay*10);
                break;
            case "check":
                Warns_display = displayMovesCheckbox.isSelected();
                if(Warnsdorf.isSelected() && thread_tour != null && !thread_tour.finnished)
                    ((KnightsTourWarnsdorf)thread_tour).display_moves = Warns_display;
                break;
            case "Start":
                if(Brute.isSelected())
                {
                    displayMovesCheckbox.setSelected(false);
                    Warns_display = false;
                }
                if(pause)
                {
                    pause = false;
                    thread_tour.paused = false;
                }
                else if(thread_tour == null || thread_tour.finnished)
                {
                    int x = Integer.parseInt(x_textbox.getText());
                    int y = Integer.parseInt(y_textbox.getText());
                    ((BoardDisplay)boardPanel).paintComponent(boardPanel.getGraphics(),boardSizes.getSelectedIndex()+5);
                    ((BoardDisplay)boardPanel).paintComponent(boardPanel.getGraphics(),boardSizes.getSelectedIndex()+5,x,y);
                    start();
                    textErrorLabel.setText("");
                }
                else
                    textErrorLabel.setText("The current task must be completed");
                break;
            case "Pause":
                if(thread_tour != null && !pause)
                {
                    thread_tour.paused = true;
                    pause = true;
                }
                else if(thread_tour != null && pause)
                {
                    thread_tour.paused = false;
                    pause = false;
                }
                break;
            case "stop":
                textErrorLabel.setText("");
                if(current_thread != null)
                    try
                    {
                        if(pause)
                        {
                            thread_tour.paused = false;
                            pause = false;
                        }
                        thread_tour.Terminate();
                        current_thread.join();
                        ((StackDisplay)StackPanel).clearAll(StackPanel.getGraphics());
                    } catch(InterruptedException e) {}  //not using a break here on purpose to clear the board
            case "board":
                if(thread_tour != null && !thread_tour.finnished)
                {
                    boardSizes.setSelectedIndex(size-5);
                    break;
                }
                size = boardSizes.getSelectedIndex()+5;
                x_textbox.setText("0");
                y_textbox.setText("0");
                ((BoardDisplay)boardPanel).paintComponent(boardPanel.getGraphics(),size);
                break;
            }
	}	
    }
    
    private class SliderListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            delayTextbox.setText(delaySlider.getValue()/100.0+"");
            delay=delaySlider.getValue();
            tour.updateTime(delay*10);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        boardPanel = new BoardDisplay();
        selectionLabel = new javax.swing.JLabel();
        Brute = new javax.swing.JRadioButton();
        Warnsdorf = new javax.swing.JRadioButton();
        startButton = new javax.swing.JButton();
        x_textbox = new javax.swing.JTextField();
        pos_x_label = new javax.swing.JLabel();
        y_textbox = new javax.swing.JTextField();
        pos_y_labe = new javax.swing.JLabel();
        textErrorLabel = new javax.swing.JLabel();
        boardSizes = new javax.swing.JComboBox<>();
        stopButton = new javax.swing.JButton();
        delayLabel = new javax.swing.JLabel();
        delaySlider = new javax.swing.JSlider();
        delayTextbox = new javax.swing.JTextField();
        StackPanel = new StackDisplay();
        pauseButton = new javax.swing.JButton();
        displayMovesCheckbox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        title.setText("Knight's Tour");

        boardPanel.setPreferredSize(new java.awt.Dimension(400, 400));
        boardPanel.setRequestFocusEnabled(false);

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        selectionLabel.setText("Algorithms");

        Brute.setSelected(true);
        Brute.setText("Brute Force");

        Warnsdorf.setText("Warnsdorf Rules");

        startButton.setText("Start");

        x_textbox.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        x_textbox.setText("0");
        x_textbox.setName(""); // NOI18N

        pos_x_label.setText("Starting Position:   x:");

        y_textbox.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        y_textbox.setText("0");

        pos_y_labe.setText("y:");

        textErrorLabel.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        textErrorLabel.setForeground(new java.awt.Color(255, 0, 0));
        textErrorLabel.setMinimumSize(new java.awt.Dimension(10, 10));

        boardSizes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));

        stopButton.setText("Stop");

        delayLabel.setText("Delay:");

        delaySlider.setMajorTickSpacing(50);
        delaySlider.setMinorTickSpacing(25);
        delaySlider.setPaintTicks(true);

        delayTextbox.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        delayTextbox.setText(".5");

        javax.swing.GroupLayout StackPanelLayout = new javax.swing.GroupLayout(StackPanel);
        StackPanel.setLayout(StackPanelLayout);
        StackPanelLayout.setHorizontalGroup(
            StackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );
        StackPanelLayout.setVerticalGroup(
            StackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pauseButton.setText("||");
        pauseButton.setActionCommand("");

        displayMovesCheckbox.setText("Display Moves");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(StackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectionLabel)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(startButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(stopButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pauseButton)))
                        .addGap(21, 21, 21)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(pos_x_label, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(x_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pos_y_labe)
                                .addGap(9, 9, 9)
                                .addComponent(y_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(delayLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(delayTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(delaySlider, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(Brute)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Warnsdorf)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(displayMovesCheckbox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(boardSizes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(173, Short.MAX_VALUE))))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(StackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectionLabel)
                    .addComponent(Brute)
                    .addComponent(Warnsdorf)
                    .addComponent(boardSizes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(displayMovesCheckbox))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(x_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pos_x_label)
                    .addComponent(y_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pos_y_labe)
                    .addComponent(textErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(delaySlider, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stopButton)
                            .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delayLabel)
                            .addComponent(delayTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KnightsTourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KnightsTourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KnightsTourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KnightsTourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KnightsTourFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Brute;
    private javax.swing.JPanel StackPanel;
    private javax.swing.JRadioButton Warnsdorf;
    private javax.swing.JPanel boardPanel;
    private javax.swing.JComboBox<String> boardSizes;
    private javax.swing.JLabel delayLabel;
    private javax.swing.JSlider delaySlider;
    private javax.swing.JTextField delayTextbox;
    private javax.swing.JCheckBox displayMovesCheckbox;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton pauseButton;
    private javax.swing.JLabel pos_x_label;
    private javax.swing.JLabel pos_y_labe;
    private javax.swing.JLabel selectionLabel;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel textErrorLabel;
    private javax.swing.JLabel title;
    private javax.swing.JTextField x_textbox;
    private javax.swing.JTextField y_textbox;
    // End of variables declaration//GEN-END:variables
}
