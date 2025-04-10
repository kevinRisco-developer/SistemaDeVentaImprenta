/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RegistrarVentaCotizacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ADM
 */
public class labelXDos {
    public void cambiarColorX(JTable tabla, JComboBox combo){
        tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            JLabel label=new JLabel("X",SwingConstants.CENTER);
            label.setOpaque(true);
            label.setForeground(Color.black);
            if(table.getClientProperty("hoverRow")!= null && (int) table.getClientProperty("hoverRow")==row){
                label.setBackground(Color.red);
                label.setForeground(Color.white);
            }else{
                label.setBackground(tabla.getBackground());
                label.setForeground(Color.black);
            }
            return label;
        }
    });
        tabla.addMouseMotionListener(new MouseAdapter(){
        @Override
        public void mouseMoved(MouseEvent e){
            int fila=tabla.rowAtPoint(e.getPoint());
            int col=tabla.columnAtPoint(e.getPoint());
            if(col==1)
                tabla.putClientProperty("hoverRow", fila);
            else
                tabla.putClientProperty("hoverRow", -1);
            tabla.repaint();
        }
    });
        tabla.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                int fila=tabla.rowAtPoint(e.getPoint());
                int col=tabla.columnAtPoint(e.getPoint());
                if(col==1){
                    int confirmar=javax.swing.JOptionPane.showConfirmDialog(null, "¿Eliminar este gasto?", "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);
                    if(confirmar==javax.swing.JOptionPane.YES_OPTION){
                        FrameVentaCotizacion frame=new FrameVentaCotizacion();
                        frame.eliminar(tabla, combo);
                        frame.mostrarEnTabla(tabla);
                    }
                }
            }
        });
    }
    
}
