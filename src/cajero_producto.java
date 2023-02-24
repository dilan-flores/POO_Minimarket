import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cajero_producto {
    Statement s;
    ResultSet rs;
    PreparedStatement ps;
    public JPanel panel;
    private JButton eliminarPRODUCTO;
    private JButton guardarPRODUCTO;
    private JButton buscarPRODUCTO;
    private JFormattedTextField textPRECIO;
    private JFormattedTextField textCANTIDAD;
    private JButton agregarPRODUCTO;
    private JFormattedTextField textPRODUCTO;
    private JFormattedTextField textCEDULA;
    private JButton buscarCLIENTE;
    private JButton registrarCLIENTE;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textAPELLIDO;
    private JFormattedTextField textDIRECCION;
    private JFormattedTextField textCELULAR;
    private JFormattedTextField textCANTIDAD_A_COMPRAR;
    private JFormattedTextField textCODIGO;
    private JTable table;
    private JButton cerrarCajaButton;
    DefaultTableModel modelo = new DefaultTableModel();
    boolean encontrado; // verifica si se encontro el cliente

    public cajero_producto(){
        buscarCLIENTE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion;
                    conexion = getConection();

                    String id = textCEDULA.getText();
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM cliente WHERE ci_cl =" + id);

                    encontrado = false;
                    while (rs.next()) {
                            textNOMBRE.setText(rs.getString(2));
                            textAPELLIDO.setText(rs.getString(3));
                            textCELULAR.setText(rs.getString(4));
                            textDIRECCION.setText(rs.getString(5));
                            encontrado = true;

                    }

                    if(!encontrado){
                        JOptionPane.showMessageDialog(null, "DATOS NO ENCONTRADOS");
                    }
                    conexion.close();
                    rs.close();
                    s.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        String[] titulo = new String[]{"CÓDIGO", "PRODUCTO", "CANTIDAD", "PRECIO"};
        modelo.setColumnIdentifiers(titulo);
        table.setModel(modelo);

        agregarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregar();
            }
        });
        eliminarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
    }

    public void agregar(){
        modelo.addRow(new Object[]{1,"paracetamol","hoasjjjd","precio"});
    }

    public void eliminar() {
        int fila = table.getSelectedRow();
        modelo.removeRow(fila);
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("CAJERO_PRODUCTO");
        frame.setContentPane(new cajero_producto().panel);
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
