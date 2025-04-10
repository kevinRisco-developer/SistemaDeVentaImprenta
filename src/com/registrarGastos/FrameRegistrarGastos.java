/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.registrarGastos;

import com.bdconexion.conexion;
import com.menu.menu2;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ADM
 */
public class FrameRegistrarGastos extends javax.swing.JFrame {

    int xMouse,yMouse;
    labelXTwo label=new labelXTwo();
        
    public FrameRegistrarGastos() {
        initComponents();
        jTable1.setDefaultEditor(Object.class, null);
        mostrarCombo(combo);
        formatoTabla(jTable1);
        mostrarCotizacionTabla(jTable1, combo);
        header.setBackground(new Color(0,0,0,0));
        exitPanel.setVisible(false);
        prevPanel.setVisible(false);
        label.cambiarColorX(jTable2, jTable1, combo);
        ajustarAnchoColumnas(jTable2);
        addTxt.setVisible(false);
        addPanel.setVisible(false);
        guardarTxt.setVisible(false);
        guardarPanel.setVisible(false);
        
        
        jTable2.getModel().addTableModelListener(e->{
        if(e.getType()==TableModelEvent.UPDATE){
            aparecerYDesaparecerGuardar(guardarTxt, guardarPanel, jTable2);
        }
        });
    }
    

    public static void actualizar(JTable tablaGasto, String idGastoDeVentas) {
        // Forzar actualización de valores editados
        if (tablaGasto.isEditing()) {
            tablaGasto.getCellEditor().stopCellEditing();
        }

        int filaSeleccionada = tablaGasto.getSelectedRow();
        if (filaSeleccionada == -1) {
            return; // No hay fila seleccionada
        }

        String descripcionGasto = tablaGasto.getValueAt(filaSeleccionada, 0).toString();
        Object valor = tablaGasto.getValueAt(filaSeleccionada, 1);
        double precio;

        try {
            precio = Double.parseDouble(valor.toString().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido en la columna de precio.");
            return;
        }

        System.out.println("---");
        System.out.println(idGastoDeVentas + "\n" + descripcionGasto + "\n" + precio);
        System.out.println("---");

        String sql = "UPDATE gastodeventas SET descripcionGasto=?, costo=? WHERE idGastoDeVentas=?;";

        try (Connection con = conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, descripcionGasto);
            pst.setDouble(2, precio);
            pst.setString(3, idGastoDeVentas);
            pst.executeUpdate();
            System.out.println("Datos actualizados correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void insertarGasto(JTable tablaGasto, JTable tablaCotizacion, JComboBox combo){
        String arreglo[]=retornarIdCotizacionYNroItemTabla(tablaCotizacion, combo);
        Object valor = tablaGasto.getValueAt(tablaGasto.getSelectedRow(), 1);
        double precio;

        if (valor instanceof Number) {
            precio = ((Number) valor).doubleValue();
        } else {
            precio = Double.parseDouble(valor.toString().trim());
        }
//        System.out.println("---");
//        System.out.println(precio);
//        System.out.println(arreglo[0]);
//        System.out.println(arreglo[1]);
//        System.out.println("---");
        String sql="insert into gastodeventas (descripcionGasto, costo, idCotizacion,nroItem) values(?,?,?,?);";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, tablaGasto.getValueAt(tablaGasto.getSelectedRow(), 0).toString());
            pst.setDouble(2,precio);
            pst.setString(3, arreglo[0]);
            pst.setString(4, arreglo[1]);
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void aparecerYDesaparecerGuardar(JLabel label, JPanel panel, JTable tabla){
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        int lastRow=modelo.getRowCount()-1;
        String descripcion=String.valueOf(tabla.getValueAt(lastRow, 0)).trim();
        String precio=String.valueOf(tabla.getValueAt(lastRow, 1)).trim();
        if(descripcion.isEmpty() && precio.isEmpty()){
            panel.setVisible(false);
            label.setVisible(false);
        }else{
            panel.setVisible(true);
            label.setVisible(true);
        }
    }
    private boolean ultimaFilaVacia(JTable tablaGasto){
        return tablaGasto.getRowCount()==0 || tablaGasto.getValueAt(tablaGasto.getRowCount()-1, 0).toString().trim().isEmpty()
                && tablaGasto.getValueAt(tablaGasto.getRowCount()-1, 1).toString().trim().isEmpty();
    }
    private void aniadirfila(JTable tablaGasto){
        DefaultTableModel modelo=(DefaultTableModel) tablaGasto.getModel();
            modelo.addRow(new Object[]{"",""});
    }
    private void ajustarAnchoColumnas(JTable tabla) {
        tabla.getColumnModel().getColumn(0).setPreferredWidth(300); // Primera columna
        tabla.getColumnModel().getColumn(1).setPreferredWidth(50); // Segunda columna
        tabla.getColumnModel().getColumn(2).setMaxWidth(15);  // Tercera columna (donde está la "X")
    }

    public String retornarIdGastoDeVentas(JTable tablaGasto, JTable tablaCotizacion, JComboBox combo){
        String arreglo[]=retornarIdCotizacionYNroItemTabla(tablaCotizacion, combo);
        String descripcion=tablaGasto.getValueAt(tablaGasto.getSelectedRow(), 0).toString();
        String sql="select * from gastoDeVentas where idCotizacion=? and nroItem=? and descripcionGasto=?;";
        String idGastoDeVentas="";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, arreglo[0]);
            pst.setString(2, arreglo[1]);
            pst.setString(3, descripcion);
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next())
                    idGastoDeVentas=rs.getString("idGastoDeVentas");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println(idGastoDeVentas);
       return idGastoDeVentas;
    }
    public void eliminarItemGasto(JTable tablaGasto, JTable tablaCotizacion, JComboBox combo){
        String idGastoDeVentas=retornarIdGastoDeVentas(tablaGasto, tablaCotizacion, combo);
        System.out.println(idGastoDeVentas);
        String sql="delete from gastodeventas where idGastoDeVentas=?;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, idGastoDeVentas);
            pst.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void limpiarTabla(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);//limpia la tabla
    }
    private void ajustarAlturaTabla(JTable tabla) {
        int rowCount = tabla.getRowCount();
        int rowHeight = tabla.getRowHeight();

        // Calcular la nueva altura total
        int nuevaAltura = rowCount * rowHeight + tabla.getTableHeader().getPreferredSize().height;

        // Ajustar la altura de la tabla
        tabla.setPreferredScrollableViewportSize(new Dimension(tabla.getWidth(), nuevaAltura));

        // Actualizar la UI
        tabla.revalidate();
        tabla.repaint();
    }

    private void formatoTabla(JTable tabla){
        tabla.getColumnModel().getColumn(0).setMaxWidth(35);  // "Item"
        tabla.getColumnModel().getColumn(1).setPreferredWidth(252); // "Descripcion"
        tabla.getColumnModel().getColumn(2).setMaxWidth(50);
        
    }
    public void mostrarCombo(JComboBox combo){
        String select="select * from venta v join cotizacion c on c.idCotizacion=v.idCotizacion;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(select);ResultSet rs=pst.executeQuery()){
            while(rs.next()){
                combo.addItem(rs.getString("nroCotizacion"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    private boolean esTablaVaci(JTable tabla){
        return tabla.getRowCount()==0;
    }
    public void mostrarCotizacionTabla(JTable tabla, JComboBox combo){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);//limpia la tabla
        String idCotizacion=retornarIdCotizacion(combo);
        String select="select * from itemcotizacion where idCotizacion=?;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(select)){
            pst.setString(1, idCotizacion);
            try(ResultSet rs=pst.executeQuery()){
                while(rs.next()){
                    Object[] fila={rs.getString("nroItem"),rs.getString("Descripcion"),rs.getString("precio")};
                    modelo.addRow(fila);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        ajustarAlturaTabla(tabla);
    }
    public void mostrarGastosDeVentas(JTable tablaCotizacion, JTable tablaGastos, JComboBox combo){
        DefaultTableModel modelo=(DefaultTableModel) tablaGastos.getModel();
        modelo.setRowCount(0);//limpia la tabla
//        ImageIcon img=new ImageIcon(getClass().getResource("/com/img/delete.png"));
        String arreglo[]=retornarIdCotizacionYNroItemTabla(tablaCotizacion, combo);
        System.out.println("pruebaaa");
        System.out.println(arreglo[0]);
        System.out.println(arreglo[1]);
        System.out.println("pruebaaa");
        String sql="select * from gastoDeVentas where idCotizacion=? and nroItem=?;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, arreglo[0]);
            pst.setString(2, arreglo[1]);
            try(ResultSet rs=pst.executeQuery()){
                while(rs.next()){
                    modelo.addRow(new Object[]{rs.getString("descripcionGasto"),rs.getString("costo")});
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }        
    public String retornarIdCotizacion(JComboBox combo){
        String idCotizacion="";
        String nroCotizacion=combo.getSelectedItem().toString();
        String select="select * from cotizacion where nroCotizacion=?;";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(select)){
            pst.setString(1, nroCotizacion);
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next()){
                    idCotizacion=rs.getString("idCotizacion");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return idCotizacion;
    }
    //que me retorne el idCotizacion y nroItem
    
    public String[] retornarIdCotizacionYNroItemTabla(JTable tabla, JComboBox combo){
        String nroCotizacion=combo.getSelectedItem().toString();
        String arreglo[]=new String[2];
        String nroItem=tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
        String descipcion=tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
        String sql="select * from itemcotizacion ic inner join venta v on v.idCotizacion=ic.idCotizacion join cotizacion c on c.idCotizacion=v.idCotizacion where ic.nroItem=? and ic.descripcion=? and c.nroCotizacion=?;";
        String idCotizacion="";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, nroItem);
            pst.setString(2, descipcion);
            pst.setString(3,nroCotizacion);
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next())
                    idCotizacion=rs.getString("idCotizacion");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        arreglo[0]=idCotizacion;
        arreglo[1]=nroItem;
        return arreglo;
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        combo = new javax.swing.JComboBox<>();
        prevTxt = new javax.swing.JLabel();
        prevPanel = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        exitPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        header = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        addTxt = new javax.swing.JLabel();
        addPanel = new javax.swing.JPanel();
        guardarTxt = new javax.swing.JLabel();
        guardarPanel = new javax.swing.JPanel();
        actualizarTxt = new javax.swing.JLabel();
        actualizarPanel = new javax.swing.JPanel();
        bg = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboMouseClicked(evt);
            }
        });
        combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboActionPerformed(evt);
            }
        });
        jPanel1.add(combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 100, -1));

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

        jPanel1.add(exitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        jScrollPane2.setToolTipText("");
        jScrollPane2.setName(""); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Descripción", "Precio", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable2MouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 610, 190));
        jScrollPane2.getAccessibleContext().setAccessibleName("");

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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Item", "Descripción", "Precio"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 760, 110));

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
        jPanel1.add(addTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 170, -1, -1));

        addPanel.setBackground(new java.awt.Color(204, 204, 204));
        addPanel.setForeground(new java.awt.Color(204, 204, 204));

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

        jPanel1.add(addPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 170, 70, 70));

        guardarTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
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
        jPanel1.add(guardarTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 100, 30));

        javax.swing.GroupLayout guardarPanelLayout = new javax.swing.GroupLayout(guardarPanel);
        guardarPanel.setLayout(guardarPanelLayout);
        guardarPanelLayout.setHorizontalGroup(
            guardarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        guardarPanelLayout.setVerticalGroup(
            guardarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(guardarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 100, 30));

        actualizarTxt.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        actualizarTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        actualizarTxt.setText("Actualizar");
        actualizarTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        actualizarTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarTxtMouseClicked(evt);
            }
        });
        jPanel1.add(actualizarTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 100, 30));

        javax.swing.GroupLayout actualizarPanelLayout = new javax.swing.GroupLayout(actualizarPanel);
        actualizarPanel.setLayout(actualizarPanelLayout);
        actualizarPanelLayout.setHorizontalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        actualizarPanelLayout.setVerticalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(actualizarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 100, 30));

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

    private void comboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboMouseClicked
        addPanel.setVisible(false);
        
    }//GEN-LAST:event_comboMouseClicked

    private void comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboActionPerformed
        mostrarCotizacionTabla(jTable1, combo);
        limpiarTabla(jTable2);
    }//GEN-LAST:event_comboActionPerformed

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

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        String idGastoDeVentas=retornarIdGastoDeVentas(jTable2, jTable1, combo);
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        mostrarGastosDeVentas(jTable1, jTable2, combo);
        addTxt.setVisible(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseEntered
        
    }//GEN-LAST:event_jTable2MouseEntered

    private void addTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTxtMouseEntered
        addPanel.setVisible(true);
    }//GEN-LAST:event_addTxtMouseEntered

    private void addTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTxtMouseExited
        addPanel.setVisible(false);
    }//GEN-LAST:event_addTxtMouseExited

    private void addTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTxtMouseClicked
        aniadirfila(jTable2);
    }//GEN-LAST:event_addTxtMouseClicked

    private void guardarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseClicked
        insertarGasto(jTable2, jTable1, combo);
        mostrarGastosDeVentas(jTable1, jTable2, combo);
    }//GEN-LAST:event_guardarTxtMouseClicked

    private void bgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMouseClicked
        addTxt.setVisible(false);
        guardarTxt.setVisible(false);
        guardarPanel.setVisible(false);
        actualizarTxt.setVisible(false);
        actualizarPanel.setVisible(false);
    }//GEN-LAST:event_bgMouseClicked

    private void guardarTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseEntered
        guardarPanel.setVisible(true);
    }//GEN-LAST:event_guardarTxtMouseEntered

    private void guardarTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarTxtMouseExited
        guardarPanel.setVisible(false);
    }//GEN-LAST:event_guardarTxtMouseExited

    private void actualizarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualizarTxtMouseClicked
        String idGastoDeVentas=retornarIdGastoDeVentas(jTable2, jTable1, combo);
        actualizar(jTable2, idGastoDeVentas);
        mostrarGastosDeVentas(jTable1, jTable2, combo);
    }//GEN-LAST:event_actualizarTxtMouseClicked

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
            java.util.logging.Logger.getLogger(FrameRegistrarGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameRegistrarGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameRegistrarGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameRegistrarGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameRegistrarGastos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualizarPanel;
    private javax.swing.JLabel actualizarTxt;
    private javax.swing.JPanel addPanel;
    private javax.swing.JLabel addTxt;
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JPanel exitPanel;
    private javax.swing.JLabel exitTxt;
    private javax.swing.JPanel guardarPanel;
    private javax.swing.JLabel guardarTxt;
    private javax.swing.JPanel header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel prevPanel;
    private javax.swing.JLabel prevTxt;
    // End of variables declaration//GEN-END:variables
}
