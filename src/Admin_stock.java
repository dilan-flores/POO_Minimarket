import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Admin_stock {
    private JFormattedTextField textCODIGO;
    private JFormattedTextField textPRODUCTO;
    private JFormattedTextField textPRECIO;
    private JFormattedTextField textCANTIDAD;
    private JButton agregarMásButton;
    public JPanel Panel;
    private JButton agregarNuevoButton;
    private JButton buscarButton;
    private JFormattedTextField textCANTIDAD_A_AGREGAR;

    private JTable table;
    private JButton cerrarButton;
    DefaultTableModel modelo = new DefaultTableModel();

    public Admin_stock(){
        String[] titulo = new String[]{"CÓDIGO", "PRODUCTO", "CANTIDAD", "PRECIO"};
        modelo.setColumnIdentifiers(titulo);
        table.setModel(modelo);
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
}
