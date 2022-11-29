package com.taiwanlife.ikash.backend.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiwanlife.ikash.backend.configuration.DatasourceScope;
import com.taiwanlife.ikash.backend.entity.ikash.BatchJob;
import com.taiwanlife.ikash.backend.repository.BatchJobRepository;
import com.taiwanlife.ikash.backend.service.BatchJobService;

@Service
public class BatchJobImpl implements BatchJobService {
	
	private BatchJobRepository batchJobRepository;
	
	@Autowired
	public BatchJobImpl(BatchJobRepository theBatchJobRepository ) {
		this.batchJobRepository = theBatchJobRepository;
	}

	@DatasourceScope(scope="IKash")
	@Override
	public List<BatchJob> getAllBatchItem() {
		return batchJobRepository.getAllBatchItem();
	}

}
