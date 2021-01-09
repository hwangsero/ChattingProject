package com.clone.chat;

import com.clone.chat.code.UserRole;
import com.clone.chat.domain.User;
import com.clone.chat.domain.base.UserBase;
import com.clone.chat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

@EnableJpaAuditing
@SpringBootApplication
@Slf4j
public class ChatApplication implements CommandLineRunner {

    @Value("${spring.application.name}")
    String applicationName;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ChatApplication.class);
        SpringApplication build = builder.build();
        build.addListeners(new ApplicationPidFileWriter());
        ConfigurableApplicationContext run = builder.run(args);
    }

    @EventListener
    public void applicationStartedEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.debug("applicationStartedEvent done!! -> " + activeProfile);
        if ("dev".equals(activeProfile)) {
            User admin = UserBase.builder().id("admin").password(passwordEncoder.encode("1234")).nickName("admin-nick").role(UserRole.ADMIN).build().map(User.class);
            User user1 = UserBase.builder().id("user1").password(passwordEncoder.encode("1234")).nickName("user1-nick").role(UserRole.USER).build().map(User.class);
            User user2 = UserBase.builder().id("user2").password(passwordEncoder.encode("1234")).nickName("user2-nick").role(UserRole.USER).build().map(User.class);
            User user3 = UserBase.builder().id("user3").password(passwordEncoder.encode("1234")).nickName("user3-nick").role(UserRole.USER).build().map(User.class);
            User user4 = UserBase.builder().id("user4").password(passwordEncoder.encode("1234")).nickName("user4-nick").role(UserRole.USER).build().map(User.class);
            User user5 = UserBase.builder().id("user5").password(passwordEncoder.encode("1234")).nickName("user5-nick").role(UserRole.USER).build().map(User.class);
            User user6 = UserBase.builder().id("user6").password(passwordEncoder.encode("1234")).nickName("user6-nick").role(UserRole.USER).build().map(User.class);
            userRepository.saveAll(Arrays.asList(admin, user1, user2, user3, user4, user5, user6));
            userRepository.flush();
            user1.addFirend(user2, user3, user4);
            user2.addFirend(user3, user4);
            user3.addFirend(user1, user5);
            user4.addFirend(user3, user5);
            user5.addFirend(user1, user2, user3, user4, user6);
            userRepository.saveAll(Arrays.asList(admin, user1, user2, user3, user4, user5, user6));

        }
    }

    @Override
    public void run(String... args) {
//        log.info("EXECUTING : command line runner");
//
//        for (int i = 0; i < args.length; ++i) {
//            log.info("args[{}]: {}", i, args[i]);
//        }
    }
//    @Bean
//	@Profile("dev")
//	public void test() {
//		log.info("----");
//	}


    @PostConstruct
    public void onStart() {
        log.info("START {}", applicationName);
    }

    @PreDestroy
    public void onExit() {
        log.info("EXIT {}", applicationName);
    }

}
