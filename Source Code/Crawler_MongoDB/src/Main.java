
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static MongoDB db = new MongoDB();
	public static DB dbconn = db.getDB();
	public static DBCollection col = dbconn.getCollection("Crawler");
	public static Document doc;
	
	public static void main(String[] args)throws IOException{
		//processPage("http://www.bongda.com.vn");
		System.out.println("The server is running...");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(8080);
        try {
            while (true) {
                new Server_side(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
        
	}
	public static void processPage(String URL) throws IOException{
		
		int temp = 1;
		doc = Jsoup.connect(URL).get();
		//get all links and recursively call the processPage method
		col.drop();	
		//CRAW ALL:
		
		Elements questions = doc.select("a[href]");
		for(Element link: questions){
			if(link.attr("href").contains("bongda.com.vn")){
				Document doc2 = Jsoup.connect(link.attr("abs:href")).ignoreHttpErrors(true).get();
					DBObject links = new BasicDBObject("_id", temp++).append("Links", link.attr("abs:href"))
																	 .append("Title", doc2.title());
					col.insert(links);
					//writeBuffer(doc2.toString());
			}
		}
		
		//CRAW WITH CONDITION:
		
//		Elements questions = doc.select("a[href]");
//		String tag = "Arsenal";
//		for(Element link: questions){
//			if(link.attr("href").contains("bongda.com.vn")){
//				Document doc2 = Jsoup.connect(link.attr("abs:href")).ignoreHttpErrors(true).get();
//				if(doc2.body().getElementsContainingOwnText(tag).select("a[style]").attr("style").contains("margin-bottom: 5px")){
//					DBObject links = new BasicDBObject("_id", temp++).append("Links", link.attr("abs:href"))
//																	 .append("Title", doc2.title());
//					col.insert(links);
//					System.out.println(link.attr("abs:href"));
//					//writeBuffer(doc2.toString());
//				}
//			}
//		}
		
				
		System.out.println("Total: " + col.count());
		System.out.println("Craw Finished !");
	
	}
	public static void writeBuffer(String content){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\WEB.txt"))) {


			bw.write(content);

			// no need to close it.
			//bw.close();

			System.out.println("Done Writing");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	//Server thread
	private static class Server_side extends Thread{
		private Socket socket;
		private int clientNumber;
		
		public Server_side(Socket socket, int clientNumber){
			this.socket = socket;
			this.clientNumber = clientNumber;
			System.out.println("New connection with client #" + clientNumber + " at " + socket);
		}
		/* server gets the right commands and send data to client */
		public void run(){
			try{
				BufferedReader in = new BufferedReader(
	                    new InputStreamReader(socket.getInputStream()));
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            
	            String input = in.readLine();
	            
	            while(true){
	            	if(input.equals("getlink")){
		            	DBCursor cursor = col.find();
		            	while(cursor.hasNext()){
		            		out.println(cursor.next());
		            	}
		            	break;
		            }
	            	else{
	            		out.println("Error");
	            		input = in.readLine();
	            	}
	            }
			}catch (IOException e){
				System.out.println("ERROR handling client #" + clientNumber + ": " + e);
				
			}finally{
				try{
					socket.close();
				}catch (IOException e){
					System.out.println("Couldn't close a socket");
				}
				System.out.println("Connection with client #" + clientNumber + "closed !");
			}
			
		}
	}
	
}

