package com.fnbadmin.controller;

import com.fnbadmin.controller.response.OptionAutoResponse;
import com.fnbadmin.controller.service.OptionService;
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
    public List<OptionAutoResponse> getOptionGroups(@RequestParam("optionType") String optionType) {
        return this.optionService.getOptionGroupList(optionType);
    }

    @GetMapping("/option/auto-complete")
    @ResponseBody
    public List<OptionAutoResponse> getOptions(@RequestParam("optionGroupId") String optionGroupId) {
        return this.optionService.getOptionList(optionGroupId);
    }
}
