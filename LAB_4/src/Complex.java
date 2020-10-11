/**
 * Класс комплексных чисел
 * @author niksh
 * @version 1.0
 */

public class Complex extends Number{
	private static final long serialVersionUID = 7746583188569394990L;

	/** Реальная составляющая числа */
	private double real;
	
	/** Комплексная составляющая числа */
	private double imag;
	
	/**
	 * Конструктор - создает комплексное число с заданными значениями
	 * @param real - реальная составляющая числа
	 * @param imag - комплексная составляющая числа
	 */
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	/**
	 * Конструктор - создает комплексное число 0 + i0
	 */
	public Complex() {
		this(0,0);
	}
	
	/**
	 * Операция сложения комплексных чисел
	 * @param n - комплексное число для сложения
	 * @return Комплексная сумма
	 */
	public Complex add(Complex n) {
		Complex res = new Complex();
		res.real = this.real + n.real;
		res.imag = this.imag + n.imag;
		return res;
	}
	
	/**
	 * Операция сложения комплексного и обычного числа
	 * @param n - число для сложения
	 * @return Комплексная сумма
	 */
	public Complex add(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real += (double) n;
		return res;
	}
	
	/**
	 * Операция вычитания комплексных чисел
	 * @param n - комплексное число для вычитания
	 * @return Комплексная разность
	 */
	public Complex sub(Complex n) {
		Complex res = new Complex();
		res.real = this.real - n.real;
		res.imag = this.imag - n.imag;
		return res;
	}
	
	/**
	 * Операция вычитания комплексного и обычного числа
	 * @param n - число для вычитания
	 * @return Комплексная разность
	 */
	public Complex sub(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real -= (double) n;
		return res;
	}
	
	/**
	 * Операция умножения комплексных чисел
	 * @param n - комплексное число для умножения
	 * @return Комплексное произведение
	 */
	public Complex mul(Complex n) {
		Complex res = new Complex();
		res.real = this.real*n.real - this.imag*n.imag;
		res.imag = this.real*n.imag + this.imag*n.real;
		return res;
	}
	
	/**
	 * Операция умножения комплексного и обычного числа
	 * @param n - число для умножения
	 * @return Комплексное произведение
	 */
	public Complex mul(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real *= (double)n;
		res.imag *= (double)n;
		return res;
	}
	
	/**
	 * Операция деления комплексных чисел
	 * @param n - комплексное число для деления
	 * @return Комплексное частное
	 */
	public Complex div(Complex n) {
		Complex res = new Complex();
		double divisor = n.real*n.real + n.imag*n.imag;
		res.real = (this.real*n.real + this.imag*n.imag)/divisor;
		res.imag = (this.imag*n.real - this.real*n.imag)/divisor;
		return res;
	}
	
	/**
	 * Операция деления комплексного и обычного числа
	 * @param n - число для деления
	 * @return Комплексное частное
	 */
	public Complex div(Number n) {
		Complex res = new Complex(this.real, this.imag);
		res.real /= (double) n;
		res.imag /= (double) n;
		return res;
	}
	
	/**
	 * Квадрат модуля комплексного числа
	 */
	public double absSqr() {
		return this.real*this.real + this.imag*this.imag;
	}
	
	/** Модуль комплексного числа типа <code>double</code> */
	@Override
	public double doubleValue() {
		double res = Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
	
	/** Модуль комплексного числа типа <code>float</code> */
	@Override
	public float floatValue() {
		float res = (float) Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
	
	/** Модуль комплексного числа типа <code>int</code> */
	@Override
	public int intValue() {
		int res = (int) Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
	
	/** Модуль комплексного числа типа <code>long</code> */
	@Override
	public long longValue() {
		long res = (long) Math.sqrt(this.real*this.real + this.imag*this.imag);
		return res;
	}
}
