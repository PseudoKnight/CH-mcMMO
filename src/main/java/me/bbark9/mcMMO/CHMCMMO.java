package me.bbark9.mcMMO;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.extensions.AbstractExtension;

public class CHMCMMO extends AbstractExtension {

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
