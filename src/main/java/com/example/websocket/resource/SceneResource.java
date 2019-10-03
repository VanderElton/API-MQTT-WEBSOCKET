package com.example.websocket.resource;

import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.scene.Action;
import com.example.websocket.entity.scene.Scene;
import com.example.websocket.service.SceneService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Api("Scenes")
@RequestMapping({"/api/scenes"})
public class SceneResource {

    @Autowired
    private SceneService sceneService;

    @GetMapping("/home/{homeId}")
    public ResponseEntity<Set<Scene>> findAll(@PathVariable String homeId){
        return ResponseEntity.ok(sceneService.findAll(homeId));
    }

    @PostMapping("/home/{homeId}")
    public ResponseEntity<Response> save(@PathVariable String homeId, @RequestBody Scene scene) {
        return ResponseEntity.ok(sceneService.save(homeId, scene));
    }

    @GetMapping("/home/{homeId}/scene/{sceneId}")
    public ResponseEntity<Scene> findAllById(@PathVariable String homeId, @PathVariable String sceneId) {
        return ResponseEntity.ok(sceneService.findById(homeId, sceneId));
    }

    @DeleteMapping("/home/{homeId}/scene/{sceneId}")
    public ResponseEntity<Response> delete(@PathVariable String homeId, @PathVariable String sceneId) {
        return ResponseEntity.ok(sceneService.delete(homeId, sceneId));
    }

    @PostMapping("/home/{homeId}/scene/{sceneId}/action")
    public ResponseEntity<Response> addAction(@PathVariable String homeId, @PathVariable String sceneId, @RequestBody Action action) {
        return ResponseEntity.ok(sceneService.addAction(homeId, sceneId, action));
    }

    @DeleteMapping("/home/{homeId}/scene/{sceneId}/action")
    public ResponseEntity<Response> deleteAction(@PathVariable String homeId, @PathVariable String sceneId, @RequestBody Action action) {
        return ResponseEntity.ok(sceneService.deleteAction(homeId, sceneId, action));
    }
}
