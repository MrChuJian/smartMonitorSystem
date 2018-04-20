package smartMonitorSystem.test;

import java.awt.image.BufferedImage;

import com.fjy.smartMonitorSystem.util.CaptchaUtil;

public class Test {

	public static void main(String[] args) {
		CaptchaUtil captcha = new CaptchaUtil();
		BufferedImage bi = captcha.createImage();
		System.out.println(bi.toString());
	}
}
