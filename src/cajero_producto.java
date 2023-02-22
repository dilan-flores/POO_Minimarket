import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cajero_producto {
    public JPanel panel;

    private JButton eliminarPRODUCTO;
    private JButton guardarPRODUCTO;
    private JButton buscarPRODUCTO;
    private JFormattedTextField textPRECIO;
    private JFormattedTextField formattedTextField3;
    private JButton agregarPRODUCTO;
    private JFormattedTextField textPRODUCTO;
    private JFormattedTextField textCEDULA;
    private JButton buscarCLIENTE;
    private JButton registrarCLIENTE;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textAPELLIDO;
    private JFormattedTextField textDIRECCION;
    private JFormattedTextField textCELULAR;
    private JFormattedTextField formattedTextField6;
    private JFormattedTextField textCODIGO;
    private JTable table;
    DefaultTableModel modelo = new DefaultTableModel();

    public cajero_producto(){
        String[] titulo = new String[]{"ID", "PRODUCTO", "DESCRIPCION", "CANTIDAD"};
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
}
