/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn_lab_project_rdp.client;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Dishita Madani
 */
public class clientscreenshare extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form clientscreenshare
     */
    public clientscreenshare() {
        initComponents();
    }

    private static long nextTime = 0;
    private static Client clientApp = null;
    private String serverName = "127.0.0.1"; //loop back ip
    private int portNo = 8088;
    private Date date = null;
    //private Socket serverSocket = null;

    public void getNextFreq() {
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        long value = random.nextInt(10);
        nextTime = currentTime + value;
        //return currentTime+value;
    }

    @Override
    public void run() {
        clientscreenshare frame = new clientscreenshare();

        frame.setSize(1930, 1050);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        frame.pack();
        frame.setVisible(true);
        while (true) {
            try {

                date = new Date();
                Socket serverSocket = new Socket(serverName, portNo);
                DateFormat dateFormat = new SimpleDateFormat("_yyMMdd_HHmmss");
                String fileName = serverSocket.getInetAddress().getHostName().replace(".", "-");
                System.out.println(fileName);
                BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(serverSocket.getInputStream()));

                JLabel lab = new JLabel(new ImageIcon(img));
                lab.setSize(500, 500);
                frame.add(lab);
                frame.repaint();
                frame.pack();

                sleep(1000);
                frame.remove(lab);

                System.out.println("Image here");

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jButton1.setBackground(new java.awt.Color(102, 0, 0));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Stop Share");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(1659, 22, 240, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cn_lab_project_rdp/client/imgonline-com-ua-resize-moyxaEMgPu6.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1930, 1050);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);

    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(clientscreenshare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(clientscreenshare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(clientscreenshare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(clientscreenshare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new clientscreenshare().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
