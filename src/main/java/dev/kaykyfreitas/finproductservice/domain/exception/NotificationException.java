package dev.kaykyfreitas.finproductservice.domain.exception;

import dev.kaykyfreitas.finproductservice.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification aNotification) {
        super(aMessage, aNotification.getErrors());
    }

}
