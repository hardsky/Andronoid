package com.games.andronoid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarPreference extends Preference implements
OnSeekBarChangeListener {

	private TextView valueTextView;
	private int currentValue;
	private int max;
	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		max = attrs.getAttributeIntValue(
				"http://schemas.android.com/apk/res/com.games.andronoid", "maxVal", 100);
		currentValue = attrs.getAttributeIntValue(
				"http://schemas.android.com/apk/res/com.games.andronoid",
				"curVal", 30);
	}

	@Override
	public View getView(View convertView, ViewGroup parent)
	{
	    if (convertView == null)
	    {
	        return super.getView(convertView, parent);
	    }
	    return super.getView(convertView, parent);
	}
	
	@Override
	protected View onCreateView(ViewGroup parent) {

		currentValue = this.getPersistedInt(30);
		
		LinearLayout layout = (LinearLayout) LayoutInflater.from(
				getContext())
				.inflate(R.layout.seek_bar_preference_layout, null);

		SeekBar bar = (SeekBar) layout.findViewById(R.id.seekPrefBar);
		bar.setMax(max);
		bar.setProgress(currentValue);
		bar.setOnSeekBarChangeListener(this);
		
		((TextView)layout.findViewById(R.id.seekPrefTitle)).setText(getTitle());

		valueTextView = (TextView) layout.findViewById(R.id.seekPrefValue);
		valueTextView.setText(getSummary() + " " + currentValue);

		return layout;
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		currentValue = seekBar.getProgress();
		updatePreference(currentValue);
		notifyChanged();
		valueTextView.invalidate();
	}

	private void updatePreference(int newValue) {
		if (shouldPersist()) {
		    persistInt(newValue);
		}		
		valueTextView.setText(getSummary() + " " + newValue);
	}
}