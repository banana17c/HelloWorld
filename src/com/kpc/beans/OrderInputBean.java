package com.kpc.beans;

import java.util.List;

import com.kpc.dao.FoodDTO;

public class OrderInputBean {

    // ������Ա_no
    private Integer _no;
    
    // ʳ����Ϣ
    private List<FoodDTO> _foodInfo;

    public Integer get_no() {
        return _no;
    }

    public void set_no(Integer _no) {
        this._no = _no;
    }

    public List<FoodDTO> get_foodInfo() {
        return _foodInfo;
    }

    public void set_foodInfo(List<FoodDTO> _foodInfo) {
        this._foodInfo = _foodInfo;
    }
}
