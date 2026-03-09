package se.iths.sofia.websocketserver.websocket;


import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// URL som klienter ansluter till: ws://localhost:8080/websocket-server/ws/chat
@ServerEndpoint("/ws/chat")
public class ChatEndpoint {
    // Trådsäker lista över alla aktiva sessioner (anslutna klienter)
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session); // Ny klient anslöt – lägg till i listan

    }

    @OnMessage
    public void onMessage(String message, Session session) {

        for (Session s : sessions) {
            if (s.isOpen()) { // Kontrollera att klienten fortfarande
                try {
                    s.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
