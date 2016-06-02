/*
 *  This file is distributed under the GPL3 license.
 *  Please see the file LICENSE that should be present in this distribution.
 *
 *  Copyright 2011
 *   Jonas Hellström <jonas@if-then-else.se>
 *   Emma Ekberg <emma.ekberg@gmail.com>
 *  
 *  Maintained by
 *   Jonas Hellström <jonas@if-then-else.se>
 *   Emma Ekberg <emma.ekberg@gmail.com>
 *  since 2011
 */

package se.bthstudent.android.bsk;

import se.bthstudent.android.bsk.R;
import se.bthstudent.android.bsk.rssreader.RssItem;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleActivity extends Activity implements BSKLOG {
	RssItem mCurrent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article);

		TextView article_title = (TextView) findViewById(R.id.article_title);
		ImageView article_icon = (ImageView) findViewById(R.id.article_icon);
		TextView article_date = (TextView) findViewById(R.id.article_date);
		WebView article_content = (WebView) findViewById(R.id.article_webview);

		mCurrent = ((BskData) getApplication()).getCurrentArticle();
		String content;
		if(mCurrent.getEncodedContent() == null) {
			content = mCurrent.getDescription();
		} else {
			content = mCurrent.getEncodedContent();
		}

		// Adds a formatted date string that includes the leading day of week
		// and the complete date with time
		String dateAndAuthor = mCurrent.getFormattedPubDate();
		Resources res = this.getResources();
		if(mCurrent.getAuthor() != null) {
			dateAndAuthor = dateAndAuthor + " " + res.getString(R.string.by).toLowerCase() + " " + mCurrent.getAuthor();
		}

		// TODO: Remove these prepending strings if they prove to be unnecessary.
		final String header = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"sv-SE\">";
		final String headStart = "<head>";
		// Styling tags to get the webview to display text color a bit nicer and more readable.
		// Protip: There is a stylesheet.css in the assets-folder that helps with styling.
		final String cssPath = "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/stylesheet.css\" />";
		final String headEnd = "</head><body>";
		final String htmlBegin = "<div>";
		final String htmlEnd = "</div></body></html>";
		// Combine all the styling and encoding support into one single stringed entity.
		final String fixedContent = header + headStart + cssPath + headEnd + htmlBegin + content + htmlEnd;

		article_title.setText(mCurrent.getTitle());
		article_icon.setBackgroundResource(mCurrent.getFeed().getIcon());
		article_date.setText(dateAndAuthor);
		article_content.loadDataWithBaseURL("", fixedContent, "text/html", "UTF-8", null);
		//article_content.setBackgroundColor(Color.BLACK);

		// Connect a listener to the "Open article (web)" button on the article layout
		// Upon a tap, send an intent containing the URL and start a new activity
		Button openArticle = (Button) findViewById(R.id.article_button_open);
		openArticle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = mCurrent.getUrl().toString();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}
}