package com.cotizacion;

import com.bdconexion.conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;


public class clasePdf {
    public boolean insert(Object[] arrHead){
        String sql="insert into cotizacion (fecha,idClientes) values (?,?);";
        System.out.println("prueba1: "+arrHead[3].toString());
        System.out.println("prueba2: "+arrHead[0].toString());
        try(Connection con=conexion.conectar(); PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1, arrHead[3].toString());
            pst.setString(2, arrHead[0].toString());
            pst.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }       
    }
    public String returnIdCotizacion(){
        String sql="select * from cotizacion order by nroCotizacion desc limit 1;";
        String idCotizacion="";
        try(Connection con=conexion.conectar();PreparedStatement pst=con.prepareStatement(sql);
                ResultSet rs=pst.executeQuery()){
            if(rs.next()){
                idCotizacion=rs.getString("idCotizacion");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return idCotizacion;
    }
    
    public boolean insertItems(Object[] arrHead, Object[] arrItems){
        String idCoString=returnIdCotizacion();
        String sql="insert into itemcotizacion (idCotizacion,cantidad,descripcion,precio,idCategoria) values " +
"(?,?,?,?,?);";
        
            try(Connection con=conexion.conectar()){
                for(int i=0; i<arrItems.length;i+=5){
                    try(PreparedStatement pst=con.prepareStatement(sql)){
                        pst.setString(1, idCoString);
                        pst.setInt(2, Integer.parseInt((String) arrItems[i+1]));//cantidad
                        pst.setString(3, (String)arrItems[i+2]);//descripción
                        pst.setDouble(4, Double.parseDouble((String)arrItems[i+3]));//precio
                        pst.setString(5, (String)arrItems[i+4]);//idCategoria
                        if(pst.executeUpdate()<=0)
                            return false;
                        }catch(Exception e){
                            e.printStackTrace();
                            return false;
                        }
                    }   
                }catch(Exception e){
                e.printStackTrace();
                return false;
        }
            return true;
    }
    
    public void generarPdf(Object[] arrHead, Object[] arrItems){
        if(insert(arrHead)&&insertItems(arrHead, arrItems)){
        try {
            System.out.println("Generando PDF...");
            String namePdf="Cotz "+arrHead[5].toString();
            // Ruta del PDF membretado (colócalo en la carpeta del proyecto o usa una ruta absoluta)
            String hojaMembretadaPath = "acá va la dirección de la hoja membretada";
            String nuevoPdfPath = "acá va la nueva ruta donde se va a guardar el pdf";

            // Crear un lector para leer el PDF membretado
            PdfReader reader = new PdfReader(new FileInputStream(new File(hojaMembretadaPath)));

            // Crear un escritor para generar el nuevo PDF
            FileOutputStream archivo = new FileOutputStream(nuevoPdfPath);
            PdfStamper stamper = new PdfStamper(reader, archivo);

            // Obtener el contenido del PDF
            PdfContentByte canvas = stamper.getOverContent(1);

            // Definir una fuente
            Font tipoLetra = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            Font tipoLetraItems = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLACK);
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            canvas.setFontAndSize(baseFont, 12);

            int nroCtz=(int)arrHead[5];
            String txtDate=arrHead[3].toString();
            String nameCli=arrHead[1].toString();
            String ruc=(arrHead[2].toString().isEmpty()) ? "\n" : "\nRuc: "+arrHead[2].toString()+"\n";
            int payingDay=(int) arrHead[4];
            String txtPayingDay=(payingDay>0) ? "Método de Pago: credito "+payingDay+" días" : 
                    "Método de Pago: pago al contado";
            
            String allText="Cotización n° "+nroCtz+" - Fecha: "+txtDate+"\n\nAtención,\nSeñores/Empresa:\n"+nameCli
                    +ruc+txtPayingDay;
            
            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(50, 710, 490, 460); // Ajusta la caja de texto (x1, y1, x2, y2)
            ct.addElement(new Paragraph(allText, tipoLetra));
            ct.go();
           
            //Crear tabla de cotización
            PdfPTable table=new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{10f, 19f, 111f, 20f});
            String[] header={"Item", "Cantidad", "Descripción", "Precio"};
            for (String colName : header) {
                PdfPCell cell= new PdfPCell(new Phrase(colName,tipoLetra));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            
            bucleFor(arrItems, table);
            
            ColumnText columnText=new ColumnText(canvas);
            columnText.setSimpleColumn(50,570,550,220);
            columnText.addElement(table);
            columnText.go();
            
            float yPosTable=columnText.getYLine();
            String msgBankAccount="Ruc: 20613237284 (Soluciones Gráficas Risco SAC)\nCuenta BCP: 1917111639035 (Soluciones Gráficas Risco SAC)\nCCI: 00219100711163903553 (Soluciones Gráficas Risco SAC)";
            
            ColumnText msgColumnText=new ColumnText(canvas);
            msgColumnText.setSimpleColumn(50, yPosTable-20, 550, yPosTable-100);
            msgColumnText.addElement(new Paragraph(msgBankAccount, tipoLetra));
            msgColumnText.go();
            // Cerrar el stamper y guardar cambios
            stamper.close();
            reader.close();

            System.out.println("PDF generado exitosamente en: " + nuevoPdfPath);
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }
    
        public void bucleFor(Object[] arrItems, PdfPTable table){
            Font tipoLetraItems = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLACK);
            for(int i=0; i<arrItems.length; i+=5){
                PdfPCell cellItem=new PdfPCell(new Phrase((String) arrItems[i],tipoLetraItems));
                PdfPCell cellAmo=new PdfPCell(new Phrase((String) arrItems[i+1],tipoLetraItems));
                PdfPCell cellDes=new PdfPCell(new Phrase((String) arrItems[i+2],tipoLetraItems));
                String price="s/"+(String) arrItems[i+3];
                PdfPCell cellPr=new PdfPCell(new Phrase(price,tipoLetraItems));
                cellItem.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellAmo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDes.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPr.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellItem.setVerticalAlignment(Element.ALIGN_CENTER);
                cellAmo.setVerticalAlignment(Element.ALIGN_CENTER);
                cellDes.setVerticalAlignment(Element.ALIGN_CENTER);
                cellPr.setVerticalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellItem);
                table.addCell(cellAmo);
                table.addCell(cellDes);
                table.addCell(cellPr);
            }
        }
    
}
