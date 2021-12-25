package io.github.bloodnighttw;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingTest extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(PingTest.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().contains("!ping")){
            event.getChannel().sendMessage("pong!").queue();
            logger.debug("Pong!");
        }
    }

}
