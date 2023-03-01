import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Login{
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
    JFormattedTextField id_cajero = new JFormattedTextField();
    JFormattedTextField nombre_caj = new JFormattedTextField();
    JFormattedTextField apellido_caj = new JFormattedTextField();
    JFormattedTextField transaccion = new JFormattedTextField();
    Boolean correcto;

    public Login(){
        //datos d = new datos();

        Imagen.setIcon( new ImageIcon("img/perfil.jpg"));

        comboBox.removeAllItems();
        comboBox.addItem(" ");
        comboBox.addItem("Administrador");
        comboBox.addItem("Cajero");
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                correcto = false;
                if("Administrador" == comboBox.getSelectedItem()){
                    try{
                        Connection conexion;
                        conexion = getConection();

                        s = conexion.createStatement();
                        rs = s.executeQuery("SELECT * FROM login_admin");

                        contrasenia.setText(new String(textCONTRA.getPassword()));

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

                        while (rs.next()) {

                            if(textUSUARIO.getText().equals(rs.getString(2)) && contrasenia.getText().equals(rs.getString(3))){
                                id_cajero.setText(rs.getString(1));
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

                    if(correcto){
                        try{ /*Abre cajero*/
                            Connection conexion;
                            conexion = getConection();

                            s = conexion.createStatement();
                            rs = s.executeQuery("SELECT nombres_caj, apellidos_caj FROM cajero WHERE id_caj =" + id_cajero.getText());

                            while (rs.next()) {
                                nombre_caj.setText(rs.getString(1));
                                apellido_caj.setText(rs.getString(2));
                            }
                            conexion.close();
                            rs.close();
                            s.close();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }

                        try { /*CABECERA DE TRANSACCIÓN inicializada*/

                            Connection conexion;
                            conexion = getConection();

                            s = conexion.createStatement();
                            rs = s.executeQuery("SELECT num_f FROM cab_trans ORDER by num_f DESC LIMIT 1");

                            //encontrado = false;
                            while (rs.next()) {
                                transaccion.setText(rs.getString(1));
                                //encontrado = true;
                            }
                            String t = String.valueOf((Integer.parseUnsignedInt(transaccion.getText()) + 1));
                            transaccion.setText(t);

                            ps = conexion.prepareStatement("Insert into cab_trans (num_f ,FKid_caj) values (?,?)");
                            ps.setString(1, transaccion.getText());
                            ps.setString(2, id_cajero.getText());
                            //ps = conexion.prepareStatement("update cab_trans set fecha_f = (DATE(NOW())) where num_f =" + n_factura);
                            System.out.println("cab_trans: " + ps);

                            int res = ps.executeUpdate();
                            if(res >0){
                                JOptionPane.showMessageDialog(null,"GUARDADO cab_inicial");
                            }else{
                                JOptionPane.showMessageDialog(null,"NO GUARDADO");
                            }

                            conexion.close();
                            rs.close();
                            s.close();
                            ps.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        /*
                        try{
                            Connection conexion;
                            conexion = getConection();

                            ps = conexion.prepareStatement("Insert into ingreso (Nombre_caj,Apellido_caj,transaccion) values (?,?,?);");
                            ps.setString(1, nombre_caj.getText());
                            ps.setString(2, apellido_caj.getText());
                            ps.setString(3, transaccion.getText());
                            System.out.println("ingreso: " + ps);
                            int res = ps.executeUpdate();
                            if(res >0){
                                JOptionPane.showMessageDialog(null,"GUARDADO ingreso");
                            }else{
                                JOptionPane.showMessageDialog(null,"NO GUARDADO ingreso");
                            }

                            conexion.close();
                            ps.close();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        */

                        JFrame frame=new JFrame("CAJERO_PRODUCTO");
                        frame.setContentPane(new cajero_producto().panel);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setBounds(0,0,1000, 800);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }/*AQUI HABÍA UN ;*/
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
