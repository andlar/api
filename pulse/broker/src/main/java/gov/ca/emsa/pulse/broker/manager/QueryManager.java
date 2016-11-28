package gov.ca.emsa.pulse.broker.manager;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import gov.ca.emsa.pulse.auth.user.CommonUser;
import gov.ca.emsa.pulse.broker.dto.PatientRecordDTO;
import gov.ca.emsa.pulse.broker.dto.QueryDTO;
import gov.ca.emsa.pulse.broker.dto.QueryLocationMapDTO;
import gov.ca.emsa.pulse.broker.saml.SAMLInput;
import gov.ca.emsa.pulse.common.domain.PatientSearch;

public interface QueryManager extends CachedDataManager {
	public QueryDTO getById(Long id);
	public List<QueryDTO> getAllQueriesForUser(String userKey);
	public List<QueryDTO> getActiveQueriesForUser(String userKey);
	public String getQueryStatus(Long queryId);
	public QueryDTO updateQuery(QueryDTO toUpdate);
	public QueryDTO createQuery(QueryDTO toCreate);
	public void delete(Long queryId);
	public QueryDTO cancelQueryToLocation(Long queryId, Long orgId);
	public QueryLocationMapDTO createOrUpdateQueryLocation(QueryLocationMapDTO toUpdate);

	public PatientRecordDTO getPatientRecordById(Long patientRecordId);
	public QueryDTO queryForPatientRecords(SAMLInput samlMessage, PatientSearch toSearch, QueryDTO query, CommonUser user) throws JsonProcessingException;
	public PatientRecordDTO addPatientRecord(PatientRecordDTO record);
	public void removePatientRecord(Long prId);
	public QueryDTO updateQueryStatusFromLocations(Long queryId);
}
