package ru.r2cloud.jradio.dstar1;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;

import ru.r2cloud.jradio.AssertJson;
import ru.r2cloud.jradio.fec.ccsds.UncorrectableException;
import ru.r2cloud.jradio.mobitex.Header;

public class Dstar1BeaconTest {

	@Test
	public void testPartialRecovery() throws IOException, UncorrectableException {
		byte[] data = new byte[] { 113, 6, 79, -91, -110, -10, -108, -79, -53, 79, -127, 102, -119, 58, -112, -2, 117, 83, -116, 97, 52, 25, -17, 111, -90, -7, -4, -17, 125, -125, -12, 17, 59, -58, 19, 80, 8, -51, 117, -1, -106, -46, 94, -53, -37, -56, 76, 127, -106, 121, 117, 14, 90, -54, -48, 13, -41, -60, -51, -70, 126, 57, -60, -67, -102, 12, -31, -4, 7, -66, 43, 100, 81, -55, -94, -40, -33, 2, -89, -51, -59, -104, -116, -32, 2, 119, -55, -24, -35, -84, -28, 65, 120, 79, -39, 53, -65, 39,
				-90, 96, 17, -108, 104, 127, 5, -114, -9, -101, -34, 116, 59, -102, -32, 90, -82, -51, 70, -52, 107, -32, 114, -96, -29, -106, 88, 102, 40, -38, 52, 11, -62, -51, 65, 25, -100, -43, 35, 58, 35, 31, 11, 34, 70, -1, 103, 12, 66, 77, -41, -11, -7, 111, -44, -112, -115, -68, 105, 101, -15, -75, -70, 114, -80, -125, -100, 12, -19, 4, -24, -30, 98, -31, -52, -109, -54, 115, 31, 72, -106, -81, 47, 33, -94, 8, 62, 79, 112, -12, 64, 69 };
		Dstar1Beacon result = new Dstar1Beacon();
		result.readExternal(data);
		AssertJson.assertObjectsEqual("Dstar1Beacon-partial.json", result);
	}

	@Test(expected = UncorrectableException.class)
	public void testNoneRecovered() throws IOException, UncorrectableException {
		byte[] data = new byte[190];
		data[0] = 113;
		data[1] = 6;
		data[2] = 79;
		Dstar1Beacon result = new Dstar1Beacon();
		result.readExternal(data);
	}

	@Test
	public void testPojo() {
		assertThat(Dstar1Beacon.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
		assertThat(PayloadData.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
		assertThat(Header.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
	}

}
