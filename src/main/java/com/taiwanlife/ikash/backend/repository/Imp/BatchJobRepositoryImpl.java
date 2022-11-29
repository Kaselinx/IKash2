package com.taiwanlife.ikash.backend.repository.Imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taiwanlife.ikash.backend.configuration.DatasourceScope;
import com.taiwanlife.ikash.backend.entity.acs.ADM_MENU;
import com.taiwanlife.ikash.backend.entity.ikash.BatchJob;
import com.taiwanlife.ikash.backend.repository.BatchJobRepository;

@Repository
public class BatchJobRepositoryImpl implements BatchJobRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public BatchJobRepositoryImpl(EntityManager _entityManager) {
		this.entityManager = _entityManager;
	}

	@Override
	@DatasourceScope(scope ="IKash")
	public List<BatchJob> getAllBatchItem() {
		Query theQuery = entityManager.createQuery("from BatchJob" , BatchJob.class);
		List<BatchJob> jobs = theQuery.getResultList();
		return jobs;
	}

}
