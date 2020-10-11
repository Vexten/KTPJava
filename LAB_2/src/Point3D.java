/**
 *  ласс, представл€ющий точку в 3D пространстве
 * @author niksh
 */

public class Point3D {
	/** ћассив координат, где [0] - x, [1] - y, [2] - z */
	private double[] coordinates = new double[3];
	
	/**
	 *  онструктор - создание точки с определенными координатами
	 * @param crds - координаты точки в 3D пространстве (можно указать только x, или только x y)
	 */
	public Point3D(String[] crds) {
		int i;
		for (i = 0; i < crds.length; i++) { 		// «аполнить массив координат по введенным данным
			this.coordinates[i] = Double.parseDouble(crds[i]);
		}
		for (i += 0; i < 3; i++) {					// «аполнить пустые координаты 0.0
			this.coordinates[i] = 0.0;
		}
	}
	
	/**
	 * ѕолучить координату точки
	 * @param crd - указатель на координату, x, y или z
	 * @return «апрошенна€ координата
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
	 * »зменить указанную координату точки
	 * @param crd - указатель на координату, x, y или z
	 * @param val - значение координаты после изменени€
	 * @return <code>true</code>, если изменение успешно, <code>flase</code> в противном случае
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
	 * ѕроверить, €вл€ютс€ две точки одним и тем же объектом, либо имеют одинаковые координаты
	 * @param point - точка дл€ сравнени€
	 * @return <code>true</code>, если €вл€ютс€, <code>false</code> в проивном случае
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
	 * ¬ычисление рассто€ни€ между двум€ точками с точностью 0.01
	 * @param point - точка, до которой измер€етс€ рассто€ние
	 * @return –ассто€ние между точками
	 */
	public double distanceTo(Point3D point) {
		double dec = 100;
		double distance = Math.sqrt(Math.pow(this.coordinates[0] - point.coordinates[0], 2) + Math.pow(this.coordinates[1] - point.coordinates[1], 2) + Math.pow(this.coordinates[2] - point.coordinates[2], 2))*dec;
		double distancernd = Math.round(distance)/dec;
		return distancernd;
	}
}
