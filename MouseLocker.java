package mouseLocker;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import javax.swing.JFrame;

public class MouseLocker implements Runnable {

	public static Point ul = new Point();
	private static Point lr = new Point();
	private static Point mLoc = new Point();

	/*public MouseLocker(String str) {
		super(str);
	}*/
	
	/**
	 * 
	 * @return Upper Left corner of the mouse cage
	 */
	public Point getUL(){
		return ul;
	}
	
	/**
	 * 
	 * @return Lower Right corner of the mouse cage
	 */
	public Point getLR(){
		return lr;
	}
	
	/**
	 * 
	 * @return Current mouse location
	 */
	public Point getMLoc(){
		return mLoc;
	}

	private static Point lockMouse(Point mLoc, Point ul, Point lr) {
		Point newMloc = new Point(mLoc.x, mLoc.y);
		if (newMloc.x > lr.x) {
			newMloc.x = lr.x;
		}
		if (newMloc.y > lr.y) {
			newMloc.y = lr.y;
		}
		if (newMloc.x < ul.x) {
			newMloc.x = ul.x;
		}
		if (newMloc.y < ul.y) {
			newMloc.y = ul.y;
		}
		return newMloc;
	}

	private static Point[] selectCorners(KeyEventListener kel, Point ul,
			Point lr) {
		char c = 'c';
		Point corners[] = new Point[2];
		Point mLoc = null;
		boolean ulSet = false;
		boolean lrSet = false;
		while (c != 3) {
			if (kel.newChar()) {
				c = kel.getC();
				mLoc = MouseInfo.getPointerInfo().getLocation();
				switch (c) {
				case '1':
					ul = new Point(mLoc.x, mLoc.y);
					ulSet = true;
					break;
				case '2':
					lr = new Point(mLoc.x, mLoc.y);
					lrSet = true;
					break;
				case '3':
					if (ulSet && lrSet) {
						if (ul.x > lr.x) {
							int ulx2 = ul.x;
							ul.x = lr.x;
							lr.x = ulx2;
						}
						if (ul.y > lr.y) {
							int uly2 = ul.y;
							ul.y = lr.y;
							lr.y = uly2;
						}
						corners[0] = ul;
						corners[1] = lr;
						return corners;
					} else {
						String message = "";
						if (!ulSet)
							message = "Upper Left not set!!";
						if (!lrSet)
							message = "Lower Right not set!!";

						System.out.println(message);
						kel.setMessage(message);
						break;
					}
				default:
					System.out.println("Wrong character pressed!!!");
				}
			}
		}
		return corners;
	}

	// public static void main(String[] args) {
	public void run() {
		// TODO Auto-generated method stub
		KeyEventListener kel = new KeyEventListener();
		JFrame f = new JFrame();
		f.getContentPane().add(kel);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);

		String message = "Please press 1 to set the upper left corner at the mouse position \r\n Please press 2 to set the lower right corner at the mouse position \r\n Press 3 when done";
		System.out.println(message);
		kel.setMessage(message);

		ul = new Point();
		lr = new Point();
		mLoc = new Point();
		Point corners[] = new Point[2];

		corners = selectCorners(kel, ul, lr);
		ul = corners[0];
		lr = corners[1];
		kel.kill();

		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			// Check mouse location and correct if necessary
			mLoc = MouseInfo.getPointerInfo().getLocation();
			Point newMloc = lockMouse(mLoc, ul, lr);
			// System.out.println("mLoc: " + mLoc.x + ", " + mLoc.y);
			// System.out.println("newMLoc: " + newMloc.x + ", " + newMloc.y);
			if (mLoc.x != newMloc.x || mLoc.y != newMloc.y) {
				r.mouseMove(newMloc.x, newMloc.y);
			}
		}
	}
}
