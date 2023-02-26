import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class cajero_producto{
    Statement s;
    ResultSet rs;
    PreparedStatement ps;
    public JPanel panel;
    private JButton eliminarPRODUCTO;
    private JButton guardarPRODUCTO;
    private JButton buscarPRODUCTO;
    private JFormattedTextField textPRECIO;
    private JFormattedTextField textCANTIDAD;
    private JButton agregarPRODUCTO;
    private JFormattedTextField textPRODUCTO;
    public JFormattedTextField textCEDULA;
    private JButton buscarCLIENTE;
    private JButton registrarCLIENTE;
    private JFormattedTextField textNOMBRE;
    private JFormattedTextField textAPELLIDO;
    private JFormattedTextField textDIRECCION;
    private JFormattedTextField textCELULAR;
    private JFormattedTextField textCANTIDAD_A_COMPRAR;
    private JFormattedTextField textCODIGO;
    private JTable table;
    private JButton cerrarCajaButton;
    private JFormattedTextField textCAJERO;
    private JFormattedTextField textFECHA;
    DefaultTableModel modelo = new DefaultTableModel();
    boolean encontrado; // verifica si se encontro el cliente
    JFormattedTextField precio_total_producto = new JFormattedTextField(); /*Precio de un producto (cantidad * precio_unit)*/
    JFormattedTextField n_factura = new JFormattedTextField(); /* define el nùmero de factura*/
    JFormattedTextField actualizar_cantidad = new JFormattedTextField();/*actualizacón de cantidad de productos en stock*/
    JFormattedTextField subtotal_f = new JFormattedTextField();
    JFormattedTextField iva_f = new JFormattedTextField();
    JFormattedTextField descuento_f = new JFormattedTextField();
    JFormattedTextField total_f = new JFormattedTextField();

    String SUBTOTAL = String.valueOf(0.0);
    String IVA = String.valueOf(0.0);
    String TOTAL= String.valueOf(0.0);


    public cajero_producto(){
        DecimalFormat df= new DecimalFormat("#.00");
        textCANTIDAD_A_COMPRAR.setEnabled(false);
        textCAJERO.setText("50");
        descuento_f.setText("0.25");
        textFECHA.setText("2020-08-05");
        buscarCLIENTE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion;
                    conexion = getConection();

                    String id = "\"" + textCEDULA.getText() + "\"";

                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM cliente WHERE ci_cl = " + id);

                    encontrado = false;
                    while (rs.next()) {
                            textNOMBRE.setText(rs.getString(2));
                            textAPELLIDO.setText(rs.getString(3));
                            textCELULAR.setText(rs.getString(4));
                            textDIRECCION.setText(rs.getString(5));
                            encontrado = true;

                    }

                    if(!encontrado){
                        JOptionPane.showMessageDialog(null, "DATOS NO ENCONTRADOS");
                    }
                    conexion.close();
                    rs.close();
                    s.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }); /*FIN BUSCAR CLIENTE*/

        registrarCLIENTE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion;
                    conexion = getConection();

                    ps = conexion.prepareStatement("Insert into cliente values (?,?,?,?,?)");
                    ps.setString(1, textCEDULA.getText());
                    ps.setString(2, textNOMBRE.getText());
                    ps.setString(3, textAPELLIDO.getText());
                    ps.setString(4, textCELULAR.getText());
                    ps.setString(5, textDIRECCION.getText());

                    /*
                    cbCANTIDAD.setSelectedItem(cantidad.getText());
                    cbVENCIDA.setSelectedItem(vencido.getText());
                     */

                    int res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"CREACIÓN CON ÉXITO");
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
            }
        });/*FIN REGISTRAR CLIENTE*/

        buscarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion;
                    conexion = getConection();

                    String cod = "\"" + textCODIGO.getText() + "\"";
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM stock WHERE cod_p =" + cod);

                    encontrado = false;
                    while (rs.next()) {
                        textPRODUCTO.setText(rs.getString(2));
                        textPRECIO.setText(rs.getString(3));
                        textCANTIDAD.setText(rs.getString(4));
                        textCANTIDAD_A_COMPRAR.setEnabled(true);
                        encontrado = true;
                    }

                    if(!encontrado){
                        JOptionPane.showMessageDialog(null, "PRODUCTO NO ENCONTRADOS");
                        textCANTIDAD_A_COMPRAR.setEnabled(false);
                    }
                    conexion.close();
                    rs.close();
                    s.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }); /*FIN BUSCAR PRODUCTO*/

        String[] titulo = new String[]{"CÓDIGO", "PRODUCTO", "CANTIDAD", "PRECIO"};
        modelo.setColumnIdentifiers(titulo);
        table.setModel(modelo);

        /*
        try { CABECERA DE TRANSACCIÓN

            Connection conexion;
            conexion = getConection();

            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM cab_trans");

            encontrado = false;
            while (rs.next()) {
                n_factura.setText(rs.getString(1));
                encontrado = true;
            }

            textFECHA.setText("2020-08-05");
            n_factura.setText("002 001 123456790");
            ps = conexion.prepareStatement("Insert into cab_trans values (?,?,?,?,?,?,?)");
            subtotal_f.setText("12.5");
            iva_f.setText("0.8");

            total_f.setText("12.6");
            ps.setString(1, n_factura.getText());
            ps.setString(2, textFECHA.getText());
            ps.setString(3, textCAJERO.getText());
            ps.setString(4, subtotal_f.getText());
            ps.setString(5, iva_f.getText());
            ps.setString(6, descuento_f.getText());
            ps.setString(7, total_f.getText());
            ps = conexion.prepareStatement("update cab_trans set fecha_f = (DATE(NOW())) where num_f =" + n_factura);

            int res = ps.executeUpdate();
            if(res >0){
                JOptionPane.showMessageDialog(null,"GUARDADO can_inicial");
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
        */

        agregarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                precio_total_producto.setText(String.valueOf(Integer.parseInt(textCANTIDAD_A_COMPRAR.getText())*Float.parseFloat(textPRECIO.getText())));
                agregar();

                try {/*LLENAR DETALLE DE TRANSFERENCIA*/

                    Connection conexion;
                    conexion = getConection();

                    n_factura.setText("002 001 123456790");
                    ps = conexion.prepareStatement("Insert into det_trans values (?,?,?,?)");
                    ps.setString(1, n_factura.getText());
                    ps.setString(2, textCODIGO.getText());
                    ps.setString(3, textCANTIDAD_A_COMPRAR.getText());
                    ps.setString(4, precio_total_producto.getText());

                    SUBTOTAL = String.valueOf(Double.parseDouble(SUBTOTAL) + Double.parseDouble(precio_total_producto.getText()));

                    int res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"CABECERA DE FACTURA");
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


                /*Altualiza la cantidad de un producto en STOCK*/
                try{

                    Connection conexion;
                    conexion = getConection();

                    String cod2 = "\"" + textCODIGO.getText() + "\"";

                    ps = conexion.prepareStatement("UPDATE stock SET cod_p = ?,nombre_p = ?, precio_unit_p= ?, cantidad_exist_p =? WHERE cod_p = " + cod2);
                    ps.setString(1, textCODIGO.getText());
                    ps.setString(2, textPRODUCTO.getText());
                    ps.setString(3, textPRECIO.getText());
                    actualizar_cantidad.setText(String.valueOf(Integer.parseInt(textCANTIDAD.getText())-Integer.parseInt(textCANTIDAD_A_COMPRAR.getText())));
                    ps.setString(4, actualizar_cantidad.getText());


                    int res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"PRODUCTO" + textCODIGO.getText() + " ACTUALIZADO");
                    }else{
                        JOptionPane.showMessageDialog(null,"NO GUARDADO");
                    }
                    conexion.close();//importante!!!!
                    rs.close();
                    s.close();
                    ps.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        eliminarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });

        guardarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*ACTUALIZA CABECERA DE TRANSACCIÓN*/
                try{
                    SUBTOTAL = SUBTOTAL.substring(0,3);

                    subtotal_f.setText(SUBTOTAL);

                    IVA = String.valueOf(Double.parseDouble(SUBTOTAL) * 0.12);
                    IVA = IVA.substring(0,3);
                    iva_f.setText(IVA);

                    TOTAL = String.valueOf(Double.parseDouble(SUBTOTAL)  + Double.parseDouble(IVA) - Double.parseDouble(descuento_f.getText()));
                    TOTAL = TOTAL.substring(0,3);
                    total_f.setText(TOTAL);

                    Connection conexion;
                    conexion = getConection();
                    n_factura.setText("002 001 123456790");

                    String fac= "\"" + n_factura +"\"";
                    ps = conexion.prepareStatement("UPDATE cab_trans SET num_f = ?,fecha_f = ?,FKid_caj= ?,subtotal_f =?,iva_f =?,descuento_f=?,total_f=?  WHERE num_f =" + fac );
                    ps.setString(1, "\""+n_factura.getText()+"\"");
                    ps.setString(2, "\""+textFECHA.getText()+"\"");
                    ps.setString(3, "\""+textCAJERO.getText()+"\"");
                    ps.setString(4, subtotal_f.getText());
                    ps.setString(5, iva_f.getText());
                    ps.setString(6, descuento_f.getText());
                    ps.setString(7, total_f.getText());

                    int res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"GUARDADO CAB_TRANSACCION");
                    }else{
                        JOptionPane.showMessageDialog(null,"NO GUARDADO cab ");
                    }
                    conexion.close();//importante!!!!
                    rs.close();
                    s.close();
                    ps.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }); /*FIN GUARDAR PRODCUTOS*/

        JFrame frame=new JFrame("Transaccion");
        frame.setContentPane(new Transaccion().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void agregar(){
        modelo.addRow(new Object[]{textCODIGO.getText(),textPRODUCTO.getText(),textCANTIDAD_A_COMPRAR.getText(),precio_total_producto.getText()});

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
