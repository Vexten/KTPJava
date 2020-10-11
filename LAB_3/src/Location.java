/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
    
    /** ��������� ������� � ������ ��������
     * @param obj - ������ ��� ���������
     * @return <code>true</code>, ���� ��������� ���������� � ����� ��� ���������� ������� ���������,
     * <code>false</code> � ��������� ������ */
    @Override
    public boolean equals(Object obj) {
    	boolean result = false;
    	if (this == obj) result = true;
    	if (getClass() == obj.getClass()) {
    		Location difloc = (Location) obj;
    		if (this.xCoord == difloc.xCoord & this.yCoord == difloc.yCoord) result = true;
    	}
    	return result;
    }
    
    /** �������� ��� �������
     * @return ���-��� ������� */
    @Override
    public int hashCode() {
    	int result = 17, prime = 31;
    	result = result * prime + this.xCoord;
    	result = result * prime + this.yCoord;
    	return result;
    }
}
