package com.skcodestack.stack.delegates.web.event;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.HashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/17
 * Version  1.0
 * Description:
 */

public class EventManager {

    private final static HashMap<String, Event> EVENTS = new HashMap<>();

    private EventManager() {
    }

    private static final class Holder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        return Holder.INSTANCE;
    }


    public EventManager addEvent(@NotNull String name,@NotNull Event event){
        EVENTS.put(name,event);
        return this;
    }

    public Event createEvent(@NotNull String action){
        final Event event = EVENTS.get(action);
        if(event == null){
           return new UndefineEvent();
        }
        return event;
    }
}
