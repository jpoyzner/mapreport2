package mapreport.front.option;

import mapreport.util.Log;

public class BooleanOption extends Option {
	Boolean value;
	
	public Boolean getBoolValue() {
		return value;
	}

	public BooleanOption(String name, String value) {
		super(name, value);
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
		    this.value = Boolean.valueOf(value);
		} else {
		    Log.info(" BooleanOption UNKNOWN value:" + value);
		}
	}

}
