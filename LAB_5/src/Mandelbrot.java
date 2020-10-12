import java.awt.geom.Rectangle2D.Double;

/**
 * �����, ����������� �������� � ��������� ������������ �������� ������������
 * @author niksh
 *
 */
public class Mandelbrot extends FractalGenerator {
	/** ������������ ���-�� �������� ��� ������� */
	public static final int MAX_ITERATIONS = 2000;
	
	/** ������� ������������� ������� (DIM*DIM) */
	public static final double REC_DIMENSION = 3;
	
	/** x ����� ������������� ������� */
	public static final double REC_CORNER_X = -2;
	
	/** y ����� ������������� ������� */
	public static final double REC_CORNER_Y = -1.5;
	
	/**
	 * ���������� ��� ��������
	 */
	public String toString() {
		return "��������� ������������";
	}
	
	/**
	 * ��������� ���������� ��������� ����������� ��������
	 */
	@Override
	public void getInitialRange(Double range) {
		range.height = REC_DIMENSION;
		range.width = REC_DIMENSION;
		range.x = REC_CORNER_X;
		range.y = REC_CORNER_Y;
	}
	
	/**
	 * ����������� ������� ������� �������������� ����� � ��������� ������������
	 * @param x - x ����������, ����������� � �������� ����� ����� �� ����������� ���������
	 * @param y - y ����������, ����������� � ����������� ����� ����� �� ����������� ���������
	 * @return ���-�� �������� ������� �� ������ �� ������� ���������, -1 ���� ������� �� ����� �� �������
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
