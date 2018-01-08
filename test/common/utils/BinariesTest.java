package common.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinariesTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testToHex() {

		assertThat("", Binaries.toHexString("".getBytes(StandardCharsets.UTF_8)), is(""));
		assertThat("", Binaries.toHexString("0123456789".getBytes(StandardCharsets.UTF_8)), is("30313233343536373839"));
		assertThat("", Binaries.toHexString("abcdefghijklmnopqrstuvwxyz".getBytes(StandardCharsets.UTF_8)), is("6162636465666768696a6b6c6d6e6f707172737475767778797a"));
		assertThat("", Binaries.toHexString("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes(StandardCharsets.UTF_8)), is("4142434445464748494a4b4c4d4e4f505152535455565758595a"));
	}
}