package com.meafs;

import com.meafs.Back.Charsets;
import com.meafs.Back.l1.Caesar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptorsApplicationTests {
	Caesar test = new Caesar(Charsets.ENG.getCharset());

	@Test
	public void contextLoads() {
		String enc = test.encrypt("abc", 3);
		Assert.assertTrue(enc.contentEquals("def"));


	}

}
