package guru.springframework.sfgjms.listner;

import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;

@Component
public class HelloMessageListner {
	
	@JmsListener(destination = JMSConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage,
			@Headers MessageHeaders messageHeaders, Message message) {
		
		System.out.println("I got a message!");
		
		System.out.println(helloWorldMessage);
		
		//throw new RuntimeException("foo");
	}

}
