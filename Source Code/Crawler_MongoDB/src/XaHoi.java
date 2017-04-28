import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class XaHoi extends BongDa {
	@Override
	public void craw() throws IOException{
		try{
		Server.doc = Jsoup.connect("http://dantri.com.vn/xa-hoi.htm").get();
		//get all links and recursively call the processPage method
		Elements questions = Server.doc.select("a[href]");
		for(Element link: questions){
			if(link.attr("href").contains("/xa-hoi/")){
				Document doc2 = Jsoup.connect(link.attr("abs:href")).ignoreHttpErrors(true).get();
				//if(!doc2.body().getElementsByAttributeValueContaining("src", "dantricdn.com/zoom").attr("src").isEmpty()){
				DBObject links = new BasicDBObject().append("Link", link.attr("abs:href"))
						 							.append("Title", doc2.title())
						 							.append("Type","Xa Hoi")
						 							.append("Images",doc2.body().getElementsByAttributeValueContaining("src","dantricdn.com/zoom/").attr("src"));

				System.out.println(doc2.body().getElementsByAttributeValueContaining("src", "dantricdn.com/zoom/").attr("src"));
				Server.col.insert(links);
				//}
				}
			}
		}catch(IOException e){
			System.out.println("Craw Xa Hoi news finished !");
	  }
	}
}