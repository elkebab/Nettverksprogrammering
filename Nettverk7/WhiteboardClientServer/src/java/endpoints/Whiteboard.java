/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author eidheim
 */
@ServerEndpoint("/whiteboardendpoint")
public class Whiteboard {

  private static final Set<Session> sessions = new HashSet<>();

  @OnOpen
  public void onOpen(Session session) throws IOException {
    synchronized (sessions) {
      sessions.add(session);
    }
  }

  @OnClose
  public void onClose(Session session) {
    synchronized (sessions) {
      sessions.remove(session);
    }
  }

  @OnMessage
  public void onMessage(String message, Session session) throws IOException {
    JSONObject jsonObject = (JSONObject) JSONValue.parse(message);
    jsonObject.put("painter", session.getId());
    
    synchronized (sessions) {
      for (Session aSession : sessions) {
        if (aSession != session) {
          aSession.getBasicRemote().sendText(jsonObject.toString());
        }
      }
    }
  }
}
