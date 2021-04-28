package assignment4.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LineTest {
	@Test
	public void getSlopeTestBasic() {
		Line a = new Line(0.0, 0.0, 1.0, 1.0);
		assertEquals(1.0, a.getSlope(), 0.0001);
		assertNotEquals(0.0, a.getSlope(), 0.0001);
	}
	
	@Test
	public void getSlopeTestEdge() {
		Line a = new Line(0.0, 0.0, 1.0, 0.0);
		assertEquals(0.0, a.getSlope(), 0.0001);
		assertNotEquals(1.0, a.getSlope(), 0.0001);
	}
	
	@Test(expected = ArithmeticException.class)
	public void expectExceptionSlope() {
		Line a = new Line(0.0, 0.0, 0.0, 1.0);
		a.getSlope();
	}
	
	@Test
	public void distanceTestBasic() {
		Line a = new Line(0.0, 0.0, 1.0, 0.0);
		assertEquals(1.0, a.getDistance(), 0.0001);
		assertNotEquals(0.0, a.getDistance(), 0.0001);
		
		Line b = new Line(0.0, 0.0, 3.0, 4.0);
		assertEquals(5.0, b.getDistance(), 0.0001);
		assertNotEquals(0.0, b.getDistance(), 0.0001);
	}
	
	@Test
	public void distanceTestEdge() {
		Line a = new Line(0.0, 0.0, 0.0, 0.0);
		assertEquals(0.0, a.getDistance(), 0.0001);
		assertNotEquals(1.0, a.getDistance(), 0.0001);
	}
	
	@Test
	public void parallelToTestBasic() {
		Line a = new Line(0.0, 0.0, 3.0, 4.0);
		Line b = new Line(0.0, 0.0, 1.0, 1.0);
		Line c = new Line(0.0, 0.0, 2.0, 2.0);
		
		assertTrue(a.parallelTo(a));
		assertTrue(b.parallelTo(c));
		assertFalse(a.parallelTo(b));
		
	}
	
	@Test(expected = ArithmeticException.class)
	public void expectExceptionParallel() {
		Line a = new Line(0.0, 0.0, 0.0, 1.0);
		Line b = new Line(0.0, 0.0, 1.0, 0.0);
		a.parallelTo(b);
	}
}
