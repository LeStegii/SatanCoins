package de.almightysatan.coins;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Coins {

	private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
	private static Mysql sql;
	private static Map<UUID, Integer> balance = new HashMap<>();
	
	static void init(String url, String user, String password) {
		try {
			sql = new Mysql(url, user, password);
		} catch(SQLException e) {
			throw new Error("Unable to connect to database", e);
		}
	}
	
	static void loadBalance(UUID uuid) {
		try {
			balance.put(uuid, sql.getCoins(uuid));
		} catch(SQLException e) {
			throw new Error("Unable to load coins for uuid " + uuid, e);
		}
	}
	
	public static int getCoins(UUID uuid) {
		return balance.get(uuid);
	}
	
	public static void setCoins(UUID uuid, int amount) {
		balance.put(uuid, amount);
		
		EXECUTOR.execute(() -> {
			try {
				sql.setCoins(uuid, amount);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void addCoins(UUID uuid, int amount) {
		balance.put(uuid, balance.get(uuid) + amount);
		
		EXECUTOR.execute(() -> {
			try {
				sql.addCoins(uuid, amount);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void removeCoins(UUID uuid, int amount) {
		addCoins(uuid, -amount);
	}
}
