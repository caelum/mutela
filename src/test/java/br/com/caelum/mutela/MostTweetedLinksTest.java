package br.com.caelum.mutela;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MostTweetedLinksTest {

	private Link l1, l2, l3, l4;
	private List<Link> links;

	@Before
	public void setUp() {
		l1 = new Link("link1");
		l2 = new Link("link2");
		l3 = new Link("link3");
		l4 = new Link("link4");
		links = Arrays.asList(l1, l2, l3, l1, l2);
	}

	@Test
	public void shouldCountEachEntry() {
		MostTweetedLinks mtl = new MostTweetedLinks(links);
		Assert.assertEquals(2 , mtl.getNumberOfHits(l1));
		Assert.assertEquals(2 , mtl.getNumberOfHits(l2));
		Assert.assertEquals(1 , mtl.getNumberOfHits(l3));
		Assert.assertEquals(0 , mtl.getNumberOfHits(l4));
	}

	@Test
	public void shouldGetMostTweetedLinksInDescendingOrder() {
		MostTweetedLinks mtl = new MostTweetedLinks(links);

		List<Link> sortedLinks = mtl.getSortedLinks();
		Assert.assertEquals(3 , sortedLinks.size());
		Assert.assertEquals(l1 , sortedLinks.get(0));
		Assert.assertEquals(l2 , sortedLinks.get(1));
		Assert.assertEquals(l3 , sortedLinks.get(2));
	}


	@Test
	public void shouldGetMostTweetedLinksInDescendingOrderCapped() {
		MostTweetedLinks mtl = new MostTweetedLinks(links);

		List<Link> sortedLinks = mtl.getSortedLinks(2);
		Assert.assertEquals(2 , sortedLinks.size());
		Assert.assertEquals(l1 , sortedLinks.get(0));
		Assert.assertEquals(l2 , sortedLinks.get(1));
	}
}
