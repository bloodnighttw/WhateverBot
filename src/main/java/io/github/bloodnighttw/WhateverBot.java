package io.github.bloodnighttw;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.LoggerFactory;


import javax.security.auth.login.LoginException;

public class WhateverBot {


    public static void main(String[] args) throws LoginException {

        for(String it:args){
            switch (it){
                case "-d":
                case "-debug":
                    Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
                    root.setLevel(Level.DEBUG);
                    System.out.println("Enable Debug Mode");
                    break;
            }
        }

        JDA bot = JDABuilder.createDefault(System.getenv("TOKEN")).build();
        bot.addEventListener(new PingTest());

    }

}
