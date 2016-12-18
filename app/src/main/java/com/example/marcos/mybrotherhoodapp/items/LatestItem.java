package com.example.marcos.mybrotherhoodapp.items;

/**
 * Class LatestItem
 * Declares the attributes needed to manage the links in the latest section
 */
public class LatestItem {

	private static final String ITEM_SEP = System.getProperty("line.separator");

	private String mHeadline;
	private String mLink;

	public LatestItem(String headline, String link) {
		this.mHeadline = headline;
		this.mLink = link;
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

	@Override
	public String toString() {
		return mHeadline + ITEM_SEP + mLink;
	}
}
