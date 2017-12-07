package ru.portretov.mytaskandroidclient.util;

/**
 * Created by adminvp on 11/29/17.
 */

public class ServerURL {
    private static final String URL_SERVER = "http://10.0.2.2:8080";

    public static final String URL_POST_TASK = URL_SERVER + "/api/tasks/create";
    public static final String URL_ALL_TASKS = URL_SERVER + "/api/tasks/all";
    public static final String URL_MY_TASKS = URL_SERVER + "/api/tasks/my";
    public static final String URL_TASK_BY_ID = URL_SERVER + "/api/tasks/";
    public static final String URL_OPEN_TASKS = URL_SERVER + "/api/tasks/open";
    public static final String URL_ONLINE_TASKS = URL_SERVER + "/api/tasks/online";
    public static final String URL_EXECUTOR_TASKS = URL_SERVER + "/api/tasks/executor/";
}
