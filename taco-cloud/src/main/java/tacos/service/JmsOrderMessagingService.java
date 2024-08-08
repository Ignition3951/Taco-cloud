package tacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import tacos.model.TacoOrder;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

	private JmsTemplate jmsTemplate;

	@Autowired
	public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void sendOrder(TacoOrder order) {
		jmsTemplate.send(new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createObjectMessage(order);
			}
		});
	}

	@Override
	public void converAndSend(TacoOrder order) {
		jmsTemplate.convertAndSend(order, new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("X_ORDER_SOURCE", "WEB");
				return message;
			}
		});

	}

}
