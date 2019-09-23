package com.web.entry.view;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <Description> personalWeb <br>
 *
 * @author chen.guangwen <br>
 * @CreateDate 2019/5/22 <br>
 */
public class TestBo {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime days;

    public LocalDateTime getDays() {
        return days;
    }

    public void setDays(LocalDateTime days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "TestBo{" +
                "days=" + days +
                '}';
    }
}
