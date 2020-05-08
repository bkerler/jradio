package ru.r2cloud.jradio.quetzal1;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Method;
import ru.r2cloud.jradio.AssertJson;
import ru.r2cloud.jradio.fec.ViterbiTest;

public class Quetzal1BeaconTest {

	@Test
	public void testTelemetry() throws Exception {
		byte[] data = ViterbiTest.hexStringToByteArray("404040404040604040404040406103F002026600515545545A414C31030C1B0000000153965353000000000044B95302BB0995000F09DA53E103AFDE00EDDD00020000004400000000101F1F668C83800080008000252315B61314242514B3110C0000000F000003CD0000000A183C1E32461E0101013C041E1E01051E010301030055564720612047756174656D616C612C205349207365207075646F");
		Quetzal1Beacon result = new Quetzal1Beacon();
		result.readBeacon(data);
		AssertJson.assertObjectsEqual("Quetzal1Beacon.json", result);
	}

	@Test
	public void testPojo() {
		assertPojoMethodsFor(Quetzal1Beacon.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Telemetry.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(RamParams.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Cdhs.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Eps.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Adcs.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(HeaterStatus.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Ina260.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(GeneralFlags.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}
}
