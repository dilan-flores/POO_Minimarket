import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Admin_stock {

    Statement s;
    ResultSet rs;
    ResultSetMetaData rsmd;
    PreparedStatement ps;
    private JFormattedTextField textCODIGO;
    private JFormattedTextField textPRODUCTO;
    private JFormattedTextField textPRECIO;
    private JFormattedTextField textCANTIDAD;
    private JButton agregarMasButton;
    public JPanel Panel;
    private JButton agregarNuevoButton;
    private JButton buscarButton;
    private JFormattedTextField textCANTIDAD_A_AGREGAR;

    private JTable table;
    private JButton cerrarButton;
    DefaultTableModel modelo = new DefaultTableModel();
    boolean encontrado = false;
    String cod;
    JFormattedTextField actualizar_cantidad = new JFormattedTextField();

    public Admin_stock(){
        /*String[] titulo = new String[]{"CÓDIGO", "PRODUCTO", "CANTIDAD", "PRECIO"};
        modelo.setColumnIdentifiers(titulo);
        table.setModel(modelo);*/
        try{
            Connection conexion;
            conexion = getConection();
            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM stock ");

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

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion;
                    conexion = getConection();

                    cod = "\"" + textCODIGO.getText() + "\"";
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM stock WHERE cod_p =" + cod);

                    encontrado = false;
                    while (rs.next()) {
                        textPRODUCTO.setText(rs.getString(2));
                        textPRECIO.setText(rs.getString(3));
                        textCANTIDAD.setText(rs.getString(4));
                        encontrado = true;
                    }

                    if(!encontrado){
                        JOptionPane.showMessageDialog(null, "PRODUCTO NO ENCONTRADOS");
                    }
                    conexion.close();
                    rs.close();
                    s.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }); /*FIN BUSCAR PRODUCTO*/

        agregarMasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*Altualiza la cantidad de un producto en STOCK*/
                try{

                    Connection conexion;
                    conexion = getConection();

                    cod = "\"" + textCODIGO.getText() + "\"";

                    ps = conexion.prepareStatement("UPDATE stock SET cantidad_exist_p =? WHERE cod_p = " + cod);
                    actualizar_cantidad.setText(String.valueOf(Integer.parseInt(textCANTIDAD.getText())+Integer.parseInt(textCANTIDAD_A_AGREGAR.getText())));
                    ps.setString(1, actualizar_cantidad.getText());


                    int res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"PRODUCTO" + textCODIGO.getText() + " ACTUALIZADO");
                        modelo.setColumnCount(0);
                        modelo.setRowCount(0);
                    }else{
                        JOptionPane.showMessageDialog(null,"NO GUARDADO");
                    }
                    conexion.close();//importante!!!!


                    ps.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                try{
                    Connection conexion;
                    conexion = getConection();

                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM stock");

                    rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();


                    // Create JTable and set model
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
                    rs.close();
                    s.close();
                    conexion.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        agregarNuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {/*LLENAR DETALLE DE TRANSFERENCIA*/

                    Connection conexion;
                    conexion = getConection();

                    cod = "\"" + textCODIGO.getText() + "\"";
                    ps = conexion.prepareStatement("Insert into stock values (?,?,?,?)");
                    ps.setString(1, textCODIGO.getText());
                    ps.setString(2, textPRODUCTO.getText());
                    ps.setString(3, textPRECIO.getText());
                    ps.setString(4, textCANTIDAD_A_AGREGAR.getText());

                    int res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"CABECERA DE FACTURA");
                        modelo.setColumnCount(0);
                        modelo.setRowCount(0);
                    }else{
                        JOptionPane.showMessageDialog(null,"NO GUARDADO");
                    }

                    conexion.close();
                    ps.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try{
                    Connection conexion;
                    conexion = getConection();

                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM stock");

                    rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

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
        /*
        uso de combo box para guardar o comparar información de bases de datos, borrar todos los cmpos de un interfaz
         */
        JFrame frame=new JFrame("ADMINISTRAR STOCK");
        frame.setContentPane(new Admin_stock().Panel);
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
