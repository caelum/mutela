package br.com.caelum.mutela;

public class Image {

	private int width;
	private int height;
	private String src;

	public Image(int width, int height, String src) {
		this.width = width;
		this.height = height;
		this.src = src;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getSrc() {
		return src;
	}
	
	@Override
	public String toString() {
		return this.src;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		return true;
	}
	
	
	
	

}
