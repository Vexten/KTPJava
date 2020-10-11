/**
 * �����, �������������� ����� � 3D ������������
 * @author niksh
 */

public class Point3D {
	/** ������ ���������, ��� [0] - x, [1] - y, [2] - z */
	private double[] coordinates = new double[3];
	
	/**
	 * ����������� - �������� ����� � ������������� ������������
	 * @param crds - ���������� ����� � 3D ������������ (����� ������� ������ x, ��� ������ x y)
	 */
	public Point3D(String[] crds) {
		int i;
		for (i = 0; i < crds.length; i++) { 		// ��������� ������ ��������� �� ��������� ������
			this.coordinates[i] = Double.parseDouble(crds[i]);
		}
		for (i += 0; i < 3; i++) {					// ��������� ������ ���������� 0.0
			this.coordinates[i] = 0.0;
		}
	}
	
	/**
	 * �������� ���������� �����
	 * @param crd - ��������� �� ����������, x, y ��� z
	 * @return ����������� ����������
	 */
	public double getCrd(char crd) {
		switch (crd) {
		case ('x'):
			return this.coordinates[0];
		case ('y'):
			return this.coordinates[1];
		case ('z'):
			return this.coordinates[2];
		}
		return 0.0;
	}
	
	/**
	 * �������� ��������� ���������� �����
	 * @param crd - ��������� �� ����������, x, y ��� z
	 * @param val - �������� ���������� ����� ���������
	 * @return <code>true</code>, ���� ��������� �������, <code>flase</code> � ��������� ������
	 */
	public boolean setCrd(char crd, double val) {
		switch (crd) {
		case ('x'):
			this.coordinates[0] = val;
			return true;
		case ('y'):
			this.coordinates[1] = val;
			return true;
		case ('z'):
			this.coordinates[2] = val;
			return true;
		}
		return false;
	}
	
	/**
	 * ���������, �������� ��� ����� ����� � ��� �� ��������, ���� ����� ���������� ����������
	 * @param point - ����� ��� ���������
	 * @return <code>true</code>, ���� ��������, <code>false</code> � �������� ������
	 */
	public boolean equals(Point3D point) {
		boolean[] eqcrds = {false, false, false};
		for (int i = 0; i < 3; i++) {
			if (this.coordinates[i] == point.coordinates[i]) eqcrds[i] = true; 
		}
		if (eqcrds[0] & eqcrds[1] & eqcrds[2]) return true;
		return false;
	}
	
	/**
	 * ���������� ���������� ����� ����� ������� � ��������� 0.01
	 * @param point - �����, �� ������� ���������� ����������
	 * @return ���������� ����� �������
	 */
	public double distanceTo(Point3D point) {
		double dec = 100;
		double distance = Math.sqrt(Math.pow(this.coordinates[0] - point.coordinates[0], 2) + Math.pow(this.coordinates[1] - point.coordinates[1], 2) + Math.pow(this.coordinates[2] - point.coordinates[2], 2))*dec;
		double distancernd = Math.round(distance)/dec;
		return distancernd;
	}
}
