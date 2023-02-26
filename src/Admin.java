import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Admin extends JFrame implements ActionListener{
    private JMenuBar barra;
    private JMenu menu1;
    private JMenuItem STOCK, VENTAS, VENTAS_INDIVIDUALES, CAJEROS;

    public Admin(){
        barra = new JMenuBar();
        setJMenuBar(barra);
        menu1 = new JMenu("MENU OPCIONES");
        barra.add(menu1);

        STOCK = new JMenuItem("STOCK");
        menu1.add(STOCK);
        STOCK.addActionListener(this);

        VENTAS = new JMenuItem("VENTAS GENERALES");
        menu1.add(VENTAS);
        VENTAS.addActionListener(this);

        VENTAS_INDIVIDUALES = new JMenuItem("VENTAS INDIVIDUALES");
        menu1.add(VENTAS_INDIVIDUALES);
        VENTAS_INDIVIDUALES.addActionListener(this);

        CAJEROS = new JMenuItem("AGREGAR CAJEROS");
        menu1.add(CAJEROS);
        CAJEROS.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==STOCK){
            JFrame frame=new JFrame("ADMINISTRAR STOCK");
            frame.setContentPane(new Admin_stock().Panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setBounds(0,0,1000, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        if(e.getSource()==VENTAS){
            /*
            JFrame frame=new JFrame("ADMINISTRAR STOCK");
            frame.setContentPane(new Admin_stock().Panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setBounds(0,0,1000, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
             */
        }
        if(e.getSource()==VENTAS_INDIVIDUALES){
            JFrame frame=new JFrame("ADMINISTRAR VENTAS INDIVIDUALES");
            frame.setContentPane(new Admin_ventas().Panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setBounds(0,0,1000, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        if(e.getSource()==CAJEROS){
            JFrame frame=new JFrame("AGREGAR CAJEROS");
            frame.setContentPane(new Admin_cajeros().Panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setBounds(0,0,1000, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}
