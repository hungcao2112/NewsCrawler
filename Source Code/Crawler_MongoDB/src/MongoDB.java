import com.mongodb.MongoClient;


import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import java.util.Arrays;
import java.util.List;

public class MongoDB {
	private DB db;
	public MongoDB(){
		try{
			MongoClient mongoClient = new MongoClient("localhost",27017);
			
			db = mongoClient.getDB("Data");
			System.out.println("Connect Successfully");
			
	    }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
	}
	
	public DB getDB(){
		return db;
	}
}
