package br.com.caelum.mutela;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DefaultLinkExtractorTest {

	private DefaultLinkExtractor extractor;

	@Before
	public void setUp() {
		extractor = new DefaultLinkExtractor();
	}

	@Test
	public void shouldExtractPrettySimpleLink() {
		String text = "Esse eh um teste https://secure.com/a bla";

		List<Link> result = extractor.extract(text);
		Assert.assertEquals(1, result.size());

		Assert.assertEquals(new Link("https://secure.com/a"), result.get(0));
	}

	@Test
	public void shouldAvoidExclamationMarks() {
		String text = "Esse eh um teste https://secure.com/a!!! bla";

		List<Link> result = extractor.extract(text);
		Assert.assertEquals(1, result.size());

		Assert.assertEquals(new Link("https://secure.com/a"), result.get(0));
	}


	@Test
	public void shouldReturnMoreThanOneLinkIfTheyExist() {
		String text = "Esse eh um teste https://secure.com/a and http://yahoo.com?q=bla!!! bla";

		List<Link> result = extractor.extract(text);
		Assert.assertEquals(2, result.size());

		Assert.assertEquals(new Link("https://secure.com/a"), result.get(0));
		Assert.assertEquals(new Link("http://yahoo.com?q=bla"), result.get(1));
	}


	@Test
	public void shouldReturnEmptyCollectionWhenNoURLIsFound() {
		String text = "Esse eh um teste https://!!! fpt:// bla";

		List<Link> result = extractor.extract(text);
		Assert.assertEquals(0, result.size());
	}



}
