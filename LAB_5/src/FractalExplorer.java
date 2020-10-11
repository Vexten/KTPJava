import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;

/**
 * Приложение для отображения и исследования фракталов
 * @author niksh
 * @version 1.1
 */
public class FractalExplorer extends JFrame {

	private static final long serialVersionUID = -8728543713519701422L;
	
	/** Размер экрана */
	private int windowSize;
	
	/** Элемент управления изображением */
	private JImageDisplay image;
	
	/** Основа генерации фракталов */
	private FractalGenerator fgen;
	
	/** Диапазон комплексной плоскости для отображения */
	private Rectangle2D.Double range;
	
	/**
	 * Конструктор - инициализирует компоненты отображения фрактала
	 * @param size - размер изображения
	 */
	public FractalExplorer(int size) {
		this.windowSize = size;
		this.image = new JImageDisplay(this.windowSize, this.windowSize);
		this.fgen = new Mandelbrot();
		this.range = new Rectangle2D.Double();
		fgen.getInitialRange(range);
	}
	
	/**
	 * Конструктор - инициализирует компоненты отображения фрактала со стандартным размером 500px
	 */
	public FractalExplorer() {
		this(JImageDisplay.DEFAULT_SIZE);
	}
	
	/**
	 * Метод-инициализатор для GUI
	 */
	public void createAndShowGUI() {
		this.image.addMouseListener(new mouse1_Click());
		this.add(this.image, BorderLayout.CENTER);
		ActionListener actions = new button_Click();
		// Кнопка сброса
		JButton reset = new JButton("Сброс");
		reset.setActionCommand("reset");
		reset.addActionListener(actions);
		// Кнопка сохранения
		JButton save = new JButton("Сохранить");
		save.setActionCommand("save");
		save.addActionListener(actions);
		// Контейнер кнопок
		JPanel bottom = new JPanel();
		bottom.add(save);
		bottom.add(reset);
		this.add(bottom, BorderLayout.SOUTH);
		// Выпадающий лист фракталов
		JComboBox<FractalGenerator> list = new JComboBox<FractalGenerator>();
		list.addItem(new Mandelbrot());
		list.addItem(new Tricorn());
		list.addItem(new BurningShip());
		list.setActionCommand("change");
		list.addActionListener(actions);
		JLabel boxLabel = new JLabel("Выбор фрактала");
		// Контейнер листа и имени
		JPanel top = new JPanel();
		top.add(boxLabel);
		top.add(list);
		this.add(top, BorderLayout.NORTH);
		// Настройка окна
		this.setTitle("Fractal Explorer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
	}
	
	/**
	 * Метод, отрисовывающий фрактал на экран
	 */
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
	
	/**
	 * Класс событий для элементов GUI
	 */
	private class button_Click implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			switch (command) {
			// Кнопка сохранения
			case ("save"):
				JFileChooser dialog = new JFileChooser();
				dialog.setLocale(getLocale());
				FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG files","png");
				dialog.setFileFilter(pngFilter);
				dialog.setAcceptAllFileFilterUsed(false);
				int result = dialog.showSaveDialog(image);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						ImageIO.write(image.bimage,"png",new File(dialog.getSelectedFile()+".png"));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(dialog, e.getLocalizedMessage(), "Save error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				break;
			//Кнопка сброса
			case ("reset"):
				fgen.getInitialRange(range);
				drawFractal();
				break;
			// Лист фракталов	
			case ("change"):
				@SuppressWarnings("unchecked") JComboBox<FractalGenerator> list = (JComboBox<FractalGenerator>) arg0.getSource();
				fgen = (FractalGenerator) list.getSelectedItem();
				fgen.getInitialRange(range);
				drawFractal();
				break;
			}
		}
		
	}
	
	/**
	 * Класс событий для мыши (принадлежит {@link FractalExplorer#image})
	 */
	private class mouse1_Click extends MouseAdapter {
		// Приблизить фрактал в точку, в которой находился курсор на момент отпускания ЛКМ
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
		FractalExplorer app = new FractalExplorer(800);
		app.createAndShowGUI();
		app.drawFractal();
	}

}
