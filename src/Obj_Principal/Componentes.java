package Obj_Principal;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;

public class Componentes {
	
	public static void main(String args[]){
		File f = new File(System.getProperty("user.dir")+"/bin");
		File[] ficheros = f.listFiles();
		for(File fich : ficheros){
			if(fich.isDirectory() && !fich.getName().startsWith(".") && fich.getName().startsWith("Cat")){
				System.out.println(fich.getName());
			}
		}
	}
	
	public void resetComponents(JLayeredPane panel){
		for(Component comp : panel.getComponents()){
			if(comp instanceof JTextField){
				((JTextField) comp).setText("");
				((JTextField) comp).setEditable(true);
			}
			if(comp instanceof JCheckBox)
				((JCheckBox) comp).setSelected(true);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void editComponents(String className){
		try {
			Class instance = Class.forName(className);
			Object instanceObject;
			instanceObject = instance.newInstance();
			
			if(instanceObject.getClass().getSuperclass().getName().length() > 0){
				Class extensions = Class.forName(instanceObject.getClass().getSuperclass().getName());
				Object extensionObjects;
				extensionObjects = extensions.newInstance();
				
				for(Field campos : ((Component) extensionObjects).getClass().getDeclaredFields())
					System.out.println("Campos: "+ campos.getName());
			}
			for(Field campos : ((Component) instanceObject).getClass().getDeclaredFields())
				System.out.println("Campos: "+ campos.getName());
			
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException: " + e.getMessage());
		}catch (InstantiationException e) {
			System.err.println("Error InstantiationException: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.err.println("Error IllegalAccessException: " + e.getMessage());
		}
	}
	
	public final JPasswordField textPassword(final JCPasswordField tmp, final String caption, final int longitud){
		tmp.addKeyListener(new KeyListener() {
			@SuppressWarnings("deprecation")
			public void keyTyped(KeyEvent e) {
				char character = e.getKeyChar();
				if((int) character == 32)
					e.consume();
				if(getTextProcesa(tmp.getText()).length() >= longitud)
					e.consume();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		
	    ((JCPasswordField) tmp).setPlaceholder(caption);
	    ((JCPasswordField) tmp).setPhColor( new Color(0,0,255) );
	    tmp.setFont( new Font("SansSerif",Font.PLAIN, 12) );
		tmp.setToolTipText(caption);
		return tmp;
	}
	
	public final JTextArea textArea(final JTextArea tmp, final String caption, final int longitud){
		tmp.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				tmp.setLineWrap(true); 
				tmp.setWrapStyleWord(true);
				if(getTextProcesa(tmp.getText()).length() >= longitud)
					e.consume();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		
		if(tmp.getClass().getSimpleName().equals("JCTextArea")){
		((JCTextArea) tmp).setPlaceholder(caption);
		((JCTextArea) tmp).setPhColor( new Color(0,0,255) );
		}
		
		tmp.setToolTipText(caption);
		tmp.setLineWrap(true); 
		tmp.setWrapStyleWord(true);
		tmp.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		
		return tmp;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final JComboBox comboBox(final JComboBox cmb, final Object[] list){
		
		Vector vector = new Vector();
		
		for(int i=0; i<list.length; i++){
			vector.add(list[i]);
		}
		
		JComboBox cbPesawat = new JComboBox();
		cbPesawat.setModel(new DefaultComboBoxModel(vector));
		cbPesawat.setSelectedIndex(0);
		cbPesawat.setEditable(true);
		JTextField text = (JTextField)cbPesawat.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.addKeyListener(new ComboListener(cbPesawat,vector));
		
		text.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {
				String text = ((JTextField)arg0.getSource()).getText();
				cbPesawat.setModel(new DefaultComboBoxModel(getFilteredList(text,vector)));
//				cbListener.setSelectedIndex(-1);
				((JTextField)cbPesawat.getEditor().getEditorComponent()).setText(text);
				cbPesawat.showPopup();
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}
		});
		
		
		
		return cbPesawat;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector getFilteredList(String text, Vector vector)
	{
		Vector v = new Vector();
		for(int a = 0;a<vector.size();a++)
		{
			if(vector.get(a).toString().startsWith(text))
			{
				v.add(vector.get(a).toString());
			}
		}
		return v;
	}
	
	Obj_tabla ObjTab =new Obj_tabla();
	public final JTextField textfiltro(final JTextField tmp, final String caption, final int longitud, final String tipo, JTable tabla,final int columnas){
		tmp.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				ObjTab.Obj_Filtro(tabla, tmp.getText().toUpperCase(), columnas,tmp);
				funciones_validacion(tmp, longitud, e, tipo);
			}
			public void keyPressed(KeyEvent e) {
				}
		});
	tmp.setToolTipText(caption);
	if(tmp.getClass().getSimpleName().equals("JCTextField")){
	((JCTextField) tmp).setPlaceholder(caption);
	((JCTextField) tmp).setPhColor( new Color(0,0,255) );
	tmp.setFont( new Font("SansSerif",Font.PLAIN, 12) );
	}
	return tmp;
}
	
	public void funciones_validacion(JTextField tmp,final int longitud,KeyEvent e, final String tipo){
		char caracter = e.getKeyChar();
		switch (tipo) {
			case "String" :
				if(getTextProcesa(tmp.getText()).length() >= longitud)
					e.consume();
				break;
			case "Int" :
				if(getTextProcesa(tmp.getText()).length() >= longitud)
					e.consume();
				if(((caracter < '0') ||
				        (caracter > '9')) &&
				        (caracter != KeyEvent.VK_BACK_SPACE)){
				    e.consume(); 
				}
				break;
			case "Double" :
				if(getTextProcesa(tmp.getText()).length() >= longitud)
					e.consume();
			    if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.' )){
			    	e.consume();
			    }
			    
			    if(caracter =='.' && getTextProcesa(tmp.getText()).length() == 0  )
			    	e.consume();
			    
			    if (caracter==KeyEvent.VK_PERIOD)
					if (tmp.getText().indexOf(".")>-1) 
						e.consume();
		
				break;
			case "Real" :
				if(getTextProcesa(tmp.getText()).length() >= longitud)
					e.consume();
			    if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.' )&& 
			    	(caracter != '-' )){
			    	e.consume();
			    }
			    	
			    if (caracter==KeyEvent.VK_PERIOD)
					if (tmp.getText().indexOf(".")>-1){
						e.consume();
					}
				
			    if (caracter==KeyEvent.VK_MINUS){
			    	if(tmp.getText().indexOf("-")>=-1 && !tmp.getText().equals("")){
			    			e.consume();
			    	}
			    }
				break;
				
			case "Negativo" :
				if(getTextProcesa(tmp.getText()).length() >= longitud)
					e.consume();
			    if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '-' )){
			    	e.consume();
			    }
			    	
				break;	
		}
		
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public final JTextField text(final JTextField tmp, final String caption, final int longitud, final String tipo){
			tmp.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					funciones_validacion(tmp, longitud, e, tipo);
				}
				public void keyReleased(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {}
			});
			
		tmp.setToolTipText(caption);
		if(tmp.getClass().getSimpleName().equals("JCTextField")){
		((JCTextField) tmp).setPlaceholder(caption);
		((JCTextField) tmp).setPhColor( new Color(0,0,255) );
		tmp.setFont( new Font("SansSerif",Font.PLAIN, 12) );
		}
		return tmp;
	}
	
	public final  JDateChooser jchooser(JDateChooser tmp, final String parametro ,int dias){
	tmp = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
	tmp.setFont( new Font("SansSerif",Font.PLAIN, 12) );
	tmp.setDate( parametro.equals("")?null:cargar_fechas(dias));
	return tmp;
   }
	
	public Date cargar_fechas(Integer dias){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date1;
	};
	
////////////
	public JCheckBox check(final JCheckBox tmp, final String nombre, final boolean select, final String caption){
		tmp.setText(nombre);
			tmp.setSelected(select);
				tmp.setToolTipText(caption);
		return tmp;
	}
	public String getTextProcesa(String texto) {
		StringTokenizer tokens = new StringTokenizer(texto);
	    texto = "";
	    while(tokens.hasMoreTokens())
	    	texto += " "+tokens.nextToken();

	    return texto.toString().trim().toUpperCase();
	}
	
	public String getTextProcesaClean(String input) {
	    String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    
	    for(int i=0; i<original.length(); i++)
	    	input = input.replace(original.charAt(i), ascii.charAt(i));

	    return input;
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public String existPackageFile(String root){
		try{
			Class instance = Class.forName(root);
			return root;
		}catch(ClassNotFoundException e){
			return "";
		}
	}
	@SuppressWarnings({ "rawtypes", "unused" })
	public String classExiste(String nombre){
		try{
			String claseRoot = "";
			File f = new File(System.getProperty("user.dir")+"/bin");
			File[] ficheros = f.listFiles();
			for(File fich : ficheros){
				if(fich.isDirectory() && !fich.getName().startsWith(".") && fich.getName().startsWith("Cat")){
					String tmp = fich.getName();
					tmp = getTextProcesaClean(tmp+".Cat "+ nombre);
					tmp = tmp.replaceAll(" ", "_");
					if(existPackageFile(tmp).length() > 0)
						claseRoot = tmp;
				}
			}
					
			Class instance = Class.forName(claseRoot);
			return claseRoot;
		}catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(null, "No se ha creado la clase [" + nombre + "] Error en Obj_Principal.Componentes.classExiste  ", "Avisa al Administrador", JOptionPane.INFORMATION_MESSAGE);
			return "";
		}
	}
}
