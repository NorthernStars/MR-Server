package de.fh_kiel.robotics.mr.scenario.maze.gui;

import de.fh_kiel.robotics.mr.scenario.maze.*;
import de.fh_kiel.robotics.mr.scenario.maze.logic.MazeLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MazeGui extends JPanel implements ComponentListener {

    private BufferedImage mWallImage, mBackGround;
    private JPopupMenu mMazePopupMenu;
    private float mPointSize, mHalfPointSize, mLineWidth;
    private Graphics2D g2d;
    public int mWidth, mHeight;
    private Font mFont;
    private Stroke mDashedStroke, mNormalStroke;
    public int widthPerTile;
    public int heightPerTile;

    final MazeLogic mLogic;

    public MazeGui(MazeLogic aLogic) {
        mLogic = aLogic;

        setBackground(new Color(255, 255, 255));
        setLayout(null);

        addComponentListener(this);

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

        mRefreshTimer.start();

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

            widthPerTile = (int) mWidth / mLogic.getMaze().length;
            heightPerTile = (int) mHeight / mLogic.getMaze().length;
            for (int i = 0; i < mLogic.getMaze().length; i++) {
                for (int j = 0; j < mLogic.getMaze()[i].length; j++) {
                    if (mLogic.getMaze()[i][j] == 0) {
                        g2d.drawImage(mWallImage,
                                widthPerTile * j,
                                heightPerTile * i,
                                widthPerTile,
                                heightPerTile,
                                this);
                    } else if (mLogic.getMaze()[i][j] == 9) {
                        g2d.setColor(Color.blue);
                        g2d.fillRect(
                                widthPerTile * j,
                                heightPerTile * i,
                                widthPerTile,
                                heightPerTile);
                    } else if (mLogic.getMaze()[i][j] == 8) {
                        g2d.setColor(Color.red);
                        g2d.fillRect(
                                widthPerTile * j,
                                heightPerTile * i,
                                widthPerTile,
                                heightPerTile);
                    }
                }
            }

            if(mLogic.getMazeRunner() != null){

                java.util.List<double[]> vPath = new ArrayList<>(mLogic.getMazeRunner().getPath());
                Collections.reverse(vPath);
                double[] vPreviousStop = new double[]{ mLogic.getMazeRunner().getPositionX(), mLogic.getMazeRunner().getPositionY() };
                g2d.setColor(Color.DARK_GRAY);
                for( double[] vStop: vPath){
                    g2d.drawLine( (int)( mWidth * vPreviousStop[0] ),
                            (int)( mHeight * vPreviousStop[1] ),
                            (int)( mWidth * vStop[0] ),
                            (int)( mHeight * vStop[1] ));
                    vPreviousStop = vStop;
                }

                g2d.setColor(Color.MAGENTA);
                g2d.drawLine( (int)( mWidth * mLogic.getMazeRunner().getPositionX() ),
                        (int)( mHeight * mLogic.getMazeRunner().getPositionY() ),
                        (int)( mWidth * mLogic.getMazeRunner().getPositionX() + 0.010 * mWidth * Math.cos( Math.toRadians( -mLogic.getMazeRunner().getOrientation() ) ) ),
                        (int)( mHeight * mLogic.getMazeRunner().getPositionY() + 0.010 * mWidth * Math.sin( Math.toRadians( -mLogic.getMazeRunner().getOrientation() ) ) ) );

                g2d.fillOval( (int)( mWidth * mLogic.getMazeRunner().getPositionX() - mHalfPointSize ),
                        (int)( mHeight * mLogic.getMazeRunner().getPositionY() - mHalfPointSize ),
                        (int)( mPointSize ),
                        (int)( mPointSize ) );

                g2d.drawOval( (int)( mWidth * mLogic.getMazeRunner().getPositionX() - 0.010 * mWidth ),
                        (int)( mHeight * mLogic.getMazeRunner().getPositionY()- 0.010 * mWidth ),
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

    public final static int INTERVAL = 25;

    Timer mRefreshTimer = new Timer(INTERVAL, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {

            repaint();
            validate();

            if(false) {
                mRefreshTimer.stop();
            }
        }
    });

    @Override
    public void componentResized(ComponentEvent e) {
        createBackground();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
