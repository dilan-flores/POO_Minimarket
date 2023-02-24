import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Login {
    Statement s;
    ResultSet rs;
    PreparedStatement ps;
    private JFormattedTextField textUSUARIO;
    private JPasswordField textCONTRA;
    public JPanel PanelLogin;
    private JButton INGRESARButton;
    private JLabel Imagen;
    private JComboBox comboBox;
    JFormattedTextField contrasenia = new JFormattedTextField();

    public Login(){
        Imagen.setIcon( new ImageIcon("img/perfil.jpg"));

        comboBox.removeAllItems();
        comboBox.addItem(" ");
        comboBox.addItem("Administrador");
        comboBox.addItem("Cajero");
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("Administrador" == comboBox.getSelectedItem()){
                    try{
                        Connection conexion;
                        conexion = getConection();

                        s = conexion.createStatement();
                        rs = s.executeQuery("SELECT * FROM login_admin");

                        contrasenia.setText(new String(textCONTRA.getPassword()));
                        boolean correcto = false;

                        while (rs.next()) {

                            if(textUSUARIO.getText().equals(rs.getString(2)) && contrasenia.getText().equals(rs.getString(3))){

                                    Admin admin= new Admin();
                                    admin.setName("MENU-ADMINISTRADOR");
                                    admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    admin.pack();
                                    admin.setBounds(0,0,1000, 800);
                                    admin.setLocationRelativeTo(null);
                                    admin.setVisible(true);

                                    correcto = true;

                            }
                        }
                        if(!correcto){
                            JOptionPane.showMessageDialog(null, "El usuario o contraseña son incorrectos");
                        }
                        conexion.close();
                        rs.close();
                        s.close();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
                if("Cajero" == comboBox.getSelectedItem()){
                    try{
                        Connection conexion;
                        conexion = getConection();

                        s = conexion.createStatement();
                        rs = s.executeQuery("SELECT * FROM login_cajero");

                        contrasenia.setText(new String(textCONTRA.getPassword()));
                        boolean correcto = false;

                        while (rs.next()) {

                            if(textUSUARIO.getText().equals(rs.getString(2)) && contrasenia.getText().equals(rs.getString(3))){

                                JFrame frame=new JFrame("CAJERO_PRODUCTO");
                                frame.setContentPane(new cajero_producto().panel);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.pack();
                                frame.setBounds(0,0,1000, 800);
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);

                                correcto = true;

                            }
                        }
                        if(!correcto){
                            JOptionPane.showMessageDialog(null, "El usuario o contraseña son incorrectos");
                        }
                        conexion.close();
                        rs.close();
                        s.close();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }

                /*boolean ingreso = false;
                String user = "DILAN";
                String contrasenia ="0012";
                String pass = new String(textCONTRA.getPassword());
                if(textUSUARIO.getText().equals(user) && pass.equals(contrasenia)){
                    ingreso = true;
                    JFrame frame=new JFrame("CAJERO_PRODUCTO");
                    frame.setContentPane(new cajero_producto().panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setBounds(0,0,1000, 800);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "El usuario o contraseña son incorrectos");
                }
                */

                /*
                if (ingreso) {
                    JOptionPane.showMessageDialog(null, "Correcto");
                    Menu menu = new Menu();
                    menu.a();
                    JOptionPane.showMessageDialog(null, "Bienvenido al sistema");
                    JFrame frame = new JFrame("PAGINA PRINCIPAL");
                    frame.setContentPane(new Menu().getJMenuBar());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setSize(700, 700);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
                 */
            }
        });
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
