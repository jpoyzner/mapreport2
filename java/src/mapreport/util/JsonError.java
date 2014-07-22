package mapreport.util;
import org.json.JSONException;
import org.json.JSONObject; 

public class JsonError {
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	String errMsg;
	String exception;
	String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public JsonError(String errMsg, Exception e) {
		this.errMsg = errMsg;
		JSONObject jObject = new JSONObject();
        try {
			jObject.put("errMsg", errMsg);
			for (StackTraceElement element : e.getStackTrace()) {
				   exception += element.toString() + "<br> \n";
			}

			jObject.put("exceptionStack", exception);
			
			text = jObject.toString();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public JsonError(Exception e) {
		this.errMsg = e.getMessage();
		
	}
	
	private void addException() {
		
	}
}
