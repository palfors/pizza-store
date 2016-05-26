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
import com.alforsconsulting.pizzastore.StoreUtil;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.MenuItemUtil;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailType;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailUtil;
import com.alforsconsulting.pizzastore.menu.pizza.ToppingPlacement;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderUtil;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.OrderLineUtil;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetailUtil;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class OrderHibernateTest extends AbstractHibernateTest {

    @BeforeClass
    public static void prepareClass() throws Exception {
        setUpClass();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        tearDownClass();
    }

    @Test
	public void testCRUD() {
        logger.debug("testCRUD entry");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // create store for order
        PizzaStore pizzaStore = StoreUtil.create("test-store");
        StoreUtil.save(session, pizzaStore);
        Assert.assertNotNull(pizzaStore);
        logger.debug("Created pizzaStore [{}]", pizzaStore);

        // create customer for order
        Customer customer = CustomerUtil.create("test-customer");
        CustomerUtil.save(session, customer);
        Assert.assertNotNull(customer);
        logger.debug("Created customer [{}]", customer);

        // get a menuItem to use for the order
        MenuItem menuItem = MenuItemUtil.getMenuItem(MenuItemType.PIZZA);
        Assert.assertNotNull(menuItem);

        // get a menuItemDetail to use for the order
        MenuItemDetail menuItemDetail =
                MenuItemDetailUtil.getMenuItemDetail(menuItem.getMenuItemId(),
                        MenuItemDetailType.TOPPING,
                        "Onion");
        Assert.assertNotNull(menuItemDetail);

        // create the order
        Order order = OrderUtil.create(pizzaStore.getStoreId(), customer.getCustomerId(), 23.45);
        OrderUtil.save(session, order);
        Assert.assertNotNull(order);
        logger.debug("Created order [{}]", order);

        // create an order line
        int lineQuantity = 2;
        double linePrice = (menuItem.getPrice() * lineQuantity);
        OrderLine orderLine = OrderLineUtil.create(
                order.getOrderId(), menuItem.getMenuItemId(), lineQuantity, linePrice);
        OrderLineUtil.save(session, orderLine);
        Assert.assertNotNull(orderLine);
        logger.debug("Created orderLine [{}]", orderLine);
        // add the line to the order
        order.addLine(orderLine);

        // create an order line detail
        OrderLineDetail orderLineDetail =
                OrderLineDetailUtil.create(orderLine.getOrderLineId(),
                        menuItemDetail.getMenuItemDetailId(),
                        ToppingPlacement.WHOLE,
                        menuItemDetail.getPrice());
        OrderLineDetailUtil.save(session, orderLineDetail);
        Assert.assertNotNull(orderLineDetail);
        logger.debug("Saved orderLineDetail [{}]", orderLineDetail);
        // add the detail to the line
        orderLine.addOrderLineDetail(orderLineDetail);

        // delete the test records
        OrderUtil.delete(session, order);
        CustomerUtil.delete(session, customer);
        StoreUtil.delete(session, pizzaStore);

        session.getTransaction().commit();
        session.close();

        // list them
        List<Order> orders = OrderUtil.getOrders();
        logger.debug("Loaded orders");
        for ( Order ord : orders ) {
            logger.debug(ord);
        }

        // load an order from the DB
        order = OrderUtil.getOrder(orders.get(0).getOrderId());
        Assert.assertNotNull(order);
        logger.debug("Loaded order [{}]", order);

    }

    @Test
    public void testObjectSave() {
        logger.debug("testObjectSave entry");

        // create store for order
        PizzaStore pizzaStore = StoreUtil.create("testObjectSave-store");
        StoreUtil.save(pizzaStore);
        Assert.assertNotNull(pizzaStore);
        logger.debug("Created pizzaStore [{}]", pizzaStore);

        // create customer for order
        Customer customer = CustomerUtil.create("testObjectSave-customer");
        CustomerUtil.save(customer);
        Assert.assertNotNull(customer);
        logger.debug("Created customer [{}]", customer);

        // get a menuItem to use for the order
        MenuItem menuItem = MenuItemUtil.getMenuItem(MenuItemType.PIZZA);
        Assert.assertNotNull(menuItem);

        // get a menuItemDetail to use for the order
        MenuItemDetail menuItemDetail =
                MenuItemDetailUtil.getMenuItemDetail(menuItem.getMenuItemId(),
                        MenuItemDetailType.TOPPING,
                        "Onion");
        Assert.assertNotNull(menuItemDetail);

        // create the order
        Order order = OrderUtil.newOrder();
        order.setStoreId(pizzaStore.getStoreId());
        order.setCustomerId(customer.getCustomerId());
        order.setPrice(23.45);
        logger.debug("Created order [{}]", order);

        // create an order line
        int lineQuantity = 2;
        double linePrice = (menuItem.getPrice() * lineQuantity);
        OrderLine orderLine = OrderLineUtil.newOrderLine();
        orderLine.setOrderId(order.getOrderId());
        orderLine.setMenuItemId(menuItem.getMenuItemId());
        orderLine.setQuantity(lineQuantity);
        orderLine.setPrice(linePrice);
        logger.debug("Created orderLine [{}]", orderLine);
        // add the line to the order
        order.addLine(orderLine);

        // create an order line detail
        OrderLineDetail orderLineDetail = OrderLineDetailUtil.newOrderLineDetail();
        orderLineDetail.setOrderLineId(orderLine.getOrderLineId());
        orderLineDetail.setMenuItemDetailId(menuItemDetail.getMenuItemDetailId());
        orderLineDetail.setPlacement(ToppingPlacement.WHOLE.name());
        orderLineDetail.setPrice(menuItemDetail.getPrice());
        logger.debug("Created orderLineDetail [{}]", orderLineDetail);
        // add the detail to the line
        orderLine.addOrderLineDetail(orderLineDetail);

        // save the order in one transaction
        OrderUtil.save(order);

        // delete the order and its children in one transaction
        OrderUtil.delete(order);

        // delete the supporting data
        CustomerUtil.delete(customer);
        StoreUtil.delete(pizzaStore);
    }

}
