package com.boxtrotstudio.fishing;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.handlers.GameHandler;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		Fishing.setDefaultHandler(new GameHandler());

		initialize(Fishing.getInstance(), config);
	}
}
