import java.awt.geom.Rectangle2D.Double;

/**
 *  ласс, управл€ющий расчетом и начальным отображением фрактала "√ор€щий корабль"
 * @author niksh
 *
 */
public class BurningShip extends FractalGenerator {
	/** ћаксимальное кол-во итераций при расчете */
	public static final int MAX_ITERATIONS = 2000;
	
	/** –азмеры отображаемого участка (DIM*DIM) */
	public static final double REC_DIMENSION = 4;
	
	/** x центр отображаемого участка */
	public static final double REC_CORNER_X = -2;
	
	/** y центр отображаемого участка */
	public static final double REC_CORNER_Y = -2.5;
	
	/**
	 * ¬озвращает им€ фрактала
	 */
	public String toString() {
		return "√ор€щий корабль";
	}
	
	/**
	 * ”становка начального диапазона отображени€ фрактала
	 */
	@Override
	public void getInitialRange(Double range) {
		range.height = REC_DIMENSION;
		range.width = REC_DIMENSION;
		range.x = REC_CORNER_X;
		range.y = REC_CORNER_Y;
	}
	
	/**
	 * »теративна€ формула расчета принадлежности точки к множеству "√ор€щий корабль"
	 * @param x - x координата, приведенна€ к реальной части числа на комплексной плоскости
	 * @param y - y координата, приведенна€ к комплексной части числа на комплексной плоскости
	 * @return  ол-во итераций расчета до выхода за пределы множества, -1 если расчеты не вышли за пределы
	 */
	@Override
	public int numIterations(double x, double y) {
		Complex coord = new Complex(x,y);
		Complex zIter = new Complex();
		Complex temp = new Complex();
		int iter = 0;
		zIter = zIter.add(coord);
		while (zIter.absSqr() < 4 & iter < MAX_ITERATIONS) {
			iter += 1;
			temp.set(Math.abs(zIter.get('r')), Math.abs(zIter.get('i')));
			zIter = temp.mul(temp).add(coord);
			temp.zero();
		}
		if (iter == 2000) return -1;
		return iter;
	}

}
