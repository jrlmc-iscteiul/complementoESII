package covid;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Hashtable;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CgiLibTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	@Test
	void testUrlDecode() { 
		assertEquals("ola ola", cgi_lib.urlDecode("ola+ola"));	
	}

	@Test
	void testHeader() {
		assertEquals("Content-type: text/html\n\n", cgi_lib.Header());
	}
	
	@Test
	void testHtmlTop() {
		String expected = new String("<html>\n" + 
				"<head>\n" + 
				"<title>\n" + 
				"Hello\n" + 
				"</title>\n" + 
				"</head>\n" + 
				"<body>\n");
		assertEquals(expected, cgi_lib.HtmlTop("Hello"));
	}
	
	@Test
	void testHtmlBot() {
		assertEquals("</body>\n</html>\n", cgi_lib.HtmlBot());
	}
	
	@Test
	void testMethGet() {
		assertEquals(false, cgi_lib.MethGet());
	}
	
	@Test
	void testMethPost() {
		assertEquals(false, cgi_lib.MethPost());
	}
	
	@Test
	void testEnvironment() {
		String expected = new String("<dl compact>\n" + 
				"<dt><b>CONTENT_TYPE</b> <dd>:<i>null</i>:<br>\n" + 
				"<dt><b>CONTENT_LENGTH</b> <dd>:<i>null</i>:<br>\n" + 
				"<dt><b>REQUEST_METHOD</b> <dd>:<i>null</i>:<br>\n" + 
				"<dt><b>QUERY_STRING</b> <dd>:<i>null</i>:<br>\n" + 
				"<dt><b>SERVER_NAME</b> <dd>:<i>null</i>:<br>\n" + 
				"<dt><b>SERVER_PORT</b> <dd>:<i>null</i>:<br>\n" + 
				"<dt><b>SCRIPT_NAME</b> <dd>:<i>null</i>:<br>\n" + 
				"<dt><b>PATH_INFO</b> <dd>:<i>null</i>:<br>\n" + 
				"</dl>\n");
		assertEquals(expected, cgi_lib.Environment());
	}
	
}
