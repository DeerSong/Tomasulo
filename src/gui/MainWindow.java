package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Window.Type;
import javax.swing.JLabel;
import java.awt.Scrollbar;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainWindow {

	private JFrame frmSimulator;
	private JTable table_instrqueue;
	private JTable table_state;
	private JTable table_loadqueue;
	private JTable table_storequeue;
	private JTable table_mem;
	private JTable table_station;
	private JTable table_fu;
	private JTable table_ru;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmSimulator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSimulator = new JFrame();
		frmSimulator.setAlwaysOnTop(true);
		frmSimulator.setTitle("Simulator");
		frmSimulator.setBounds(0, 0, 900, 700);
		frmSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSimulator.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_instrqueue = new JScrollPane();
		scrollPane_instrqueue.setBounds(42, 140, 234, 121);
		frmSimulator.getContentPane().add(scrollPane_instrqueue);
		
		table_instrqueue = new JTable();
		scrollPane_instrqueue.setViewportView(table_instrqueue);
		table_instrqueue.setCellSelectionEnabled(true);
		table_instrqueue.getTableHeader().setReorderingAllowed(false);
		
		
		JScrollPane scrollPane_state = new JScrollPane();
		scrollPane_state.setBounds(327, 140, 234, 121);
		frmSimulator.getContentPane().add(scrollPane_state);
		
		table_state = new JTable();
		table_state.setCellSelectionEnabled(true);
		scrollPane_state.setViewportView(table_state);
		
		JScrollPane scrollPane_loadqueue = new JScrollPane();
		scrollPane_loadqueue.setBounds(619, 56, 216, 67);
		frmSimulator.getContentPane().add(scrollPane_loadqueue);
		
		table_loadqueue = new JTable();
		table_loadqueue.setCellSelectionEnabled(true);
		scrollPane_loadqueue.setViewportView(table_loadqueue);
		
		JScrollPane scrollPane_storequeue = new JScrollPane();
		scrollPane_storequeue.setBounds(619, 166, 216, 67);
		frmSimulator.getContentPane().add(scrollPane_storequeue);
		
		table_storequeue = new JTable();
		table_storequeue.setCellSelectionEnabled(true);
		scrollPane_storequeue.setViewportView(table_storequeue);
		
		JScrollPane scrollPane_mem = new JScrollPane();
		scrollPane_mem.setBounds(42, 305, 234, 49);
		frmSimulator.getContentPane().add(scrollPane_mem);
		
		table_mem = new JTable();
		table_mem.setCellSelectionEnabled(true);
		scrollPane_mem.setViewportView(table_mem);
		
		JScrollPane scrollPane_station = new JScrollPane();
		scrollPane_station.setBounds(327, 305, 508, 141);
		frmSimulator.getContentPane().add(scrollPane_station);
		
		table_station = new JTable();
		table_station.setCellSelectionEnabled(true);
		scrollPane_station.setViewportView(table_station);
		
		JScrollPane scrollPane_fu = new JScrollPane();
		scrollPane_fu.setBounds(114, 490, 721, 60);
		frmSimulator.getContentPane().add(scrollPane_fu);
		
		table_fu = new JTable();
		table_fu.setCellSelectionEnabled(true);
		scrollPane_fu.setViewportView(table_fu);
		
		JScrollPane scrollPane_ru = new JScrollPane();
		scrollPane_ru.setBounds(114, 591, 727, 41);
		frmSimulator.getContentPane().add(scrollPane_ru);
		
		table_ru = new JTable();
		table_ru.setCellSelectionEnabled(true);
		scrollPane_ru.setViewportView(table_ru);
		
		JLabel lblRunningState = new JLabel("Running State");
		lblRunningState.setBounds(398, 115, 122, 15);
		frmSimulator.getContentPane().add(lblRunningState);
		
		JLabel lblLoadQueue = new JLabel("Load Queue");
		lblLoadQueue.setBounds(681, 31, 122, 15);
		frmSimulator.getContentPane().add(lblLoadQueue);
		
		JLabel lblStoreQueue = new JLabel("Store Queue");
		lblStoreQueue.setBounds(681, 141, 122, 15);
		frmSimulator.getContentPane().add(lblStoreQueue);
		
		JLabel lblMemory = new JLabel("Memory");
		lblMemory.setBounds(135, 280, 122, 15);
		frmSimulator.getContentPane().add(lblMemory);
		
		JLabel lblInstructionQueue = new JLabel("Instruction Queue");
		lblInstructionQueue.setBounds(103, 115, 122, 15);
		frmSimulator.getContentPane().add(lblInstructionQueue);
		
		JLabel lblReservation = new JLabel("Reservation Stations");
		lblReservation.setBounds(518, 280, 122, 15);
		frmSimulator.getContentPane().add(lblReservation);
		
		JLabel lblFloatRegistersfu = new JLabel("Float Registers(FU)");
		lblFloatRegistersfu.setBounds(374, 467, 122, 15);
		frmSimulator.getContentPane().add(lblFloatRegistersfu);
		
		JLabel lblIntergerRegisteriu = new JLabel("Interger Register(RU)");
		lblIntergerRegisteriu.setBounds(363, 566, 148, 15);
		frmSimulator.getContentPane().add(lblIntergerRegisteriu);
		
		JLabel lblRegisterNumber = new JLabel("Reg Number");
		lblRegisterNumber.setBounds(16, 491, 65, 15);
		frmSimulator.getContentPane().add(lblRegisterNumber);
		
		JLabel lblExpression = new JLabel("Expression");
		lblExpression.setBounds(16, 516, 65, 15);
		frmSimulator.getContentPane().add(lblExpression);
		
		JLabel lblData = new JLabel("data");
		lblData.setBounds(16, 535, 65, 15);
		frmSimulator.getContentPane().add(lblData);
		
		JLabel label = new JLabel("Reg Number");
		label.setBounds(16, 592, 65, 15);
		frmSimulator.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("data");
		label_1.setBounds(16, 617, 65, 15);
		frmSimulator.getContentPane().add(label_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(42, 41, 519, 46);
		frmSimulator.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 11, 0, 0));
		
		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		
		JButton button = new JButton("New button");
		panel.add(button);
		
		JButton button_1 = new JButton("New button");
		panel.add(button_1);
		
		JButton button_2 = new JButton("New button");
		panel.add(button_2);
		
		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1);
		
		JButton button_3 = new JButton("New button");
		panel.add(button_3);
		
		JButton button_4 = new JButton("New button");
		panel.add(button_4);
		
		JSeparator separator_2 = new JSeparator();
		panel.add(separator_2);
		
		JButton button_5 = new JButton("New button");
		panel.add(button_5);
		
		JButton button_6 = new JButton("New button");
		panel.add(button_6);
		
		JLabel lblNewLabel = new JLabel("Clock");
		lblNewLabel.setBounds(79, 406, 54, 15);
		frmSimulator.getContentPane().add(lblNewLabel);
		
		JLabel label_2 = new JLabel("0");
		label_2.setBounds(180, 406, 54, 15);
		frmSimulator.getContentPane().add(label_2);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 884, 21);
		frmSimulator.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("File[E]");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Input[I]");
		menuBar.add(mnNewMenu_1);
		
		JMenu mnAssigns = new JMenu("Assign[S]");
		menuBar.add(mnAssigns);
		
		JMenu mnNewMenu_2 = new JMenu("Run[R]");
		menuBar.add(mnNewMenu_2);
		
		JMenu mnMode = new JMenu("Mode[C]");
		menuBar.add(mnMode);
		
		JMenu mnHelp = new JMenu("Help[H]");
		menuBar.add(mnHelp);
		

	}
	
	void load_data() {
		load_instr_queue();
	}
	
	void load_instr_queue(){
		
	}
}
