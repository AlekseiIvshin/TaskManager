package com.alekseiivhsin.taskmanager.models;

/**
 * Created on 15/12/2015.
 */
public class PoolMember {

    public final int id;
    public final String firstName;
    public final String lastName;

    public PoolMember(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
