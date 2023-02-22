import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Login {
    private JFormattedTextField textUSUARIO;
    private JPasswordField textCONTRA;
    public JPanel PanelLogin;
    private JButton INGRESARButton;
    private JLabel Imagen;

    public Login(){
        Imagen.setIcon( new ImageIcon("img/perfil.jpg"));
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*boolean ingreso = false;*/
                String user = "DILAN";
                String contrasenia ="0012";
                String pass = new String(textCONTRA.getPassword());
                if(textUSUARIO.getText().equals(user) && pass.equals(contrasenia)){
                    /*ingreso = true;*/
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
}
