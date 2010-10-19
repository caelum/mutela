package br.com.caelum.mutela;

import java.util.Collection;

public interface ContentExtractor {

	String content();

	String title();
	
	Collection<Image> images();

}
