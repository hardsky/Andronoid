package com.games.andronoid;
import java.util.*;
public class Part {
	private ArrayList<Part> mParts;
	private int mCurIdx = 0;
	
    public Part () 
    { 
    	mParts = new ArrayList<Part>(); 
    };
    
    public void Add( Part child )
    {
    	mParts.add(child);
    }


    public Part Next(  )
    {
    	return mParts.get(mCurIdx++);
    }


    public boolean IsEnded()
    {
    	return mCurIdx > mParts.size();
    }

}
