package com.registrarGastos;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class labelXTwo {

    public void cambiarColorX(JTable tablaGasto, JTable tablaCotizacion, JComboBox combo) {
        // Renderizador de celda personalizado para la columna 2 (tercera columna)
        tablaGasto.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel("X", SwingConstants.CENTER);
                label.setOpaque(true);
                label.setForeground(Color.BLACK);
                
                // Verifica si el mouse está sobre esta celda específica
                if (table.getClientProperty("hoverRow") != null && (int) table.getClientProperty("hoverRow") == row) {
                    label.setBackground(Color.RED);
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(tablaGasto.getBackground());
                    label.setForeground(Color.BLACK);
                }

                return label;
            }
        });

        // Listener para detectar el movimiento del mouse y actualizar la celda en la tercera columna
        tablaGasto.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int fila = tablaGasto.rowAtPoint(e.getPoint());
                int columna = tablaGasto.columnAtPoint(e.getPoint());

                // Si el mouse está sobre la tercera columna, actualizamos la celda
                if (columna == 2 && fila != -1) {
                    tablaGasto.putClientProperty("hoverRow", fila);
                } else {
                    tablaGasto.putClientProperty("hoverRow", -1);
                }

                // Forzamos la actualización de la tabla para reflejar los cambios
                tablaGasto.repaint();
            }
        });

        // Listener para eliminar la fila al hacer clic en la "X"
        tablaGasto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaGasto.rowAtPoint(e.getPoint());
                int columna = tablaGasto.columnAtPoint(e.getPoint());

                if (columna == 2 && fila != -1) {
                    int confirmar = javax.swing.JOptionPane.showConfirmDialog(null, "¿Eliminar este gasto?", "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
                        //acá tiene que eliminar
                        FrameRegistrarGastos frame=new FrameRegistrarGastos();
                        frame.eliminarItemGasto(tablaGasto, tablaCotizacion, combo);
                        frame.mostrarGastosDeVentas(tablaCotizacion, tablaGasto, combo);
                    }
                }
            }
        });
    }
}

