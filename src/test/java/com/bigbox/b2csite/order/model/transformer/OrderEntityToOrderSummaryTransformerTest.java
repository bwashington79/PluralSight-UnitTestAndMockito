package com.bigbox.b2csite.order.model.transformer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.UUID;

import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Rule;


import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.entity.OrderItemEntity;
import org.junit.rules.ExpectedException;

public class OrderEntityToOrderSummaryTransformerTest {

   private OrderEntityToOrderSummaryTransformer target = null;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setup(){
       target = new OrderEntityToOrderSummaryTransformer();
   }

   @Test
    public void test_transform_success(){

        String orderNumberFixture = UUID.randomUUID().toString();

        OrderEntity orderEntityFixture = new OrderEntity();
        orderEntityFixture.setOrderNumber(orderNumberFixture);
        orderEntityFixture.setOrderItemList(new LinkedList<OrderItemEntity>());

        OrderItemEntity itemFixture1 = new OrderItemEntity();
        itemFixture1.setQuantity(1);
        itemFixture1.setSellingPrice(new BigDecimal("10.00"));
        orderEntityFixture.getOrderItemList().add(itemFixture1);

        OrderItemEntity itemFixture2 = new OrderItemEntity();
        itemFixture2.setQuantity(2);
        itemFixture2.setSellingPrice(new BigDecimal("1.50"));
        orderEntityFixture.getOrderItemList().add(itemFixture2);

        orderEntityFixture.setId(123456L);


       OrderSummary result  = target.transform(orderEntityFixture);

        Assert.assertNotNull(result);
        Assert.assertEquals(1234567L, orderEntityFixture.getId());

        Assert.assertEquals(orderNumberFixture, result.getOrderNumber());
        Assert.assertEquals(3, result.getItemCount());
        Assert.assertEquals(new BigDecimal("13.00"), result.getTotalAmount());

   }


   @Test()
    public void test_transform_inputIsNull(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("orderEntity should not be null");
        target.transform(null);
   }
}
