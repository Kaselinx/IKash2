package com.taiwanlife.ikash.backend.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

public class DatasourceChooser extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DatasourceContext.getDatasource();
	}
}
