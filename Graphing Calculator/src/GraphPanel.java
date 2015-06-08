import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GraphPanel extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener
{
	private double xMin, yMin, xMax, yMax;
	private boolean derivative;

	public GraphPanel()
	{
		setPreferredSize(new Dimension(1920, 1080));

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		
		xMin = -1;
		yMin = -1;
		xMax = 1;
		yMax = 1;
		
		derivative = false;
	}
	
	public double f(double x)
	{
		return x * x;
	}
	
	public double mapX(double x)
	{
		return (x - xMin) / (xMax - xMin) * getWidth();
	}
	
	public double mapY(double y)
	{
		return getHeight() * (1 - (y - yMin) / (yMax - yMin));
	}
	
	public double unmapX(double x)
	{
		return x / getWidth() * (xMax - xMin) + xMin;
	}
	
	public double unmapY(double y)
	{
		return (1 - y / getHeight()) * (yMax - yMin) + yMin;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLACK);
		g.drawLine(0, (int) Math.round(mapY(0)), getWidth(), (int) Math.round(mapY(0)));
		g.drawLine((int) Math.round(mapX(0)), 0, (int) Math.round(mapX(0)), getHeight());
		
		double xTickInterval = Math.max(1, Math.round((xMax - xMin) / 10));
		for (double x = (int) xMin; x <= xMax; x += xTickInterval)
		{
			g.drawLine((int) Math.round(mapX(x)), (int) Math.round(mapY(0)) - 5, (int) Math.round(mapX(x)), (int) Math.round(mapY(0)) + 5);
			g.drawString("" + x, (int) Math.round(mapX(x)) - 20, (int) Math.round(mapY(0)) + 25);
		}
		
		double yTickInterval = Math.max(1, Math.round((yMax - yMin) / 10));
		for (double y = (int) yMin; y <= yMax; y += yTickInterval)
		{
			g.drawLine((int) Math.round(mapX(0)) - 5, (int) Math.round(mapY(y)), (int) Math.round(mapX(0)) + 5, (int) Math.round(mapY(y)));
			g.drawString("" + y, (int) Math.round(mapX(0)) - 30, (int) Math.round(mapY(y)));
		}
		
		g.setColor(Color.RED);
		
		double xInterval = (xMax - xMin) / getWidth();
		
		double lastY = Double.NaN;
		double lastDy = Double.NaN;
		
		for (int x = 0; x < getWidth(); x++)
		{
			double y = f(unmapX(x));
			double dy = Double.NaN;
			
			if (Double.isFinite(lastY) && Double.isFinite(y))
			{
				g.drawLine((int) x - 1, (int) Math.round(mapY(lastY)), (int) x, (int) Math.round(mapY(y)));
				dy = (y - lastY) / xInterval;
			}
			
			if (derivative && Double.isFinite(dy) && Double.isFinite(lastDy))
			{
				g.drawLine((int) x - 1, (int) Math.round(mapY(lastDy)), (int) x, (int) Math.round(mapY(dy)));
			}
			
			lastY = y;
			lastDy = dy;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			double move = (yMax - yMin) / 10;
			yMin += move;
			yMax += move;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			double move = (yMax - yMin) / 10;
			yMin -= move;
			yMax -= move;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			double move = (xMax - xMin) / 10;
			xMin -= move;
			xMax -= move;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			double move = (xMax - xMin) / 10;
			xMin += move;
			xMax += move;
		}
		else if (e.getKeyCode() == KeyEvent.VK_W)
		{
			double scale = (yMax - yMin) / 20;
			yMin += scale;
			yMax -= scale;
		}
		else if (e.getKeyCode() == KeyEvent.VK_S)
		{
			double scale = (yMax - yMin) / 20;
			yMin -= scale;
			yMax += scale;
		}
		else if (e.getKeyCode() == KeyEvent.VK_A)
		{
			double scale = (xMax - xMin) / 20;
			xMin -= scale;
			xMax += scale;
		}
		else if (e.getKeyCode() == KeyEvent.VK_D)
		{
			double scale = (xMax - xMin) / 20;
			xMin += scale;
			xMax -= scale;
		}
		else if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			derivative = !derivative;
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}