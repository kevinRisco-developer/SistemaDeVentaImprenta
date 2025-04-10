package com.interfazPrincipal;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class NewClass {
    //1ero Crear, Tamaño, Color, Hoover
    private JLabel createXTxt(){
        JLabel xTxt=new JLabel("X",SwingConstants.CENTER);
        xTxt.setForeground(Color.black);
        return xTxt;
    }
    private JPanel createXPanel(){
        JPanel xPanel=new JPanel();
        xPanel.setBackground(new Color(0,0,0,0));
        return xPanel;
    }
    private JLabel createPrevTxt(){
        JLabel xPrev=new JLabel("<",SwingConstants.CENTER);
        xPrev.setForeground(Color.black);
        return xPrev;
    }
    
    private JPanel createPrevPanel(){
        JPanel prevPanel=new JPanel();
        prevPanel.setBackground(new Color(0,0,0,0));
        return prevPanel;
    }
    private void sizeX(JLabel xTxt, JPanel xPanel){
        xTxt.setBounds(0, 0, 50, 40);
        xPanel.setBounds(0, 0, 50, 40);
    }
    private void sizePrev(JLabel prevTxt, JPanel prevPanel){
        prevTxt.setBounds(50, 0, 50, 40);
        prevPanel.setBounds(50, 0, 50, 40);
    }
    
    private boolean withPrev(boolean confirm){
        return confirm;
    }
    public void createUIFrame(JLabel bg, boolean confirmPrev){
        JLabel xTxt=createXTxt();
        JPanel xPanel=createXPanel();
        
        sizeX(xTxt, xPanel);
        xPanel.setLayout(null);
        xPanel.add(xTxt);
        bg.add(xPanel);
        
        hooverX(xTxt, xPanel);
        
        if(withPrev(confirmPrev)){
        JLabel prevTxt=createPrevTxt();
        JPanel prevPanel=createPrevPanel();
        sizePrev(prevTxt, prevPanel);
        prevPanel.add(xTxt);
        bg.add(xTxt);
        }
        
    }
    
    public void hooverX(JLabel xTxt, JPanel xPanel){
        xTxt.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                xTxt.setForeground(Color.white);
                xPanel.setBackground(new Color(255,0,0));
            }
            @Override
            public void mouseExited(MouseEvent e){
                xTxt.setForeground(Color.black);
                xPanel.setBackground(new Color(0,0,0,0));
            }
            @Override
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });
        
    }
}

