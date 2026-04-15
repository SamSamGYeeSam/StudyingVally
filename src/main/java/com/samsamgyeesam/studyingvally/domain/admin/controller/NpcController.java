package com.samsamgyeesam.studyingvally.domain.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NpcController {

    @GetMapping("/npc")
    public String npcMain() {
        return "npc";
    }

}
