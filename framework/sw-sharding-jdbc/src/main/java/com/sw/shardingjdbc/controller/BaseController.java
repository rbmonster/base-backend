package com.sw.shardingjdbc.controller;


import com.sw.shardingjdbc.dao.NotificationEventDao;
import com.sw.shardingjdbc.model.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BaseController {

    @Autowired
    private NotificationEventDao notificationEventDao;

    @GetMapping
    public List<NotificationEvent> getBatch() {
        return notificationEventDao.findUsers();
    }
}
