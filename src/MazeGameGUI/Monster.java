package MazeGameGUI;

import java.util.Random;

public class Monster implements Runnable {
	private String name, image1, image2, currentImage;
	private int xcoordinate, ycoordinate, lifepoints, damageinflict, cooldown, probhits, probhitbyPlayer, row, col;
	private Game mediator;
 //both ImageIcons to print for a monster;
	public Monster(Game med, String name, String image1, String image2, int xvalue, int yvalue, int lifepoints, int damageinflict, int cooldown, int probhits,int probhit)
	{
		this.name = name;
		this.image1 = image1;
		this.image2 = image2;
		xcoordinate = xvalue;
		ycoordinate = yvalue;
		this.lifepoints = lifepoints;
		this.damageinflict=  damageinflict;
		this.cooldown = cooldown;
		this.probhits = probhits;
		this.probhitbyPlayer = probhit;
		currentImage =this.image1;
		row = 0;
		col = 0;
		mediator = med;
	}
	public void setRowandCol(int i, int j)
	{
		row = i;
		col = j;
	}
	public int getRow()
	{
		return row;
	}
	public int getCol()
	{
		return col;

	}
	public String getImage1Name()
	{
		return image1;
	}
	public String getImage2Name()
	{
		return image2;
	}
	public void updateCurrent()
	{
		if (currentImage.equals(image1))
		{
			currentImage = image2;
		}
		else if (currentImage.equals(image2))
		{
			currentImage = image1;
		}
	}
	public Game getMediator()
	{
		return mediator;
	}
	public String currentImage()
	{
		return currentImage;
	}
	public void setHealth(int damage)
	{
		lifepoints = lifepoints - damage;
	}
	public int getHealth()
	{
		return lifepoints;
	}
	public int getXCoordinate()
	{
		return xcoordinate;
	}
	public int getYCoordinate()
	{
		return ycoordinate;
	}
	public int getLP()
	{
		return lifepoints;
	}
	public int getDI()
	{
		return damageinflict;
	}
	public String getName()
	{
		return name;
	}
	public int getPHit()
	{
		return probhits;
	}
	public int getPhitbyP()
	{
		return probhitbyPlayer;
	}
	public int getCoolDown()
	{
		return cooldown;
	}
	public void moveMonster()
	{
		//This method is used in the run method for the monster and will decide where the monster will move
		//This method will also move the monster in the maze and then repaint any images on the GUI
		Random numgen = new Random();
		Tile up = null;
		Tile down = null;
		Tile left = null;
		Tile right = null;
		Tile monstertile = mediator.getTile(row, col); 
		boolean wallnorth = monstertile.haswallnorth();
		boolean wallsouth = monstertile.haswallsouth();
		boolean walleast = monstertile.haswalleast();
		boolean wallwest = monstertile.haswallwest();
		int rowTotal = mediator.getRowTotal();
		int colTotal = mediator.getColTotal();
		int playerrow = mediator.getPlayerRow();
		int playercol = mediator.getPlayerCol();
		if (row > 0)
		{
			up = mediator.getTile(row-1, col);
		}
		if (row < rowTotal-1)
		{
			down = mediator.getTile(row+1, col);
		}
		if (col > 0)
		{
			left = mediator.getTile(row, col-1);
		}
		if (col < colTotal-1)
		{
			right = mediator.getTile(row, col+1);
		}
		int enemychoose = numgen.nextInt(100);
		boolean initialchoice = false; //will be true if the first intelligent move is true
		int moverow = 0;
		int movecol = 0;
		if (enemychoose <= 50)
		{
			//Enemy will adjust based on the x coordinate
			if (playercol > col)
			{
				movecol = col +1; //moving 
				moverow = row;
				if ((movecol <= colTotal-1) && (walleast == false) && (right != null) && (right.hasMonster()== false) && (right.hasPlayer()==false))
				{
					initialchoice = true;
				}
			}
			else if (playercol < col)
			{
				movecol = col -1;
				moverow = row;
				if ((movecol >= 0) && (wallwest == false) && (left != null) && (left.hasMonster()==false) && (left.hasPlayer()==false))
				{
					initialchoice = true;
				}
			}
		}
		else
		{
			//If enemychoose > 50, then the enemy will adjust their y coordinate to try to equalize with the player
			if (playerrow > row)
			{
				moverow = row + 1;
				movecol = col;
				if ((moverow <= rowTotal -1) && (wallsouth == false) && (down != null) && (down.hasMonster() == false)&&(down.hasPlayer() == false))
				{
					initialchoice = true;
				}
			}
			else if ((playerrow < row))
			{
				moverow = row - 1;
				movecol = col;
				if ((moverow >= 0) && (wallnorth == false)&&(up!= null) && (up.hasMonster() == false) && (up.hasPlayer()== false))
				{
					initialchoice = true;
				}
			}
		}
		if (initialchoice == true)
		{
			mediator.removeMonster(row, col);
			mediator.addMonster(moverow, movecol, this);
			row = moverow;
			col = movecol;
		}
		else
		{
			moverow = row;
			movecol = col;
			initialchoice = false;
			if (enemychoose <= 50)
			{
				//Monster initially couldn't move based on x coordinate, so now doing it based on y coordinate
				if (playerrow > row)
				{
					moverow = row + 1;
					movecol = col;
					if ((moverow <= rowTotal -1) && (wallsouth == false) && (down != null) && (down.hasMonster() == false)&&(down.hasPlayer() == false))
					{
						initialchoice = true;
					}
				}
				else if ((playerrow < row))
				{
					moverow = row - 1;
					movecol = col;
					if ((moverow >= 0) && (wallnorth == false)&&(up!= null) && (up.hasMonster() == false) && (up.hasPlayer()== false))
					{
						initialchoice = true;
					}
				}
			}
			else
			{
				//If enemychoose > 50, then the enemy will adjust their x coordinate to try to equalize based on x coordinate
				
				if (playercol > col)
				{
					movecol = col +1; //moving 
					moverow = row;
					if ((movecol <= colTotal-1) && (walleast == false) && (right != null) && (right.hasMonster()== false) && (right.hasPlayer()==false))
					{
						initialchoice = true;
					}
				}
				else if (playercol < col)
				{
					movecol = col -1;
					moverow = row;
					if ((movecol >= 0) && (wallwest == false) && (left != null) && (left.hasMonster()==false) && (left.hasPlayer()==false))
					{
						initialchoice = true;
					}
				}
			}
			if (initialchoice == true)
			{
				mediator.removeMonster(row, col);
				mediator.addMonster(moverow, movecol, this);
				row = moverow;
				col = movecol;
			}
		}
	}
	private boolean checkforPlayer()
	{
		//This function will return a boolean and see if the player is up,down, left, or right of the player
		//This function is used in the run method to determine if the player is nearby. If they are, the monster can attack
		boolean returner = false;
		Tile up = null;
		Tile down = null;
		Tile left = null;
		Tile right = null;
		Tile monstertile = mediator.getTile(row, col);
		boolean wallnorth = monstertile.haswallnorth();
		boolean wallsouth = monstertile.haswallsouth();
		boolean walleast = monstertile.haswalleast();
		boolean wallwest = monstertile.haswallwest();
		int rowTotal = mediator.getRowTotal();
		int colTotal = mediator.getColTotal();
		if (row > 0)
		{
			up = mediator.getTile(row-1, col);
		}
		if (row < rowTotal-1)
		{
			down = mediator.getTile(row+1, col);
		}
		if (col > 0)
		{
			left = mediator.getTile(row, col-1);
		}
		if (col < colTotal-1)
		{
			right = mediator.getTile(row, col+1);
		}
		if ((right !=null) && (right.hasPlayer()) && (walleast == false))
		{
			returner = true;
		}
		else if ((left !=null) && (left.hasPlayer()) && (wallwest == false))
		{
			returner = true;
		}
		else if ((up!= null) && (up.hasPlayer())&&(wallnorth == false))
		{
			returner = true;
		}
		else if ((down != null) && (down.hasPlayer())&&(wallsouth == false))
		{
			returner = true;
		}
		return returner;
	}
	public void run() 
	{
		Random numgen= new Random();
		while ((mediator.getPlayerHealth() > 0) && (lifepoints > 0))
		{
			if (checkforPlayer() == true)
			{
				System.out.println(name + " tries to attack!");
				int prob = numgen.nextInt(100);
				if (prob < probhits)
				{
					mediator.playMonsterAttackSound();
					System.out.println(name + " attacks successfully!");
					mediator.decreasePlayerHealth(damageinflict);
					try {
						Thread.sleep(cooldown*500);
						mediator.repaintWalls();
						Thread.sleep(cooldown*500);
						mediator.repaintWalls();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else
				{
					System.out.println(name + " misses!");
					try {
						Thread.sleep(cooldown*500);
						mediator.repaintWalls();
						Thread.sleep(cooldown*500);
						mediator.repaintWalls();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{
				moveMonster();
				try
				{
					Thread.sleep(cooldown*1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
