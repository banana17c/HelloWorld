package com.kpc.beans;

import java.util.List;

import com.kpc.dao.FoodDTO;

public class SelectOrderBean {
    
    // �����߱��
    private String peo;

    // ʳ����Ϣ
    private List<FoodDTO> _foodInfo;

    public String getPeo() {
        return peo;
    }

    public void setPeo(String peo) {
        this.peo = peo;
    }

    public List<FoodDTO> get_foodInfo() {
        return _foodInfo;
    }

    public void set_foodInfo(List<FoodDTO> _foodInfo) {
        this._foodInfo = _foodInfo;
    }
}
