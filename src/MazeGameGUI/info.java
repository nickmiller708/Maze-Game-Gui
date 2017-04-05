package MazeGameGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class info extends JPanel
{
	private ImageIcon attack, movement;
	private Player p;
	public info(Player p)
	{
		attack = new ImageIcon("resources//attackButton.png");
		movement = new ImageIcon("resources//movebuttons.png");
		this.setBackground(new Color(200,192,232));
		this.setPreferredSize(new Dimension(425,200));
		this.p = p;
	}
	public void paint(Graphics g)
	{
		//draws all the statistics of the player, including, health, treasure, and compass
		//This will continually get from the mediator by updating the player
		super.paint(g);
		int health = p.getCurrentHealth();
		g.drawImage(attack.getImage(), 275, 25, null);
		g.drawImage(movement.getImage(), 250,75,null);
		String direction = p.getDirection();
		g.setColor(Color.black);
		Font f = new Font("Lucinda Handwriting", Font.BOLD, 15);
		g.setFont(f);
		g.drawString("Treasure", 25, 100);
		g.drawString("" + p.getTreasure(), 25, 125);
	
		g.drawString("Health", 25, 10);
		g.drawRect(25, 25, 201, 25);
		if (health >= 67)
		{
			g.setColor(Color.green);
		}
		else if ((health < 67) && (health > 33))
		{
			g.setColor(Color.yellow);
		}
		else if ((health <= 33))
		{
			g.setColor(Color.red);
		}
		g.fillRect(25,25,2*health, 25);
		g.setColor(Color.black);
		g.fillRect(230, 55, 30, 5);
		g.fillRect(242, 45, 5, 30);
		if (direction.equals("N"))
		{
			g.drawString("N", 242, 35);
		}
		else if (direction.equals("E"))
		{
			g.drawString("N", 216, 62);
			
		}
		else if (direction.equals("W"))
		{
			
			g.drawString("N", 264, 62);

		}
		else if (direction.equals("S"))
		{
			
			g.drawString("N", 242,90);
		}
	}
	public void updatePlayer(Player p)
	{
		//Will always have an updated player so it can redraw the stats correctly
		this.p = p;
	}
}
