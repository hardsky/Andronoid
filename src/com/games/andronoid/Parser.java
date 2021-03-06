package com.games.andronoid;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
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
						 lvl.setLevelName(parser.getAttributeValue(null, "level"));
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

	public static Mosaic CreateMosaic(Context context, Resources rc, GameSettings settings, float metersToPixelsX, float metersToPixelsY){
		
		String name = settings.getMosaicName();
		boolean bIsSoundOn = settings.isSoundOn();
		Mosaic mosaic = new Mosaic();
		
        try {
        	
        	XmlPullParserFactory factory = XmlPullParserFactory.newInstance ();
        	XmlPullParser parser = factory.newPullParser();
        	parser.setInput(rc.getAssets().open(name), "utf-8");
			int eventType = parser.getEventType();
				
			String tagName = null;
			Row row = null;
			while(eventType != XmlResourceParser.END_DOCUMENT){
				
				if(eventType == XmlResourceParser.START_TAG){
					
					tagName = parser.getName();
					if(tagName.equalsIgnoreCase("row")){
						row = new Row();
						mosaic.addRow(row);
					}
					else if(tagName.equalsIgnoreCase("hole"))
						row.addBrick(Brick.Create(context, Brick.Type.hole, rc, metersToPixelsX, metersToPixelsY, bIsSoundOn));
					else if(tagName.equalsIgnoreCase("green"))
						row.addBrick(Brick.Create(context, Brick.Type.green, rc, metersToPixelsX, metersToPixelsY, bIsSoundOn));
					else if(tagName.equalsIgnoreCase("blue"))
						row.addBrick(Brick.Create(context, Brick.Type.blue, rc, metersToPixelsX, metersToPixelsY, bIsSoundOn));
					else if(tagName.equalsIgnoreCase("pink"))
						row.addBrick(Brick.Create(context, Brick.Type.pink, rc, metersToPixelsX, metersToPixelsY, bIsSoundOn));
					else if(tagName.equalsIgnoreCase("red"))
						row.addBrick(Brick.Create(context, Brick.Type.red, rc, metersToPixelsX, metersToPixelsY, bIsSoundOn));
					else if(tagName.equalsIgnoreCase("sea"))
						row.addBrick(Brick.Create(context, Brick.Type.sea, rc, metersToPixelsX, metersToPixelsY, bIsSoundOn));
					else if(tagName.equalsIgnoreCase("yellow"))
						row.addBrick(Brick.Create(context, Brick.Type.yellow, rc, metersToPixelsX, metersToPixelsY, bIsSoundOn));
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
