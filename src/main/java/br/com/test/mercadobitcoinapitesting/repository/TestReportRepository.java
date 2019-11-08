package br.com.test.mercadobitcoinapitesting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.test.mercadobitcoinapitesting.domain.TestReport;

@Repository
public interface TestReportRepository extends MongoRepository<TestReport, String>{

}
