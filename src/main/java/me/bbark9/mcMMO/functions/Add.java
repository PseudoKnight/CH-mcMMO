package me.bbark9.mcMMO.functions;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;

/**
 *
 * @author import
 */
public class Add {
    public String docs() {
        return "MCMMO functionality.";
    }

    @api
    public static class mcmmo_add_level extends AbstractFunction {
        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
        
        public Exceptions.ExceptionType[] thrown() {
            return new Exceptions.ExceptionType[]{};
        }

        public Construct exec(Target t, Environment environment,
                Construct... args) throws ConfigRuntimeException {
            Static.checkPlugin("mcMMO", t);

            BukkitMCPlayer player = (BukkitMCPlayer) Static.GetPlayer(args[0], t);
            SkillType skill;
            int amount = 1;
            
            try {
                skill = SkillType.valueOf(args[1].val().toUpperCase());
            } catch (Exception e) {
                throw new ConfigRuntimeException("Unknown McMMO skilltype for mcmmo_add_level, " + args[1].val(), Exceptions.ExceptionType.NotFoundException, t);
            }
            
            if (args.length == 3) {
                try {
                    amount = Integer.parseInt(args[2].val());
                } catch (Exception e) {
                    throw new ConfigRuntimeException("Bad amount for mcmmo_add_level, " + args[2].val(), Exceptions.ExceptionType.RangeException, t);
                }
            }
            
            ExperienceAPI.addLevel(player._Player(), skill.name(), amount);

            return CVoid.VOID;
        }

        public String getName() {
            return "mcmmo_add_level"; //Name of function
        }

        public Integer[] numArgs() {
            return new Integer[]{2, 3}; //Arguments in function
        }

        public String docs() {
            return "array {player, skillname[, levels]} Add levels to a given skill"
                    + " for a given player. If levels isn't given, 1 is used.";
        }
    }
    
    @api
    public static class mcmmo_add_exp extends AbstractFunction {
        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
        
        public Exceptions.ExceptionType[] thrown() {
            return new Exceptions.ExceptionType[]{};
        }

        public Construct exec(Target t, Environment environment,
                Construct... args) throws ConfigRuntimeException {
            Static.checkPlugin("mcMMO", t);

            BukkitMCPlayer player = (BukkitMCPlayer) Static.GetPlayer(args[0], t);
            SkillType skill;
            int amount = 1;
            
            try {
                skill = SkillType.valueOf(args[1].val().toUpperCase());
            } catch (Exception e) {
                throw new ConfigRuntimeException("Unknown McMMO skilltype for mcmmo_add_exp, " + args[1].val(), Exceptions.ExceptionType.RangeException, t);
            }
            
            if (args.length == 3) {
                try {
                    amount = Integer.parseInt(args[2].val());
                } catch (Exception e) {
                    throw new ConfigRuntimeException("Bad amount for mcmmo_add_exp, " + args[2].val(), Exceptions.ExceptionType.RangeException, t);
                }
            }
            
            ExperienceAPI.addXP(player._Player(), skill.name(), amount);

            return CVoid.VOID;
        }

        public String getName() {
            return "mcmmo_add_exp"; //Name of function
        }

        public Integer[] numArgs() {
            return new Integer[]{2, 3}; //Arguments in function
        }

        public String docs() {
            return "array {player, skillname[, exp]} Add exp to a given skill"
                    + " for a given player. If exp isn't given, 1 is used.";
        }
    }
}
