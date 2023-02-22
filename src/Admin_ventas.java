import javax.swing.*;

public class Admin_ventas {
    private JFormattedTextField textCAJERO;
    private JButton buscarButton;
    private JTable table;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textAPELLIDO;
    private JButton revisarVentasButton;
    private JPanel Panel;
    private JFormattedTextField textCELULAR;
    private JButton cerrarButton;
    private JComboBox id_cajero;

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ingresar cajero");
        ventana.setBounds(0, 0, 550, 550);
        ventana.setContentPane(new Admin_ventas().Panel);
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
