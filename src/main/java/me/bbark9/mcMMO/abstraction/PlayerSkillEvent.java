package me.bbark9.mcMMO.abstraction;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.abstraction.events.MCPlayerEvent;

/**
 *
 * @author import
 */
public class PlayerSkillEvent implements MCPlayerEvent {

    McMMOPlayerLevelUpEvent event;

    public PlayerSkillEvent(McMMOPlayerLevelUpEvent event) {
        this.event = event;
    }

    public Object _GetObject() {
        return event;
    }

    public SkillType getSkill() {
        return event.getSkill();
    }

    public int getSkillLevel() {
        return event.getSkillLevel();
    }

    public int getLevelsGained() {
        return event.getLevelsGained();
    }

    public MCPlayer getPlayer() {
        return new BukkitMCPlayer(event.getPlayer());
    }
}
