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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class ScenarioOptionsGUI extends JFrame {
	public JCheckBox chckbxShowBackgroundImage;
	private JTextField mTextFieldGameTickTime;
	private JTextField mTextFieldBallMovementMax;
	private JTextField mTextFieldFieldHeight;
	private JTextField mTextFieldFieldWidth;
	private JTextField mTextFieldBotMaxMovement;
	private JCheckBox chckbxSimulation;
	private JCheckBox chckbxAutomaticGame;

	/**
	 * Create the frame.
	 */
	public ScenarioOptionsGUI() {
		ScenarioInformation info = ScenarioInformation.getInstance();
		
		setTitle( info.getmScenarioName() + " Options" );
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 518, 368);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panelGeneral = new JPanel();
		panelGeneral.setAlignmentY(Component.TOP_ALIGNMENT);
		tabbedPane.addTab("General", null, panelGeneral, null);
		GridBagLayout gbl_panelGeneral = new GridBagLayout();
		gbl_panelGeneral.columnWidths = new int[]{0, 46, 0, 0};
		gbl_panelGeneral.rowHeights = new int[]{20, 20, 20, 20, 0};
		gbl_panelGeneral.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelGeneral.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelGeneral.setLayout(gbl_panelGeneral);
		
		JLabel lblNewLabel = new JLabel("Time per GameTick in s:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelGeneral.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		mTextFieldGameTickTime = new JTextField();
		GridBagConstraints gbc_mTextFieldGameTickTime = new GridBagConstraints();
		gbc_mTextFieldGameTickTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_mTextFieldGameTickTime.insets = new Insets(0, 0, 5, 5);
		gbc_mTextFieldGameTickTime.gridx = 1;
		gbc_mTextFieldGameTickTime.gridy = 0;
		panelGeneral.add(mTextFieldGameTickTime, gbc_mTextFieldGameTickTime);
		mTextFieldGameTickTime.setColumns(10);
		mTextFieldGameTickTime.setText( Double.toString( ScenarioInformation.getInstance().getGameTickTime() ) );
		
		JLabel lblNewLabel_1 = new JLabel("Max. Ball Movement per GameTick in % of PlayFieldBackground:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setEnabled(false);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelGeneral.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		mTextFieldBallMovementMax = new JTextField();
		mTextFieldBallMovementMax.setEnabled(false);
		GridBagConstraints gbc_mTextFieldBallMovementMax = new GridBagConstraints();
		gbc_mTextFieldBallMovementMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_mTextFieldBallMovementMax.insets = new Insets(0, 0, 5, 5);
		gbc_mTextFieldBallMovementMax.gridx = 1;
		gbc_mTextFieldBallMovementMax.gridy = 1;
		panelGeneral.add(mTextFieldBallMovementMax, gbc_mTextFieldBallMovementMax);
		mTextFieldBallMovementMax.setColumns(10);
		
		JLabel lblMaxAbsoluteHeight = new JLabel("Height of PlayFieldBackground in % of Width:");
		lblMaxAbsoluteHeight.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblMaxAbsoluteHeight = new GridBagConstraints();
		gbc_lblMaxAbsoluteHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMaxAbsoluteHeight.anchor = GridBagConstraints.EAST;
		gbc_lblMaxAbsoluteHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxAbsoluteHeight.gridx = 0;
		gbc_lblMaxAbsoluteHeight.gridy = 2;
		panelGeneral.add(lblMaxAbsoluteHeight, gbc_lblMaxAbsoluteHeight);
		
		mTextFieldFieldHeight = new JTextField();
		GridBagConstraints gbc_mTextFieldFieldHeight = new GridBagConstraints();
		gbc_mTextFieldFieldHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_mTextFieldFieldHeight.insets = new Insets(0, 0, 5, 5);
		gbc_mTextFieldFieldHeight.gridx = 1;
		gbc_mTextFieldFieldHeight.gridy = 2;
		panelGeneral.add(mTextFieldFieldHeight, gbc_mTextFieldFieldHeight);
		mTextFieldFieldHeight.setColumns(10);
		mTextFieldFieldHeight.setText( Double.toString( ScenarioInformation.getInstance().getYFactor() ) );
		
		JLabel lblMaxAbsoluteWidht = new JLabel("Max. absolute Width of PlayFieldBackground in Points:");
		lblMaxAbsoluteWidht.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblMaxAbsoluteWidht = new GridBagConstraints();
		gbc_lblMaxAbsoluteWidht.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMaxAbsoluteWidht.anchor = GridBagConstraints.EAST;
		gbc_lblMaxAbsoluteWidht.insets = new Insets(0, 0, 0, 5);
		gbc_lblMaxAbsoluteWidht.gridx = 0;
		gbc_lblMaxAbsoluteWidht.gridy = 3;
		panelGeneral.add(lblMaxAbsoluteWidht, gbc_lblMaxAbsoluteWidht);
		
		mTextFieldFieldWidth = new JTextField();
		GridBagConstraints gbc_mTextFieldFieldWidth = new GridBagConstraints();
		gbc_mTextFieldFieldWidth.insets = new Insets(0, 0, 0, 5);
		gbc_mTextFieldFieldWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_mTextFieldFieldWidth.gridx = 1;
		gbc_mTextFieldFieldWidth.gridy = 3;
		panelGeneral.add(mTextFieldFieldWidth, gbc_mTextFieldFieldWidth);
		mTextFieldFieldWidth.setColumns(10);
		mTextFieldFieldWidth.setText( Double.toString( ScenarioInformation.getInstance().getMaxValue() ) );
		
		JPanel panelSimulation = new JPanel();
		tabbedPane.addTab("Simulation", null, panelSimulation, null);
		GridBagLayout gbl_panelSimulation = new GridBagLayout();
		gbl_panelSimulation.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelSimulation.rowHeights = new int[]{23, 0, 0};
		gbl_panelSimulation.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelSimulation.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelSimulation.setLayout(gbl_panelSimulation);
		
		chckbxSimulation = new JCheckBox("Simulation");
		chckbxSimulation.setSelected( Core.getInstance().isSimulation() );
		GridBagConstraints gbc_chckbxSimulation = new GridBagConstraints();
		gbc_chckbxSimulation.gridwidth = 2;
		gbc_chckbxSimulation.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxSimulation.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSimulation.gridx = 0;
		gbc_chckbxSimulation.gridy = 0;
		panelSimulation.add(chckbxSimulation, gbc_chckbxSimulation);
		
		JLabel lblMaxBotMovement = new JLabel("Max. Bot Movement per Tick in % of PlayFieldBackground:");
		GridBagConstraints gbc_lblMaxBotMovement = new GridBagConstraints();
		gbc_lblMaxBotMovement.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMaxBotMovement.insets = new Insets(0, 0, 0, 5);
		gbc_lblMaxBotMovement.gridx = 0;
		gbc_lblMaxBotMovement.gridy = 1;
		panelSimulation.add(lblMaxBotMovement, gbc_lblMaxBotMovement);
		lblMaxBotMovement.setHorizontalAlignment(SwingConstants.RIGHT);
		
		mTextFieldBotMaxMovement = new JTextField();
		GridBagConstraints gbc_mTextFieldBotMaxMovement = new GridBagConstraints();
		gbc_mTextFieldBotMaxMovement.insets = new Insets(0, 0, 0, 5);
		gbc_mTextFieldBotMaxMovement.fill = GridBagConstraints.HORIZONTAL;
		gbc_mTextFieldBotMaxMovement.gridx = 1;
		gbc_mTextFieldBotMaxMovement.gridy = 1;
		panelSimulation.add(mTextFieldBotMaxMovement, gbc_mTextFieldBotMaxMovement);
		mTextFieldBotMaxMovement.setText( Double.toString( ScenarioInformation.getInstance().getSimulationBotSpeed() ) );
		mTextFieldBotMaxMovement.setColumns(10);
		
		JPanel panelPlayModes = new JPanel();
		tabbedPane.addTab("PlayModes", null, panelPlayModes, null);
		GridBagLayout gbl_panelPlayModes = new GridBagLayout();
		gbl_panelPlayModes.columnWidths = new int[]{413, 0, 0};
		gbl_panelPlayModes.rowHeights = new int[]{23, 0};
		gbl_panelPlayModes.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelPlayModes.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelPlayModes.setLayout(gbl_panelPlayModes);
		
		chckbxAutomaticGame = new JCheckBox("Automatic Game");
		chckbxAutomaticGame.setSelected( Core.getInstance().isAutomaticGame() );
		GridBagConstraints gbc_chckbxAutomaticGame = new GridBagConstraints();
		gbc_chckbxAutomaticGame.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxAutomaticGame.anchor = GridBagConstraints.NORTH;
		gbc_chckbxAutomaticGame.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxAutomaticGame.gridx = 0;
		gbc_chckbxAutomaticGame.gridy = 0;
		panelPlayModes.add(chckbxAutomaticGame, gbc_chckbxAutomaticGame);
		
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
			ScenarioInformation.getInstance().setSimulationBotSpeed( Double.parseDouble( mTextFieldBotMaxMovement.getText() ) );
			
			Core.getInstance().setSimulation( chckbxSimulation.isSelected() );
			Core.getInstance().setAutomaticGame( chckbxAutomaticGame.isSelected() );
			
		} catch ( NullPointerException | NumberFormatException vException ){
			
			ScenarioCore.getLogger().error( "Could not parse options {} ", vException );
			ScenarioCore.getLogger().catching(vException);
			
		}
		
		
	}
}
