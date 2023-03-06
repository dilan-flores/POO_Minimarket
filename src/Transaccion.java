import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.*;

//import java.awt.Font;

//Para el pdf
import com.itextpdf.text.*;
//import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class Transaccion {
    Statement s;
    ResultSet rs;
    PreparedStatement ps;
    ResultSetMetaData rsmd;
    public JPanel Panel;
    private JFormattedTextField textFECHA;
    private JFormattedTextField textFACTURA;
    private JFormattedTextField textCEDULA;
    private JFormattedTextField textAPELLIDO;
    private JFormattedTextField textDIRECCION;

    private JFormattedTextField textCAJERO;
    private JFormattedTextField textSUBTOTAL;
    private JFormattedTextField textIVA;
    private JFormattedTextField textDESCUENTO;
    private JFormattedTextField textTOTAL;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textCELULAR;
    private JTable table;
    private JButton cerrarButton;
    private JButton PDFButton;
    String Fac;// Obtiene el número de de factura con una sintáxis correcta
    String ci;// Obtiene la cédula del cliente con una sintáxis correcta
    DefaultTableModel modelo = new DefaultTableModel();
    JFormattedTextField id_cajero = new JFormattedTextField();
    public Transaccion(){

        try{//Cabecera de transacción
            Connection conexion;
            conexion = getConection();

            s = conexion.createStatement();
            rs = s.executeQuery("Select * from cab_trans ORDER by num_f DESC LIMIT 1");

            while (rs.next()) {
                textFACTURA.setText(rs.getString(1));
                textFECHA.setText(rs.getString(2));
                id_cajero.setText(rs.getString(3));
                textSUBTOTAL.setText(rs.getString(4));
                textIVA.setText(rs.getString(5));
                textDESCUENTO.setText(rs.getString(6));
                textTOTAL.setText(rs.getString(7));
                textCEDULA.setText(rs.getString(8));
            }

            rs = s.executeQuery("SELECT nombres_caj, apellidos_caj FROM cajero WHERE id_caj =" + id_cajero.getText());

            while (rs.next()) {
                textCAJERO.setText(rs.getString(1) + " " + rs.getString(2));
            }

            conexion.close();
            rs.close();
            s.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }


        try{
            Connection conexion;
            conexion = getConection();

            Fac = "\"" + textFACTURA.getText()+"\"";
            s = conexion.createStatement();
            rs = s.executeQuery("SELECT (Select nombre_p from stock where cod_p = FKcod_p ) as descripcion, cantidad_dt, precio_dt FROM det_trans WHERE FKnum_f = " + Fac);

            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();


            // Create JTable and set model
            /*table = new JTable();*/
            modelo = (DefaultTableModel) table.getModel();

            // Add columns to table model
            for (int i = 1; i <= columnCount; i++) {
                modelo.addColumn(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                modelo.addRow(row);
            }
            rs.close();
            s.close();
            conexion.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
           Connection conexion;
           conexion = getConection();

           ci = textCEDULA.getText();
           s = conexion.createStatement();
           rs = s.executeQuery("SELECT * FROM cliente WHERE ci_cl =" + ci);

           while (rs.next()) {
                if (textCEDULA.getText().equals(rs.getString(1))) {
                    textNOMBRE.setText(rs.getString(2));
                    textAPELLIDO.setText(rs.getString(3));
                    textCELULAR.setText(rs.getString(4));
                    textDIRECCION.setText(rs.getString(5));
                } else {
                    JOptionPane.showMessageDialog(null, "DATOS NO ENCONTRADOS");
                }
           }
           conexion.close();
           rs.close();
           s.close();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        /*
        try {
            Connection conexion;
            conexion = getConection();

            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM cab_trans WHERE num_f =" + Fac);

            while (rs.next()) {
                if (textFACTURA.getText().equals(rs.getString(1))) {
                    textFECHA.setText(rs.getString(2));
                    textCAJERO.setText(rs.getString(3));
                    textSUBTOTAL.setText(rs.getString(4));
                    textIVA.setText(rs.getString(5));
                    textDESCUENTO.setText(rs.getString(6));
                    textTOTAL.setText(rs.getString(7));
                } else {
                    JOptionPane.showMessageDialog(null, "DATOS NO ENCONTRADOS");
                }
            }
            conexion.close();
            rs.close();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         */

        cerrarButton.addActionListener(new ActionListener() {/*CERRAR Y PASAR A VENTANA ANTERIOR*/
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame=new JFrame("CAJERO_PRODUCTO");
                frame.setContentPane(new cajero_producto().panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setBounds(0,0,1000, 800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }); /*CERRAR Y PASAR A VENTANA ANTERIOR*/

        PDFButton.addActionListener(new ActionListener() {/*Generar un pdf con la compra realizada*/
            @Override
            public void actionPerformed(ActionEvent e) {
                Document documento = new Document();
                try{
                    String ruta = System.getProperty("user.home");
                    PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Documents/Factura.pdf"));
                    documento.open();

                    // Fuente 1
                    Font f1 = FontFactory.getFont(FontFactory.HELVETICA);
                    f1.setSize(20);
                    f1.setColor(CMYKColor.MAGENTA);

                    //Fuente 2
                    Font f2 = FontFactory.getFont(FontFactory.HELVETICA);
                    f2.setSize(12);
                    f2.setColor(CMYKColor.BLUE);

                    //Línea hoorizontal
                    LineSeparator linea = new LineSeparator();
                    linea.setOffset(-8.0f);
                    linea.setAlignment(Element.ALIGN_LEFT);
                    linea.setPercentage(100.0f);

                    documento.add(new Paragraph("                           FACTURA DE SERVICIO\n\n", f1));

                    documento.add(new Paragraph("     DON JUSTO                            "+"                                                      FACTURA: " + textFACTURA.getText() + "\n"));
                    documento.add(new Paragraph("AV. 6 DE DICIEMBRE      "+"                                                                  FECHA: " + textFECHA.getText() + "\n\n"));
                    documento.add(new Paragraph("CAJERO: "+ textCAJERO.getText()));
                    // Agrega una línea

                    documento.add(linea);

                    documento.add(new Paragraph("\nCLIENTE:      " + textNOMBRE.getText() + " " + textAPELLIDO.getText() + "\n"));
                    documento.add(new Paragraph("DIRECCIÓN: " + textDIRECCION.getText() + "\n"));
                    documento.add(new Paragraph("CI / RUC:       " + textCEDULA.getText() + "\n\n"));

                    // Agrega una línea
                    documento.add(linea);
                    documento.add(new Paragraph("\n\n\n"));

                    // Creación de una tabla de 3 columnas
                    PdfPTable tabla = new PdfPTable(3);
                    tabla.setWidthPercentage(100.0f);
                    tabla.setWidths(new float[] {2.0f,2.0f,2.0f});
                    tabla.setSpacingBefore(10);

                    // Para implementar color a la cabecera de tabla
                    PdfPCell cell = new PdfPCell();
                    cell.setBackgroundColor(CMYKColor.CYAN);
                    cell.setPadding(5);

                    cell.setPhrase(new Phrase("Descripción"));
                    tabla.addCell(cell);

                    cell.setPhrase(new Phrase("Cantidad"));
                    tabla.addCell(cell);

                    cell.setPhrase(new Phrase("Precio"));
                    tabla.addCell(cell);


                    try{
                        Connection conexion;
                        conexion = getConection();

                        ps = conexion.prepareStatement("SELECT (Select nombre_p from stock where cod_p = FKcod_p ) as descripcion, cantidad_dt, precio_dt FROM det_trans WHERE FKnum_f = " + Fac);
                        rs = ps.executeQuery();

                        if(rs.next()){
                            do{
                                tabla.addCell(rs.getString(1));
                                tabla.addCell(rs.getString(2));
                                tabla.addCell(rs.getString(3));
                            }while(rs.next());
                            documento.add(tabla);
                        }
                        conexion.close();
                        rs.close();
                        ps.close();
                        documento.add(new Paragraph("\n\n"));
                        documento.add(new Paragraph("-----------------------------------------------------------------------------------------------      SUBTOTAL:    " + textSUBTOTAL.getText() + "\n",f2));
                        documento.add(new Paragraph("-----------------------------------------------------------------------------------------------      IVA:                 " + textIVA.getText() + "\n",f2));
                        documento.add(new Paragraph("-----------------------------------------------------------------------------------------------      DESCUENTO: " + textDESCUENTO.getText() +"\n",f2));
                        documento.add(new Paragraph("-----------------------------------------------------------------------------------------------      TOTAL:            " + textTOTAL.getText() + "\n",f2));

                    }catch(Exception ex){
                    }
                    documento.close();
                    JOptionPane.showMessageDialog(null, "FACTURA CREADA");
                }catch(Exception ex){
                }

            }
        });

    }
    /*
    public static void main(String[] args) {
        JFrame frame=new JFrame("Transaccion");
        frame.setContentPane(new Transaccion().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
     */

    public static Connection getConection()
    {
        Connection conexion;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/minimarket", "root", "3001a"
            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }
}
