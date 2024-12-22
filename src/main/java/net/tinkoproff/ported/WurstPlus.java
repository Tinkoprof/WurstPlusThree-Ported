package net.tinkoproff.ported;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WurstPlus implements ModInitializer {
	public static final String MOD_ID = "wp3"
	public static final String LOG_NAME = "WurstPlusThree-ported"
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Welcome to WurstPlusThree ported by Tinkoprof");
	}
}
