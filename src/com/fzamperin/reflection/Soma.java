package com.fzamperin.reflection;

public class Soma implements Component{
	
	private Float a,b;

	@Override
	public void setParameter(Object... objects) {
		// TODO Auto-generated method stub
		if(objects.length == 2){
			if(objects[0].getClass() == String.class) {
				a = Float.parseFloat(objects[0].toString());
			}
			else {
				a = (Float) objects[0];
			}
			if(objects[1].getClass() == String.class) {
				b = Float.parseFloat(objects[1].toString());
			}
			else {
				b = (Float) objects[1];
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		b = a + b;
	}

	@Override
	public Object getOutput() {
		// TODO Auto-generated method stub
		return b;
	}

}
