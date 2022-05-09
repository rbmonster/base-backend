package com.sw.shardingjdbc.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * notification_event
 * @author 
 */
@Data
public class NotificationEvent implements Serializable {
    private Integer id;

    private String eventId;

    private String globalId;

    private Integer version;

    private String status;

    private String eventType;

    private String eventTopic;

    private String tags;

    private String notificationBody;

    private Date createdTime;

    private Date modifiedTime;

    private static final long serialVersionUID = 1L;
}