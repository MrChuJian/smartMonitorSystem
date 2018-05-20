package com.fjy.smartMonitorSystem.Timer;

import java.util.TimerTask;

import com.fjy.smartMonitorSystem.api.FileController;
import com.fjy.springboot.SampleTomcatApplication;

public class DeleteDirTimer extends TimerTask{

	@Override
	public void run() {
		FileController fileController = SampleTomcatApplication.applicationContext.getBean(FileController.class);
		fileController.deleteDir();
	}

}
