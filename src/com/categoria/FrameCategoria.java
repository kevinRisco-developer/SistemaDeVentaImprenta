/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.categoria;

import com.bdconexion.conexion;
import com.menu.menu2;
import java.awt.Color;
import java.awt.Frame;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADM
 */
public class FrameCategoria extends javax.swing.JFrame {

    int xMouse,yMouse;
    public FrameCategoria() {
        initComponents();
        formatoTabla(jTable1);
        addPanel.setVisible(false);
        eliminarPanel.setVisible(false);
        eliminarTxt.setVisible(false);
        actualizarPanel.setVisible(false);
        actualizarTxt.setVisible(false);
        mostrar(jTable1);
        jTextFieldCategoria.setBackground(new Color(0,0,0,0));
        jTextFieldCategoria.setEditable(false);
        separador.setVisible(false);
        header.setBackground(new Color(0,0,0,0));
        exitPanel.setVisible(false);
        prevPanel.setVisible(false);
        guardarPanel.setVisible(false);
        guardarTxt.setVisible(false);
        actualizarBtnPanel.setVisible(false);
        ActualizarBtnTxt.setVisible(false);
        jTable1.setDefaultEditor(Object.class, null);
////        jComboBox1=combo();
    }
    private void formatoTabla(JTable tabla){
        String[] columna = {"Categoria"};
        DefaultTableModel modelo = new DefaultTableModel(null, columna);
        tabla.setModel(modelo);
    }
    
    private void limpiarTabla(JTable tabla){//limpia la tabla
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
    }
    
    public void mostrar(JTable tabla){
        String sql="select * from categoria;";
        try(Connection con=conexion.conectar(); 
                PreparedStatement pst=con.prepareStatement(sql);
                ResultSet rs=pst.executeQuery()){
            limpiarTabla(tabla);
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            while(rs.next()){
                Object[] fila={rs.getString("nombre")};
                modelo.addRow(fila);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private String buscarIdCategoria(JTable tabla){
        String idCategoria="";
        String sql="select * from categoria where nombre=?;";
        String nombre=(String) tabla.getValueAt(tabla.getSelectedRow(), 0);
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, nombre);
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next())
                idCategoria=rs.getString("idCategoria");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return idCategoria;
    }
    
    public void aniadir(JTextField nombreCategoria){
        String comprobar=" select * from categoria where nombre = ?;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(comprobar)){
            pst.setString(1, nombreCategoria.getText());
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next()){
                    JOptionPane.showMessageDialog(new JFrame(), "Ya hay una categoria "+nombreCategoria.getText());
                    return;
                }else{
                    String sql="insert into categoria (nombre) values(?);";
                    if(nombreCategoria.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(), "No se han ingresado datos");
                    return;
                    }
                    try(PreparedStatement pst2=con.prepareStatement(sql)){
                        pst2.setString(1, nombreCategoria.getText());
                        pst2.executeUpdate();
                    }
                }
            }
        }catch (SQLException e) {
        e.printStackTrace();
        }
        
    }
    public void seleccionarDatos(JTable tabla, JTextField categoria) {
    // Si no hay ninguna fila seleccionada, no hacer nada
    if (tabla.getSelectedRow() == -1) {
        return;
    }

    // Obtener valores de la fila seleccionada y asignarlos a los JTextField
    categoria.setText((String) tabla.getValueAt(tabla.getSelectedRow(), 0)); // Columna 0: razonSocial
    }
    public void eliminar(JTable tabla){
        int confirmacion=JOptionPane.showConfirmDialog(new Frame(),
            "¿Estás seguro que quieres elimar la categoria: "+tabla.getValueAt(tabla.getSelectedRow(), 0)+"?",
            "confirmar eliminación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(confirmacion==JOptionPane.YES_OPTION){
            String sql="delete from categoria where idCategoria=?;";
            String idCategoria=buscarIdCategoria(tabla);
            try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
                pst.setString(1, idCategoria);
                pst.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        jTextFieldCategoria.setText("");
        actualizarTxt.setVisible(false);
        eliminarTxt.setVisible(false);
    }
    private String nombreCategoria(JTable tabla){
        String nombre="";
        String sql="select * from categoria where idCategoria=?;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, buscarIdCategoria(tabla));
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next()){
                    nombre=rs.getString("nombre");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return nombre;
    }
        
    public void actualizar(JTable tabla,JTextField nombreCategoria){
        int confirmacion=JOptionPane.showConfirmDialog(new Frame(),
            "¿Estás seguro que quieres actualizar a : "+nombreCategoria.getText()+"?",
            "confirmar actualización",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(confirmacion==JOptionPane.YES_OPTION){
           String actualizarSql="update categoria set nombre =? where idCategoria=?;";
        String nombreCategoriaTabla=nombreCategoria(tabla);
        if(nombreCategoria.getText().trim().equalsIgnoreCase(nombreCategoriaTabla.trim())){
            JOptionPane.showMessageDialog(new JFrame(), "El nombre ingresado es el mismo al original, ingresa otro");
            return;
        }
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(actualizarSql)){
            pst.setString(1, nombreCategoria.getText());
            pst.setString(2,buscarIdCategoria(tabla));
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        } 
        }
    }
    public JComboBox combo(){
        JComboBox combox=new JComboBox();
        String sql="select * from categoria;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql);
                ResultSet rs=pst.executeQuery()){
            while(rs.next()){
                combox.addItem(rs.getString("nombre"));
//                System.out.println(rs.getString("nombre"));
            }
        }catch(Exception e){
        }
        return combox;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        addTxt = new javax.swing.JLabel();
        addPanel = new javax.swing.JPanel();
        actualizarTxt = new javax.swing.JLabel();
        eliminarTxt = new javax.swing.JLabel();
        actualizarPanel = new javax.swing.JPanel();
        eliminarPanel = new javax.swing.JPanel();
        separador = new javax.swing.JSeparator();
        actualizarBtnPanel = new javax.swing.JPanel();
        ActualizarBtnTxt = new javax.swing.JLabel();
        exitTxt = new javax.swing.JLabel();
        exitPanel = new javax.swing.JPanel();
        prevTxt = new javax.swing.JLabel();
        prevPanel = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jTextFieldCategoria = new javax.swing.JTextField();
        guardarPanel = new javax.swing.JPanel();
        guardarTxt = new javax.swing.JLabel();
        categoriaTxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/add.png"))); // NOI18N
        addTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addTxtMouseExited(evt);
            }
        });
        jPanel1.add(addTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        addPanel.setBackground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout addPanelLayout = new javax.swing.GroupLayout(addPanel);
        addPanel.setLayout(addPanelLayout);
        addPanelLayout.setHorizontalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        addPanelLayout.setVerticalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel1.add(addPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 70, 70));

        actualizarTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/update5.png"))); // NOI18N
        actualizarTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        actualizarTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                actualizarTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                actualizarTxtMouseExited(evt);
            }
        });
        jPanel1.add(actualizarTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 230, 70, 70));

        eliminarTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/delete.png"))); // NOI18N
        eliminarTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eliminarTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eliminarTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eliminarTxtMouseExited(evt);
            }
        });
        jPanel1.add(eliminarTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 310, 70, 70));

        actualizarPanel.setBackground(new java.awt.Color(240, 240, 240));
        actualizarPanel.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout actualizarPanelLayout = new javax.swing.GroupLayout(actualizarPanel);
        actualizarPanel.setLayout(actualizarPanelLayout);
        actualizarPanelLayout.setHorizontalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        actualizarPanelLayout.setVerticalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel1.add(actualizarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 230, 70, 70));

        eliminarPanel.setBackground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout eliminarPanelLayout = new javax.swing.GroupLayout(eliminarPanel);
        eliminarPanel.setLayout(eliminarPanelLayout);
        eliminarPanelLayout.setHorizontalGroup(
            eliminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        eliminarPanelLayout.setVerticalGroup(
            eliminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel1.add(eliminarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 310, 70, 70));

        separador.setBackground(new java.awt.Color(0, 204, 204));
        separador.setForeground(new java.awt.Color(255, 51, 0));
        jPanel1.add(separador, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 320, 10));

        actualizarBtnPanel.setBackground(new java.awt.Color(69, 73, 74));

        ActualizarBtnTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ActualizarBtnTxt.setForeground(new java.awt.Color(204, 204, 204));
        ActualizarBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ActualizarBtnTxt.setText("Actualizar");
        ActualizarBtnTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ActualizarBtnTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ActualizarBtnTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ActualizarBtnTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout actualizarBtnPanelLayout = new javax.swing.GroupLayout(actualizarBtnPanel);
        actualizarBtnPanel.setLayout(actualizarBtnPanelLayout);
        actualizarBtnPanelLayout.setHorizontalGroup(
            actualizarBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actualizarBtnPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ActualizarBtnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        actualizarBtnPanelLayout.setVerticalGroup(
            actualizarBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actualizarBtnPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ActualizarBtnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(actualizarBtnPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 100, 30));

        exitTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        exitTxt.setForeground(new java.awt.Color(0, 0, 0));
        exitTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitTxt.setText("X");
        exitTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitTxtMouseExited(evt);
            }
        });
        jPanel1.add(exitTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        exitPanel.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout exitPanelLayout = new javax.swing.GroupLayout(exitPanel);
        exitPanel.setLayout(exitPanelLayout);
        exitPanelLayout.setHorizontalGroup(
            exitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        exitPanelLayout.setVerticalGroup(
            exitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(exitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

        jPanel1.add(prevPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

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

        jTextFieldCategoria.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTextFieldCategoria.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldCategoria.setBorder(null);
        jPanel1.add(jTextFieldCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 320, 30));

        guardarPanel.setBackground(new java.awt.Color(69, 73, 74));
        guardarPanel.setForeground(new java.awt.Color(60, 63, 65));

        guardarTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        guardarTxt.setForeground(new java.awt.Color(204, 204, 204));
        guardarTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        guardarTxt.setText("Guardar");
        guardarTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        guardarTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                guardarTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                guardarTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout guardarPanelLayout = new javax.swing.GroupLayout(guardarPanel);
        guardarPanel.setLayout(guardarPanelLayout);
        guardarPanelLayout.setHorizontalGroup(
            guardarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guardarPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(guardarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        guardarPanelLayout.setVerticalGroup(
            guardarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guardarPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(guardarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(guardarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 100, 30));

        categoriaTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        categoriaTxt.setForeground(new java.awt.Color(0, 0, 0));
        categoriaTxt.setText("Categoria:");
        jPanel1.add(categoriaTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, -1, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 220, 240));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/bg.png"))); // NOI18N
        bg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgMouseClicked(evt);
            }
        });
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTxtMouseEntered
        addPanel.setVisible(true);
    }//GEN-LAST:event_addTxtMouseEntered

    private void addTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTxtMouseExited
        addPanel.setVisible(false);
    }//GEN-LAST:event_addTxtMouseExited

    private void addTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTxtMouseClicked
        separador.setVisible(true);
        jTextFieldCategoria.setVisible(true);
        jTextFieldCategoria.setEditable(true);
        guardarPanel.setVisible(true);
        guardarTxt.setVisible(true);
    }//GEN-LAST:event_addTxtMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        jTextFieldCategoria.setVisible(true);
        separador.setVisible(true);
        actualizarTxt.setVisible(true);
        eliminarTxt.setVisible(true);
        seleccionarDatos(jTable1, jTextFieldCategoria);
    }//GEN-LAST:event_jTable1MouseClicked

    private void bgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMouseClicked
        actualizarTxt.setVisible(false);
        actualizarPanel.setVisible(false);
        eliminarTxt.setVisible(false);
        eliminarPanel.setVisible(false);
        separador.setVisible(false);
        jTextFieldCategoria.setVisible(false);
        guardarPanel.setVisible(false);
        guardarTxt.setVisible(false);
        jTextFieldCategoria.setText("");
        ActualizarBtnTxt.setVisible(false);
        actualizarBtnPanel.setVisible(false);
    }//GEN-LAST:event_bgMouseClicked

    private void actualizarTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarTxtMouseEntered
        actualizarPanel.setVisible(true);
    }//GEN-LAST:event_actualizarTxtMouseEntered

    private void actualizarTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarTxtMouseExited
        actualizarPanel.setVisible(false);
    }//GEN-LAST:event_actualizarTxtMouseExited

    private void eliminarTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eliminarTxtMouseEntered
        eliminarPanel.setVisible(true);
    }//GEN-LAST:event_eliminarTxtMouseEntered

    private void eliminarTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eliminarTxtMouseExited
        eliminarPanel.setVisible(false);
    }//GEN-LAST:event_eliminarTxtMouseExited

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        xMouse=evt.getX();
        yMouse=evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x=evt.getXOnScreen();
        int y=evt.getYOnScreen();
        this.setLocation(x-xMouse,y-yMouse);
    }//GEN-LAST:event_headerMouseDragged

    private void exitTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseEntered
        exitPanel.setVisible(true);
        exitTxt.setForeground(Color.white);
    }//GEN-LAST:event_exitTxtMouseEntered

    private void exitTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseExited
        exitPanel.setVisible(false);
        exitTxt.setForeground(Color.black);
    }//GEN-LAST:event_exitTxtMouseExited

    private void prevTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevTxtMouseEntered
        prevPanel.setVisible(true);
        prevTxt.setForeground(Color.white);
    }//GEN-LAST:event_prevTxtMouseEntered

    private void prevTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevTxtMouseExited
        prevPanel.setVisible(false);
        prevTxt.setForeground(Color.black);
    }//GEN-LAST:event_prevTxtMouseExited

    private void exitTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTxtMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitTxtMouseClicked

    private void prevTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevTxtMouseClicked
        menu2 menu=new menu2();
        this.setVisible(false);
        menu.setVisible(true);
    }//GEN-LAST:event_prevTxtMouseClicked

    private void guardarTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseEntered
        guardarTxt.setForeground(Color.black);
        guardarPanel.setBackground(new Color(51,255,0));//se pone en verde
    }//GEN-LAST:event_guardarTxtMouseEntered

    private void guardarTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseExited
        guardarTxt.setForeground(new Color(204,204,204));
        guardarPanel.setBackground(new Color(69,73,74));//color gris original
    }//GEN-LAST:event_guardarTxtMouseExited

    private void guardarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseClicked
        aniadir(jTextFieldCategoria);
        mostrar(jTable1);
        jTextFieldCategoria.setText("");
    }//GEN-LAST:event_guardarTxtMouseClicked

    private void eliminarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eliminarTxtMouseClicked
        eliminar(jTable1);
        mostrar(jTable1);
        
    }//GEN-LAST:event_eliminarTxtMouseClicked

    private void actualizarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarTxtMouseClicked
        jTextFieldCategoria.setEditable(true);
        actualizarBtnPanel.setVisible(true);
        ActualizarBtnTxt.setVisible(true);
    }//GEN-LAST:event_actualizarTxtMouseClicked

    private void ActualizarBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ActualizarBtnTxtMouseEntered
        actualizarBtnPanel.setBackground(new Color(240,240,240));
        ActualizarBtnTxt.setForeground(Color.black);
    }//GEN-LAST:event_ActualizarBtnTxtMouseEntered

    private void ActualizarBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ActualizarBtnTxtMouseExited
        actualizarBtnPanel.setBackground(new Color(69,73,74));
        ActualizarBtnTxt.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_ActualizarBtnTxtMouseExited

    private void ActualizarBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ActualizarBtnTxtMouseClicked
        actualizar(jTable1, jTextFieldCategoria);
        mostrar(jTable1);
        jTextFieldCategoria.setText("");
        ActualizarBtnTxt.setVisible(false);
        actualizarBtnPanel.setVisible(false);
        actualizarTxt.setVisible(false);
        actualizarPanel.setVisible(false);
        eliminarTxt.setVisible(false);
        eliminarPanel.setVisible(false);
        jTextFieldCategoria.setEditable(false);
    }//GEN-LAST:event_ActualizarBtnTxtMouseClicked

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
            java.util.logging.Logger.getLogger(FrameCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameCategoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ActualizarBtnTxt;
    private javax.swing.JPanel actualizarBtnPanel;
    private javax.swing.JPanel actualizarPanel;
    private javax.swing.JLabel actualizarTxt;
    private javax.swing.JPanel addPanel;
    private javax.swing.JLabel addTxt;
    private javax.swing.JLabel bg;
    private javax.swing.JLabel categoriaTxt;
    private javax.swing.JPanel eliminarPanel;
    private javax.swing.JLabel eliminarTxt;
    private javax.swing.JPanel exitPanel;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JPanel guardarPanel;
    private javax.swing.JLabel guardarTxt;
    private javax.swing.JPanel header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldCategoria;
    private javax.swing.JPanel prevPanel;
    private javax.swing.JLabel prevTxt;
    private javax.swing.JSeparator separador;
    // End of variables declaration//GEN-END:variables
}
