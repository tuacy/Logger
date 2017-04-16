package com.tuacy.logger.printer;


import com.tuacy.logger.bean.LoggerInfo;

public interface IPrinter {

	/**
	 * log信息
	 *
	 * @param info log对象
	 */
	void print(LoggerInfo info);

}
