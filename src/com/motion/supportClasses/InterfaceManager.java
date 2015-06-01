package com.motion.supportClasses;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class InterfaceManager
{
	private static	InterfaceManager 	pInterfaceManager 	= null;
	private static	MapFrame			mapFrame 			= null;

	protected static int StringsSpacingX;
	protected static int StringsSpacingY;
	protected static int LowestX = 0, LowestY = 0, HighestX = 0, HighestY = 0;

	protected static int[][]	zonesLimitsCoords;	//Vetor Nx4 de pares de coords das zonas
	protected static int[]		agentCoordAtuais;	//Vetor 2x1 com o par de coords do agente
													//atualizadas a cada instante

	private InterfaceManager(int[][] zonesLimitCoords)
	{
		InterfaceManager.zonesLimitsCoords = zonesLimitCoords;

//		this.fixAgentCoordinates();		
		agentCoordAtuais = new int[2];
		mapFrame = new MapFrame();

		if(InterfaceManager.zonesLimitsCoords!=null)
		{
			InterfaceManager.LowestX 	= zonesLimitsCoords[0][0];
			InterfaceManager.HighestX 	= zonesLimitsCoords[0][1];
			InterfaceManager.LowestY 	= zonesLimitsCoords[0][2];
			InterfaceManager.HighestY 	= zonesLimitsCoords[0][3];
			
			for(int i = 0; i<zonesLimitsCoords.length-1; i++)
			{
				if(zonesLimitsCoords[i][0] < InterfaceManager.LowestX)
					InterfaceManager.LowestX = zonesLimitsCoords[i][0];
				if(zonesLimitsCoords[i][1] > InterfaceManager.HighestX)
					InterfaceManager.HighestX = zonesLimitsCoords[i][1];
				if(zonesLimitsCoords[i][2] < InterfaceManager.LowestY)
					InterfaceManager.LowestY = zonesLimitsCoords[i][2];
				if(zonesLimitsCoords[i][3] > InterfaceManager.HighestY)
					InterfaceManager.HighestY = zonesLimitsCoords[i][3];
			}
			
			InterfaceManager.StringsSpacingX = (int)(mapFrame.getMapSizeFrameX()/
					(InterfaceManager.HighestX - InterfaceManager.LowestX));
			InterfaceManager.StringsSpacingY = (int)(mapFrame.getMapSizeFrameY()/
					(InterfaceManager.HighestY - InterfaceManager.LowestY));
		}
	}
	
	public static InterfaceManager getInterfaceManager(int[][] zonesLimitsCoords) 
	{
		if (pInterfaceManager == null){
			pInterfaceManager = new InterfaceManager(zonesLimitsCoords);
			return pInterfaceManager;
		}		
		else {
			return pInterfaceManager;
		}
	}

	public void setAgentCoord(int agentCoordX, int agentCoordY)
	{
		InterfaceManager.agentCoordAtuais[0] = agentCoordX*InterfaceManager.StringsSpacingX;
		InterfaceManager.agentCoordAtuais[1] = agentCoordY*InterfaceManager.StringsSpacingY;
	}

	public void notifyRepaint() 
	{
		mapFrame.notifyRepaint(InterfaceManager.agentCoordAtuais);
	}

//	private void fixAgentCoordinates()
//	{
//		@SuppressWarnings("unchecked")
//		ArrayList<int[]> agentCoordinatesClone = (ArrayList<int[]>)InterfaceManager.agentCoordinates.clone();
//		Iterator<int[]> iterator = agentCoordinatesClone.iterator();		
//		for(int i=InterfaceManager.agentCoordinates.size()-1; iterator.hasNext(); i--)
//		{
//			InterfaceManager.agentCoordinates.set(i, iterator.next());
//		}
//		InterfaceManager.agentCoordinates.add(0, 
//				new int[]{this.agentCoordIniciais[0],this.agentCoordIniciais[1]});
//	}
}

//----------CLASSE MAP-FRAME----------//
class MapFrame extends JFrame
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static MapPanel 	mapPanel 		= null;
	final protected int MapFrameSizeX = 602;
	final protected int MapFrameSizeY = 646;

	protected MapFrame()
	{					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.setBounds(100, 40, this.MapFrameSizeX, this.MapFrameSizeY);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);	
		
		this.validate();	
		mapPanel = new MapPanel();
		this.add(mapPanel);
	}
		
	protected void notifyRepaint(int[] agentCoordinates)
	{
		mapPanel.repaint();
	}
	
	protected int getMapSizeFrameX()
	{
		return this.MapFrameSizeX;
	}
	
	protected int getMapSizeFrameY()
	{
		return this.MapFrameSizeY;
	}
}

//----------CLASSE INTERFACE-PANEL----------//
class MapPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> colors = new ArrayList<String>(Arrays.asList("black", "blue", "cyan", 
			"darkGray", "green", "lightGray", "magenta", "orange", "pink", "red", "white", 
			"yellow"));
	
	protected MapPanel()
	{}

	@Override
	public void paintComponent(Graphics g)
	{
		for(int i = 0; i<InterfaceManager.zonesLimitsCoords.length-1; i++)
		{
			String color = colors.get(i);
			if(color.equals("black"))			g.setColor(Color.black);
			else if(color.equals("blue")) 		g.setColor(Color.blue);
			else if(color.equals("darkGray")) 	g.setColor(Color.darkGray);
			else if(color.equals("green")) 		g.setColor(Color.green);
			else if(color.equals("lightGray")) 	g.setColor(Color.lightGray);
			else if(color.equals("magenta")) 	g.setColor(Color.magenta);
			else if(color.equals("organge")) 	g.setColor(Color.orange);
			else if(color.equals("pink")) 		g.setColor(Color.pink);
			else if(color.equals("red"))		g.setColor(Color.red);
			else if(color.equals("white"))		g.setColor(Color.white);
			else if(color.equals("yellow"))		g.setColor(Color.yellow);
			else g.setColor(Color.black);
			
			g.drawRect(InterfaceManager.zonesLimitsCoords[i][0], 
					InterfaceManager.zonesLimitsCoords[i][1], 
					InterfaceManager.zonesLimitsCoords[i][0]-InterfaceManager.zonesLimitsCoords[i][3], 
					InterfaceManager.zonesLimitsCoords[i][1]-InterfaceManager.zonesLimitsCoords[i][4]);
		}
		
		g.drawOval(InterfaceManager.agentCoordAtuais[0], 
				InterfaceManager.agentCoordAtuais[1], 
				10, 10);
	}
}