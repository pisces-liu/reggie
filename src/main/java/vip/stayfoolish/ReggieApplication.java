package vip.stayfoolish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan // 扫描 servlet filter
@EnableTransactionManagement
public class ReggieApplication {

	public static void main(String[] args) {
		log.info("项目启动成功！");
		SpringApplication.run(ReggieApplication.class, args);
	}

}
