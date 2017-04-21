import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class CongNghe implements Crawler{
	@Override
	public void craw() throws IOException{
		Server.doc = Jsoup.connect("https://www.thongtincongnghe.com/").get();
		//get all links and recursively call the processPage method
		Elements questions = Server.doc.select("a[href]");
		for(Element link: questions){
			if(link.attr("href").contains("thongtincongnghe.com")){
				Document doc2 = Jsoup.connect(link.attr("abs:href")).ignoreHttpErrors(true).get();
				//if(!doc2.body().getElementsByAttributeValueContaining("src", "https://static.techtalk.vn/").attr("src").contains("https://static.techtalk.vn/wp-content/uploads/2016/02/logoretina.png")){
					DBObject links = new BasicDBObject().append("Link", link.attr("abs:href"))
							 							.append("Title", doc2.title())
							 							.append("Type", "Cong Nghe")
							 							.append("Images",doc2.body().getElementsByAttributeValueContaining("src", "https://www.thongtincongnghe.com/sites/default/files/imagecache/").attr("src"));

					BasicDBObject andQuery = new BasicDBObject();
					List<BasicDBObject> object = new ArrayList<BasicDBObject>();
					object.add(new BasicDBObject("Type", "Cong Nghe"));
					object.add(new BasicDBObject("Link", link.attr("abs:href")));
					andQuery.put("$and", object);
					
					DBCursor cursor = Server.col.find(andQuery);
					if(cursor.count()==0){
						Server.col.insert(links);
						System.out.println(link.attr("abs:href"));
					}
				//}
			}
		}
		System.out.println("Craw Cong Nghe news finished !");
	}
}
