import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Admin_cajeros {
    Statement s;
    ResultSet rs;
    ResultSetMetaData rsmd;
    PreparedStatement ps;
    int res;
    private JButton agregarButton;
    private JFormattedTextField textID_CAJERO;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textAPELLIDO;
    private JFormattedTextField textCELULAR;
    private JButton buscarButton;
    public JPanel Panel;
    private JFormattedTextField textDIRECCION;
    private JFormattedTextField textFEC_NAC;
    private JFormattedTextField textUSUARIO;
    private JFormattedTextField testCONTRA;
    private JButton cerrarButton;
    private JTable table;

    DefaultTableModel modelo = new DefaultTableModel();
    Boolean encontrado;

    public Admin_cajeros(){
        try{// Abre cajero
            Connection conexion;
            conexion = getConection();
            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM cajero ");

            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();


            // Crea una tabla y envía a modelo
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
                encontrado=false;
                try {// Abre cajerp
                    Connection conexion;
                    conexion = getConection();

                    String id = "\"" + textID_CAJERO.getText() + "\"";
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM cajero WHERE id_caj =" + id);

                    while (rs.next()) {
                        textNOMBRE.setText(rs.getString(2));
                        textAPELLIDO.setText(rs.getString(3));
                        textCELULAR.setText(rs.getString(4));
                        textDIRECCION.setText(rs.getString(5));
                        textFEC_NAC.setText(rs.getString(6));
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

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion;
                    conexion = getConection();

                    ps = conexion.prepareStatement("Insert into cajero values (?,?,?,?,?,?)");
                    ps.setString(1, textID_CAJERO.getText());
                    ps.setString(2, textNOMBRE.getText());
                    ps.setString(3, textAPELLIDO.getText());
                    ps.setString(4, textCELULAR.getText());
                    ps.setString(5, textDIRECCION.getText());
                    ps.setString(6, textFEC_NAC.getText());

                    res = ps.executeUpdate();
                    if(!(res >0)){
                        JOptionPane.showMessageDialog(null,"CAJERO NO GUADADO");
                    }

                    conexion.close();
                    ps.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {//Cargar datos en login_cajero
                    Connection conexion;
                    conexion = getConection();

                    ps = conexion.prepareStatement("Insert into login_cajero values (?,?,?)");
                    ps.setString(1, textID_CAJERO.getText());
                    ps.setString(2, textUSUARIO.getText());
                    ps.setString(3, testCONTRA.getText());

                    res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"CAJERO AGREGADO");
                        modelo.setColumnCount(0); // Se elimina la columna de tabla
                        modelo.setRowCount(0);// Se elimina la fila de tabla
                    }else{
                        JOptionPane.showMessageDialog(null,"CAJERO NO GUARDADO");
                    }
                    conexion.close();
                    ps.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try{ /*CARGAR NUEVAMENTE LOS DATOS DE CAJEROS*/
                    Connection conexion;
                    conexion = getConection();
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM cajero ");

                    rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    // tabla envía a modelo
                    modelo = (DefaultTableModel) table.getModel();

                    // Agregar columnas en modelo
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
    /*
    public static void main(String[] args) {

        //uso de combo box para guardar o comparar información de bases de datos, borrar todos los cmpos de un interfaz

        JFrame frame=new JFrame("ADMINISTRAR CAJERO");
        frame.setContentPane(new Admin_cajeros().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    */
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
