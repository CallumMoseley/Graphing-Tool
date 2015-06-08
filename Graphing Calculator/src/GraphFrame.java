import javax.swing.JFrame;

public class GraphFrame extends JFrame
{
	GraphPanel p;
	
	public GraphFrame()
	{
		super("Frame");
		p = new GraphPanel(); 
		
		setContentPane(p);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args)
	{
		GraphFrame frame = new GraphFrame();
		frame.pack();
		frame.setVisible(true);
	}
}