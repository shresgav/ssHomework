/**
 * 
 */
package assignment2.shape;

/**
 * @author Gavin Shrestha
 *
 */
public class Rectangle implements Shape {
	private int h;
	private int w;
	private int area;
	
	
	public Rectangle(int h, int w) {
		this.h = h;
		this.w = w;
	}

	public void calculateArea() {
		area = h * w;
	}

	public void display() {
		System.out.println(area);
	}

}
