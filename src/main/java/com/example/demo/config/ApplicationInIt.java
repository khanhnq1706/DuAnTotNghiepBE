package com.example.demo.config;


import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.UserEnitty;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInIt {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void addAdminUser() {

        if (userRepository.findByUsername("admin") == null) {
            UserEnitty adminUser = new UserEnitty();
            adminUser.setUsername("admin");
            PasswordEncoder pe = new BCryptPasswordEncoder(10);
            adminUser.setPassword(pe.encode("admin"));
            adminUser.setFullname("Administrator");
            adminUser.setIsAdmin(true);
            userRepository.save(adminUser); // Thêm người dùng admin vào database
            System.out.println("Admin user added successfully!");
        }

        if(areaRepository.findAll().isEmpty()){
            AreaEntity area = new AreaEntity(1,"T1",null);
            areaRepository.save(area);

        }

        if(categoryRepository.findAll().isEmpty()){
            CategoryFoodEntity cateFood = new CategoryFoodEntity();
            CategoryFoodEntity cateDrink = new CategoryFoodEntity();
            CategoryFoodEntity cateOrther = new CategoryFoodEntity();
            cateFood.setNameCategory("FOOD");
            cateDrink.setNameCategory("DRINK");
            cateOrther.setNameCategory("ORTHER");
            categoryRepository.save(cateFood);
            categoryRepository.save(cateDrink);
            categoryRepository.save(cateOrther);
        }

    }

}
