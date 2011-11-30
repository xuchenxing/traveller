package xcx.traveller;

import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShareToWeibo extends Activity{
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo);
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);

    	//取到token和secret，如果取不到进入授权流程
    	String oauthToken = getSharedPreferences("data", 0).getString("TRAVLLER_OAUTH_TOKEN", "");
		String oauthTokenSecret = getSharedPreferences("data", 0).getString("TRAVLLER_OAUTH_TOKEN_SECRET", "");
		if("".equals(oauthToken) && "".equals(oauthTokenSecret)){
			System.out.println("can not find token and secret");
			oauth();
		}
		OAuthConstant.getInstance().setAccessToken(new AccessToken(oauthToken,oauthTokenSecret));
		System.out.println("token="+ oauthToken + "oauthTokenSecret=" + oauthTokenSecret);
		final EditText weibocontent = (EditText)findViewById(R.id.weibocontent);
		Button send=  (Button) findViewById(R.id.send);
    	send.setOnClickListener(new Button.OnClickListener() {
            public void onClick( View v )
            {   
            	System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
            	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
                try {
                	Weibo weibo = new Weibo();
        			weibo.setToken(OAuthConstant.getInstance().getToken(), OAuthConstant.getInstance().getTokenSecret());
        			String content = weibocontent.getText().toString();
        			System.out.println("xcx"+content);
                	Status status = weibo.updateStatus(content);
                	System.out.println(status.getId() + " : "+ status.getText()+"  "+status.getCreatedAt());
                	startActivity(new Intent().setClass(ShareToWeibo.this, TravellerActivity.class));
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            }
        } );
	}

	/*
	 * 开始oauth授权
	 */
	private void oauth() {
		Weibo weibo = new Weibo();
    	RequestToken requestToken;
		try {
			requestToken =weibo.getOAuthRequestToken("weibo4android://OAuthActivity");
			OAuthConstant.getInstance().setRequestToken(requestToken);
			Uri uri = Uri.parse(requestToken.getAuthenticationURL()+ "&display=mobile");
			startActivity(new Intent(Intent.ACTION_VIEW, uri));
		} catch (WeiboException e) { 
			e.printStackTrace();
		}
	}

}
