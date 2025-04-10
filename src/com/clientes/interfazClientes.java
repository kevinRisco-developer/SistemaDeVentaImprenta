
package com.clientes;

import com.bdconexion.conexion;
import com.entidades.Clientes;
import com.menu.menu2;
import estructuraDeDatos.ListaEnlazada;
import java.awt.Color;
import java.awt.Frame;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public final class interfazClientes extends javax.swing.JFrame {

    int xMouse, yMouse;
    public interfazClientes() {
        initComponents();
        exitBtn.setVisible(false);
        prevBtn.setVisible(false);
        header.setBackground(new Color(0,0,0,0));
        razonSocialTextField.setBackground(new Color(0,0,0,0));
        rucJTextField.setBackground(new Color(0,0,0,0));
        invisible.setVisible(false);
        mostrarClientesEnTabla(jTable);
        addUser.setVisible(false);
        
        guardarTxt.setVisible(false);
        guardar.setVisible(false);
        
        razonSocialTextField.setEditable(false);
        rucJTextField.setEditable(false);
        
        deletePanel.setVisible(false);
        deleteTxt.setVisible(false);
        actualizarPanel.setVisible(false);
        actualizarTxt.setVisible(false);
        
        actualizarBtnPanel.setVisible(false);
        actualizarBtnTxt.setVisible(false);
        
        jTable.setDefaultEditor(Object.class, null);
    }

    
    public void mostrarClientesEnTabla(JTable tabla) {
    String sql = "SELECT * FROM clientes;";
    ListaEnlazada lista=new ListaEnlazada();

    try (Connection con = conexion.conectar();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        String[] columnas = {"Razon Social", "RUC"};

        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tabla.setModel(modelo); 

        while (rs.next()) {
            Clientes cliente=new Clientes(rs.getString("idClientes"), rs.getString("razonSocial"), rs.getString("RUC"));
            lista.insertar(cliente);
            modelo.addRow(new Object[]{cliente.getRazonSocial(),cliente.getRuc()});
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Muestra errores en consola
    }
}
    public void seleccionarDatos(JTable tabla, JTextField rss, JTextField ruc) {
    // Si no hay ninguna fila seleccionada, no hacer nada
    if (tabla.getSelectedRow() == -1) {
        return;
    }

    // Obtener valores de la fila seleccionada y asignarlos a los JTextField
    rss.setText((String) tabla.getValueAt(tabla.getSelectedRow(), 0)); // Columna 0: razonSocial
    ruc.setText((String) tabla.getValueAt(tabla.getSelectedRow(), 1)); // Columna 1: RUC
    }

    public void insertarCliente(String razonSocial, String ruc){
        String sql="insert into clientes (razonSocial,ruc) values (?,?);";
        try(Connection con=conexion.conectar(); PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, razonSocial);
            pst.setString(2, ruc);
            pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void elimnar(JTextField JTxtRazonSocial, JTextField JTxtruc){
        String razonSocial=JTxtRazonSocial.getText();
        String ruc=JTxtruc.getText();
        if(razonSocial.isEmpty() && ruc.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "Los campos están vacios");
            return;
        }else{
                String idClientes = encontrarIdCliente(razonSocial, ruc);
                    if (idClientes.isEmpty()) { // Si no se encontró el cliente, se muestra un mensaje y se detiene
                        JOptionPane.showMessageDialog(new JFrame(), "Cliente no encontrado.");
                        return;
                    }       
                        int confirmacion=JOptionPane.showConfirmDialog(new Frame(),
                                   "¿Estás seguro que quieres elimar al cliente: "+razonSocial+" con RUC: "+ruc+" ?",
                                   "confirmar eliminación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                           
                        if(confirmacion==JOptionPane.YES_OPTION){
                            String deleteSQL = "DELETE FROM clientes WHERE idClientes = ?;";
                            try(Connection con=conexion.conectar(); PreparedStatement deletePst=
                                       con.prepareStatement(deleteSQL)){
                            deletePst.setString(1, idClientes);
                            deletePst.executeUpdate();
                               }
                            catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "Error al eliminar: " + e.getMessage());
            }
                   } 
                }
            
            }
       

    public String encontrarIdCliente(String razonSocial, String ruc){
        String sql="select * from clientes where razonSocial=? and ruc=?;";
        String idCliente="";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, razonSocial);
            pst.setString(2, ruc);
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next())
                    idCliente=rs.getString("idClientes");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(new JFrame(), "Error: " + e.getMessage());
        }
        
        return idCliente;
    }
    public String devolverRazonSeleccionTabla(JTable tabla){
        String razon="";
        razon=(String) tabla.getValueAt(tabla.getSelectedRow(), 0);
        return razon;
    }
    public String devolverRucSeleccionTabla(JTable tabla){
        String ruc="";
        ruc=(String) tabla.getValueAt(tabla.getSelectedRow(), 1);
        return ruc;
    }
    public void actualizar(JTextField JtxtRazonSocial, JTextField JTxtRuc, JTable tabla){
        String razonSocial=JtxtRazonSocial.getText();
        String ruc=JTxtRuc.getText();
        //ahora quiero los datos de la base de datos
        String sql="select * from clientes where razonSocial= ? and ruc= ?;";
        try(Connection con= conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, razonSocial);
            pst.setString(2, ruc);
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next()){//si existe lo que modifiqué
                    JOptionPane.showMessageDialog(new JFrame(), 
                            "Los datos son los mismos o ya existe ese cliente. Vuelve a modificarlos");
                        return;
                }else{ //si no existe lo va a actualizar
                    String idCliente=encontrarIdCliente(devolverRazonSeleccionTabla(tabla),
                            devolverRucSeleccionTabla(tabla));
                    if(idCliente == null || idCliente.trim().isEmpty()){
                        JOptionPane.showMessageDialog(new JFrame(), "No se pudo obtener el ID del cliente.");
                        return;
                    }
                    
                    int confirmacion=JOptionPane.showConfirmDialog(new Frame(),
                                   "¿Estás seguro que quieres actualizar a: "+razonSocial+" con RUC: "+ruc+" ?",
                                   "confirmar eliminación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(confirmacion==JOptionPane.YES_OPTION){
                    String sqlActualizar="update clientes set razonSocial=?, ruc=? where idClientes=?;";
                    try(PreparedStatement pstUpdate=con.prepareCall(sqlActualizar)){
                        pstUpdate.setString(1, razonSocial);
                        pstUpdate.setString(2, ruc);
                        pstUpdate.setString(3, idCliente);
                        pstUpdate.executeUpdate();
                    }
                  }
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(new JFrame(), "Error en la consulta: " + e.getMessage());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(new JFrame(), "Error: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        exitBtnTxt = new javax.swing.JLabel();
        exitBtn = new javax.swing.JPanel();
        prevBtnTxt = new javax.swing.JLabel();
        prevBtn = new javax.swing.JPanel();
        addUserTxt = new javax.swing.JLabel();
        addUser = new javax.swing.JPanel();
        rucTxt = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        rucJTextField = new javax.swing.JTextField();
        actualizarBtnTxt = new javax.swing.JLabel();
        actualizarBtnPanel = new javax.swing.JPanel();
        deleteTxt = new javax.swing.JLabel();
        deletePanel = new javax.swing.JPanel();
        actualizarTxt = new javax.swing.JLabel();
        actualizarPanel = new javax.swing.JPanel();
        razonSocialTextField = new javax.swing.JTextField();
        razonSocialTxt = new javax.swing.JLabel();
        guardar = new javax.swing.JPanel();
        guardarTxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        header = new javax.swing.JPanel();
        bg = new javax.swing.JLabel();
        invisible = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exitBtnTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        exitBtnTxt.setForeground(new java.awt.Color(0, 0, 0));
        exitBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitBtnTxt.setText("X");
        exitBtnTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitBtnTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitBtnTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitBtnTxtMouseExited(evt);
            }
        });
        jPanel1.add(exitBtnTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        exitBtn.setBackground(new java.awt.Color(255, 0, 51));
        exitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitBtnMouseExited(evt);
            }
        });

        javax.swing.GroupLayout exitBtnLayout = new javax.swing.GroupLayout(exitBtn);
        exitBtn.setLayout(exitBtnLayout);
        exitBtnLayout.setHorizontalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        exitBtnLayout.setVerticalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(exitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        prevBtnTxt.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        prevBtnTxt.setForeground(new java.awt.Color(0, 0, 0));
        prevBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        prevBtnTxt.setText("<");
        prevBtnTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prevBtnTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prevBtnTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                prevBtnTxtMouseExited(evt);
            }
        });
        jPanel1.add(prevBtnTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 40));

        prevBtn.setBackground(new java.awt.Color(82, 83, 87));
        prevBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prevBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                prevBtnMouseExited(evt);
            }
        });

        javax.swing.GroupLayout prevBtnLayout = new javax.swing.GroupLayout(prevBtn);
        prevBtn.setLayout(prevBtnLayout);
        prevBtnLayout.setHorizontalGroup(
            prevBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        prevBtnLayout.setVerticalGroup(
            prevBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(prevBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 40));

        addUserTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addUserTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/aniadirUsuario.png"))); // NOI18N
        addUserTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addUserTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addUserTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addUserTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addUserTxtMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addUserTxtMousePressed(evt);
            }
        });
        jPanel1.add(addUserTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));
        addUserTxt.getAccessibleContext().setAccessibleParent(jPanel1);

        addUser.setBackground(new java.awt.Color(204, 204, 204));
        addUser.setPreferredSize(new java.awt.Dimension(85, 85));

        javax.swing.GroupLayout addUserLayout = new javax.swing.GroupLayout(addUser);
        addUser.setLayout(addUserLayout);
        addUserLayout.setHorizontalGroup(
            addUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        addUserLayout.setVerticalGroup(
            addUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel1.add(addUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 70, 70));

        rucTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        rucTxt.setForeground(new java.awt.Color(255, 255, 255));
        rucTxt.setText("RUC:");
        rucTxt.setToolTipText("");
        jPanel1.add(rucTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, -1, -1));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, 140, 20));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 360, 20));

        rucJTextField.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        rucJTextField.setBorder(null);
        jPanel1.add(rucJTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, 140, 30));

        actualizarBtnTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        actualizarBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        actualizarBtnTxt.setText("Actualizar");
        actualizarBtnTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        actualizarBtnTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarBtnTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                actualizarBtnTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                actualizarBtnTxtMouseExited(evt);
            }
        });
        jPanel1.add(actualizarBtnTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, 100, 30));

        javax.swing.GroupLayout actualizarBtnPanelLayout = new javax.swing.GroupLayout(actualizarBtnPanel);
        actualizarBtnPanel.setLayout(actualizarBtnPanelLayout);
        actualizarBtnPanelLayout.setHorizontalGroup(
            actualizarBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        actualizarBtnPanelLayout.setVerticalGroup(
            actualizarBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(actualizarBtnPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, -1, 30));

        deleteTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/delete.png"))); // NOI18N
        deleteTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteTxtMouseExited(evt);
            }
        });
        jPanel1.add(deleteTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, -1, -1));

        deletePanel.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout deletePanelLayout = new javax.swing.GroupLayout(deletePanel);
        deletePanel.setLayout(deletePanelLayout);
        deletePanelLayout.setHorizontalGroup(
            deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        deletePanelLayout.setVerticalGroup(
            deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel1.add(deletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, -1, -1));

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
        jPanel1.add(actualizarTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 280, -1, -1));

        actualizarPanel.setBackground(new java.awt.Color(204, 204, 204));

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

        jPanel1.add(actualizarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 280, -1, -1));

        razonSocialTextField.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        razonSocialTextField.setBorder(null);
        razonSocialTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                razonSocialTextFieldActionPerformed(evt);
            }
        });
        jPanel1.add(razonSocialTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 360, 30));

        razonSocialTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        razonSocialTxt.setForeground(new java.awt.Color(0, 0, 0));
        razonSocialTxt.setText("Razón social:");
        jPanel1.add(razonSocialTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 116, 30));

        guardar.setBackground(new java.awt.Color(69, 73, 74));

        guardarTxt.setBackground(new java.awt.Color(153, 153, 153));
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

        javax.swing.GroupLayout guardarLayout = new javax.swing.GroupLayout(guardar);
        guardar.setLayout(guardarLayout);
        guardarLayout.setHorizontalGroup(
            guardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guardarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(guardarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        guardarLayout.setVerticalGroup(
            guardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guardarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(guardarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 190, -1, 30));

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 530, 210));

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
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        javax.swing.GroupLayout invisibleLayout = new javax.swing.GroupLayout(invisible);
        invisible.setLayout(invisibleLayout);
        invisibleLayout.setHorizontalGroup(
            invisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        invisibleLayout.setVerticalGroup(
            invisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(invisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 800, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseEntered
        
    }//GEN-LAST:event_exitBtnMouseEntered

    private void exitBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseExited
        
    }//GEN-LAST:event_exitBtnMouseExited

    private void prevBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevBtnMouseEntered
        
    }//GEN-LAST:event_prevBtnMouseEntered

    private void prevBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevBtnMouseExited
        
    }//GEN-LAST:event_prevBtnMouseExited

    private void exitBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnTxtMouseEntered
        exitBtn.setVisible(true);
        exitBtnTxt.setForeground(Color.white);
    }//GEN-LAST:event_exitBtnTxtMouseEntered

    private void exitBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnTxtMouseExited
        exitBtn.setVisible(false);
        exitBtnTxt.setForeground(Color.black);
    }//GEN-LAST:event_exitBtnTxtMouseExited

    private void prevBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevBtnTxtMouseEntered
        prevBtn.setVisible(true);
        prevBtnTxt.setForeground(Color.white);
    }//GEN-LAST:event_prevBtnTxtMouseEntered

    private void prevBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevBtnTxtMouseExited
        prevBtn.setVisible(false);
        prevBtnTxt.setForeground(Color.black);
    }//GEN-LAST:event_prevBtnTxtMouseExited

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        xMouse=evt.getX();
        yMouse=evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x=evt.getXOnScreen();
        int y=evt.getYOnScreen();
        this.setLocation(x-xMouse,y-yMouse);
    }//GEN-LAST:event_headerMouseDragged

    private void exitBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnTxtMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitBtnTxtMouseClicked

    private void prevBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevBtnTxtMouseClicked
        menu2 menu=new menu2();
        this.setVisible(false);
        menu.setVisible(true);
        
    }//GEN-LAST:event_prevBtnTxtMouseClicked

    private void razonSocialTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_razonSocialTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_razonSocialTextFieldActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        guardar.setVisible(false);
        guardarTxt.setVisible(false);
        razonSocialTextField.setEditable(false);
        rucJTextField.setEditable(false);
        seleccionarDatos(jTable,razonSocialTextField,rucJTextField);
        deleteTxt.setVisible(true);
        actualizarTxt.setVisible(true);
    }//GEN-LAST:event_jTableMouseClicked

    private void bgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMouseClicked
        razonSocialTextField.setText("");
        rucJTextField.setText("");
        razonSocialTextField.setEditable(false);
        rucJTextField.setEditable(false);
        guardar.setVisible(false);
        guardarTxt.setVisible(false);
        deleteTxt.setVisible(false);
        actualizarTxt.setVisible(false);
        actualizarBtnTxt.setVisible(false);
    }//GEN-LAST:event_bgMouseClicked

    private void addUserTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserTxtMouseEntered
        addUser.setVisible(true);
//        addUser.setVisible(true);//dfsdfsdfsdf
    }//GEN-LAST:event_addUserTxtMouseEntered

    private void addUserTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserTxtMouseExited
        addUser.setVisible(false);
        
    }//GEN-LAST:event_addUserTxtMouseExited

    private void addUserTxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserTxtMousePressed
        
    }//GEN-LAST:event_addUserTxtMousePressed

    private void guardarTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseEntered
        guardarTxt.setForeground(Color.black);
        guardar.setBackground(new Color(51,255,0));//se pone en verde
        
    }//GEN-LAST:event_guardarTxtMouseEntered

    private void guardarTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseExited
        guardarTxt.setForeground(new Color(204,204,204));
        guardar.setBackground(new Color(69,73,74));//color gris original
//        guardarTxt.setForeground(new Color(187,187,187));
    }//GEN-LAST:event_guardarTxtMouseExited

    private void addUserTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserTxtMouseClicked
        guardar.setVisible(true);
        guardarTxt.setVisible(true);
        razonSocialTextField.setEditable(true);
        rucJTextField.setEditable(true);
        razonSocialTextField.setText("");
        rucJTextField.setText("");
        actualizarBtnTxt.setVisible(false);
    }//GEN-LAST:event_addUserTxtMouseClicked

    private void guardarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseClicked
        // acá se guarda el cliente
        String razonSocial=razonSocialTextField.getText().trim();
        String ruc=rucJTextField.getText().trim();
        if(razonSocial.isEmpty() && ruc.isEmpty()){
//            throw new IndexOutOfBoundsException("No se ha ingresado nigún valor");
        JOptionPane.showMessageDialog(new JFrame(), "No se han ingresado valores");
        }else{
            insertarCliente(razonSocial, ruc);
            //acá mostrar de nuevo el select
            mostrarClientesEnTabla(jTable);
        }
    }//GEN-LAST:event_guardarTxtMouseClicked

    private void actualizarTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarTxtMouseEntered
        actualizarPanel.setVisible(true);
    }//GEN-LAST:event_actualizarTxtMouseEntered

    private void actualizarTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarTxtMouseExited
        actualizarPanel.setVisible(false);
    }//GEN-LAST:event_actualizarTxtMouseExited

    private void deleteTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteTxtMouseEntered
        deletePanel.setVisible(true);
    }//GEN-LAST:event_deleteTxtMouseEntered

    private void deleteTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteTxtMouseExited
        deletePanel.setVisible(false);
    }//GEN-LAST:event_deleteTxtMouseExited

    private void actualizarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarTxtMouseClicked
    actualizarBtnTxt.setVisible(true);
    razonSocialTextField.requestFocus();
    razonSocialTextField.setEditable(true);
    rucJTextField.setEditable(true);
    razonSocialTextField.selectAll();
    }//GEN-LAST:event_actualizarTxtMouseClicked

    private void deleteTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteTxtMouseClicked
        elimnar(razonSocialTextField, rucJTextField);
        mostrarClientesEnTabla(jTable);
    }//GEN-LAST:event_deleteTxtMouseClicked

    private void actualizarBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarBtnTxtMouseEntered
        actualizarBtnPanel.setVisible(true);
    }//GEN-LAST:event_actualizarBtnTxtMouseEntered

    private void actualizarBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarBtnTxtMouseExited
        actualizarBtnPanel.setVisible(false);
    }//GEN-LAST:event_actualizarBtnTxtMouseExited

    private void actualizarBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarBtnTxtMouseClicked
        actualizar(razonSocialTextField, rucJTextField, jTable);
        mostrarClientesEnTabla(jTable);
        razonSocialTextField.setEditable(false);
        rucJTextField.setEditable(false);
        actualizarBtnTxt.setVisible(false);
        actualizarBtnPanel.setVisible(false);
    }//GEN-LAST:event_actualizarBtnTxtMouseClicked

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
            java.util.logging.Logger.getLogger(interfazClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interfazClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interfazClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interfazClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new interfazClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualizarBtnPanel;
    private javax.swing.JLabel actualizarBtnTxt;
    private javax.swing.JPanel actualizarPanel;
    private javax.swing.JLabel actualizarTxt;
    private javax.swing.JPanel addUser;
    private javax.swing.JLabel addUserTxt;
    private javax.swing.JLabel bg;
    private javax.swing.JPanel deletePanel;
    private javax.swing.JLabel deleteTxt;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitBtnTxt;
    private javax.swing.JPanel guardar;
    private javax.swing.JLabel guardarTxt;
    private javax.swing.JPanel header;
    private javax.swing.JPanel invisible;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable;
    private javax.swing.JPanel prevBtn;
    private javax.swing.JLabel prevBtnTxt;
    private javax.swing.JTextField razonSocialTextField;
    private javax.swing.JLabel razonSocialTxt;
    private javax.swing.JTextField rucJTextField;
    private javax.swing.JLabel rucTxt;
    // End of variables declaration//GEN-END:variables
}
