package guru.springframework.sfgjms.sender;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloSender {
	
	private final JmsTemplate jmsTemplate;
	private final ObjectMapper objectMapper;
	
	@Scheduled(fixedRate = 2000)
	public void sendMessage() {
		
//		System.out.println("I'm sending message !");
		
		HelloWorldMessage message = HelloWorldMessage.builder()
				.id(UUID.randomUUID())
				.message("Hello World!")
				.build();
		
		jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, message);
		
//		System.out.println("Message Sent!");
		
	}

	@Scheduled(fixedRate = 2000)
	public void sendReceiveMessage() throws JMSException {
		
		HelloWorldMessage message = HelloWorldMessage.builder()
				.id(UUID.randomUUID())
				.message("Hello")
				.build();
		
		Message receiveMsg = jmsTemplate.sendAndReceive(JMSConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message helloMessage = null;
				
				try {
					helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
					helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
					
					System.out.println("Sending hello");
					return helloMessage;
				} catch (JsonProcessingException | JMSException e) {
					throw new JMSException("boom");
				}
				
			}
		});
		
		System.out.println("Reciving message : "+receiveMsg.getBody(String.class));
		
	}
}
