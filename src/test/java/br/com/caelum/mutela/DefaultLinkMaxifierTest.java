package br.com.caelum.mutela;

import org.apache.commons.httpclient.HttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultLinkMaxifierTest {

	LinkMaxifier maxifier;
	HttpClient client;

	@Before
	public void setUp() {
		client = new HttpClient();
		maxifier = new DefaultLinkMaxifier(client);
	}

	@Test
	public void shouldBeAbleToTranslateBitly() {
		Assert.assertEquals(new Link("http://www.caelum.com.br/"), maxifier.maxify(new Link("http://bit.ly/dvslYG")));
	}

	@Test
	public void shouldBeAbleToTranslateBitlyWithDirectory() {
		Assert.assertEquals(new Link("http://www.guj.com.br/posts/list/219000.java"),
				maxifier.maxify(new Link("http://bit.ly/ci70VU")));
	}

	@Test
	public void shouldNotModifyLinkThatDoesNotRedirect() {
		Assert.assertEquals(new Link("http://www.caelum.com.br/"),
				maxifier.maxify(new Link("http://www.caelum.com.br/")));
	}
}
