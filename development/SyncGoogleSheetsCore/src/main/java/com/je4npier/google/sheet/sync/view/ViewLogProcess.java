package com.je4npier.google.sheet.sync.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.je4npier.google.sheet.sync.controller.RunnableProcess;
import com.je4npier.google.sheet.sync.controller.WriteLog;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewLogProcess extends JDialog implements WriteLog {
	
	private static final long serialVersionUID = 1L;
	private JTextArea textAreaLogProcess;
	private JButton btnCerrar;
	
	private RunnableProcess process;
	private SwingWorker<String, Void> worker;
	
	private boolean isRunning;
	
	/**
	 * @wbp.parser.constructor
	 */
	public ViewLogProcess(JFrame jFrameModal, RunnableProcess process) {
		super(jFrameModal, true);
		this.process = process;
		try {
			initialize();
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ViewLogProcess(JFrame jFrameModal, String message) {
		super(jFrameModal, true);
		try {
			this.setVisible(true);
			initialize();
			textAreaLogProcess.setText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setTitle("Ejecuci\u00F3n de Proceso");
		this.setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isRunning) {
					worker.cancel(true);
					addLog("Proceso detenido por el usuario");
					btnCerrar.setText("Cerrar");
					isRunning = false;
				}else {
					dispose();
				}
			}
		});
		panel.add(btnCerrar);
		
		JScrollPane scrollPane = new JScrollPane();
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textAreaLogProcess = new JTextArea();
		textAreaLogProcess.setText("Inicio del proceso");
		textAreaLogProcess.setEditable(false);
		textAreaLogProcess.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(textAreaLogProcess);
		
		runProcess();
	}
	
	private void runProcess() {
		if(process != null) {
			worker = new SwingWorker<String, Void>(){

				@Override
				protected String doInBackground() throws Exception {
					isRunning = true;
					btnCerrar.setText("Detener proceso");
					process.run();
					return null;
				}
				
				@Override
				protected void done() {
					super.done();
					isRunning = false;
					btnCerrar.setText("Cerrar");					
				}
				
			};
			
			process.setWriteLog(this);
			worker.execute();
		}
	}

	@Override
	public void addLog(String log) {
		textAreaLogProcess.append("\n");
		textAreaLogProcess.append(log);
	}

}
