/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package com.alforsconsulting.pizzastore.order.dao.hibernate;

import com.alforsconsulting.pizzastore.AbstractHibernateTest;
import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailType;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.pizza.topping.ToppingPlacement;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class OrderHibernateTest extends AbstractHibernateTest {
    @BeforeClass
    public static void prepareClass() {
        AbstractHibernateTest.prepareClass();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

	@Test
	public void testBasicUsage() {
        logger.debug("testBasicUsage entry");
		// create orders...
		Session session = sessionFactory.openSession();
		session.beginTransaction();

        // create store for order
        PizzaStore pizzaStore = (PizzaStore) applicationContext.getBean("pizzaStore");
        pizzaStore.generateId();
        pizzaStore.setName("hibernate-store");
        session.save(pizzaStore);
        logger.debug("Saved pizzaStore [{}]", pizzaStore);

		// create customers for order
        Customer customer = (Customer) applicationContext.getBean("customer");
        customer.setName("hibernate-customer");
        logger.debug("Saved customer [{}]", customer);
        session.save(customer);

        // create the order
        Order order = (Order) applicationContext.getBean("order");
        order.setStoreId(pizzaStore.getStoreId());
        order.setCustomerId(customer.getCustomerId());
        order.setPrice(23.45);
        session.save(order);
        logger.debug("Saved order [{}]", order);

        // create a menuItem to add
        Pizza pizza = (Pizza) applicationContext.getBean("pizza");
        pizza.setName("hibernate-pizza");
        pizza.setPrice(8.99);
        session.save(pizza);
        logger.debug("Saving pizza [{}]", pizza);

        // create an order line
        OrderLine orderLine = (OrderLine) applicationContext.getBean("orderLine");
        orderLine.setOrderId(order.getOrderId());
        orderLine.setMenuItemId(pizza.getMenuItemId());
        orderLine.setQuantity(2);
        orderLine.setPrice(23.45);
        session.save(orderLine);
        logger.debug("Saved orderLine [{}]", orderLine);
        // add the line to the order
        order.addLine(orderLine);

        // create a menu item detail for this order line
        MenuItemDetail menuItemDetail =
                (MenuItemDetail) applicationContext.getBean("menuItemDetail");
        menuItemDetail.setMenuItemId(pizza.getMenuItemId());
        menuItemDetail.setDetailType(MenuItemDetailType.TOPPING.getBeanName());
        menuItemDetail.setName("hibernate-menuitemdetail");
        menuItemDetail.setPrice(3.25);
        session.save(menuItemDetail);
        logger.debug("Saved menuItemDetail [{}]", menuItemDetail);

        // create an order line detail
        OrderLineDetail orderLineDetail = (OrderLineDetail) applicationContext.getBean("orderLineDetail");
        orderLineDetail.setOrderLineId(orderLine.getOrderId());
        orderLineDetail.setMenuItemDetailId(menuItemDetail.getMenuItemDetailId());
        orderLineDetail.setPlacement(ToppingPlacement.WHOLE.name());
        orderLineDetail.setPrice(menuItemDetail.getPrice());
        session.save(orderLineDetail);
        logger.debug("Saved orderLineDetail [{}]", orderLineDetail);
        // add the detail to the line
        orderLine.addOrderLineDetail(orderLineDetail);

        // TODO: load the record from the DB

		// list them
        List<Order> orders = (List<Order>) session.createQuery( "from Order" ).list();
        logger.debug("Loading orders");
		for ( Order ord : orders ) {
            logger.debug(ord);
		}

        // delete the test records
        session.beginTransaction();
        session.delete(orderLineDetail);
        session.delete(menuItemDetail);
        session.delete(orderLine);
        session.delete(order);
        session.delete(pizza);
        session.delete(customer);
        session.delete(pizzaStore);

        session.getTransaction().commit();
        session.close();

        // TODO: verify record no longer exists
	}
}
