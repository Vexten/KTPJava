/**
 *  ласс, который провер€ет, €вл€етс€ ли строка палиндромом
 * @author niksh
 */

public class Palindrome {
	
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.print(args[i] + " ");
			if (isPalindrome(args[i])) System.out.print(true + "\n");
			else System.out.print(false + "\n");
		}
	}
	
	/**
	 * ‘ункци€, возвращающа€ реверсированную строку
	 * @param orig - строка дл€ обработки
	 * @return —трока, обратна€ orig
	 */
	public static String reverseString(String orig) {		
		String rev = "";
		for (int i = orig.length()-1; i > -1; i--) {		
			rev += orig.charAt(i);							
		}
		return rev;
	}
	
	/**
	 * ‘ункци€, провер€юща€ €вл€етс€ ли строка палиндромом,
	 * сравнива€ ее с реверсированной версеей себ€
	 * @see Palindrome#reverseString(String)
	 * @param str - строка дл€ проверки
	 * @return <code>true</code>, если €вл€етс€, <code>false</code>, если не €вл€етс€
	 */
	public static boolean isPalindrome(String str) {
		if (str.equals(reverseString(str))) return true;
		return false;
	}
}
