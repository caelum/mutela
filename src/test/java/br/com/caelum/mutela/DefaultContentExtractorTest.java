package br.com.caelum.mutela;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.startsWith;

import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
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


	@Test()
	@Ignore
	public void testSymphony() throws IOException {
		// http://blog.chloeveltman.com/2009/05/two-very-different-symphonies.html

		test("/symphony.txt", "/symphony.html", "Two Very Different Symphonies");
	}


	@Test
	public void testRamya() throws IOException {
		// http://blogs.mercurynews.com/aei/2009/06/04/ramya-auroprem-joins-cast-of-spelling-bee/
		test("/ramya.txt", "/ramya.html", "Ramya Auroprem joins cast of �Spelling Bee�");
	}


	@Test
	public void testBlogCaelum() throws IOException {
		// http://blog.caelum.com.br/2010/07/21/entao-voce-quer-ser-um-arquiteto-java/
		test("/arquiteto.txt", "/arquiteto.html", "Ent�o voc� quer ser um arquiteto Java?�");
	}

	@Test
	public void testNoticiaUOL() throws IOException {
		// http://noticias.uol.com.br/bbc/2010/10/03/fiel-da-balanca-marina-propoe-plenaria-no-pv-sobre-2-turno.jhtm
		test("/uol.txt", "/uol.html", "Fiel da balan�a, Marina prop�e plen�ria no PV sobre 2� turno�");
	}


	@Test
	public void testNoticiaGlobo() throws IOException {
		// http://g1.globo.com/especiais/eleicoes-2010/noticia/2010/10/marta-suplicy-se-diz-surpresa-com-desempenho-de-aloysio-nunes.html
		test("/globo.txt", "/globo.html", "Marta Suplicy se diz surpresa com desempenho de Aloysio Nunes�");
	}

	@Test
	public void testAria() throws IOException {
		//http://www.sfgate.com/cgi-bin/article.cgi?f=/c/a/2009/06/04/DD7V1806SV.DTL&type=performance
		test("/aria.txt", "/aria.html", "Opera review: 'Tosca' arias pulsate");
	}


	private  void test(String txtFile, String  htmlFile, String title) throws IOException {
		String text = readFile(txtFile);
		ContentExtractor extractor = new DefaultContentExtractor(readFile(htmlFile));

		assertThat(extractor.content(), startsWith(text.substring(0, 15)));
		assertThat(extractor.title(), Matchers.equalTo(title));
	}
}
