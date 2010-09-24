package br.com.caelum.mutela;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultLinkExtractor implements LinkExtractor {

	private static final Pattern PATTERN = Pattern.compile("http[s]?://[\\w\\d#@%/\\.&\\+\\-\\?\\=\\~\\(\\)]+");

	@Override
	public List<Link> extract(String string) {
		List<Link> list = new ArrayList<Link>();

		Matcher m = PATTERN.matcher(string);
		while(m.find()) {
			list.add(new Link(m.group()));			
		}

		return list ;
	}

}
