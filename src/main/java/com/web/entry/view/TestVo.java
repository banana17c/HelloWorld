package com.web.entry.view;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <Description> personalWeb <br>
 *
 * @author chen.guangwen <br>
 * @CreateDate 2019/5/22 <br>
 */
public class TestVo extends TestBo{

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime day;

    public LocalDateTime getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "TestVo{" +
                "day=" + day +
                '}';
    }
}
