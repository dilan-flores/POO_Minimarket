import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Transaccion {
    Statement s;
    ResultSet rs;
    ResultSetMetaData rsmd;
    public JPanel Panel;
    private JFormattedTextField textFECHA;
    private JFormattedTextField textFACTURA;
    private JFormattedTextField textCEDULA;
    private JFormattedTextField textAPELLIDO;
    private JFormattedTextField textDIRECCION;

    private JFormattedTextField textID_CAJERO;
    private JFormattedTextField textSUBTOTAL;
    private JFormattedTextField textIVA;
    private JFormattedTextField textDESCUENTO;
    private JFormattedTextField textTOTAL;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textCELULAR;
    private JTable table;
    private JButton cerrarButton;

    DefaultTableModel modelo = new DefaultTableModel();
    public Transaccion(){
        /*String[] titulo = new String[]{"CÓDIGO", "PRODUCTO", "CANTIDAD", "PRECIO"};
        modelo.setColumnIdentifiers(titulo);
        table.setModel(modelo);*/
        try{
            Connection conexion;
            conexion = getConection();

            textFACTURA.setText("002 001 123456790");
            String F = "\"" + textFACTURA.getText()+"\"";
            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM det_trans WHERE FKnum_f = " + F);

            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();


            // Create JTable and set model
            /*table = new JTable();*/
            modelo = (DefaultTableModel) table.getModel();

            // Add columns to table model
            for (int i = 2; i <= columnCount; i++) {
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
           textCEDULA.setText("1727906070");
           String ci = textCEDULA.getText();
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

        try {
            Connection conexion;
            conexion = getConection();
            textFACTURA.setText("002 001 123456790");

            String fac = "\"" + textFACTURA.getText() + "\"";
            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM cab_trans WHERE num_f =" + fac);

            while (rs.next()) {
                if (textFACTURA.getText().equals(rs.getString(1))) {
                    textFECHA.setText(rs.getString(2));
                    textID_CAJERO.setText(rs.getString(3));
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

    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("Transaccion");
        frame.setContentPane(new Transaccion().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Connection getConection()
    {
        Connection conexion = null;
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
