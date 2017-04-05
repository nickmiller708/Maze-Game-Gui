package MazeGameGUI;

import java.util.Hashtable;

public class Tile {
	private boolean monster, treasure, player, wallnorth, wallsouth, walleast, wallwest;
	String monstername, treasurename, Texture;
	private Monster m;
	private Treasure t;
	private Player p;
	private int row, col;
	public Tile(int i, int j, String monstername, String treasurename, boolean wallnorth, boolean wallsouth, boolean walleast, boolean wallwest, String texture, Hashtable<String,Monster>monsters, Hashtable<String,Treasure> treasures)
	{
		this.monstername = monstername;
		this.treasurename = treasurename;
		this.wallnorth = wallnorth;
		this.wallsouth = wallsouth;
		this.walleast = walleast;
		this.wallwest = wallwest;
		row = i;
		col = j;
		if (this.monstername.equals("none"))
		{
			monster = false;
		}
		else
		{
			//can add monster from arraylist made in Game Class
			monster = true;
			Monster adder = monsters.get(monstername);
			String name =  adder.getName();
			String image1 = adder.getImage1Name();
			String image2 = adder.getImage2Name();
			int xcoordinate = adder.getXCoordinate();
			int ycoordinate = adder.getYCoordinate();
			int lifepoints = adder.getLP();
			int damageinflict=  adder.getDI();
			int cooldown = adder.getCoolDown();
			int probhits = adder.getPHit();
			int probhitbyPlayer = adder.getPhitbyP();
			Game mediator = adder.getMediator();
			m = new Monster(mediator, name, image1, image2, xcoordinate, ycoordinate, lifepoints, damageinflict, cooldown, probhits, probhitbyPlayer);
			m.setRowandCol(i,j);
		}
		if (this.treasurename.equals("none"))
		{
			treasure = false;
		}
		else
		{
			treasure = true;
			Treasure adder = treasures.get(treasurename);
			String name = adder.getName();
			String imagename = adder.getImageName();
			int gold = adder.getGold();
			int healthrestore = adder.getHR();
			int width = adder.getWidth();
			int height = adder.getHeight();
			int xcoordinate = adder.getXCoordinate();
			int ycoordinate = adder.getYCoordinate();
			t = new Treasure(name, imagename, gold, healthrestore, width, height, xcoordinate, ycoordinate);
		}
		player = false; //will create every tile and then add the player to its initial tile at the beginning
		p = null;
		Texture = texture;
	}
	public int getRow()
	{
		return row;
	}
	public int getCol()
	{
		return col;
	}
	public boolean haswallnorth()
	{
		return wallnorth;
	}
	public boolean haswallsouth()
	{
		return wallsouth;
	}
	public boolean haswallwest()
	{
		return wallwest;
	}
	public boolean haswalleast()
	{
		return walleast;
	}
	public String getTexture()
	{
		return Texture;
	}
	public boolean hasMonster()
	{
		//returns name of the monster if this is a monster, else return null
		return monster;
	}
	public Monster getMonster()
	{
		return m;
	}
	public void removeMonster()
	{
		monster = false;
		m = null;
	}
	public void addMonster(Monster m)
	{
		monster = true;
		this.m = m;
	}
	public Treasure getTreasure()
	{
		return t;
	}
	public boolean hasTreasure()
	{
		return treasure;
	}
	public String getMonsterName()
	{
		return m.getName();
	}
	public String getTreasureName()
	{
		return t.getName();
	}
	public Player getPlayer()
	{
		return p;
	}
	public boolean hasPlayer()
	{
		return player;
	}
	public void removePlayer()
	{
		this.p = null;
		player = false;
	}
	public void removeTreasure()
	{
		t = null;
		treasure = false;
	}
	public void addPlayer(Player p)
	{
		this.p = p;
		player = true;
	}
}
