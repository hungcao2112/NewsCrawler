import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class BongDa {
	public void craw() throws IOException{
		Server.doc = Jsoup.connect("http://bongda.com.vn").get();
		//get all links and recursively call the processPage method
		Server.col.drop();	
		//CRAW ALL:
		
		Elements questions = Server.doc.select("a[href]");
		for(Element link: questions){
			if(link.attr("href").contains("bongda.com.vn")){
				Document doc2 = Jsoup.connect(link.attr("abs:href")).ignoreHttpErrors(true).get();
				//if(!doc2.body().getElementsByAttributeValueContaining("src", "media.bongda.com.vn/files").attr("src").isEmpty()){
					DBObject links = new BasicDBObject().append("Link", link.attr("abs:href"))
							 							.append("Title", doc2.title())
							 							.append("Type", "Bong Da")
							 							.append("Images",doc2.body().getElementsByAttributeValueContaining("src", "media.bongda.com.vn/files").attr("src"));

					System.out.println(link.attr("abs:href"));
					Server.col.insert(links);
				//}
			}
		}
		System.out.println("Craw BongDa news finished !");
	}
}
