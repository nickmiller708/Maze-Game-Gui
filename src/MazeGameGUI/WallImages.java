package MazeGameGUI;
//prints the walls and the treasures in the top part of the screen

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class WallImages extends JPanel{
	private Player p;
	private Maze m;
	public WallImages(Maze m, Player p)
	{
		//Hash table for walls will have keys with the texture of the item and the values will be an all of the images.
		//ImageIcon[0] = North ImageIcon[1] = East ImageIcon[2] = South ImageIcon [3] = West 
		this.p = p;
		this.m = m;
		this.setSize(new Dimension(800,600));
		this.setPreferredSize(new Dimension(800,600));
	}
	public String setUpFront()
	{
		//Will calculate the name of the front wall to draw and return filename
		String[] names = new String[3];
		names[0] = "_center.png";
		names[1] = "_center_no_wall_back.png";
		names[2] = "_center_wall_in_face.png";
		int returner = 0;
		int row = p.getRow();
		int col = p.getCol();
		int rowtotal = m.getRow();
		int coltotal = m.getCol();
		String direction = p.getDirection();
		Tile playerin = m.getTile(row,col);
		boolean north = playerin.haswallnorth();
		boolean south = playerin.haswallsouth();
		boolean west = playerin.haswallwest();
		boolean east = playerin.haswalleast();
		boolean northa = true;
		Tile ahead = getAheadTile();
		if ((direction.equals("N")) && (ahead != null))
		{
			northa = ahead.haswallnorth();
			if ((north))
			{
				returner = 2;
			}
			else if ((northa) && (!north))
			{
				returner = 0;
			}
			else if ((!northa)&&(!north))
			{
				returner = 1;
			}
		}
		else if ((direction.equals("S")) && ( ahead!= null))
		{
			northa = ahead.haswallsouth();
			if ((south))
			{
				returner = 2;
			}
			else if ((northa) && (!south))
			{
				returner = 0;
			}
			else if ((!northa)&&(!south))
			{
				returner = 1;
			}
		}
		else if ((direction.equals("E")) && (ahead != null))
		{
			northa = ahead.haswalleast();
			if ((east))
			{
				returner = 2;
			}
			else if ((northa) && (!east))
			{
				returner = 0;
			}
			else if ((!northa)&&(!east))
			{
				returner = 1;
			}
		}
		else if ((direction.equals("W")) && (ahead != null))
		{
			northa = ahead.haswallwest();
			if ((west))
			{
				returner = 2;
			}
			else if ((northa) && (!west))
			{
				returner = 0;
			}
			else if ((!northa)&&(!west))
			{
				returner = 1;
			}
		}
		else if (ahead == null)
		{
			returner = 2;
		}
		return names[returner];
	}
	public String setUpRight()
	{
		//Will determine what right wall to draw and return string name of the file
		String[] names = new String[4];
		names[0] = "_right.png";
		names[1] = "_right_no_wall_back.png";
		names[2] = "_right_no_wall_both.png";
		names[3] = "_right_no_wall_front.png";
		int returner = 0;
		int row = p.getRow();
		int col = p.getCol();
		String direction = p.getDirection();
		Tile playerin = m.getTile(row,col);
		boolean north = playerin.haswallnorth();
		boolean south = playerin.haswallsouth();
		boolean west = playerin.haswallwest();
		boolean east = playerin.haswalleast();
		Tile ahead = getAheadTile();
		boolean easta = true;
		if ((direction.equals("N")) && (ahead != null))
		{
			easta = ahead.haswalleast();
			if ((easta) && (east))
			{
				returner = 0;
			}
			else if ((easta) && (!east))
			{
				returner = 1;
			}
			else if ((!easta)&&(!east))
			{
				returner = 2;
			}
			else if ((!easta) && (east))
			{
				returner = 3;
			}
		}
		else if ((direction.equals("S")) && ( ahead!= null))
		{
			easta = ahead.haswallwest();
			if ((easta) && (west))
			{
				returner = 0;
			}
			else if ((easta) && (!west))
			{
				returner = 1;
			}
			else if ((!easta)&&(!west))
			{
				returner = 2;
			}
			else if ((!easta) && (west))
			{
				returner = 3;
			}
		}
		else if ((direction.equals("E")) && (ahead != null))
		{
			easta = ahead.haswallsouth();
			if ((easta) && (south))
			{
				returner = 0;
			}
			else if ((easta) && (!south))
			{
				returner = 1;
			}
			else if ((!easta)&&(!south))
			{
				returner = 2;
			}
			else if ((!easta) && (south))
			{
				returner = 3;
			}
		}
		else if ((direction.equals("W")) && (ahead != null))
		{
			easta = ahead.haswallnorth();
			if ((easta) && (north))
			{
				returner = 0;
			}
			else if ((easta) && (!north))
			{
				returner = 1;
			}
			else if ((!easta)&&(!north))
			{
				returner = 2;
			}
			else if ((!easta) && (north))
			{
				returner = 3;
			}
		}
		else if (ahead == null)
		{
			if (direction.equals("N"))
			{
				if (east)
				{
					returner = 3;
				}
				else if (!east)
				{
					returner =2;
				}
			}
			else if (direction.equals("E"))
			{
				if (south)
				{
					returner =3;
				}
				else if (!south)
				{
					returner =2;
				}
			}
			else if (direction.equals("W"))
			{
				if (north)
				{
					returner =3;
				}
				else if (!north)
				{
					returner =2;
				}
			}
			else if (direction.equals("S"))
			{
				if (west)
				{
					returner =3;
				}
				else if (!west)
				{
					returner =2;
				}
			}
		}
		return names[returner];
	}
	public String setUpLeft()
	{
		//Will determine what what left wall to draw
		String[] names = new String[4];
		names[0] = "_left.png";
		names[1] = "_left_no_wall_back.png";
		names[2] = "_left_no_wall_both.png";
		names[3] = "_left_no_wall_front.png";
		int returner = 0;
		int row = p.getRow();
		int col = p.getCol();
		String direction = p.getDirection();
		Tile playerin = m.getTile(row,col);
		boolean north = playerin.haswallnorth();
		boolean south = playerin.haswallsouth();
		boolean west = playerin.haswallwest();
		boolean east = playerin.haswalleast();
		Tile ahead = getAheadTile();
		boolean westa = true;
		if ((direction.equals("N")) && (ahead != null))
		{
			westa = ahead.haswallwest();
			if ((westa) && (west))
			{
				returner = 0;
			}
			else if ((westa) && (!west))
			{
				returner = 1;
			}
			else if ((!westa)&&(!west))
			{
				returner = 2;
			}
			else if ((!westa) && (west))
			{
				returner = 3;
			}
		}
		else if ((direction.equals("S")) && ( ahead!= null))
		{
			westa = ahead.haswalleast();
			if ((westa) && (east))
			{
				returner = 0;
			}
			else if ((westa) && (!east))
			{
				returner = 1;
			}
			else if ((!westa)&&(!east))
			{
				returner = 2;
			}
			else if ((!westa) && (east))
			{
				returner = 3;
			}
		}
		else if ((direction.equals("E")) && (ahead != null))
		{
			westa = ahead.haswallnorth();
			if ((westa) && (north))
			{
				returner = 0;
			}
			else if ((westa) && (!north))
			{
				returner = 1;
			}
			else if ((!westa)&&(!north))
			{
				returner = 2;
			}
			else if ((!westa) && (north))
			{
				returner = 3;
			}
		}
		else if ((direction.equals("W")) && (ahead != null))
		{
			westa = ahead.haswallsouth();
			if ((westa) && (south))
			{
				returner = 0;
			}
			else if ((westa) && (!south))
			{
				returner = 1;
			}
			else if ((!westa)&&(!south))
			{
				returner = 2;
			}
			else if ((!westa) && (south))
			{
				returner = 3;
			}
		}
		else if (ahead == null)
		{
			if (direction.equals("N"))
			{
				if (west)
				{
					returner =3;
				}
				else if (!west)
				{
					returner =2;
				}
			}
			else if (direction.equals("E"))
			{
				if (north)
				{
					returner =3;
				}
				else if (!north)
				{
					returner = 2;
				}
			}
			else if (direction.equals("W"))
			{
				if (south)
				{
					returner =3;
				}
				else if (!south)
				{
					returner =2;
				}
			}
			else if (direction.equals("S"))
			{
				if (east)
				{
					returner =3;
				}
				else if (!east)
				{
					returner = 2;
				}
			}
		}
		return names[returner];
	}
	public Tile getAheadTile()
	{
		//Will return the Tile ahead of the player. If nothing, returns null
		Tile returner;
		String direction = p.getDirection();
		int row = p.getRow();
		int col = p.getCol();
		int rowTotal = m.getRow();
		int colTotal = m.getCol();
		Tile current = m.getTile(row, col);
		boolean north = current.haswallnorth();
		boolean south = current.haswallsouth();
		boolean east = current.haswalleast();
		boolean west = current.haswallwest();
		if ((direction.equals("N"))&&(row > 0) && (north != true))
		{
			returner = m.getTile(row-1, col);
			
		}
		else if ((direction.equals("S")) && ( row < rowTotal-1) && (south != true))
		{
			returner = m.getTile(row+1, col);
		}
		else if ((direction.equals("E")) && (col < colTotal-1)&& (east != true))
		{
			returner = m.getTile(row, col+1);
		}
		else if ((direction.equals("W"))&& (col > 0) && (west != true))
		{
			returner = m.getTile(row,  col-1);
		}
		else
		{
			returner = null;
		}
		return returner;
	}
	public Treasure treasureImage()
	{
		//Will the treasure to drawelse it won't print anything at all
		Treasure returner = null;
		Tile ahead = getAheadTile();
		if (ahead != null)
		{
			if (ahead.hasTreasure())
			{
			returner = ahead.getTreasure();
			}
		}
		else
		{
			returner = null;
		}
		return returner;
	}
	public Monster monsterImage()
	{
		Monster returner = null;
		Tile ahead = getAheadTile();
		if (ahead != null)
		{
			if (ahead.hasMonster()){
				returner = ahead.getMonster();
			}
		}
		else
		{
			returner = null;
		}
		return returner;
	}
	public void paint(Graphics g)
	{
		//will paint based on where the player is located
		//will also need to consider textures to draw the walls
		//also need to draw all of the treasures
		super.paint(g);
		String up = setUpFront();
		String right = setUpRight();
		String left = setUpLeft();
		Tile current = m.getTile(p.getRow(), p.getCol());
		String texture = current.getTexture();
		String monsterlocation = "resources//resources///monsters//";
		String treasurelocation = "resources//resources///treasures//";
		String upfilename = "resources//resources//textures//" + texture + up;
		String rightfilename = "resources//resources//textures//" + texture + right;
		String leftfilename = "resources//resources//textures//" + texture + left;
		Monster mprint = monsterImage();
		Treasure tprint = treasureImage();
		ImageIcon upprint = new ImageIcon(upfilename);
		ImageIcon rightprint = new ImageIcon(rightfilename);
		ImageIcon leftprint =  new ImageIcon(leftfilename);
		g.drawImage(upprint.getImage(), 228, 0, null);
		g.drawImage(leftprint.getImage(), 0, 0, null);
		g.drawImage(rightprint.getImage(), 571, 0, null);
		if (mprint != null)
		{
			ImageIcon m = new ImageIcon("" + monsterlocation + mprint.currentImage());
			g.drawImage(m.getImage(), mprint.getXCoordinate(), mprint.getYCoordinate(),null);
			mprint.updateCurrent();
		}
		if (tprint !=null)
		{
			ImageIcon t = new ImageIcon("" + treasurelocation +tprint.getImageName());
			g.drawImage(t.getImage(), tprint.getXCoordinate(), tprint.getYCoordinate(), tprint.getWidth(), tprint.getHeight(), null);
		}
	}
	public void updateMapandPlayer(Maze m, Player p)
	{
		this.m = m;
		this.p = p;
	}
}
