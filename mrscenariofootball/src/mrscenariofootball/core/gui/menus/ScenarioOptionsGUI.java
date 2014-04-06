package mrscenariofootball.core.gui.menus;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.game.Core;

import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JTextField;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ScenarioOptionsGUI extends JFrame {
	public JCheckBox chckbxShowBackgroundImage;
	private JTextField mTextFieldGameTickTime;
	private JTextField mTextFieldBallMovementMax;
	private JTextField mTextFieldFieldHeight;
	private JTextField mTextFieldFieldWidth;
	private JTextField mTextFieldBotMaxMovement;
	private JCheckBox chckbxSimulation;

	/**
	 * Create the frame.
	 */
	public ScenarioOptionsGUI() {
		ScenarioInformation info = ScenarioInformation.getInstance();
		
		setTitle( info.getmScenarioName() + " Options" );
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 448, 368);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		tabbedPane.addTab("General", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 10, 425, 20);
		panel.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		JLabel lblNewLabel = new JLabel("Time per GameTick in s:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_4.add(lblNewLabel);
		
		mTextFieldGameTickTime = new JTextField();
		panel_4.add(mTextFieldGameTickTime);
		mTextFieldGameTickTime.setColumns(10);
		mTextFieldGameTickTime.setText( Double.toString( ScenarioInformation.getInstance().getGameTickTime() ) );
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setHgap(10);
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		flowLayout_1.setVgap(0);
		panel_5.setBounds(0, 40, 425, 20);
		panel.add(panel_5);
		
		JLabel lblNewLabel_1 = new JLabel("Max. Ball Movement per GameTick in % of PlayField:");
		panel_5.add(lblNewLabel_1);
		
		mTextFieldBallMovementMax = new JTextField();
		panel_5.add(mTextFieldBallMovementMax);
		mTextFieldBallMovementMax.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_6.getLayout();
		flowLayout_2.setHgap(10);
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		flowLayout_2.setVgap(0);
		panel_6.setBounds(0, 70, 425, 20);
		panel.add(panel_6);
		
		JLabel lblMaxAbsoluteHeight = new JLabel("Height of PlayField in % of Width:");
		panel_6.add(lblMaxAbsoluteHeight);
		
		mTextFieldFieldHeight = new JTextField();
		mTextFieldFieldHeight.setColumns(10);
		panel_6.add(mTextFieldFieldHeight);
		mTextFieldFieldHeight.setText( Double.toString( ScenarioInformation.getInstance().getYFactor() ) );
		
		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_7.getLayout();
		flowLayout_3.setHgap(10);
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		flowLayout_3.setVgap(0);
		panel_7.setBounds(0, 100, 425, 20);
		panel.add(panel_7);
		
		JLabel lblMaxAbsoluteWidht = new JLabel("Max. absolute Width of PlayField in Points:");
		panel_7.add(lblMaxAbsoluteWidht);
		
		mTextFieldFieldWidth = new JTextField();
		mTextFieldFieldWidth.setColumns(10);
		panel_7.add(mTextFieldFieldWidth);
		mTextFieldFieldWidth.setText( Double.toString( ScenarioInformation.getInstance().getMaxValue() ) );
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Simulation", null, panel_3, null);
		panel_3.setLayout(null);
		
		chckbxSimulation = new JCheckBox("Simulation");
		chckbxSimulation.setSelected( Core.getInstance().isSimulation() );
		chckbxSimulation.setBounds(6, 7, 97, 23);
		panel_3.add(chckbxSimulation);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(10, 37, 425, 20);
		panel_3.add(panel_8);
		panel_8.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		
		JLabel lblMaxBotMovement = new JLabel("Max. Bot Movement per Tick in % of PlayField:");
		lblMaxBotMovement.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_8.add(lblMaxBotMovement);
		
		mTextFieldBotMaxMovement = new JTextField();
		mTextFieldBotMaxMovement.setText("0.01");
		mTextFieldBotMaxMovement.setColumns(10);
		panel_8.add(mTextFieldBotMaxMovement);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("PlayModes", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton vBtnOk = new JButton("Ok");
		vBtnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveOptions();
				dispose();
			}
		});
		panel_2.add(vBtnOk);
		
		JButton bBtnApply = new JButton("Apply");
		bBtnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveOptions();
			}
		});
		panel_2.add(bBtnApply);
		
		JButton vBtnCancel = new JButton("Cancel");
		vBtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_2.add(vBtnCancel);
	}
	
	private void saveOptions(){
		
		try{

			ScenarioInformation.getInstance().setGameTickTime( Double.parseDouble( mTextFieldGameTickTime.getText() ) );
			ScenarioInformation.getInstance().setYFactor( Double.parseDouble( mTextFieldFieldHeight.getText() ) );
			ScenarioInformation.getInstance().setMaxValue( Double.parseDouble( mTextFieldFieldWidth.getText() ) );
			
			Core.getInstance().setSimulation( chckbxSimulation.isSelected() );
			
		} catch ( NullPointerException | NumberFormatException vException ){
			
			ScenarioCore.getLogger().error( "Could not parse options {} ", vException );
			ScenarioCore.getLogger().catching(vException);
			
		}
		
		
	}
}
