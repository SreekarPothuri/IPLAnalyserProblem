package com.blz.ipl;

import java.io.Reader;
import java.util.List;

public interface ICSVBuilderFactory {

	public <E> List<E> getListFromCsv(Reader reader, Class<E> csvclass) throws IPLAnalyserException;
}
