import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Transaccion {
    public JPanel Panel;
    private JFormattedTextField textFECHA;
    private JFormattedTextField textFACTURA;
    private JFormattedTextField textCEDULA;
    private JFormattedTextField textAPELLIDO;
    private JFormattedTextField textDIRECCION;

    private JFormattedTextField textID_CAJERO;
    private JFormattedTextField textSUBTOTAL;
    private JFormattedTextField textIVA;
    private JFormattedTextField textDESCUENTO;
    private JFormattedTextField textTOTAL;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textCELULAR;
    private JTable table;
    private JPanel Panel_cliente;
    private JButton cerrarButton;

    DefaultTableModel modelo = new DefaultTableModel();
    public Transaccion(){
        String[] titulo = new String[]{"CÓDIGO", "PRODUCTO", "CANTIDAD", "PRECIO"};
        modelo.setColumnIdentifiers(titulo);
        table.setModel(modelo);
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("Transaccion");
        frame.setContentPane(new Transaccion().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
