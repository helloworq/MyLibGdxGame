package com.mygdx.game.net.websocket;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServerHandler {
    private static final Map<String, List<WebSocket>> STATUS = new HashMap<>();
    public static final String MAIN_ROOM = "main";

    public static void addUser2MainRoom(WebSocket webSocket) {
        STATUS.getOrDefault(MAIN_ROOM, new ArrayList<>()).add(webSocket);
    }
}
