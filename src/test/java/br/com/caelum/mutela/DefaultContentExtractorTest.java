package br.com.caelum.mutela;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.hamcrest.Matchers;
import org.junit.Test;

public class DefaultContentExtractorTest {

	public String readFile(String file) throws IOException {
		InputStream f = (Class.class.getResourceAsStream(file));
		System.out.println(f);
		String r  = new Scanner(f).useDelimiter("$$").next();
		f.close();
		return r;

	}

	@Test
	public void testObama() throws IOException {
		// http://reason.com/archives/2009/06/11/baffled-by-the-economy
		test("/obama.txt", "/obama.html", "Baffled by the Economy");
	}

	private  void test(String txtFile, String  htmlFile, String title) throws IOException {
		String text = readFile(txtFile);
		ContentExtractor extractor = new DefaultContentExtractor(readFile(htmlFile));
		
		assertThat(extractor.content(), startsWith(text));
		assertThat(extractor.title(), Matchers.equalTo(title));
		
	}
}
