package com.example.modelexam;

import java.util.Date;

public class Task {

    long _id, _employee_id;
    String _title, _description;
    Date _start, _end;
    boolean _done;

    public Task(long _id, long _employee_id, String _title, String _description, Date _start, Date _end, boolean _done) {
        this._id = _id;
        this._employee_id = _employee_id;
        this._title = _title;
        this._description = _description;
        this._start = _start;
        this._end = _end;
        this._done = _done;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long get_employee_id() {
        return _employee_id;
    }

    public void set_employee_id(long _employee_id) {
        this._employee_id = _employee_id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public Date get_start() {
        return _start;
    }

    public void set_start(Date _start) {
        this._start = _start;
    }

    public Date get_end() {
        return _end;
    }

    public void set_end(Date _end) {
        this._end = _end;
    }

    public boolean is_done() {
        return _done;
    }

    public void set_done(boolean _done) {
        this._done = _done;
    }
}
