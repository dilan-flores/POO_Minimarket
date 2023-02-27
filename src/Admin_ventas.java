import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin_ventas {
    Statement s;
    ResultSet rs;
    ResultSetMetaData rsmd;
    private JButton buscarButton;
    private JTable table;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textAPELLIDO;
    private JButton revisarVentasButton;
    public JPanel Panel;
    private JFormattedTextField textCELULAR;
    private JButton cerrarButton;
    private JComboBox id_cajero;
    DefaultTableModel modelo = new DefaultTableModel();
    Boolean encontrado = false;
    String id;


    public Admin_ventas(){

        try {/*Almacena en los JComboBox todos los datos de la BD*/
            Connection conexion;
            conexion = getConection();

            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM cajero ");

            id_cajero.removeAllItems();
            id_cajero.addItem(" ");
            while (rs.next()) {
                id_cajero.addItem(rs.getString(1));
            }
            conexion.close();
            rs.close();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encontrado=false;
                try {

                    Connection conexion;
                    conexion = getConection();

                    id = (String)id_cajero.getSelectedItem();
                    System.out.println(id);
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM cajero WHERE id_caj =" + id);


                    while (rs.next()) {
                        textNOMBRE.setText(rs.getString(2));
                        textAPELLIDO.setText(rs.getString(3));
                        textCELULAR.setText(rs.getString(4)); /*Captura los datos de cantidad*/
                        encontrado = true;
                    }

                    if(!encontrado){
                        JOptionPane.showMessageDialog(null, "CAJERO NO ENCONTRADOS");
                    }
                    conexion.close();
                    rs.close();
                    s.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        revisarVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setColumnCount(0);
                modelo.setRowCount(0);
                try{
                    Connection conexion;
                    conexion = getConection();

                    id = (String)id_cajero.getSelectedItem();
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM det_trans WHERE FKnum_f = (Select num_f from cab_trans where FKid_caj =" +id+ ")");

                    rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    modelo = (DefaultTableModel) table.getModel();

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
            }
        });
        cerrarButton.addActionListener(new ActionListener() {/*CERRAR Y PASAR A VENTANA ANTERIOR*/
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin admin= new Admin();
                admin.setName("MENU-ADMINISTRADOR");
                admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                admin.pack();
                admin.setBounds(0,0,1000, 800);
                admin.setLocationRelativeTo(null);
                admin.setVisible(true);
            }
        }); /*CERRAR Y PASAR A VENTANA ANTERIOR*/
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("ADMINISTRAR VENTAS INDIVIDUALES");
        frame.setContentPane(new Admin_ventas().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /*ventana.add(Panel);*/
        /*aplicacion.setSize(0,0,500,500);*/
        /*
        uso de combo box para guardar o comparar información de bases de datos, borrar todos los cmpos de un interfaz
         */
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
