package physics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainWindow {
	public final int MAX_SPAWN = 30;
	public static final int X = 640;
	public static final int Y = 480;
	public final String TITLE = "2D Physics Engine";
	private JFrame f;
	private Canvas c;
	public BufferStrategy b;
	private GraphicsEnvironment ge;
	private GraphicsDevice gd;
	private GraphicsConfiguration gc;
	private BufferedImage buffer;
	private Graphics graphics;
	private Graphics2D g2d;
	private AffineTransform at;

	MoveEngine moveEngine;

	public static void main(String[] args) {
		new MainWindow().start();
	}

	public void start() {
		// Initialize some things.
		initializeJFrame();

		// Create and start simulation.
		moveEngine = new MoveEngine();
		giveBirth(20, 20, 200, 200, 10);
		giveBirth(300, 20, -200, 200, 20);
		giveBirth(30, 100, 400, 50, 10);
		giveBirth(400, 100, -400, 200, 10);
		giveBirth(150, 300, 0, -200, 10);
		moveEngine.start();

		moveEngine.setRunning(true);

		// Run the animation loop.
		runAnimation();
	}

	public void runAnimation() {
		// Set up some variables.
		int fps = 0;
		int frames = 0;
		long totalTime = 0;
		long curTime = System.currentTimeMillis();
		long lastTime = curTime;
		// Start the loop.
		while (true) {
			try {
				// Calculations for FPS.
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				totalTime += curTime - lastTime;
				if (totalTime > 1000) {
					totalTime -= 1000;
					fps = frames;
					frames = 0;
				}
				++frames;
				// clear back buffer...
				g2d = buffer.createGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, X, Y);
				// Draw entities
				ArrayList<Spawn> living = moveEngine.getLiving();
				for (int i = 0; i < living.size(); i++) {
					at = new AffineTransform();
					at.translate(living.get(i).getX(), living.get(i).getY());
					g2d.setColor(Color.BLACK);
					Spawn s = living.get(i);
					g2d.fill(new Ellipse2D.Double(s.getX(), s.getY(), s.getRadius() * 2, s.getRadius() * 2));
				}
				// display frames per second...
				g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
				g2d.setColor(Color.GREEN);
				g2d.drawString(String.format("FPS: %s", fps), 20, 20);
				// Blit image and flip...
				graphics = b.getDrawGraphics();
				graphics.drawImage(buffer, 0, 0, null);
				if (!b.contentsLost())
					b.show();
				// Let the OS have a little time...
				Thread.sleep(15);
			} catch (InterruptedException e) {
			} finally {
				// release resources
				if (graphics != null)
					graphics.dispose();
				if (g2d != null)
					g2d.dispose();
			}
		}
	}

	public boolean allDead() {
		ArrayList<Spawn> living = moveEngine.getLiving();
		if (living.size() < 1)
			return true;
		return false;
	}

	public synchronized int giveBirth(int x, int y, double vx, double vy, int m) {
		ArrayList<Spawn> living = moveEngine.getLiving();
		if (living.size() >= MAX_SPAWN)
			return 1;
		living.add(new Spawn(x, y, vx, vy, m));
		return 0;
	}

	private void initializeJFrame() {
		// Create the frame...
		f = new JFrame(TITLE);
		f.setIgnoreRepaint(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create canvas for painting...
		c = new Canvas();
		c.setIgnoreRepaint(true);
		c.setSize(X, Y);
		// Add the canvas, and display.
		f.add(c);
		f.pack();
		// The following line centers the window on the screen.
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		// Set up the BufferStrategy for double buffering.
		c.createBufferStrategy(2);
		b = c.getBufferStrategy();
		// Get graphics configuration...
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
		gc = gd.getDefaultConfiguration();
		// Create off-screen drawing surface
		buffer = gc.createCompatibleImage(X, Y);
		// Objects needed for rendering...
		graphics = null;
		g2d = null;
	}
}
