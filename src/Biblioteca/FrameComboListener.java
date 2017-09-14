package Biblioteca;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

//import com.mxrck.autocompleter.TextAutoCompleter;

import Obj_Principal.ComboListener;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class FrameComboListener extends JFrame 
{

	
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox cbPesawat;
	String pesawat[] = {"Garuda","Lion Air","Lufthansa Air","Batavia Air","Bali Air"};
	@SuppressWarnings("rawtypes")
	Vector vectorPesawat = new Vector();

	JTextField txtbuscar    = new Componentes().text(new JCTextField(), "Puesto", 200                                                 , "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FrameComboListener() 
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		cbPesawat = new JComboBox();
		cbPesawat.setModel(new DefaultComboBoxModel(vectorPesawat));
		cbPesawat.setSelectedIndex(-1);
		cbPesawat.setEditable(true);
		JTextField text = (JTextField)cbPesawat.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setText("");
		text.addKeyListener(new ComboListener(cbPesawat,vectorPesawat));
		
		cbPesawat.setBounds(144, 56, 165, 24);
		contentPane.add(cbPesawat);
		
		
		////para jtext field
		txtbuscar.setBounds(144, 100, 165, 24);
		contentPane.add(txtbuscar);
		
//		TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( txtbuscar );
//		textAutoAcompleter.setMode(0);
//		
//		textAutoAcompleter.addItem("Super 1");
//		textAutoAcompleter.addItem("Super 2");
//		textAutoAcompleter.addItem("Super 5");
//		textAutoAcompleter.addItem("Refaccionaria");
		

		setLocationRelativeTo(null);
		setVectorPesawat();
	}
	
	@SuppressWarnings("unchecked")
	public void setVectorPesawat()
	{
		int a;
		for(a=0;a<pesawat.length;a++)
		{
			vectorPesawat.add(pesawat[a]);
		}
	}

	
	
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new FrameComboListener().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}
