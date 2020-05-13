/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.dao;


import java.io.*;
import javax.swing.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ExportPDF{
 public ExportPDF(){
     
 }
 public String getFileName(String baseName){
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTimeInfo = dateformat.format(new Date());
        return baseName.concat(String.format("_%s.pdf",dateTimeInfo)); 
    }
 public void exportPdfData(JTable table){
     String path = getFileName("operation".concat("_Export"));
     try {
        
         DefaultTableModel mo = (DefaultTableModel) table.getModel();
           String col[] = {mo.getColumnName(0),mo.getColumnName(1),mo.getColumnName(2),mo.getColumnName(3),mo.getColumnName(4),mo.getColumnName(5)};
        
        Document document=new Document();
        PdfWriter.getInstance(document,new FileOutputStream(path));
        document.open();
        document.addTitle("Informations from operations made");
        
        PdfPTable tab=new PdfPTable(col.length);
        tab.setWidthPercentage(100);
        for(int i=0;i<col.length;i++){
            tab.addCell(col[i]);
        }
         tab.completeRow();
        for(int i = 0;i<mo.getRowCount();i++){
            for(int j = 0;j< mo.getColumnCount();j++){
                tab.addCell(mo.getValueAt(i, j).toString());
            }
            tab.completeRow();
        }
        document.add(tab);
        document.close();
     } catch (DocumentException | FileNotFoundException ex) {
         Logger.getLogger(ExportPDF.class.getName()).log(Level.SEVERE, null, ex);
     }
 }
}