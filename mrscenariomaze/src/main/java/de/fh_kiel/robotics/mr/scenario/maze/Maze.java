package de.fh_kiel.robotics.mr.scenario.maze;

import de.fh_kiel.robotics.mr.server.misc.botcontrol.interfaces.BotControl;
import de.fh_kiel.robotics.mr.server.misc.bots.interfaces.Bot;
import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionDataPackage;
import de.fh_kiel.robotics.mr.server.misc.scenario.interfaces.Scenario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class Maze extends JPanel implements ComponentListener, Scenario {

	private BufferedImage mWallImage, mBackGround;
	private JPopupMenu mMazePopupMenu;
	private float mPointSize, mHalfPointSize, mLineWidth;
	private Graphics2D g2d;
	public int mWidth, mHeight;
	private Font mFont;
	private Stroke mDashedStroke, mNormalStroke;
	// Ersatz f�r aWorldData
    public int[][] feld = new int[][] {
        {0,9,0,0,0,0,0,0,0,0},
        {0,1,0,0,0,0,1,1,1,0},
        {0,1,1,0,0,0,1,0,1,0},
        {0,0,1,0,0,0,1,0,1,0},
        {1,1,1,1,1,1,1,1,0,0},
        {0,0,1,0,0,1,0,1,0,0},
        {0,0,1,0,0,0,0,1,0,0},
        {0,0,0,1,1,1,1,1,0,0},
        {0,0,0,1,0,1,0,1,0,0},
        {0,0,0,8,0,0,0,0,1,0},
        {0,0,0,0,0,0,0,0,0,0}
        };
	public int widthPerTile;
	public int heightPerTile;
	
	private Maze mNO = null;
	
	private Runnable mLogicLoop = new Runnable() {
		
		@Override
		public void run() {

			while(true) {
				synchronized(mNO) {
					if (bot != null) {
						if(bot.getLastAction() != null) {
							Command vCommand = Command.unmarshallXMLPositionDataPackageString( bot.getLastAction() );
							
							if( vCommand != null ){
								
								if( vCommand.isMovement() ){
									
									moveBot( vCommand.getMovement() );
									
								} 
							}
						}
						
						bot.sendPosition(new Position(bot, mNO));
					}
				}
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private void moveBot( Movement aMovement ) {
	
		double vSpeed, vRotation, vRightDegree, vLeftDegree;
		final double vMaxSpeed = 0.004;
		
		vSpeed = ( aMovement.getLeftWheelVelocity() + aMovement.getRightWheelVelocity() ) / 200.0 * vMaxSpeed / (double) mLineWidth;

		vRightDegree = Math.toDegrees( Math.atan2( 1.0, aMovement.getRightWheelVelocity() / 100.0 ) );
		vLeftDegree = Math.toDegrees( Math.atan2( 1.0, aMovement.getLeftWheelVelocity() / 100.0 ) );
		
		vRotation = (vRightDegree + vLeftDegree ) / 2 - vRightDegree;
		vRotation /= 1.5;
		
		bot.orientation = bot.orientation + vRotation;
 
		bot.positionX = bot.positionX + vSpeed * Math.cos( Math.toRadians( bot.orientation ) );
		bot.positionY = bot.positionY + vSpeed * Math.sin( Math.toRadians( bot.orientation ) );
		
	}
	
	/**
	 * Create the panel.
	 */
	public Maze() {
		setBackground(new Color(255, 255, 255));
		setLayout(null);

		addComponentListener(this);

		// createLinesToDraw();

		// mMazePopupMenu = new MazePopup();

		try {
			mWallImage = ImageIO.read(Maze.class.getResource("/mrscenariomaze/gui/mWallImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {

					// mMazePopupMenu.show(e.getComponent(), e.getX(), e.getY());

				}

			}
		});

		mNO = this;

		Thread vStart = new Thread(mLogicLoop);
		vStart.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		try {

			g2d = (Graphics2D) g;

			if (mNormalStroke == null) {
				mNormalStroke = g2d.getStroke();
			}

			if (mBackGround == null) {
				createBackground();
			}
			g2d.drawImage(mBackGround, 0, 0, this);
			
			// g2d.setColor(Color.black);
			// g2d.drawRect(0, 0, 100, 100);
			
			// g2d.translate(0, mHeight);
			// g2d.scale(1.0 / ScenarioInformation.getInstance().getXFactor(),
			//		 1.0 / ScenarioInformation.getInstance().getYFactor());

			widthPerTile = (int) mWidth / feld.length;
			heightPerTile = (int) mHeight / feld.length;
			for (int i = 0; i < feld.length; i++) {
				for (int j = 0; j < feld[i].length; j++) {
					if (feld[i][j] == 0) {
						g2d.drawImage(mWallImage, 
								widthPerTile * j,
								heightPerTile * i,
								widthPerTile, 
								heightPerTile, 
								this);
					} else if (feld[i][j] == 9) {
						g2d.setColor(Color.blue);
						g2d.fillRect( 
								widthPerTile * j,
								heightPerTile * i,
								widthPerTile, 
								heightPerTile);
					} else if (feld[i][j] == 8) {
						g2d.setColor(Color.red);
						g2d.fillRect( 
								widthPerTile * j,
								heightPerTile * i,
								widthPerTile, 
								heightPerTile);
					}
				}
			}
			
			if(bot != null){
			
				g2d.drawLine( (int)( mWidth * bot.positionX ), 
						(int)( -mHeight * bot.positionX ),
						(int)( mWidth * bot.positionX + 0.010 * mWidth * Math.cos( Math.toRadians( -bot.orientation ) ) ),
						(int)( -mHeight * bot.positionY + 0.010 * mWidth * Math.sin( Math.toRadians( -bot.orientation ) ) ) );
				
	            g2d.fillOval( (int)( mWidth * bot.positionX - mHalfPointSize ), 
						(int)( -mHeight * bot.positionY - mHalfPointSize ), 
						(int)( mPointSize ), 
						(int)( mPointSize ) );
	            g2d.drawOval( (int)( mWidth * bot.positionX - 0.010 * mWidth ), 
								(int)( -mHeight * bot.positionY - 0.010 * mWidth ), 
								(int)( 0.020 * mWidth ), 
								(int)( 0.020 * mWidth ) );
			}
			g2d.dispose();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createBackground() {

		if (getWidth() == 0 || getHeight() == 0) {
			mBackGround = null;
			return;
		}

		mWidth = this.getWidth();
		mHeight = this.getHeight();

		mPointSize = mHeight / 100;
		mHalfPointSize = mPointSize * 0.5f;

		int vScale = mHeight / 40;

		mBackGround = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) mBackGround.getGraphics();
		if (g == null) {
			mBackGround = null;
			return;
		}

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		super.paintComponent(g);

	}

	/**
	 * Updates graphic
	 */
	public void update() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				repaint();
				validate();

			}
		});

	}

	@Override
	public void componentResized(ComponentEvent e) {

		createBackground();

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean needVision() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needBotControl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean putPositionData(PositionDataPackage aPositionDataPackage) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerBotControl(BotControl aBotControl) {
		// TODO Auto-generated method stub
		return false;
	}

	private volatile BotAI bot = null;
	@Override
	public boolean registerNewBot(Bot aBot) {
//		if (bot == null) {
//			bot = new BotAI(aBot);
//			return true;
//		}
//		return false;
		synchronized(mNO) {
			bot = new BotAI(aBot);
			for (int i = 0; i < feld.length; i++) {
				for (int j = 0; j < feld[i].length; j++) {
					if (feld[i][j] == 9) {
						bot.positionX = widthPerTile*(i+0.5)/mWidth;
						bot.positionY = heightPerTile*(j+0.5)/mHeight;
						return true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean unregisterBot(Bot aBot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerGraphics(de.fh_kiel.robotics.mr.server.misc.graphics.interfaces.Graphics aGraphics) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadScenario() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel getScenarioGUI() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public JFrame getScenarioOptionsGUI() {
		// TODO Auto-generated method stub
		return new JFrame();
	}
}
