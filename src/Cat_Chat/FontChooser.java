package Cat_Chat;

import java.awt.Font;


/******************************************************************************
 * @FontChooser.java: 
 * 
 * Selector de Fuente
 **/
public class FontChooser extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private Cat_Chat client;
	private final String[] arrayFont = { "Agency FB", "Algerial", "Arial",
			"Arial Black", "Arial Narrow", "Arial Rouded MT Bold",
			"Arial Unicode MS", "Baskerville Old Face", "Bauhaus 93",
			"Bell MT", "Berlin Sands FB", "Berlin Sands FB Demi",
			"Bernard MT Condensed", "Blackadder ITC", "Bodoni MT",
			"Bodoni MT Black", "Bodoni MT Condensed",
			"Bodoni MT Poster Compressed", "Book Antiqua", "Bookman Old Style",
			"Bookshelf Symbol 7", "Bradley Hand ITC", "Britannic Bold",
			"Broadway", "Brush Script MT", "Calibri", "Californian FB",
			"Calisto MT", "Cambria", "Cambria Math", "Candara", "Castellar",
			"Centaur", "Century", "Century Gothic", "Century Schoolbook",
			"Chiller", "Conlonna MT", "Comic Sans MS", "Consolas",
			"Constancia", "Cooper Black", "Cooperplate Gothic Bold",
			"Cooperplate Gothic Light", "Corbel", "CorBel", "Courier New",
			"Curlz MT", "Dialog", "DialogInput", "Edwardian Script ITC",
			"Elephant", "Engravers MT", "Eras Bold ITC", "Eras Demi ITC",
			"Eras Light ITC", "Eras Medium ITC", "Estrangelo Edassa",
			"Felix Titling", "Footlight MT Light", "Forte",
			"Franklin Gothic Book", "Franklin Gothic Demi Cond",
			"Franklin Gothic Demi", "Franklin Gothic Heavy",
			"Franklin Gothic Medium", "Franklin Gothic Medium Cond",
			"Freestyle Script", "French Script MT", "Garamond", "Gautami",
			"Georgia", "Gigi", "Gill Sans MT", "Gill Sans MT Condensed",
			"Gill Sans MT Ext Condensed Bold", "Gill Sans Ultra Bold",
			"Gill Sans Ultra Bold Condensed", "Gloucerter Mt Extra Condensed",
			"Goudy Old Style", "Goudy Stout", "Haettenschweiler",
			"Harlow Solid Italic", "Harrington", "High Tower Text", "Impact",
			"Imprint MT Shadow", "Informal Roman", "Jokerman", "Juice ITC",
			"Kartika", "Kristen ITC", "Kunstler Script", "Latha",
			"Lucida Bringht", "Lucida Calligraphy", "Lucida Console",
			"Lucida Fax", "Lucida Handwriting", "Lucida Sans",
			"Lucida Sans Typewriter", "Lucida Sans Unicode", "Magneto",
			"Maiandra GD", "Mangal", "Marlett", "Matura MT Script Capitals",
			"Microsoft Sans Serif", "Mistral", "Modern No.20", "Monospaced",
			"Monotype Corsiva", "MS Micho", "MS Outlook",
			"MS Reference Sans Serif", "MS Reference Specialty", "MT Extra",
			"MV Boli", "Niagara Engreved", "OCR A Extended",
			"Old English Text MT", "Onyx", "Palace Script MT",
			"Paladino Linotype", "Papyrus", "Parchment", "Perpetua",
			"Perpetua Titling MT", "Playbill", "Poor Richard", "Pristina",
			"Raavi", "Rage Italic", "Ravie", "Rockwell Condensed",
			"Rockwell Extra Bold", "SansSerif", "Script MT Bold", "Segoe UI",
			"Serif", "Shruti", "Showcard Gothic", "Snap ITC", "Stencil",
			"Sylfaen", "Symbol", "Tahoma", "Tempus Sans ITC",
			"Times New Roman", "Trebuchet MS", "Tunga", "TW Cen MT",
			"Tw Cen MT Condensed", "Tw Cen MT Condensed Extra Bold", "Verdana",
			"Viner Hand ITC", "Vivaldi", "Vladimir Script", "Wrinda",
			"Webdings", "Wide Latin", "Wingdings", "Wingdings 2", "Wingdings 3" };
	public FontChooser(Cat_Chat gui, boolean modal) {
		client = gui;
		setLocationRelativeTo(client);
		initComponents();
		this.setModal(true);
		setLocationRelativeTo(client);
		for (int i = 0; i < arrayFont.length; i++) {
			list3.add(arrayFont[i]);
		}
		jTextField1.setText(arrayFont[2]);
		jLabel5.setText(arrayFont[2]);
		jLabel5.setFont(new Font("Tahoma", 0, 14));
		setVisible(true);
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jLabel5 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		list3 = new java.awt.List();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Selector de fuentes");
		setFocusCycleRoot(false);
		setFocusable(false);

		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jLabel1.setText("Font:");

		jTextField1.setEditable(false);
		jTextField1.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.LOWERED));
		jPanel2.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Preview"));
		jPanel2.setDoubleBuffered(false);
		jPanel2.setEnabled(false);
		jPanel2.setFocusable(false);
		jPanel2.setOpaque(false);
		jPanel2.setRequestFocusEnabled(false);
		jPanel2.setVerifyInputWhenFocusTarget(false);

		jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel5.setFocusCycleRoot(true);
		jLabel5.setFocusable(false);
		jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		jLabel5.setOpaque(true);
		jLabel5.setVerifyInputWhenFocusTarget(false);
		jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addGap(38, 38, 38)
						.addComponent(jLabel5,
								javax.swing.GroupLayout.PREFERRED_SIZE, 165,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(32, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addComponent(jLabel5,
						javax.swing.GroupLayout.PREFERRED_SIZE, 17,
						javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		jScrollPane1
				.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane1
				.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		list3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				list3MouseClicked(evt);
			}

		});
		jScrollPane1.setViewportView(list3);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																247,
																Short.MAX_VALUE)
														.addComponent(
																jTextField1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																247,
																Short.MAX_VALUE)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel1)
																		.addGap(
																				218,
																				218,
																				218))
														.addComponent(
																jPanel2,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jTextField1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												20,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												155, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jPanel2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												53,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		jButton1.setText("OK");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("Cancelar");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap(109, Short.MAX_VALUE)
										.addComponent(
												jButton1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												85,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jButton2)
										.addContainerGap())
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jPanel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jPanel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton2)
														.addComponent(jButton1))
										.addContainerGap()));

		pack();
	}// </editor-fold>

	private void list3MouseClicked(java.awt.event.MouseEvent evt) {
		jTextField1.setText(arrayFont[list3.getSelectedIndex()]);
		jLabel5.setText(arrayFont[list3.getSelectedIndex()]);
		jLabel5.setFont(new Font(arrayFont[list3.getSelectedIndex()], 0, 18));

	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		client.setFontString(jTextField1.getText());
//		System.out.println(jTextField1.getText()+":"+client.getColor());
		client.showMessage(" : ", jTextField1.getText(), 0, client.getColor());
		client.setFontArea(jTextField1.getText());
		this.dispose();
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextField jTextField1;
	private java.awt.List list3;
	// End of variables declaration

}