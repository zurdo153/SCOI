package Cat_Chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import Obj_Principal.JCButton;

public class Cat_Chat extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JSplitPane  splitPaneComplete, splitPane;

	private JScrollPane scrollPaneAreaText;
	private JScrollPane scrollPaneMainArea;

	private JMenuItem itemChangeBackgroundColor2, itemChangeBackgroundColor3;
	private JMenuItem itemChangeForegroundColor3;	
	private JMenuItem itemListPrivate, itemClearAM;
	private JMenuItem itemChangeBackgroundColor1, itemBarExit;
	private JMenuItem itemDisconnect;
	private JMenuItem itemChangeForegroundColor2, itemConnect;
	private JMenuItem itemChangeForegroundColor1;
	
	private JCheckBoxMenuItem checkBoxMenuItemBlockEmotes;
	private JCheckBoxMenuItem checkBoxMenuItemBlockPrivateMessage;
	
	private JButton buttonFontChange   = new JCButton("Cambiar Fuente","font-icone-4850-32.png","Azul");;      
	private JButton buttonSelectColor  = new JCButton("Color"         ,"color-browser-icone-9428-32.png","Azul");
	private JButton buttonSendText     = new JCButton("Enviar"        ,"Aplicar.png","Verde");;
	private JButton buttonEmoticons    = new JCButton("Emoticones"    ,"Emoticones.png","Azul");
	private JToggleButton buttonBold   = new JToggleButton("B"); 

	private JMenu menuFile;
	private JMenu menuOption;
	private JMenu menuExtendsForegroundChoser;
	private JMenu menuExtendsColorChooser;
	
	private JPopupMenu popupMenuList;
	private JPopupMenu popupMenuMainArea;

	private Document     document;

	private Container glassPanelVert;

	private String fontString = "tahoma";
	private String styleFont  = "plain";
	private Color  color      = Color.BLACK;

	private JTextField       textPaneTextArea;
	private JTextPane        textPaneMainArea;

	private JLabel           labelStatus;
	private JTabbedPane      tabbedPane;
	private JTabbedPane      tabbedPane1;
	@SuppressWarnings("rawtypes")
	private JList            userList;
	private JMenuBar         menuBarMain;
	private JToolBar         toolBar;

	private ImageIcon        iconTabUser;
	private ImageIcon        iconConnect;
	/****************************************************************************************************************************/
	private final ControllerListerner             controllerListerner             = new ControllerListerner(this);
	private final ControllerKeyListerner          controllerKeyListerner          = new ControllerKeyListerner(this, controllerListerner);
	private final ControllerListSelectionListener controllerListSelectionListener = new ControllerListSelectionListener(this);
    /******************************************************************************/
	@SuppressWarnings("rawtypes")
	private DefaultListModel listModel;
	private Hashtable<String,PanelPrivateMessage> hashtableTaps = new Hashtable<String, PanelPrivateMessage>(); 
	private Object         toPv;
	private boolean        select;
	private int            indexTemp;
	private static PanelEmoticons panelEmoticons;
	private StyledDocument styledDocument;
	private StyleContext   context;
	private JLabel         labelUsersConnect;
	private int            nroUsers;
	private boolean        blockPrivateMessage = false;
	private boolean        blockEmotes = false;

	public boolean isBlockPrivateMessage() {
		return blockPrivateMessage;
	}
	public void setBlockPrivateMessage(boolean blockPrivateMessage) {
		this.blockPrivateMessage = blockPrivateMessage;
	}
	
	public boolean isBlockEmotes() {
		return blockEmotes;
	}
	public void setBlockEmotes(boolean blockEmotes) {
		this.blockEmotes = blockEmotes;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Chat() {
	    this.setSize(1024, 450);
	 	this.setLocationRelativeTo(new JFrame());
	 	this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	     final JRootPane rootPane =  this.getRootPane();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/google-talk-chat-icone-6146-32.png"));
		this.setTitle("Chat SCOI");
		
	    //Iconos
		final ImageIcon iconDisconnect = new ImageIcon(getClass().getResource(
				"/content/Icons/iconDisconnect.jpg"));
		
	    iconConnect = new ImageIcon(getClass().getResource(
				"/content/Icons/online.png"));
	    iconTabUser   = new ImageIcon("imagen/iconTabUsers.png");
	     
		listModel     = new DefaultListModel();
		userList      = new JList(listModel);
		
		 MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				
		        JList theList = (JList) mouseEvent.getSource();
		        
			        if (mouseEvent.getClickCount() == 2) {
			          int index = theList.locationToIndex(mouseEvent.getPoint());
			          
			          if (index >= 0) {
				            Object o = theList.getModel().getElementAt(index);
				            
				            controllerListerner.sendPV(String.valueOf(o));
			          }
		        }
		        if (mouseEvent.getClickCount() == 1) {
		        	
		        	int index = theList.locationToIndex(mouseEvent.getPoint());
		        	
		        	    indexTemp = index;
		        	    
		        }
		      }
		    };
		    
		userList .addMouseListener(mouseListener);
	    ListCellRenderer renderer = new IconListCellRenderer(iconConnect);
		renderer                  = new IconListCellRenderer(iconConnect);
		userList .setCellRenderer(renderer);
		glassPanelVert = (Container) rootPane.getGlassPane();
		
		popupMenuList              = new JPopupMenu("PopupLU");
		popupMenuMainArea          = new JPopupMenu("PoputAM");
		itemListPrivate            = new JMenuItem("Enviar mensaje privado");
		itemConnect                = new JMenuItem("Conectar");
		itemDisconnect             = new JMenuItem("Desconectar");
		itemClearAM                = new JMenuItem("Limpiar.");
		
		itemChangeBackgroundColor1 = new JMenuItem("[F]  Lista de usuarios");
		itemChangeBackgroundColor2 = new JMenuItem("[F]  Area de mensajes" );
		itemChangeBackgroundColor3 = new JMenuItem("[F]  Area de texto"    );
		itemChangeForegroundColor1 = new JMenuItem("[T]  Lista de usuarios");
		itemChangeForegroundColor2 = new JMenuItem("[T]  Area de mensajes" );
		itemChangeForegroundColor3 = new JMenuItem("[T]  Area de texto"    );

		itemBarExit = new JMenuItem("Salir");

		menuExtendsColorChooser     = new JMenu("Cambiar color de fondo");
		menuExtendsForegroundChoser = new JMenu("Cambiar color de texto");
		
		menuBarMain     = new JMenuBar();
		menuFile        = new JMenu("Archivo");
		menuOption      = new JMenu("Opciones");
		tabbedPane      = new JTabbedPane();
		
		itemDisconnect .setEnabled(false);
		itemListPrivate.setEnabled(false);
		//Paneles
		final JPanel panelList              = new JPanel(new GridLayout(1, 1));
		final JPanel panelArea              = new JPanel(new BorderLayout());
		final JPanel panelSendClear         = new JPanel(new BorderLayout());
		final JPanel panelAreaMensages      = new JPanel(new BorderLayout());
		final JPanel panelPrivate           = new JPanel(new BorderLayout());
		final JPanel panelTemp              = new JPanel(new BorderLayout());
		final JPanel panelToolbar           = new JPanel(new GridLayout(1, 1));
		final JPanel panelsouth             = new JPanel(new BorderLayout());

		popupMenuList      .add(itemListPrivate);
		popupMenuList      .add(new JSeparator());
		
		popupMenuMainArea  .add(itemClearAM);

		userList                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList                .setSize(0, 100);
		userList                .setToolTipText("Lista de Usuarios");
		userList                .setComponentPopupMenu(popupMenuList);
		userList                .addListSelectionListener(controllerListSelectionListener);
		menuOption              .add(new JSeparator());

		menuExtendsColorChooser     .add(itemChangeBackgroundColor1);
		menuExtendsColorChooser     .add(itemChangeBackgroundColor2);
		menuExtendsColorChooser     .add(itemChangeBackgroundColor3);
		menuExtendsForegroundChoser .add(itemChangeForegroundColor1);
		menuExtendsForegroundChoser .add(itemChangeForegroundColor2);
		menuExtendsForegroundChoser .add(itemChangeForegroundColor3);
		
        checkBoxMenuItemBlockPrivateMessage = new JCheckBoxMenuItem("Bloquear todos los mensajes privados");
        checkBoxMenuItemBlockEmotes         = new JCheckBoxMenuItem("Bloquear emoticonos");
        menuOption.add(checkBoxMenuItemBlockPrivateMessage);
        menuOption.add(checkBoxMenuItemBlockEmotes);
		menuOption  .add(new JSeparator());
		menuOption  .add(menuExtendsColorChooser);
		menuOption  .add(menuExtendsForegroundChoser);
		
		menuFile    .add(itemConnect);
		menuFile    .add(itemDisconnect);
		menuFile    .add(new JSeparator());
		menuFile    .add(itemBarExit);
		menuBarMain .add(menuFile);
		menuBarMain .add(menuOption);
		this.setJMenuBar(menuBarMain);

		final Border border = BorderFactory.createEtchedBorder();
		panelList.setBorder(border);
		final JScrollPane scrollPaneListUser = new JScrollPane(userList);
		
		scrollPaneListUser.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneListUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		tabbedPane.addTab("Lista de usuarios", scrollPaneListUser);
		tabbedPane.setBackground(Color.white);
		panelList .add(tabbedPane);

		splitPaneComplete = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
		splitPaneComplete   .setDividerSize(20);
		splitPaneComplete   .setDividerLocation(793);
		splitPaneComplete   .setContinuousLayout(true);
		splitPaneComplete   .setOneTouchExpandable(true);
		splitPane           .setDividerLocation(270);//170
		context          = new StyleContext();
		styledDocument   = new DefaultStyledDocument(context);
		textPaneMainArea = new JTextPane(styledDocument);
		
		textPaneTextArea = new JTextField();
	    document         = textPaneTextArea.getDocument();

		scrollPaneMainArea = new JScrollPane(textPaneMainArea);
		scrollPaneAreaText = new JScrollPane(textPaneTextArea);
		
		scrollPaneAreaText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneAreaText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       
		toolBar  = new JToolBar("");
		toolBar .setFloatable(true);
		toolBar .setRollover(true);

		scrollPaneMainArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		itemDisconnect    .setIcon(iconDisconnect);
		itemBarExit       .setBackground(Color.LIGHT_GRAY);
		
		itemConnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.ALT_MASK | InputEvent.CTRL_MASK));
		itemBarExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		
		buttonEmoticons   .setFocusCycleRoot(false);
		buttonEmoticons   .setFocusable(false);
		buttonSelectColor .setFocusable(false);
		
		labelStatus    = new JLabel("   Estatus | Desconectado");
		labelStatus   .setForeground(Color.GRAY);
		labelStatus   .setHorizontalAlignment(SwingConstants.LEFT);

		buttonFontChange  .setFocusable(false);
		buttonSendText    .setFocusable(false);
	
		this.toolBar.add(buttonBold);
		this.toolBar.add(buttonSelectColor);
		this.toolBar.add(buttonFontChange);
		this.toolBar.add(buttonEmoticons);
		this.toolBar.add(glassPanelVert);
		this.toolBar.setFloatable(false);
		this.toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		panelToolbar.setLayout(new GridLayout(1, 1));
		panelToolbar.add(toolBar);

		buttonFontChange     .setPreferredSize(new Dimension(140, 35));
		buttonEmoticons      .setPreferredSize(new Dimension(140, 35));
		buttonSelectColor    .setPreferredSize(new Dimension(100, 35));
		buttonSelectColor    .setBorder(BorderFactory.createEtchedBorder());
		buttonBold           .setPreferredSize(new Dimension(40, 35));
		buttonBold           .setBorder(BorderFactory.createEtchedBorder());
		buttonBold           .setFont(new Font("Black", 1, 15));
		buttonBold           .setFocusable(false);
		buttonSendText       .setEnabled(false);

		panelSendClear.add(buttonSendText, BorderLayout.NORTH);

		panelArea.add(panelToolbar, BorderLayout.NORTH);
		panelArea.add(scrollPaneAreaText, BorderLayout.CENTER);
		panelArea.add(panelSendClear, BorderLayout.EAST);

		panelPrivate.setBorder(BorderFactory.createEtchedBorder());
		panelTemp.add(scrollPaneMainArea, BorderLayout.CENTER);

		textPaneMainArea  .setEditable(false);
		textPaneMainArea  .setComponentPopupMenu(popupMenuMainArea);
		textPaneMainArea  .setLayout(new FlowLayout());
		textPaneMainArea  .setToolTipText("Ares de Mensajes");
		textPaneMainArea  .setFont(new Font("Tahoma", 0, 12));
		textPaneMainArea.setBackground(Color.WHITE);
		textPaneTextArea.setBackground(Color.WHITE);
	    panelEmoticons = new PanelEmoticons(this);
		final ControlleMainDocumentListener controlleMainDocumentListener = 
			new ControlleMainDocumentListener(this,textPaneMainArea,styledDocument,panelEmoticons);
			styledDocument.addDocumentListener(controlleMainDocumentListener);
			
		panelPrivate.add(panelEmoticons,BorderLayout.CENTER);
		glassPanelVert.add(panelPrivate);
		glassPanelVert.setLayout(new GridLayout(1, 1));
		glassPanelVert.setFocusable(false);
		tabbedPane1 = new JTabbedPane();
		tabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane1.setFocusable(false);
		
		//listerner que escucha las pesta�as seleccionadas
		tabbedPane1.addChangeListener(new ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				changeListerner(evt);
			};
		});
		panelAreaMensages.add(new JLabel(" | Area de Mensajes |"),BorderLayout.NORTH);
		panelAreaMensages.add(panelTemp, BorderLayout.CENTER);
		tabbedPane1      .addTab("Principal ",null,panelAreaMensages,"Area principal");

		splitPane  .setTopComponent(tabbedPane1);
		splitPane  .setBottomComponent(panelArea);
		splitPane  .setAutoscrolls(false);
	
		splitPaneComplete  .setLeftComponent(splitPane);
		splitPaneComplete  .setRightComponent(panelList);

		panelsouth.add(labelStatus,BorderLayout.WEST);
		labelUsersConnect = new JLabel("Usuarios conectados: "+nroUsers+"   ");
		panelsouth.add(labelUsersConnect,BorderLayout.EAST);
		
		
		
		final Container container = this.getContentPane();
		container.add(splitPaneComplete, BorderLayout.CENTER);
		container.add(panelsouth, BorderLayout.SOUTH);
		
		 //Registrar los Componentes al listerner	 
		buttonSendText    .addActionListener(controllerListerner);
		buttonFontChange  .addActionListener(controllerListerner);
		buttonEmoticons   .addActionListener(controllerListerner);
		buttonSelectColor .addActionListener(controllerListerner);
		itemListPrivate   .addActionListener(controllerListerner);

		itemBarExit       .addActionListener(controllerListerner);
		itemClearAM       .addActionListener(controllerListerner);
		itemConnect       .addActionListener(controllerListerner);
		itemDisconnect    .addActionListener(controllerListerner);

		itemChangeBackgroundColor1.addActionListener(controllerListerner);
		itemChangeBackgroundColor2.addActionListener(controllerListerner);
		itemChangeBackgroundColor3.addActionListener(controllerListerner);
		itemChangeForegroundColor1.addActionListener(controllerListerner);
		itemChangeForegroundColor2.addActionListener(controllerListerner);
		itemChangeForegroundColor3.addActionListener(controllerListerner);
		buttonBold                .addActionListener(controllerListerner);
		  checkBoxMenuItemBlockPrivateMessage.addItemListener(new ItemListener() {
				@Override
					public void itemStateChanged(ItemEvent e) {
						   int state = e.getStateChange();
						   if (state == ItemEvent.SELECTED) {
							   blockPrivateMessage = true;
						       }
						   else {
							   blockPrivateMessage = false;}
					}
				});
		  checkBoxMenuItemBlockEmotes.addItemListener(new ItemListener() {
				@Override
					public void itemStateChanged(ItemEvent e) {
						   int state = e.getStateChange();
						   if (state == ItemEvent.SELECTED) {
							   blockEmotes = true;
						       }
						   else {
							   blockEmotes = false;}
					}
				});
		      
		textPaneTextArea.addKeyListener(controllerKeyListerner);

		}
	public int getNroUsers() {
		
		return nroUsers;
	}
	public void setNroUsers(int nroUsers) {
		
		this.nroUsers = nroUsers;
	}
	/***
	 * @Metodo Actualiza la lista de usuarios conectados
	 **/
	public void  updateLabelNroUsers(){
		
		labelUsersConnect.setText("Usuarios conectados:  "+nroUsers+"   ");
     }
	/***
	 * @ChangeListerner TabbedPane
	 **/
	public void changeListerner(ChangeEvent evt) {
		String tap  = null;
		toPv        = null;
		select      = false;
		
		JTabbedPane sourceTabbedPane = (JTabbedPane) evt.getSource();
		
			int index = sourceTabbedPane.getSelectedIndex();
			
				tap = sourceTabbedPane.getTitleAt(index);
				
				if(tap.indexOf("Pv")!= -1){
				
					 toPv=tap.substring(4);	 
					 select = true;
			}
	}
	/***
	 * @Metodo Recupera el focus de la lista de usuarios
	 **/
	public void UDUserlist(){
		userList.setSelectedIndex(indexTemp);
	}
	public Hashtable<String, PanelPrivateMessage> getHashtableTaps() {
		return hashtableTaps;
	}

	public void setHashtableTaps(
			Hashtable<String, PanelPrivateMessage> hashtableTaps) {
		this.hashtableTaps = hashtableTaps;
	}
	//@Metodo que retorna verdadero si selecciono una pesta�a
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}

	public Object getToPv() {
		return toPv.toString();
	}

	public void setToPv(Object toPv) {
		this.toPv = toPv;
	}

	//@Metodos de cambio de color de fondo y fuente
	public void newBackgroundListUser(Color Background) {
		userList.setBackground(Background);
	}

	public void newBackgroundAreaMessages(Color Background) {
		textPaneMainArea.setBackground(Background);
	}

	public void newBackgroundAreaText(Color Background) {
		textPaneTextArea.setBackground(Background);
	}

	public void newForegroundListUser(Color Foreground) {
		userList.setForeground(Foreground);
	}

	public void newForegroundAreaMessages(Color Foreground) {
		textPaneMainArea.setForeground(Foreground);
	}

	public void newForegroundAreaText(Color Foreground) {
		textPaneTextArea.setForeground(Foreground);
	}

	/***
	 * @Metodo Agrega un nombre de usuarios a la lista
	 **/
	@SuppressWarnings("unchecked")
	public void addUser(String user) {
		
		listModel.addElement(user);
	}
	/***
	 * @Metodo Cambia el estatus
	 **/
	public void showStatus(String status) {
		
		labelStatus.setForeground(new Color(60, 100, 40, 140));
		
		labelStatus.setText(status);
	}
	public void insertString(String string){
		try {
			
			document.insertString(textPaneTextArea.getText().length(),string, null);
		} catch (BadLocationException e) {
		
			e.printStackTrace();
		}
	}
	/***
	 * @Metodo Hace visible el panel para emotins
	 **/
	public void setVisibleGlassPanelVert(boolean tree) {
		
		if (tree == true)
			glassPanelVert.setVisible(true);
		else
			glassPanelVert.setVisible(false);
	}

	public String getSelectedValueList() {
		
		return controllerListSelectionListener.getSelectedValue().toString();
	}

	public int getColor() {
		return color.getRGB();
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getStyleFont() {
		return styleFont;
	}

	public void setStyleFont(String style) {
		this.styleFont = style;
	}

	public void setEnableItemDisconnect() {
		itemDisconnect.setEnabled(true);
		
		itemConnect.setEnabled(false);
	}

	/***
	 * @Metodo verifica si se presiono o no el style bold
	 **/
	public void setButtonBoldPressed(boolean afirm) {
		if (afirm) {
			setStyleFont("bold");
			
			buttonBold.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			
			textPaneTextArea.setFont(new Font(getFontString(), 1, 14));
		}
		if (!afirm) {
			setStyleFont("plain");
			
			buttonBold.setBorder(BorderFactory.createEtchedBorder());
			
			textPaneTextArea.setFont(new Font(getFontString(), 0, 14));
		}
	}

	public String getTextArea() {
		return textPaneTextArea.getText();
	}

	public void setEnableButtonSendText() {
		buttonSendText.setEnabled(true);
	}

	public String getFontString() {
		return fontString;
	}

	public void setFontString(String font) {
		textPaneTextArea.setFont(new Font(font, 0, 14));
		
		this.fontString = font;
	}

	public void setFontArea(String font) {
		textPaneTextArea.updateUI();
		
		textPaneTextArea.setFont(new Font(font, 0, 14));
	}

	public void setEnableItemListPrivate() {
		itemListPrivate.setEnabled(true);
	
	}

	public void setForegroundAreaText(final Color newForeground) {
		textPaneTextArea.setForeground(newForeground);
	}
	
	public void showMessage(String text, Object styleConstants, int b,int color) {

		final String newline = "\n";
		final StringTokenizer tokenizer = new StringTokenizer(text, ":");
		final String username = tokenizer.nextToken();
		final String message = tokenizer.nextToken();
		
		LexicoAnalyzer anLex = new LexicoAnalyzer(message);
		        
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		
		SimpleAttributeSet attributesU = new SimpleAttributeSet();

	    SimpleAttributeSet attributeSetB = new SimpleAttributeSet();

		StyleConstants.setFontFamily(attributesU, "Tahoma");
		
		StyleConstants.setFontFamily(attributes, styleConstants.toString());
		
		attributes.addAttribute(StyleConstants.CharacterConstants.Foreground,new Color(color));
		
		if (b == 1) {
			attributeSetB = new SimpleAttributeSet();
			
			attributes.addAttribute(StyleConstants.CharacterConstants.Bold,Boolean.TRUE);
			
			textPaneTextArea.setFont(new Font(getStyleFont(), 1, 14));
		}
		if (b == 0) {
			attributeSetB = new SimpleAttributeSet();
			
			attributeSetB.addAttribute("Plain", Boolean.TRUE);
			
			textPaneTextArea.setFont(new Font(getStyleFont(), 0, 14));
		}
		try {
			styledDocument.insertString(styledDocument.getLength(), username + ":", attributesU);
			
			String    token;
			String    ruta;
			ImageIcon img;
						
			while( anLex.hasToken() ){
				
				token = anLex.nextToken();
								
				if( Emoticons.esEmoticon(token) ){
					
					ruta = Emoticons.emoticonos.get(token);
					
					img  = new ImageIcon(System.getProperty("user.dir")+ruta);			
					if(!blockEmotes)
					insertEmoticon(img, token);
				}
				else
					styledDocument.insertString(styledDocument.getLength(), token, attributes);
				
			}			
	        styledDocument.insertString(styledDocument.getLength(), "" + newline, attributeSetB);
			
		} catch (BadLocationException ble) {
			System.err.println("Couldn't insert" + "text.");
		} catch (Exception e) {
			e.printStackTrace();
JOptionPane.showMessageDialog(this, "OK","::",JOptionPane.INFORMATION_MESSAGE);
		}
	}
		
	public void requestFocusText(){
		textPaneTextArea.requestFocus();
	}
	/***
	 * @Metodo elimina todos los usuarios
	 **/
	public void removeAllUsers() {
    listModel.removeAllElements();
	}	
	/***
	 * @Metodo limpia el area de texto
	 **/
	public void clearAreaText() {
		try {
			textPaneTextArea.setText("");		
			
	        textPaneTextArea.requestFocus();
	        
			textPaneTextArea.updateUI();
			
		} catch (Exception e) {
			content.Util.showException(this.getName(), "Error al limpiar el area de texto",e.toString());
		}
	}
	/***
	 * @Metodo Limpia el area de mensajes principal
	 **/
	public void clearAreaMessages() throws BadLocationException {
		
		textPaneMainArea.setText("");
	}
	//@Metodo que retorna el nro de pesta�as activas
	public int getNroTaps(){
		
		return tabbedPane1.getTabCount();
	}
	/***
	 * @Metodo Elimina una pesta�a
	 **/
	public void removeTap(PanelPrivateMessage component){
		tabbedPane1.remove(component);
	}
	/***
	 * @Metodo Elimina un panel de hash
	 **/
	public void removeToHashTable(String to){
		hashtableTaps.remove(to);
	}
	
	public void disableTapIcon(int index){
		tabbedPane1.setIconAt(index, null);
	}
	/***
	 * @Metodo Selecciona una pesta�a por su indice
	 **/
	public void selecctTap(int index){
		tabbedPane1.setSelectedIndex(index);
	}
	/***
	 * @Metodo chekea si existe una pesta�a
	 **/
	public boolean checkTap(String tap){ 
		boolean fount = false;
		
		//recorre las pesta�as
		for(int index =1;index<getNroTaps();index++){	 
			
		//extraemos el nombre de usuario pv: user
		String n=tabbedPane1.getTitleAt(index).substring(4).trim();	 
		
		// si existe la pesta�a
			if(n.equalsIgnoreCase(tap)){		
				
				//establecemos a verdadero
				fount = true;
//				selecctTap(index);
			   }
		}
		return fount;
	}
	
	public void init() {
		this.setVisible(true);
	}
	/***
	 * @Metodo para agregar una nueva pesta�a
	 **/
	public void addTap(String username,String to){
				
		//creamos un objeto de panel privado
		PanelPrivateMessage privateMessage =new PanelPrivateMessage(username.trim(),to,this,controllerListerner);
		//agregamos al hash table el objeto
				
		hashtableTaps.put(to.trim(),privateMessage);
		// creamos la pestania
	    tabbedPane1.addTab("Pv: "+to.trim(),iconTabUser,privateMessage);
	    
	    tabbedPane1.setSelectedIndex(tabbedPane1.getTabCount() - 1);
	    
		clearAreaText();
	}
	/***
	 * @Metodo Inserta un emoticon al area principal
	 **/
	public void insertEmoticon(final ImageIcon ic, final String atajo){
	    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
	    
	    Icon icon = ic;
	    
	    JLabel label = new JLabel(icon);
	    
	    StyleConstants.setComponent(labelStyle, label);
	    
	    try {
	      styledDocument.insertString(styledDocument.getLength(), atajo, labelStyle);
	      
	    } catch (BadLocationException badLocationException) {
	      System.err.println("Oops");
	    }		
    }
	public JButton getJBEnviar(){ return buttonSendText; }
	
	public void sendMessage(ConnectorClient connectorClient, String message){
	String msj;
		
		if( message.equals("\n") )
			msj = "";
		else
			msj = content.Util.removeNewLine(message.getBytes());
	
		if( !msj.equals("") ){
			new ThreadMainWriter(
					connectorClient.connector,
					   content.Util.removeNewLine(message.getBytes()),
					       connectorClient.getUsername(),
					          this);
		}
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); 
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Chat().setVisible(true);
		}catch(Exception e){}
	}
	
}

