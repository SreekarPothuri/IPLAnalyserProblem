package com.blz.ipl;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blz.ipl.IPLAnalyserException.IPLAnalyserExceptionType;
import com.google.gson.Gson;

public class IPLAnalyserTest {

	private static final String RIGHT_CENSUS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRuns.csv";
	private static final String WRONG_CENSUS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRun.csv";
	private static final String WRONG_TYPE_CENSUS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRuns.pdf";

	static IPLAnalyser iplbatting;

	@BeforeClass
	public static void obj() {
		iplbatting = new IPLAnalyser();
	}

	@Test
	public void getNumOfRecords_ShouldReturnCount() {
		try {
			int numOfRecords = iplbatting.loadBatsmenData(RIGHT_CENSUS_CSV);
			Assert.assertEquals(100, numOfRecords);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenWrongCsvFile_ShouldThrowIPLAnalyserExceptionOfTypeCensusFileProblem() {
		try {
			new IPLAnalyser().loadBatsmenData(WRONG_CENSUS_CSV);
		} catch (IPLAnalyserException e) {
			assertEquals(IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM, e.exceptionType);
		}
	}

	@Test
	public void givenWrongTypeCsvFile_ShouldThrowIPLAnalyserExceptionOfTypeIncorrectType() {
		try {
			new IPLAnalyser().loadBatsmenData(WRONG_TYPE_CENSUS_CSV);
		} catch (IPLAnalyserException e) {
			assertEquals(IPLAnalyserExceptionType.INCORRECT_TYPE, e.exceptionType);
		}
	}

	//UC1
	@Test
	public void givenSortedOnBattingAverageBatsmenList_ShouldReturnBestAveragedBatsman() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnBattingAverage(RIGHT_CENSUS_CSV);
			IPLBattingAnalysis[] batsmenListCsv = new Gson().fromJson(sortedBatsmenJson, IPLBattingAnalysis[].class);
			assertEquals("MS Dhoni", batsmenListCsv[0].playerName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
