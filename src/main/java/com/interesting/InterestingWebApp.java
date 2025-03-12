/**
 *
 */
package com.interesting;

import com.interesting.common.util.oConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @version 创建时间：2017年11月12日 上午22:57:51 类说明
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@Slf4j
public class InterestingWebApp {

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(InterestingWebApp.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = oConvertUtils.getString(env.getProperty("server.servlet.context-path"));
		log.info("\n----------------------------------------------------------\n\t" +
				"Application Jeecg-Boot is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + path + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
				"----------------------------------------------------------");
	}

}
