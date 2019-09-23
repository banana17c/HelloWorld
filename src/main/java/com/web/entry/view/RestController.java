package com.web.entry.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <Description> personalWeb <br>
 *
 * @author chen.guangwen <br>
 * @CreateDate 2019/5/13 <br>
 */
@MapRestController
public class RestController {

    @RequestMapping("/sky")
    public String sky() {
        return "sky";
    }

    @RequestMapping("/days")
    public Page<TestVo> days() {

        Page page = new Page();
        List<TestVo> list = new ArrayList<>();
        TestVo vo = new TestVo();
        vo.setDay(LocalDateTime.now());
        vo.setDays(LocalDateTime.now());
        list.add(vo);
        page.setList(list);
        return page;
    }
}
