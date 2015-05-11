package com.motion.supportClasses;

public interface ObservadoIF {
	
	public void add(ObservadorIF o);
	public void remove(ObservadorIF o);
	public void atualizaObservadores(ObservadoIF o);
	public Object get();

}
