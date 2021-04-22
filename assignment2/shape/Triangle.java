/**
 * 
 */
package assignment2.shape;

/**
 * @author Gavin Shrestha
 *
 */
public class Triangle implements Shape {
	int h;
	int w;
	double area;
	
	public Triangle(int h, int w) {
		this.h = h;
		this.w = w;
	}
	
	public void calculateArea() {
		area = h * w * 0.5;
	}

	public void display() {
		System.out.println(area);
	}

}
