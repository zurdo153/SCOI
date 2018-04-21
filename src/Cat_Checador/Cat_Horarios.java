package Cat_Checador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;

import Cat_Reportes.Cat_Reportes_De_Horarios;
import Conexiones_SQL.Obj_Configuracion_Del_Sistema;
import Obj_Checador.Obj_Horarios;

@SuppressWarnings("serial")
public class Cat_Horarios extends Cat_Horario_base{	
	Border blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
	
	public void getContenedor(){
		this.setSize(850,460);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.lblMarcoDoblaExtra1.setBorder(BorderFactory.createTitledBorder(blackline," Dobla Extra 1 "));
		this.lblMarcoDoblaExtra2.setBorder(BorderFactory.createTitledBorder(blackline," Dobla Extra 2 "));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/reloj.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Horario"));
		this.setTitle("Horario");
		this.horario1.setOpaque(true); 
		this.horario1.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		
		lblInicio.setFont(new Font("arial", Font.BOLD, 10));
		lblFin.setFont(new Font("arial", Font.BOLD, 10));
		lblEntrada.setFont(new Font("arial", Font.BOLD, 10));
		lblSalida.setFont(new Font("arial", Font.BOLD, 10));
		lblReceso.setFont(new Font("arial", Font.BOLD, 10));
		
		lblDobla.setFont(new Font("arial", Font.BOLD, 10));
		lblDobla2.setFont(new Font("arial", Font.BOLD, 10));
		lblDobla21.setFont(new Font("arial", Font.BOLD, 10));
		lblDobla3.setFont(new Font("arial", Font.BOLD, 10));
		lblDobla31.setFont(new Font("arial", Font.BOLD, 10));
		
		lblLimi.setFont(new Font("Arial Black",Font.BOLD,10));
		lblTrabajo.setFont(new Font("Arial Black",Font.BOLD,10));
		lblComida.setFont(new Font("Arial Black",Font.BOLD,10));
		
		lblSintaxis.setFont(new Font("Arial ",Font.BOLD,10));
		lblSintaxis2.setFont(new Font("Arial ",Font.BOLD,10));
		lblSintaxis3.setFont(new Font("Arial ",Font.BOLD,10));
		
		lblSintaxis2.setForeground(Color.red);
		
		chbHorarioDeposito.setFont(new Font("Arial",Font.BOLD,10));
		chbRecesoExtraDiario.setFont(new Font("Arial",Font.BOLD,10));
		
		this.menu_toolbar.add(btnFiltro  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnEditar  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnNuevo   );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnAceptar);
		this.menu_toolbar.setFloatable(false);
		
		int x=20, y=5, width=90,height=20 ,sepv=30;
    	panel.add(menu_toolbar).setBounds       (x+100 ,y+10    ,400      ,height);
    	panel.add(btnReportes).setBounds        (x+620 ,y+10    ,190      ,height);
		
		horario1.add(lblFolio).setBounds        (x     ,y       ,width    ,height);
		horario1.add(txtFolio).setBounds        (x+50  ,y       ,width    ,height);
		
		
		horario1.add(lblSintaxis).setBounds(70,50,140,20);
		horario1.add(lblSintaxis2).setBounds(205,50,60,20);
		horario1.add(lblSintaxis3).setBounds(260,50,250,20);
		
		x=20;
		horario1.add(lblLimi).setBounds          (x,80,100,height);
		horario1.add(cmbTurnoCuadrante).setBounds(130,80,150,height);
		horario1.add(lblTrabajo).setBounds       (285+(x+=20),80,60,height);
		horario1.add(lblComida).setBounds        (410+x,80,90,20);
	

		
		horario1.add(lblSalida).setBounds       (330+x,90,60,20);
		horario1.add(lblReceso).setBounds       (410+x,90,50,20);
		horario1.add(lblDobla).setBounds        (490+x,90,80,20);
		horario1.add(lblDobla2).setBounds       (560+x,85,80,20);
		horario1.add(lblDobla21).setBounds      (560+x,95,80,20);
		horario1.add(lblDobla3).setBounds       (630+x,85,80,20);
		horario1.add(lblDobla31).setBounds      (630+x,95,80,20);
		
		horario1.add(lblDia).setBounds			(x=20  ,y=90    ,width    ,height);
		horario1.add(btnDomingo).setBounds		(x     ,y+=sepv ,width    ,height);
		horario1.add(btnLunes).setBounds		(x     ,y+=sepv ,width    ,height);
		horario1.add(btnMartes).setBounds		(x     ,y+=sepv ,width    ,height);
		horario1.add(btnMiercoles).setBounds	(x     ,y+=sepv ,width    ,height);
		horario1.add(btnJueves).setBounds		(x     ,y+=sepv ,width    ,height);
		horario1.add(btnViernes).setBounds		(x     ,y+=sepv ,width    ,height);
		horario1.add(btnSabado).setBounds		(x     ,y+=sepv ,width    ,height);
		horario1.add(btnSD).setBounds			(x     ,y+=sepv ,width    ,height);

		horario1.add(lblInicio).setBounds       (x=130 ,y=100    ,width=70 ,height);
		horario1.add(spDomingo1).setBounds      (x     ,y+=(sepv-10) ,width    ,height);
		horario1.add(spLunes1).setBounds        (x     ,y+=sepv ,width    ,height);
		horario1.add(spMartes1).setBounds       (x     ,y+=sepv ,width    ,height);
		horario1.add(spMiercoles1).setBounds    (x     ,y+=sepv ,width    ,height);
		horario1.add(spJueves1).setBounds       (x     ,y+=sepv ,width    ,height);
		horario1.add(spViernes1).setBounds      (x     ,y+=sepv ,width    ,height);
		horario1.add(spSabado1).setBounds       (x     ,y+=sepv ,width    ,height);
		horario1.add(btnIgual).setBounds        (x     ,y+=sepv ,390      ,height);
		
		horario1.add(lblFin).setBounds          (x=210 ,y=100    ,width=70 ,height);
		horario1.add(spDomingo2).setBounds      (x     ,y+=(sepv-10) ,width    ,height);
		horario1.add(spLunes2).setBounds        (x     ,y+=sepv ,width    ,height);	
		horario1.add(spMartes2).setBounds       (x     ,y+=sepv ,width    ,height);
		horario1.add(spMiercoles2).setBounds    (x     ,y+=sepv ,width    ,height);	
		horario1.add(spJueves2).setBounds       (x     ,y+=sepv ,width    ,height);
		horario1.add(spViernes2).setBounds      (x     ,y+=sepv ,width    ,height);		
		horario1.add(spSabado2).setBounds       (x     ,y+=sepv ,width    ,height);
	
		horario1.add(lblEntrada).setBounds      (x=290 ,y=90    ,width=70 ,height);
		horario1.add(spDomingo3).setBounds      (x     ,y+=sepv ,width    ,height);
		horario1.add(spLunes3).setBounds        (x     ,y+=sepv ,width    ,height);
		horario1.add(spMartes3).setBounds       (x     ,y+=sepv ,width    ,height);
		horario1.add(spMiercoles3).setBounds    (x     ,y+=sepv ,width    ,height);
		horario1.add(spJueves3).setBounds       (x     ,y+=sepv ,width    ,height);
		horario1.add(spViernes3).setBounds      (x     ,y+=sepv ,width    ,height);
		horario1.add(spSabado3).setBounds       (x     ,y+=sepv ,width    ,height);
		
		x=40;
		horario1.add(spDomingo4).setBounds(330+x,120,70,20);
		horario1.add(spLunes4).setBounds(330+x,150,70,20);	
		horario1.add(spMartes4).setBounds(330+x,180,70,20);	
		horario1.add(spMiercoles4).setBounds(330+x,210,70,20);	
		horario1.add(spViernes4).setBounds(330+x,270,70,20);
		horario1.add(spSabado4).setBounds(330+x,300,70,20);
		
		
		horario1.add(spDomingo5).setBounds(410+x,120,70,20);
		
		horario1.add(rbDomingo).setBounds(505+x,120,18,18);
		horario1.add(rbDomingo2).setBounds(575+x,120,18,18);
		horario1.add(rbDomingo3).setBounds(645+x,120,18,18);
	

		horario1.add(spLunes5).setBounds(410+x,150,70,20);
		
		horario1.add(rbLunes).setBounds(505+x,150,18,18);
		horario1.add(rbLunes2).setBounds(575+x,150,18,18);
		horario1.add(rbLunes3).setBounds(645+x,150,18,18);


		horario1.add(spMartes5).setBounds(410+x,180,70,20);
		
		horario1.add(rbMartes).setBounds(505+x,180,18,18);
		horario1.add(rbMartes2).setBounds(575+x,180,18,18);
		horario1.add(rbMartes3).setBounds(645+x,180,18,18);
	

		horario1.add(spMiercoles5).setBounds(410+x,210,70,20);
		
		horario1.add(rbMiercoles).setBounds(505+x,210,18,18);
		horario1.add(rbMiercoles2).setBounds(575+x,210,18,18);
		horario1.add(rbMiercoles3).setBounds(645+x,210,18,18);

		horario1.add(spJueves4).setBounds(330+x,240,70,20);
		horario1.add(spJueves5).setBounds(410+x,240,70,20);
		
		horario1.add(rbJueves).setBounds(505+x,240,18,18);
		horario1.add(rbJueves2).setBounds(575+x,240,18,18);
		horario1.add(rbJueves3).setBounds(645+x,240,18,18);


		horario1.add(spViernes5).setBounds(410+x,270,70,20);
		
		horario1.add(rbViernes).setBounds(505+x,270,18,18);
		horario1.add(rbViernes2).setBounds(575+x,270,18,18);
		horario1.add(rbViernes3).setBounds(645+x,270,18,18);
		

		horario1.add(spSabado5).setBounds(410+x,300,70,20);
		
		horario1.add(rbSabado).setBounds(505+x,300,18,18);
		horario1.add(rbSabado2).setBounds(575+x,300,18,18);
		horario1.add(rbSabado3).setBounds(645+x,300,18,18);
		
		horario1.add(rbNoDobla).setBounds(505+x,340,18,18);
		horario1.add(rbNoDobla2).setBounds(575+x,340,18,18);
		horario1.add(rbNoDobla3).setBounds(645+x,340,18,18);
		
		horario1.add(new JLabel("Nombre:")).setBounds(20,35,50,20);
		horario1.add(txtNombre).setBounds(70,35,580,20);
		
		horario1.add(chbHorarioDeposito).setBounds(655,35,165,20);
		horario1.add(chbRecesoExtraDiario).setBounds(655,60,165,20);
		
		horario1.add(lblNoDobla).setBounds(720,340,70,20);
		
		horario1.add(lblMarcoDoblaExtra1).setBounds(675+x,105,100,120);
		horario1.add(new JLabel("Entrada:")).setBounds(685+x,115,80,20);
		horario1.add(txtEntradaExtra1).setBounds(685+x,130,80,20);
		horario1.add(new JLabel("Salida:")).setBounds(685+x,148,80,20);
		horario1.add(txtSalidaExtra1).setBounds(685+x,163,80,20);
		horario1.add(new JLabel("T Comida:")).setBounds(685+x,181,80,20);
		horario1.add(txtComida1).setBounds(685+x,196,80,20);
		
		horario1.add(lblMarcoDoblaExtra2).setBounds(675+x,222,100,120);
		horario1.add(new JLabel("Entrada:")).setBounds(685+x,233,80,20);
		horario1.add(txtEntradaExtra2).setBounds(685+x,251,80,20);
		horario1.add(new JLabel("salida:")).setBounds(685+x,266,80,20);
		horario1.add(txtSalidaExtra2).setBounds(685+x,281,80,20);
		horario1.add(new JLabel("T Comida:")).setBounds(685+x,299,80,20);
		horario1.add(txtComida2).setBounds(685+x,314,80,20);
		
		panel.add(paneles).setBounds(10,20,825,400);
		paneles.addTab("Horario 1", horario1);

		
		spDomingo1.setForeground(Color.blue);
		
		txtEntradaExtra1.setEditable(false);
		txtEntradaExtra2.setEditable(false);
		txtSalidaExtra1.setEditable(false);
		txtSalidaExtra2.setEditable(false);
		txtComida1.setEditable(false);
		txtComida2.setEditable(false);
		
		horasdefault();
		
		Obj_Configuracion_Del_Sistema configs2 = new Obj_Configuracion_Del_Sistema().buscar2();
		btnNuevo.setEnabled(configs2.isGuardar_horario());
		
ButtonGroup botonesAgrupados = new ButtonGroup();
		botonesAgrupados.add(btnDomingo);
		botonesAgrupados.add(btnLunes);
		botonesAgrupados.add(btnMartes);
		botonesAgrupados.add(btnMiercoles);
		botonesAgrupados.add(btnJueves);
		botonesAgrupados.add(btnViernes);
		botonesAgrupados.add(btnSabado);
		botonesAgrupados.add(btnSD);
		
ButtonGroup RBAgrupados = new ButtonGroup();
		RBAgrupados.add(rbDomingo);
		RBAgrupados.add(rbLunes);
		RBAgrupados.add(rbMartes);
		RBAgrupados.add(rbMiercoles);
		RBAgrupados.add(rbJueves);
		RBAgrupados.add(rbViernes);
		RBAgrupados.add(rbSabado);
		RBAgrupados.add(rbNoDobla);
		
ButtonGroup RBAgrupados2 = new ButtonGroup();
		RBAgrupados2.add(rbDomingo2);
		RBAgrupados2.add(rbLunes2);
		RBAgrupados2.add(rbMartes2);
		RBAgrupados2.add(rbMiercoles2);
		RBAgrupados2.add(rbJueves2);
		RBAgrupados2.add(rbViernes2);
		RBAgrupados2.add(rbSabado2);
		RBAgrupados2.add(rbNoDobla2);
		
ButtonGroup RBAgrupados3 = new ButtonGroup();
		RBAgrupados3.add(rbDomingo3);
		RBAgrupados3.add(rbLunes3);
		RBAgrupados3.add(rbMartes3);
		RBAgrupados3.add(rbMiercoles3);
		RBAgrupados3.add(rbJueves3);
		RBAgrupados3.add(rbViernes3);
		RBAgrupados3.add(rbSabado3);
		RBAgrupados3.add(rbNoDobla3);
		
		btnDomingo.addActionListener(Domingo);
		btnLunes.addActionListener(Lunes);
		btnMartes.addActionListener(Martes);
		btnMiercoles.addActionListener(Miercoles);
		btnJueves.addActionListener(Jueves);
		btnViernes.addActionListener(Viernes);
		btnSabado.addActionListener(Sabado);
		
		btnSD.addActionListener(SinDescanso);
		
		btnIgual.setToolTipText("Igualar todos los dias");
	
		btnReportes.addActionListener(Reportes);
		btnIgual.addActionListener(igualar);
		btnAceptar.addActionListener(Guardar);
		btnDeshacer.addActionListener(deshacer);
		btnFiltro.addActionListener(filtro);
		
		btnEditar.addActionListener(editar);
		
		chbHorarioDeposito.addActionListener(pintarchbHorarioDep);
		chbRecesoExtraDiario.addActionListener(pintarchbRecesoEx);
		
		btnNuevo.addActionListener(nuevo);
		txtNombre.addKeyListener(valida);
		
		txtFolio.addActionListener(buscarDirecto);
		cmbTurnoCuadrante.addActionListener(opHoraTurno);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
		        txtFolio.requestFocus();
		     }
		});
		
		cont.add(panel);
		btnNuevo.setEnabled(true);
//     abre el filtro de busqueda
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");        
        getRootPane().getActionMap().put("filtro", new AbstractAction(){     @Override
            public void actionPerformed(ActionEvent e){      	btnFiltro.doClick();  }   });
        
	}	

	public void preguntas(){
	     if(btnDomingo.isSelected()==true) { Descanso=7;}
	     if (btnLunes.isSelected()==true){Descanso=1;}
	     if (btnMartes.isSelected()==true){Descanso=2;}
	     if (btnMiercoles.isSelected()==true){Descanso=3;}
	     if (btnJueves.isSelected()==true){Descanso=4;}
	     if (btnViernes.isSelected()==true){Descanso=5;}
	     if (btnSabado.isSelected()==true){Descanso=6;}
	 }
	
	public Cat_Horarios()
	{
		pintarChb();
		getContenedor();
		camposbooleano(false);
		resestTextFieldDobladasExtras();
		
		btnAceptar.setEnabled(false);
		btnEditar.setEnabled(false);
	}
	
	@SuppressWarnings("deprecation")
	public Cat_Horarios(int folio)//String nom
	{
		getContenedor();
		camposbooleano(false);
		txtFolio.requestFocus();
		btnAceptar.setEnabled(false);
		
		CargarCajero();
		
		Obj_Horarios buscar_horario = new Obj_Horarios().buscar(folio);
		
		if(buscar_horario.getFolio() != 0){

			txtFolio.setText(buscar_horario.getFolio()+"");
			txtNombre.setText(buscar_horario.getNombre());
			
			txtEntradaExtra1.setText(buscar_horario.getEntrada_doblada_extra1());
			txtComida1.setText(buscar_horario.getComida_doblada_extra1());
			txtSalidaExtra1.setText(buscar_horario.getSalida_doblada_extra1());
			
			txtEntradaExtra2.setText(buscar_horario.getEntrada_doblada_extra2());
			txtComida2.setText(buscar_horario.getComida_doblada_extra2());
			txtSalidaExtra2.setText(buscar_horario.getSalida_doblada_extra2());
			
//			ASIGNAR AL BOTON SELECCIONADO Y OCULTAR SU CAMPOS DE DIA DE DESCANSO
			switch(buscar_horario.getDescanso()){
				case 7:	btnDomingo.setSelected(true);	 DomingoOculto();	break;
				case 1:	btnLunes.setSelected(true);		 LunesOculto();		break;
				case 2:	btnMartes.setSelected(true); 	 MartesOculto();	break;
				case 3:	btnMiercoles.setSelected(true);  MiercolesOculto();	break;
				case 4:	btnJueves.setSelected(true); 	 JuevesOculto();	break;
				case 5:	btnViernes.setSelected(true); 	 ViernesOculto();	break;
				case 6:	btnSabado.setSelected(true); 	 SabadoOculto();	break;
			}
			
			switch(buscar_horario.getDiaDobla()){
				case 0:	rbNoDobla.setSelected(true);									break;
				case 1:	rbLunes.setSelected(true);		rbNoDobla.setSelected(false);	break;
				case 2:	rbMartes.setSelected(true);		rbNoDobla.setSelected(false);	break;
				case 3:	rbMiercoles.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 4:	rbJueves.setSelected(true);		rbNoDobla.setSelected(false);	break;
				case 5: rbViernes.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 6:	rbSabado.setSelected(true);		rbNoDobla.setSelected(false);	break;
				case 7:	rbDomingo.setSelected(true);	rbNoDobla.setSelected(false);	break;
			}
			switch(buscar_horario.getDiaDobla2()){
				case 0:	rbNoDobla2.setSelected(true);									break;
				case 1:	rbLunes2.setSelected(true);		rbNoDobla.setSelected(false);	break;
				case 2:	rbMartes2.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 3:	rbMiercoles2.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 4:	rbJueves2.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 5: rbViernes2.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 6:	rbSabado2.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 7:	rbDomingo2.setSelected(true);	rbNoDobla.setSelected(false);	break;
			}
			switch(buscar_horario.getDiaDobla3()){
				case 0:	rbNoDobla3.setSelected(true);									break;
				case 1:	rbLunes3.setSelected(true);		rbNoDobla.setSelected(false);	break;
				case 2:	rbMartes3.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 3:	rbMiercoles3.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 4:	rbJueves3.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 5:	rbViernes3.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 6:	rbSabado3.setSelected(true);	rbNoDobla.setSelected(false);	break;
				case 7:	rbDomingo3.setSelected(true);	rbNoDobla.setSelected(false);	break;
			}	
			
		}
			
		if(buscar_horario.getRecesoDiarioExtra()==1){
				chbRecesoExtraDiario.setSelected(true);
				chbRecesoExtraDiario.setBackground(Color.ORANGE);
				chbRecesoExtraDiario.setForeground(Color.BLUE);
		
			}else{
				chbRecesoExtraDiario.setSelected(false);
				chbRecesoExtraDiario.setBackground(Color.GREEN);
				chbRecesoExtraDiario.setForeground(Color.BLACK);
				}
			
		if(buscar_horario.getHorarioDeposito()==1){
				chbHorarioDeposito.setSelected(true);
				chbHorarioDeposito.setBackground(Color.ORANGE);
				chbHorarioDeposito.setForeground(Color.BLUE);
			}else{
				chbHorarioDeposito.setSelected(false);
				chbHorarioDeposito.setBackground(Color.GREEN);
				chbHorarioDeposito.setForeground(Color.BLACK);
				}
			
//			DOMINGO
			String[] domingoIn = buscar_horario.getDomingo1().split(":");
			spDomingo1.setValue(new Time(Integer.parseInt(domingoIn[0]),Integer.parseInt(domingoIn[1]),Integer.parseInt(domingoIn[2])));
			
			String[] domingoFin = buscar_horario.getDomingo2().split(":");
			spDomingo2.setValue(new Time(Integer.parseInt(domingoFin[0]),Integer.parseInt(domingoFin[1]),Integer.parseInt(domingoFin[2])));
			
			String[] domingoEn = buscar_horario.getDomingo3().split(":");
			spDomingo3.setValue(new Time(Integer.parseInt(domingoEn[0]),Integer.parseInt(domingoEn[1]),Integer.parseInt(domingoEn[2])));
			
			String[] domingoSal = buscar_horario.getDomingo4().split(":");
			spDomingo4.setValue(new Time(Integer.parseInt(domingoSal[0]),Integer.parseInt(domingoSal[1]),Integer.parseInt(domingoSal[2])));
			
			String[] domingoRec = buscar_horario.getDomingo5().split(":");
			spDomingo5.setValue(new Time(Integer.parseInt(domingoRec[0]),Integer.parseInt(domingoRec[1]),Integer.parseInt(domingoRec[2])));
			
//			LUNES
			String[] lunesIn = buscar_horario.getLunes1().split(":");
			spLunes1.setValue(new Time(Integer.parseInt(lunesIn[0]),Integer.parseInt(lunesIn[1]),Integer.parseInt(lunesIn[2])));
			
			String[] lunesFin = buscar_horario.getLunes2().split(":");
			spLunes2.setValue(new Time(Integer.parseInt(lunesFin[0]),Integer.parseInt(lunesFin[1]),Integer.parseInt(lunesFin[2])));
			
			String[] lunesEn = buscar_horario.getLunes3().split(":");
			spLunes3.setValue(new Time(Integer.parseInt(lunesEn[0]),Integer.parseInt(lunesEn[1]),Integer.parseInt(lunesEn[2])));
			
			String[] lunesSal = buscar_horario.getLunes4().split(":");
			spLunes4.setValue(new Time(Integer.parseInt(lunesSal[0]),Integer.parseInt(lunesSal[1]),Integer.parseInt(lunesSal[2])));
			
			String[] lunesRec = buscar_horario.getLunes5().split(":");
			spLunes5.setValue(new Time(Integer.parseInt(lunesRec[0]),Integer.parseInt(lunesRec[1]),Integer.parseInt(lunesRec[2])));
			
//			MARTES
			String[] martesIn = buscar_horario.getMartes1().split(":");
			spMartes1.setValue(new Time(Integer.parseInt(martesIn[0]),Integer.parseInt(martesIn[1]),Integer.parseInt(martesIn[2])));
			
			String[] martesFin = buscar_horario.getMartes2().split(":");
			spMartes2.setValue(new Time(Integer.parseInt(martesFin[0]),Integer.parseInt(martesFin[1]),Integer.parseInt(martesFin[2])));
			
			String[] martesEn = buscar_horario.getMartes3().split(":");
			spMartes3.setValue(new Time(Integer.parseInt(martesEn[0]),Integer.parseInt(martesEn[1]),Integer.parseInt(martesEn[2])));
			
			String[] martesSal = buscar_horario.getMartes4().split(":");
			spMartes4.setValue(new Time(Integer.parseInt(martesSal[0]),Integer.parseInt(martesSal[1]),Integer.parseInt(martesSal[2])));
			
			String[] martesRec = buscar_horario.getMartes5().split(":");
			spMartes5.setValue(new Time(Integer.parseInt(martesRec[0]),Integer.parseInt(martesRec[1]),Integer.parseInt(martesRec[2])));
			
//			MIERCOLES
			String[] miercolesIn = buscar_horario.getMiercoles1().split(":");
			spMiercoles1.setValue(new Time(Integer.parseInt(miercolesIn[0]),Integer.parseInt(miercolesIn[1]),Integer.parseInt(miercolesIn[2])));
			
			String[] miercolesFin = buscar_horario.getMiercoles2().split(":");
			spMiercoles2.setValue(new Time(Integer.parseInt(miercolesFin[0]),Integer.parseInt(miercolesFin[1]),Integer.parseInt(miercolesFin[2])));
			
			String[] miercolesEn = buscar_horario.getMiercoles3().split(":");
			spMiercoles3.setValue(new Time(Integer.parseInt(miercolesEn[0]),Integer.parseInt(miercolesEn[1]),Integer.parseInt(miercolesEn[2])));
			
			String[] miercolesSal = buscar_horario.getMiercoles4().split(":");
			spMiercoles4.setValue(new Time(Integer.parseInt(miercolesSal[0]),Integer.parseInt(miercolesSal[1]),Integer.parseInt(miercolesSal[2])));
			
			String[] miercolesRec = buscar_horario.getMiercoles5().split(":");
			spMiercoles5.setValue(new Time(Integer.parseInt(miercolesRec[0]),Integer.parseInt(miercolesRec[1]),Integer.parseInt(miercolesRec[2])));
			
//			JUEVES
			String[] juevesIn = buscar_horario.getJueves1().split(":");
			spJueves1.setValue(new Time(Integer.parseInt(juevesIn[0]),Integer.parseInt(juevesIn[1]),Integer.parseInt(juevesIn[2])));
			
			String[] juevesFin = buscar_horario.getJueves2().split(":");
			spJueves2.setValue(new Time(Integer.parseInt(juevesFin[0]),Integer.parseInt(juevesFin[1]),Integer.parseInt(juevesFin[2])));
			
			String[] juevesEn = buscar_horario.getJueves3().split(":");
			spJueves3.setValue(new Time(Integer.parseInt(juevesEn[0]),Integer.parseInt(juevesEn[1]),Integer.parseInt(juevesEn[2])));
			
			String[] juevesSal = buscar_horario.getJueves4().split(":");
			spJueves4.setValue(new Time(Integer.parseInt(juevesSal[0]),Integer.parseInt(juevesSal[1]),Integer.parseInt(juevesSal[2])));
			
			String[] juevesRec = buscar_horario.getJueves5().split(":");
			spJueves5.setValue(new Time(Integer.parseInt(juevesRec[0]),Integer.parseInt(juevesRec[1]),Integer.parseInt(juevesRec[2])));
			
//			VIERNES
			String[] viernesIn = buscar_horario.getViernes1().split(":");
			spViernes1.setValue(new Time(Integer.parseInt(viernesIn[0]),Integer.parseInt(viernesIn[1]),Integer.parseInt(viernesIn[2])));
			
			String[] viernesFin = buscar_horario.getViernes2().split(":");
			spViernes2.setValue(new Time(Integer.parseInt(viernesFin[0]),Integer.parseInt(viernesFin[1]),Integer.parseInt(viernesFin[2])));
			
			String[] viernesEn = buscar_horario.getViernes3().split(":");
			spViernes3.setValue(new Time(Integer.parseInt(viernesEn[0]),Integer.parseInt(viernesEn[1]),Integer.parseInt(viernesEn[2])));
			
			String[] viernesSal = buscar_horario.getViernes4().split(":");
			spViernes4.setValue(new Time(Integer.parseInt(viernesSal[0]),Integer.parseInt(viernesSal[1]),Integer.parseInt(viernesSal[2])));
			
			String[] viernesRec = buscar_horario.getViernes5().split(":");
			spViernes5.setValue(new Time(Integer.parseInt(viernesRec[0]),Integer.parseInt(viernesRec[1]),Integer.parseInt(viernesRec[2])));
			
//			SABADO
			String[] sabadoIn = buscar_horario.getSabado1().split(":");
			spSabado1.setValue(new Time(Integer.parseInt(sabadoIn[0]),Integer.parseInt(sabadoIn[1]),Integer.parseInt(sabadoIn[2])));
			
			String[] sabadoFin = buscar_horario.getSabado2().split(":");
			spSabado2.setValue(new Time(Integer.parseInt(sabadoFin[0]),Integer.parseInt(sabadoFin[1]),Integer.parseInt(sabadoFin[2])));
			
			String[] sabadoEn = buscar_horario.getSabado3().split(":");
			spSabado3.setValue(new Time(Integer.parseInt(sabadoEn[0]),Integer.parseInt(sabadoEn[1]),Integer.parseInt(sabadoEn[2])));
			
			String[] sabadoSal = buscar_horario.getSabado4().split(":");
			spSabado4.setValue(new Time(Integer.parseInt(sabadoSal[0]),Integer.parseInt(sabadoSal[1]),Integer.parseInt(sabadoSal[2])));
			
			String[] sabadoRec = buscar_horario.getSabado5().split(":");
			spSabado5.setValue(new Time(Integer.parseInt(sabadoRec[0]),Integer.parseInt(sabadoRec[1]),Integer.parseInt(sabadoRec[2])));
		}
	


	@SuppressWarnings("unused")
	public void CargarCajero()
	{
		  File archivo = null;
 	      FileReader fr = null;
 	      BufferedReader br = null;
		 try {
 	         archivo = new File ("Config/users");
 	         fr = new FileReader (archivo);
 	         br = new BufferedReader(fr);
 	         String linea;
 	         
 	       boolean permiso_horario = new Obj_Horarios().Existe_permiso_horario(Integer.parseInt(br.readLine()));
 	       btnEditar.setEnabled(permiso_horario);

		 }
 	      catch(Exception e){
 	         e.printStackTrace();
 	      }finally{
 	         try{                   
 	            if( null != fr ){  
 	               fr.close();    
 	            }                 
 	         }catch (Exception e2){
 	            e2.printStackTrace();
 	         }
 	      }
	}
	
//buscar horario
	
	ActionListener buscarDirecto = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(txtFolio.getText().equals("")){
        		JOptionPane.showMessageDialog(null, "El folio es requerido","Aviso!", JOptionPane.INFORMATION_MESSAGE);
        		return;
        	}else{
            	dispose();
            	new Cat_Horarios(Integer.parseInt(txtFolio.getText())).setVisible(true);
            	txtFolio.setEnabled(false);
        	}
		}
	};
	
	ActionListener pintarchbRecesoEx = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			pintarChb();
		}
	};
	
	ActionListener pintarchbHorarioDep = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			pintarChb();
		}
	};
	
	public void pintarChb(){
		if(chbRecesoExtraDiario.isSelected()){
			chbRecesoExtraDiario.setBackground(Color.ORANGE);
			chbRecesoExtraDiario.setForeground(Color.BLUE);
		}else{
			chbRecesoExtraDiario.setBackground(Color.GREEN);
			chbRecesoExtraDiario.setForeground(Color.BLACK);
		}
		
		if(chbHorarioDeposito.isSelected()){
			chbHorarioDeposito.setBackground(Color.ORANGE);
			chbHorarioDeposito.setForeground(Color.BLUE);
		}else{
			chbHorarioDeposito.setBackground(Color.GREEN);
			chbHorarioDeposito.setForeground(Color.BLACK);
		}
	}
	
	ActionListener filtro = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			dispose();
			new Cat_Filtro_Horarios().setVisible(true);
		}
	};
	
	public void DomingoOculto()
	{
		spDomingo1.setVisible(false);
		spDomingo2.setVisible(false);
		spDomingo3.setVisible(false);
		spDomingo4.setVisible(false);
		spDomingo5.setVisible(false);
		
		if(rbDomingo.isSelected()){rbNoDobla.setSelected(true);}
		if(rbDomingo2.isSelected()){rbNoDobla2.setSelected(true);}
		if(rbDomingo3.isSelected()){rbNoDobla3.setSelected(true);}
		
		rbDomingo.setVisible(false);
		rbDomingo2.setVisible(false);
		rbDomingo3.setVisible(false);
	}
	
	public void DomingoVisible()
	{
		spDomingo1.setVisible(true);
		spDomingo2.setVisible(true);
		spDomingo3.setVisible(true);
		spDomingo4.setVisible(true);
		spDomingo5.setVisible(true);
		
		rbDomingo.setVisible(true);
		rbDomingo2.setVisible(true);
		rbDomingo3.setVisible(true);
	}
	
	public void LunesOculto()
	{
		spLunes1.setVisible(false);
		spLunes2.setVisible(false);
		spLunes3.setVisible(false);
		spLunes4.setVisible(false);
		spLunes5.setVisible(false);
		
		if(rbLunes.isSelected()){rbNoDobla.setSelected(true);}
		if(rbLunes2.isSelected()){rbNoDobla2.setSelected(true);}
		if(rbLunes3.isSelected()){rbNoDobla3.setSelected(true);}
		
		rbLunes.setVisible(false);
		rbLunes2.setVisible(false);
		rbLunes3.setVisible(false);
	}
	
	public void LunesVisible()
	{
		spLunes1.setVisible(true);
		spLunes2.setVisible(true);
		spLunes3.setVisible(true);
		spLunes4.setVisible(true);
		spLunes5.setVisible(true);
		
		rbLunes.setVisible(true);
		rbLunes2.setVisible(true);
		rbLunes3.setVisible(true);
	}
	
	public void MartesOculto()
	{
		spMartes1.setVisible(false);
		spMartes2.setVisible(false);
		spMartes3.setVisible(false);
		spMartes4.setVisible(false);
		spMartes5.setVisible(false);
		
		if(rbMartes.isSelected()){rbNoDobla.setSelected(true);}
		if(rbMartes2.isSelected()){rbNoDobla2.setSelected(true);}
		if(rbMartes3.isSelected()){rbNoDobla3.setSelected(true);}
		
		rbMartes.setVisible(false);
		rbMartes2.setVisible(false);
		rbMartes3.setVisible(false);
	}
	
	public void MartesVisible()
	{
		spMartes1.setVisible(true);
		spMartes2.setVisible(true);
		spMartes3.setVisible(true);
		spMartes4.setVisible(true);
		spMartes5.setVisible(true);
		
		rbMartes.setVisible(true);
		rbMartes2.setVisible(true);
		rbMartes3.setVisible(true);
	}
	
	public void MiercolesOculto()
	{
		spMiercoles1.setVisible(false);
		spMiercoles2.setVisible(false);
		spMiercoles3.setVisible(false);
		spMiercoles4.setVisible(false);
		spMiercoles5.setVisible(false);
		
		if(rbMiercoles.isSelected()){rbNoDobla.setSelected(true);}
		if(rbMiercoles2.isSelected()){rbNoDobla2.setSelected(true);}
		if(rbMiercoles3.isSelected()){rbNoDobla3.setSelected(true);}
		
		rbMiercoles.setVisible(false);
		rbMiercoles2.setVisible(false);
		rbMiercoles3.setVisible(false);
	}

	public void MiercolesVisible()
	{
		spMiercoles1.setVisible(true);
		spMiercoles2.setVisible(true);
		spMiercoles3.setVisible(true);
		spMiercoles4.setVisible(true);
		spMiercoles5.setVisible(true);
		
		rbMiercoles.setVisible(true);
		rbMiercoles2.setVisible(true);
		rbMiercoles3.setVisible(true);
	}
	
	public void JuevesOculto()
	{
		spJueves1.setVisible(false);
		spJueves2.setVisible(false);
		spJueves3.setVisible(false);
		spJueves4.setVisible(false);
		spJueves5.setVisible(false);
		
		if(rbJueves.isSelected()){rbNoDobla.setSelected(true);}
		if(rbJueves2.isSelected()){rbNoDobla2.setSelected(true);}
		if(rbJueves3.isSelected()){rbNoDobla3.setSelected(true);}
		
		rbJueves.setVisible(false);
		rbJueves2.setVisible(false);
		rbJueves3.setVisible(false);
	}
	
	public void JuevesVisible()
	{
		spJueves1.setVisible(true);
		spJueves2.setVisible(true);
		spJueves3.setVisible(true);
		spJueves4.setVisible(true);
		spJueves5.setVisible(true);
		
		rbJueves.setVisible(true);
		rbJueves2.setVisible(true);
		rbJueves3.setVisible(true);
	}
	
	public void ViernesOculto()
	{
		spViernes1.setVisible(false);
		spViernes2.setVisible(false);
		spViernes3.setVisible(false);
		spViernes4.setVisible(false);
		spViernes5.setVisible(false);
		
		if(rbViernes.isSelected()){rbNoDobla.setSelected(true);}
		if(rbViernes2.isSelected()){rbNoDobla2.setSelected(true);}
		if(rbViernes3.isSelected()){rbNoDobla3.setSelected(true);}
		
		rbViernes.setVisible(false);
		rbViernes2.setVisible(false);
		rbViernes3.setVisible(false);
	}
	
	public void ViernesVisible()
	{
		spViernes1.setVisible(true);
		spViernes2.setVisible(true);
		spViernes3.setVisible(true);
		spViernes4.setVisible(true);
		spViernes5.setVisible(true);
		
		rbViernes.setVisible(true);
		rbViernes2.setVisible(true);
		rbViernes3.setVisible(true);
	}
	
	public void SabadoOculto()	{
		spSabado1.setVisible(false);
		spSabado2.setVisible(false);
		spSabado3.setVisible(false);
		spSabado4.setVisible(false);
		spSabado5.setVisible(false);
		
		rbNoDobla.setSelected(true);
		rbNoDobla2.setSelected(true);
		rbNoDobla3.setSelected(true);
		
		if(rbSabado.isSelected()){rbNoDobla.setSelected(true);}
		if(rbSabado2.isSelected()){rbNoDobla2.setSelected(true);}
		if(rbSabado3.isSelected()){rbNoDobla3.setSelected(true);}
		
		rbSabado.setVisible(false);
		rbSabado2.setVisible(false);
		rbSabado3.setVisible(false);
	}
	
	public void SabadoVisible()	{
		spSabado1.setVisible(true);
		spSabado2.setVisible(true);
		spSabado3.setVisible(true);
		spSabado4.setVisible(true);
		spSabado5.setVisible(true);
		rbSabado.setVisible(true);
		rbSabado2.setVisible(true);
		rbSabado3.setVisible(true);
	}
	
	ActionListener deshacer = new ActionListener() {
		public void actionPerformed(ActionEvent arg0){
			horasdefault();
			resetear();
			camposbooleano(false);
			btnNuevo.setEnabled(true);
			txtFolio.setEditable(true);
			resestTextFieldDobladasExtras();
		}
	};
	
	public void resetear()
	{
		DomingoVisible();
		LunesVisible();
		MartesVisible();
		MiercolesVisible();
		JuevesVisible();
		ViernesVisible();
		SabadoVisible();
		
		txtFolio.setText("");
		txtNombre.setText("");
		
		rbNoDobla.setSelected(true);
		rbNoDobla2.setSelected(true);
		rbNoDobla3.setSelected(true);
		
		chbRecesoExtraDiario.setSelected(false);
		chbHorarioDeposito.setSelected(false);
		
		btnAceptar.setEnabled(false);
		btnEditar.setEnabled(false);
		
		txtFolio.requestFocus();
	}
	
	@SuppressWarnings("deprecation")
	public void horasdefault(){
		spDomingo1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spDomingo2.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		spDomingo3.setValue(new Time(Integer.parseInt(entradaDefault[0]),Integer.parseInt(entradaDefault[1]),Integer.parseInt(entradaDefault[2])));
		spDomingo4.setValue(new Time(Integer.parseInt(salidaDefault[0]),Integer.parseInt(salidaDefault[1]),Integer.parseInt(salidaDefault[2])));
		spDomingo5.setValue(new Time(Integer.parseInt(recesoDefault[0]),Integer.parseInt(recesoDefault[1]),Integer.parseInt(recesoDefault[2])));
		
		spLunes1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spLunes2.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		spLunes3.setValue(new Time(Integer.parseInt(entradaDefault[0]),Integer.parseInt(entradaDefault[1]),Integer.parseInt(entradaDefault[2])));
		spLunes4.setValue(new Time(Integer.parseInt(salidaDefault[0]),Integer.parseInt(salidaDefault[1]),Integer.parseInt(salidaDefault[2])));
		spLunes5.setValue(new Time(Integer.parseInt(recesoDefault[0]),Integer.parseInt(recesoDefault[1]),Integer.parseInt(recesoDefault[2])));
		
		spMartes1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spMartes2.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		spMartes3.setValue(new Time(Integer.parseInt(entradaDefault[0]),Integer.parseInt(entradaDefault[1]),Integer.parseInt(entradaDefault[2])));
		spMartes4.setValue(new Time(Integer.parseInt(salidaDefault[0]),Integer.parseInt(salidaDefault[1]),Integer.parseInt(salidaDefault[2])));
		spMartes5.setValue(new Time(Integer.parseInt(recesoDefault[0]),Integer.parseInt(recesoDefault[1]),Integer.parseInt(recesoDefault[2])));
		
		spMiercoles1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spMiercoles2.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		spMiercoles3.setValue(new Time(Integer.parseInt(entradaDefault[0]),Integer.parseInt(entradaDefault[1]),Integer.parseInt(entradaDefault[2])));
		spMiercoles4.setValue(new Time(Integer.parseInt(salidaDefault[0]),Integer.parseInt(salidaDefault[1]),Integer.parseInt(salidaDefault[2])));
		spMiercoles5.setValue(new Time(Integer.parseInt(recesoDefault[0]),Integer.parseInt(recesoDefault[1]),Integer.parseInt(recesoDefault[2])));
		
		spJueves1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spJueves2.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		spJueves3.setValue(new Time(Integer.parseInt(entradaDefault[0]),Integer.parseInt(entradaDefault[1]),Integer.parseInt(entradaDefault[2])));
		spJueves4.setValue(new Time(Integer.parseInt(salidaDefault[0]),Integer.parseInt(salidaDefault[1]),Integer.parseInt(salidaDefault[2])));
		spJueves5.setValue(new Time(Integer.parseInt(recesoDefault[0]),Integer.parseInt(recesoDefault[1]),Integer.parseInt(recesoDefault[2])));
		
		spViernes1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spViernes2.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		spViernes3.setValue(new Time(Integer.parseInt(entradaDefault[0]),Integer.parseInt(entradaDefault[1]),Integer.parseInt(entradaDefault[2])));
		spViernes4.setValue(new Time(Integer.parseInt(salidaDefault[0]),Integer.parseInt(salidaDefault[1]),Integer.parseInt(salidaDefault[2])));
		spViernes5.setValue(new Time(Integer.parseInt(recesoDefault[0]),Integer.parseInt(recesoDefault[1]),Integer.parseInt(recesoDefault[2])));
		
		spSabado1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spSabado2.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		spSabado3.setValue(new Time(Integer.parseInt(entradaDefault[0]),Integer.parseInt(entradaDefault[1]),Integer.parseInt(entradaDefault[2])));
		spSabado4.setValue(new Time(Integer.parseInt(salidaDefault[0]),Integer.parseInt(salidaDefault[1]),Integer.parseInt(salidaDefault[2])));
		spSabado5.setValue(new Time(Integer.parseInt(recesoDefault[0]),Integer.parseInt(recesoDefault[1]),Integer.parseInt(recesoDefault[2])));
	}
	
	ActionListener opHoraTurno = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
				Object[] tiempo = new Object[2];
				
				for(int i=0; i<turnosCuadrante.length; i++){
					if(turnosCuadrante[i][1].toString().trim().equals(cmbTurnoCuadrante.getSelectedItem().toString().trim())){
						tiempo[0]=turnosCuadrante[i][2].toString().trim();
						tiempo[1]=turnosCuadrante[i][3].toString().trim();
						i=turnosCuadrante.length;
					}
				}
				horasTurno(tiempo);
		}
	};
	
	@SuppressWarnings("deprecation")
	public void horasTurno(Object[] tiempo){
		System.out.println(tiempo[0].toString());
		System.out.println(tiempo[1].toString());
		String[] inicio =tiempo[0].toString().trim().split (":");
		String[] fin =tiempo[1].toString().trim().split (":");
		
		spDomingo1.setValue(new Time(Integer.parseInt(inicio[0]),Integer.parseInt(inicio[1]),Integer.parseInt(inicio[2])));
		spDomingo2.setValue(new Time(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]),Integer.parseInt(fin[2])));
		
		spLunes1.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spLunes2.setValue(new Time(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]),Integer.parseInt(fin[2])));
		
		spMartes1.setValue(new Time(Integer.parseInt(inicio[0]),Integer.parseInt(inicio[1]),Integer.parseInt(inicio[2])));
		spMartes2.setValue(new Time(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]),Integer.parseInt(fin[2])));
		
		spMiercoles1.setValue(new Time(Integer.parseInt(inicio[0]),Integer.parseInt(inicio[1]),Integer.parseInt(inicio[2])));
		spMiercoles2.setValue(new Time(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]),Integer.parseInt(fin[2])));
		
		spJueves1.setValue(new Time(Integer.parseInt(inicio[0]),Integer.parseInt(inicio[1]),Integer.parseInt(inicio[2])));
		spJueves2.setValue(new Time(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]),Integer.parseInt(fin[2])));
		
		spViernes1.setValue(new Time(Integer.parseInt(inicio[0]),Integer.parseInt(inicio[1]),Integer.parseInt(inicio[2])));
		spViernes2.setValue(new Time(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]),Integer.parseInt(fin[2])));
		
		spSabado1.setValue(new Time(Integer.parseInt(inicio[0]),Integer.parseInt(inicio[1]),Integer.parseInt(inicio[2])));
		spSabado2.setValue(new Time(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]),Integer.parseInt(fin[2])));
	}
	
	public void camposbooleano(boolean booleano ){
		txtNombre.setEditable(booleano);
		txtFolio.setEditable(false);
		
		btnDomingo.setEnabled(booleano);                         
		btnLunes.setEnabled(booleano);                           
		btnMartes.setEnabled(booleano);  
		btnMiercoles.setEnabled(booleano);           
		btnJueves.setEnabled(booleano);  
		btnViernes.setEnabled(booleano);  
		btnSabado.setEnabled(booleano);  
		btnSD.setEnabled(booleano);  
		
		rbDomingo.setEnabled(booleano);
		rbLunes.setEnabled(booleano);
		rbMartes.setEnabled(booleano);
		rbMiercoles.setEnabled(booleano);
		rbJueves.setEnabled(booleano);
		rbViernes.setEnabled(booleano);
		rbSabado.setEnabled(booleano);
		rbNoDobla.setEnabled(booleano);
		
		rbDomingo2.setEnabled(booleano);
		rbLunes2.setEnabled(booleano);
		rbMartes2.setEnabled(booleano);
		rbMiercoles2.setEnabled(booleano);
		rbJueves2.setEnabled(booleano);
		rbViernes2.setEnabled(booleano);
		rbSabado2.setEnabled(booleano);
		rbNoDobla2.setEnabled(booleano);
		
		rbDomingo3.setEnabled(booleano);
		rbLunes3.setEnabled(booleano);
		rbMartes3.setEnabled(booleano);
		rbMiercoles3.setEnabled(booleano);
		rbJueves3.setEnabled(booleano);
		rbViernes3.setEnabled(booleano);
		rbSabado3.setEnabled(booleano);
		rbNoDobla3.setEnabled(booleano);
		                                                                   
		btnIgual.setEnabled(booleano);
		btnAceptar.setEnabled(booleano);
		btnEditar.setEnabled(booleano);

		chbHorarioDeposito.setEnabled(booleano);
		chbRecesoExtraDiario.setEnabled(booleano);
		
		
		cmbTurnoCuadrante.setEnabled(booleano);
		
		spDomingo1.setEnabled(false);
		spDomingo2.setEnabled(false);
		spDomingo3.setEnabled(booleano);
		spDomingo4.setEnabled(booleano);
		spDomingo5.setEnabled(booleano);
		
		spLunes1.setEnabled(false);
		spLunes2.setEnabled(false);
		spLunes3.setEnabled(booleano);
		spLunes4.setEnabled(booleano);
		spLunes5.setEnabled(booleano);
		
		spMartes1.setEnabled(false);
		spMartes2.setEnabled(false);
		spMartes3.setEnabled(booleano);
		spMartes4.setEnabled(booleano);
		spMartes5.setEnabled(booleano);
		
		spMiercoles1.setEnabled(false);
		spMiercoles2.setEnabled(false);
		spMiercoles3.setEnabled(booleano);
		spMiercoles4.setEnabled(booleano);
		spMiercoles5.setEnabled(booleano);
		
		spJueves1.setEnabled(false);
		spJueves2.setEnabled(false);
		spJueves3.setEnabled(booleano);
		spJueves4.setEnabled(booleano);
		spJueves5.setEnabled(booleano);
		
		spViernes1.setEnabled(false);
		spViernes2.setEnabled(false);
		spViernes3.setEnabled(booleano);
		spViernes4.setEnabled(booleano);
		spViernes5.setEnabled(booleano);
		
		spSabado1.setEnabled(false);
		spSabado2.setEnabled(false);
		spSabado3.setEnabled(booleano);
		spSabado4.setEnabled(booleano);
		spSabado5.setEnabled(booleano);
	}
	
	public void resestTextFieldDobladasExtras(){
	txtEntradaExtra1.setText("00:00:00");
	txtComida1.setText("00:00:00");
	txtSalidaExtra1.setText("00:00:00");
	
	txtEntradaExtra2.setText("00:00:00");
	txtComida2.setText("00:00:00");
	txtSalidaExtra2.setText("00:00:00");
	}
	
	ActionListener Guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}else{			
				Obj_Horarios horario = new Obj_Horarios().buscar(Integer.parseInt(txtFolio.getText()));
				
				if(new Obj_Horarios().Existe(Integer.parseInt(txtFolio.getText())) == true){
					if(JOptionPane.showConfirmDialog(null, "El registro ya existe, �desea cambiarlo?") == 0){
						if(txtNombre.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "El Nombre es Requerido:", "Error al Actualizar el Registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
//							actualizar
							preguntas();
							SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
							
							//Domingo
							String Domingo_resultado1 = sdf.format ((Date) spDomingo1.getValue());
							String Domingo_resultado2 = sdf.format((Date) spDomingo2.getValue());
							String Domingo_resultado3 = sdf.format ((Date) spDomingo3.getValue());
							String Domingo_resultado4 = sdf.format ((Date) spDomingo4.getValue());
							String Domingo_resultado5 = sdf.format ((Date) spDomingo5.getValue());
							
							//Lunes
							String Lunes_resultado1 = sdf.format ((Date) spLunes1.getValue());
							String Lunes_resultado2 = sdf.format((Date) spLunes2.getValue());
							String Lunes_resultado3 = sdf.format ((Date) spLunes3.getValue());
							String Lunes_resultado4 = sdf.format ((Date) spLunes4.getValue());
							String Lunes_resultado5 = sdf.format ((Date) spLunes5.getValue());
							
							//Martes
							String Martes_resultado1 = sdf.format ((Date) spMartes1.getValue());
							String Martes_resultado2 = sdf.format((Date)spMartes2.getValue());
							String Martes_resultado3 = sdf.format ((Date) spMartes3.getValue());
							String Martes_resultado4 = sdf.format ((Date) spMartes4.getValue());
							String Martes_resultado5 = sdf.format ((Date) spMartes5.getValue());
							
							//Miercoles
							String Miercoles_resultado1 = sdf.format ((Date) spMiercoles1.getValue());
							String Miercoles_resultado2 = sdf.format ((Date) spMiercoles2.getValue());
							String Miercoles_resultado3 = sdf.format ((Date) spMiercoles3.getValue());
							String Miercoles_resultado4 = sdf.format ((Date) spMiercoles4.getValue());
							String Miercoles_resultado5 = sdf.format ((Date) spMiercoles5.getValue());
							
							//Jueves
							String Jueves_resultado1 = sdf.format ((Date) spJueves1.getValue());
							String Jueves_resultado2 = sdf.format ((Date) spJueves2.getValue());
							String Jueves_resultado3 = sdf.format ((Date) spJueves3.getValue());
							String Jueves_resultado4 = sdf.format ((Date) spJueves4.getValue());
							String Jueves_resultado5 = sdf.format ((Date) spJueves5.getValue());
							
							//Viernes
							String Viernes_resultado1 = sdf.format ((Date) spViernes1.getValue());
							String Viernes_resultado2 = sdf.format ((Date) spViernes2.getValue());
							String Viernes_resultado3 = sdf.format ((Date) spViernes3.getValue());
							String Viernes_resultado4 = sdf.format ((Date) spViernes4.getValue());
							String Viernes_resultado5 = sdf.format ((Date) spViernes5.getValue());
							
							//Sabado
							String Sabado_resultado1 = sdf.format ((Date) spSabado1.getValue());
							String Sabado_resultado2 = sdf.format ((Date) spSabado2.getValue());
							String Sabado_resultado3 = sdf.format ((Date) spSabado3.getValue());
							String Sabado_resultado4 = sdf.format ((Date) spSabado4.getValue());
							String Sabado_resultado5 = sdf.format ((Date) spSabado5.getValue());
							
							
							Obj_Horarios horario_emp = new Obj_Horarios();
							
								//Asignamos los datos
								horario_emp.setFolio(Integer.parseInt(txtFolio.getText()));
								horario_emp.setNombre(txtNombre.getText().toUpperCase());
								
								horario_emp.setDomingo1(Domingo_resultado1);
								horario_emp.setDomingo2(Domingo_resultado2);
								horario_emp.setDomingo3(Domingo_resultado3);
								horario_emp.setDomingo4(Domingo_resultado4);
								horario_emp.setDomingo5(Domingo_resultado5);
								
								horario_emp.setLunes1(Lunes_resultado1);
								horario_emp.setLunes2(Lunes_resultado2);
								horario_emp.setLunes3(Lunes_resultado3);
								horario_emp.setLunes4(Lunes_resultado4);
								horario_emp.setLunes5(Lunes_resultado5);
								
								horario_emp.setMartes1(Martes_resultado1);
								horario_emp.setMartes2(Martes_resultado2);
								horario_emp.setMartes3(Martes_resultado3);
								horario_emp.setMartes4(Martes_resultado4);
								horario_emp.setMartes5(Martes_resultado5);
								
								horario_emp.setMiercoles1(Miercoles_resultado1);
								horario_emp.setMiercoles2(Miercoles_resultado2);
								horario_emp.setMiercoles3(Miercoles_resultado3);
								horario_emp.setMiercoles4(Miercoles_resultado4);
								horario_emp.setMiercoles5(Miercoles_resultado5);
								
								horario_emp.setJueves1(Jueves_resultado1);
								horario_emp.setJueves2(Jueves_resultado2);
								horario_emp.setJueves3(Jueves_resultado3);
								horario_emp.setJueves4(Jueves_resultado4);
								horario_emp.setJueves5(Jueves_resultado5);
								
								horario_emp.setViernes1(Viernes_resultado1);
								horario_emp.setViernes2(Viernes_resultado2);
								horario_emp.setViernes3(Viernes_resultado3);
								horario_emp.setViernes4(Viernes_resultado4);
								horario_emp.setViernes5(Viernes_resultado5);
								
								horario_emp.setSabado1(Sabado_resultado1);
								horario_emp.setSabado2(Sabado_resultado2);
								horario_emp.setSabado3(Sabado_resultado3);
								horario_emp.setSabado4(Sabado_resultado4);
								horario_emp.setSabado5(Sabado_resultado5);
								
								horario_emp.setDescanso(Descanso);
								
//								manda al objeto el dia de la semana que dobla numerico 
									if(rbNoDobla.isSelected()==true){	horario_emp.setDiaDobla(0);		}
									if(rbLunes.isSelected()==true){		horario_emp.setDiaDobla(1);		}
									if(rbMartes.isSelected()==true){	horario_emp.setDiaDobla(2);		}
									if(rbMiercoles.isSelected()==true){	horario_emp.setDiaDobla(3);		}
									if(rbJueves.isSelected()==true){	horario_emp.setDiaDobla(4);		}
									if(rbViernes.isSelected()==true){	horario_emp.setDiaDobla(5);		}
									if(rbSabado.isSelected()==true){	horario_emp.setDiaDobla(6);		}
									if(rbDomingo.isSelected()==true){	horario_emp.setDiaDobla(7);		}
									
//								manda al objeto el diaExtra1 de la semana que dobla numerico 
									if(rbNoDobla2.isSelected()==true){	horario_emp.setDiaDobla2(0);		}
									if(rbLunes2.isSelected()==true){	horario_emp.setDiaDobla2(1);		}
									if(rbMartes2.isSelected()==true){	horario_emp.setDiaDobla2(2);		}
									if(rbMiercoles2.isSelected()==true){horario_emp.setDiaDobla2(3);		}
									if(rbJueves2.isSelected()==true){	horario_emp.setDiaDobla2(4);		}
									if(rbViernes2.isSelected()==true){	horario_emp.setDiaDobla2(5);		}
									if(rbSabado2.isSelected()==true){	horario_emp.setDiaDobla2(6);		}
									if(rbDomingo2.isSelected()==true){	horario_emp.setDiaDobla2(7);		}
										
//								manda al objeto el diaExtra2 de la semana que dobla numerico 
									if(rbNoDobla3.isSelected()==true){	horario_emp.setDiaDobla3(0);		}
									if(rbLunes3.isSelected()==true){	horario_emp.setDiaDobla3(1);		}
									if(rbMartes3.isSelected()==true){	horario_emp.setDiaDobla3(2);		}
									if(rbMiercoles3.isSelected()==true){horario_emp.setDiaDobla3(3);		}
									if(rbJueves3.isSelected()==true){	horario_emp.setDiaDobla3(4);		}
									if(rbViernes3.isSelected()==true){	horario_emp.setDiaDobla3(5);		}
									if(rbSabado3.isSelected()==true){	horario_emp.setDiaDobla3(6);		}
									if(rbDomingo3.isSelected()==true){	horario_emp.setDiaDobla3(7);		}
									
//								status extras
									if(chbHorarioDeposito.isSelected()==true){ horario_emp.setHorarioDeposito(1);}else{horario_emp.setHorarioDeposito(0);}
									if(chbRecesoExtraDiario.isSelected()==true){ horario_emp.setRecesoDiarioExtra(1);}else{horario_emp.setRecesoDiarioExtra(0);}
									
									if(rbDomingo.isSelected() && rbDomingo2.isSelected() || rbDomingo2.isSelected() && rbDomingo3.isSelected() || rbDomingo.isSelected() && rbDomingo3.isSelected()){
										JOptionPane.showMessageDialog(null, "El dia Domingo Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
												                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}
									if(rbLunes.isSelected() && rbLunes2.isSelected() || rbLunes2.isSelected() && rbLunes3.isSelected() || rbLunes.isSelected() && rbLunes3.isSelected()){
										JOptionPane.showMessageDialog(null, "El dia Lunes Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
												                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}
									if(rbMartes.isSelected() && rbMartes2.isSelected() || rbMartes2.isSelected() && rbMartes3.isSelected() || rbMartes.isSelected() && rbMartes3.isSelected()){
										JOptionPane.showMessageDialog(null, "El dia Martes Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
												                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}
									if(rbMiercoles.isSelected() && rbMiercoles2.isSelected() || rbMiercoles2.isSelected() && rbMiercoles3.isSelected() || rbMiercoles.isSelected() && rbMiercoles3.isSelected()){
										JOptionPane.showMessageDialog(null, "El dia Miercoles Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
												                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}
									if(rbJueves.isSelected() && rbJueves2.isSelected() || rbJueves2.isSelected() && rbJueves3.isSelected() || rbJueves.isSelected() && rbJueves3.isSelected()){
										JOptionPane.showMessageDialog(null, "El dia Jueves Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
												                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}
									if(rbViernes.isSelected() && rbViernes2.isSelected() || rbViernes2.isSelected() && rbViernes3.isSelected() || rbViernes.isSelected() && rbViernes3.isSelected()){
										JOptionPane.showMessageDialog(null, "El dia Viernes Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
												                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}
									if(rbSabado.isSelected() && rbSabado2.isSelected() || rbSabado2.isSelected() && rbSabado3.isSelected() || rbSabado.isSelected() && rbSabado3.isSelected()){
										JOptionPane.showMessageDialog(null, "El dia Sabado Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
												                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}else{
											if(horario_emp.Actualizar(Integer.parseInt(txtFolio.getText()))){
												camposbooleano(false);
												btnEditar.setEnabled(true);
												btnAceptar.setEnabled(false);
												JOptionPane.showMessageDialog(null, "El registro se Actualizo exitosamente!" , "Exito al Actualizar!", JOptionPane.INFORMATION_MESSAGE);
												return;
											}else{
												JOptionPane.showMessageDialog(null, "Error al tratar de Actualizar el registro", "Error al Actualizar registro", JOptionPane.WARNING_MESSAGE);
												return;
											}
									}
						}
					}
					
				}else{
//						guardar
					preguntas();
						if(txtNombre.getText().equals(""))
						{
								JOptionPane.showMessageDialog(null, "El Nombre es Requerido:", "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
								return;
						}else{
							SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
							
//							SimpleDateFormat sdf1 = new SimpleDateFormat ("E H:mm");
							
							//Domingo
							String Domingo_resultado1 = sdf.format ((Date) spDomingo1.getValue());
							String Domingo_resultado2 = sdf.format((Date) spDomingo2.getValue());
							String Domingo_resultado3 = sdf.format ((Date) spDomingo3.getValue());
							String Domingo_resultado4 = sdf.format ((Date) spDomingo4.getValue());
							String Domingo_resultado5 = sdf.format ((Date) spDomingo5.getValue());
							
							//Lunes
							String Lunes_resultado1 = sdf.format ((Date) spLunes1.getValue());
							String Lunes_resultado2 = sdf.format((Date) spLunes2.getValue());
							String Lunes_resultado3 = sdf.format ((Date) spLunes3.getValue());
							String Lunes_resultado4 = sdf.format ((Date) spLunes4.getValue());
							String Lunes_resultado5 = sdf.format ((Date) spLunes5.getValue());
							
							//Martes
							String Martes_resultado1 = sdf.format ((Date) spMartes1.getValue());
							String Martes_resultado2 = sdf.format((Date)spMartes2.getValue());
							String Martes_resultado3 = sdf.format ((Date) spMartes3.getValue());
							String Martes_resultado4 = sdf.format ((Date) spMartes4.getValue());
							String Martes_resultado5 = sdf.format ((Date) spMartes5.getValue());
							
							//Miercoles
							String Miercoles_resultado1 = sdf.format ((Date) spMiercoles1.getValue());
							String Miercoles_resultado2 = sdf.format ((Date) spMiercoles2.getValue());
							String Miercoles_resultado3 = sdf.format ((Date) spMiercoles3.getValue());
							String Miercoles_resultado4 = sdf.format ((Date) spMiercoles4.getValue());
							String Miercoles_resultado5 = sdf.format ((Date) spMiercoles5.getValue());
							
							//Jueves
							String Jueves_resultado1 = sdf.format ((Date) spJueves1.getValue());
							String Jueves_resultado2 = sdf.format ((Date) spJueves2.getValue());
							String Jueves_resultado3 = sdf.format ((Date) spJueves3.getValue());
							String Jueves_resultado4 = sdf.format ((Date) spJueves4.getValue());
							String Jueves_resultado5 = sdf.format ((Date) spJueves5.getValue());
							
							//Viernes
							String Viernes_resultado1 = sdf.format ((Date) spViernes1.getValue());
							String Viernes_resultado2 = sdf.format ((Date) spViernes2.getValue());
							String Viernes_resultado3 = sdf.format ((Date) spViernes3.getValue());
							String Viernes_resultado4 = sdf.format ((Date) spViernes4.getValue());
							String Viernes_resultado5 = sdf.format ((Date) spViernes5.getValue());
							
							//Sabado
							String Sabado_resultado1 = sdf.format ((Date) spSabado1.getValue());
							String Sabado_resultado2 = sdf.format ((Date) spSabado2.getValue());
							String Sabado_resultado3 = sdf.format ((Date) spSabado3.getValue());
							String Sabado_resultado4 = sdf.format ((Date) spSabado4.getValue());
							String Sabado_resultado5 = sdf.format ((Date) spSabado5.getValue());
							
							
							
//							ObjHorario horario_emp = new ObjHorario();
							horario.setNombre(txtNombre.getText().toUpperCase());
							
							//Asignamos los datos
							horario.setDomingo1(Domingo_resultado1);
							horario.setDomingo2(Domingo_resultado2);
							horario.setDomingo3(Domingo_resultado3);
							horario.setDomingo4(Domingo_resultado4);
							horario.setDomingo5(Domingo_resultado5);
							
							horario.setLunes1(Lunes_resultado1);
							horario.setLunes2(Lunes_resultado2);
							horario.setLunes3(Lunes_resultado3);
							horario.setLunes4(Lunes_resultado4);
							horario.setLunes5(Lunes_resultado5);
							
							horario.setMartes1(Martes_resultado1);
							horario.setMartes2(Martes_resultado2);
							horario.setMartes3(Martes_resultado3);
							horario.setMartes4(Martes_resultado4);
							horario.setMartes5(Martes_resultado5);
							
							horario.setMiercoles1(Miercoles_resultado1);
							horario.setMiercoles2(Miercoles_resultado2);
							horario.setMiercoles3(Miercoles_resultado3);
							horario.setMiercoles4(Miercoles_resultado4);
							horario.setMiercoles5(Miercoles_resultado5);
							
							horario.setJueves1(Jueves_resultado1);
							horario.setJueves2(Jueves_resultado2);
							horario.setJueves3(Jueves_resultado3);
							horario.setJueves4(Jueves_resultado4);
							horario.setJueves5(Jueves_resultado5);
							
							horario.setViernes1(Viernes_resultado1);
							horario.setViernes2(Viernes_resultado2);
							horario.setViernes3(Viernes_resultado3);
							horario.setViernes4(Viernes_resultado4);
							horario.setViernes5(Viernes_resultado5);
							
							horario.setSabado1(Sabado_resultado1);
							horario.setSabado2(Sabado_resultado2);
							horario.setSabado3(Sabado_resultado3);
							horario.setSabado4(Sabado_resultado4);
							horario.setSabado5(Sabado_resultado5);
							
							horario.setDescanso(Descanso);
							
//							manda al objeto el dia de la semana que dobla numerico 
								if(rbNoDobla.isSelected()==true){	horario.setDiaDobla(0);		}
								if(rbLunes.isSelected()==true){		horario.setDiaDobla(1);		}
								if(rbMartes.isSelected()==true){	horario.setDiaDobla(2);		}
								if(rbMiercoles.isSelected()==true){	horario.setDiaDobla(3);		}
								if(rbJueves.isSelected()==true){	horario.setDiaDobla(4);		}
								if(rbViernes.isSelected()==true){	horario.setDiaDobla(5);		}
								if(rbSabado.isSelected()==true){	horario.setDiaDobla(6);		}
								if(rbDomingo.isSelected()==true){	horario.setDiaDobla(7);		}
								
//							manda al objeto el diaExtra1 de la semana que dobla numerico 
								if(rbNoDobla2.isSelected()==true){	horario.setDiaDobla2(0);		}
								if(rbLunes2.isSelected()==true){	horario.setDiaDobla2(1);		}
								if(rbMartes2.isSelected()==true){	horario.setDiaDobla2(2);		}
								if(rbMiercoles2.isSelected()==true){horario.setDiaDobla2(3);		}
								if(rbJueves2.isSelected()==true){	horario.setDiaDobla2(4);		}
								if(rbViernes2.isSelected()==true){	horario.setDiaDobla2(5);		}
								if(rbSabado2.isSelected()==true){	horario.setDiaDobla2(6);		}
								if(rbDomingo2.isSelected()==true){	horario.setDiaDobla2(7);		}
									
//							manda al objeto el diaExtra2 de la semana que dobla numerico 
								if(rbNoDobla3.isSelected()==true){	horario.setDiaDobla3(0);		}
								if(rbLunes3.isSelected()==true){	horario.setDiaDobla3(1);		}
								if(rbMartes3.isSelected()==true){	horario.setDiaDobla3(2);		}
								if(rbMiercoles3.isSelected()==true){horario.setDiaDobla3(3);		}
								if(rbJueves3.isSelected()==true){	horario.setDiaDobla3(4);		}
								if(rbViernes3.isSelected()==true){	horario.setDiaDobla3(5);		}
								if(rbSabado3.isSelected()==true){	horario.setDiaDobla3(6);		}
								if(rbDomingo3.isSelected()==true){	horario.setDiaDobla3(7);		}
							
//							status extras
								if(chbHorarioDeposito.isSelected()==true){ horario.setHorarioDeposito(1);}else{horario.setHorarioDeposito(0);}
								if(chbRecesoExtraDiario.isSelected()==true){ horario.setRecesoDiarioExtra(1);}else{horario.setRecesoDiarioExtra(0);}
								
								if(rbDomingo.isSelected() && rbDomingo2.isSelected() || rbDomingo2.isSelected() && rbDomingo3.isSelected() || rbDomingo.isSelected() && rbDomingo3.isSelected()){
									JOptionPane.showMessageDialog(null, "El dia Domingo Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
											                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								if(rbLunes.isSelected() && rbLunes2.isSelected() || rbLunes2.isSelected() && rbLunes3.isSelected() || rbLunes.isSelected() && rbLunes3.isSelected()){
									JOptionPane.showMessageDialog(null, "El dia Lunes Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
											                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								if(rbMartes.isSelected() && rbMartes2.isSelected() || rbMartes2.isSelected() && rbMartes3.isSelected() || rbMartes.isSelected() && rbMartes3.isSelected()){
									JOptionPane.showMessageDialog(null, "El dia Martes Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
											                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								if(rbMiercoles.isSelected() && rbMiercoles2.isSelected() || rbMiercoles2.isSelected() && rbMiercoles3.isSelected() || rbMiercoles.isSelected() && rbMiercoles3.isSelected()){
									JOptionPane.showMessageDialog(null, "El dia Miercoles Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
											                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								if(rbJueves.isSelected() && rbJueves2.isSelected() || rbJueves2.isSelected() && rbJueves3.isSelected() || rbJueves.isSelected() && rbJueves3.isSelected()){
									JOptionPane.showMessageDialog(null, "El dia Jueves Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
											                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								if(rbViernes.isSelected() && rbViernes2.isSelected() || rbViernes2.isSelected() && rbViernes3.isSelected() || rbViernes.isSelected() && rbViernes3.isSelected()){
									JOptionPane.showMessageDialog(null, "El dia Viernes Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
											                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								if(rbSabado.isSelected() && rbSabado2.isSelected() || rbSabado2.isSelected() && rbSabado3.isSelected() || rbSabado.isSelected() && rbSabado3.isSelected()){
									JOptionPane.showMessageDialog(null, "El dia Sabado Tiene mas de un Dia Dobla seleccionado\n                        (selecciones solo uno)" ,
											                "Aviso!", JOptionPane.INFORMATION_MESSAGE);
									return;
								}else{
								
									if(horario.Guardar()){
										camposbooleano(false);
										btnEditar.setEnabled(true);
										btnAceptar.setEnabled(false);
										JOptionPane.showMessageDialog(null, "El registro se guard� exitosamente!" , "Exito al guardar!", JOptionPane.INFORMATION_MESSAGE);
										return;
									}else{
										JOptionPane.showMessageDialog(null, "Error al tratar de guardar el registro", "Error al guardar registro", JOptionPane.WARNING_MESSAGE);
										return;
									}
								}	
						}
					}
			}
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				try {
					Obj_Horarios horario = new Obj_Horarios().buscar_nuevo();
					
					if(horario.getFolio() >= 0){
						txtFolio.setText(horario.getFolio()+1+"");
						txtNombre.setText("");
						txtNombre.requestFocus();
						
						camposbooleano(true);
						txtFolio.setEditable(false);
						btnFiltro.setEnabled(true);
						btnEditar.setEnabled(true);
						
						chbHorarioDeposito.setSelected(false);
						chbRecesoExtraDiario.setSelected(false);
						btnEditar.setEnabled(false);
					}
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
	};
	
	ActionListener Domingo = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(btnDomingo.isSelected()){
				LunesVisible();
				MartesVisible();
				MiercolesVisible();
				JuevesVisible();
				ViernesVisible();
				SabadoVisible();
				DomingoOculto();
			}else{
				DomingoVisible();
				}
		}
	};
	
	ActionListener Lunes = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(btnLunes.isSelected()){
				LunesOculto();
				MartesVisible();
				MiercolesVisible();
				JuevesVisible();
				ViernesVisible();
				SabadoVisible();
				DomingoVisible();
			}else{
				LunesVisible();
				}
		}
	};
	
	ActionListener Martes = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(btnMartes.isSelected()){
				LunesVisible();
				MartesOculto();
				MiercolesVisible();
				JuevesVisible();
				ViernesVisible();
				SabadoVisible();
				DomingoVisible();
			}else{
				MartesVisible();
				}
		}
	};
	
	ActionListener Miercoles = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(btnMiercoles.isSelected()){
				LunesVisible();
				MartesVisible();
				MiercolesOculto();
				JuevesVisible();
				ViernesVisible();
				SabadoVisible();
				DomingoVisible();
			}else{
				MiercolesVisible();
				}
		}
	};
	
	ActionListener Jueves = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(btnJueves.isSelected()){
				LunesVisible();
				MartesVisible();
				MiercolesVisible();
				JuevesOculto();
				ViernesVisible();
				SabadoVisible();
				DomingoVisible();
			}else{
				JuevesVisible();
				}
		}
	};
	
	ActionListener Viernes = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(btnViernes.isSelected()){
				LunesVisible();
				MartesVisible();
				MiercolesVisible();
				JuevesVisible();
				ViernesOculto();
				SabadoVisible();
				DomingoVisible();
			}else{
				ViernesVisible();
				}
		}
	};
	
	ActionListener Sabado = new ActionListener() {
		public void actionPerformed(ActionEvent arg0){
			if(btnSabado.isSelected()){
				LunesVisible();
				MartesVisible();
				MiercolesVisible();
				JuevesVisible();
				ViernesVisible();
				SabadoOculto();
				DomingoVisible();
			}else{
				SabadoVisible();
				}
		}
	};
	
	ActionListener SinDescanso = new ActionListener() {
		public void actionPerformed(ActionEvent arg0){
			if(btnSD.isSelected()){
				LunesVisible();
				MartesVisible();
				MiercolesVisible();
				JuevesVisible();
				ViernesVisible();
				SabadoVisible();
				DomingoVisible();
			}
		}
	};
	
	ActionListener editar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0){
			camposbooleano(true);
			txtFolio.setEditable(false);
			btnFiltro.setEnabled(true);
			btnAceptar.setEnabled(true);
			btnEditar.setEnabled(false);
		}
	};
	
	ActionListener Reportes = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{			new Cat_Reportes_De_Horarios().setVisible(true);
		}
	};
	
	ActionListener igualar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
//			si el btnDomingo esta deseleccionado, iguala los demas campos a los valores del dia domingo
//			de lo contrario iguala a lunes
			if(btnDomingo.isSelected()!=true){
				//Igualamos todos los campos al primer campo
				spLunes1.setValue(spDomingo1.getValue());
				spMartes1.setValue(spDomingo1.getValue());
				spMiercoles1.setValue(spDomingo1.getValue());
				spJueves1.setValue(spDomingo1.getValue());
				spViernes1.setValue(spDomingo1.getValue());
				spSabado1.setValue(spDomingo1.getValue());
				
				//Igualamos todos los campos al primer campo
				spLunes2.setValue(spDomingo2.getValue());
				spMartes2.setValue(spDomingo2.getValue());
				spMiercoles2.setValue(spDomingo2.getValue());
				spJueves2.setValue(spDomingo2.getValue());
				spViernes2.setValue(spDomingo2.getValue());
				spSabado2.setValue(spDomingo2.getValue());
				
				//Igualamos todos los campos al primer campo
				spLunes3.setValue(spDomingo3.getValue());
				spMartes3.setValue(spDomingo3.getValue());
				spMiercoles3.setValue(spDomingo3.getValue());
				spJueves3.setValue(spDomingo3.getValue());
				spViernes3.setValue(spDomingo3.getValue());
				spSabado3.setValue(spDomingo3.getValue());
				
				//Igualamos todos los campos al primer campo
				spLunes4.setValue(spDomingo4.getValue());
				spMartes4.setValue(spDomingo4.getValue());
				spMiercoles4.setValue(spDomingo4.getValue());
				spJueves4.setValue(spDomingo4.getValue());
				spViernes4.setValue(spDomingo4.getValue());
				spSabado4.setValue(spDomingo4.getValue());
				
				//Igualamos todos los campos al primer campo
				spLunes5.setValue(spDomingo5.getValue());
				spMartes5.setValue(spDomingo5.getValue());
				spMiercoles5.setValue(spDomingo5.getValue());
				spJueves5.setValue(spDomingo5.getValue());
				spViernes5.setValue(spDomingo5.getValue());
				spSabado5.setValue(spDomingo5.getValue());
				
			}else{
				//Igualamos todos los campos al primer campo
				spDomingo1.setValue(spLunes1.getValue());
				spMartes1.setValue(spLunes1.getValue());
				spMiercoles1.setValue(spLunes1.getValue());
				spJueves1.setValue(spLunes1.getValue());
				spViernes1.setValue(spLunes1.getValue());
				spSabado1.setValue(spLunes1.getValue());
				
				//Igualamos todos los campos al primer campo
				spDomingo2.setValue(spLunes2.getValue());
				spMartes2.setValue(spLunes2.getValue());
				spMiercoles2.setValue(spLunes2.getValue());
				spJueves2.setValue(spLunes2.getValue());
				spViernes2.setValue(spLunes2.getValue());
				spSabado2.setValue(spLunes2.getValue());
				
				//Igualamos todos los campos al primer campo
				spDomingo3.setValue(spLunes3.getValue());
				spMartes3.setValue(spLunes3.getValue());
				spMiercoles3.setValue(spLunes3.getValue());
				spJueves3.setValue(spLunes3.getValue());
				spViernes3.setValue(spLunes3.getValue());
				spSabado3.setValue(spLunes3.getValue());
				
				//Igualamos todos los campos al primer campo
				spDomingo4.setValue(spLunes4.getValue());
				spMartes4.setValue(spLunes4.getValue());
				spMiercoles4.setValue(spLunes4.getValue());
				spJueves4.setValue(spLunes4.getValue());
				spViernes4.setValue(spLunes4.getValue());
				spSabado4.setValue(spLunes4.getValue());
				
				//Igualamos todos los campos al primer campo
				spDomingo5.setValue(spLunes5.getValue());
				spMartes5.setValue(spLunes5.getValue());
				spMiercoles5.setValue(spLunes5.getValue());
				spJueves5.setValue(spLunes5.getValue());
				spViernes5.setValue(spLunes5.getValue());
				spSabado5.setValue(spLunes5.getValue());
			}
		}
	};

	
	KeyListener valida = new KeyListener() {
		public void keyTyped(KeyEvent arg0){}
		public void keyReleased(KeyEvent arg0){}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnAceptar.doClick();
				txtNombre.requestFocus();
			}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Horarios().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
