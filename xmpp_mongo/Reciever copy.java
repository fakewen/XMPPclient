
import org.jivesoftware.smack.*;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.listener.*;

//Mongo's libs with java
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;

//JSAON

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Reciever{
	static DBCollection coll;
static DBCollection conn(){
	DBCollection coll2=new DBCollection();
	try{
		         MongoClient mongoClient = new MongoClient( "140.112.170.32" , 27017 );
         DB db = mongoClient.getDB( "wukong" );
         DBCollection coll3 = db.getCollection("readings");
         return coll3;
	}catch(Exception e){
      	System.out.println("ERROR");
	     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  }
	  return coll2;
}
	public static void main(String[] args) {
			String USER_NAME = "xmpp_mongo";
			String PASSWORD = "xmpp_mongo";
			String ServerAddress = "140.112.170.26";
			//String ServerAddress = "192.168.0.103";
			String ServerName = "pubsub.wukongdemac-mini.local";
			String port="5222";
			//MongoClient mongoClient;
			//DB db;
			
		XMPP.XMPPinit(USER_NAME,PASSWORD,ServerAddress,port,ServerName);

		ItemEventListener<PayloadItem> lis=new ItemEventListener<PayloadItem>(){
				public void handlePublishedItems(ItemPublishEvent evt){
				
				//System.out.println("Get one item");
				for(Object obj : evt.getItems()){
				
				PayloadItem item = (PayloadItem) obj;
				String msg=item.getPayload().toString();
				msg= msg.substring(msg.indexOf("{"),msg.indexOf("}")+1);
String[] aArray = msg.split(",");
int[] cArray=new int[6];
for(int i=0;i<6;i++){
	String[] bArray = aArray[i].split(": ");
	cArray[i]=Integer.valueOf(bArray[1]);//bArray[1]
}

				System.out.println("Get context from Kinnect:" +cArray[0]+cArray[1]+cArray[2]+cArray[3]+cArray[4]+cArray[5]);		
//transport to Mongo

	try{   
coll=conn();
         BasicDBObject doc = new BasicDBObject("node_id", "kinect").
            append("port", "kinect").
            append("ppnum1", cArray[0]).
            append("ppnum2", cArray[1]).
            append("ppnum3", cArray[2]).
            append("ppnum4", cArray[3]).
            append("ppnum5", cArray[4]).
            append("ppnum6", cArray[5]);
         coll.insert(doc);
         System.out.println("Document inserted into MongoDB successfully");
      }catch(Exception e){
      	System.out.println("ERROR");
	     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  }
}
				}

		};
		String nodeId = "nooneknow";
		XMPP.XMPPsubscribe(nodeId,lis);


	}


}

