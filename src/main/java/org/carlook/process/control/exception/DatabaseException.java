package org.carlook.process.control.exception;

public class DatabaseException extends  Exception{


    public DatabaseException(String reason){
        this.reason = reason;
    }


    public String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }





}
