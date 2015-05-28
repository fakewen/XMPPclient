import org.jivesoftware.smack.*;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.listener.*;

public class Reciever{


	public static void main(String[] args) {
			String USER_NAME = "admin";
			String PASSWORD = "admin";
			String ServerAddress = "140.112.170.26";
			//String ServerAddress = "192.168.0.103";
			String ServerName = "pubsub.wukongdemac-mini.local";
			String port="5222";
		XMPP.XMPPinit(USER_NAME,PASSWORD,ServerAddress,port,ServerName);

		ItemEventListener<PayloadItem> lis=new ItemEventListener<PayloadItem>(){
				public void handlePublishedItems(ItemPublishEvent evt){
				int i=0;
				
				System.out.println("Get one item");
				for(Object obj : evt.getItems()){
				System.out.println("chek num="+i++);
				PayloadItem item = (PayloadItem) obj;
				System.out.println("--:Payload=" + item.getPayload().toString());
				}
				}

		};
		String nodeId = "nooneknow";
		XMPP.XMPPsubscribe(nodeId,lis);


	}


}
