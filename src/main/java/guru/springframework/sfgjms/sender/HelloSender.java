package guru.springframework.sfgjms.sender;

import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloSender {
	
	private final JmsTemplate jmsTemplate;
	
	@Scheduled
	public void sendMessage() {
		
		System.out.println("I'm sending message !");
		
		HelloWorldMessage message = HelloWorldMessage.builder()
				.id(UUID.randomUUID())
				.message("Hello World!")
				.build();
		
		jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, message);
		
		System.out.println("Message Sent!");
		
	}

}
