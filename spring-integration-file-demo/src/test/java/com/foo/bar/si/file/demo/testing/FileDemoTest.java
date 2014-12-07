package com.foo.bar.si.file.demo.testing;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.foo.bar.si.file.demo.context.SpringIntegrationFileDemoContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringIntegrationFileDemoContext.class)
public class FileDemoTest {
	
	@Autowired
	private MessageChannel filesIn;
	
	@Test
	public void textContextLoad(){
		
		Assert.assertNotNull(filesIn);
		
	}
	
	@Test
	public void testFilterFile() throws URISyntaxException{
		
		URL fileUrl = this.getClass().getResource("/FILES/bigFile.xlsx");
		File file = new File(fileUrl.toURI());
        Assert.assertTrue(file.exists());
		
		boolean isSent=filesIn.send(MessageBuilder.withPayload(file).build());
		
		Assert.assertNotNull(FileUtils.getFile("/output/bigFile/bigFile.xlsx"));
		
		
	}
	
	@Test
	public void testIntegration() throws URISyntaxException{
		
		URL fileUrl = this.getClass().getResource("/FILES/testFile.xlsx");
		File file = new File(fileUrl.toURI());
        Assert.assertTrue(file.exists());
		
		boolean isSent=filesIn.send(MessageBuilder.withPayload(file).build());
		
		Assert.assertNotNull(FileUtils.getFile("/output/zip/testFile.xlsx.csv.zip"));
	}
	
	
	@Test
	public void testRooting() throws URISyntaxException{
		
		URL fileUrl = this.getClass().getResource("/FILES/test.zip");
		File file = new File(fileUrl.toURI());
        Assert.assertTrue(file.exists());
		
		boolean isSent=filesIn.send(MessageBuilder.withPayload(file).build());
		
		Assert.assertNotNull(FileUtils.getFile("/output/others/test.zip"));
	}

}
