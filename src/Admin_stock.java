import javax.swing.*;

public class Admin_stock {
    private JTable table1;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JFormattedTextField formattedTextField3;
    private JFormattedTextField formattedTextField4;
    private JButton agregarButton1;
    private JPanel Panel;

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ingresar cajero");
        ventana.setBounds(0, 0, 550, 550);
        ventana.setContentPane(new Admin_stock().Panel);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*ventana.add(Panel);*/
        /*aplicacion.setSize(0,0,500,500);*/
        ventana.setVisible(true);
        /*
        uso de combo box para guardar o comparar información de bases de datos, borrar todos los cmpos de un interfaz
         */
    }
}
