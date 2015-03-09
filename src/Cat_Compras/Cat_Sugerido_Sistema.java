package Cat_Compras;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Obj_Renders.tablaRenderer;


@SuppressWarnings("serial")
public class Cat_Sugerido_Sistema extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
		Object[][] matriz = {{"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false,"".toUpperCase(),"".toUpperCase()},
							 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false,"".toUpperCase(),"".toUpperCase()},
							 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false,"".toUpperCase(),"".toUpperCase()},
							 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false,"".toUpperCase(),"".toUpperCase()},
							 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false,"".toUpperCase(),"".toUpperCase()}};
	
    public DefaultTableModel modelo = new DefaultTableModel(matriz, new String[]{"Cod.Prod.","Descripcion","Exist.Cedis", "Transferencia", "sugerido", "*", "Sugerido Cedis", "Observaciones"} ){
                    
		@SuppressWarnings({ "rawtypes" })
		Class[] types = new Class[]{
                   java.lang.String.class, 
                   java.lang.Deprecated.class, 
                   java.lang.Double.class, 
                   java.lang.Float.class,
                   java.lang.Integer.class, 
                   java.lang.Boolean.class,
                   java.lang.String.class, 
                   java.lang.String.class
                    
    };
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
        }
    public boolean isCellEditable(int fila, int columna){
    	
                switch(columna){
                        case 0  : return false; 
                        case 1  : return false; 
                        case 2  : return false; 
                        case 3  : return false; 
                        case 4  : return false; 
                        case 5  : return true; 
                        case 6  : if(modelo.getValueAt(fila, 5).toString().equals("true")){
                        	return true;
                        }else{
                        	return false;
                        }
                        case 7  : if(modelo.getValueAt(fila, 5).toString().equals("true")){
                        	return true;
                        }else{
                        	return false;
                        } 
                }
                 return false;
                 
                 
         }
    };
    
    JTable tabla = new JTable(modelo);
    JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JButton btnGenerar = new JButton("Generar");
    
	public Cat_Sugerido_Sistema(){
		
		panel.add(scroll).setBounds(15, 40, 990, 130);
		panel.add(btnGenerar).setBounds(905,180,90,20);
		
		btnGenerar.addActionListener(opGenerar);
		cont.add(panel);
		
//		new Obj_Render(tabla);
		llamarRender();
		tamanoColumnas();
		
		this.setSize(1024,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public void llamarRender(){
		
		for(int i=0; i<tabla.getColumnCount(); i++){
			
			System.out.println(tabla.getColumnClass(i).getSimpleName());
			
			switch(tabla.getColumnClass(i).getSimpleName()){
//				case "String": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
//				Object = fechas
				case "Object": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
				case "Double": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
				case "Float": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
				case "Integer": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
				case "Deprecated": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
				case "Boolean": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",11)); break;
				default: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
			}
		}
	}
	
	public void tamanoColumnas(){
		int x=80;
		
    	this.tabla.getTableHeader().setReorderingAllowed(false);
    	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	this.tabla.getColumnModel().getColumn(0 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(0 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(1 ).setMaxWidth(x*4);
    	this.tabla.getColumnModel().getColumn(1 ).setMinWidth(x*4);
    	this.tabla.getColumnModel().getColumn(2 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(2 ).setMinWidth(x);
    	                                               
    	this.tabla.getColumnModel().getColumn(3 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(3 ).setMinWidth(x);		
    	this.tabla.getColumnModel().getColumn(4 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(4 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(5 ).setMaxWidth(x/2);
    	this.tabla.getColumnModel().getColumn(5 ).setMinWidth(x/2);
    	this.tabla.getColumnModel().getColumn(6 ).setMaxWidth(x+20);
    	this.tabla.getColumnModel().getColumn(6 ).setMinWidth(x+20);
    	                                                
    	this.tabla.getColumnModel().getColumn(7 ).setMaxWidth(x*3);
    	this.tabla.getColumnModel().getColumn(7 ).setMinWidth(x*3);	
	}
	
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			Object[][] matriz = new Object[tabla.getRowCount()][tabla.getColumnCount()];
			for(int i=0; i<tabla.getRowCount(); i++){
				for(int j=0; j<tabla.getColumnCount(); j++){
					
					if(tabla.getValueAt(i, 5).toString().equals("true")){
						if(tabla.getValueAt(i, 6).toString().equals("")){
						
							matriz=null;
							JOptionPane.showMessageDialog(null, "La fila con el codigo de producto   '"+tabla.getValueAt(i, 0)+"'   esta seleccionada y no asigno valor en:   'Sugerido Cedis'","Aviso",JOptionPane.WARNING_MESSAGE);
							return;
							
						}else{
							
							if(tabla.getValueAt(i, 7).toString().equals("")){
									matriz=null;
									JOptionPane.showMessageDialog(null, "La fila con el codigo de producto   '"+tabla.getValueAt(i, 0)+"'   esta seleccionada y no se agrego:   'Observaciones'","Aviso",JOptionPane.WARNING_MESSAGE);
									return;
								
							}else{
									matriz[i][j] = tabla.getValueAt(i, j);
									if(j==tabla.getColumnCount()-1){
										System.out.println(tabla.getValueAt(i, j).toString());
									}else{
										System.out.print(tabla.getValueAt(i, j).toString()+"  ");
									}
							}
						}
					}else{
							matriz[i][j] = tabla.getValueAt(i, j);
							if(j==tabla.getColumnCount()-1){
								System.out.println(tabla.getValueAt(i, j).toString());
							}else{
								System.out.print(tabla.getValueAt(i, j).toString()+"  ");
							}
					}
				}
			}
//			if(new GuardarSQL().Guardar_Sugerido_Sistema(matriz)){
//				JOptionPane.showMessageDialog(null, "Sugeridos de sistema guardados","Aviso",JOptionPane.WARNING_MESSAGE);
//				return;
//			}
		}
	};
	
	public static void main(String [] arg){
		new Cat_Sugerido_Sistema().setVisible(true);
	}
}
