package mapreport.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

@WebServlet("/wsnews")
public class WebSocketServletClass extends WebSocketServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String protocol, HttpServletRequest request) {
		return new MapReportMessageInpound();
	}
	
	private class MapReportMessageInpound extends MessageInbound {
		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			//Don't forget that WebSockets recycle the connection (uses this same class instance and variables)!
			
			//getWsOutbound().writeTextMessage(CharBuffer.wrap(Endpoints.news()));
			getWsOutbound().flush();
		}
		
		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			throw new UnsupportedOperationException("Binary message not supported.");
		}
	}
}