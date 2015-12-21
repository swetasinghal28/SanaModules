package org.sana.android;


public class Patient_notification {
    private String _notification;
    private int _flag;
    public Patient_notification() {
    }

    public Patient_notification(String notification) {
        this._notification = notification;
    }
    public Patient_notification(int flag, String notification)
    {
        _notification = notification;
        _flag = flag;
    }
    public void set_flag(int _flag) {
        this._flag = _flag;
    }

    public int get_flag() {

        return _flag;
    }



    public String get_notification() {
        return _notification;
    }





    public void set_notification(String _notification) {
        this._notification = _notification;
    }


}
