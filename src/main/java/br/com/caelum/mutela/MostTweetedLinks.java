package br.com.caelum.mutela;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

public class MostTweetedLinks {

	private final List<Link> links;
	private final Map<Link, Integer> cardinalityMap;
	//private final List<Link> uniqueLinksSortedByCardinalityDesc;

	@SuppressWarnings("unchecked")
	public MostTweetedLinks(List<Link> links) {
		if (links == null)
			throw new NullPointerException();

		this.links = links;
		this.cardinalityMap = CollectionUtils.getCardinalityMap(this.links);
		
	}

	public int getNumberOfHits(Link link) {
		if (!cardinalityMap.containsKey(link)) return 0;
		return cardinalityMap.get(link);
	}
}
