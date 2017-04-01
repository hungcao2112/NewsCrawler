
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.*;

public class Server extends WebSocketServer{
	public static MongoDB db = new MongoDB();
	public static DB dbconn = db.getDB();
	public static DBCollection col = dbconn.getCollection("Links");
	public static DBCollection usr = dbconn.getCollection("User");
	public static Document doc;
	public static WebSocketServer server;
	public static WebSocket c;
	
	public Server(InetSocketAddress address) {
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
	    try {
			JSONObject obj = new JSONObject(message);
			String TOPIC = obj.getString("Topic");
			if(TOPIC.equals("LOGIN")){
				onLogin(message);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
	    System.err.println("an error occured on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
	}
	
	
	public static void main(String[] args)throws IOException{
		//BongDa bongda = new BongDa();
//		bongda.craw();
//		TheGioi thegioi = new TheGioi();
//		thegioi.craw();
//		CongNghe congnghe = new CongNghe();
//		congnghe.craw();
		String host = "192.168.1.106";
	    int port = 8887;
	    server = new Server(new InetSocketAddress(host, port));
	    server.run();
		
        
	}
//	public static void writeBuffer(String content){
//		try (BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/caonguyen/text.txt"))) {
//			bw.write(content);
//			// no need to close it.
//			//bw.close();
//			System.out.println("Done Writing");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void sendToAll( String text ) {
		Collection<WebSocket> con = connections();
		synchronized ( con ) {
			for( WebSocket c : con ) {
				c.send( text );
			}
		}
	}
	public void onLogin(String message){
		try {
			JSONObject obj = new JSONObject(message);
			String id = obj.getString("Id");
			String pass = obj.getString("Pass");
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> object = new ArrayList<BasicDBObject>();
			object.add(new BasicDBObject("Id", id));
			object.add(new BasicDBObject("Pass", pass));
			andQuery.put("$and", object);
			
			DBCursor cursor = col.find(andQuery);
			if(cursor.hasNext()){
				JSONObject ret = new JSONObject();
				ret.put("Topic","RLOGIN");
				ret.put("Rcode","200");
				c.send(ret.toString());
			}else{
				JSONObject ret = new JSONObject();
				ret.put("Topic","RLOGIN");
				ret.put("Topic", "201");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
}	

