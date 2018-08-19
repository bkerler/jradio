package ru.r2cloud.jradio.lrpt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import ru.r2cloud.jradio.meteor.MeteorImage;
import ru.r2cloud.jradio.meteor.MeteorImageTest;

public class LRPTInputStreamTest {

	@Test
	public void readFromStream() throws Exception {
		List<VCDU> data = new ArrayList<>();
		try (LRPTInputStream is = new LRPTInputStream(LRPTInputStreamTest.class.getClassLoader().getResourceAsStream("vcdu.bin"))) {
			assertTrue(is.hasNext());
			data.add(is.next());
		}
		MeteorImage image = new MeteorImage(data.iterator());
		BufferedImage actual = image.toBufferedImage();
		try (InputStream is1 = MeteorImageTest.class.getClassLoader().getResourceAsStream("expected8bitsoft.png")) {
			BufferedImage expected = ImageIO.read(is1);
			for (int i = 0; i < expected.getWidth(); i++) {
				for (int j = 0; j < expected.getHeight(); j++) {
					assertEquals(expected.getRGB(i, j), actual.getRGB(i, j));
				}
			}
		}
	}

}