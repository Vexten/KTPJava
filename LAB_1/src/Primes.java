/**
 * Класс, выводящий простые числа от 2 до 100 
 * @author Никита Шевцов
 */

public class Primes{
	
	public static void main(String[] args) {
		for (int i = 2; i < 101; i++) {
			if (isPrime(i)) System.out.print(i + " ");
		}
	}
	
	/**
	 * Функция, проверяющая число num на простоту, путем деления его на все числа от 2 до sqrt(num)
	 * @param num - целое число для проверки
	 */
	public static boolean isPrime(int num) { 		
		int lim = (int) Math.ceil(Math.sqrt(num+1));
		for (int i = 2; i < lim; i++) { 			
			if (num%i == 0) return false;
		}
		return true;
	}
}