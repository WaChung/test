package com.example.demo.log;

import java.util.Date;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

public class MySampleLayout extends LayoutBase<ILoggingEvent> {

	  String prefix = null;
	  boolean printThreadName = true;

	  public void setPrefix(String prefix) {
	    this.prefix = prefix;
	  }

	  public void setPrintThreadName(boolean printThreadName) {
	    this.printThreadName = printThreadName;
	  }

	  public String doLayout(ILoggingEvent event) {
	    StringBuffer sbuf = new StringBuffer(128);
	    if (prefix != null) {
	      sbuf.append(prefix + ": ");
	    }
	    Date d = new Date(event.getTimeStamp());
	    sbuf.append("[" + d + "]");
	    sbuf.append(" ");
	    sbuf.append("[" + event.getLevel() + "]");
	    sbuf.append(" ");
	    sbuf.append("[" + event.getLoggerName() + "]");
	    sbuf.append(" ");
	    //Trace ID
	    sbuf.append("[" + "bbbbb8a557" + "]");
	    sbuf.append(" ");
	    //Context ID
	    sbuf.append("[" + "AA002" + "]");
	    sbuf.append(" ");
	    //caller_context_id
	    sbuf.append("[" + "" + "]");
	    sbuf.append(" ");
	    //app_id
	    sbuf.append("[" + "HKG2020021200199999" + "]");
	    sbuf.append(" ");
	    //biz_func
	    sbuf.append("[" + "FundSwitch" + "]");
	    sbuf.append(" ");
	    //message_type
	    sbuf.append("[" + "Trace" + "]");
	    sbuf.append(" ");
	    //version
	    sbuf.append("[" + "1.1.1.1" + "]");
	    sbuf.append(" ");
	    //thread_name
	    sbuf.append("[" + event.getThreadName() + "]");
	    sbuf.append(" ");
	    //biz_keys
	    sbuf.append("[" + "{\"State\": \"Call\", \"policyID\": \"B623182877\"}" + "]");
	    sbuf.append(" ");
	    //message

	    sbuf.append(CoreConstants.LINE_SEPARATOR);
	    sbuf.append("[" + event.getFormattedMessage()+ "]");
	    sbuf.append(CoreConstants.LINE_SEPARATOR);
	    return sbuf.toString();
	  }
	}