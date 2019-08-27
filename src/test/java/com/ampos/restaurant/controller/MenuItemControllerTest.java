package com.ampos.restaurant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MimeTypeUtils;

import com.ampos.restaurant.RestaurantApplicationTests;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.repositories.MenuItemRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuItemControllerTest extends RestaurantApplicationTests {

	@Autowired
	private MenuItemRepository repository;
	
	 /**
     * Test case for create menu
     *
     * @throws IOException
     * @throws Exception
     */
	@Test
	public void test1_createMenuTestCase() throws Exception {
		// initialize data
		MenuItem expectedRes = new MenuItem();
		expectedRes.setName("Cheese Burger");
		expectedRes.setDescription("A cheeseburger is a hamburger topped with cheese");
		expectedRes.setImageUrl("http://example.com/image.jpg");
		expectedRes.setPrice(15.0);
		expectedRes.setDetails("Fast Food");
		MvcResult result = mockMvc.perform(post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(expectedRes))).andExpect(status().is(201)).andReturn();
		MenuItem actualRes = jsonToObject(result.getResponse().getContentAsString(), MenuItem.class);
		//compare to expected result
		assertEquals(expectedRes.getName(), actualRes.getName());
		assertEquals(expectedRes.getDescription(), actualRes.getDescription());
		assertEquals(expectedRes.getImageUrl(), actualRes.getImageUrl());
		assertEquals(expectedRes.getPrice(), actualRes.getPrice());
		assertEquals(expectedRes.getDetails(), actualRes.getDetails());
	}
	
	/**
     * Test case for get menu
     *
     * @throws IOException
     * @throws Exception
     */
	@Test
	public void test2_getMenuTestCase() throws IOException, Exception {
		// initialize data
		MenuItem expectedRes = new MenuItem();
		expectedRes.setName("Chicken Burger");
		expectedRes.setDescription("A burger is a hamburger topped with chicken");
		expectedRes.setImageUrl("http://example.com/image.jpg");
		expectedRes.setPrice(30.0);
		expectedRes.setDetails("Fast Food and Drink");
		mockMvc.perform(post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(expectedRes))).andExpect(status().is(201)).andReturn();
		
		// get the menu newly added with id is 1 and compare to expect result
		MvcResult result = mockMvc.perform(get("/api/menu-items/1")).andExpect(status().is(200)).andReturn();
		MenuItem actualRes = jsonToObject(result.getResponse().getContentAsString(), MenuItem.class);
		//compare to expected result
		assertEquals(expectedRes.getName(), actualRes.getName());
		assertEquals(expectedRes.getDescription(), actualRes.getDescription());
		assertEquals(expectedRes.getImageUrl(), actualRes.getImageUrl());
		assertEquals(expectedRes.getPrice(), actualRes.getPrice());
		assertEquals(expectedRes.getDetails(), actualRes.getDetails());
	}
	
	/**
     * Test case for update menu
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void test3_updateMenuTestCase() throws IOException, Exception {
    	// initialize data
		MenuItem expectedRes = new MenuItem();
		expectedRes.setName("Italian PastaSpaghetti");
		expectedRes.setDescription("A burger is a hamburger topped with chicken");
		expectedRes.setImageUrl("http://example.com/image.jpg");
		expectedRes.setPrice(30.0);
		expectedRes.setDetails("Fast Food and Drink");
		mockMvc.perform(post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(expectedRes))).andExpect(status().is(201)).andReturn();
        // compare
		expectedRes.setPrice(45.0);
		expectedRes.setDescription("Spaghetti is a long, thin, solid, cylindrical pasta");
        MvcResult result = mockMvc.perform(
                put("/api/menu-items/1").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(expectedRes)))
                .andExpect(status().is(200)).andReturn();
        MenuItem actualRes = jsonToObject(result.getResponse().getContentAsString(), MenuItem.class);
        
        //compare to expected result
        assertEquals(expectedRes.getName(), actualRes.getName());
        assertEquals(expectedRes.getDescription(), actualRes.getDescription());
        assertEquals(expectedRes.getImageUrl(), actualRes.getImageUrl());
        assertEquals(expectedRes.getPrice(), actualRes.getPrice());
        assertEquals(expectedRes.getDetails(), actualRes.getDetails());
    }
    

    /**
     * Test case for delete menu
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void test4_deleteMenuTestCase() throws IOException, Exception {
    	MenuItem expectedRes = new MenuItem();
		expectedRes.setName("Italian PastaSpaghetti");
		expectedRes.setDescription("A burger is a hamburger topped with chicken");
		expectedRes.setImageUrl("http://example.com/image.jpg");
		expectedRes.setPrice(30.0);
		expectedRes.setDetails("Fast Food and Drink");
		mockMvc.perform(post("/api/menu-items").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(expectedRes))).andExpect(status().is(201)).andReturn();
        assertTrue(repository.existsById((long) 1));
        
        MvcResult result = mockMvc.perform(delete("/api/menu-items/1").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().is(204)).andReturn();
        
        assertEquals("", result.getResponse().getContentAsString());
    }
}
