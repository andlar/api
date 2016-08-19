package gov.ca.emsa.pulse.broker.dto;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.emsa.pulse.broker.manager.impl.JSONUtils;
import gov.ca.emsa.pulse.broker.manager.impl.PatientQueryService;
import gov.ca.emsa.pulse.common.domain.Address;
import gov.ca.emsa.pulse.common.domain.AlternateCareFacility;
import gov.ca.emsa.pulse.common.domain.Document;
import gov.ca.emsa.pulse.common.domain.Organization;
import gov.ca.emsa.pulse.common.domain.Patient;
import gov.ca.emsa.pulse.common.domain.PatientOrganizationMap;
import gov.ca.emsa.pulse.common.domain.PatientRecord;
import gov.ca.emsa.pulse.common.domain.PatientSearch;
import gov.ca.emsa.pulse.common.domain.Query;
import gov.ca.emsa.pulse.common.domain.QueryOrganization;

public class DtoToDomainConverter {
	private static final Logger logger = LogManager.getLogger(DtoToDomainConverter.class);

	public static Patient convert(PatientDTO dtoObj) {
		Patient result = new Patient();
		result.setId(dtoObj.getId());
		result.setGivenName(dtoObj.getGivenName());
		result.setFamilyName(dtoObj.getFamilyName());
		result.setGender(dtoObj.getGender());
		result.setDateOfBirth(dtoObj.getDateOfBirth());
		result.setPhoneNumber(dtoObj.getPhoneNumber());
		result.setSsn(dtoObj.getSsn());
		result.setLastRead(dtoObj.getLastReadDate());
		
		if(dtoObj.getAcf() != null) {
			AlternateCareFacility acf = new AlternateCareFacility();
			acf.setId(dtoObj.getAcf().getId());
			acf.setName(dtoObj.getAcf().getName());
			if(dtoObj.getAcf().getAddress() != null)  {
				AddressDTO acfAddrDto = dtoObj.getAcf().getAddress();
				Address acfAddr = convert(acfAddrDto);
				acf.setAddress(acfAddr);
			}
			result.setAcf(acf);
		}
		
		if(dtoObj.getAddress() != null) {
			Address addr = convert(dtoObj.getAddress());
			result.setAddress(addr);
		}
		
		if(dtoObj.getOrgMaps() != null && dtoObj.getOrgMaps().size() > 0) {
			for(PatientOrganizationMapDTO orgMapDto : dtoObj.getOrgMaps()) {
				PatientOrganizationMap orgMap = DtoToDomainConverter.convert(orgMapDto);
				result.getOrgMaps().add(orgMap);
			}
		}
		
		return result;
	}
	
	public static PatientOrganizationMap convert(PatientOrganizationMapDTO dto) {	
		PatientOrganizationMap result = new PatientOrganizationMap();
		result.setId(dto.getId());
		result.setPatientId(dto.getPatientId());
		if(dto.getOrg() != null) {
			result.setOrganization(DtoToDomainConverter.convert(dto.getOrg()));
		} else {
			Organization org = new Organization();
			org.setId(dto.getOrganizationId());
			result.setOrganization(org);
		}
		result.setDocumentsQuerySuccess(dto.getDocumentsQuerySuccess());
		result.setDocumentsQueryStatus(dto.getDocumentsQueryStatus());
		result.setDocumentsQueryStart(dto.getDocumentsQueryStart());
		result.setDocumentsQueryEnd(dto.getDocumentsQueryEnd());

		if(dto.getDocuments() != null && dto.getDocuments().size() > 0) {
			for(DocumentDTO docDto : dto.getDocuments()) {
				Document doc = DtoToDomainConverter.convert(docDto);
				result.getDocuments().add(doc);
			}
		}
		return result;
	}
	
	public static Address convert(AddressDTO addressDto){
		Address address = new Address();
		address.setId(addressDto.getId());
		address.setCity(addressDto.getCity());
		address.setCountry(address.getCountry());
		address.setState(addressDto.getState());
		address.setStreet1(addressDto.getStreetLineOne());
		address.setStreet2(addressDto.getStreetLineTwo());
		address.setZipcode(addressDto.getZipcode());
		address.setCountry(addressDto.getCountry());
		return address;
	}
	
	public static AlternateCareFacility convert(AlternateCareFacilityDTO acfDto){
		AlternateCareFacility acf = new AlternateCareFacility();
		if(acfDto.getAddress() != null){
			acf.setAddress(convert(acfDto.getAddress()));
		}
		acf.setId(acfDto.getId());
		acf.setName(acfDto.getName());
		acf.setPhoneNumber(acfDto.getPhoneNumber());
		acf.setLastRead(acfDto.getLastReadDate());
		return acf;
	}
	
	public static Query convert(QueryDTO queryDto){
		Query query = new Query();
		query.setId(queryDto.getId());
		query.setStatus(queryDto.getStatus());
		query.setLastRead(queryDto.getLastReadDate());

		try {
			ObjectMapper termReader = new ObjectMapper();
			PatientSearch terms = termReader.readValue(queryDto.getTerms(), PatientSearch.class);
			query.setTerms(terms);
		} catch(IOException ioex) {
			logger.error("Could not read " + queryDto.getTerms() + " as JSON.");
		}
		
		query.setUserToken(queryDto.getUserId());
		for(QueryOrganizationDTO qOrgDto : queryDto.getOrgStatuses()){
			QueryOrganization qOrg = DtoToDomainConverter.convert(qOrgDto);
			query.getOrgStatuses().add(qOrg);
		}
		return query;
	}
	
	public static QueryOrganization convert(QueryOrganizationDTO qOrgDto){
		QueryOrganization qOrg = new QueryOrganization();
		qOrg.setId(qOrgDto.getId());
		
		if(qOrgDto.getOrg() != null) {
			qOrg.setOrg(convert(qOrgDto.getOrg()));
		}
		
		qOrg.setQueryId(qOrgDto.getQueryId());
		for(PatientRecordDTO prDto : qOrgDto.getResults()){
			PatientRecord pr = DtoToDomainConverter.convert(prDto);
			qOrg.getResults().add(pr);
		}		
		qOrg.setStartDate(qOrgDto.getStartDate());
		qOrg.setEndDate(qOrgDto.getEndDate());
		qOrg.setStatus(qOrgDto.getStatus());
		qOrg.setSuccess(qOrgDto.getSuccess());
		return qOrg;
	}
	
	public static PatientRecord convert(PatientRecordDTO prDto){
		PatientRecord pr = new PatientRecord();
		pr.setId(prDto.getId());
		pr.setSsn(prDto.getSsn());
		pr.setGivenName(prDto.getGivenName());
		pr.setFamilyName(prDto.getFamilyName());
		pr.setGender(prDto.getGender());
		pr.setPhoneNumber(prDto.getPhoneNumber());
		pr.setDateOfBirth(prDto.getDateOfBirth());
		return pr;
	}
	
	public static Organization convert(OrganizationDTO orgDto){
		Organization org = new Organization();
		org.setId(orgDto.getId());
		org.setName(orgDto.getName());
		org.setAdapter(orgDto.getAdapter());
		org.setActive(orgDto.isActive());
		org.setOrganizationId(orgDto.getOrganizationId());
		if(orgDto.getCertificationKey() != null){
			org.setCertificationKey(orgDto.getCertificationKey());
		}
		if(orgDto.getEndpointUrl() != null){
			org.setEndpointUrl(orgDto.getEndpointUrl());
		}
		if(orgDto.getIpAddress() != null){
			org.setIpAddress(orgDto.getIpAddress());
		}
		if(orgDto.getUsername() != null){
			org.setUsername(orgDto.getUsername());
		}
		if(orgDto.getPassword() != null){
			org.setPassword(orgDto.getPassword());
		}
		return org;
	}
	
	public static Document convert(DocumentDTO dtoObj) {
		Document result = new Document();
		result.setId(dtoObj.getId()+"");
		result.setName(dtoObj.getName());
		result.setCached(dtoObj.getContents() != null && dtoObj.getContents().length > 0);
		result.setOrgMapId(dtoObj.getPatientOrgMapId());
		return result;
	}
}
