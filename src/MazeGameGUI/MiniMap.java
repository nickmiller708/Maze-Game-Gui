package MazeGameGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MiniMap extends JPanel
{
	private Maze m;
	public MiniMap(Maze m)
	{
		this.m = m;
		this.setSize(new Dimension(350,200));
		this.setPreferredSize(new Dimension(350, 200));
	}
	public void paint (Graphics g)
	{
		//Will paint the minimap based on the maze of the game
		super.paint(g);
		int col = m.getCol();
		int row = m.getRow();
		int RoomSize =  43;
		int startx = 10;
		int starty = 0;
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				Tile drawer = m.getTile(i, j);
				//get the walls above, below, left, and right
				String texture = drawer.getTexture();
				if (texture.equals("texture1"))
				{
					g.setColor(Color.black);
				}
				else if (texture.equals("texture2"))
				{
					g.setColor(Color.green);
				}
				else if (texture.equals("texture3"))
				{
					g.setColor(Color.magenta);
				}
				else if (texture.equals("texture4"))
				{
					g.setColor(Color.blue);
				}
				if (drawer.haswallnorth() == true)
				{
					g.drawLine(startx, starty, startx + RoomSize, starty);
				}
				if (drawer.haswallwest() == true)
				{
					g.drawLine(startx, starty, startx, starty+RoomSize);
				}
				if (drawer.haswalleast() == true)
				{
					g.drawLine(startx + RoomSize, starty, startx+RoomSize, starty + RoomSize);
				}
				if (drawer.haswallsouth())
				{
					g.drawLine(startx, starty+RoomSize, startx+RoomSize, starty + RoomSize);
				}
				if (drawer.hasMonster())
				{
					g.setColor(Color.black);
					Character adder = new Character(drawer.getMonsterName().charAt(0));
					String add = adder.toString();
					g.drawString(add, startx+10, starty + RoomSize-10);
				}
				if (drawer.hasTreasure())
				{
					if (drawer.getTreasureName().equals("Health Potion"))
					{
						g.setColor(Color.blue);
						g.drawOval(startx+(3/4)*RoomSize+10, starty + (1/4)*(RoomSize), 12, 12);
						g.fillOval(startx+(3/4)*RoomSize+10, starty + (1/4)*(RoomSize), 12, 12);
					}
					else if (drawer.getTreasureName().equals("Chalice"))
					{
						g.setColor(Color.yellow);
						g.drawOval(startx+(3/4)*RoomSize+10, starty + (1/4)*(RoomSize), 12, 12);
						g.fillOval(startx+(3/4)*RoomSize+10, starty + (1/4)*(RoomSize), 12, 12);
					}
					else if (drawer.getTreasureName().equals("Gem"))
					{
						g.setColor(Color.RED);
						g.drawOval(startx+(3/4)*RoomSize+ 10, starty + (1/4)*(RoomSize), 12, 12);
						g.fillOval(startx+(3/4)*RoomSize+10, starty + (1/4)*(RoomSize), 12, 12);
					}
					else if (drawer.getTreasureName().equals("Gold Bag"))
					{
						g.setColor(Color.black);
						g.drawOval(startx+(3/4)*RoomSize+10, starty + (1/4)*(RoomSize), 12, 12);
						g.fillOval(startx+(3/4)*RoomSize+10, starty + (1/4)*(RoomSize), 12, 12);
					}
				}
				if (drawer.hasPlayer() == true)
				{
					//Draw a String there to show the player is there
					g.setColor(Color.black);
					g.drawString("U", startx + (RoomSize)-20, starty+RoomSize-10);
				}
				startx = startx+ RoomSize+ 5;
				g.setColor(Color.black);
			}
			startx = 10;
			starty= starty+ (RoomSize)+ 5;
		}
	}
	public void updateMaze(Maze maze)
	{
		this.m = maze;
	}
}
