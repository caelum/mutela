package br.com.caelum.mutela;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.log4j.Logger;

public class DefaultLinkMaxifier implements LinkMaxifier {

	private static final Logger LOG = Logger.getLogger(DefaultLinkMaxifier.class);
	private HttpClient client;

	public DefaultLinkMaxifier(HttpClient client) {
		this.client = client;
		client.getParams().setParameter("http.protocol.max-redirects", 3);
	}

	@Override
	public Link maxify(Link old) {
		HeadMethod head = new HeadMethod(old.getUrl());
		try {
			LOG.info("connecting " + old.getUrl());
			client.executeMethod(head);
			return new Link(head.getURI().getURI());
		} catch (HttpException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}
