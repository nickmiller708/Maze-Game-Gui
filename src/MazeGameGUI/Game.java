package MazeGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame implements MouseListener, KeyListener{

	//In constructor, we can make an array list for each type of object to use in the constructor
	//The constructor will also make overview panel and call the MiniMap class which will create the custom painted objects
	private Maze m;
	protected Hashtable<String,Monster> monsters;
	protected Hashtable<String,Treasure> treasures;
	private JPanel mappanel, lowpanel, lowmap, infopanel;
	private MiniMap map;
	private Player p;
	private info stats;
	private ArrayList<Monster> adder;
	private WallImages walls;
	public Game() throws IOException
	{
		//will read in all files and generate array lists to create the maze.
		//then will generate the maze.
		
		super("My aMAZEing game!");
		try {
			p = new Player();
			monsters = new Hashtable<String,Monster>();
			treasures = new Hashtable<String,Treasure>();
			FileReader mfile = new FileReader("resources//monsters.txt");
			FileReader tfile= new FileReader("resources//treasures.txt");
			BufferedReader monsterscan = new BufferedReader(mfile);
			BufferedReader treasurescanner = new BufferedReader(tfile);
			String mnumline = monsterscan.readLine();
			Integer mnum = new Integer(mnumline);
			int monsternum = mnum.intValue();
			
			for (int i = 0; i < monsternum; i++) //read in monsters and storing in a Hashtable
			{ 
				@SuppressWarnings("unused")
				String junk = monsterscan.readLine();
				String name = monsterscan.readLine();
				String file1 = monsterscan.readLine();
				String file2 = monsterscan.readLine();
				String x1string = monsterscan.readLine();
				Integer x1Int = new Integer(x1string);
				int x1coord = x1Int.intValue();
				String y1string = monsterscan.readLine();
				Integer y1Int = new Integer(y1string);
				int y1coord = y1Int.intValue();
				String lifestring = monsterscan.readLine();
				Integer lifeInt = new Integer(lifestring);
				int lifepoints = lifeInt.intValue();
				String damageString = monsterscan.readLine();
				Integer damageInt = new Integer(damageString);
				int damagepoints = damageInt.intValue(); 
				String cooldowntime = monsterscan.readLine();
				Integer coolInt = new Integer(cooldowntime);
				int cool = coolInt.intValue();
				String probstring = monsterscan.readLine();
				Integer probInt = new Integer(probstring);
				int probMonsterhitPlayer = probInt.intValue();
				String probstring2 = monsterscan.readLine();
				Integer probInt2 = new Integer(probstring2);
				int probplayerhitmonster = probInt2.intValue(); 
				Monster adder = new Monster(this, name, file1, file2, x1coord, y1coord, lifepoints, damagepoints, cool, probMonsterhitPlayer, probplayerhitmonster);
				monsters.put(name, adder);
			}
			System.out.println("Monsters read in");
			monsterscan.close();
			String tnumline = treasurescanner.readLine();
			Integer tnumInt = new Integer(tnumline);
			int tnum = tnumInt.intValue();
			for (int j = 0; j < tnum; j++) //Reads in all the treasures and storing in a Hashtable
			{
				treasurescanner.readLine();
				String name = treasurescanner.readLine();
				String file1 = treasurescanner.readLine();
				String goldstring = treasurescanner.readLine();
				Integer goldInt = new Integer(goldstring);
				int goldvalue = goldInt.intValue();
				String healthstring = treasurescanner.readLine();
				Integer healthInt = new Integer(healthstring);
				int healthrestore = healthInt.intValue();
				String widthString = treasurescanner.readLine();
				Integer widthInt = new Integer(widthString);
				int imagewidth= widthInt.intValue();
				String height = treasurescanner.readLine();
				Integer heightInt = new Integer(height);
				int imageheight= heightInt.intValue(); 
				String xval = treasurescanner.readLine();
				Integer xInt = new Integer(xval);
				int itemx = xInt.intValue();
				String yval = treasurescanner.readLine();
				Integer yInt = new Integer(yval);
				int itemy = yInt.intValue(); 
				Treasure adder = new Treasure(name, file1, goldvalue, healthrestore, imagewidth, imageheight, itemx, itemy);
				treasures.put(name, adder);
				
			}
			treasurescanner.close();
			System.out.println("Treasures read in");
			m = new Maze(monsters, treasures); //Creating the maze
			m.addPlayer(0,0, p); //adding the player to the maze
			adder = m.getArrayList();
			//create the maze to read in for MiniMap class
			//Note: Rename MiniMap class to Painting/CustomImage
			map = new MiniMap(m); //creating the minimap from the maze
			stats = new info(p); //creating the stat bars based on the player
			walls = new WallImages(m,p); //Creating the starting wall images
			
			this.setSize(800,850);
			this.setLayout(new BorderLayout(5,5));
			this.addMouseListener(this);
			this.addKeyListener(this);
			//Panel to contain the walls
			mappanel = new JPanel();
			mappanel.setSize(new Dimension(800,600));
			mappanel.setLayout(new BorderLayout(5,5));
			mappanel.add(walls, BorderLayout.NORTH);
			this.add(mappanel, BorderLayout.NORTH);
			
			lowpanel = new JPanel();
			lowpanel.setSize(new Dimension(800,200));
			lowpanel.setBackground(new Color(200,192,232));
			lowpanel.setLayout(new BorderLayout(5,5));
			
			infopanel = new JPanel();
			infopanel.setBackground(new Color(200,192,232));
			infopanel.setSize(new Dimension(425,200));
			infopanel.add(stats);
			lowpanel.add(infopanel, BorderLayout.WEST);
			
			lowmap = new JPanel();
			lowmap.setPreferredSize(new Dimension(350,200));
			lowmap.setBackground(Color.WHITE);
			lowmap.add(map);
			lowpanel.add(lowmap,BorderLayout.EAST);
			
			this.add(lowpanel, BorderLayout.SOUTH);
			this.setVisible(true);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR. FILE NOT FOUND");
		}
	}
	public int getRowTotal()
	{
		return m.getRow();
	}
	public int getColTotal()
	{
		return m.getCol();
	}
	public int getPlayerHealth()
	{
		return p.getCurrentHealth();
	}
	public void startThreads()
	{
		ArrayList<Thread> threads = m.getThreads();
		int size = threads.size();
		for (int i = 0; i < size; i ++)
		{
			Thread monsterstart = threads.get(i);
			monsterstart.start();
		}
	}
	public void repaintWalls()
	{
		walls.repaint();
	}
	public Tile getTile(int i, int j)
	{
		return m.getTile(i, j);
	}

	public static void main(String[] args) throws IOException
	{
		Game tester = new Game();
		tester.startThreads();
	}
	public void removeMonster(int i, int j)
	{
		m.removeMonster(i, j);
	}
	public void addMonster(int i, int j, Monster mon)
	{
		//Will use to move to move monsters around and then update the map
		m.addMonster(i, j, mon);
		map.updateMaze(m);
		map.repaint();
		walls.updateMapandPlayer(m, p);
		walls.repaint();
	}
	public void playMonsterAttackSound()
	{
		try {
			AudioInputStream streamer = AudioSystem.getAudioInputStream(new File("resources//baseball.wav"));
			AudioFormat player = streamer.getFormat();
			
			DataLine.Info info = new DataLine.Info(Clip.class, player);
			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(streamer);
			audioClip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void playTreasureSound()
	{
		try {
			AudioInputStream streamer = AudioSystem.getAudioInputStream(new File("resources//coin2.wav"));
			AudioFormat player = streamer.getFormat();
			
			DataLine.Info info = new DataLine.Info(Clip.class, player);
			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(streamer);
			audioClip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateWalls()
	{
		walls.updateMapandPlayer(m, p);
		walls.repaint();
	}
	public void decreasePlayerHealth(int damage)
	{
		p.setCurrentHealth(damage);
		stats.updatePlayer(p);
		stats.repaint();
	}
	public int getPlayerRow()
	{
		return p.getRow();
	}
	public int getPlayerCol()
	{
		return p.getCol();
	}
	public boolean checkMonsterUp(int i, int j)
	{
		//Will return true if there is a monster in the tile above the player,
		//Else will return false
		boolean returner = false;
		Tile checker = null;
		if (i > 0)
		{
			checker = m.getTile(i-1, j);
		}
		if (checker != null)
		{
			returner = checker.hasMonster();
		}
		return returner;
	}
	public boolean checkMonsterDown(int i, int j)
	{
		//Will return true if there is a monster in the tile below the player
		//Else will return false
		boolean returner = false;
		Tile checker = null;
		if (i < m.getRow()-1)
		{
			checker = m.getTile(i+1, j);
		}
		if (checker != null)
		{
			returner = checker.hasMonster();
		}
		return returner;
	}
	public boolean checkMonsterRight(int i, int j)
	{
		//Will return true if there is a monster in the tile to the right of the player
		//Else return false
		boolean returner = false;
		Tile checker = null;
		if (j < m.getCol()-1)
		{
			checker = m.getTile(i, j+1);
		}
		if (checker != null)
		{
			returner = checker.hasMonster();
		}
		return returner;
	}
	public boolean checkMonsterLeft(int i, int j)
	{
		//Returns true if htere is a mosnter to the left of the player
		//Else returns falls
		boolean returner = false;
		Tile checker = null;
		if (j > 0)
		{
			checker = m.getTile(i, j-1);
		}
		if (checker != null)
		{
			returner = checker.hasMonster();
		}
		return returner;
	}
	@Override
	public void mouseClicked(MouseEvent me) {
		//Allows the player to move, attack, and pick up treasure
		//Computations are made here to perform those actions, same as in the keyboardPressed function
		int x = (int) me.getX();
		int y = (int) me.getY();
		int prowi = p.getRow();
		int pcoli = p.getCol();
		int pcol = pcoli;
		int prow = prowi;
		int rowtotal = m.getRow();
		int coltotal = m .getCol();
		String direction = p.getDirection();
		Tile playert = m.getTile(prow, pcol);
		boolean north = playert.haswallnorth();
		boolean south = playert.haswallsouth();
		boolean west = playert.haswallwest();
		boolean east = playert.haswalleast();
		boolean monster = false;
		boolean treasure = false;
		int xahead = -1;
		int yahead = -1;
		Tile ahead=  null;
		if (direction.equals("N")) //Gets the Tile if facing north
		{
			prow = prow -1;
		}
		else if (direction.equals("S")) //Gets the Tile if facing south
		{
			prow = prow + 1;
		}
		else if (direction.equals("E")) //Facing East
		{
			pcol = pcol + 1;
		}
		else if (direction.equals("W")) //Facing West
		{
			pcol = pcol -1;
		}
		if ((pcol >= 0) && (pcol <= coltotal-1) && (prow >= 0) && ( prow <= rowtotal-1))
		{
			ahead = m.getTile(prow,pcol);
			monster =ahead.hasMonster();
			treasure = ahead.hasTreasure();
			xahead = prow;
			yahead = pcol;
		}
		
		if ((x > 265) && (x < 310) && ( y > 720) && (y < 760))
		{
			System.out.println("Player rotates left");
			p.RotateLeft();
			stats.updatePlayer(p);
			stats.repaint();
			walls.updateMapandPlayer(m, p);
			walls.repaint();
		}
		else if ((x > 320) && (x < 360) && ( y > 720) && ( y < 760))
		{
			//Player moves up relative to their direction
			System.out.println("Player moves up");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				prow = prowi -1;
			}
			else if (direction.equals("S")) //Facing South
			{
				prow = prowi + 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				pcol = pcoli + 1;
			}
			else if (direction.equals("W")) //Facing West
			{
				pcol = pcoli -1;
			}
			if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (north != true)&& (direction.equals("N")&&(checkMonsterUp(prowi,pcoli) == false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("S")&&(checkMonsterDown(prowi,pcoli) == false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("E")&&(checkMonsterRight(prowi,pcoli) == false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("W"))&&(checkMonsterLeft(prowi,pcol) == false))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
		}
		else if ((x > 370) && ( x < 420) && ( y > 720) && ( y < 760))
		{
			System.out.println("Player rotates right");
			p.RotateRight();
			stats.updatePlayer(p);
			stats.repaint();
			walls.updateMapandPlayer(m, p);
			walls.repaint();
		}
		else if ((x>265) && (x < 315) && (y >770) && ( y < 810))
		{
			//Player moves left after hitting the left button
			System.out.println("Player moves to the left");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				pcol = pcoli - 1;
			}
			else if (direction.equals("S")) //Facing South
			{
				pcol = pcoli + 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				prow = prowi -1;
			}
			else if (direction.equals("W")) //Facing West
			{
				prow = prowi + 1;
			}
			if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("N")&&(checkMonsterLeft(prowi,pcoli) == false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("S")&&(checkMonsterRight(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli <=coltotal-1) && (north != true)&& (direction.equals("E")&&(checkMonsterUp(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("W")&&(checkMonsterDown(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
		}
		else if ((x > 320) && ( x < 365) && (y > 770) && ( y < 810))
		{
			//Player will move down after hitting the down button
			System.out.println("Player moves down");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				prow = prowi + 1;
			}
			else if (direction.equals("S")) //Facing South
			{
				prow = prowi - 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				pcol = pcoli -1;
			}
			else if (direction.equals("W")) //Facing West
			{
				pcol = pcoli + 1;
			}
			if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("N")&&(checkMonsterDown(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (north != true)&& (direction.equals("S")&&(checkMonsterUp(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("E")&&(checkMonsterLeft(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("W")&&(checkMonsterRight(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
		}
		else if ((x >370) && (x < 420) && ( y > 770) && ( y < 810))
		{
			System.out.println("Player moves to the right");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				pcol = pcoli + 1;
			}
			else if (direction.equals("S")) //Facing South
			{
				pcol = pcoli - 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				prow = prowi + 1;
			}
			else if (direction.equals("W")) //Facing West
			{
				prow = prowi - 1;
			}
			if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("N")&&(checkMonsterRight(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("S")&&(checkMonsterLeft(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("E")&&(checkMonsterDown(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (north != true)&& (direction.equals("W")&&(checkMonsterUp(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
		}
		else if ((x >290) && ( x < 420) && ( y > 660) && ( y < 700) && (ahead != null))
		{
			System.out.println("You try to attack!");
			if (monster)
			{
				Monster attacked = ahead.getMonster();
				Random rand = new Random();
				int probattack = rand.nextInt(100);
				int attack = p.getAttack();
				if (probattack < attacked.getPhitbyP())
				{
					System.out.println("You hit the " + attacked.getName() + " !");
					attacked.setHealth(attack);
				}
				else
				{
					System.out.println("You missed the " + attacked.getName() + " !");
				}
				if (attacked.getHealth() < 0)
				{
					adder.remove(adder.indexOf(attacked));
					m.removeMonster(prow, pcol);
					System.out.println(attacked.getName() + " died!");
					
				}
				else
				{
					m.addMonster(xahead, yahead, attacked);
				}
			}
			map.updateMaze(m);
			map.repaint();
			walls.updateMapandPlayer(m, p);
			walls.repaint();
		}
		if (treasure)
		{
			Treasure onmap = ahead.getTreasure();
			int xinitial = onmap.getXCoordinate();
			int xfinal = xinitial + 2*onmap.getWidth();
			int yinitial = onmap.getYCoordinate();
			int yfinal = yinitial + 2*onmap.getHeight();
			if ((x >= xinitial) && (x <= xfinal) && (y >= yinitial) && (y<= yfinal))
			{
				this.playTreasureSound();
				p.increase(onmap);
				m.removeTreasure(xahead, yahead);
				stats.updatePlayer(p);
				stats.repaint();
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
				
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent me) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent me) {
		
		
	}
	@Override
	public void mousePressed(MouseEvent me) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent me) {
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//Movement based on keyboard presses, computations for movement same as in mousePressed, but accounting for keyboard values
		
		// TODO Auto-generated method stub
		int prowi = p.getRow();
		int pcoli = p.getCol();
		int pcol = pcoli;
		int prow = prowi;
		int rowtotal = m.getRow();
		int coltotal = m .getCol();
		String direction = p.getDirection();
		Tile playert = m.getTile(prow, pcol);
		boolean north = playert.haswallnorth();
		boolean south = playert.haswallsouth();
		boolean west = playert.haswallwest();
		boolean east = playert.haswalleast();
		boolean monster = false;
		boolean treasure = false;
		int xahead = -1;
		int yahead = -1;
		Tile ahead=  null;
		if (direction.equals("N")) //Gets the Tile if facing north
		{
			prow = prow -1;
		}
		else if (direction.equals("S")) //Gets the Tile if facing south
		{
			prow = prow + 1;
		}
		else if (direction.equals("E")) //Facing East
		{
			pcol = pcol + 1;
		}
		else if (direction.equals("W")) //Facing West
		{
			pcol = pcol -1;
		}
		if ((pcol >= 0) && (pcol <= coltotal-1) && (prow >= 0) && ( prow <= rowtotal-1))
		{
			ahead = m.getTile(prow,pcol);
			monster =ahead.hasMonster();
			treasure = ahead.hasTreasure();
			xahead = prow;
			yahead = pcol;
		}
		if (((e.getKeyChar() == 'w'))||((e.getKeyCode() == KeyEvent.VK_UP)))
		{
			//Player moves up
			System.out.println("Player moves up");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				prow = prowi -1;
			}
			else if (direction.equals("S")) //Facing South
			{
				prow = prowi + 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				pcol = pcoli + 1;
			}
			else if (direction.equals("W")) //Facing West
			{
				pcol = pcoli -1;
			}
			if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (north != true)&& (direction.equals("N")&&(checkMonsterUp(prowi,pcoli) == false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("S")&&(checkMonsterDown(prowi,pcoli) == false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("E")&&(checkMonsterRight(prowi,pcoli) == false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("W"))&&(checkMonsterLeft(prowi,pcol) == false))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
		}
		else if (((e.getKeyChar() == 's'))||((e.getKeyCode() == KeyEvent.VK_DOWN)))
		{
			//Player moves down
			System.out.println("Player moves down");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				prow = prowi + 1;
			}
			else if (direction.equals("S")) //Facing South
			{
				prow = prowi - 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				pcol = pcoli -1;
			}
			else if (direction.equals("W")) //Facing West
			{
				pcol = pcoli + 1;
			}
			if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("N")&&(checkMonsterDown(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (north != true)&& (direction.equals("S")&&(checkMonsterUp(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("E")&&(checkMonsterLeft(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("W")&&(checkMonsterRight(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
		}
		else if (((e.getKeyChar() == 'a'))||((e.getKeyCode() == KeyEvent.VK_LEFT)))
		{
			//Player moves left
			System.out.println("Player moves to the left");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				pcol = pcoli - 1;
			}
			else if (direction.equals("S")) //Facing South
			{
				pcol = pcoli + 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				prow = prowi -1;
			}
			else if (direction.equals("W")) //Facing West
			{
				prow = prowi + 1;
			}
			if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("N")&&(checkMonsterLeft(prowi,pcoli) == false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("S")&&(checkMonsterRight(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli <=coltotal-1) && (north != true)&& (direction.equals("E")&&(checkMonsterUp(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("W")&&(checkMonsterDown(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
		}
		else if (((e.getKeyChar() == 'd'))||((e.getKeyCode() == KeyEvent.VK_RIGHT)))
		{
			//Player moves right
			System.out.println("Player moves to the right");
			m.removePlayer(prowi,pcoli);
			if (direction.equals("N")) //Facing North
			{
				pcol = pcoli + 1;
			}
			else if (direction.equals("S")) //Facing South
			{
				pcol = pcoli - 1;
			}
			else if (direction.equals("E")) //Facing East
			{
				prow = prowi + 1;
			}
			else if (direction.equals("W")) //Facing West
			{
				prow = prowi - 1;
			}
			if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (east != true)&& (direction.equals("N")&&(checkMonsterRight(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prowi >= 0) && (prowi <= rowtotal-1) && (pcol >= 0) && (pcol<=coltotal-1) && (west != true)&& (direction.equals("S")&&(checkMonsterLeft(prowi,pcoli)==false)))
			{
				p.setRowandCol(prowi, pcol);
				m.addPlayer(prowi, pcol, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (south != true)&& (direction.equals("E")&&(checkMonsterDown(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			else if ((prow >= 0) && (prow <= rowtotal-1) && (pcoli >= 0) && (pcoli<=coltotal-1) && (north != true)&& (direction.equals("W")&&(checkMonsterUp(prowi,pcoli)==false)))
			{
				p.setRowandCol(prow, pcoli);
				m.addPlayer(prow, pcoli, p);
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
			
			else
			{
				m.addPlayer(prowi, pcoli, p);
				System.out.println("There's a wall there");
			}
			
		}
		else if (e.getKeyChar() == 'e')
		{
			//Player rotates right
			p.RotateRight();
			stats.updatePlayer(p);
			stats.repaint();
			walls.updateMapandPlayer(m, p);
			walls.repaint();
		}
		else if (e.getKeyChar() == 'q')
		{
			//Player rotates left
			p.RotateLeft();
			stats.updatePlayer(p);
			stats.repaint();
			walls.updateMapandPlayer(m, p);
			walls.repaint();
		}
		else if (e.getKeyChar() ==  ' ')
		{
			//Player attacks
			System.out.println("You try to attack!");
			if (monster)
			{
				Monster attacked = ahead.getMonster();
				Random rand = new Random();
				int probattack = rand.nextInt(100);
				int attack = p.getAttack();
				if (probattack < attacked.getPhitbyP())
				{
					System.out.println("You hit the " + attacked.getName() + " !");
					attacked.setHealth(attack);
				}
				else
				{
					System.out.println("You missed the " + attacked.getName() + " !");
				}
				if (attacked.getHealth() < 0)
				{
					adder.remove(adder.indexOf(attacked));
					m.removeMonster(prow, pcol);
					System.out.println(attacked.getName() + " died!");
					
				}
				else
				{
					m.addMonster(xahead, yahead, attacked);
				}
			}
			map.updateMaze(m);
			map.repaint();
			walls.updateMapandPlayer(m, p);
			walls.repaint();
		}
		else if (e.getKeyChar() == 't')
		{
			if (treasure)
			{
				Treasure onmap = ahead.getTreasure();
				this.playTreasureSound();
				p.increase(onmap);
				m.removeTreasure(xahead, yahead);
				stats.updatePlayer(p);
				stats.repaint();
				map.updateMaze(m);
				map.repaint();
				walls.updateMapandPlayer(m, p);
				walls.repaint();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
