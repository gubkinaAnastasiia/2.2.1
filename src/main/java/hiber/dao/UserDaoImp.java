package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByModelAndSeries(String model, int series) {
      User user = null;
      try {
         Session session = sessionFactory.getCurrentSession();
         String HQL = "SELECT user FROM User user WHERE user.car.model = :model and user.car.series = :series";
         user = session.createQuery(HQL, User.class)
                 .setParameter("model", model)
                 .setParameter("series", series)
                 .uniqueResult();
         if (user == null) {
            System.out.println("Не существует user связанного с car с такими параметрами.");
         }
      } catch (NonUniqueResultException e) {
         System.err.println("Нельзя получить данные об пользователе, так как пользователей с машиной такой модели и серии несколько.");
      } catch (HibernateException e) {
         System.err.println("Ошибка в получение пользователя по серии и модели машины");
         e.printStackTrace();
      }
      return user;
   }


}
