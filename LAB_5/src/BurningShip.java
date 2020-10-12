import java.awt.geom.Rectangle2D.Double;

/**
 * �����, ����������� �������� � ��������� ������������ �������� "������� �������"
 * @author niksh
 *
 */
public class BurningShip extends FractalGenerator {
	/** ������������ ���-�� �������� ��� ������� */
	public static final int MAX_ITERATIONS = 2000;
	
	/** ������� ������������� ������� (DIM*DIM) */
	public static final double REC_DIMENSION = 4;
	
	/** x ����� ������������� ������� */
	public static final double REC_CORNER_X = -2;
	
	/** y ����� ������������� ������� */
	public static final double REC_CORNER_Y = -2.5;
	
	/**
	 * ���������� ��� ��������
	 */
	public String toString() {
		return "������� �������";
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
	 * ����������� ������� ������� �������������� ����� � ��������� "������� �������"
	 * @param x - x ����������, ����������� � �������� ����� ����� �� ����������� ���������
	 * @param y - y ����������, ����������� � ����������� ����� ����� �� ����������� ���������
	 * @return ���-�� �������� ������� �� ������ �� ������� ���������, -1 ���� ������� �� ����� �� �������
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
