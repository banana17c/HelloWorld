package com.kpc.dao;

public class FoodDTO {

    /* ʳ���� */
    private String _foodId;
    
    /* ʳ������ */
    private String _foodNumber;
    
    /* ʳ������ */
    private String _foodName;
    
    /* ���״̬ */
    private String _active;
    
    /* �����߱�� */
    private String _peo;
    
    /* ����ʱ�� */
    private String _time;

    public String get_foodId() {
        return _foodId;
    }

    public void set_foodId(String _foodId) {
        this._foodId = _foodId;
    }

    public String get_foodNumber() {
        return _foodNumber;
    }

    public void set_foodNumber(String _foodNumber) {
        this._foodNumber = _foodNumber;
    }

    public String get_foodName() {
        return _foodName;
    }

    public void set_foodName(String _foodName) {
        this._foodName = _foodName;
    }

    public String get_active() {
        return _active;
    }

    public void set_active(String _active) {
        this._active = _active;
    }

    public String get_peo() {
        return _peo;
    }

    public void set_peo(String _peo) {
        this._peo = _peo;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }
}
