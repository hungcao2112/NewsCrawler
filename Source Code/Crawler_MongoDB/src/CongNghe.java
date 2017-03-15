import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CongNghe extends BongDa{
	@Override
	public void craw() throws IOException{
		Server.doc = Jsoup.connect("https://www.thongtincongnghe.com/").get();
		//get all links and recursively call the processPage method
		Elements questions = Server.doc.select("a[href]");
		for(Element link: questions){
			if(link.attr("href").contains("")){
				Document doc2 = Jsoup.connect(link.attr("abs:href")).ignoreHttpErrors(true).get();
				//if(!doc2.body().getElementsByAttributeValueContaining("src", "https://static.techtalk.vn/").attr("src").contains("https://static.techtalk.vn/wp-content/uploads/2016/02/logoretina.png")){
					DBObject links = new BasicDBObject().append("Link", link.attr("abs:href"))
							 							.append("Title", doc2.title())
							 							.append("Type", "Cong Nghe")
							 							.append("Images",doc2.body().getElementsByAttributeValueContaining("src", "https://www.thongtincongnghe.com/sites/default/files/imagecache/").attr("src"));

					System.out.println(doc2.body().getElementsByAttributeValueContaining("src", "https://www.thongtincongnghe.com/sites/default/files/imagecache/").attr("src"));
					Server.col.insert(links);
				//}
			}
		}
		System.out.println("Craw Cong Nghe news finished !");
	}
}
