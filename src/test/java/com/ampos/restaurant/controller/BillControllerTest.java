package com.ampos.restaurant.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MimeTypeUtils;

import com.ampos.restaurant.RestaurantApplicationTests;
import com.ampos.restaurant.model.Bill;
import com.ampos.restaurant.model.BillDetail;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.dto.BillDTO;
import com.ampos.restaurant.model.dto.BillDetailDTO;
import com.ampos.restaurant.model.dto.OrderDTO;
import com.ampos.restaurant.model.dto.OrderedItemDTO;
import com.ampos.restaurant.repositories.MenuItemRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillControllerTest extends RestaurantApplicationTests {

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * Test case for create bill
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void test1_createBillTestCase() throws Exception {

        MenuItem item_1 = new MenuItem();
        item_1.setName("Cheese Burger");
        item_1.setDescription("A cheeseburger is a hamburger topped with cheese");
        item_1.setImageUrl("http://example.com/image.jpg");
        item_1.setPrice(15.0);
        item_1.setDetails("Fast Food");
        mockMvc.perform(
                post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(item_1)))
                .andExpect(status().is(201)).andReturn();
        MenuItem item_2 = new MenuItem();
        item_2.setName("Chicken Burger");
        item_2.setDescription("A burger is a hamburger topped with chicken");
        item_2.setImageUrl("http://example.com/image.jpg");
        item_2.setPrice(30.0);
        item_2.setDetails("Fast Food and Drink");
        mockMvc.perform(
                post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(item_2)))
                .andExpect(status().is(201)).andReturn();

        OrderDTO order = new OrderDTO();
        List<OrderedItemDTO> lstOrders = new ArrayList<OrderedItemDTO>();
        OrderedItemDTO ordered_1 = new OrderedItemDTO("Cheese Burger", 2);
        OrderedItemDTO ordered_2 = new OrderedItemDTO("Chicken Burger", 2);
        lstOrders.add(ordered_1);
        lstOrders.add(ordered_2);
        order.setOrder(lstOrders);

        MvcResult result = mockMvc.perform(
                post("/api/bills").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(order)))
                .andExpect(status().is(201)).andReturn();
        BillDTO actualRes = jsonToObject(result.getResponse().getContentAsString(), BillDTO.class);
        assertEquals(actualRes.getId(), Long.valueOf(1));
    }

    /**
     * Test case for update bill
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void test2_updateBillTestCase() throws Exception {

        MenuItem item_1 = new MenuItem();
        item_1.setName("Cheese Burger");
        item_1.setDescription("A cheeseburger is a hamburger topped with cheese");
        item_1.setImageUrl("http://example.com/image.jpg");
        item_1.setPrice(15.0);
        item_1.setDetails("Fast Food");
        mockMvc.perform(
                post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(item_1)))
                .andExpect(status().is(201)).andReturn();
        MenuItem item_2 = new MenuItem();
        item_2.setName("Chicken Burger");
        item_2.setDescription("A burger is a hamburger topped with chicken");
        item_2.setImageUrl("http://example.com/image.jpg");
        item_2.setPrice(30.0);
        item_2.setDetails("Fast Food and Drink");
        mockMvc.perform(
                post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(item_2)))
                .andExpect(status().is(201)).andReturn();

        OrderDTO order_1 = new OrderDTO();
        List<OrderedItemDTO> lstOrders = new ArrayList<OrderedItemDTO>();
        OrderedItemDTO ordered_1 = new OrderedItemDTO("Cheese Burger", 2);
        OrderedItemDTO ordered_2 = new OrderedItemDTO("Chicken Burger", 2);
        lstOrders.add(ordered_1);
        lstOrders.add(ordered_2);
        order_1.setOrder(lstOrders);

        mockMvc.perform(
                post("/api/bills").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(order_1)))
                .andExpect(status().is(201)).andReturn();

        lstOrders.clear();
        OrderDTO order_2 = new OrderDTO();
        ordered_1 = new OrderedItemDTO("Cheese Burger", 2);
        ordered_2 = new OrderedItemDTO("Chicken Burger", 10);
        lstOrders.add(ordered_1);
        lstOrders.add(ordered_2);
        order_2.setOrder(lstOrders);

        MvcResult result = mockMvc.perform(
                put("/api/bills/1").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(order_2)))
                .andExpect(status().is(200)).andReturn();

        BillDTO actualRes = jsonToObject(result.getResponse().getContentAsString(), BillDTO.class);
        boolean isUpdateSuccessfully = false;
        Set<BillDetailDTO> billDetails = actualRes.getBillDetails();
        for (BillDetailDTO billDetail : billDetails) {
            MenuItem menuItem = menuItemRepository.findById(2L).orElse(null);
            if (menuItem != null) {
                if ("Chicken Burger".equals(menuItem.getName())) {
                    if (billDetail.getQuantity() == 10) {
                        isUpdateSuccessfully = true;
                    }
                }
            }
        }
        assertEquals(isUpdateSuccessfully, true);
    }

}
