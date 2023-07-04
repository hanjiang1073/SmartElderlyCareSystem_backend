package com.example.crud3.controller;


import com.example.crud3.entity.VolunteerEntity;

import com.example.crud3.service.VolunteerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/volunteer")

public class VolunteerController {
    @Resource
    VolunteerService volunteerService;

    @GetMapping("")
    public List<VolunteerEntity> getAll() {
        return volunteerService.list();
    }


    @PostMapping("")
    public boolean add(@RequestBody VolunteerEntity volunteer) {
        return volunteerService.saveOrUpdate(volunteer);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") int id, @RequestBody VolunteerEntity volunteer) {
        volunteer.setVolunteerid(id);
        return volunteerService.updateById(volunteer);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return volunteerService.removeById(id);
    }

    @GetMapping("/{id}")
    public VolunteerEntity getById(@PathVariable Integer id) {
        return volunteerService.getById(id);
    }
}


