package com.fnbadmin.controller;

import com.fnbadmin.controller.response.*;
import com.fnbadmin.controller.service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/option-group/auto-complete")
    @ResponseBody
    public List<OptionAutoResponse> getOptionGroupAutoComplete(@RequestParam("optionType") String optionType) {
        return this.optionService.getOptionGroupAutoComplete(optionType);
    }

    @GetMapping("/option-group/{optionType}/list")
    public ResponseEntity<List<OptionGroupListResponse>> getOptionGroups(@PathVariable String optionType) {
        return ResponseEntity.ok(this.optionService.getOptionGroups(optionType));
    }

    @GetMapping("/option-group/{optionType}/{optionGroupId}")
    public ResponseEntity<OptionGroupInfoResponse> getOptionGroupInfo(@PathVariable String optionType, @PathVariable String optionGroupId) {
        return ResponseEntity.ok(this.optionService.getOptionGroup(optionType, optionGroupId));
    }

    @GetMapping("/option/auto-complete")
    @ResponseBody
    public List<OptionAutoResponse> getOptionAutoComplete(@RequestParam("optionGroupId") String optionGroupId) {
        return this.optionService.getOptionAutoComplete(optionGroupId);
    }

    @GetMapping("/option/{optionGroupId}/list")
    public ResponseEntity<List<OptionListResponse>> getOptions(@PathVariable String optionGroupId) {
        return ResponseEntity.ok(this.optionService.getOptions(optionGroupId));
    }

    @GetMapping("/option/{optionGroupId}/{optionId}")
    public ResponseEntity<OptionInfoResponse> getOptions(@PathVariable String optionGroupId, @PathVariable String optionId) {
        return ResponseEntity.ok(this.optionService.getOption(optionGroupId, optionId));
    }
}
