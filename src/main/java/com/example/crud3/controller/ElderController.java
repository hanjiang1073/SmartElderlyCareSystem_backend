package com.example.crud3.controller;

import com.example.crud3.entity.ElderEntity;
import com.example.crud3.entity.StusEntity;
import com.example.crud3.service.ElderService;
import com.example.crud3.service.StusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/elder")

public class ElderController {
    @Resource
    ElderService elderService;

    @GetMapping("")
    public List<ElderEntity> getAll() {
        return elderService.list();
    }

    @PostMapping("")
    public boolean add(@RequestBody ElderEntity elder) {
        return elderService.saveOrUpdate(elder);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") int id, @RequestBody ElderEntity elder) {
        elder.setElderid(id);
        return elderService.updateById(elder);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return elderService.removeById(id);
    }

    @GetMapping("/{id}")
    public ElderEntity getById(@PathVariable Integer id) {
        return elderService.getById(id);
    }
}

