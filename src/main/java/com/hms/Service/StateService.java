package com.hms.Service;

import com.hms.entity.State;
import com.hms.payload.StateDTO;
import com.hms.repository.PropertyRepository;
import com.hms.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateService {

    private final StateRepository stateRepository;
    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;
    public StateService(StateRepository stateRepository, PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.stateRepository = stateRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }
    State mapToEntity(StateDTO stateDTO){
        return modelMapper.map(stateDTO, State.class);
    }
    StateDTO mapToDTO(State state){
        return modelMapper.map(state, StateDTO.class);
    }

    public StateDTO addState(StateDTO stateDTO) {
        State state = mapToEntity(stateDTO);
        Optional<State> stateName = stateRepository.findByStateName(state.getStateName());
        if (stateName.isPresent()){
            throw new RuntimeException("State already exists !");
        }else{
            State save = stateRepository.save(state);
            return mapToDTO(save);
        }
    }

    // delete state and set the value null in the place of state
    @Transactional
    public void deleteState(Long id) {
        State state = stateRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("State with id " + id + "not found !"));

        propertyRepository.updateStateToNull(id);
        stateRepository.delete(state);
    }

    public List<StateDTO> getAllState() {
        List<State> stateList = stateRepository.findAll();
        return stateList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
