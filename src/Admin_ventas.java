import javax.swing.*;

public class Admin_ventas {
    private JFormattedTextField textCAJERO;
    private JButton buscarButton;
    private JTable table;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textAPELLIDO;
    private JButton revisarVentasButton;
    public JPanel Panel;
    private JFormattedTextField textCELULAR;
    private JButton cerrarButton;
    private JComboBox id_cajero;

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
}
