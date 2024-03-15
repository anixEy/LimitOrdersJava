package org.afob.limit;

import java.math.BigDecimal;

import org.afob.execution.ExecutionClient;
import org.junit.Assert;
import org.junit.Test;

public class LimitOrderAgentTest {

    @Test
    public void addTestsHere() {
    	
    	// Creating LimitOrderAgent instance
    	ExecutionClient executionClient = new ExecutionClient();
    	LimitOrderAgent limitOrderAgent = new LimitOrderAgent(executionClient);

        // Adding test orders
        limitOrderAgent.addOrder(true, "IBM", 500, 110.0);
        limitOrderAgent.addOrder(false, "AAPL", 300, 130.0);

        // Simulating price tick
        limitOrderAgent.priceTick("IBM", BigDecimal.valueOf(90.00)); // IBM price falls below $100, should execute buy order
        limitOrderAgent.priceTick("AAPL", BigDecimal.valueOf(140.00)); // AAPL price rises above $130, should execute sell order

//        Assert.fail("not implemented");
    }
}