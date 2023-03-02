import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cajero_producto{
    Statement s;
    ResultSet rs;
    PreparedStatement ps;
    int res;
    public JPanel panel;
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
    DefaultTableModel modelo = new DefaultTableModel();
    boolean encontrado; // verifica si se encontro el cliente
    JFormattedTextField precio_total_producto = new JFormattedTextField(); /*Precio de un producto (cantidad * precio_unit)*/
    JFormattedTextField actualizar_cantidad = new JFormattedTextField();/*actualizacón de cantidad de productos en stock*/
    JFormattedTextField subtotal_f = new JFormattedTextField(); /*subtotal final para transacciòn*/
    JFormattedTextField iva_f = new JFormattedTextField();/*iva final para transacción*/
    JFormattedTextField descuento_f = new JFormattedTextField();/*descuento final para transacción*/
    JFormattedTextField total_f = new JFormattedTextField();/*total final para transacción*/
    JFormattedTextField Num_factura = new JFormattedTextField();/*número de la transacción*/

    String SUBTOTAL = String.valueOf(0.0);/*Realiza un proceso: suma de los precios*/
    String IVA = String.valueOf(0.0);/*Realiza un proceso: calcula el iva de subtotal*/
    String DESCUENTO= String.valueOf(0.0);/*Realiza un proceso: calcula el descuento */

    String TOTAL= String.valueOf(0.0);/*Realiza un proceso: calcula el total(subtotal+iva-descuento)*/

    public cajero_producto() {

        /*descuento_f.setText("0.10");*/

        try{ /*Se obtiene el nombre completo del cajero*/
            Connection conexion;
            conexion = getConection();

            s = conexion.createStatement();
            rs = s.executeQuery("Select nombres_caj,apellidos_caj from cajero where id_caj = (Select FKid_caj from cab_trans ORDER by num_f DESC LIMIT 1)");

            while (rs.next()) {
                textCAJERO.setText(rs.getString(1) + " " + rs.getString(2));
            }

            rs = s.executeQuery("Select num_f from cab_trans ORDER by num_f DESC LIMIT 1");

            while (rs.next()) {
                Num_factura.setText(rs.getString(1));
            }

            conexion.close();
            rs.close();
            s.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }//Fin obtener nombre del cajero

        buscarCLIENTE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encontrado = false;
                try { // Se busca al cliente por medio de la cédula
                    Connection conexion;
                    conexion = getConection();

                    String id = "\"" + textCEDULA.getText() + "\"";

                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM cliente WHERE ci_cl = " + id);

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
        }); // Fin buscar cliente

        registrarCLIENTE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {/* Registra un nuevo cliente*/
                    Connection conexion;
                    conexion = getConection();

                    ps = conexion.prepareStatement("Insert into cliente values (?,?,?,?,?)");
                    ps.setString(1, textCEDULA.getText());
                    ps.setString(2, textNOMBRE.getText());
                    ps.setString(3, textAPELLIDO.getText());
                    ps.setString(4, textCELULAR.getText());
                    ps.setString(5, textDIRECCION.getText());

                    res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"CLIENTE " + textNOMBRE.getText() + " agregado");
                    }else{
                        JOptionPane.showMessageDialog(null,"CLIENTE NO GUARDADO");
                    }

                    conexion.close();
                    ps.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });//FIN REGISTRAR CLIENTE

        buscarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encontrado = false;
                try {// Busca un producto por medio del código
                    Connection conexion;
                    conexion = getConection();

                    String cod = "\"" + textCODIGO.getText() + "\"";
                    s = conexion.createStatement();
                    rs = s.executeQuery("SELECT * FROM stock WHERE cod_p =" + cod);

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

        //Se agrega la cabecera de la tabla
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
                // Se obtiene le precio total de un producto(cantidad * precio)
                precio_total_producto.setText(String.valueOf(Integer.parseInt(textCANTIDAD_A_COMPRAR.getText())*Float.parseFloat(textPRECIO.getText())));
                agregar();

                if(textCODIGO.getText().equals("aa10")){// Se obtiene el descuento del 10% de un producto en particular
                    descuento_f.setText(String.valueOf(Integer.parseUnsignedInt(precio_total_producto.getText())*0.10));
                }else{
                    descuento_f.setText(String.valueOf(0.0));
                }

                DESCUENTO = DESCUENTO + descuento_f;

                try {// Ingresa los productos al detalle de transacción

                    Connection conexion;
                    conexion = getConection();

                    ps = conexion.prepareStatement("Insert into det_trans values (?,?,?,?)");
                    ps.setString(1, Num_factura.getText());
                    ps.setString(2, textCODIGO.getText());
                    ps.setString(3, textCANTIDAD_A_COMPRAR.getText());
                    ps.setString(4, precio_total_producto.getText());

                    SUBTOTAL = String.valueOf(Double.parseDouble(SUBTOTAL) + Double.parseDouble(precio_total_producto.getText()));

                    res = ps.executeUpdate();
                    if(!(res >0)){
                        JOptionPane.showMessageDialog(null,"PRODUCTO EN DETALLE DE TRANSACCIÓN NO GUARDADO");
                    }

                    conexion.close();
                    ps.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }// Fin detalle de transacción

                try{//Actualiza la cantidad de un producto en STOCK

                    Connection conexion;
                    conexion = getConection();

                    String cod2 = "\"" + textCODIGO.getText() + "\"";

                    ps = conexion.prepareStatement("UPDATE stock SET cod_p = ?,nombre_p = ?, precio_unit_p= ?, cantidad_exist_p =? WHERE cod_p = " + cod2);
                    ps.setString(1, textCODIGO.getText());
                    ps.setString(2, textPRODUCTO.getText());
                    ps.setString(3, textPRECIO.getText());
                    actualizar_cantidad.setText(String.valueOf(Integer.parseInt(textCANTIDAD.getText())-Integer.parseInt(textCANTIDAD_A_COMPRAR.getText())));
                    ps.setString(4, actualizar_cantidad.getText());


                    res = ps.executeUpdate();
                    if(!(res >0)){
                        JOptionPane.showMessageDialog(null,"CANTIDAD DE PRODUCTO NO ACTUALIZADO");
                    }

                    conexion.close();//importante!!!!
                    ps.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }// FIN DE ACTUALIZACIÓN DE STOCK
            }
        });
        /*
        eliminarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
         */

        guardarPRODUCTO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{// Ingresa campos pendientes en CABECERA DE TRANSACCIÓN
                    SUBTOTAL = SUBTOTAL.substring(0,3);
                    subtotal_f.setText(SUBTOTAL);

                    IVA = String.valueOf(Double.parseDouble(SUBTOTAL) * 0.12);
                    IVA = IVA.substring(0,3);
                    iva_f.setText(IVA);

                    DESCUENTO = DESCUENTO.substring(0,3);
                    descuento_f.setText(DESCUENTO);

                    TOTAL = String.valueOf(Double.parseDouble(SUBTOTAL)  + Double.parseDouble(IVA) - Double.parseDouble(descuento_f.getText()));
                    TOTAL = TOTAL.substring(0,3);
                    total_f.setText(TOTAL);

                    Connection conexion;
                    conexion = getConection();

                    String fac= "\"" + Num_factura.getText() +"\"";
                    ps = conexion.prepareStatement("UPDATE cab_trans SET fecha_f= concat(DATE(NOW()),\" \", TIME(NOW())),subtotal_f =?,iva_f =?,descuento_f=?,total_f=?,  FKci_cl = ? WHERE num_f =" + fac );
                    ps.setString(1, subtotal_f.getText());
                    ps.setString(2, iva_f.getText());
                    ps.setString(3, descuento_f.getText());
                    ps.setString(4, total_f.getText());
                    ps.setString(5, textCEDULA.getText());
                    System.out.println(ps);


                    res = ps.executeUpdate();
                    if(!(res >0)){
                        JOptionPane.showMessageDialog(null,"DATOS EN CABECERA DE TRANSACCIÓN NO GUARDADOS");
                    }
                    conexion.close();//importante!!!!
                    rs.close();
                    s.close();
                    ps.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }//Fin cabecera de transacción
                /*
                try{
                    Connection conexion;
                    conexion = getConection();

                    s = conexion.createStatement();
                    rs = s.executeQuery("UPDATE ingreso SET cod_p = ?,nombre_p = ?, precio_unit_p= ?, cantidad_exist_p =? WHERE cod_p =");

                    while (rs.next()) {
                        textCAJERO.setText(rs.getString(1) + " " + rs.getString(2));
                    }

                    rs = s.executeQuery("Select num_f from cab_trans ORDER by num_f DESC LIMIT 1");

                    while (rs.next()) {
                        Num_factura.setText(rs.getString(1));
                    }

                    conexion.close();
                    rs.close();
                    s.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                */

                /*AL GUARDAR SE ABRE LA VENTANA CON LA TRANSACCION REALIZADA*/
                JFrame frame=new JFrame("Transaccion");
                frame.setContentPane(new Transaccion().Panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setBounds(0,0,1000, 800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }); /*FIN GUARDAR PRODCUTOS*/

        cerrarCajaButton.addActionListener(new ActionListener() {/*CERRAR Y PASAR A VENTANA ANTERIOR(LOGIN)*/
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame=new JFrame("LOGIN");
                frame.setContentPane(new Login().PanelLogin);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setBounds(0,0,600, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }); /*CERRAR Y PASAR A VENTANA ANTERIOR*/
    }

    public void agregar(){//Agrega a la tabla los producto comprados
        modelo.addRow(new Object[]{textCODIGO.getText(),textPRODUCTO.getText(),textCANTIDAD_A_COMPRAR.getText(),precio_total_producto.getText()});
    }
    /*
    public void eliminar() {
        int fila = table.getSelectedRow();
        modelo.removeRow(fila);
    }
    */
    /*
    public static void main(String[] args) {
        JFrame frame=new JFrame("CAJERO_PRODUCTO");
        frame.setContentPane(new cajero_producto().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    */

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
