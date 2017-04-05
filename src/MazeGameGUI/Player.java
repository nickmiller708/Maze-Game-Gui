package MazeGameGUI;

public class Player 
{
	private int Health, CurrentHealth, Attack, row, col, treasure;
	private String direction;
	public Player()
	{
		Health = 100;
		CurrentHealth = 100;
		Attack = 5;
		row = 0;
		col = 0;
		direction = "S";
		treasure = 0;
	}
	public void setTile(int i, int j)
	{
		//resets row and column to represent where the player is in.
		row = i;
		col = j;
	}
	public int getTreasure()
	{
		return treasure;
	}
	public void RotateRight()
	{
		if (direction.equals("N"))
		{
			direction = "E";
		}
		else if (direction.equals("E"))
		{
			direction = "S";
		}
		else if (direction.equals("S"))
		{
			direction = "W";
		}
		else if (direction.equals("W"))
		{
			direction = "N";
		}
	}
	public void RotateLeft()
	{
		if (direction.equals("N"))
		{
			direction = "W";
		}
		else if (direction.equals("W"))
		{
			direction = "S";
		}
		else if (direction.equals("S"))
		{
			direction = "E";
		}
		else if (direction.equals("E"))
		{
			direction = "N";
		}
	}
	public int getRow()
	{
		return row;
	}
	public int getCol()
	{
		return col;
	}
	public void setRowandCol(int i, int j)
	{
		row = i;
		col = j;
	}
	
	public String getDirection()
	{
		return direction;
	}
	public int getCurrentHealth()
	{
		return CurrentHealth;
	}
	public void setCurrentHealth(int damaged)
	{
		CurrentHealth = CurrentHealth - damaged;
	}
	public int getMaxHealth()
	{
		return Health;
	}
	public int getAttack()
	{
		return Attack;
	}
	public void increase(Treasure t)
	{
		//Will add treasure to the player's stats, will be called in the mediator when the user picks up a treasure
		//After that, the mediator will call and repaint the player's stats.
		int hr = t.getHR();
		int gold = t.getGold();
		System.out.println("Health increase by: " + hr);
		System.out.println("Gold increase by: " + gold);
		treasure = treasure + gold;
		CurrentHealth = CurrentHealth + hr;
		if (CurrentHealth > Health)
		{
			CurrentHealth = Health;
		}
	}
}
