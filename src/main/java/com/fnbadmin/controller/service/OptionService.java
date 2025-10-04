package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.OptionRepository;
import com.fnbadmin.controller.response.OptionAutoResponse;
import com.fnbadmin.domain.Option;
import com.fnbadmin.domain.OptionGroup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<OptionAutoResponse> getOptionGroupList(String optionType) {
        List<OptionGroup> optionGroups = this.optionRepository.findOptionGroups(optionType);
        List<OptionAutoResponse> responses = new ArrayList<>();

        for (OptionGroup optionGroup : optionGroups) {
            responses.add(OptionAutoResponse.builder()
                    .label(optionGroup.getName())
                    .value(optionGroup.getOptionGroupId())
                    .build());
        }

        return responses;
    }

    public List<OptionAutoResponse> getOptionList(String optionGroupId) {
        List<Option> options = this.optionRepository.findOptions(optionGroupId);
        List<OptionAutoResponse> responses = new ArrayList<>();

        for (Option option : options) {
            responses.add(OptionAutoResponse.builder()
                    .label(option.getName())
                    .value(option.getOptionId())
                    .build());
        }

        return responses;
    }
}
