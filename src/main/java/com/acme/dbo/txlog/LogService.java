package com.acme.dbo.txlog;

import com.acme.dbo.txlog.message.Message;
import com.acme.dbo.txlog.printer.Printer;

public class LogService {
    private final Printer printer;
    private Message aggregatedMessage;

    public LogService(Printer printer) {
        this.printer = printer;
    }

    public void log(Message message) {
        if (aggregatedMessage != null && aggregatedMessage.shouldAggregate(message)) {
            aggregatedMessage.aggregate(message);
        } else {
            flush();
            aggregatedMessage = message;
        }
    }

    public void flush() {
        if (aggregatedMessage == null) {
            return;
        }
        printer.print(aggregatedMessage.getDecorated());
        aggregatedMessage = null;
    }
}
