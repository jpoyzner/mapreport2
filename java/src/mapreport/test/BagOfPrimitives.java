package mapreport.test;

import com.google.gson.Gson;

class BagOfPrimitives {
	  @SuppressWarnings("unused")
	private int value1 = 1;
	  @SuppressWarnings("unused")
	private String value2 = "abc";
	  @SuppressWarnings("unused")
	private transient int value3 = 3;
	  BagOfPrimitives() {
	    // no-args constructor
	  }
	  
	    public static void main (String args[]) {
	    	BagOfPrimitives obj = new BagOfPrimitives();
	    	Gson gson = new Gson();
	    	String json = gson.toJson(obj);
	    	
	    	System.out.println(json);
	    }
}