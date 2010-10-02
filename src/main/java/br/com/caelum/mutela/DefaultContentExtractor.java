package br.com.caelum.mutela;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.HTMLElements;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultContentExtractor implements ContentExtractor {

	private final String fullContent;

	private Map<Element, Integer> elementToScore = new HashMap<Element, Integer>();

	private static Logger log = LoggerFactory.getLogger(DefaultContentExtractor.class);

	// adaptado do Readability
	public static final String unlikelyCandidates = "(?i)(?m).*[combx|comment|community|disqus|extra|foot|header|menu|remark|rss|shoutbox|sidebar|sponsor|agegate|pagination|pager|popup].*";
	public static final String okMaybeItsACandidate = "(?i)(?m).*[and|article|body|column|main|shadow].*";
	public static final String positive = "(?i)(?m).*[article|body|content|entry|hentry|main|page|pagination|post|text|blog|story].*";
	public static final String negative = "(?i)(?m).*[combx|comment|com-|contact|foot|footer|footnote|masthead|media|meta|outbrain|promo|related|scroll|shoutbox|sidebar|sponsor|shopping|tags|tool|widget].*";
	public static final String divToPElements = "(?i)(?m).*<[a|blockquote|dl|div|ol|p|pre|table|ul].*";

	private List<Element> elements;

	private String content;

	public DefaultContentExtractor(String fullContent) {
		this.fullContent = fullContent;
		Source source = new Source(this.fullContent);
		source.fullSequentialParse();
		this.elements = source.getAllElements();

		for (Iterator<Element> i = elements.iterator(); i.hasNext();) {

			// pontuamos apenas P, TD, PRE e tambem DIVs que poderiam ser PRE

			// adiciona os pontos pro pai dele, e tambem m etade pro av� dele.
			Element e = i.next();

			if (canBeContent(e)) {
				int weight = getValueForTag(e.getStartTag());

				if (matches(e.getAttributeValue("class"), positive) || matches(e.getAttributeValue("id"), positive)) {
					weight += 50;
				}
				if (matches(e.getAttributeValue("class"), negative) || matches(e.getAttributeValue("id"), negative)) {
					weight -= 50;
				}
				if (matches(e.getAttributeValue("class"), okMaybeItsACandidate)) {
					weight += 10;
				}
				if (matches(e.getAttributeValue("class"), unlikelyCandidates)) {
					weight -= 10;
				}

				String inner = e.getTextExtractor().setIncludeAttributes(true).toString();
				if (inner.length() < 25)
					continue; // mto pequeno

				weight += (inner.split(",|\\.|;").length + 1) * 2;

				int links = 0;
				for (Element l : e.getAllElements("a")) {
					links += l.toString().length();
				}

				if (weight > 0)
					weight *= (double) (1 - ((double) links / (double) e.toString().length()));

				// dono recebe pontuacao
				if (e.getParentElement() != null)
					addScore(e.getParentElement(), weight);

				System.out.println(weight);
				// avo fica com a metade
				// if (e.getParentElement().getParentElement() != null)
				// addScore(e.getParentElement().getParentElement(), weight /
				// 2);
			}

		}

		Collections.sort(elements, new Comparator<Element>() {
			public int compare(Element o1, Element o2) {
				return getScore(o1) - getScore(o2);
			}

			private int getScore(Element e) {
				return elementToScore.get(e) == null ? 0 : elementToScore.get(e);
			}
		});

		Collections.reverse(elements);

		log.info("Total de " + elements.size() + " elementos ");

		// imprime os 3 "primeiros"
		for (Element e : elements.subList(0, Math.min(3, elements.size()))) {
			log.info("");
			log.info("======================== Score deste: " + elementToScore.get(e));
			log.info(e.getTextExtractor().setIncludeAttributes(true).toString());
			log.info("======================== Raw html: ");
			log.info(e.toString());

		}

		this.content = elements.get(0).getTextExtractor().setIncludeAttributes(true).toString();
	}

	private void addScore(Element element, int weight) {
		if (elementToScore.containsKey(element)) {
			elementToScore.put(element, elementToScore.get(element) + weight);
		} else {
			elementToScore.put(element, weight);
		}
	}

	private int getValueForTag(StartTag tag) {
		String name = tag.getName().toUpperCase();
		if (name.equals("DIV"))
			return 10;
		if ("PRE TD BLOCKQUOTE".contains(name))
			return 5;
		if ("ADDRESS OL UL DL DD DT LI FORM".contains(name))
			return -5;
		if ("H1 H2 H3 H4 H5 H6 TH".contains(name))
			return -10;
		return 0;
	}

	private boolean canBeContent(Element e) {
		return e.getName().toLowerCase() == HTMLElementName.P || e.getName().toLowerCase() == HTMLElementName.PRE;
		// || (e.getName() == HTMLElementName.DIV &&
		// !e.getContent().toString().matches(divToPElements));
	}

	private boolean matches(String value, String regex) {
		if (value == null)
			return false;
		return value.matches(regex);
	}

	@Override
	public String content() {
		return this.content;
	}

	@Override
	public String title() {
		// TODO Auto-generated method stub
		return null;
	}

}
