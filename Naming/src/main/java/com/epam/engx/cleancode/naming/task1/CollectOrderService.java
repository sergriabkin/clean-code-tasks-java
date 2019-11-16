package com.epam.engx.cleancode.naming.task1;


import com.epam.engx.cleancode.naming.task1.thirdpartyjar.CollectionService;
import com.epam.engx.cleancode.naming.task1.thirdpartyjar.Message;
import com.epam.engx.cleancode.naming.task1.thirdpartyjar.NotificationManager;
import com.epam.engx.cleancode.naming.task1.thirdpartyjar.Order;

public class CollectOrderService implements IOrderService {

    public static final int CRITICAL_LEVEL = 1;
    public static final int INFO_LEVEL = 4;
    private CollectionService service;
    private NotificationManager manager;

    public void submitOrder(Order order) {
        if (service.isEligibleForCollection(order))
            manager.notifyCustomer(Message.READY_FOR_COLLECT, INFO_LEVEL);
        else
            manager.notifyCustomer(Message.IMPOSSIBLE_TO_COLLECT, CRITICAL_LEVEL);
    }

    public void setService(CollectionService service) {
        this.service = service;
    }

    public void setManager(NotificationManager manager) {
        this.manager = manager;
    }
}
