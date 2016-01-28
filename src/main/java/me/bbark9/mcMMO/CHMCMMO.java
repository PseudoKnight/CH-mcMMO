package me.bbark9.mcMMO;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.LifeCycle;
import com.laytonsmith.commandhelper.CommandHelperPlugin;

public class CHMCMMO extends LifeCycle {

    static MMOListener listener;

    public Version getVersion() {
        return new SimpleVersion(0,1,0);
    }

    @Override
    public void onStartup() {
        CommandHelperPlugin chp = CommandHelperPlugin.self;
        listener = new MMOListener(chp);
    }

    @Override
    public void onShutdown() {
        listener.unregister();
    }
}
