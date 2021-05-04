package eva.assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import eva.assignment.dao.ShareQuantityRepo;
import eva.assignment.dao.ShareRepo;
import eva.assignment.dao.UserRepo;
import eva.assignment.model.Share;
import eva.assignment.model.ShareQuantity;
import eva.assignment.model.User;
import eva.assignment.service.ShareService;

@SpringBootApplication
@EnableScheduling
public class EvachangeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
        		SpringApplication.run(EvachangeApplication.class, args);
        
        ShareRepo shareRepo = configurableApplicationContext.getBean(ShareRepo.class);
        UserRepo userRepo = configurableApplicationContext.getBean(UserRepo.class);
        ShareQuantityRepo shareQuantityRepo = configurableApplicationContext.getBean(ShareQuantityRepo.class);
        
        
        // Dummy DB
        Share share1 = new Share();
        share1.setSymbol("ABC");
        share1.setRate(3.50);
        shareRepo.save(share1);
        
        Share share2 = new Share();
        share2.setSymbol("DEF");
        share2.setRate(3.00);
        shareRepo.save(share2);
        
        Share share3 = new Share();
        share3.setSymbol("GHI");
        share3.setRate(7.50);
        shareRepo.save(share3);
        
        
        User user1 = new User();
        user1.setWallet(new HashSet<ShareQuantity>());
        user1.setName("Tugrul");
        user1.setBudget(500);
        userRepo.save(user1);
        
        User user2 = new User();
        user2.setWallet(new HashSet<ShareQuantity>());
        user2.setName("user1");
        user2.setBudget(1000);
        userRepo.save(user2);
        
        
        ShareQuantity shareQuantity1 = new ShareQuantity();
        shareQuantity1.setQuantity(2);
        shareQuantity1.setSymbol("ABC");
        shareQuantity1.setUser(user2);
        shareQuantityRepo.save(shareQuantity1);
        
    }
    

}
