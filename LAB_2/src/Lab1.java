// Класс, возвращающий площадь треугольника в трехмерном пространстве, заданного по точкам

public class Lab1 {
	public static void main(String[] args) {
		Point3D[] points = new Point3D[3];					// Массив трех точек треугольника
		String[] crds = new String[3];						// Массив координат точки
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				crds[j] = args[3*i + j];					// Сформировать координаты одной точки из args
			}
			points[i] = new Point3D(crds);					// Создать по ним точку
		}
		if (!points[0].equals(points[1]) & !points[0].equals(points[2]) & !points[1].equals(points[2])) System.out.print(computeArea(points));
		else System.out.print("Две точки одинаковы, невозможно расчитать площадь треугольника.");
	}
	public static double computeArea(Point3D[] points) {	// Вычисление площади треугольника по формуле Герона
		double A, B, C, p;
		A = points[0].distanceTo(points[1]);
		B = points[1].distanceTo(points[2]);
		C = points[0].distanceTo(points[2]);
		p = (A + B + C)/2;
		return Math.sqrt(p*(p - A)*(p - B)*(p - C));
	}
}
