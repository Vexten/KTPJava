import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;

/**
 * Приложение для отображения и исследования фракталов
 * @author niksh
 * @version 1.0
 */
public class FractalExplorer extends JFrame {

	private static final long serialVersionUID = -8728543713519701421L;
	
	/** Размер экрана */
	private int windowSize;
	
	/** Элемент управления изображением */
	private JImageDisplay image;
	
	/** Основа генерации фракталов */
	private FractalGenerator fgen;
	
	/** Диапазон комплексной плоскости для отображения */
	private Rectangle2D.Double range;
	
	/**
	 * Конструктор - инициализирует компоненты GUI и отображения фрактала
	 * @param size - размер окна
	 */
	public FractalExplorer(int size) {
		this.windowSize = size;
		this.image = new JImageDisplay(this.windowSize, this.windowSize);
		this.fgen = new Mandelbrot();
		this.range = new Rectangle2D.Double();
		fgen.getInitialRange(range);
	}
	
	public FractalExplorer() {
		this(JImageDisplay.DEFAULT_SIZE);
	}
	
	public void createAndShowGUI() {
		this.add(this.image, BorderLayout.CENTER);
		this.image.addMouseListener(new mouse1_Click());
		JButton button = new JButton("Сброс");
		button.addActionListener(new button1_Click());
		this.add(button, BorderLayout.SOUTH);
		this.setTitle("Fractal Explorer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
	}
	
	private void drawFractal() {
		int iter;
		for (int y = 0; y < this.windowSize; y++) {
			for (int x = 0; x < this.windowSize; x++) {
				double real = FractalGenerator.getCoord(range.x, range.x + range.width, this.windowSize, x);
				double imag = FractalGenerator.getCoord(range.y, range.y + range.height, this.windowSize, y);
				iter = fgen.numIterations(real, imag);
				if (iter == -1) image.drawPixel(x, y, 0);
				else {
					float hue = 0.7f + (float)iter/200f;
					int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
					image.drawPixel(x, y, rgbColor);
				}
			}
		}
		image.repaint();
	}
	
	private class button1_Click implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fgen.getInitialRange(range);
			drawFractal();
		}
		
	}
	
	private class mouse1_Click extends MouseAdapter {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			Point loc = arg0.getPoint();
			double x = FractalGenerator.getCoord(range.x, range.x + range.width, windowSize, loc.x);
			double y = FractalGenerator.getCoord(range.y, range.y + range.height, windowSize, loc.y);
			fgen.recenterAndZoomRange(range, x, y, 0.5);
			drawFractal();
		}
		
	}
	
	public static void main(String[] args) {
		FractalExplorer app = new FractalExplorer(1000);
		app.createAndShowGUI();
		app.drawFractal();
	}

}
