import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class SimpleServer extends WebSocketServer {
	 public SimpleServer(InetSocketAddress address) {
	        super(address);
	    }

	    @Override
	    public void onOpen(WebSocket conn, ClientHandshake handshake) {
	        System.out.println("new connection to " + conn.getRemoteSocketAddress());
	    }

	    @Override
	    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
	        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
	    }

	    @Override
	    public void onMessage(WebSocket conn, String message) {
	        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
	        conn.send("OK");
	    }

	    @Override
	    public void onError(WebSocket conn, Exception ex) {
	        System.err.println("an error occured on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
	    }

	    public static void main(String[] args) {
	        String host = "192.168.1.106";
	        int port = 8080;

	        WebSocketServer server = new SimpleServer(new InetSocketAddress(host, port));
	        server.run();
	    }
	}
