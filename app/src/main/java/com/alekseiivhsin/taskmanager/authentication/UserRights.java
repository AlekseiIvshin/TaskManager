package com.alekseiivhsin.taskmanager.authentication;

/**
 * Created on 07/12/2015.
 */
public interface UserRights {

    String USER_RIGHTS = "taskmanager.authentication.USER_RIGHTS";

    int NONE = 0x00;
    int CAN_VIEW_TASK = 0x01;
    int CAN_UPDATE_TASK = 0x02;
    int CAN_CREATE_TASK = 0x04;
    int CAN_CLOSE_TASK = 0x08;
}
