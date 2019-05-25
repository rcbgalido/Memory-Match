package memorymatch;

import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author rcbgalido
 */
public class Main extends javax.swing.JFrame {
    
    private boolean gridLock;
    private final static int NUMBER_OF_TILES = 12;
    private final static int NUMBER_OF_RANDOMIZATION = 100;
    private final static String RESOURCE_LOCATION = "resources/";
    
    private boolean noOpenedTileFlag; // opened tile - a tile that is opened but not yet correctly matched
    private int timeElapsed; // records the time elapsed after starting the game (in seconds)
    private int matchedTilesCount; // counts the number of tiles that are already correctly matched
    private int firstOpenedTileNumber, secondOpenedTileNumber;
    
    private final JButton[] gridButtons;
    private final String[] tileImageFilenames;
    private final boolean[] matchedTileFlag; // flag that determines whether a certain tile is already correctly matched
    private final Random random;
    
    private Timer timer;
    private ImageIcon titleImage;

    public Main() {
        initComponents();
        setLocationRelativeTo(null); // position frame in the center of the screen
        
        gridButtons = new JButton[]{
            oneBTN, twoBTN, threeBTN, fourBTN,
            fiveBTN, sixBTN, sevenBTN, eightBTN,
            nineBTN, tenBTN, elevenBTN, twelveBTN
        };
        tileImageFilenames = new String[]{
            "", 
            "02_tile_1.jpg", "02_tile_1.jpg", "03_tile_2.jpg", "03_tile_2.jpg", 
            "04_tile_3.jpg", "04_tile_3.jpg", "05_tile_4.jpg", "05_tile_4.jpg",
            "06_tile_5.jpg", "06_tile_5.jpg", "07_tile_6.jpg", "07_tile_6.jpg"
        };
        matchedTileFlag = new boolean[NUMBER_OF_TILES];
        random = new Random();
    }
    
    private void startGame() {
        // randomize tileImageFilenames values (randomizes the grid)
        for (int a = 0; a < NUMBER_OF_RANDOMIZATION; a++) {
            int first = 1 + random.nextInt(NUMBER_OF_TILES);
            int second = 1 + random.nextInt(NUMBER_OF_TILES);
            String temp = tileImageFilenames[first];
            tileImageFilenames[first] = tileImageFilenames[second];
            tileImageFilenames[second] = temp;
        }
        
        noOpenedTileFlag = true;
        matchedTilesCount = 0;
        Arrays.fill(matchedTileFlag, false);
        startBTN.setEnabled(false);
        
        // reset all tiles to no icon/image and set them to be enabled (clickable)
        for (JButton gridButton : gridButtons) {
            gridButton.setIcon(null);
            gridButton.setEnabled(true);
        }
        
        startTimer();
    }
    
    private void startTimer() {
        timeElapsed = 0;
        timerTF.setText("" + timeElapsed);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                timerTF.setText("" + (++timeElapsed));
            }
        }, 1000, 1000);
    }
    
    private void onTileClick(int tileNumber) {
        // check if grid is not locked and clicked tile is not yet correctly matched
        if (!gridLock && !matchedTileFlag[tileNumber - 1]) {
            
            // display image on clicked tile based on corresponding tileImageFilenames value
            ImageIcon tileImage = new ImageIcon(getClass().getResource(RESOURCE_LOCATION + tileImageFilenames[tileNumber]));
            gridButtons[tileNumber - 1].setIcon(tileImage);
            
            if (noOpenedTileFlag) {
                firstOpenedTileNumber = tileNumber;
            } else {
                secondOpenedTileNumber = tileNumber;
                if (tileImageFilenames[firstOpenedTileNumber].equals(tileImageFilenames[secondOpenedTileNumber]) 
                        && firstOpenedTileNumber != secondOpenedTileNumber) { // correct match!
                    
                    matchedTileFlag[firstOpenedTileNumber - 1] = true;
                    matchedTileFlag[secondOpenedTileNumber - 1] = true;
                    matchedTilesCount += 2;
                    
                    if (matchedTilesCount == NUMBER_OF_TILES) { // solved!
                        timer.cancel();
                        startBTN.setEnabled(true);
                    }
                } else {
                    IncorrectMatchThread incorrectMatchThread = new IncorrectMatchThread();
                    incorrectMatchThread.start();
                }
            }
            noOpenedTileFlag = !noOpenedTileFlag;
        }
    }
    
    class IncorrectMatchThread extends Thread {

        public void run() {
            gridLock = true; // nothing happens when clicking the tiles as long as gridLock is set to true
            
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // reset incorrectly matched tiles
            gridButtons[firstOpenedTileNumber - 1].setIcon(null);
            gridButtons[secondOpenedTileNumber - 1].setIcon(null);
            
            gridLock = false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        oneBTN = new javax.swing.JButton();
        threeBTN = new javax.swing.JButton();
        twoBTN = new javax.swing.JButton();
        fourBTN = new javax.swing.JButton();
        eightBTN = new javax.swing.JButton();
        fiveBTN = new javax.swing.JButton();
        sixBTN = new javax.swing.JButton();
        sevenBTN = new javax.swing.JButton();
        nineBTN = new javax.swing.JButton();
        tenBTN = new javax.swing.JButton();
        elevenBTN = new javax.swing.JButton();
        twelveBTN = new javax.swing.JButton();
        startBTN = new javax.swing.JButton();
        timerTF = new javax.swing.JTextField();
        titleImage=new ImageIcon(getClass().getResource("resources/01_title.png"));
        jLabel2 = new javax.swing.JLabel(titleImage);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Memory Match");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(91, 125, 135));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(46, 50, 60), 10));

        jPanel2.setBackground(new java.awt.Color(91, 125, 135));

        oneBTN.setEnabled(false);
        oneBTN.setFocusable(false);
        oneBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        oneBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneBTNActionPerformed(evt);
            }
        });

        threeBTN.setEnabled(false);
        threeBTN.setFocusable(false);
        threeBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        threeBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeBTNActionPerformed(evt);
            }
        });

        twoBTN.setEnabled(false);
        twoBTN.setFocusable(false);
        twoBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        twoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoBTNActionPerformed(evt);
            }
        });

        fourBTN.setEnabled(false);
        fourBTN.setFocusable(false);
        fourBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fourBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourBTNActionPerformed(evt);
            }
        });

        eightBTN.setEnabled(false);
        eightBTN.setFocusable(false);
        eightBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        eightBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightBTNActionPerformed(evt);
            }
        });

        fiveBTN.setEnabled(false);
        fiveBTN.setFocusable(false);
        fiveBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fiveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiveBTNActionPerformed(evt);
            }
        });

        sixBTN.setEnabled(false);
        sixBTN.setFocusable(false);
        sixBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sixBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixBTNActionPerformed(evt);
            }
        });

        sevenBTN.setEnabled(false);
        sevenBTN.setFocusable(false);
        sevenBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sevenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sevenBTNActionPerformed(evt);
            }
        });

        nineBTN.setEnabled(false);
        nineBTN.setFocusable(false);
        nineBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nineBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nineBTNActionPerformed(evt);
            }
        });

        tenBTN.setEnabled(false);
        tenBTN.setFocusable(false);
        tenBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenBTNActionPerformed(evt);
            }
        });

        elevenBTN.setEnabled(false);
        elevenBTN.setFocusable(false);
        elevenBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        elevenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elevenBTNActionPerformed(evt);
            }
        });

        twelveBTN.setEnabled(false);
        twelveBTN.setFocusable(false);
        twelveBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        twelveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twelveBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(fiveBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sixBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sevenBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(eightBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(oneBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(twoBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(threeBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(fourBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(nineBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tenBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(elevenBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(twelveBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(oneBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(threeBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(twoBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fourBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fiveBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sevenBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sixBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eightBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nineBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(elevenBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(twelveBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        startBTN.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        startBTN.setText("Start Game");
        startBTN.setFocusable(false);
        startBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBTNActionPerformed(evt);
            }
        });

        timerTF.setEditable(false);
        timerTF.setBackground(new java.awt.Color(204, 204, 204));
        timerTF.setFont(new java.awt.Font("Arial Unicode MS", 0, 24)); // NOI18N
        timerTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timerTF.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(startBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timerTF))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timerTF, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBTNActionPerformed
        startGame();
    }//GEN-LAST:event_startBTNActionPerformed

    private void oneBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneBTNActionPerformed
        onTileClick(1);
    }//GEN-LAST:event_oneBTNActionPerformed

    private void threeBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeBTNActionPerformed
        onTileClick(3);
    }//GEN-LAST:event_threeBTNActionPerformed

    private void twoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoBTNActionPerformed
        onTileClick(2);
    }//GEN-LAST:event_twoBTNActionPerformed

    private void fourBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourBTNActionPerformed
        onTileClick(4);
    }//GEN-LAST:event_fourBTNActionPerformed

    private void fiveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiveBTNActionPerformed
        onTileClick(5);
    }//GEN-LAST:event_fiveBTNActionPerformed

    private void sixBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixBTNActionPerformed
        onTileClick(6);
    }//GEN-LAST:event_sixBTNActionPerformed

    private void sevenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sevenBTNActionPerformed
        onTileClick(7);
    }//GEN-LAST:event_sevenBTNActionPerformed

    private void eightBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightBTNActionPerformed
        onTileClick(8);
    }//GEN-LAST:event_eightBTNActionPerformed

    private void nineBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nineBTNActionPerformed
        onTileClick(9);
    }//GEN-LAST:event_nineBTNActionPerformed

    private void tenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenBTNActionPerformed
        onTileClick(10);
    }//GEN-LAST:event_tenBTNActionPerformed

    private void elevenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elevenBTNActionPerformed
        onTileClick(11);
    }//GEN-LAST:event_elevenBTNActionPerformed

    private void twelveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twelveBTNActionPerformed
        onTileClick(12);
    }//GEN-LAST:event_twelveBTNActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton eightBTN;
    private javax.swing.JButton elevenBTN;
    private javax.swing.JButton fiveBTN;
    private javax.swing.JButton fourBTN;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton nineBTN;
    private javax.swing.JButton oneBTN;
    private javax.swing.JButton sevenBTN;
    private javax.swing.JButton sixBTN;
    private javax.swing.JButton startBTN;
    private javax.swing.JButton tenBTN;
    private javax.swing.JButton threeBTN;
    private javax.swing.JTextField timerTF;
    private javax.swing.JButton twelveBTN;
    private javax.swing.JButton twoBTN;
    // End of variables declaration//GEN-END:variables
}
