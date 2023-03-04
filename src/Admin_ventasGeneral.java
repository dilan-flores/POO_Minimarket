import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Admin_ventasGeneral {
    private JTable table;
    public JPanel Panel;
    private JButton cerrarButton;
    Statement s;
    ResultSet rs;
    ResultSetMetaData rsmd;
    DefaultTableModel modelo = new DefaultTableModel();
    public Admin_ventasGeneral(){
        try {
            Connection conexion;
            conexion = getConection();

            s = conexion.createStatement();
            rs = s.executeQuery("SELECT FKnum_f AS Factura, (SELECT concat(nombres_caj,\" \",apellidos_caj) FROM cajero WHERE id_caj = ANY(SELECT FKid_caj FROM cab_trans WHERE num_f = FKnum_f)) AS Cajero,FKcod_p AS Código_Producto, cantidad_dt AS Cantidad,precio_dt AS Precio FROM det_trans");

            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            modelo = (DefaultTableModel) table.getModel();

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
            conexion.close();
            rs.close();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    /*
    public static void main(String[] args) {
        JFrame frame=new JFrame("ADMINISTRAR VENTAS GENERALES");
        frame.setContentPane(new Admin_ventasGeneral().Panel);
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
