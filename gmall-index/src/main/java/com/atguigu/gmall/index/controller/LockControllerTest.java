package com.atguigu.gmall.index.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.service.impl.LockTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author erdong
 * @create 2019-11-12 20:40
 */
@RestController
@RequestMapping("index")
public class LockControllerTest {

    @Autowired
    private LockTest lockTest;

    @GetMapping("testLock")
    public Resp<Object> testLock(HttpServletRequest request){
        System.out.println(request.getLocalPort());
        String msg = this.lockTest.testLock();
        return Resp.ok(msg);
    }

    @GetMapping("read")
    public Resp<Object> testRead(){
        String msg = this.lockTest.testRead();
        return Resp.ok(msg);
    }

    @GetMapping("write")
    public Resp<Object> testWrite(){
        String msg = this.lockTest.testWrite();
        return Resp.ok(msg);
    }

    @GetMapping("latch")
    public Resp<Object> latch() throws InterruptedException {
        String msg = this.lockTest.latch();
        return Resp.ok(msg);
    }

    @GetMapping("out")
    public Resp<Object> out(){
        String msg = this.lockTest.out();
        return Resp.ok(msg);
    }

}
