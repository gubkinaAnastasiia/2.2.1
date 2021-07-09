package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru",
              new Car("Subaru", 10)));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru",
              new Car("BMW", 11)));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru",
              new Car("Toyota", 12)));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru",
              new Car("Forester", 13)));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         if (user.getCar() != null) {
            System.out.println("Car = " + user.getCar());
         }
      }

      System.out.println(userService.getUserByModelAndSeries("Subaru", 10));
      System.out.println(userService.getUserByModelAndSeries("BMW", 11));
      System.out.println(userService.getUserByModelAndSeries("Toyota", 12));
      System.out.println(userService.getUserByModelAndSeries("Forester", 13));
      System.out.println(userService.getUserByModelAndSeries("Foresteri", 134));

      context.close();
   }
}
