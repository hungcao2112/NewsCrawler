
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
import com.mongodb.WriteConcern;
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
	public static DBCollection usr = dbconn.getCollection("Users");
	public static Document doc;
	public static WebSocketServer server;
	public static WebSocket c;
	int i=0;
	public Server(InetSocketAddress address) {
	    super(address);
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("new connection to " + conn.getRemoteSocketAddress());
		System.out.println("Number of Connections: " + i++);
		c=conn;
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
			else if(TOPIC.equals("GETLINK")){
				onGetLink(message);
			}
			else if(TOPIC.equals("HISTORY")){
				onHistory(message);
			}
			else if(TOPIC.equals("GETSUGGEST")){
				onSuggest(message);
			}
			else{
				onFailure();
				System.out.println("Command Not Found !");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
	   ex.printStackTrace();
	}
	
	
	public static void main(String[] args)throws IOException{
//		BongDa bongda = new BongDa();
//		bongda.craw();
//		TheGioi thegioi = new TheGioi();
//		thegioi.craw();
//		CongNghe congnghe = new CongNghe();
//		congnghe.craw();
		String host = "10.45.94.67";
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
			String id = obj.getString("UserId");
			String pass = obj.getString("Pass");
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> object = new ArrayList<BasicDBObject>();
			object.add(new BasicDBObject("UserId", id));
			object.add(new BasicDBObject("Pass", pass));
			andQuery.put("$and", object);
			
			DBCursor cursor = usr.find(andQuery);
			if(cursor.hasNext()){
				JSONObject ret = new JSONObject();
				ret.put("Topic","RLOGIN");
				ret.put("Rcode","200");
				c.send(ret.toString());
			}else{
				JSONObject ret = new JSONObject();
				ret.put("Topic","RLOGIN");
				ret.put("Topic", "201");
				c.send(ret.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	public void onGetLink(String message){
		try {
			JSONObject obj = new JSONObject(message);
			String type = obj.getString("Type");
			JSONObject data = new JSONObject();
			JSONArray array = new JSONArray();
			data.put("Topic","RGETLINK");
			data.put("Rcode","200");
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("Type", type);
			DBCursor cursor = col.find(whereQuery);
			while(cursor.hasNext()) {
			    array.put(cursor.next());
			}
			data.put("RLinks",array);
			System.out.println(data.toString());
			c.send(data.toString());
			System.out.println(cursor.count());
			System.out.println(array.length());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onHistory(String message){
		try {
			JSONObject obj = new JSONObject(message);
			String type = obj.getString("Type");
			BasicDBObject query = new BasicDBObject();
		    query.put("UserId", "hungcao");
		    query.put("History.Type", type);
		    BasicDBObject incValue = new BasicDBObject("History.$.Count", 1); // or "items.damage" ???
		    BasicDBObject intModifier = new BasicDBObject("$inc", incValue);
		    usr.update(query, intModifier, false, false, WriteConcern.SAFE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onSuggest(String message){
		try {
			JSONObject obj = new JSONObject(message);
			JSONObject data = new JSONObject();
			JSONArray array = new JSONArray();
			data.put("Topic","RGETSUGGEST");
			data.put("Rcode", "200");
			String id = obj.getString("UserId");
			BasicDBObject query = new BasicDBObject();
		    query.put("UserId", id);
		    DBCursor cursor = usr.find(query);
		    while(cursor.hasNext()){
		    	array.put(cursor.next());
		    }
		    data.put("Data",array);
		    System.out.println(data.toString());
		    c.send(data.toString());
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onFailure(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("Rcode","201");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}	

