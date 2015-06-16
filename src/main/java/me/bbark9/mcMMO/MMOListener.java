package me.bbark9.mcMMO;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import me.bbark9.mcMMO.abstraction.PlayerSkillEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author import
 */
public class MMOListener implements Listener {

    CommandHelperPlugin plugin;

    public MMOListener(CommandHelperPlugin plugin) {
        this.plugin = plugin;
        plugin.registerEvents(this);
    }

    public void unregister() {
        McMMOPlayerLevelUpEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onLevelUp(McMMOPlayerLevelUpEvent event) {
        PlayerSkillEvent e = new PlayerSkillEvent(event);
        EventUtils.TriggerListener(Driver.EXTENSION, "mcmmo_levelup", e);
    }
}
