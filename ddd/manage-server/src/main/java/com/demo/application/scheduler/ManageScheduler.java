package com.demo.application.scheduler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * 定时任务入口
 */
@RestController("/scheduler")
public class ManageScheduler {

    @GetMapping("/close")
    public void closeManage() {
        CompletableFuture.runAsync(() -> System.out.println("start close job"));
    }
}
