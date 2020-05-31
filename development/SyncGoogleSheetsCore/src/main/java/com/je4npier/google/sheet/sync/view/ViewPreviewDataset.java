package com.je4npier.google.sheet.sync.view;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ViewPreviewDataset extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public ViewPreviewDataset(JFrame jFrameModal, List<List<Object>> previewDataset) {
		super(jFrameModal, true);
		try {
			initialize(castListListObjectToDefaultTableModel(previewDataset));
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private DefaultTableModel castListListObjectToDefaultTableModel(List<List<Object>> datasetRaw) {		
		Vector<Object> columnNames = new Vector<Object>(datasetRaw.get(0));
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (List<Object> row : datasetRaw.subList(1, datasetRaw.size()))
			data.add(new Vector<Object>(row));
		
		
		return new DefaultTableModel(data, columnNames);
	}
	
	private void initialize(DefaultTableModel previewDataset) {
		this.setTitle("Preview Dataset");
		this.setBounds(100, 100, 523, 310);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCerrar);
		
		JScrollPane scrollPane = new JScrollPane();
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(previewDataset);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
	}
}
