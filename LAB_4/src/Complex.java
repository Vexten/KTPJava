/**
 * ����� ����������� �����
 * @author niksh
 * @version 1.0
 */

public class Complex extends Number{
	private static final long serialVersionUID = 7746583188569394990L;

	/** �������� ������������ ����� */
	private double real;
	
	/** ����������� ������������ ����� */
	private double imag;
	
	/**
	 * ����������� - ������� ����������� ����� � ��������� ����������
	 * @param real - �������� ������������ �����
	 * @param imag - ����������� ������������ �����
	 */
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	/**
	 * ����������� - ������� ����������� ����� 0 + i0
	 */
	public Complex() {
		this(0,0);
	}
	
	/**
	 * �������� �������� ����������� �����
	 * @param n - ����������� ����� ��� ��������
	 * @return ����������� �����
	 */
	public Complex add(Complex n) {
		Complex res = new Complex();
		res.real = this.real + n.real;
		res.imag = this.imag + n.imag;
		return res;
	}
	
	/**
	 * �������� �������� ������������ � �������� �����
	 * @param n - ����� ��� ��������
	 * @return ����������� �����
	 */
	public Complex add(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real += (double) n;
		return res;
	}
	
	/**
	 * �������� ��������� ����������� �����
	 * @param n - ����������� ����� ��� ���������
	 * @return ����������� ��������
	 */
	public Complex sub(Complex n) {
		Complex res = new Complex();
		res.real = this.real - n.real;
		res.imag = this.imag - n.imag;
		return res;
	}
	
	/**
	 * �������� ��������� ������������ � �������� �����
	 * @param n - ����� ��� ���������
	 * @return ����������� ��������
	 */
	public Complex sub(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real -= (double) n;
		return res;
	}
	
	/**
	 * �������� ��������� ����������� �����
	 * @param n - ����������� ����� ��� ���������
	 * @return ����������� ������������
	 */
	public Complex mul(Complex n) {
		Complex res = new Complex();
		res.real = this.real*n.real - this.imag*n.imag;
		res.imag = this.real*n.imag + this.imag*n.real;
		return res;
	}
	
	/**
	 * �������� ��������� ������������ � �������� �����
	 * @param n - ����� ��� ���������
	 * @return ����������� ������������
	 */
	public Complex mul(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real *= (double)n;
		res.imag *= (double)n;
		return res;
	}
	
	/**
	 * �������� ������� ����������� �����
	 * @param n - ����������� ����� ��� �������
	 * @return ����������� �������
	 */
	public Complex div(Complex n) {
		Complex res = new Complex();
		double divisor = n.real*n.real + n.imag*n.imag;
		res.real = (this.real*n.real + this.imag*n.imag)/divisor;
		res.imag = (this.imag*n.real - this.real*n.imag)/divisor;
		return res;
	}
	
	/**
	 * �������� ������� ������������ � �������� �����
	 * @param n - ����� ��� �������
	 * @return ����������� �������
	 */
	public Complex div(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real /= (double) n;
		res.imag /= (double) n;
		return res;
	}
	
	/**
	 * ������� ������ ������������ �����
	 */
	public double absSqr() {
		return this.real*this.real + this.imag*this.imag;
	}
	
	/** ������ ������������ ����� ���� <code>double</code> */
	@Override
	public double doubleValue() {
		double res = Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
	
	/** ������ ������������ ����� ���� <code>float</code> */
	@Override
	public float floatValue() {
		float res = (float) Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
	
	/** ������ ������������ ����� ���� <code>int</code> */
	@Override
	public int intValue() {
		int res = (int) Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
	
	/** ������ ������������ ����� ���� <code>long</code> */
	@Override
	public long longValue() {
		long res = (long) Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
}
