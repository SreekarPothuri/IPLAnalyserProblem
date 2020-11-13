package com.blz.ipl;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import com.blz.ipl.IPLAnalyserException.IPLAnalyserExceptionType;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCsvBuilder implements ICSVBuilderFactory {

	public <E> CsvToBean<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws RuntimeException {
		CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
		csvToBeanBuilder.withType(csvClass);
		csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
		CsvToBean<E> csvToBean = csvToBeanBuilder.build();
		return csvToBean;
	}

	public <E> List<E> getListFromCsv(Reader reader, Class<E> csvClass) throws IPLAnalyserException {
		try {

			CsvToBean<E> csvToBean = this.getCSVFileIterator(reader, csvClass);
			List<E> censusList = csvToBean.parse();
			return censusList;
		} catch (IllegalStateException e) {
			throw new IPLAnalyserException("Parsing Error", IPLAnalyserExceptionType.PARSE_ERROR);
		} catch (RuntimeException e) {
			throw new IPLAnalyserException("Wrong Delimiter or Header", IPLAnalyserExceptionType.SOME_OTHER_ERRORS);
		}
	}
}
