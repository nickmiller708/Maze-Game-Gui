package MazeGameGUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Maze 
{
	//This class will construct the maze and have methods to call to check specific parts of the maze
	private int rooms, rows, columns;
	private Tile[][] maze;
	private ArrayList<Monster> monsters;
	private ArrayList<Thread> threadlist;
	public Maze(Hashtable<String,Monster>monsters, Hashtable<String, Treasure> treasures) throws IOException
	{
		try
		{
			FileReader fin = new FileReader("resources//map.txt");
			BufferedReader mapreader = new BufferedReader(fin);
			String roomString = mapreader.readLine();
			Integer roomInt = new Integer(roomString);
			rooms = roomInt.intValue();
			String rowString = mapreader.readLine();
			Integer rowInt = new Integer(rowString);
			rows = rowInt.intValue();
			String colString = mapreader.readLine();
			Integer colInt = new Integer(colString);
			columns = colInt.intValue();
			maze = new Tile[rows][columns];
			this.monsters = new ArrayList<Monster>();
			boolean wallnorth;
			boolean wallsouth;
			boolean walleast;
			boolean wallwest;
			threadlist = new ArrayList<Thread>();
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					mapreader.readLine();
					String monster = mapreader.readLine();
					String treasure = mapreader.readLine();
					String walln = mapreader.readLine();
					if (walln.equals("true"))
					{
						 wallnorth = true;
					}
					else 
					{
						wallnorth = false;
					}
					String walle = mapreader.readLine();
					if (walle.equals("true"))
					{
						 walleast = true;
					}
					else
					{
						walleast = false;
					}
					String walls= mapreader.readLine();
					if (walls.equals("true"))
					{
						 wallsouth= true;
					}
					else
					{
						wallsouth = false;
					}
					String wallw = mapreader.readLine();
					if (wallw.equals("true"))
					{
						 wallwest= true;
					}
					else 
					{
						wallwest = false;
					}
					String texture = mapreader.readLine();
					Tile adder = new Tile(i, j, monster, treasure, wallnorth, wallsouth, walleast, wallwest, texture, monsters, treasures);
					maze[i][j] = adder; //creating each tile and adding to the maze
					wallnorth = false;
					wallsouth = false;
					walleast = false;
					wallwest = false;
					if (adder.hasMonster())
					{
						//Will have a list of all the threads to be passed to the mediator
						//Will only happen if a tile has a monster
						this.monsters.add(adder.getMonster());
						Thread threadadd = new Thread(adder.getMonster());
						threadlist.add(threadadd);
					}
				}
			}
			mapreader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR: Map File Not Found");
		}
	}
	public ArrayList<Thread> getThreads()
	{
		return threadlist;
	}
	public void addPlayer(int i, int j, Player p)
	{
		maze[i][j].addPlayer(p);
	}
	public void removePlayer(int i, int j)
	{
		maze[i][j].removePlayer();
	}
	public void addMonster(int i, int j, Monster m)
	{
		maze[i][j].addMonster(m);
	}
	public void removeMonster(int i, int j)
	{
		maze[i][j].removeMonster();
	}
	public int getRow()
	{
		return rows;
	}
	public void removeTreasure(int i, int j)
	{
		maze[i][j].removeTreasure();
	}
	public int getCol()
	{
		return columns;
	}
	public int getRooms()
	{
		return rooms;
	}
	public Tile getTile(int i, int j)
	{
		//Will return desired tile if within the confines of the maze,
		//Else returns null
		if ((i >= 0) && (i <= rows -1) && ( j >= 0) && ( j <= columns-1))
		{
		return maze[i][j];
		}
		else
		{
			return null;
		}
	}
	public void updateMonsters(ArrayList<Monster> m)
	{
		this.monsters = m;
		int size = monsters.size();
		for (int i = 0; i < size; i++)
		{
			Monster add = monsters.get(i);
			maze[add.getRow()][add.getCol()].removeMonster();
			maze[add.getRow()][add.getCol()].addMonster(add);
		}
	}
	public ArrayList<Monster> getArrayList()
	{
		return monsters;
	}
}
