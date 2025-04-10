/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.cotizacion;


/**
 *
 * @author ADM
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        clasePdf pdf=new clasePdf();
        Object[] arrHead={"CL001","kevin risco","48652659","2025-03-23",10,200,"IC010"};
        Object[] arrItems={};
        pdf.generarPdf(arrHead,arrItems);
//    JOptionPane.showMessageDialog(null, "Ocurrió un error", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}
