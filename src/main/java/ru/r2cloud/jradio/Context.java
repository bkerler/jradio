package ru.r2cloud.jradio;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores stream metadata. Shared object across different blocks
 *
 */
public class Context {

	private Tag current = null;
	private final Map<String, Tag> tagById = new HashMap<>();
	private Long totalSamples;
	private float sampleRate;
	private int channels = 1;
	private int sampleSizeInBits;
	private LongValueSource currentSample;
	private Boolean softBits;

	public Context() {
		// do nothing
	}

	public Context(Context orig) {
		current = orig.current;
		tagById.putAll(orig.tagById);
		totalSamples = orig.totalSamples;
		sampleRate = orig.sampleRate;
		channels = orig.channels;
		sampleSizeInBits = orig.sampleSizeInBits;
		currentSample = orig.currentSample;
		softBits = orig.softBits;
	}

	public void put(String id, Tag tag) {
		tagById.put(id, tag);
		current = tag;
	}

	public Tag getCurrent() {
		return current;
	}

	public void setCurrent(Tag current) {
		this.current = current;
	}

	public void resetCurrent() {
		current = null;
	}

	public Tag getTag(String id) {
		return tagById.get(id);
	}

	public Map<String, Tag> getTags() {
		return tagById;
	}

	public Long getTotalSamples() {
		return totalSamples;
	}

	public void setTotalSamples(Long totalSamples) {
		this.totalSamples = totalSamples;
	}

	public int getSampleSizeInBits() {
		return sampleSizeInBits;
	}

	public void setSampleSizeInBits(int sampleSizeInBits) {
		this.sampleSizeInBits = sampleSizeInBits;
	}

	public float getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public LongValueSource getCurrentSample() {
		return currentSample;
	}

	public void setCurrentSample(LongValueSource currentSample) {
		this.currentSample = currentSample;
	}
	
	public void setSoftBits(boolean softBits) {
		this.softBits = softBits;
	}
	
	public Boolean getSoftBits() {
		return softBits;
	}

}
