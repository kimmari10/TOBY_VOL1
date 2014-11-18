package springbook.user.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("/mysql.xml")
public class TestApplicationContext {

}
