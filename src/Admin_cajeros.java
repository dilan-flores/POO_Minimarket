import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Admin_cajeros {

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

    public Admin_cajeros(){
        String[] titulo = new String[]{"ID", "NOMBRES", "APRELLIDOS", "TELÉFONO", "DIRECCION", "FECHA NACIMIENTO", "USUARIO"};
        modelo.setColumnIdentifiers(titulo);
        table.setModel(modelo);
    }

    public static void main(String[] args) {
        /*
        uso de combo box para guardar o comparar información de bases de datos, borrar todos los cmpos de un interfaz
         */
        JFrame frame=new JFrame("ADMINISTRAR CAJERO");
        frame.setContentPane(new Admin_cajeros().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
