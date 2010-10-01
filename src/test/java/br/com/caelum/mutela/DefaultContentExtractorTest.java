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
		String r  = new Scanner(f).useDelimiter("$$").next();
		f.close();
		return r;

	}

	@Test
	public void testObama() throws IOException {
		// http://reason.com/archives/2009/06/11/baffled-by-the-economy
		test("/obama.txt", "/obama.html", "Baffled by the Economy");
	}

	
	@Test
	public void testSymphony() throws IOException {
		// http://blog.chloeveltman.com/2009/05/two-very-different-symphonies.html
		
		test("/symphony.txt", "/symphony.html", "Two Very Different Symphonies");
		
		System.out.println("blabla a bla".matches(DefaultContentExtractor.divToPElements));
	}
	
	
	@Test
	public void testRamya() throws IOException {
		// http://blogs.mercurynews.com/aei/2009/06/04/ramya-auroprem-joins-cast-of-spelling-bee/
		test("/ramya.txt", "/ramya.html", "Ramya Auroprem joins cast of “Spelling Bee”");
	}
	
	@Test
	public void testAria() throws IOException {
		http://www.sfgate.com/cgi-bin/article.cgi?f=/c/a/2009/06/04/DD7V1806SV.DTL&type=performance
		test("/aria.txt", "/aria.html", "Opera review: 'Tosca' arias pulsate");
	}

	
	private  void test(String txtFile, String  htmlFile, String title) throws IOException {
		String text = readFile(txtFile);
		ContentExtractor extractor = new DefaultContentExtractor(readFile(htmlFile));
		
		assertThat(extractor.content(), startsWith(text));
		assertThat(extractor.title(), Matchers.equalTo(title));
		
	}
}
