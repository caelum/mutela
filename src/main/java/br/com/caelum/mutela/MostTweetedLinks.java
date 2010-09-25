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
	private final List<Link> uniqueLinksSortedByCardinalityDesc;

	@SuppressWarnings("unchecked")
	public MostTweetedLinks(List<Link> links) {
		if (links == null)
			throw new NullPointerException();

		this.links = links;
		this.cardinalityMap = CollectionUtils.getCardinalityMap(this.links);
		this.uniqueLinksSortedByCardinalityDesc = new ArrayList<Link>(this.cardinalityMap.keySet());

		Collections.sort(this.uniqueLinksSortedByCardinalityDesc, new Comparator<Link>() {
			public int compare(Link l1, Link l2) {
				return cardinalityMap.get(l1).compareTo(cardinalityMap.get(l2));
			}
		});

		Collections.reverse(this.uniqueLinksSortedByCardinalityDesc);
	}

	public int getNumberOfHits(Link link) {
		if (!cardinalityMap.containsKey(link)) return 0;
		return cardinalityMap.get(link);
	}

	public List<Link> getSortedLinks() {
		return getSortedLinks(uniqueLinksSortedByCardinalityDesc.size());
	}

	public List<Link> getSortedLinks(int n) {
		// defensive?
		return uniqueLinksSortedByCardinalityDesc.subList(0, n);
	}
}
