package com.games.andronoid;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class Parser {
	
	static public Game CreateGame(Resources rc)
	{
		Game gl = new Game();
		try
		{
			XmlResourceParser parser = rc.getXml(R.xml.game);
			int eventType = parser.getEventType();

			Stage st = null;
			Level lvl = null;
			String tagName;
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				 if(eventType == XmlPullParser.START_TAG) {
					 
					 tagName = parser.getName();
					 if(tagName.equalsIgnoreCase("stage"))
					 {
							st = new Stage();
							st.setId(parser.getAttributeIntValue(null, "id", -1));			
							st.setBackgroundImgFile(parser.getAttributeValue(null, "background"));
							st.setMusic(parser.getAttributeValue(null, "music"));
							 
							gl.Add(st);	        		 
					 }
					 else if(tagName.equalsIgnoreCase("mosaic"))
					 {
						 lvl = new Level();
						 lvl.setMosaicName(parser.getAttributeValue(null, "name"));
						 st.Add(lvl);
					 }
				}
				 
				 eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return gl;		
	}

	public static Mosaic CreateMosaic(Resources rc, String name)
	{
		Mosaic mosaic = new Mosaic();
        try {
        	XmlPullParserFactory factory = XmlPullParserFactory.newInstance ();
        	XmlPullParser parser = factory.newPullParser();
        	parser.setInput(rc.getAssets().open(name), "utf-8");
			int eventType = parser.getEventType();
				
			String tagName = null;
			Row row = null;
			while(eventType != XmlResourceParser.END_DOCUMENT)
			{
				if(eventType == XmlResourceParser.START_TAG)
				{
					tagName = parser.getName();
					if(tagName.equalsIgnoreCase("row"))
					{
						row = new Row();
						mosaic.addRow(row);
					}
					else if(tagName.equalsIgnoreCase("hole"))
					{
						row.addBrick(Brick.Create(Brick.Type.hole, rc));
					}
					else if(tagName.equalsIgnoreCase("green"))
					{
						row.addBrick(Brick.Create(Brick.Type.green, rc));
					}
				}
				
					eventType = parser.next();
				
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mosaic;
	}
}
