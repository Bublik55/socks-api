import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import controller.SockController;

@SpringBootApplication
@RestController
public class App {
  @Autowired
  SockController sockController;

  public static void main(String[] args) {
  SpringApplication.run(App.class,args);  
  }
}
