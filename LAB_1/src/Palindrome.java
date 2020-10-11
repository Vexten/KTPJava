/**
 * �����, ������� ���������, �������� �� ������ �����������
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
	 * �������, ������������ ��������������� ������
	 * @param orig - ������ ��� ���������
	 * @return ������, �������� orig
	 */
	public static String reverseString(String orig) {		
		String rev = "";
		for (int i = orig.length()-1; i > -1; i--) {		
			rev += orig.charAt(i);							
		}
		return rev;
	}
	
	/**
	 * �������, ����������� �������� �� ������ �����������,
	 * ��������� �� � ��������������� ������� ����
	 * @see Palindrome#reverseString(String)
	 * @param str - ������ ��� ��������
	 * @return <code>true</code>, ���� ��������, <code>false</code>, ���� �� ��������
	 */
	public static boolean isPalindrome(String str) {
		if (str.equals(reverseString(str))) return true;
		return false;
	}
}
