package de.almightysatan.coins;

import java.util.UUID;

public interface CoinsPlayer {

	UUID getUUID();
	void sendMessage(String message);
	boolean hasPermission(String permission);
}
