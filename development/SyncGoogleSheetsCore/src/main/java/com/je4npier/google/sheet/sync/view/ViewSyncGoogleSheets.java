package com.je4npier.google.sheet.sync.view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.SystemColor;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.je4npier.google.sheet.sync.controller.DataBaseController;
import com.je4npier.google.sheet.sync.controller.GoogleCredentialsController;
import com.je4npier.google.sheet.sync.controller.GoogleSheetsController;
import com.je4npier.google.sheet.sync.controller.LoadDataController;
import com.je4npier.google.sheet.sync.controller.ParametersController;
import com.je4npier.google.sheet.sync.controller.RunnableProcess;
import com.je4npier.google.sheet.sync.model.Parameters;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class ViewSyncGoogleSheets {

	private JFrame frmSyncOracleAnd;
	
	private JCheckBox chckbxUsarProxy;
	private JTextField textFieldProxyHost;
	private JTextField textFieldProxyPort;
	private JTextField textFieldProxyUser;
	private JTextField textFieldProxyPass;
	
	private JComboBox<String> comboBoxDBName;
	private JTextField textFieldDbStringConnection;
	private JTextField textFieldDbUser;
	private JTextField textFieldDbPass;
	private JTextArea textAreaDbTableName;
	
	private JTextField textFieldGSSpreadsheetId;
	private JTextField textFieldGSSheetName;
	private JSpinner spinnerGSHeaderRow;
	private JSpinner spinnerGSHeaderColumn;
	
	private JCheckBox chckbxTruncateGoogleSheets;
	private JRadioButton rdbtnAgregarColumnasNuevas;
	private JRadioButton rdbtnIgnorarColumnasNuevas;
	private JRadioButton rdbtnIgnorarColumnasNo;
	private JRadioButton rdbtnEliminarColumnasNo;
	private JSpinner spinnerCommit;
	private JSpinner spinnerRetries;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					ViewSyncGoogleSheets window = new ViewSyncGoogleSheets();
					window.frmSyncOracleAnd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ViewSyncGoogleSheets() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSyncOracleAnd = new JFrame();
		frmSyncOracleAnd.setResizable(false);
		frmSyncOracleAnd.setTitle("Sync DataBase and Google Sheet");
		frmSyncOracleAnd.setBounds(100, 100, 450, 496);
		frmSyncOracleAnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSyncOracleAnd.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 401);
		frmSyncOracleAnd.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Sistema", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(SystemColor.controlShadow));
		panel_4.setBounds(10, 7, 409, 355);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		chckbxUsarProxy = new JCheckBox("Usar Proxy");
		chckbxUsarProxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldProxyHost.setEnabled(chckbxUsarProxy.isSelected());
				textFieldProxyPort.setEnabled(chckbxUsarProxy.isSelected());
				textFieldProxyUser.setEnabled(chckbxUsarProxy.isSelected());
				textFieldProxyPass.setEnabled(chckbxUsarProxy.isSelected());
				
				if(chckbxUsarProxy.isSelected()) {
					System.setProperty("https.proxyHost", textFieldProxyHost.getText());
			        System.setProperty("https.proxyPort", textFieldProxyPort.getText());
			        System.setProperty("https.proxyUser", textFieldProxyUser.getText());
			        System.setProperty("https.proxyPassword", textFieldProxyPass.getText());
			        System.setProperty("com.google.api.client.should_use_proxy", "true");
				}else {
					System.setProperty("https.proxyHost", "");
			        System.setProperty("https.proxyPort", "");
			        System.setProperty("https.proxyUser", "");
			        System.setProperty("https.proxyPassword", "");
			        System.setProperty("com.google.api.client.should_use_proxy", "false");
				}
			}
		});
		chckbxUsarProxy.setBounds(10, 7, 97, 23);
		panel_4.add(chckbxUsarProxy);
		
		JLabel lblProxyPort = new JLabel("Proxy host:");
		lblProxyPort.setBounds(10, 40, 63, 14);
		panel_4.add(lblProxyPort);
		
		JLabel label = new JLabel("Proxy port:");
		label.setBounds(10, 68, 63, 14);
		panel_4.add(label);
		
		JLabel lblProxyUserName = new JLabel("Proxy user name:");
		lblProxyUserName.setBounds(10, 96, 95, 14);
		panel_4.add(lblProxyUserName);
		
		JLabel lblProxyPassword = new JLabel("Proxy password:");
		lblProxyPassword.setBounds(10, 124, 95, 14);
		panel_4.add(lblProxyPassword);
		
		textFieldProxyHost = new JTextField();
		textFieldProxyHost.setEnabled(false);
		textFieldProxyHost.setBounds(100, 37, 173, 20);
		panel_4.add(textFieldProxyHost);
		textFieldProxyHost.setColumns(10);
		
		textFieldProxyPort = new JTextField();
		textFieldProxyPort.setEnabled(false);
		textFieldProxyPort.setBounds(100, 65, 173, 20);
		panel_4.add(textFieldProxyPort);
		textFieldProxyPort.setColumns(10);
		
		textFieldProxyUser = new JTextField();
		textFieldProxyUser.setEnabled(false);
		textFieldProxyUser.setBounds(100, 93, 173, 20);
		panel_4.add(textFieldProxyUser);
		textFieldProxyUser.setColumns(10);
		
		textFieldProxyPass = new JTextField();
		textFieldProxyPass.setEnabled(false);
		textFieldProxyPass.setBounds(100, 121, 173, 20);
		panel_4.add(textFieldProxyPass);
		textFieldProxyPass.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Data Base", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(UIManager.getColor("Button.shadow")));
		panel_5.setBounds(10, 11, 409, 351);
		panel_1.add(panel_5);
		panel_5.setLayout(null);
		
		comboBoxDBName = new JComboBox<String>();
		comboBoxDBName.setModel(new DefaultComboBoxModel<String>(new String[] {"Oracle", "SQL Server"}));
		comboBoxDBName.setBounds(91, 11, 142, 20);
		panel_5.add(comboBoxDBName);
		
		JLabel lblBaseDeDatos = new JLabel("Base de datos:");
		lblBaseDeDatos.setBounds(10, 14, 82, 14);
		panel_5.add(lblBaseDeDatos);
		
		JLabel lblUrl = new JLabel("String de conexi\u00F3n:");
		lblUrl.setBounds(10, 45, 95, 14);
		panel_5.add(lblUrl);
		
		textFieldDbStringConnection = new JTextField();
		textFieldDbStringConnection.setBounds(115, 42, 284, 20);
		panel_5.add(textFieldDbStringConnection);
		textFieldDbStringConnection.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 73, 46, 14);
		panel_5.add(lblUsuario);
		
		textFieldDbUser = new JTextField();
		textFieldDbUser.setColumns(10);
		textFieldDbUser.setBounds(115, 70, 284, 20);
		panel_5.add(textFieldDbUser);
		
		textFieldDbPass = new JTextField();
		textFieldDbPass.setColumns(10);
		textFieldDbPass.setBounds(115, 98, 284, 20);
		panel_5.add(textFieldDbPass);
		
		JLabel lblPasword = new JLabel("Pasword:");
		lblPasword.setBounds(10, 101, 46, 14);
		panel_5.add(lblPasword);
		
		JLabel lblTableSql = new JLabel("Table / SQL query:");
		lblTableSql.setBounds(10, 129, 95, 14);
		panel_5.add(lblTableSql);
		
		JLabel labelDbTestConnection = new JLabel("");
		labelDbTestConnection.setForeground(SystemColor.controlShadow);
		labelDbTestConnection.setBounds(142, 321, 257, 14);
		panel_5.add(labelDbTestConnection);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 129, 284, 177);
		panel_5.add(scrollPane);
		
		textAreaDbTableName = new JTextArea();
		scrollPane.setViewportView(textAreaDbTableName);
		
		JButton btnDbTestConnection = new JButton("Probar conexi\u00F3n");
		btnDbTestConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String dbName = comboBoxDBName.getSelectedItem().toString();
				String url = textFieldDbStringConnection.getText();
				String user = textFieldDbUser.getText();
				String password = textFieldDbPass.getText();
				String tableName = textAreaDbTableName.getText();
				
				if(url.isEmpty() || tableName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete los campos necesarios: String de Conexión de la BD o consulta SQL", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						List<List<Object>> previewDataSet = DataBaseController.
								getPreviewDataset(dbName, url, user, password, tableName);
						
						labelDbTestConnection.setText("Conexión ok");
						labelDbTestConnection.setToolTipText(null);
						new ViewPreviewDataset(frmSyncOracleAnd, previewDataSet);
					} catch (Exception e) {
						e.printStackTrace();
						labelDbTestConnection.setText("Error en la conexión");
						labelDbTestConnection.setToolTipText(e.getMessage());
					}
				}
			}
		});
		btnDbTestConnection.setBounds(10, 317, 122, 23);
		panel_5.add(btnDbTestConnection);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Google Sheets", null, panel_2, null);
		panel_2.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(UIManager.getColor("Button.shadow")));
		panel_6.setBounds(10, 11, 409, 351);
		panel_2.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Establecer conexi\u00F3n:");
		lblNewLabel.setBounds(10, 11, 109, 14);
		panel_6.add(lblNewLabel);
		
		JLabel labelGSResultTest = new JLabel("");
		labelGSResultTest.setForeground(SystemColor.controlShadow);
		labelGSResultTest.setBounds(141, 321, 258, 14);
		panel_6.add(labelGSResultTest);
		
		JLabel lblYaExisteUna = new JLabel("No hay conexi\u00F3n");
		lblYaExisteUna.setForeground(SystemColor.controlShadow);
		lblYaExisteUna.setBounds(272, 11, 127, 14);
		panel_6.add(lblYaExisteUna);
		
		JButton btnConectar = new JButton("Crear nueva conexi\u00F3n");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteGoogleConnection();
				
				boolean sucessConnection = GoogleCredentialsController.makeNewConecctionGoogleSheets();
				if(sucessConnection) lblYaExisteUna.setText("Nueva conexión éxitosa");
				else lblYaExisteUna.setText("Error al conectar");
			}
		});
		btnConectar.setBounds(116, 7, 146, 23);
		panel_6.add(btnConectar);
		
		JLabel lblSheetsId = new JLabel("Spreadsheet ID:");
		lblSheetsId.setBounds(10, 39, 88, 14);
		panel_6.add(lblSheetsId);
		
		textFieldGSSpreadsheetId = new JTextField();
		textFieldGSSpreadsheetId.setBounds(100, 36, 299, 20);
		panel_6.add(textFieldGSSpreadsheetId);
		textFieldGSSpreadsheetId.setColumns(10);
		
		JLabel lblNombreDeHoja = new JLabel("Nombre de hoja:");
		lblNombreDeHoja.setBounds(10, 64, 88, 14);
		panel_6.add(lblNombreDeHoja);
		
		textFieldGSSheetName = new JTextField();
		textFieldGSSheetName.setBounds(100, 61, 299, 20);
		panel_6.add(textFieldGSSheetName);
		textFieldGSSheetName.setColumns(10);
		
		spinnerGSHeaderRow = new JSpinner();
		spinnerGSHeaderRow.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerGSHeaderRow.setBounds(100, 86, 47, 20);
		panel_6.add(spinnerGSHeaderRow);
		
		JLabel lblFilaDeCabeceras = new JLabel("Fila de cabeceras:");
		lblFilaDeCabeceras.setBounds(10, 89, 95, 14);
		panel_6.add(lblFilaDeCabeceras);
		
		JLabel lblColumnaInicioCabecera = new JLabel("Columna inicio cabecera:");
		lblColumnaInicioCabecera.setBounds(155, 89, 127, 14);
		panel_6.add(lblColumnaInicioCabecera);
		
		spinnerGSHeaderColumn = new JSpinner();
		spinnerGSHeaderColumn.setModel(new SpinnerListModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB", "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL", "EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ", "FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ", "HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU", "HV", "HW", "HX", "HY", "HZ", "IA", "IB", "IC", "ID", "IE", "IF", "IG", "IH", "II", "IJ", "IK", "IL", "IM", "IN", "IO", "IP", "IQ", "IR", "IS", "IT", "IU", "IV", "IW", "IX", "IY", "IZ", "JA", "JB", "JC", "JD", "JE", "JF", "JG", "JH", "JI", "JJ", "JK", "JL", "JM", "JN", "JO", "JP", "JQ", "JR", "JS", "JT", "JU", "JV", "JW", "JX", "JY", "JZ", "KA", "KB", "KC", "KD", "KE", "KF", "KG", "KH", "KI", "KJ", "KK", "KL", "KM", "KN", "KO", "KP", "KQ", "KR", "KS", "KT", "KU", "KV", "KW", "KX", "KY", "KZ", "LA", "LB", "LC", "LD", "LE", "LF", "LG", "LH", "LI", "LJ", "LK", "LL", "LM", "LN", "LO", "LP", "LQ", "LR", "LS", "LT", "LU", "LV", "LW", "LX", "LY", "LZ", "MA", "MB", "MC", "MD", "ME", "MF", "MG", "MH", "MI", "MJ", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NB", "NC", "ND", "NE", "NF", "NG", "NH", "NI", "NJ", "NK", "NL", "NM", "NN", "NO", "NP", "NQ", "NR", "NS", "NT", "NU", "NV", "NW", "NX", "NY", "NZ", "OA", "OB", "OC", "OD", "OE", "OF", "OG", "OH", "OI", "OJ", "OK", "OL", "OM", "ON", "OO", "OP", "OQ", "OR", "OS", "OT", "OU", "OV", "OW", "OX", "OY", "OZ", "PA", "PB", "PC", "PD", "PE", "PF", "PG", "PH", "PI", "PJ", "PK", "PL", "PM", "PN", "PO", "PP", "PQ", "PR", "PS", "PT", "PU", "PV", "PW", "PX", "PY", "PZ", "QA", "QB", "QC", "QD", "QE", "QF", "QG", "QH", "QI", "QJ", "QK", "QL", "QM", "QN", "QO", "QP", "QQ", "QR", "QS", "QT", "QU", "QV", "QW", "QX", "QY", "QZ", "RA", "RB", "RC", "RD", "RE", "RF", "RG", "RH", "RI", "RJ", "RK", "RL", "RM", "RN", "RO", "RP", "RQ", "RR", "RS", "RT", "RU", "RV", "RW", "RX", "RY", "RZ", "SA", "SB", "SC", "SD", "SE", "SF", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SP", "SQ", "SR", "SS", "ST", "SU", "SV", "SW", "SX", "SY", "SZ", "TA", "TB", "TC", "TD", "TE", "TF", "TG", "TH", "TI", "TJ", "TK", "TL", "TM", "TN", "TO", "TP", "TQ", "TR", "TS", "TT", "TU", "TV", "TW", "TX", "TY", "TZ", "UA", "UB", "UC", "UD", "UE", "UF", "UG", "UH", "UI", "UJ", "UK", "UL", "UM", "UN", "UO", "UP", "UQ", "UR", "US", "UT", "UU", "UV", "UW", "UX", "UY", "UZ", "VA", "VB", "VC", "VD", "VE", "VF", "VG", "VH", "VI", "VJ", "VK", "VL", "VM", "VN", "VO", "VP", "VQ", "VR", "VS", "VT", "VU", "VV", "VW", "VX", "VY", "VZ", "WA", "WB", "WC", "WD", "WE", "WF", "WG", "WH", "WI", "WJ", "WK", "WL", "WM", "WN", "WO", "WP", "WQ", "WR", "WS", "WT", "WU", "WV", "WW", "WX", "WY", "WZ", "XA", "XB", "XC", "XD", "XE", "XF", "XG", "XH", "XI", "XJ", "XK", "XL", "XM", "XN", "XO", "XP", "XQ", "XR", "XS", "XT", "XU", "XV", "XW", "XX", "XY", "XZ", "YA", "YB", "YC", "YD", "YE", "YF", "YG", "YH", "YI", "YJ", "YK", "YL", "YM", "YN", "YO", "YP", "YQ", "YR", "YS", "YT", "YU", "YV", "YW", "YX", "YY", "YZ", "ZA", "ZB", "ZC", "ZD", "ZE", "ZF", "ZG", "ZH", "ZI", "ZJ", "ZK", "ZL", "ZM", "ZN", "ZO", "ZP", "ZQ", "ZR", "ZS", "ZT", "ZU", "ZV", "ZW", "ZX", "ZY", "ZZ"}));
		spinnerGSHeaderColumn.setBounds(277, 86, 47, 20);
		panel_6.add(spinnerGSHeaderColumn);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Carga de datos", null, panel_3, null);
		panel_3.setLayout(null);
		
		tabbedPane.setSelectedIndex(3);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(UIManager.getColor("Button.shadow")));
		panel_7.setBounds(10, 11, 409, 351);
		panel_3.add(panel_7);
		panel_7.setLayout(null);
		
		chckbxTruncateGoogleSheets = new JCheckBox("Truncate google sheets");
		chckbxTruncateGoogleSheets.setToolTipText("Eliminar todas las filas del documento google sheets antes de inserci\u00F3n.");
		chckbxTruncateGoogleSheets.setBounds(6, 7, 139, 23);
		panel_7.add(chckbxTruncateGoogleSheets);
		
		rdbtnAgregarColumnasNuevas = new JRadioButton("Ignorar columnas nuevas");
		rdbtnAgregarColumnasNuevas.setToolTipText("En desarrollo");
		rdbtnAgregarColumnasNuevas.setEnabled(false);
		rdbtnAgregarColumnasNuevas.setSelected(true);
		rdbtnAgregarColumnasNuevas.setBounds(6, 35, 161, 23);
		panel_7.add(rdbtnAgregarColumnasNuevas);
		
		rdbtnIgnorarColumnasNuevas = new JRadioButton("Agregar columnas nuevas");
		rdbtnIgnorarColumnasNuevas.setToolTipText("En desarrollo");
		rdbtnIgnorarColumnasNuevas.setEnabled(false);
		rdbtnIgnorarColumnasNuevas.setBounds(206, 35, 161, 23);
		panel_7.add(rdbtnIgnorarColumnasNuevas);
		
		rdbtnIgnorarColumnasNo = new JRadioButton("Ignorar columnas no encontradas");
		rdbtnIgnorarColumnasNo.setToolTipText("En desarrollo");
		rdbtnIgnorarColumnasNo.setEnabled(false);
		rdbtnIgnorarColumnasNo.setSelected(true);
		rdbtnIgnorarColumnasNo.setBounds(6, 61, 187, 23);
		panel_7.add(rdbtnIgnorarColumnasNo);
		
		rdbtnEliminarColumnasNo = new JRadioButton("Eliminar columnas no encontradas");
		rdbtnEliminarColumnasNo.setToolTipText("En desarrollo");
		rdbtnEliminarColumnasNo.setEnabled(false);
		rdbtnEliminarColumnasNo.setBounds(206, 61, 187, 23);
		panel_7.add(rdbtnEliminarColumnasNo);
		
		ButtonGroup addNewColumn = new ButtonGroup();
		addNewColumn.add(rdbtnAgregarColumnasNuevas);
		addNewColumn.add(rdbtnIgnorarColumnasNuevas);
		
		ButtonGroup dropNotFoundColumn = new ButtonGroup();
		dropNotFoundColumn.add(rdbtnIgnorarColumnasNo);
		dropNotFoundColumn.add(rdbtnEliminarColumnasNo);
		
		JLabel lblC = new JLabel("Commit rows:");
		lblC.setBounds(6, 91, 72, 14);
		panel_7.add(lblC);
		
		spinnerCommit = new JSpinner();
		spinnerCommit.setModel(new SpinnerNumberModel(4000, 1000, 10000, 1000));
		spinnerCommit.setBounds(88, 88, 57, 20);
		panel_7.add(spinnerCommit);
		
		JTextArea txtrObservaciones = new JTextArea();
		txtrObservaciones.setEnabled(false);
		txtrObservaciones.setEditable(false);
		txtrObservaciones.setBackground(SystemColor.control);
		txtrObservaciones.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrObservaciones.setText("*Observaciones:\r\n- Tener en cuenta que Google Sheets solo acepta 5 millones de celdas (no de \r\nfilas).\r\n- No se admiten campos tipo fecha de la BD, convertirlos a String con formato \r\n\"yyyy/MM/dd\"");
		txtrObservaciones.setBounds(6, 170, 393, 79);
		panel_7.add(txtrObservaciones);
		
		JLabel lblNmeroDeReintentos = new JLabel("N\u00FAmero de reintentos:");
		lblNmeroDeReintentos.setBounds(6, 119, 116, 14);
		panel_7.add(lblNmeroDeReintentos);
		
		spinnerRetries = new JSpinner();
		spinnerRetries.setToolTipText("");
		spinnerRetries.setModel(new SpinnerNumberModel(2, 0, 5, 1));
		spinnerRetries.setBounds(126, 116, 31, 20);
		panel_7.add(spinnerRetries);
		
		JButton btnTruncar = new JButton("Truncar");
		btnTruncar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String spreadsheetId = textFieldGSSpreadsheetId.getText();
				String sheetName = textFieldGSSheetName.getText();
				int headerRow = (Integer) spinnerGSHeaderRow.getValue();
				String headerColumn = spinnerGSHeaderColumn.getValue().toString();
				
				int numCommit = (Integer) spinnerCommit.getValue();
				try {
					final GoogleSheetsController gsController = new GoogleSheetsController(spreadsheetId, sheetName, headerRow, headerColumn);
					
					RunnableProcess process = new RunnableProcess(gsController) {
						@Override
						public void run() {
							try {
								gsController.truncateSheet(numCommit);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					};
					
					new ViewLogProcess(frmSyncOracleAnd, process);
					
				} catch (Exception e) {
					new ViewLogProcess(frmSyncOracleAnd, e.getMessage());
				}
				
			}
		});
		btnTruncar.setBounds(151, 7, 89, 23);
		panel_7.add(btnTruncar);
		
		JButton btnTest = new JButton("Probar conexi\u00F3n");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String spreadsheetId = textFieldGSSpreadsheetId.getText();
				String sheetName = textFieldGSSheetName.getText();
				int headerRow = (Integer) spinnerGSHeaderRow.getValue();
				String headerColumn = spinnerGSHeaderColumn.getValue().toString();
				
				if(spreadsheetId.isEmpty() || sheetName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete los campos necesarios: spreadsheetId o sheetName", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						GoogleSheetsController gsController = new GoogleSheetsController(spreadsheetId, sheetName, headerRow, headerColumn);
						List<List<Object>> previewDataSet = gsController.getPreviewData();
						
						if(previewDataSet == null) {
							labelGSResultTest.setText("Error en la conexión");
							labelGSResultTest.setToolTipText("No se rescató ningún registro");
						}
						else {
							labelGSResultTest.setText("Prueba exitosa");
							lblYaExisteUna.setText("Conexión éxitosa");
							labelGSResultTest.setToolTipText(null);
							new ViewPreviewDataset(frmSyncOracleAnd, previewDataSet);
						}
					} catch (Exception e2) {
						labelGSResultTest.setText("Error en la conexión");
						labelGSResultTest.setToolTipText(e2.getMessage());
						e2.printStackTrace();
					}
				}
				
				
			}
		});
		btnTest.setBounds(10, 317, 121, 23);
		panel_6.add(btnTest);
		
		JButton btnGestionarColumnas = new JButton("Gestionar columnas");
		btnGestionarColumnas.setToolTipText("En desarrollo");
		btnGestionarColumnas.setEnabled(false);
		btnGestionarColumnas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ViewManageSheetsColumns(frmSyncOracleAnd, null);
			}
		});
		btnGestionarColumnas.setBounds(10, 115, 136, 23);
		panel_6.add(btnGestionarColumnas);
		
		JButton btnNewButton = new JButton("Ejecutar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Google sheets data
				String spreadsheetId = textFieldGSSpreadsheetId.getText();
				String sheetName = textFieldGSSheetName.getText();
				int headerRow = (Integer) spinnerGSHeaderRow.getValue();
				String headerColumn = spinnerGSHeaderColumn.getValue().toString();
				
				//Oracle data
				String dbName = comboBoxDBName.getSelectedItem().toString();
				String url = textFieldDbStringConnection.getText();
				String user = textFieldDbUser.getText();
				String password = textFieldDbPass.getText();
				String tableName = textAreaDbTableName.getText();
				
				//Program params
				boolean needTruncate = chckbxTruncateGoogleSheets.isSelected();
				int numCommit = (Integer) spinnerCommit.getValue();
				int numRetries = (Integer) spinnerRetries.getValue();
				
				if(spreadsheetId.isEmpty() || sheetName.isEmpty() || url.isEmpty() || tableName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete los campos necesarios: spreadsheetId, sheetName, String de Conexión de la BD o consulta SQL", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						final LoadDataController loadDataController =
								new LoadDataController(spreadsheetId, sheetName, headerRow, headerColumn,
										dbName, url, user, password, tableName);
						
						RunnableProcess process = new RunnableProcess(loadDataController) {
							@Override
							public void run() {
								try {
									loadDataController.execProcess(needTruncate, numCommit, numRetries);
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						};
						
						new ViewLogProcess(frmSyncOracleAnd, process);
						
					} catch (Exception e) {
						new ViewLogProcess(frmSyncOracleAnd, e.getMessage());
					}
				}
				
			}
		});
		btnNewButton.setBounds(246, 412, 89, 23);
		frmSyncOracleAnd.getContentPane().add(btnNewButton);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCerrar.setBounds(345, 412, 89, 23);
		frmSyncOracleAnd.getContentPane().add(btnCerrar);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.windowBorder);
		frmSyncOracleAnd.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmImportarConfiguracin = new JMenuItem("Abrir Configuraci\u00F3n");
		mntmImportarConfiguracin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Abrir configuración");
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("JSON FILE",
						"json");
				jFileChooser.setFileFilter(fileNameExtensionFilter);
				int returnValue = jFileChooser.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						Parameters parameters = ParametersController.readParameters(jFileChooser.getSelectedFile().getAbsolutePath());
						updateValuesView(parameters);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
				
			});
		
		mntmImportarConfiguracin.setIcon(null);
		mnNewMenu.add(mntmImportarConfiguracin);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Guardar configuraci\u00F3n");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Abrir configuración");
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("JSON FILE",
						"json");
				jFileChooser.setFileFilter(fileNameExtensionFilter);
				int returnValue = jFileChooser.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						Parameters parameters = getParameters();
						ParametersController.saveParameters(parameters, jFileChooser.getSelectedFile().getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		
		mntmNewMenuItem.setIcon(null);
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnAcercaDe = new JMenu("Help");
		menuBar.add(mnAcercaDe);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null ,"Autor: Jean Pierre Pérez Pinedo\n"
						+ "Email: je4npier@gmail.com\n"
						+ "Link: https://github.com/je4nhack/SyncGoogleSheets",
						"Acerca de",
						JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		mnAcercaDe.add(mntmAcercaDe);
		
		//Verify if exists token
		if(existGoogleConnection()) lblYaExisteUna.setText("Ya existe conexión");
		else lblYaExisteUna.setText("No existe conexión");
		
		//read ini.json
		String url = null;
		if(this.getClass().getResource(ParametersController.INIT_FILE_NAME) != null)
			url = this.getClass().getResource(ParametersController.INIT_FILE_NAME).getPath();
		Parameters parameters = null;
		
		if(url != null) {
			try {
				parameters = ParametersController.readParameters(url);
				updateValuesView(parameters);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void updateValuesView(Parameters parameters) {
		if(parameters.getUseProxy() != null) chckbxUsarProxy.setSelected(parameters.getUseProxy());
		if(parameters.getProxyHost() != null) textFieldProxyHost.setText(parameters.getProxyHost());
		if(parameters.getProxyPort() != null) textFieldProxyPort.setText(parameters.getProxyPort());
		if(parameters.getProxyUserName() != null) textFieldProxyUser.setText(parameters.getProxyUserName());
		if(parameters.getProxyPassword() != null) textFieldProxyPass.setText(parameters.getProxyPassword());
		
		if(parameters.getDbName() != null) comboBoxDBName.setSelectedItem(parameters.getDbName());
		if(parameters.getDbStringConnection() != null) textFieldDbStringConnection.setText(parameters.getDbStringConnection());
		if(parameters.getDbUserName() != null) textFieldDbUser.setText(parameters.getDbUserName());
		if(parameters.getDbPassword() != null) textFieldDbPass.setText(parameters.getDbPassword());
		if(parameters.getDbQuery() != null) textAreaDbTableName.setText(parameters.getDbQuery());
		
		if(parameters.getSpreadsheetId() != null) textFieldGSSpreadsheetId.setText(parameters.getSpreadsheetId());
		if(parameters.getSpreadsheetSheetName() != null) textFieldGSSheetName.setText(parameters.getSpreadsheetSheetName());
		if(parameters.getSpreadsheetRowHeader() != null) spinnerGSHeaderRow.setValue(parameters.getSpreadsheetRowHeader());
		if(parameters.getSpreadsheetColumnHeader() != null) spinnerGSHeaderColumn.setValue(parameters.getSpreadsheetColumnHeader());
		
		if(parameters.getTruncateTable() != null) chckbxTruncateGoogleSheets.setSelected(parameters.getTruncateTable());
		if(parameters.getIgnoreNewColumns() != null) {
			rdbtnAgregarColumnasNuevas.setSelected(!parameters.getIgnoreNewColumns());
			rdbtnIgnorarColumnasNuevas.setSelected(parameters.getIgnoreNewColumns());
		}
		if(parameters.getIgnoreNotFoundColumns() != null) {
			rdbtnIgnorarColumnasNo.setSelected(parameters.getIgnoreNotFoundColumns());
			rdbtnEliminarColumnasNo.setSelected(!parameters.getIgnoreNotFoundColumns());
		}
		if(parameters.getCommitRows() != null) spinnerCommit.setValue(parameters.getCommitRows());
		if(parameters.getAttempsNumber() != null) spinnerRetries.setValue(parameters.getAttempsNumber());
	}
	
	private Parameters getParameters() {
		Parameters parameters = new Parameters();
		
		parameters.setUseProxy(chckbxUsarProxy.isSelected());
		parameters.setProxyHost(textFieldProxyHost.getText());
		parameters.setProxyPort(textFieldProxyPort.getText());
		parameters.setProxyUserName(textFieldProxyUser.getText());
		parameters.setProxyPassword(textFieldProxyPass.getText());
		
		parameters.setDbName(comboBoxDBName.getSelectedItem().toString());
		parameters.setDbStringConnection(textFieldDbStringConnection.getText());
		parameters.setDbUserName(textFieldDbUser.getText());
		parameters.setDbPassword(textFieldDbPass.getText());
		parameters.setDbQuery(textAreaDbTableName.getText());
		
		parameters.setSpreadsheetId(textFieldGSSpreadsheetId.getText());
		parameters.setSpreadsheetSheetName(textFieldGSSheetName.getText());
		parameters.setSpreadsheetRowHeader((Integer) spinnerGSHeaderRow.getValue());
		parameters.setSpreadsheetColumnHeader(spinnerGSHeaderColumn.getValue().toString());
		
		parameters.setTruncateTable(chckbxTruncateGoogleSheets.isSelected());
		parameters.setIgnoreNewColumns(rdbtnIgnorarColumnasNuevas.isSelected());
		parameters.setIgnoreNotFoundColumns(rdbtnIgnorarColumnasNo.isSelected());
		parameters.setCommitRows((Integer) spinnerCommit.getValue());
		parameters.setAttempsNumber((Integer) spinnerRetries.getValue());
		
		return parameters;
	}
	
	private boolean existGoogleConnection() {
		boolean result = false;
		File folderToken = new File(GoogleCredentialsController.TOKENS_DIRECTORY_PATH);
		if(folderToken.exists()){
			result = folderToken.list().length > 0;
		}
		
		return result;
	}
	
	private void deleteGoogleConnection() {
		File folderToken = new File(GoogleCredentialsController.TOKENS_DIRECTORY_PATH);
		if(folderToken.exists()){
			String[] entries = folderToken.list();
			for (String entrie : entries) {
				new File(folderToken, entrie).delete();
			}
		}
	}
}
