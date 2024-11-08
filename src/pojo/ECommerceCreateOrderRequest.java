package pojo;

import java.util.List;

public class ECommerceCreateOrderRequest {
	
	List<ECommerceOrderInformation> orders;

	public List<ECommerceOrderInformation> getOrders() {
		return orders;
	}

	public void setOrders(List<ECommerceOrderInformation> orders) {
		this.orders = orders;
	}

}
