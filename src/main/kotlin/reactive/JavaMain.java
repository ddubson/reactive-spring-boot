package reactive;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

@Configuration
public class JavaMain {
	public static void main(String[] args) {
		// Spring 5 dynamic bean initialization feature.
		// Drop in an initializer with a context, then register beans conditionally
		new SpringApplicationBuilder().sources(SimpleSpringBootApp.class)
				.initializers((ApplicationContextInitializer<GenericApplicationContext>) applicationContext -> {
					if(Math.random() > .5) {
						applicationContext.registerBean(ApplicationRunner.class, () -> (arg -> System.out.println("Hello World!")));
					}
				}).run(args);
	}

/*	@Bean
	ApplicationRunner runner() {
		return args -> System.out.println("Hello World");
	}*/

}
