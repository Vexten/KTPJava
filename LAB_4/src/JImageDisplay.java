import javax.swing.JComponent;
import java.awt.image.*;
import java.awt.*;

/**
 * Класс элемента, управляющий отображением изображения
 * @author niksh
 */

public class JImageDisplay extends JComponent {
	
	private static final long serialVersionUID = -611291715804825948L;

	/** Ширина элемента в пикселях */
	private int width;
	
	/** Высота элемента в пикселях */
	private int height;
	
	/** Объект изображения */
	private BufferedImage image;
	
	/** Стандартный размер окна */
	public static final int DEFAULT_SIZE = 500;
	
	/**
	 * Конструктор - создает элемент интерфейса, содержащий изображение 
	 * @param width - ширина элемента
	 * @param height - высота элемента
	 */
	public JImageDisplay(int width, int height) {
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);	// Создать изображение width*height с цветовым пространством RGB
		super.setPreferredSize(new Dimension(width,height));	// Задать размер элемента равным размеру изображения
	}
	
	/**
	 * Конструктор - создает элемент интерфейса, размером 500 на 500, содержащий изображение
	 */
	public JImageDisplay() {
		this(DEFAULT_SIZE,DEFAULT_SIZE);
	}
	
	/**
	 * Переопределение метода paintComponent, который отображает наш элемент
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage (image, 0, 0, this.width, this.height, null);
	}
	
	/**
	 * Метод, устанавливающий все пиксели изображения в черный цвет
	 */
	public void clearImage() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				this.image.setRGB(x, y, 0);
			}
		}
	}
	
	/**
	 * Метод, устанавливающий выбранный пиксель изображения в указанный цвет
	 * @param x - x координата пикселя (ширина)
	 * @param y - y координата пикселя (высота)
	 * @param rgbColor - код цвета
	 * @see Color#getRGB()
	 */
	public void drawPixel(int x, int y, int rgbColor) {
		this.image.setRGB(x, y, rgbColor);
	}
}
