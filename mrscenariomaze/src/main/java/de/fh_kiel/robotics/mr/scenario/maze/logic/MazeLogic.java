package de.fh_kiel.robotics.mr.scenario.maze.logic;

import de.fh_kiel.robotics.mr.server.misc.bots.interfaces.Bot;

import java.util.concurrent.atomic.AtomicBoolean;

public class MazeLogic extends Thread {

    public int[][] mMaze = new int[][] {
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

    public int[][] getMaze() {
        return mMaze;
    }

    private final double mSimulationBotSpeed = 0.006;

    private void moveBot( Movement aMovement ) {

        double vSpeed, vRotation, vRightDegree, vLeftDegree;

        vSpeed = ( aMovement.getLeftWheelVelocity() + aMovement.getRightWheelVelocity() ) / 200.0 * mSimulationBotSpeed;

        vRightDegree = Math.toDegrees( Math.atan2( 1.0, aMovement.getRightWheelVelocity() / 100.0 ) );
        vLeftDegree = Math.toDegrees( Math.atan2( 1.0, aMovement.getLeftWheelVelocity() / 100.0 ) );

        vRotation = (vRightDegree + vLeftDegree ) / 2 - vRightDegree;
        vRotation /= 1.5;

        double vNewRotation = mMazeRunner.getOrientation() + vRotation;
        double vNewPositionX = mMazeRunner.getPositionX() + vSpeed * Math.cos( Math.toRadians( vNewRotation ) );
        double vNewPositionY = mMazeRunner.getPositionY() + vSpeed * Math.sin( Math.toRadians( vNewRotation ) );

        if( mMaze[(int) (vNewPositionY*mMaze.length)][(int) (vNewPositionX*mMaze[0].length)] == 0 ||
            vNewPositionX < 0 || vNewPositionX > 1.0 ||
            vNewPositionY < 0 || vNewPositionY > 1.0 ){

            System.out.println(mMaze[(int) (vNewPositionX*mMaze.length)][(int) (vNewPositionY*mMaze[0].length)] + " " + (int) (vNewPositionX*mMaze.length) + " " + (int) (vNewPositionY*mMaze[0].length) );
            return;
        }

        mMazeRunner.setOrientation( vNewRotation );

        mMazeRunner.setPosition( vNewPositionX, vNewPositionY );

    }

    private  MazeRunner mMazeRunner = null;

    public MazeRunner getMazeRunner() {
        return mMazeRunner;
    }

    public boolean setMazeRunner(Bot aMazeRunner) {
        synchronized(this) {
            mMazeRunner = new MazeRunner(aMazeRunner);
            for (int i = 0; i < mMaze.length; i++) {
                for (int j = 0; j < mMaze[i].length; j++) {
                    if (mMaze[i][j] == 9) {
                        mMazeRunner.setPosition( (1.0/mMaze[i].length)*(j+0.5), (1.0/mMaze.length)*(i+0.5));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void startLogic() {

        if( !isAlive() && mRunning.get() ) {

            super.start();

        }

    }

    public void stopLogic(){

        if( isAlive()){

            while( isAlive() ){

                try {

                    mRunning.set(false);
                    Thread.sleep( 10 );

                } catch ( InterruptedException vInterruptedException ) {
                    vInterruptedException.printStackTrace();
                }
            }

        }

    }

    AtomicBoolean mRunning = new AtomicBoolean(true);

    @Override
    public void run(){

        while(mRunning.get()) {
            synchronized(this) {
                if (mMazeRunner != null) {
                    if(mMazeRunner.getLastAction() != null) {
                        Command vCommand = Command.unmarshallXMLPositionDataPackageString( mMazeRunner.getLastAction() );

                        if( vCommand != null ){

                            if( vCommand.isMovement() ){

                                moveBot( vCommand.getMovement() );

                            }
                        }
                    }

                    mMazeRunner.sendPosition(mMaze);
                }
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(){

        this.startLogic();

    }

}
