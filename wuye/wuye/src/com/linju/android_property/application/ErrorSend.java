package com.linju.android_property.application;

import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ErrorReporter;
import org.acra.collector.CrashReportData;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.content.Context;

/**
 * 异常发送报告
 * @author Administrator
 *
 */
public class ErrorSend implements ReportSender{

	private final Context mContext;
	
	public ErrorSend(Context mContext) {
		super();
		this.mContext = mContext;  
		ACRA.getErrorReporter().putCustomData("PLATFORM", "ANDROID");
		ACRA.getErrorReporter().putCustomData("BUILD_ID", android.os.Build.ID);		
		ACRA.getErrorReporter().putCustomData("DEVICE_NAME", android.os.Build.PRODUCT);	
	}
	
	
	@Override
	public void send(CrashReportData arg0) throws ReportSenderException {
//		EmailIntentSender emailSender = new EmailIntentSender(mContext);
//		emailSender.send(arg0);
		System.out.println(arg0.toString());
	}

	
}
