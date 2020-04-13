package com.transfer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.transfer.controller.HomeController;

public class AppTest{
	
    @Test
    public void testApp(){
    	
    	HomeController hc = new HomeController();
    	String result = hc.home();
    	assertEquals(result, "Transfer server is running");
        
    }
}
