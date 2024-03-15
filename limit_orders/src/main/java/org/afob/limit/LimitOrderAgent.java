package org.afob.limit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.afob.execution.ExecutionClient;
import org.afob.execution.ExecutionClient.ExecutionException;
import org.afob.prices.PriceListener;

public class LimitOrderAgent implements PriceListener {
	
	  private ExecutionClient executionClient;
	  private List<Order> orders;

    public LimitOrderAgent(final ExecutionClient executionClient) {
    	 super();
         this.executionClient = executionClient;
         this.orders = new ArrayList<>();
    }


    @Override
    public void priceTick(String productId, BigDecimal price) {
    	  List<String> success = new ArrayList<>();
    	  try {
          if (productId.equals("IBM") && price.doubleValue() < 100.0) {
              executionClient.buy(productId, 1000);
              success.add(productId);
              return;
          }
          
          List<Order> tempOrders = new ArrayList<>(orders);
          for (Order order : tempOrders) {
              if (order.getProductId().equals(productId) && order.isBuy() && order.getLimit() <= price.doubleValue()) {
                  try {
                      executionClient.buy(order.getProductId(), order.getAmount());
                      orders.remove(order);
                      success.add(productId);
                  } catch (ExecutionException e) {
                      System.out.println("Failed to execute buy order: " + e.getMessage());
                  }
              } else if (order.getProductId().equals(productId) && !order.isBuy() && order.getLimit() >= price.doubleValue()) {
                  try {
                      executionClient.sell(order.getProductId(), order.getAmount());
                      orders.remove(order);
                      success.add(productId);
                  } catch (ExecutionException e) {
                      System.out.println("Failed to execute sell order: " + e.getMessage());
                  }
              }
          }
    	  }catch (ExecutionException e) {
			e.printStackTrace();
		}
    }
    
    public void addOrder(boolean buy, String productId, int amount, double limit) {
        orders.add(new Order(buy, productId, amount, limit));
    }

}
