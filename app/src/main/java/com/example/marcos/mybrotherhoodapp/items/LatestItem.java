package com.example.marcos.mybrotherhoodapp.items;

import android.content.Intent;

public class LatestItem {

	public static final String ITEM_SEP = System.getProperty("line.separator");

	public final static String HEADLINE = "headline";
	public final static String LINK = "link";

	private String mHeadline = new String();
	private String mLink = new String();

	public LatestItem(String headline, String link) {
		this.mHeadline = headline;
		this.mLink = link;
	}

	// Create a new ToDoItem from data packaged in an Intent

	public LatestItem(Intent intent) {

		mHeadline = intent.getStringExtra(LatestItem.HEADLINE);
		mLink = intent.getStringExtra(LatestItem.LINK);
	}

	public String getHeadline() {
		return mHeadline;
	}

	public void setHeadline(String headline) {
		mHeadline = headline;
	}

	public String getLink() {
		return mLink;
	}

	public void setLink(String link) {
		mLink = link;
	}

	public static void packageIntent(Intent intent, String headline, String link) {

		intent.putExtra(LatestItem.HEADLINE, headline);
		intent.putExtra(LatestItem.LINK, link);
	
	}

	@Override
	public String toString() {
		return mHeadline + ITEM_SEP + mLink;
	}

	public String toLog() {
		return "Headline:" + mHeadline + ITEM_SEP + "Link:" + mLink;
	}

}
