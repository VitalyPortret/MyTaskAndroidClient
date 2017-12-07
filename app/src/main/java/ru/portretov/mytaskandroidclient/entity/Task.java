package ru.portretov.mytaskandroidclient.entity;

import java.util.Date;

import ru.portretov.mytaskandroidclient.entity.enumirate.Alert;
import ru.portretov.mytaskandroidclient.entity.enumirate.TaskType;

public class Task {

    private String id;

    private String title;

    private String description;

    private Date dueDate;

    private Date publicationDate;

    private String taskStatus;

    private TaskType taskType;

    private Alert alert;

    private String location;

    private byte radius;

    private byte countPeople;

    private double budget;

    private Profile creator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte getRadius() {
        return radius;
    }

    public void setRadius(byte radius) {
        this.radius = radius;
    }

    public byte getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(byte countPeople) {
        this.countPeople = countPeople;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Profile getCreator() {
        return creator;
    }

    public void setCreator(Profile creator) {
        this.creator = creator;
    }
}
