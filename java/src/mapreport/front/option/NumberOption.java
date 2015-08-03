package mapreport.front.option;

import mapreport.util.Log;

public class NumberOption extends Option {
	Double value;

	public Double getNumberValue() {
		return value;
	}

	public NumberOption(String name, String value) {
		super(name, value);
		try {
			this.value = Double.valueOf(value);
		} catch (NumberFormatException e) {
			 Log.info(" NumberOption UNKNOWN value:" + value);
		}
	}

}
