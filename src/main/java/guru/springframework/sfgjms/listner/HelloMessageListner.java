package guru.springframework.sfgjms.listner;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloMessageListner {
	
	private final JmsTemplate jmsTemplate;
	
	@JmsListener(destination = JMSConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage,
			@Headers MessageHeaders messageHeaders, Message message) {
		
//		System.out.println("I got a message!");
		
		System.out.println(helloWorldMessage);
		
		//throw new RuntimeException("foo");
	}
	
	@JmsListener(destination = JMSConfig.MY_SEND_RCV_QUEUE)
	public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
			@Headers MessageHeaders messageHeaders, Message message) throws JMSException {
		
		HelloWorldMessage payloadMsg = HelloWorldMessage.builder()
				.id(UUID.randomUUID())
				.message("Hello")
				.build();
		
		jmsTemplate.convertAndSend(message.getJMSReplyTo(),payloadMsg);
	}

}
