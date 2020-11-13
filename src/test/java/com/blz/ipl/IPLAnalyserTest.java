package com.blz.ipl;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLAnalyserTest {

	private static final String RIGHT_CENSUS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRuns.csv";
	private static final String WRONG_CENSUS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRun.csv";
	private static final String WRONG_TYPE_CENSUS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRuns.pdf";

	static IPLAnalyser ipl;

	@BeforeClass
	public static void obj() {
		ipl = new IPLAnalyser();
	}

	@Test
	public void getNumOfRecords_ShouldReturnCount() {
		try {
			int numOfRecords = ipl.loadBatsmenData(RIGHT_CENSUS_CSV);
			Assert.assertEquals(100, numOfRecords);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}

	//UC1
	@Test
	public void givenSortedOnBattingAverageBatsmenList_ShouldReturnBestAveragedBatsman() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnBattingAverage(RIGHT_CENSUS_CSV);
			IPLBattingAnalysis[] batsmenListCsv = new Gson().fromJson(sortedBatsmenJson, IPLBattingAnalysis[].class);
			assertEquals(83.2, batsmenListCsv[0].avg, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//UC2
	@Test
	public void givenSortedOnBattingStrikeRateBatsmenList_ShouldReturnBestStrikeRatedBatsman() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnBattingStrikeRate(RIGHT_CENSUS_CSV);
			IPLBattingAnalysis[] batsmenListCsv = new Gson().fromJson(sortedBatsmenJson, IPLBattingAnalysis[].class);
			assertEquals(333.33, batsmenListCsv[0].strikeRate, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
