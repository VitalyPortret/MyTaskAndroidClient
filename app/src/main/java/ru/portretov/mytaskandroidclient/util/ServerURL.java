package ru.portretov.mytaskandroidclient.util;

/**
 * Created by adminvp on 11/29/17.
 */

class ServerURL {
    private static final String URL_SERVER = "http://10.0.2.2:8080";

    static final String URL_POST_TASK = URL_SERVER + "/api/tasks/create";

    static final String URL_ALL_TASKS = URL_SERVER + "/api/tasks/";
}
