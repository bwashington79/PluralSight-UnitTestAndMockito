package com.bigbox.b2csite.order.model.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.bigbox.b2csite.order.service.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.bigbox.b2csite.order.dao.OrderDao;
import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.transformer.OrderEntityToOrderSummaryTransformer;
import org.mockito.MockitoAnnotations;

public class OrderServiceImplTest {

    private final static long CUSTOMER_ID = 1L;

//    protected OrderDao mockOrderDao;
//    protected OrderEntityToOrderSummaryTransformer mockTransformer;

    //This is an example using the @Mock annotation that allows the intialization of annotation members all at once.

      @Mock private OrderDao mockOrderDao;
      @Mock private OrderEntityToOrderSummaryTransformer mockTransformer;


    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);  //This reduces creating the previous references below.

//        this.mockOrderDao = Mockito.mock(OrderDao.class);
//        this.mockTransformer = Mockito.mock(OrderEntityToOrderSummaryTransformer.class);
    }


    @Test
	public void test_getOrderSummary_success() throws Exception {

            // Setup
            OrderServiceImpl target = new OrderServiceImpl();

            target.setOrderDao(mockOrderDao);
            target.setTransformer(mockTransformer);

            OrderEntity orderEntityFixture = new OrderEntity();
            List<OrderEntity> orderEntityListFixture = new LinkedList<>();
            orderEntityListFixture.add(orderEntityFixture);

            Mockito.when(mockOrderDao.findOrdersByCustomer(CUSTOMER_ID))
            .thenReturn(orderEntityListFixture);

            OrderSummary orderSummaryFixture = new OrderSummary();
            Mockito.when(mockTransformer.transform(orderEntityFixture))
            .thenReturn(orderSummaryFixture);


            // Execution
            List<OrderSummary> result = target.getOrderSummary(CUSTOMER_ID);

            // Verification
            Mockito.verify(mockOrderDao).findOrdersByCustomer(CUSTOMER_ID);
            Mockito.verify(mockTransformer).transform(orderEntityFixture);
            Assert.assertNotNull(result);
            Assert.assertEquals(1, result.size());
            Assert.assertSame(orderSummaryFixture, result.get(0));

        }
    }
