package com.acme.dbo.txlog;

import com.acme.dbo.txlog.message.IntMessage;
import com.acme.dbo.txlog.message.StringMessage;
import com.acme.dbo.txlog.printer.ConsolePrinter;

public class Facade {
    private static final LogService service = new LogService(new ConsolePrinter());

    public static void log(int message) {
        service.log(new IntMessage(message));
    }

    public static void log(String message) {
        service.log(new StringMessage(message));
    }

    public static void flush() {
        service.flush();
    }
}
