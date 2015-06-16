package me.bbark9.mcMMO.functions;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.exceptions.InvalidPlayerException;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;

public class Get {

    public String docs() {
        return "MCMMO functionality.";
    }

    @api
    public static class mcmmo_get extends AbstractFunction {
        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
        
        public ExceptionType[] thrown() {
            return new ExceptionType[]{};
        }

        public Construct exec(Target t, Environment environment,
                Construct... args) throws ConfigRuntimeException {
            // Make sure mcmmo is installed.
            Static.checkPlugin("mcMMO", t);
            
            String name;
            BukkitMCPlayer player = null;

            // Get user if online.
            try {
                if (args.length == 1) {
                    player = (BukkitMCPlayer) Static.GetPlayer(args[0], t);
                } else {
                    player = (BukkitMCPlayer) environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
                }
                
                name = player.getName();
            } catch (ConfigRuntimeException e) {
                if (args.length == 0) {
                    throw new ConfigRuntimeException("You need to specify a player.", ExceptionType.InsufficientArgumentsException, t);
                }
                // Player is offline, we'll use the given string name later.
                name = args[0].val();
            }

            CArray skills = new CArray(t);
            int power;
            
            if (player != null) {
                power = ExperienceAPI.getPowerLevel(player._Player());
            } else {
                try {
                    power = ExperienceAPI.getPowerLevelOffline(name);
                } catch (InvalidPlayerException e) {
                    throw new ConfigRuntimeException("Unknown McMMO player, " + name, ExceptionType.NotFoundException, t);
                }
            }
            
            // Accumulate skills into the skill map.
            for (SkillType skillname : SkillType.values()) {
                int level;

                if (player != null) {
                    level = ExperienceAPI.getLevel(player._Player(), skillname.name());
                } else {
                    try {
                        level = ExperienceAPI.getLevelOffline(name, skillname.toString());
                    } catch (InvalidPlayerException e) {
                        throw new ConfigRuntimeException("Unknown McMMO player, " + name, ExceptionType.NotFoundException, t);
                    }
                }

                CString skill = new CString(skillname.name(), t);
                CInt value = new CInt(level, t);

                skills.set(skill, value, t);
            }
            
            skills.set("POWER", new CInt(power, t), t);

            return skills;
        }

        public String getName() {
            return "mcmmo_get"; //Name of function
        }

        public Integer[] numArgs() {
            return new Integer[]{0, 1}; //Arguments in function
        }

        public String docs() {
            return "array {[player]} Return an associative array of skills by either"
                    + " the current player or the given player. Will throw an exception"
                    + " if the player has not joined the server since McMMO was installed.";
        }
    }
    
    @api
    public static class mcmmo_get_exp extends AbstractFunction {
        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
        
        public ExceptionType[] thrown() {
            return new ExceptionType[]{};
        }

        public Construct exec(Target t, Environment environment,
                Construct... args) throws ConfigRuntimeException {
            // Make sure mcmmo is installed.
            Static.checkPlugin("mcMMO", t);

            BukkitMCPlayer player = null;
            String name;

            // Get user if online.
            try {
                if (args.length == 1) {
                    player = (BukkitMCPlayer) Static.GetPlayer(args[0], t);
                } else {
                    player = (BukkitMCPlayer) environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
                }
                
                name = player.getName();
            } catch (ConfigRuntimeException e) {
                if (args.length == 0) {
                    throw new ConfigRuntimeException("You need to specify a player.", ExceptionType.InsufficientArgumentsException, t);
                }
                
                // Player is offline, we'll use the given string name later.
                name = args[0].val();
            }

            CArray skills = new CArray(t);

            // Accumulate skills into the skill map.
            for (SkillType skillname : SkillType.values()) {
                if (skillname.isChildSkill()) {
                    // Child skills have no exp to report.
                    continue;
                }
                
                int level;
                
                System.out.println(skillname.name());
                
                if (player != null) {
                    level = ExperienceAPI.getXP(player._Player(), skillname.name());
                } else {
                    try {
                        level = ExperienceAPI.getOfflineXP(name, skillname.toString());
                    } catch (InvalidPlayerException e) {
                        throw new ConfigRuntimeException("Unknown McMMO player, " + name, ExceptionType.NotFoundException, t);
                    }
                }

                CString skill = new CString(skillname.name(), t);
                CInt value = new CInt(level, t);

                skills.set(skill, value, t);
            }

            return skills;
        }

        public String getName() {
            return "mcmmo_get_exp"; //Name of function
        }

        public Integer[] numArgs() {
            return new Integer[]{0, 1}; //Arguments in function
        }

        public String docs() {
            return "array {[player]} Return an associative array of skill experience by either"
                    + " the current player or the given player. Will throw an exception"
                    + " if the player has not joined the server since McMMO was installed.";
        }
    }
    
    @api
    public static class mcmmo_exp_till_levelup extends AbstractFunction {
        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
        
        public ExceptionType[] thrown() {
            return new ExceptionType[]{};
        }

        public Construct exec(Target t, Environment environment,
                Construct... args) throws ConfigRuntimeException {
            // Make sure mcmmo is installed.
            Static.checkPlugin("mcMMO", t);

            BukkitMCPlayer player = null;
            String name;

            // Get user if online.
            try {
                if (args.length == 1) {
                    player = (BukkitMCPlayer) Static.GetPlayer(args[0], t);
                } else {
                    player = (BukkitMCPlayer) environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
                }
                
                name = player.getName();
            } catch (ConfigRuntimeException e) {
                if (args.length == 0) {
                    throw new ConfigRuntimeException("You need to specify a player.", ExceptionType.InsufficientArgumentsException, t);
                }
                
                // Player is offline, we'll use the given string name later.
                name = args[0].val();
            }

            CArray skills = new CArray(t);

            // Accumulate skills into the skill map.
            for (SkillType skillname : SkillType.values()) {
                if (skillname.isChildSkill()) {
                    // Child skills have no exp to report.
                    continue;
                }
                
                int level;

                if (player != null) {
                    level = ExperienceAPI.getXPToNextLevel(player._Player(), skillname.name());
                } else {
                    try {
                        level = ExperienceAPI.getOfflineXPToNextLevel(name, skillname.toString());
                    } catch (InvalidPlayerException e) {
                        throw new ConfigRuntimeException("Unknown McMMO player, " + name, ExceptionType.NotFoundException, t);
                    }
                }

                CString skill = new CString(skillname.name(), t);
                CInt value = new CInt(level, t);

                skills.set(skill, value, t);
            }

            return skills;
        }

        public String getName() {
            return "mcmmo_exp_till_levelup"; //Name of function
        }

        public Integer[] numArgs() {
            return new Integer[]{0, 1}; //Arguments in function
        }

        public String docs() {
            return "array {[player]} Return an associative array of skill experience needed to"
                    + " achieve the next skill level by either"
                    + " the current player or the given player. Will throw an exception"
                    + " if the player has not joined the server since McMMO was installed.";
        }
    }
    
    @api
    public static class mcmmo_skills extends AbstractFunction {
        public boolean isRestricted() {
            return false;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
        
        public ExceptionType[] thrown() {
            return new ExceptionType[]{};
        }

        public Construct exec(Target t, Environment environment,
                Construct... args) throws ConfigRuntimeException {
            // Make sure mcmmo is installed.
            Static.checkPlugin("mcMMO", t);

            CArray skills = new CArray(t);
            
            for (SkillType skillname : SkillType.values()) {
                CString skill = new CString(skillname.name(), t);
                skills.push(skill);
            }

            return skills;
        }

        public String getName() {
            return "mcmmo_skills"; //Name of function
        }

        public Integer[] numArgs() {
            return new Integer[]{0}; //Arguments in function
        }

        public String docs() {
            return "array {} Return an array of possible skills.";
        }
    }
}
