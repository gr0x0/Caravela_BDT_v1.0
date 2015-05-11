package com.motion.supportClasses;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class InterfaceManager
{
	static private	InterfaceManager 	pInterfaceManager = null;
	static private	InterfaceFrame		iFrame = null;
	static protected int FrameSizeX = 1024;
	static protected int FrameSizeY = 512;
	static protected int MatrixColumns;
	static protected int MatrixRows;
	static protected int StringsSpacingX;
	static protected int StringsSpacingY;

	static protected String backgroundImgPath;				//Endereço - no diretório - da imagem a ser 
																//usada como background
	static protected String agentImgPath;					//Endereço - no diretório - da imagem a ser 
																//usada como agente
	static protected Matrix zonesCoordMatrix;				//Matriz - mapa de posições - do agente, com 
																//o valor de cada coordenada como custo
	static protected ArrayList<Integer>	agentCoordinates;	//Coordenadas X e Y instantâneas do agente na 
																//matriz zonesCoordMatrix. É inicializado
																//com apenas 2 casas de valores inteiros
																

	static public InterfaceManager getInterfaceManager(String backgroundImgPath, String agentImgPath, 
			Matrix zonesCoordMatrix) 
	{
		if (pInterfaceManager == null){
			pInterfaceManager = new InterfaceManager(backgroundImgPath, agentImgPath, zonesCoordMatrix);
			return pInterfaceManager;
		}		
		else {
			return pInterfaceManager;
		}
	}

	private InterfaceManager(String backgroundImgPath, String agentImgPath, Matrix zonesCoordMatrix)
	{
		InterfaceManager.zonesCoordMatrix = zonesCoordMatrix;
		InterfaceManager.backgroundImgPath = backgroundImgPath;
		InterfaceManager.agentImgPath = agentImgPath;

		InterfaceManager.agentCoordinates = new ArrayList<Integer>(2);
		InterfaceManager.agentCoordinates.add(0, 0);
		InterfaceManager.agentCoordinates.add(1, 0);	
		
		if(InterfaceManager.zonesCoordMatrix!=null)
		{
			InterfaceManager.MatrixColumns = InterfaceManager.zonesCoordMatrix.getX();
			InterfaceManager.MatrixRows = InterfaceManager.zonesCoordMatrix.getY();
			InterfaceManager.StringsSpacingX = (int) (InterfaceManager.FrameSizeX/InterfaceManager.MatrixColumns);
			InterfaceManager.StringsSpacingY = (int) (InterfaceManager.FrameSizeY/InterfaceManager.MatrixRows);
		}

		iFrame = new InterfaceFrame();
	}

	public void setAgentCoord(int coordX, int coordY)
	{
		InterfaceManager.agentCoordinates.set(0, (int)(coordX/InterfaceManager.MatrixColumns));
		InterfaceManager.agentCoordinates.set(1, (int)(coordY/InterfaceManager.MatrixRows));	    
		/*TEST PRINT*/System.out.printf("   Agent(x,y) = "+coordX+", "+coordY+"\n");
		
		this.notifyRepaint();
	}

	private void notifyRepaint() 
	{
		iFrame.repaint();		
	}
}

//----------CLASSE INTERFACE-FRAME----------//
class InterfaceFrame extends JFrame
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected InterfaceFrame()
	{					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.setBounds(10, 100, InterfaceManager.FrameSizeX, InterfaceManager.FrameSizeY);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setLayout(new BorderLayout());
		this.setVisible(true);	

		InterfacePanel iPanel = new InterfacePanel();
		this.add(iPanel);
		iPanel.setVisible(true);

		this.validate();		
	}
}

//----------CLASSE INTERFACE-PANEL----------//
class InterfacePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image imgBackground;
	private Image imgAgent;

	protected InterfacePanel()
	{}

	@Override
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		Graphics2D graphics2D = (Graphics2D) g;

		try
		{
			//Pintando o background (com fundo branco ou com imagem de parâmetro, se houver)
			if(InterfaceManager.backgroundImgPath == null)
			{
				graphics2D.setColor(Color.WHITE);
				graphics2D.drawRect(0, 0, InterfaceManager.FrameSizeX, InterfaceManager.FrameSizeY);
			}
			else
			{
				imgBackground = ImageIO.read(new File(InterfaceManager.backgroundImgPath));
				g.drawImage(imgBackground, 0, 0, null);
			}

			//Pintando o agente (como um círculo preto ou com imagem de parâmetro, se houver)
			if(InterfaceManager.agentImgPath != null)
			{
				imgAgent = ImageIO.read(new File(InterfaceManager.agentImgPath));
				g.drawImage(imgAgent, 
						InterfaceManager.agentCoordinates.get(0), 
						InterfaceManager.agentCoordinates.get(1), 
						Color.WHITE, 
						null);
			}
			else
			{
				g.drawOval(InterfaceManager.agentCoordinates.get(0), 
						InterfaceManager.agentCoordinates.get(1), 
						18, 18);
				g.setColor(Color.BLACK);
				g.fillOval(InterfaceManager.agentCoordinates.get(0), 
						InterfaceManager.agentCoordinates.get(1), 
						18, 18);
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}
