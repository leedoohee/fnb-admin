package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.OptionRepository;
import com.fnbadmin.controller.response.*;
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

    public List<OptionAutoResponse> getOptionGroupAutoComplete(String optionType) {
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

    public List<OptionAutoResponse> getOptionAutoComplete(String optionGroupId) {
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

    public List<OptionGroupListResponse> getOptionGroups(String optionType) {
        List<OptionGroup> optionGroups = this.optionRepository.findOptionGroups(optionType);
        List<OptionGroupListResponse> responses = new ArrayList<>();

        for (OptionGroup group : optionGroups) {
            responses.add(OptionGroupListResponse.builder()
                    .optionGroupId(group.getOptionGroupId())
                    .name(group.getName())
                    .build());
        }

        return responses;
    }

    public OptionGroupInfoResponse getOptionGroup(String optionType, String optionGroupId) {
        OptionGroup optionGroup = this.optionRepository.findOptionGroup(optionType, optionGroupId);

        return OptionGroupInfoResponse.builder()
                .optionGroupId(optionGroup.getOptionGroupId())
                .description(optionGroup.getDescription())
                .name(optionGroup.getName())
                .isUsed(optionGroup.isUse()).build();
    }

    public OptionInfoResponse getOption(String optionGroupId, String optionId) {
        Option option = this.optionRepository.findOption(optionGroupId, optionId);

        return OptionInfoResponse.builder()
                .optionGroupId(option.getOptionGroupId())
                .optionId(option.getOptionId())
                .name(option.getName())
                .price(option.getPrice()).build();
    }

    public List<OptionListResponse> getOptions(String optionGroupId) {
        List<Option> options = this.optionRepository.findOptions(optionGroupId);
        List<OptionListResponse> responses = new ArrayList<>();

        for (Option option : options) {
            responses.add(OptionListResponse.builder()
                    .optionId(option.getOptionId())
                    .name(option.getName())
                    .build());
        }

        return responses;
    }
}
