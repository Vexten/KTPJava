import java.awt.geom.Rectangle2D.Double;

/**
 * Класс, управляющий расчетом и начальным отображением фрактала Мандельброта
 * @author niksh
 *
 */
public class Mandelbrot extends FractalGenerator {
	/** Максимальное кол-во итераций при расчете */
	public static final int MAX_ITERATIONS = 2000;
	
	/** Размеры отображаемого участка (DIM*DIM) */
	public static final double REC_DIMENSION = 3;
	
	/** x центр отображаемого участка */
	public static final double REC_CORNER_X = -2;
	
	/** y центр отображаемого участка */
	public static final double REC_CORNER_Y = -1.5;
	
	/**
	 * Возвращает имя фрактала
	 */
	public String toString() {
		return "Множество Мандельброта";
	}
	
	/**
	 * Установка начального диапазона отображения фрактала
	 */
	@Override
	public void getInitialRange(Double range) {
		range.height = REC_DIMENSION;
		range.width = REC_DIMENSION;
		range.x = REC_CORNER_X;
		range.y = REC_CORNER_Y;
	}
	
	/**
	 * Итеративная формула расчета принадлежности точки к множеству Мандельброта
	 * @param x - x координата, приведенная к реальной части числа на комплексной плоскости
	 * @param y - y координата, приведенная к комплексной части числа на комплексной плоскости
	 * @return Кол-во итераций расчета до выхода за пределы множества, -1 если расчеты не вышли за пределы
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
