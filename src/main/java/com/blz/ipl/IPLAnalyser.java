package com.blz.ipl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import com.blz.ipl.IPLAnalyserException.IPLAnalyserExceptionType;
import com.google.gson.Gson;

public class IPLAnalyser {

	public int loadBatsmenData(String csvFilePath) throws IPLAnalyserException {
		if (!(csvFilePath.matches(".*\\.csv$")))
			throw new IPLAnalyserException("Incorrect Type", IPLAnalyserExceptionType.INCORRECT_TYPE);
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICSVBuilderFactory csvBuilder = CSVBuilderFactory.createCsvBuilder();
			List<IPLBattingAnalysis> batsmenList = csvBuilder.getListFromCsv(reader, IPLBattingAnalysis.class);
			return batsmenList.size();
		} catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new IPLAnalyserException("Wrong Delimiter or Header", IPLAnalyserExceptionType.SOME_OTHER_ERRORS);
		}
	}

	public String getSortedBatsmenListOnBattingAverage(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICSVBuilderFactory csvBuilder = CSVBuilderFactory.createCsvBuilder();
			List<IPLBattingAnalysis> batsmenList = csvBuilder.getListFromCsv(reader, IPLBattingAnalysis.class);
			Function<IPLBattingAnalysis, Double> batsmanEntity = record -> record.avg;
			Comparator<IPLBattingAnalysis> censusComparator = Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(batsmenList, censusComparator);
			String sortedStateCensusToJson = new Gson().toJson(batsmenList);
			return sortedStateCensusToJson;
		} catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	
	public String getSortedBatsmenListOnBattingStrikeRate(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICSVBuilderFactory csvBuilder = CSVBuilderFactory.createCsvBuilder();
			List<IPLBattingAnalysis> batsmenList = csvBuilder.getListFromCsv(reader, IPLBattingAnalysis.class);
			Function<IPLBattingAnalysis, Double> batsmanEntity = record -> record.strikeRate;
			Comparator<IPLBattingAnalysis> censusComparator = Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(batsmenList, censusComparator);
			String sortedStateCensusToJson = new Gson().toJson(batsmenList);
			return sortedStateCensusToJson;
		} catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}


	private void sortBatsmenList(List<IPLBattingAnalysis> batsmenList,
			Comparator<IPLBattingAnalysis> censusComparator) {
		for (int i = 0; i < batsmenList.size() - 1; i++) {
			for (int j = 0; j < batsmenList.size() - i - 1; j++) {
				IPLBattingAnalysis sortKey1 = batsmenList.get(j);
				IPLBattingAnalysis sortKey2 = batsmenList.get(j + 1);
				if (censusComparator.compare(sortKey1, sortKey2) < 0) {
					batsmenList.set(j, sortKey2);
					batsmenList.set(j + 1, sortKey1);
				}
			}
		}
	}
}
