/**
 * 
 */
package assignment2.shape;

/**
 * @author Gavin Shrestha
 *
 */
public class Circle implements Shape {
	
	int r;
	double area;
	
	public Circle(int r) {
		this.r = r;
	}
	
	public void calculateArea() {
		area = Math.PI * r * r;
	}

	public void display() {
		System.out.println(area);
	}

}
