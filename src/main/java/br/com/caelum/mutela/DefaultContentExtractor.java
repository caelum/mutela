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
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultContentExtractor implements ContentExtractor {

	private final String content;

	private Map<Element, Integer> elementToScore = new HashMap<Element, Integer>();
	
	private static Logger log = LoggerFactory.getLogger(DefaultContentExtractor.class);

	// from Readability
	private String unlikelyCandidates = "combx|comment|community|disqus|extra|foot|header|menu|remark|rss|shoutbox|sidebar|sponsor|ad-break|agegate|pagination|pager|popup";
	private String okMaybeItsACandidate = "and|article|body|column|main|shadow";
	private String positive = "article|body|content|entry|hentry|main|page|pagination|post|text|blog|story";
	private String negative = "combx|comment|com-|contact|foot|footer|footnote|masthead|media|meta|outbrain|promo|related|scroll|shoutbox|sidebar|sponsor|shopping|tags|tool|widget";
	private String divToPElements = "<(blockquote|dl|div|ol|p|pre|table|ul)";

	private List<Element> elements;
	

	public DefaultContentExtractor(String content) {
		this.content = content;
		Source source = new Source(this.content);
		source.fullSequentialParse();
		this.elements = source.getAllElements();
		for (Iterator<Element> i = elements.iterator(); i.hasNext();) {
			Element e = i.next();
			int weight = getValueForTag(e.getStartTag());
			
			if (matches(e.getAttributeValue("class"), positive)) {
				weight +=50;
			}
			if (matches(e.getAttributeValue("class"), negative)) {
				weight -=50;
			}
			if (matches(e.getAttributeValue("class"), okMaybeItsACandidate)) {
				weight +=3;
			}
			if (matches(e.getAttributeValue("class"), unlikelyCandidates)) {
				weight -=10;
			}
			
			if(canBeContent(e)) {
				weight += e.toString().split(",").length;
				addScore(e.getParentElement(), weight);
				i.remove();
			} else {
				addScore(e, weight);
			}
			System.out.println(weight);
		}
		
		Collections.sort(elements, new Comparator<Element>() {
			public int compare(Element o1, Element o2) {
				return elementToScore.get(o1) - elementToScore.get(o2);
			}
		});
		
		Collections.reverse(elements);
		
		System.out.println();
		
		System.out.println(elements.size());
		for(Element e : elements.subList(0, 3)) {
			System.out.println("========================");
			System.out.println(elementToScore.get(e));
			System.out.println(e);
		}
	}

	private void addScore(Element element, int weight) {
		if(elementToScore.containsKey(element)) {
			elementToScore.put(element, elementToScore.get(element) + weight);
		}
		else {
			elementToScore.put(element, weight);
		}
	}

	private int getValueForTag(StartTag tag) {
		 String name = tag.getName().toUpperCase();
		 if(name.equals("DIV")) return 5;
		 if("PRE TD BLOCKQUOTE".contains(name)) return 3;
		 if("ADDRESS OL UL DL DD DT LI FORM".contains(name)) return -3;
		 if("H1 H2 H3 H4 H5 H6 TH".contains(name)) return -5;
		return 0;
	}

	private boolean canBeContent(Element e) {
		return e.getName() == HTMLElementName.P;//  || !e.getContent().toString().matches(divToPElements);
	}

	private boolean matches(String value, String regex) {
		if(value == null) return false;
		return value.matches(regex);
	}

	private static String getTitle(Source source) {
		Element titleElement = source.getFirstElement(HTMLElementName.TITLE);
		if (titleElement == null)
			return null;
		// TITLE element never contains other tags so just decode it collapsing
		// whitespace:
		return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
	}

	private static String getMetaValue(Source source, String key) {
		for (int pos = 0; pos < source.length();) {
			StartTag startTag = source.getNextStartTag(pos, "name", key, false);
			if (startTag == null)
				return null;
			if (startTag.getName() == HTMLElementName.META)
				return startTag.getAttributeValue("content"); // Attribute
																// values are
																// automatically
																// decoded
			pos = startTag.getEnd();
		}
		return null;
	}

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String title() {
		// TODO Auto-generated method stub
		return null;
	}

}
