package com.leverx.services;

import com.leverx.model.Position;
import com.leverx.model.dto.requests.PositionRequest;
import com.leverx.model.dto.responses.PositionResponse;
import com.leverx.model.utils.convertors.PositionConvertor;
import com.leverx.repositories.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class PositionService {

  private final PositionRepository positionRepository;
  private final PositionConvertor positionConvertor;

  public List<PositionResponse> findAll() {
    List<Position> positions = positionRepository.findAll();
    return positionConvertor.convertToListPositionResponse(positions);
  }

  public PositionResponse create(PositionRequest request) {
    Position position = positionConvertor.convertRequestToPosition(request);
    Position savedProject = positionRepository.save(position);
    return positionConvertor.convertPositionToResponse(savedProject);
  }

  public PositionResponse update(PositionRequest request, Long positionId) {
    Position position = positionConvertor.convertRequestToPosition(request, positionId);
    Position updatedPosition = positionRepository.save(position);
    return positionConvertor.convertPositionToResponse(updatedPosition);
  }

  public void delete(Long positionId) {
    positionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    positionRepository.deleteById(positionId);
  }
}
