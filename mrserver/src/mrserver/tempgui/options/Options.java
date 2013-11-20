package mrserver.tempgui.options;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Options extends JFrame {

	private JPanel contentPane;
	private JTabbedPane mTabbedPane;

	private Server mServer = new Server();
	private Vision mVision = new Vision();
	private BotControl mBotControl = new BotControl();
	private Graphics mGraphics = new Graphics();
	private BotAIs mBotAIs = new BotAIs();
	private final JButton btnApply = new JButton("Apply");
	
	/**
	 * Create the frame.
	 */
	public Options() {
		setTitle("Options");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		mTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(mTabbedPane, BorderLayout.CENTER);

		mTabbedPane.addTab("Server", null, new JScrollPane( mServer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), null);
		mTabbedPane.addTab("Vision", null, new JScrollPane( mVision, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), null);
		mTabbedPane.addTab("BotControl", null, new JScrollPane( mBotControl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), null);
		mTabbedPane.addTab("Graphics", null, new JScrollPane( mGraphics, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), null);
		mTabbedPane.addTab("BotAIs", null, new JScrollPane( mBotAIs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), null);
		
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		bottomPanel.add(panel);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				reloadOptions();
				setVisible(false);
				
			}
		});
		panel.add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				saveOptions();
				setVisible(false);
				
			}
		});
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				saveOptions();
				
			}
		});
		
		panel.add(btnApply);
		panel.add(btnOk);
	}
	
	public void setTab( int aTab ){
		
		mTabbedPane.setSelectedIndex( aTab );
		
	}
	
	public void reloadOptions(){

		mServer.reload();
		mVision.reload();
		mBotControl.reload();
		
	}
	void saveOptions(){
		
		mServer.save();
		mVision.save();
		mBotControl.save();
		
	}

}
