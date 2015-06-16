CH-mcMMO
========
Compiling
---------
To compile CH-mcMMO, use Maven:

    mvn clean package install
You can also download the latest jar, already compiled, [here](https://dl.dropboxusercontent.com/u/8521871/hosted/builds/CH-mcMMO/CH-mcMMO-0.0.2.jar).
How to Install
---------
 1. Make sure you are running a recent version of Spigot or Bukkit.  New versions can be built using BuildTools.jar ([instructions from Spigot here](http://www.spigotmc.org/wiki/buildtools/)).
 2. Install CommandHelper and mcMMO into your plugins folder.  CommandHelper can be downloaded from [here](http://builds.enginehub.org/job/commandhelper/last-successful?branch=master).  mcMMO is currently a premium Spigot resource, and costs $10.61 to [download from Spigot](http://www.spigotmc.org/resources/mcmmo.2445/).  If you'd prefer to not pay, you can compile it yourself for free using Maven ([source on GitHub](https://github.com/mcMMO-Dev/mcMMO)).
 3. Run the server once with the CommandHelper.jar and mcMMO.jar in the plugins folder.
 4. Download the latest CH-mcMMO.jar [here](https://dl.dropboxusercontent.com/u/8521871/hosted/builds/CH-mcMMO/CH-mcMMO-0.0.2.jar), or compile it from the [source](https://github.com/gfrung4/CH-mcMMO/) using Maven.
 4. Open the directory **plugins/CommandHelper/extensions** and place the CH-mcMMO.jar you downloaded there.
 5. Restart the server, you're good to go!

Getting Started
---------
To test things out, open your **aliases.msa** file and add in the following alias:

    /skills = msg(mcmmo_get())
Run **/reloadaliases** in game to load the change in, and then run **/skills**.  You should see an array of your skills in chat, like this:

![enter image description here](https://dl.dropboxusercontent.com/u/8521871/hosted/github/chatSample.png)

If you see this, everything was installed correctly and CommandHelper is properly connected with mcMMO.  The complete documentation is below to help you get to writing some awesome scripts!

> At the time of writing, mcMMO has 13 parent skills and 2 child skills.  Some notes about the skills you'll see:
> 
> **Note:** The skills **salvage** and **smelting** are "child skills".  They are not leveled up directly.  The player's **salvage** level is the average of their **fishing** and **repair** levels, and the player's **smelting** level is the average of their **repair** and **mining** levels.  Because these skills do not have their own experience and levels, attempting to use the exp/level functions on them will throw a stack trace.  The extension does not currently handle this gracefully.
> 
> **Note:** The skill **power** listed in mcmmo_get() is simply the total of all other skills, except for **salvage** and **smelting** (because these are "child skills", as mentioned above, counting them in **power** would be essentially double counting other skills).  You cannot set the level of **power**, as it is not actually a skill.
> 
> You can learn more about all the skills in mcMMO in their guide, [here](https://github.com/mcMMO-Dev/mcMMO/wiki/Skills).


Functions
---------
Function Name|Returns|Arguments|Throws|Description|Since|Restricted
--------|---|------|-------|---|---|---
**mcmmo_skills()**|array|||Return an array of possible skills.|3.3.1|No
**mcmmo_get()**|array|[player]|NotFoundException<br> InsufficientArgumentsException|Return an associative array of skills by either the current player or the given player. Will throw an exception if the player has not joined the server since McMMO was installed.|3.3.1|Yes
**mcmmo_get_exp()**|array|[player]|NotFoundException<br> InsufficientArgumentsException|Return an associative array of skill experience by either the current player or the given player. Will throw an exception if the player has not joined the server since McMMO was installed.|3.3.1|Yes
**mcmmo_exp_till_levelup()**|array|[player]|NotFoundException<br> InsufficientArgumentsException|Return an associative array of skill experience needed to achieve the next skill level by either the current player or the given player. Will throw an exception if the player has not joined the server since McMMO was installed.|3.3.1|Yes
**mcmmo_add_level()**|void|player, skillname[, levels]|NotFoundException<br>RangeException|Add levels to a given skill for a given player. If levels isn't given, 1 is used.|3.3.1|Yes
**mcmmo_add_exp()**|void|player, skillname[, exp]|RangeException|Add exp to a given skill for a given player. If exp isn't given, 1 is used.|3.3.1|Yes

Events
---------
Event Name|Description|Prefilters|Event Data|Mutable Fields|Since
--------|---|------|-------|---|---
**mcmmo_levelup**|This event is called when a player levels up a skill.|**player**: Macro|**player**: The player being leveled up<br>**skill**: The skill being leveled up<br>**levelsgained**: the number of levels gained<br>**level**: the new level value||3.3.1
