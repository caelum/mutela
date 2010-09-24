package br.com.caelum.mutela;

public class Link {

	private final String url;

	public Link(String url) {
		if (url == null)
			throw new NullPointerException();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj instanceof Link == false) {
			return false;
		}

		return url.equals(((Link) obj).url);
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}

	@Override
	public String toString() {
		return String.format("[link %s]", url);
	}

}
