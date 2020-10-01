package de.almightysatan.coins;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CoinsBukkit extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		try {
			loadConfig();
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	private void loadConfig() throws IOException {
		File configFile = new File("plugins/coins/config.yml");

		YamlConfiguration yamlConfiguration = null;

		if(!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			configFile.createNewFile();

			yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

			yamlConfiguration.set("mysql.url", "jdbc:mysql://localhost:port/db-name");
			yamlConfiguration.set("mysql.user", "db-user");
			yamlConfiguration.set("mysql.pw", "db-password");
			yamlConfiguration.save(configFile);
			return;
		}else
			yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

		Coins.init(yamlConfiguration.getString("mysql.url"), yamlConfiguration.getString("mysql.user"), yamlConfiguration.getString("mysql.pw"));
	}

	@EventHandler
	public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
		Coins.loadBalance(e.getUniqueId());
	}
}
