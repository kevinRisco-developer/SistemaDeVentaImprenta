package com.cotizacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class labelX {
    public void formatXLabel(JTable firstTable){
        firstTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column){
                JLabel label=new JLabel("X",SwingConstants.CENTER);
                label.setOpaque(true);
                label.setForeground(Color.black);
                if(table.getClientProperty("hoverRow")!=null && (int)table.getClientProperty("hoverRow")==row){
                    label.setBackground(Color.red);
                    label.setForeground(Color.white);
                }else{
                    label.setBackground(firstTable.getBackground());
                    label.setForeground(Color.black);
                }
                return label;
            }
        });
        firstTable.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                int row=firstTable.rowAtPoint(e.getPoint());
                int col=firstTable.columnAtPoint(e.getPoint());
                if(col==5 && row!=-1)
                    firstTable.putClientProperty("hoverRow", row);
                else
                    firstTable.putClientProperty("hoverRow", -1);
                firstTable.repaint();
            }
        });
        firstTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(firstTable.getRowCount()==1 && firstTable.columnAtPoint(e.getPoint())==5){
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el único item", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                DefaultTableModel model=(DefaultTableModel) firstTable.getModel();
                int row=firstTable.rowAtPoint(e.getPoint());
                int col=firstTable.columnAtPoint(e.getPoint());
                if(col==5 && row != -1){
                    int confirm=javax.swing.JOptionPane.showConfirmDialog(null, "¿Eliminar este item?","Confirmar eliminación",javax.swing.JOptionPane.YES_NO_OPTION);
                    if(confirm==javax.swing.JOptionPane.YES_OPTION){
                        //acá se elimina
                        model.removeRow(row);
                    }
                }
                int rowCount=firstTable.getRowCount();
                if(rowCount==0)
                    return;
                for(int i=0;i<rowCount;i++){
                    int item=i+1;
                    firstTable.setValueAt(item, i, 0);
                }
                
            }
        }});
    }
}
