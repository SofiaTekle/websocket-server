package se.iths.sofia.websocketserver.websocket;


import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

// URL som klienter ansluter till: ws://localhost:8080/websocket-server/ws/chat
@ServerEndpoint("/ws/chat")
public class ChatEndpoint {
    // Trådsäker lista över alla aktiva sessioner (anslutna klienter)
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    private static final Logger logger = Logger.getLogger(ChatEndpoint.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        logger.info("Klient anslöt: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        for (Session s : sessions) {
            if (s.isOpen()) { // Kontrollera att klienten fortfarande är ansluten
                try {
                    s.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Fel vid broadcast", e);
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        logger.info("Klient frånkopplad: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.log(Level.SEVERE, "WebSocket-fel för session " + session.getId(), error);
    }

}
