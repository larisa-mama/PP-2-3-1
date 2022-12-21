package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("web")                                   //указываем тот пакет, где лежит наш контроллер
public class WebConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    //@Autowired
    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/");              // папка, где лежат наши представления
        templateResolver.setSuffix(".html");                        // задаем расширение представлений
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());         // установка распознавателя и его возврат
        templateEngine.setEnableSpringELCompiler(true);         /*Включение компилятора SpringEL с Spring 4.2.4 или новее может ускорить выполнение в большинстве сценариев, но может быть несовместимым с конкретными случаями повторного использования выражений в одном шаблоне
                                                                 для разных типов данных, поэтому этот флаг по умолчанию «ложь» для более безопасной обратной совместимости */
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {         // отвечает за обработку шаблонов Thymeleaf в результате выполнения контроллеров
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html; charset=UTF-8");
        registry.viewResolver(resolver);
    }

}
