/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.RegistrarVentaCotizacion;

import javax.swing.JComboBox;
import java.sql.*;
import com.bdconexion.conexion;
import com.menu.menu2;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADM
 */
public class FrameVentaCotizacion extends javax.swing.JFrame {

    labelXDos label=new labelXDos();
    int xMouse,yMouse; 
    
    public FrameVentaCotizacion() {
        initComponents();
        header.setBackground(new Color(0,0,0,0));
        xPanel.setVisible(false);
        prevPanel.setVisible(false);
        okBtn.setVisible(false);
        mostrarCombo(combo);
        mostrarEnTabla(jTable1);
        combo.setVisible(false);
        label.cambiarColorX(jTable1,combo);
        ajustarAnchoColumnas(jTable1);
    }

    public void mostrarCombo(JComboBox combo){
        String sql="{CALL comboCotizacionVenta()};";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql);ResultSet rs=pst.executeQuery()){
            while(rs.next()){
                combo.addItem(rs.getString("nroCotizacion"));
            }
        }catch(Exception e){
            
        }
    }
    public String retornarIdCotizacion(JComboBox combo){
        String idCotizacion="";
        String sql="{CALL returnIdCotizacionXNroCotizacion(?)};";//recién
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setInt(1, Integer.parseInt((String) combo.getSelectedItem()));
            try(ResultSet rs=pst.executeQuery()){
            if(rs.next()){
                idCotizacion=rs.getString("idCotizacion");
            }
        }    
        }catch(Exception e){
            
        }
        return idCotizacion;
    }
    public String retornarIdVenta(JTable tabla, JComboBox combo){
        String idCotizacion=retornarIdCotizacion(combo);
        String sql="{CALL returnIdVentaXIdCotizacion(?)};";
        String idVenta="";
        try(Connection con=conexion.conectar(); PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, idCotizacion);
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next()){
                    idVenta=rs.getString("idVenta");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return idVenta;
    }
    public void mostrarEnTabla(JTable tabla){
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        String sql="{CALL showOnTableFrVentCtz()};";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql);
                ResultSet rs=pst.executeQuery()){
            while(rs.next()){
                Object[] fila={rs.getString("nroCotizacion")};
                modelo.addRow(fila);
            }
        }catch(Exception e){
        }
    }
    public void aniadirCotizacion(JComboBox combo){
        String idCotizacion=retornarIdCotizacion(combo);
        String sql="{CALL insertCtz(?)};";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, idCotizacion);
            pst.executeUpdate();
        }catch(Exception e){
            
        }
    }
    public void eliminar(JTable tabla, JComboBox combo){
        String sql="{CALL deleteSale(?)};";
        String idVenta=retornarIdVenta(tabla, combo);
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, idVenta);
            pst.executeQuery();
        }catch(Exception e){
        }
    }
    private void ajustarAnchoColumnas(JTable tabla) {
        tabla.getColumnModel().getColumn(0).setPreferredWidth(300); // Primera columna
        tabla.getColumnModel().getColumn(1).setMaxWidth(15);  // Tercera columna (donde está la "X")
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        AgreCtzBtn = new javax.swing.JToggleButton();
        combo = new javax.swing.JComboBox<>();
        okBtn = new javax.swing.JToggleButton();
        prevTxt = new javax.swing.JLabel();
        prevPanel = new javax.swing.JPanel();
        xTxt = new javax.swing.JLabel();
        xPanel = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cotizaciones Vendidas", ""
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 140, 270));

        AgreCtzBtn.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        AgreCtzBtn.setText("Registrar Cotización");
        AgreCtzBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgreCtzBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AgreCtzBtnMouseClicked(evt);
            }
        });
        jPanel1.add(AgreCtzBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 210, 40));

        combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboActionPerformed(evt);
            }
        });
        jPanel1.add(combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 130, -1));

        okBtn.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        okBtn.setText("Ok");
        okBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okBtnMouseClicked(evt);
            }
        });
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });
        jPanel1.add(okBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, 70, -1));

        prevTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        prevTxt.setForeground(new java.awt.Color(0, 0, 0));
        prevTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        prevTxt.setText("<");
        prevTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        prevTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prevTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prevTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                prevTxtMouseExited(evt);
            }
        });
        jPanel1.add(prevTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 40));

        prevPanel.setBackground(new java.awt.Color(82, 83, 87));

        javax.swing.GroupLayout prevPanelLayout = new javax.swing.GroupLayout(prevPanel);
        prevPanel.setLayout(prevPanelLayout);
        prevPanelLayout.setHorizontalGroup(
            prevPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        prevPanelLayout.setVerticalGroup(
            prevPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(prevPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 40));

        xTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        xTxt.setForeground(new java.awt.Color(0, 0, 0));
        xTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xTxt.setText("X");
        xTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        xTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                xTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                xTxtMouseExited(evt);
            }
        });
        jPanel1.add(xTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        xPanel.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout xPanelLayout = new javax.swing.GroupLayout(xPanel);
        xPanel.setLayout(xPanelLayout);
        xPanelLayout.setHorizontalGroup(
            xPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        xPanelLayout.setVerticalGroup(
            xPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(xPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 40));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/bg.png"))); // NOI18N
        bg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgMouseClicked(evt);
            }
        });
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_okBtnActionPerformed

    private void comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboActionPerformed
        // acá muestra en la tabla
    }//GEN-LAST:event_comboActionPerformed

    private void AgreCtzBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgreCtzBtnMouseClicked
        combo.setVisible(true);
        okBtn.setVisible(true);
    }//GEN-LAST:event_AgreCtzBtnMouseClicked

    private void bgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMouseClicked
        combo.setVisible(false);
        okBtn.setVisible(false);
    }//GEN-LAST:event_bgMouseClicked

    private void okBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okBtnMouseClicked
        aniadirCotizacion(combo);
        mostrarEnTabla(jTable1);
        combo.setVisible(false);
        okBtn.setVisible(false);
    }//GEN-LAST:event_okBtnMouseClicked

    private void xTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xTxtMouseEntered
        xPanel.setVisible(true);
        xTxt.setForeground(Color.white);
    }//GEN-LAST:event_xTxtMouseEntered

    private void xTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xTxtMouseExited
        xPanel.setVisible(false);
        xTxt.setForeground(Color.black);
    }//GEN-LAST:event_xTxtMouseExited

    private void prevTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevTxtMouseEntered
        prevPanel.setVisible(true);
        prevTxt.setForeground(Color.white);
    }//GEN-LAST:event_prevTxtMouseEntered

    private void prevTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevTxtMouseExited
        prevPanel.setVisible(false);
        prevTxt.setForeground(Color.black);
    }//GEN-LAST:event_prevTxtMouseExited

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x=evt.getXOnScreen();
        int y=evt.getYOnScreen();
        this.setLocation(x-xMouse,y-yMouse);
    }//GEN-LAST:event_headerMouseDragged

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        xMouse=evt.getX();
        yMouse=evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void xTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xTxtMouseClicked
        System.exit(0);
    }//GEN-LAST:event_xTxtMouseClicked

    private void prevTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevTxtMouseClicked
        this.setVisible(false);
        menu2 menu=new menu2();
        menu.setVisible(true);
        
    }//GEN-LAST:event_prevTxtMouseClicked

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
            java.util.logging.Logger.getLogger(FrameVentaCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameVentaCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameVentaCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameVentaCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameVentaCotizacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton AgreCtzBtn;
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JPanel header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton okBtn;
    private javax.swing.JPanel prevPanel;
    private javax.swing.JLabel prevTxt;
    private javax.swing.JPanel xPanel;
    private javax.swing.JLabel xTxt;
    // End of variables declaration//GEN-END:variables
}
