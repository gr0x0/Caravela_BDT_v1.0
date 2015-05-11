package com.motion.cartografo;

import java.util.ArrayList;
import com.motion.cartografo.Mar;

public class MarList extends ArrayList<Mar>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Mar findMar(double latAtual, double lonAtual)
	{		
		for (int i = 0; i < this.size(); i++) 
		{
			Mar marElem = (Mar) this.get(i);
//			/*TEST PRINT*/System.out.print("marElem ---> lat0 = "+marElem.getLatIni()+"; latF = "+marElem.getLatFim()+"; lon0 = "+marElem.getLonIni()+"; lonF = "+marElem.getLonFim()+"\n");
			if(latAtual >= marElem.getLatIni() && latAtual <= marElem.getLatFim() && 
					lonAtual >= marElem.getLonIni() && lonAtual <= marElem.getLonFim())
			{
				return marElem;
			}			
		}
		
		return null;
	}	
}
