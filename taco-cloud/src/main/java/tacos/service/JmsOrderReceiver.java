package tacos.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import tacos.model.TacoOrder;

@Service
public class JmsOrderReceiver implements OrderReceiver {

	private JmsTemplate jmsTemplate;

	public JmsOrderReceiver(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public TacoOrder receiveOrder() {
		return (TacoOrder) jmsTemplate.receiveAndConvert("tacocloud.order.queue");
	}

}
