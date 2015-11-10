package com.base.test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.base.utils.Generator;
import com.base.utils.StrUtils;

public class Test {

	public static void main(String[] args) throws IOException {
		String hash = "908878dddbc37a2c3016cdd4e7c4aa2f";

		Generator g = new Generator();
		for (int i = 0; i < 10; i++) {
			try {
				ImageIO.write(g.create(StrUtils.GUID_32(), 64), "png", new File("C:/test"+i+".png"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
