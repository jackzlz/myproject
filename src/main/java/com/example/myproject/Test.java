package com.example.myproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Test implements Serializable{
	
	static class OOMObject{}

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4201618362939820350L;

	public static void main(String[] args) throws Exception {
		List<OOMObject> list = new ArrayList<Test.OOMObject>();
		while(true){
			list.add(new OOMObject());
		}
	}
}
