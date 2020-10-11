import javax.swing.JComponent;
import java.awt.image.*;
import java.awt.*;

/**
 * ����� ��������, ����������� ������������ �����������
 * @author niksh
 */

public class JImageDisplay extends JComponent {
	
	private static final long serialVersionUID = -611291715804825948L;

	/** ������ �������� � �������� */
	private int width;
	
	/** ������ �������� � �������� */
	private int height;
	
	/** ������ ����������� */
	private BufferedImage image;
	
	/** ����������� ������ ���� */
	public static final int DEFAULT_SIZE = 500;
	
	/**
	 * ����������� - ������� ������� ����������, ���������� ����������� 
	 * @param width - ������ ��������
	 * @param height - ������ ��������
	 */
	public JImageDisplay(int width, int height) {
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);	// ������� ����������� width*height � �������� ������������� RGB
		super.setPreferredSize(new Dimension(width,height));	// ������ ������ �������� ������ ������� �����������
	}
	
	/**
	 * ����������� - ������� ������� ����������, �������� 500 �� 500, ���������� �����������
	 */
	public JImageDisplay() {
		this(DEFAULT_SIZE,DEFAULT_SIZE);
	}
	
	/**
	 * ��������������� ������ paintComponent, ������� ���������� ��� �������
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage (image, 0, 0, this.width, this.height, null);
	}
	
	/**
	 * �����, ��������������� ��� ������� ����������� � ������ ����
	 */
	public void clearImage() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				this.image.setRGB(x, y, 0);
			}
		}
	}
	
	/**
	 * �����, ��������������� ��������� ������� ����������� � ��������� ����
	 * @param x - x ���������� ������� (������)
	 * @param y - y ���������� ������� (������)
	 * @param rgbColor - ��� �����
	 * @see Color#getRGB()
	 */
	public void drawPixel(int x, int y, int rgbColor) {
		this.image.setRGB(x, y, rgbColor);
	}
}
