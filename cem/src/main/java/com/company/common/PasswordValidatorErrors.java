/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.common;

/**
 *
 * @author sits_lahirupr
 */
public class PasswordValidatorErrors {

    public static final int USER_SUCCESSFULLY_REGISTERED = 0;
    public static final int USER_REGISTRATION_UNSUCCESSFUL = 1;

    public static final int PASSWORD_SUCCESSFULLY_VALIDATED = 10;
    public static final int PASSWORD_VALIDATION_UNSUCCESSFUL = 11;

    public static final int PASSWORD_SUCCESSFULLY_CHANGED = 20;
    public static final int PASSWORD_CHANGE_UNSUCCESSFUL = 21;
    public static final int PASSWORDS_NOT_MATCH = 22;
    public static final int INVALID_CURRENT_PASSWORD = 23;
    public static final int PASSWORD_SUCCESSFULLY_SET = 24;
    public static final int PASSWORD_SUCCESSFULLY_REMOVED = 25;
    public static final int PASSWORD_EXPDATE_SUCCESSFULLY_RESET = 26;
    public static final int PASSWORD_CHANGE_CHECK_FAILED = 29;

    public static int PASSWORD_CANT_REPEAT = 31;
    public static int PASSWORD_EXPIRED = 40;
    public static int ALLOWED_ATTEMPTS_EXCEEDED = 41;
    public static int PASSWORD_LOCKED = 42;

    public static final int USER_SUCCESSFULLY_STOPPED = 50;
    public static final int USER_SUCCESSFULLY_REMOVED = 51;

    public static final int INACTIVE_NEW_USER = 80;
    public static final int INACTIVE_OLD_USER = 81;
    public static final int ALREADY_ACTIVE_USER = 82;
    public static final int DELETED_USER = 83;
    public static final int STOPED_USER = 84;
    public static final int ALREADY_STOPED_USER = 85;
    public static final int ALREADY_AUTHORIZED_USER = 86;
    public static final int USER_SUCCESSFULLY_AUTHORIZED = 87;
    public static final int USER_NOT_AUTHORIZED = 88;
    public static final int USER_SUCCESSFULLY_ACTIVATED = 89;

    public static final int PASSWORD_NOT_SET = 90;
    public static final int PASSWORD_ALREADY_SET = 91;
    public static final int CAN_NOT_SET_PRIMARY_PASSWORD = 92;
    public static final int USER_DOES_NOT_EXIST = 93;
    public static final int USER_ALREADY_EXIST = 94;
    public static final int INVALID_USERID_OR_PASSWORD = 95;
    public static final int INVALID_USERID_FORMAT = 96;
    public static final int INVALID_PASSWORD_FORMAT = 97;
    public static final int DATABASE_ERROR = 98;
    public static final int UNKNOWN_ERROR = 99;
    public static final int PROCESS_FAIL = 110;

    public static String getPasswordValidateDesc(int code) {
        String desc = "Unknown Error.";

        if (code == USER_SUCCESSFULLY_REGISTERED) {
            desc = "User Successful Registered.";
        }
        if (code == USER_REGISTRATION_UNSUCCESSFUL) {
            desc = "User Registration Unsuccessful.";
        }
        if (code == PASSWORD_SUCCESSFULLY_VALIDATED) {
            desc = "Password Successfully Validated.";
        }
        if (code == PASSWORD_VALIDATION_UNSUCCESSFUL) {
            desc = "Password Validation Unsuccessfully.";
        }
        if (code == PASSWORD_SUCCESSFULLY_CHANGED) {
            desc = "Password Successfully Changed.";
        }
        if (code == PASSWORD_CHANGE_UNSUCCESSFUL) {
            desc = "Password Changed Unsuccessfully.";
        }
        if (code == PASSWORDS_NOT_MATCH) {
            desc = "Passwords Not Match.";
        }
        if (code == INVALID_CURRENT_PASSWORD) {
            desc = "Invalid Current Password.";
        }
        if (code == PASSWORD_SUCCESSFULLY_SET) {
            desc = "Passwords Successfully Set.";
        }
        if (code == PASSWORD_SUCCESSFULLY_REMOVED) {
            desc = "Passwords Successfully Removed.";
        }
        if (code == PASSWORD_EXPDATE_SUCCESSFULLY_RESET) {
            desc = "Passwords Exp Date Successfully Reset.";
        }
        if (code == PASSWORD_CHANGE_CHECK_FAILED) {
            desc = "Passwords Change Check Failed.";
        }
        if (code == PASSWORD_CANT_REPEAT) {
            desc = "Passwords Can not Repeat.";
        }
        if (code == PASSWORD_EXPIRED) {
            desc = "Passwords Expired.";
        }
        if (code == ALLOWED_ATTEMPTS_EXCEEDED) {
            desc = "Allow Attempts Exceeds.";
        }
        if (code == PASSWORD_LOCKED) {
            desc = "Passwords Locked.";
        }
        if (code == USER_SUCCESSFULLY_STOPPED) {
            desc = "User Successfully Stoped.";
        }
        if (code == USER_SUCCESSFULLY_REMOVED) {
            desc = "User Successfully Removed.";
        }
        if (code == INACTIVE_NEW_USER) {
            desc = "Inactive New User.";
        }
        if (code == INACTIVE_OLD_USER) {
            desc = "Innactive Old User.";
        }
        if (code == ALREADY_ACTIVE_USER) {
            desc = "Already Active User.";
        }
        if (code == DELETED_USER) {
            desc = "Deleted User.";
        }
        if (code == STOPED_USER) {
            desc = "Stoped User.";
        }
        if (code == ALREADY_STOPED_USER) {
            desc = "Already Stoped User.";
        }
        if (code == ALREADY_AUTHORIZED_USER) {
            desc = "Already Authorized User.";
        }
        if (code == USER_SUCCESSFULLY_AUTHORIZED) {
            desc = "User Successfully Authorized.";
        }
        if (code == USER_NOT_AUTHORIZED) {
            desc = "User Not Authorized.";
        }
        if (code == USER_SUCCESSFULLY_ACTIVATED) {
            desc = "User Successfully Actived.";
        }
        if (code == PASSWORD_NOT_SET) {
            desc = "Passwords Not Set.";
        }
        if (code == PASSWORD_ALREADY_SET) {
            desc = "Passwords Already Set.";
        }
        if (code == CAN_NOT_SET_PRIMARY_PASSWORD) {
            desc = "Can not Set Primary Password.";
        }
        if (code == USER_DOES_NOT_EXIST) {
            desc = "User Does not Exist.";
        }
        if (code == USER_ALREADY_EXIST) {
            desc = "User Already Exist.";
        }
        if (code == INVALID_USERID_OR_PASSWORD) {
            desc = "Invalid UserId Or Password.";
        }
        if (code == INVALID_USERID_FORMAT) {
            desc = "Invalid UserId Format.";
        }
        if (code == INVALID_PASSWORD_FORMAT) {
            desc = "Invalid Password Format.";
        }
        if (code == DATABASE_ERROR) {
            desc = "Database Error.";
        }
        if (code == UNKNOWN_ERROR) {
            desc = "Unknown Error.";
        }
        if (code == PROCESS_FAIL) {
            desc = "Process fail.";
        }

        return desc;
    }
}
