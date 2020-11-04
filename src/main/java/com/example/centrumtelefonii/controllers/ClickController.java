package com.example.centrumtelefonii.controllers;


import com.example.centrumtelefonii.dao.ClickRepo;
import com.example.centrumtelefonii.models.Click;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;


@RestController
public class ClickController {

    ClickRepo clickRepo;

    public ClickController(ClickRepo clickRepo) {
        this.clickRepo = clickRepo;
    }

    @GetMapping("/klikniecia")
    public List<Click> getClicks() {
        return clickRepo.findAllSorted();
    }

    @GetMapping("/klikniecia/{date}")
    public Optional<Click> getClickForDay(@PathVariable("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return clickRepo.findByDate(localDate);
    }

    @GetMapping("/klikniecia/dzisiaj")
    public int getTodayClicks(){
        Optional<Click> click = clickRepo.findByDate(LocalDate.now());
        return click.map(Click::getClicks).orElse(0);
    }

    @GetMapping("/klikniecia/tydzien")
    public int getClicksFromWeek(){
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = now.get(weekFields.weekOfWeekBasedYear());
        int clicks=0;
        for(Click click: clickRepo.findAll()){
            if(click.getDate().get(weekFields.weekOfWeekBasedYear()) == weekNumber-1){
                clicks+=click.getClicks();
            }
        }
        return clicks;
    }

    @GetMapping("/klikniecia/wszystko")
    public int getAllClicks(){
        int clicks=0;
        for(Click click: clickRepo.findAll()){
            clicks+=click.getClicks();
        }
        return clicks;
    }

    @PutMapping(path = "/klikniecie")
    public Click addClick() {
        LocalDate localDate = LocalDate.now();
        Click click;
        Optional<Click> optionalClick;
        optionalClick = clickRepo.findByDate(localDate);
        if (optionalClick.isPresent()) {
            optionalClick.get().setClicks(optionalClick.get().getClicks() + 1);
            click = optionalClick.get();
        } else {
            click = new Click();
            click.setDate(localDate);
            click.setClicks(1);
        }
        clickRepo.save(click);
        return click;
    }

}
