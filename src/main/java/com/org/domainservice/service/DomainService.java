package com.org.domainservice.service;

import com.org.domainservice.dto.RoleUpdateRequestDTO;
import com.org.domainservice.repository.DomainTrustGroupRepository;
import com.org.domainservice.dto.DomainsResponseDTO;
import com.org.domainservice.dto.DomainDTO;
import com.org.domainservice.dto.DomainUpdateRequestDTO;
import com.org.domainservice.exception.DatabaseException;
import com.org.domainservice.exception.GenericAPIException;
import com.org.domainservice.model.Domain;
import com.org.domainservice.model.DomainTrustGroup;
import com.org.domainservice.util.Constants;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DomainService {


  @Autowired
  private DomainTrustGroupRepository domainTrustGroupRepository;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${GOOGLE_DRIVE_SERVICE}")
  private String googleAPI;

  @Value("${trustService.endpoint}")
  private String trustServiceEndpoint;

  /**
   * This will read departmentID and OrgCollabID from the request and get all the permissions
   * associated with that Department from trust-service and add that response with domains
   * associated with this department.
   *
   * @param deptId
   * @param orgCollabId
   * @return
   */

  public ResponseEntity<DomainsResponseDTO> getAllDomainsForADepartment(
      final UUID deptId,
      final UUID orgCollabId) {
    ResponseEntity<DomainsResponseDTO> domainData = getDomainData(deptId, orgCollabId);
    List<DomainTrustGroup> domainsTrustGroups =
        domainTrustGroupRepository.findByDeptIdAndOrgCollabId(
            deptId,
            orgCollabId);
    List<DomainDTO> domains = buildUIResponse(domainData, domainsTrustGroups);
    domainData.getBody().setDomains(domains);
    return domainData;
  }

  private List<DomainDTO> buildUIResponse(
      final ResponseEntity<DomainsResponseDTO> domainData,
      final List<DomainTrustGroup> domainsTrustGroups) {
    Map<UUID, String> trustGroupMap = domainData.getBody().getTrustGroups().stream()
        .collect(Collectors.toMap(tg -> tg.getTrustGroupId(), tg -> tg.getName()));
    return domainsTrustGroups.stream().map(domainTG -> {
          Domain domain = domainTG.getDomain();
          return DomainDTO
              .builder()
              .id(domain.getDomainId())
              .domainName(domain.getName())
              .trustScore(domain.getTrustScore())
              .trustGroupId(domainTG.getTgFlavourId())
              .relationship(domain.getRelationship())
              .trustGroupName(trustGroupMap.get(domainTG.getTgFlavourId()))
              .build();
        }
    ).collect(Collectors.toList());
  }

  private ResponseEntity<DomainsResponseDTO> getDomainData(
      final UUID deptId,
      final UUID orgCollabId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(Constants.ORG_COLLAB_HEADER, orgCollabId.toString());
    HttpEntity<?> entity = new HttpEntity<>(headers);
    return restTemplate.exchange(trustServiceEndpoint + "departments/{deptId}/trustGroups",
        HttpMethod.GET, entity, DomainsResponseDTO.class, deptId);
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
      final UUID deptId,
      final UUID orgCollabId) {
    try {
      DomainTrustGroup domain = domainTrustGroupRepository.findByDeptIdAndOrgCollabIdAndDomainId(
          deptId,
          orgCollabId,
          request.getDomainId())
          .orElseThrow(() ->
              new GenericAPIException(
                  "Unable to update the domain now , Seems data is not proper"));
      UUID trustGroupId = request.getTrustGroupId();
      String newRole = getRoleForNewTrustGroup(trustGroupId);
      String domainName = "zemoso.design"; // current functional domain
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
    } catch (Exception e) {
      throw new DatabaseException(
          "Unable to update trust group of a domain" + e.getLocalizedMessage(), e);
    }
  }

  private void updateDomainWithNewRole(
      final String domainName,
      final String newRole) {
    try {
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
      if (!googleServiceResponse.getStatusCode().equals(HttpStatus.OK)) {
        throw new GenericAPIException("Unable to update domain with new Role");
      }
    } catch (Exception e) {
      throw new GenericAPIException("Unable to update domain with new Role", e);
    }
  }

  private String getRoleForNewTrustGroup(final UUID trustGroupId) {
    ResponseEntity<String> response = restTemplate.getForEntity(
        trustServiceEndpoint + "trustGroups/{tgFlavourId}/role",
        String.class,
        trustGroupId);
    return response.hasBody() ? response.getBody() : Constants.UNKNOWN;
  }
}
