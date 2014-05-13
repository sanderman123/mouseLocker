package mouseLocker;

import java.awt.Point;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MouseLocker ml = new MouseLocker();
		new Thread(ml).run();
		while(true){
			//somehow the program never gets here...
			System.out.println("sdlfjsflj: "+ml.ul);
			Point ul = ml.getUL();
			Point lr = ml.getLR();		
			System.out.println("ul: "+ul);
			System.out.println("lr: "+lr);			
		}		
	}
}
