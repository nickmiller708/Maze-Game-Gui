package MazeGameGUI;

import javax.swing.ImageIcon;

public class Treasure {
	private String name, imagename;
	private int gold, healthrestore, width, height, xcoordinate, ycoordinate;
	private ImageIcon graphic;
	public Treasure(String name, String image, int gold, int health, int width, int height, int xvalue, int yvalue)
	{
		//Creates a treasure object based on parameters of the File
		this.name = name;
		imagename = image;
		this.gold = gold;
		healthrestore = health;
		this.width = width;
		this.height = height;
		xcoordinate = xvalue;
		ycoordinate = yvalue;
		graphic  = new ImageIcon("resources//resources//treasures//" + imagename);
	}
	public ImageIcon getGraphic()
	{
		return graphic;
	}
	public int getXCoordinate()
	{
		return xcoordinate;
	}
	public int getYCoordinate()
	{
		return ycoordinate;
	}
	public String getName()
	{
		return name;
	}
	public String getImageName()
	{
		return imagename;
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}
	public int getGold()
	{
		return gold;
	}
	public int getHR()
	{
		return healthrestore;
	}
}
