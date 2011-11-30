package xcx.traveller;

import java.util.List;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OAuthActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo);
		Uri uri=this.getIntent().getData();
		try {
			RequestToken requestToken= OAuthConstant.getInstance().getRequestToken();
			AccessToken accessToken=requestToken.getAccessToken(uri.getQueryParameter("oauth_verifier"));
			OAuthConstant.getInstance().setAccessToken(accessToken);
			
			saveAccessTokenToPreferData(accessToken);
			System.out.println("授权成功");
			OAuthActivity.this.startActivity(new Intent().setClass(OAuthActivity.this, ShareToWeibo.class));
			System.out.println("跳转成功");
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 保存accessToken和accessTokenSevret到手机sharedata中
	 */
	private void saveAccessTokenToPreferData(AccessToken accessToken) {
		Editor sharedata = getSharedPreferences("data", 0).edit();
		sharedata.putString("TRAVLLER_OAUTH_TOKEN", accessToken.getToken());
		sharedata.putString("TRAVLLER_OAUTH_TOKEN_SECRET", accessToken.getTokenSecret());
		sharedata.commit();
	}
}
