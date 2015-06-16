package me.bbark9.mcMMO.events;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import java.util.HashMap;
import java.util.Map;
import me.bbark9.mcMMO.abstraction.PlayerSkillEvent;

/**
 *
 * @author import
 */
public class LevelUp {

    @api
    public static class mcmmo_levelup extends AbstractEvent {

        public String getName() {
            return "mcmmo_levelup";
        }

        public String docs() {
            return "{player: <macro> | tagged: <macro>}"
                    + " This event is called when a player levels up a skill."
                    + " {player: The player being leveled up | skill: The skill being leveled up"
                    + " | level: the new level value }"
                    + " {}"
                    + " {player | level | skill | levelsgained}";
        }

        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            return true;
        }

        public BindableEvent convert(CArray manualObject, Target t) {
            return null;
        }

        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if (e instanceof PlayerSkillEvent) {
                PlayerSkillEvent event = (PlayerSkillEvent) e;
                Map<String, Construct> ret = new HashMap<String, Construct>();

                ret.put("player", new CString(event.getPlayer().getName(), Target.UNKNOWN));
                ret.put("skill", new CString(event.getSkill().name(), Target.UNKNOWN));
                ret.put("level", new CInt(event.getSkillLevel(), Target.UNKNOWN));
                ret.put("levelsgained", new CInt(event.getLevelsGained(), Target.UNKNOWN));

                return ret;
            } else {
                throw new EventException(null);
            }
        }

        public Driver driver() {
            return Driver.EXTENSION;
        }

        public boolean modifyEvent(String key, Construct value, BindableEvent event) {
            return false;
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
    }
}
