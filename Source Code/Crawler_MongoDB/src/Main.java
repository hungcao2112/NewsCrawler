
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import java.util.Arrays;
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
		//Document doc = Jsoup.connect("http://uit.edu.vn").get();
		processPage("http://bongda.com.vn");
        
	}
	public static void processPage(String URL) throws IOException{
		
		int temp = 1;
		Scanner sc = new Scanner(System.in);
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
					System.out.println(link.attr("abs:href"));
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
		sc.close();
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
}

