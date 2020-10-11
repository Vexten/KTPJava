import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;

/**
 * ���������� ��� ����������� � ������������ ���������
 * @author niksh
 * @version 1.2
 */
public class FractalExplorer extends JFrame {

	private static final long serialVersionUID = -8728543713519701422L;
	
	/** ������ ������ */
	private int windowSize;
	
	/** ������� ���������� ������������ */
	private JImageDisplay image;
	
	/** ������ ��������� ��������� */
	private FractalGenerator fgen;
	
	/** �������� ����������� ��������� ��� ����������� */
	private Rectangle2D.Double range;
	
	/** ���-������� ����������� ���� */
	private HashMap<Integer,Component> componentMap;
	
	/** ���-�� ���������� ��� ��������� ����� ����������� */
	private int rowsRemainig;
	
	/**
	 * ����������� - �������������� ���������� ����������� ��������
	 * @param size - ������ �����������
	 */
	public FractalExplorer(int size) {
		this.windowSize = size;
		this.image = new JImageDisplay(this.windowSize, this.windowSize);
		this.fgen = new Mandelbrot();
		this.range = new Rectangle2D.Double();
		fgen.getInitialRange(range);
	}
	
	/**
	 * ����������� - �������������� ���������� ����������� �������� �� ����������� �������� 500px
	 */
	public FractalExplorer() {
		this(JImageDisplay.DEFAULT_SIZE);
	}
	
	/**
	 * �����-������������� ��� GUI
	 */
	public void createAndShowGUI() {
		// ����������� ��������
		this.image.addMouseListener(new mouse1_Click());
		this.add(this.image, BorderLayout.CENTER);
		ActionListener actions = new button_Click();
		// ������ ������
		JButton reset = new JButton("�����");
		reset.setActionCommand("reset");
		reset.addActionListener(actions);
		// ������ ����������
		JButton save = new JButton("���������");
		save.setActionCommand("save");
		save.addActionListener(actions);
		// ��������� ������
		JPanel bottom = new JPanel();
		bottom.add(save);
		bottom.add(reset);
		this.add(bottom, BorderLayout.SOUTH);
		// ���������� ���� ���������
		JComboBox<FractalGenerator> list = new JComboBox<FractalGenerator>();
		list.addItem(new Mandelbrot());
		list.addItem(new Tricorn());
		list.addItem(new BurningShip());
		list.setActionCommand("change");
		list.addActionListener(actions);
		JLabel boxLabel = new JLabel("����� ��������");
		// ��������� ����� � �����
		JPanel top = new JPanel();
		top.add(boxLabel);
		top.add(list);
		this.add(top, BorderLayout.NORTH);
		// ��������� ����
		this.setTitle("Fractal Explorer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
		// ���������� ����������� ����������� � HashMap
		this.componentMap = new HashMap<Integer,Component>();
		this.componentMap.put(0, save);
		this.componentMap.put(1, reset);
		this.componentMap.put(2, list);
		this.componentMap.put(3, image);
	}

	
	/**
	 * �����, �������������� ������� �� �����
	 */
	private void drawFractal() {
		this.rowsRemainig = this.windowSize;
		this.enableUI(false);
		for (int y = 0; y < this.windowSize; y++) {
			FractalWorker row = new FractalWorker(y);
			row.execute();
		}
	}
	
	/**
	 * ����� - �������� ��� ��������� �������� � {@link FractalExplorer#componentMap} �������� GUI
	 * @param toggle - <code>true</code> ��������, <code>false</code> ���������
	 */
	private void enableUI(boolean toggle) {
		for (int i = 0; i < componentMap.size(); i++) {
			componentMap.get(i).setEnabled(toggle);
		}
	}
	
	/**
	 * �����, ����������� ������������� ��������� �������� ����� Swing
	 */
	private class FractalWorker extends SwingWorker<Object,Object> {
		/** <code>y</code> ���������� �������� */
		private int yCoord;
		
		/** ������ ������ ��������� ������ �������� */
		public int[] xRGB;
		
		/**
		 * ����������� - �������� ������� ��� ���������� ������
		 * @param y - ������ ��� ����������
		 */
		public FractalWorker(int y) {
			this.yCoord = y;
		}
		
		/**
		 * ������� ���������� ������ ���� ����������� ��������
		 */
		@Override
		protected Object doInBackground() throws Exception {
			this.xRGB = new int[windowSize];
			double imag = FractalGenerator.getCoord(range.y, range.y + range.height, windowSize, yCoord);
			int iter;
			for (int x = 0; x < windowSize; x++) {
				double real = FractalGenerator.getCoord(range.x, range.x + range.width, windowSize, x);
				iter = fgen.numIterations(real, imag);
				if (iter == -1) xRGB[x] = 0;
				else {
					float hue = 0.7f + (float)iter/200f;
					int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
					xRGB[x] = rgbColor;
				}
			}
			return null;
		}
		
		/**
		 * ��������� ���������� ������ �� ���������� ����������
		 */
		@Override
		protected void done() {
			for (int x = 0; x < windowSize; x++) {
				image.drawPixel(x, yCoord, xRGB[x]);
			}
			image.repaint(0, yCoord, windowSize, 1);
			rowsRemainig -= 1;
			if (rowsRemainig == 0) enableUI(true);
			super.done();
		}
		
		
	}
	
	/**
	 * ����� ������� ��� ��������� GUI
	 */
	private class button_Click implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			switch (command) {
			// ������ ����������
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
			//������ ������
			case ("reset"):
				fgen.getInitialRange(range);
				drawFractal();
				break;
			// ���� ���������	
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
	 * ����� ������� ��� ���� (����������� {@link FractalExplorer#image})
	 */
	private class mouse1_Click extends MouseAdapter {
		// ���������� ������� � �����, � ������� ��������� ������ �� ������ ���������� ���
		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (image.isEnabled()) {
				Point loc = arg0.getPoint();
				double x = FractalGenerator.getCoord(range.x, range.x + range.width, windowSize, loc.x);
				double y = FractalGenerator.getCoord(range.y, range.y + range.height, windowSize, loc.y);
				fgen.recenterAndZoomRange(range, x, y, 0.5);
				drawFractal();
			}
		}
		
	}
	
	public static void main(String[] args) {
		FractalExplorer app = new FractalExplorer(800);
		app.createAndShowGUI();
		app.drawFractal();
	}

}
