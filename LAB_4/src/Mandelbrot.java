import java.awt.geom.Rectangle2D.Double;

/**
 *  ласс, управл€ющий расчетом и начальным отображением фрактала ћандельброта
 * @author niksh
 *
 */
public class Mandelbrot extends FractalGenerator {
	/** ћаксимальное кол-во итераций при расчете */
	public static final int MAX_ITERATIONS = 2000;
	
	/** –азмеры отображаемого участка (DIM*DIM) */
	public static final double REC_DIMENSION = 3;
	
	/** x центр отображаемого участка */
	public static final double REC_CENTER_X = -2;
	
	/** y центр отображаемого участка */
	public static final double REC_CENTER_Y = -1.5;
	
	/**
	 * ”становка начального диапазона отображени€ фрактала
	 */
	@Override
	public void getInitialRange(Double range) {
		range.height = REC_DIMENSION;
		range.width = REC_DIMENSION;
		range.x = REC_CENTER_X;
		range.y = REC_CENTER_Y;
	}
	
	/**
	 * »теративна€ формула расчета принадлежности точки к множеству ћандельброта
	 * @param x - x координата, приведенна€ к реальной части числа на комплексной плоскости
	 * @param y - y координата, приведенна€ к комплексной части числа на комплексной плоскости
	 * @return  ол-во итераций расчета до выхода за пределы множества, -1 если расчеты не вышли за пределы
	 */
	@Override
	public int numIterations(double x, double y) {
		Complex coord = new Complex(x,y);
		Complex zIter = new Complex();
		int iter = 0;
		zIter = zIter.add(coord);
		while (zIter.absSqr() < 4 & iter < MAX_ITERATIONS) {
			iter += 1;
			zIter = zIter.mul(zIter).add(coord);
		}
		if (iter == 2000) return -1;
		return iter;
	}

}
