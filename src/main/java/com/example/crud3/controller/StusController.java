package com.example.crud3.controller;

import com.example.crud3.entity.StusEntity;
import com.example.crud3.service.StusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/stus")

public class StusController {
    @Resource
    StusService stusService;
    @GetMapping("")
    public List<StusEntity> getAll() {

        return stusService.list();
    }
    @PostMapping("")
    public boolean add(@RequestBody StusEntity stus) {
        return stusService.saveOrUpdate(stus);
    }
    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") int id, @RequestBody StusEntity stus) {
        stus.setId(id);
        return stusService.updateById(stus);
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return stusService.removeById(id);
    }
    @GetMapping("/{id}")
    public StusEntity getById(@PathVariable Integer id) {
        return stusService.getById(id);
    }
}