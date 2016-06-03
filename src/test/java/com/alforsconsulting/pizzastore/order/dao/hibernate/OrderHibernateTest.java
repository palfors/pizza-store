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
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

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
        assertNotNull(pizzaStore);
        logger.debug("Created pizzaStore [{}]", pizzaStore);

        // create customer for order
        Customer customer = CustomerUtil.create("test-customer");
        CustomerUtil.save(session, customer);
        assertNotNull(customer);
        logger.debug("Created customer [{}]", customer);

        // get a menuItem to use for the order
        MenuItem menuItem = MenuItemUtil.getMenuItem(MenuItemType.PIZZA);
        assertNotNull(menuItem);

        // get a menuItemDetail to use for the order
        MenuItemDetail menuItemDetail =
                MenuItemDetailUtil.getMenuItemDetail(menuItem.getMenuItemId(),
                        MenuItemDetailType.TOPPING,
                        "Onion");
        assertNotNull(menuItemDetail);

        // create the order
        Order order = OrderUtil.create(pizzaStore.getStoreId(), customer.getCustomerId(), 23.45);
        OrderUtil.save(session, order);
        assertNotNull(order);
        order = OrderUtil.getOrder(session, order.getOrderId());
        assertNotNull(order);
        logger.debug("Created order [{}]", order);

        // create an order line
        int lineQuantity = 2;
        double linePrice = (menuItem.getPrice() * lineQuantity);
        OrderLine orderLine = OrderLineUtil.create(
                order.getOrderId(), menuItem.getMenuItemId(), lineQuantity, linePrice);
        OrderLineUtil.save(session, orderLine);
        assertNotNull(orderLine);
        orderLine = OrderLineUtil.getOrderLine(session, orderLine.getOrderLineId());
        assertNotNull(orderLine);
        logger.debug("Created orderLine [{}]", orderLine);
        // add the line to the order
        order.addLine(orderLine);
        // check order line count
        List<OrderLine> orderLines = OrderLineUtil.getOrderLines(session, order.getOrderId());
        assertTrue(orderLines.size() == 1);

        // create an order line detail
        OrderLineDetail orderLineDetail =
                OrderLineDetailUtil.create(orderLine.getOrderLineId(),
                        menuItemDetail.getMenuItemDetailId(),
                        ToppingPlacement.WHOLE,
                        menuItemDetail.getPrice());
        OrderLineDetailUtil.save(session, orderLineDetail);
        assertNotNull(orderLineDetail);
        orderLineDetail = OrderLineDetailUtil.getOrderLineDetail(session, orderLineDetail.getOrderLineDetailId());
        assertNotNull(orderLineDetail);
        logger.debug("Saved orderLineDetail [{}]", orderLineDetail);
        // add the detail to the line
        orderLine.addOrderLineDetail(orderLineDetail);
        // check order line detail count
        List<OrderLineDetail> orderLineDetails = OrderLineDetailUtil.getOrderLineDetails(session, orderLine.getOrderLineId());
        assertTrue(orderLineDetails.size() == 1);

        // delete the test records
        OrderUtil.delete(session, order);
        order = OrderUtil.getOrder(session, order.getOrderId());
        assertNull(order);

        CustomerUtil.delete(session, customer);
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertNull(customer);

        StoreUtil.delete(session, pizzaStore);
        pizzaStore = StoreUtil.getStore(session, pizzaStore.getStoreId());
        assertNull(pizzaStore);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    @Ignore("Ignoring until there are orders maintained in the database")
    public void list() {
        logger.debug("Listing orders");
        List<Order> orders = OrderUtil.getOrders();
        assertTrue(orders.size() > 0);
        logger.debug("Loaded orders");
        for ( Order order : orders ) {
            logger.debug(order);
        }

        // list lines for first order
        long orderId = orders.get(0).getOrderId();
        List<OrderLine> orderLines = OrderLineUtil.getOrderLines(orderId);
        assertTrue(orderLines.size() > 0);
        logger.debug("Loaded orderLines for order [{}]", orderId);
        for ( OrderLine orderLine : orderLines ) {
            logger.debug(orderLine);
        }

        // list details for first line
        long orderLineId = orderLines.get(0).getOrderLineId();
        List<OrderLineDetail> orderLineDetails =
                OrderLineDetailUtil.getOrderLineDetails(orderLineId);
        assertTrue(orderLineDetails.size() > 0);
        logger.debug("Loaded orderLineDetails for orderLine [{}]", orderLineId);
        for ( OrderLineDetail orderLineDetail : orderLineDetails ) {
            logger.debug(orderLineDetail);
        }
    }

    @Test
    @Ignore("Ignoring until there are orders maintained in the database")
    public void listOrders() {
        logger.debug("Listing orders");
        List<Order> orders = OrderUtil.getOrders();
        assertTrue(orders.size() > 0);
        logger.debug("Loaded orders");
        for ( Order order : orders ) {
            logger.debug(order);
        }
    }

    @Test
    @Ignore("Ignoring until there are orders maintained in the database")
    public void listOrderLines() {
        logger.debug("Listing orderLines");
        List<OrderLine> orderLines = OrderLineUtil.getOrderLines();
        assertTrue(orderLines.size() > 0);
        logger.debug("Loaded orderLines");
        for ( OrderLine orderLine : orderLines ) {
            logger.debug(orderLine);
        }
    }

    @Test
    @Ignore("Ignoring until there are orders maintained in the database")
    public void listOrderLineDetails() {
        logger.debug("Listing orderLineDetails");
        List<OrderLineDetail> orderLineDetails = OrderLineDetailUtil.getOrderLineDetails();
        assertTrue(orderLineDetails.size() > 0);
        logger.debug("Loaded orderLineDetails");
        for ( OrderLineDetail orderLineDetail : orderLineDetails ) {
            logger.debug(orderLineDetail);
        }
    }

    @Test
    @Ignore("Ignoring until there are orders maintained in the database")
    public void load() {
        logger.debug("Load() entry");
        // load a record from the DB
        List<Order> orders = OrderUtil.getOrders();
        assertTrue(orders.size() > 0);

        long orderId = orders.get(0).getStoreId();

        Order order = OrderUtil.getOrder(orderId);
        assertNotNull(order);
        logger.debug("Found order by Id [{}] with [{}] lines", order, order.getOrderLines().size());

        List<OrderLine> orderLines = OrderLineUtil.getOrderLines(orderId);
        assertTrue(orderLines.size() > 0);

        long orderLineId = orderLines.get(0).getOrderLineId();

        OrderLine orderLine = OrderLineUtil.getOrderLine(orderLineId);
        assertNotNull(orderLine);
        logger.debug("Found orderLine by Id [{}]", orderLine);

        List<OrderLineDetail> orderLineDetails = OrderLineDetailUtil.getOrderLineDetails(orderLineId);
        assertTrue(orderLineDetails.size() > 0);

        long orderLineDetailId = orderLineDetails.get(0).getOrderLineDetailId();

        OrderLineDetail orderLineDetail = OrderLineDetailUtil.getOrderLineDetail(orderLineDetailId);
        assertNotNull(orderLineDetail);
        logger.debug("Found orderLineDetail by Id [{}]", orderLineDetail);
    }


    @Test
    public void testObjectSave() {
        logger.debug("testObjectSave entry");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // create store for order
        PizzaStore pizzaStore = StoreUtil.create("testObjectSave-store");
        StoreUtil.save(session, pizzaStore);
        assertNotNull(pizzaStore);
        logger.debug("Created pizzaStore [{}]", pizzaStore);

        // create customer for order
        Customer customer = CustomerUtil.create("testObjectSave-customer");
        CustomerUtil.save(session, customer);
        assertNotNull(customer);
        logger.debug("Created customer [{}]", customer);

        // get a menuItem to use for the order
        MenuItem menuItem = MenuItemUtil.getMenuItem(MenuItemType.PIZZA);
        assertNotNull(menuItem);

        // get a menuItemDetail to use for the order
        MenuItemDetail menuItemDetail =
                MenuItemDetailUtil.getMenuItemDetail(menuItem.getMenuItemId(),
                        MenuItemDetailType.TOPPING,
                        "Onion");
        assertNotNull(menuItemDetail);

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
        OrderUtil.save(session, order);

        // delete the order and its children in one transaction
        OrderUtil.delete(session, order);

        // delete the supporting data
        CustomerUtil.delete(session, customer);
        StoreUtil.delete(session, pizzaStore);

        session.getTransaction().commit();
        session.close();

    }

    @Test
    public void testDeleteCustomerOrders() {
        logger.debug("testDeleteCustomerOrders entry");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // create store for order
        PizzaStore pizzaStore = StoreUtil.create("test-store");
        StoreUtil.save(session, pizzaStore);
        assertNotNull(pizzaStore);

        // create the customer
        Customer customer = CustomerUtil.create("OrderHibernateTestCustomer");
        CustomerUtil.save(customer);
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertNotNull(customer);

        // create an order for the customer
        Order order = OrderUtil.create(pizzaStore.getStoreId(), customer.getCustomerId(), 23.45);
        OrderUtil.save(session, order);
        assertNotNull(order);
        order = OrderUtil.getOrder(session, order.getOrderId());
        assertNotNull(order);

        // get customer order count
        List<Order> orders = OrderUtil.getCustomerOrders(customer.getCustomerId(), session);
        assertTrue(orders.size() == 1);

        // delete the customer orders
        OrderUtil.deleteCustomerOrders(customer.getCustomerId(), session);

        // get customer order count
        orders = OrderUtil.getCustomerOrders(customer.getCustomerId(), session);
        assertTrue(orders.size() == 0);

        // delete customer
        CustomerUtil.delete(session, customer);
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertNull(customer);

        // delete store
        StoreUtil.delete(session, pizzaStore);
        pizzaStore = StoreUtil.getStore(session, pizzaStore.getStoreId());
        assertNull(pizzaStore);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testDeleteStoreOrders() {
        logger.debug("testDeleteStoreOrders entry");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // create store for order
        PizzaStore pizzaStore = StoreUtil.create("test-store");
        StoreUtil.save(session, pizzaStore);
        assertNotNull(pizzaStore);

        // create the customer
        Customer customer = CustomerUtil.create("OrderHibernateTestCustomer");
        CustomerUtil.save(customer);
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertNotNull(customer);

        // create an order for the store
        Order order = OrderUtil.create(pizzaStore.getStoreId(), customer.getCustomerId(), 23.45);
        OrderUtil.save(session, order);
        assertNotNull(order);
        order = OrderUtil.getOrder(session, order.getOrderId());
        assertNotNull(order);

        // get store order count
        List<Order> orders = OrderUtil.getStoreOrders(pizzaStore.getStoreId(), session);
        assertTrue(orders.size() == 1);

        // delete the store orders
        OrderUtil.deleteStoreOrders(pizzaStore.getStoreId(), session);

        // get store order count
        orders = OrderUtil.getStoreOrders(pizzaStore.getStoreId(), session);
        assertTrue(orders.size() == 0);

        // delete customer
        CustomerUtil.delete(session, customer);
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertNull(customer);

        // delete store
        StoreUtil.delete(session, pizzaStore);
        pizzaStore = StoreUtil.getStore(session, pizzaStore.getStoreId());
        assertNull(pizzaStore);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void merge() {
        logger.debug("merge an order");

        int num = new Double(Math.random()*1000).intValue();
        PizzaStore store = StoreUtil.create("TEST_STORE_" + num);
        StoreUtil.save(store);
        assertNotNull(store);

        num = new Double(Math.random()*1000).intValue();
        Customer customer = CustomerUtil.create("TEST_CUSTOMER_" + num);
        CustomerUtil.save(customer);
        assertNotNull(customer);

        // create an order
        Order order = OrderUtil.create(store.getStoreId(), customer.getCustomerId(), 23.45);
        OrderUtil.save(order);
        assertNotNull(order);
        order = OrderUtil.getOrder(order.getOrderId());
        assertNotNull(order);

        // merge an updated detached order
        Order updatedOrder = OrderUtil.newOrder();
        updatedOrder.setOrderId(order.getOrderId());
        updatedOrder.setStoreId(order.getStoreId());
        updatedOrder.setCustomerId(order.getCustomerId());
        // updated price
        updatedOrder.setPrice(100.00);
        OrderUtil.merge(updatedOrder);

        // verify change
        order = OrderUtil.getOrder(order.getOrderId());
        assertEquals(order.getPrice(), 100.00, 0);

        // get a menuItem to use for the order
        MenuItem menuItem = MenuItemUtil.getMenuItem(MenuItemType.PIZZA);
        assertNotNull(menuItem);

        // add a line
        OrderLine orderLine = OrderLineUtil.create(order.getOrderId(), menuItem.getMenuItemId(), 1, 23.45);
        OrderLineUtil.save(orderLine);
        assertNotNull(orderLine);
        orderLine = OrderLineUtil.getOrderLine(orderLine.getOrderLineId());
        assertNotNull(orderLine);

        // merge an updated detached order line
        OrderLine updatedOrderLine = OrderLineUtil.newOrderLine();
        updatedOrderLine.setOrderLineId(orderLine.getOrderLineId());
        updatedOrderLine.setOrderId(orderLine.getOrderId());
        updatedOrderLine.setMenuItemId(orderLine.getMenuItemId());
        // updated quantity
        updatedOrderLine.setQuantity(2);
        updatedOrderLine.setPrice(orderLine.getPrice());
        OrderLineUtil.merge(updatedOrderLine);

        // verify change
        orderLine = OrderLineUtil.getOrderLine(orderLine.getOrderLineId());
        assertEquals(orderLine.getQuantity(), 2);

        // get a menuItemDetail to use
        MenuItemDetail menuItemDetail = MenuItemDetailUtil.getMenuItemDetail(
                menuItem.getMenuItemId(), MenuItemDetailType.TOPPING, "Onion");
        assertNotNull(menuItemDetail);

        // add a line detail
        OrderLineDetail orderLineDetail = OrderLineDetailUtil.create(
                orderLine.getOrderLineId(),
                menuItemDetail.getMenuItemDetailId(),
                ToppingPlacement.WHOLE, 23.45);
        OrderLineDetailUtil.save(orderLineDetail);
        assertNotNull(orderLineDetail);
        orderLineDetail = OrderLineDetailUtil.getOrderLineDetail(
                orderLineDetail.getOrderLineDetailId());
        assertNotNull(orderLineDetail);

        // merge an updated detached order line
        OrderLineDetail updatedOrderLineDetail = OrderLineDetailUtil.newOrderLineDetail();
        updatedOrderLineDetail.setOrderLineDetailId(orderLineDetail.getOrderLineDetailId());
        updatedOrderLineDetail.setOrderLineId(orderLineDetail.getOrderLineId());
        updatedOrderLineDetail.setMenuItemDetailId(orderLineDetail.getMenuItemDetailId());
        updatedOrderLine.setPrice(orderLine.getPrice());
        // updated placement
        updatedOrderLineDetail.setPlacement(ToppingPlacement.LEFT.name());
        OrderLineDetailUtil.merge(updatedOrderLineDetail);

        // verify change
        orderLineDetail = OrderLineDetailUtil.getOrderLineDetail(orderLineDetail.getOrderLineDetailId());
        assertEquals(orderLineDetail.getPlacement(), ToppingPlacement.LEFT.name());

        // delete the order
        OrderUtil.delete(order);

        // get store order count
        order = OrderUtil.getOrder(order.getOrderId());
        assertNull(order);

        CustomerUtil.delete(customer);
        customer = CustomerUtil.getCustomer(customer.getCustomerId());
        assertNull(customer);

        StoreUtil.delete(store);
        store = StoreUtil.getStore(store.getStoreId());
        assertNull(store);
    }

}
