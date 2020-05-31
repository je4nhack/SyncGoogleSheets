package com.je4npier.google.sheet.sync.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.je4npier.google.sheet.sync.controller.GoogleSheetsController;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

public class ViewManageSheetsColumns extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public ViewManageSheetsColumns(JFrame jFrame, GoogleSheetsController googleSheetsController) {
		super(jFrame, true);
		setResizable(false);
		initialize();
		this.setVisible(true);
	}
	
	private void initialize() {
		setTitle("Gesti\u00F3n de columnas");
		setBounds(100, 100, 305, 339);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				JList<String> listColumns = new JList<String>();
				listColumns.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				listColumns.setModel(new AbstractListModel<String>() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					String[] values = new String[] {"TEST1", "TEST2"};
					public int getSize() {
						return values.length;
					}
					public String getElementAt(int index) {
						return values[index];
					}
				});
				scrollPane.setViewportView(listColumns);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
			{
				JPanel panel = new JPanel();
				buttonPane.add(panel);
				panel.setLayout(new GridLayout(0, 4, 0, 0));
				{
					JLabel lblNewLabel = new JLabel("Error");
					lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
					panel.add(lblNewLabel);
				}
				{
					JButton btnNewButton = new JButton("Agregar");
					panel.add(btnNewButton);
				}
				{
					JButton btnNewButton_1 = new JButton("Eliminar");
					panel.add(btnNewButton_1);
				}
				{
					JButton btnNewButton_2 = new JButton("Cerrar");
					panel.add(btnNewButton_2);
				}
			}
		}
	}

}
