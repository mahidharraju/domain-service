package com.org.domainservice.service;

import com.org.domainservice.dto.DomainDTO;
import com.org.domainservice.dto.DomainUpdateRequestDTO;
import com.org.domainservice.dto.DomainsResponseDTO;
import com.org.domainservice.dto.RoleUpdateRequestDTO;
import com.org.domainservice.dto.TrustGroupUpdateDTO;
import com.org.domainservice.exception.GenericAPIException;
import com.org.domainservice.exception.NoDataFoundException;
import com.org.domainservice.model.Domain;
import com.org.domainservice.model.DomainTrustGroup;
import com.org.domainservice.repository.DomainTrustGroupRepository;
import com.org.domainservice.util.Constants;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
@Qualifier("domainService")
public class DomainService implements IDomainService {


  private DomainTrustGroupRepository domainTrustGroupRepository;

  private RestTemplate restTemplate;

  @Value("${GOOGLE_DRIVE_SERVICE}")
  private String googleAPI;

  @Value("${trustService.endpoint}")
  private String trustServiceEndpoint;

  @Value("${domain}")
  private String domainName;

  public DomainService(DomainTrustGroupRepository domainTrustGroupRepository, RestTemplate restTemplate) {
    this.domainTrustGroupRepository = domainTrustGroupRepository;
    this.restTemplate = restTemplate;
  }

  /**
   * This will read departmentID and OrgCollabID from the request and get all the permissions
   * associated with that Department from trust-service and add that response with domains
   * associated with this department.
   *
   * @param deptId
   * @param orgCollabId
   * @return
   */

  public DomainsResponseDTO getAllDomainsForADepartment(
      final Long deptId,
      final Long orgCollabId) {
    DomainsResponseDTO domainData = getDomainData(deptId, orgCollabId).orElseThrow(
        () -> new GenericAPIException("No domains for given department and collaboration platform"));
    List<DomainTrustGroup> domainsTrustGroups =
        domainTrustGroupRepository.findByDeptIdAndOrgCollabId(
            deptId,
            orgCollabId)
            .orElseThrow(() -> new NoDataFoundException("No Trust groups available with given department"));
    List<DomainDTO> domains = buildUIResponse(domainData, domainsTrustGroups);
    domainData.setDomains(domains);
    return domainData;
  }

  private List<DomainDTO> buildUIResponse(
      final DomainsResponseDTO domainData,
      final List<DomainTrustGroup> domainsTrustGroups) {
    Map<Long, String> trustGroupMap = domainData.getTrustGroups().stream()
        .collect(Collectors.toMap(TrustGroupUpdateDTO::getTrustGroupId, TrustGroupUpdateDTO::getName));
    return domainsTrustGroups.stream().map(domainTG -> {
          Domain domain = domainTG.getDomain();
          return DomainDTO
              .builder()
              .id(domain.getId())
              .domainName(domain.getName())
              .trustScore(domain.getTrustScore())
              .trustGroupId(domainTG.getTgFlavourId())
              .relationship(domain.getRelationship())
              .trustGroupName(trustGroupMap.get(domainTG.getTgFlavourId()))
              .build();
        }
    ).collect(Collectors.toList());
  }

  private Optional<DomainsResponseDTO> getDomainData(
      final Long deptId,
      final Long orgCollabId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(Constants.ORG_COLLAB_HEADER, orgCollabId.toString());
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<DomainsResponseDTO> response = restTemplate.exchange(trustServiceEndpoint + "departments/{deptId}/trustGroups",
        HttpMethod.GET, entity, DomainsResponseDTO.class, deptId);
    return response.hasBody() ? Optional.of(response.getBody()) : Optional.empty();
  }


  /**
   * This Will read Department ID and Organization CollabId and TG FlavourID Domain ID
   * from request and Updates domain with new TG flavour and same time do a rest call
   * to Google API to update the Drive permission
   *
   * @param request
   * @param deptId
   * @param orgCollabId
   * @return
   */


  public DomainDTO updateDomainTrustGroup(
      final DomainUpdateRequestDTO request,
      final Long deptId,
      final Long orgCollabId) {
    DomainTrustGroup domain = domainTrustGroupRepository.findByDeptIdAndOrgCollabIdAndDomainId(
        deptId,
        orgCollabId,
        request.getDomainId())
        .orElseThrow(() ->
            new GenericAPIException(
                "Unable to update the domain now , Seems data is not proper"));
    Long trustGroupId = request.getTrustGroupId();
    String newRole = getRoleForNewTrustGroup(trustGroupId);
    updateDomainWithNewRole(domainName, newRole);
    domain.setTgFlavourId(trustGroupId);
    domain = domainTrustGroupRepository.save(domain);
    return DomainDTO
        .builder()
        .id(domain.getId())
        .domainName(domain.getDomain().getName())
        .trustScore(domain.getDomain().getTrustScore())
        .trustGroupId(domain.getTgFlavourId())
        .relationship(domain.getDomain().getRelationship())
        .build();
  }

  private void updateDomainWithNewRole(
      final String domainName,
      final String newRole) {
    RoleUpdateRequestDTO request = RoleUpdateRequestDTO
        .builder()
        .domain(domainName)
        .role(newRole)
        .build();
    URI googleServiceUrl = URI.create(googleAPI);
    ResponseEntity<String> googleServiceResponse =
        restTemplate.patchForObject(
            googleServiceUrl,
            request,
            ResponseEntity.class);
    if (googleServiceResponse != null && !googleServiceResponse.getStatusCode().equals(HttpStatus.OK)) {
      throw new GenericAPIException("Unable to update domain with new Role");
    }
  }

  private String getRoleForNewTrustGroup(final Long trustGroupId) {
    ResponseEntity<String> response = restTemplate.getForEntity(
        trustServiceEndpoint + "trustGroups/{tgFlavourId}/role",
        String.class,
        trustGroupId);
    return response.hasBody() ? response.getBody() : Constants.UNKNOWN;
  }
}
