package com.meafs;

import com.meafs.Back.Charset;
import com.meafs.Back.CharsetProvider;
import com.meafs.Back.CharsetStore;
import com.meafs.Back.l1.Caesar;
import com.meafs.Back.l2.Trithemius;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptorsApplicationTests {

	@Before
	@Test
	public void configure() {
		LinkedList<Charset> charsetSet = (CharsetProvider.getInstance().getAll());
		Caesar test = new Caesar(charsetSet.getFirst().getCharset());
		Trithemius method = new Trithemius(charsetSet.iterator().next().getCharset());
	}

	@Test
	public void caesar(){
		LinkedList<Charset> charsetSet = (CharsetProvider.getInstance().getAll());
		Caesar test = new Caesar(charsetSet.getFirst().getCharset());
		String enc = test.encrypt("123", 3);
		Assert.assertTrue(enc.contentEquals("456"));
	}

	@Test
	public void charsetLoader(){
		LinkedList<Charset> csList = CharsetProvider.getInstance().getAll();
		for (final Charset cset : csList) {
			System.out.println(cset.toString());
		}
	}

}
