package com.demo.storage;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

public enum InMemoryMessageStorage {

    INSTANCE;

    private final Vector<JSONObject> messages = new Vector<>();

    public void addMessage(final JSONObject message) {
        messages.add(message);
    }

    public String getAllMessages() {
        final JSONObject jsonResponse = new JSONObject();
        synchronized (messages) {
            final JSONArray jsonMessages = new JSONArray();
            messages.forEach(
                jsonMessage -> jsonMessages.put(jsonMessage)
            );
            jsonResponse.put("messages", jsonMessages);
            messages.clear();
        }
        return jsonResponse.toString();
    }
}
