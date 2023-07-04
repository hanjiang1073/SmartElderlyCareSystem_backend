package com.example.crud3.controller;


import com.example.crud3.entity.EventEntity;

import com.example.crud3.service.EventService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/event")

public class EventController {
    @Resource
    EventService eventService;
    @GetMapping("")

    public List<EventEntity> getAll() {
        return eventService.list();
    }

    @PostMapping("")
    public boolean add(@RequestBody EventEntity elder) {
        return eventService.saveOrUpdate(elder);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") int id, @RequestBody EventEntity elder) {
        elder.setEventid(id);
        return eventService.updateById(elder);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return eventService.removeById(id);
    }

    @GetMapping("/{id}")
    public EventEntity getById(@PathVariable Integer id) {
        return eventService.getById(id);
    }

}

