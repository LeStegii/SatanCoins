package de.almightysatan.coins;

import java.util.UUID;

public interface CoinsCommandSender {

	boolean isPlayer();
	UUID getUUID();
	void sendMessage(String message);
	boolean hasPermission(String permission);
}
