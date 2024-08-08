package tacos.service;

import tacos.model.TacoOrder;

public interface OrderMessagingService {

	void sendOrder(TacoOrder order);

	void converAndSend(TacoOrder order);

}
