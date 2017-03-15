import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TheGioi extends BongDa {
	@Override
	public void craw(){
		try{
			Server.doc = Jsoup.connect("http://vnexpress.net/tin-tuc/the-gioi").get();
			//get all links and recursively call the processPage method
			Elements questions = Server.doc.select("a[href]");
			for(Element link: questions){
				if(link.attr("href").contains("vnexpress.net")){
					Document doc2 = Jsoup.connect(link.attr("abs:href")).ignoreHttpErrors(true).get();
					//if(!doc2.body().getElementsByAttributeValueContaining("src", "media.bongda.com.vn/files").attr("src").isEmpty()){
						DBObject links = new BasicDBObject().append("Link", link.attr("abs:href"))
								 							.append("Title", doc2.title())
								 							.append("Type", "The Gioi")
								 							.append("Images",doc2.body().getElementsByAttributeValueContaining("src","http://img.f29.vnecdn.net/" ).attr("src"));

						System.out.println(doc2.body().getElementsByAttributeValueContaining("src","http://img.f29.vnecdn.net/" ).attr("src"));
						Server.col.insert(links);
					//}
				}
			}
		}catch(IOException e){
			System.out.println("Craw The Gioi news finished !");
		}
	}
}
