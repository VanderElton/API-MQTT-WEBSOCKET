package com.example.websocket.resource;

import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.Home;
import com.example.websocket.service.HomeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("Homes")
@RequestMapping({"/api/homes"})
public class HomeResource {

    @Autowired
    HomeService homeService;

    @PostMapping()
    public ResponseEntity<Response> save(@RequestBody Home home) {
        return ResponseEntity.ok(homeService.save(home));
    }

    @GetMapping()
    public ResponseEntity<List<Home>> findAll() {
        return ResponseEntity.ok(homeService.findAll());
    }

    @PutMapping()
    public ResponseEntity<Response> update(@RequestBody Home home) {
        return ResponseEntity.ok(homeService.update(home));
    }

    @GetMapping("/{homeId}")
    public ResponseEntity<Home> findById(@PathVariable String homeId) {
        return ResponseEntity.ok(homeService.findById(homeId));
    }

    @DeleteMapping("/{homeId}")
    public ResponseEntity<Response> delete(@PathVariable String homeId) {
        return ResponseEntity.ok(homeService.delete(homeId));
    }
}
