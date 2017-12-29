package Cat_Checador;

import java.awt.Container;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCSpinner;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Horario_base  extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JLayeredPane horario1 = new JLayeredPane();       
	JLayeredPane horario2 = new JLayeredPane(); 
	JTabbedPane paneles = new JTabbedPane();                                           
	                                                                                   
	JToggleButton btnDomingo   = new JToggleButton("Domingo"  );                           
	JToggleButton btnLunes     = new JToggleButton("Lunes"    );                               
	JToggleButton btnMartes    = new JToggleButton("Martes"   );                             
	JToggleButton btnMiercoles = new JToggleButton("Miércoles");                       
	JToggleButton btnJueves    = new JToggleButton("Jueves"   );                             
	JToggleButton btnViernes   = new JToggleButton("Viernes"  );                           
	JToggleButton btnSabado    = new JToggleButton("Sábado"   );                             
	JToggleButton btnSD        = new JToggleButton("Todos"    );  
	
	JRadioButton rbDomingo   = new JRadioButton("",false);
	JRadioButton rbLunes     = new JRadioButton("",false);
	JRadioButton rbMartes    = new JRadioButton("",false);
	JRadioButton rbMiercoles = new JRadioButton("",false);
	JRadioButton rbJueves    = new JRadioButton("",false);
	JRadioButton rbViernes   = new JRadioButton("",false);
	JRadioButton rbSabado    = new JRadioButton("",false);
	JRadioButton rbNoDobla   = new JRadioButton("",true );
	
	JRadioButton rbDomingo2  = new JRadioButton("",false);
	JRadioButton rbLunes2    = new JRadioButton("",false);
	JRadioButton rbMartes2   = new JRadioButton("",false);
	JRadioButton rbMiercoles2= new JRadioButton("",false);
	JRadioButton rbJueves2   = new JRadioButton("",false);
	JRadioButton rbViernes2  = new JRadioButton("",false);
	JRadioButton rbSabado2   = new JRadioButton("",false);
	JRadioButton rbNoDobla2  = new JRadioButton("",true );
	
	JRadioButton rbDomingo3  = new JRadioButton("",false);
	JRadioButton rbLunes3    = new JRadioButton("",false);
	JRadioButton rbMartes3   = new JRadioButton("",false);
	JRadioButton rbMiercoles3= new JRadioButton("",false);
	JRadioButton rbJueves3   = new JRadioButton("",false);
	JRadioButton rbViernes3  = new JRadioButton("",false);
	JRadioButton rbSabado3   = new JRadioButton("",false);
	JRadioButton rbNoDobla3  = new JRadioButton("",true );
	
	JToolBar menu_toolbar = new JToolBar();
	
	JCButton btnFiltro    = new JCButton("Buscar"    ,"Filter-List-icon16.png"      ,"Azul"); 
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png"                   ,"Azul");
	JCButton btnEditar    = new JCButton("Modificar" ,"Modify.png"                  ,"Azul");
	JCButton btnAceptar   = new JCButton("Guardar"   ,"Guardar.png"                 ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"              ,"Azul");
	JCButton btnReportes  = new JCButton("Reportes de Horarios","Lista.png"         ,"Azul");	
	JCButton btnIgual     = new JCButton("Copiar A Igual Todos Los Dias","igual.png","Azul");	
	
	JCheckBox chbHorarioDeposito = new JCheckBox("Horario Deposito");
	JCheckBox chbRecesoExtraDiario = new JCheckBox("Receso extra diario (15 min)");
	
	int Descanso=0;

	String[] inicioDefault ="0:00:00".split (":");
	String[] finDefault    ="23:59:59".split(":");
	String[] entradaDefault="8:00:00".split (":");
	String[] salidaDefault ="20:00:00".split(":");
	String[] recesoDefault ="2:00:00".split (":");

	JSpinner  spDomingo1    = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  spDomingo2    = new JCSpinner().JCSpinnerHoras(23 ,59); 
	JSpinner  spDomingo3    = new JCSpinner().JCSpinnerHoras(8  ,0 ); 
	JSpinner  spDomingo4    = new JCSpinner().JCSpinnerHoras(20 ,0 );  
	JSpinner  spDomingo5    = new JCSpinner().JCSpinnerHoras(2  ,0 );  
                                                                      
	JSpinner  spLunes1      = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  spLunes2      = new JCSpinner().JCSpinnerHoras(23 ,59); 
	JSpinner  spLunes3      = new JCSpinner().JCSpinnerHoras(8  ,0 ); 
	JSpinner  spLunes4      = new JCSpinner().JCSpinnerHoras(20 ,0 );  
	JSpinner  spLunes5      = new JCSpinner().JCSpinnerHoras(2  ,0 );  	  
                                                                   
	JSpinner  spMartes1     = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  spMartes2     = new JCSpinner().JCSpinnerHoras(23 ,59); 
	JSpinner  spMartes3     = new JCSpinner().JCSpinnerHoras(8  ,0 ); 
	JSpinner  spMartes4     = new JCSpinner().JCSpinnerHoras(20 ,0 );  
	JSpinner  spMartes5     = new JCSpinner().JCSpinnerHoras(2  ,0 );  	  
    
	JSpinner  spMiercoles1  = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  spMiercoles2  = new JCSpinner().JCSpinnerHoras(23 ,59); 
	JSpinner  spMiercoles3  = new JCSpinner().JCSpinnerHoras(8  ,0 ); 
	JSpinner  spMiercoles4  = new JCSpinner().JCSpinnerHoras(20 ,0 );  
	JSpinner  spMiercoles5  = new JCSpinner().JCSpinnerHoras(2  ,0 );  
                                                                    
	JSpinner  spJueves1     = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  spJueves2     = new JCSpinner().JCSpinnerHoras(23 ,59); 
	JSpinner  spJueves3     = new JCSpinner().JCSpinnerHoras(8  ,0 ); 
	JSpinner  spJueves4     = new JCSpinner().JCSpinnerHoras(20 ,0 );  
	JSpinner  spJueves5     = new JCSpinner().JCSpinnerHoras(2  ,0 );  
                                                                   
	JSpinner  spViernes1    = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  spViernes2    = new JCSpinner().JCSpinnerHoras(23 ,59); 
	JSpinner  spViernes3    = new JCSpinner().JCSpinnerHoras(8  ,0 ); 
	JSpinner  spViernes4    = new JCSpinner().JCSpinnerHoras(20 ,0 );  
	JSpinner  spViernes5    = new JCSpinner().JCSpinnerHoras(2  ,0 );  
                                                                 
	JSpinner  spSabado1     = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  spSabado2     = new JCSpinner().JCSpinnerHoras(23 ,59); 
	JSpinner  spSabado3     = new JCSpinner().JCSpinnerHoras(8  ,0 ); 
	JSpinner  spSabado4     = new JCSpinner().JCSpinnerHoras(20 ,0 );  
	JSpinner  spSabado5     = new JCSpinner().JCSpinnerHoras(2  ,0 );

	JLabel lblDobla            = new JLabel("Dia Dobla");
	JLabel lblDobla2           = new JLabel("Dia Dobla");
	JLabel lblDobla21          = new JLabel("  Extra 1");
	JLabel lblDobla3           = new JLabel("Dia Dobla");
	JLabel lblDobla31          = new JLabel("  Extra 2");
	JLabel lblNoDobla          = new JLabel("No Dobla");
	
	JLabel lblDia              = new JLabel("Día");                                                 
	JLabel lblLimi             = new JLabel("Límites del día");                                    
	JLabel lblInicio           = new JLabel("Inicio");                                           
	JLabel lblFin              = new JLabel("Fin");                                                 
	                                                                                   
	JLabel lblTrabajo          = new JLabel("Trabajo");                                         
	JLabel lblEntrada          = new JLabel("Entrada");                                         
	JLabel lblSalida           = new JLabel("Salida");                                           
	JLabel lblComida           = new JLabel("Comida");                                           
	JLabel lblReceso           = new JLabel("Receso");
	JLabel lblSintaxis         = new JLabel("(Puesto   Establecimiento   ");
	JLabel lblSintaxis2        = new JLabel("Mat/Vesp   ");
	JLabel lblSintaxis3        = new JLabel("am-pm   C:Comida(hrs)   D:Descanso   DB:Dobla)");

	JLabel lblMarcoDoblaExtra1 = new JLabel();
	JLabel lblMarcoDoblaExtra2 = new JLabel();
	JLabel lblFolio = new JLabel("Folio");
	
	JTextField txtFolio         = new Componentes().text(new JCTextField(), "Folio"    , 10, "Int");
	JTextField txtNombre        = new Componentes().text(new JCTextField(), "Nombre Del Horario> Puesto Establecimiento Mat/Int/Vesp  am-pm C:Comida (hrs/min) D:Descanso"    , 100, "String");
	JTextField txtEntradaExtra1 = new JTextField("");
	JTextField txtSalidaExtra1  = new JTextField("");
	JTextField txtEntradaExtra2 = new JTextField("");
	JTextField txtSalidaExtra2  = new JTextField("");
	JTextField txtComida1       = new JTextField("");
	JTextField txtComida2       = new JTextField("");
}
