/**
 * �����, ��������� ������� ����� �� 2 �� 100 
 * @author ������ ������
 */

public class Primes{
	
	public static void main(String[] args) {
		for (int i = 2; i < 101; i++) {
			if (isPrime(i)) System.out.print(i + " ");
		}
	}
	
	/**
	 * �������, ����������� ����� num �� ��������, ����� ������� ��� �� ��� ����� �� 2 �� sqrt(num)
	 * @param num - ����� ����� ��� ��������
	 */
	public static boolean isPrime(int num) { 		
		int lim = (int) Math.ceil(Math.sqrt(num+1));
		for (int i = 2; i < lim; i++) { 			
			if (num%i == 0) return false;
		}
		return true;
	}
}