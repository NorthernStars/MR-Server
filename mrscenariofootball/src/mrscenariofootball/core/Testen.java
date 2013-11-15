package mrscenariofootball.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.collision.BoundsListener;
import org.dyn4j.collision.Collidable;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Capsule;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Slice;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to show a simple example of using the dyn4j project using
 * Java2D for rendering.
 * <p>
 * This class can be used as a starting point for projects.
 * @author William Bittle
 * @version 3.1.5
 * @since 3.0.0
 */
public class Testen extends JFrame implements BoundsListener {
	/** The serial version id */
	private static final long serialVersionUID = 5663760293144882635L;
	
	/** The scale 45 pixels per meter */
	public static final double SCALE = 500.0;
	
	/** The conversion factor from nano to base */
	public static final double NANO_TO_BASE = 1.0e9;
	
	/**
	 * Custom Body class to add drawing functionality.
	 * @author William Bittle
	 * @version 3.0.2
	 * @since 3.0.0
	 */
	public static class GameObject extends Body {
		/** The color of the object */
		protected Color color;
		
		/**
		 * Default constructor.
		 */
		public GameObject() {
			// randomly generate the color
			this.color = new Color(
					(float)Math.random() * 0.5f + 0.5f,
					(float)Math.random() * 0.5f + 0.5f,
					(float)Math.random() * 0.5f + 0.5f);
		}
		
		/**
		 * Draws the body.
		 * <p>
		 * Only coded for polygons and circles.
		 * @param g the graphics object to render to
		 */
		public void render(Graphics2D g) {
			// save the original transform
			AffineTransform ot = g.getTransform();
			
			// transform the coordinate system from world coordinates to local coordinates
			AffineTransform lt = new AffineTransform();
			lt.translate(this.transform.getTranslationX() * SCALE, this.transform.getTranslationY() * SCALE);
			lt.rotate(this.transform.getRotation());
			
			// apply the transform
			g.transform(lt);
			
			// loop over all the body fixtures for this body
			for (BodyFixture fixture : this.fixtures) {
				// get the shape on the fixture
				Convex convex = fixture.getShape();
				Graphics2DRenderer.render(g, convex, SCALE, color);
			}
			
			// set the original transform
			g.setTransform(ot);
		}
	}
	
	/** The canvas to draw to */
	protected Canvas canvas;
	
	/** The dynamics engine */
	protected World world;
	
	/** Wether the example is stopped or not */
	protected boolean stopped;
	
	/** The time stamp for the last iteration */
	protected long last;

	private GameObject circle;
	
	/**
	 * Default constructor for the window
	 */
	public Testen() {
		super("Graphics2D Example");
		// setup the JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add a window listener
		this.addWindowListener(new WindowAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				// before we stop the JVM stop the example
				stop();
				super.windowClosing(e);
			}
		});
		
		// create the size of the window
		Dimension size = new Dimension(800, 600);
		
		// create a canvas to paint to 
		this.canvas = new Canvas();
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				world.removeBody(circle);
				
				Circle cirShape = new Circle(0.01);
				circle = new GameObject();
				circle.addFixture(cirShape);
				circle.setMass( new Mass(new Vector2(0, 0), 10.0, 1.0));
				circle.translate( new Vector2( e.getX()/SCALE , e.getY()/SCALE ) );
				circle.applyForce( new Vector2( 100, 0 ) );
				// set some linear damping to simulate rolling friction
				circle.setLinearDamping(1);
				circle.setAutoSleepingEnabled(false);
				
				world.addBody(circle);
				
			}
		});
		this.canvas.setPreferredSize(size);
		this.canvas.setMinimumSize(size);
		this.canvas.setMaximumSize(size);
		
		// add the canvas to the JFrame
		getContentPane().add(this.canvas);
		
		// make the JFrame not resizable
		// (this way I dont have to worry about resize events)
		this.setResizable(false);
		
		// size everything
		this.pack();
		
		// make sure we are not stopped
		this.stopped = false;
		
		// setup the world
		this.initializeWorld();
	}
	
	/**
	 * Creates game objects and adds them to the world.
	 * <p>
	 * Basically the same shapes from the Shapes test in
	 * the TestBed.
	 */
	protected void initializeWorld() {
		// create the world
		AxisAlignedBounds vBounds = new AxisAlignedBounds( 1.0, 0.75 ); 
		vBounds.shiftCoordinates( new Vector2( 0.5, 0.75*0.5 ) );
		this.world = new World( vBounds );
		Settings vSettings = new Settings();
		vSettings.setStepFrequency( 1.0/20.0 );
		System.out.println(Settings.DEFAULT_STEP_FREQUENCY);
		world.setGravity(new Vector2(0, 0));
		world.addListener(this);
		
		Circle cirShape = new Circle(0.01);
		circle = new GameObject();
		circle.addFixture(cirShape);
		circle.setMass( new Mass(new Vector2(0, 0), 10.0, 1.0));
		circle.translate(0, 0);
		circle.applyForce( new Vector2( 100, 0 ) );
		// set some linear damping to simulate rolling friction
		circle.setLinearDamping(1);
		circle.setAutoSleepingEnabled(false);
		this.world.addBody(circle);
		
	}
	
	/**
	 * Start active rendering the example.
	 * <p>
	 * This should be called after the JFrame has been shown.
	 */
	public void start() {
		// initialize the last update time
		this.last = System.nanoTime();
		// don't allow AWT to paint the canvas since we are
		this.canvas.setIgnoreRepaint(true);
		// enable double buffering (the JFrame has to be
		// visible before this can be done)
		this.canvas.createBufferStrategy(2);
		// run a separate thread to do active rendering
		// because we don't want to do it on the EDT
		Thread thread = new Thread() {
			public void run() {
				// perform an infinite loop stopped
				// render as fast as possible
				Vector2 vVector;
				
				while (!isStopped()) {
					gameLoop();
					// you could add a Thread.yield(); or
					// Thread.sleep(long) here to give the
					// CPU some breathing room
					if( false && circle.getLinearVelocity().getMagnitude() < 1){
						 
						vVector = new Vector2( (Math.random()*2 - 1) * 100, (Math.random()*2 - 1) * 100);
						circle.applyForce( vVector );
						circle.applyTorque( (Math.random()*2 - 1) * 100 );
						
					}
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		// set the game loop thread to a daemon thread so that
		// it cannot stop the JVM from exiting
		thread.setDaemon(true);
		// start the game loop
		thread.start();
	}
	
	/**
	 * The method calling the necessary methods to update
	 * the game, graphics, and poll for input.
	 */
	protected void gameLoop() {
		// get the graphics object to render to
		Graphics2D g = (Graphics2D)this.canvas.getBufferStrategy().getDrawGraphics();
		
		// before we render everything im going to flip the y axis and move the
		// origin to the center (instead of it being in the top left corner)
		
		
		// now (0, 0) is in the center of the screen with the positive x axis
		// pointing right and the positive y axis pointing up
		
		// render anything about the Example (will render the World objects)
		this.render(g);
		
		// dispose of the graphics object
		g.dispose();
		
		// blit/flip the buffer
		BufferStrategy strategy = this.canvas.getBufferStrategy();
		if (!strategy.contentsLost()) {
			strategy.show();
		}
		
		// Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
        
        // update the World
        
        // get the current time
        long time = System.nanoTime();
        // get the elapsed time from the last iteration
        long diff = time - this.last;
        // set the last time
        this.last = time;
    	// convert from nanoseconds to seconds
    	double elapsedTime = (double)diff / NANO_TO_BASE;
        // update the world with the elapsed time
    	
        this.world.update(elapsedTime);
	}

	/**
	 * Renders the example.
	 * @param g the graphics object to render to
	 */
	protected void render(Graphics2D g) {
		// lets draw over everything with a white background
		g.setColor(Color.WHITE);
		g.fillRect( 0, 0, 800, 600);
		
		// draw all the objects in the world
		for (int i = 0; i < this.world.getBodyCount(); i++) {
			// get the object
			GameObject go = (GameObject) this.world.getBody(i);
			// draw the object
			go.render(g);
		}

		g.drawRect( 0, 0, (int) (SCALE), (int) (0.75*SCALE));
		
	}
	
	/**
	 * Stops the example.
	 */
	public synchronized void stop() {
		this.stopped = true;
	}
	
	/**
	 * Returns true if the example is stopped.
	 * @return boolean true if stopped
	 */
	public synchronized boolean isStopped() {
		return this.stopped;
	}
	
	/**
	 * Entry point for the example application.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		// set the look and feel to the system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		// create the example JFrame
		Testen window = new Testen();
		
		// show it
		window.setVisible(true);
		
		// start it
		window.start();
	}

	@Override
	public <E extends Collidable> void outside(E arg0) {
		// TODO Auto-generated method stub
		GameObject vDing = (GameObject) arg0;
		System.out.println( "outside " + System.currentTimeMillis() );
		vDing.setActive(true);
		if( vDing.getWorldCenter().x > 1 || vDing.getWorldCenter().x < 0 ){
			
			this.world.removeBody(circle);
			
			Circle cirShape = new Circle(0.01);
			circle = new GameObject();
			circle.addFixture(cirShape);
			circle.setMass( new Mass(new Vector2(0, 0), 50.0, 1.0));
			circle.translate(0.5, 0.75*0.5);
			// set some linear damping to simulate rolling friction
			circle.setLinearDamping(1);
			circle.setAutoSleepingEnabled(false);
			this.world.addBody(circle);
			
		} 
		if( vDing.getWorldCenter().y > 0.75 || vDing.getWorldCenter().y < 0 ) {
			vDing.setLinearVelocity( new Vector2( vDing.getLinearVelocity().x, -vDing.getLinearVelocity().y ));
		}
			
	}
}
