package mapreport.front.option;

import mapreport.util.Log;

public class Option {
	String name;
	String value;
	
	public Option (String value) {
		Log.info("Option (String value) name:" + name + " value:" + value);
		this.value = value;
	}
	
	public Option (String name, String value) {
		Log.info("Option (String value, String name) name:" + name + " value:" + value);
		this.value = value;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "Option " + name + ": " + value;
	}
}
