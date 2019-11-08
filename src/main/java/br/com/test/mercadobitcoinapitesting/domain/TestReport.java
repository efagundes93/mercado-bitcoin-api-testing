package br.com.test.mercadobitcoinapitesting.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "testReport")
public class TestReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7232605933156230816L;
	
	@Id
	private String executionId;
	private LocalDateTime startedAt;
	private LocalDateTime finishedAt;
	private String testName;
	private String status;
	private String parameter;
	
	public TestReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the executionId
	 */
	public String getExecutionId() {
		return executionId;
	}

	/**
	 * @param executionId the executionId to set
	 */
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	/**
	 * @return the startedAt
	 */
	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	/**
	 * @param startedAt the startedAt to set
	 */
	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	/**
	 * @return the finishedAt
	 */
	public LocalDateTime getFinishedAt() {
		return finishedAt;
	}

	/**
	 * @param finishedAt the finishedAt to set
	 */
	public void setFinishedAt(LocalDateTime finishedAt) {
		this.finishedAt = finishedAt;
	}

	/**
	 * @return the testName
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * @param testName the testName to set
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((executionId == null) ? 0 : executionId.hashCode());
		result = prime * result + ((finishedAt == null) ? 0 : finishedAt.hashCode());
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((testName == null) ? 0 : testName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TestReport))
			return false;
		TestReport other = (TestReport) obj;
		if (executionId == null) {
			if (other.executionId != null)
				return false;
		} else if (!executionId.equals(other.executionId))
			return false;
		if (finishedAt == null) {
			if (other.finishedAt != null)
				return false;
		} else if (!finishedAt.equals(other.finishedAt))
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (startedAt == null) {
			if (other.startedAt != null)
				return false;
		} else if (!startedAt.equals(other.startedAt))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (testName == null) {
			if (other.testName != null)
				return false;
		} else if (!testName.equals(other.testName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestReport [executionId=" + executionId + ", startedAt=" + startedAt + ", finishedAt=" + finishedAt
				+ ", testName=" + testName + ", status=" + status + ", parameter=" + parameter + "]";
	}
}
