package com.example.websocket.service;

import com.cloudant.client.api.model.Response;
import com.example.websocket.entity.Home;
import com.example.websocket.entity.scene.Action;
import com.example.websocket.entity.scene.Scene;
import com.example.websocket.exception.ApiException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class SceneService {

    protected static final Log LOGGER = LogFactory.getLog(SceneService.class);

    @Autowired
    private HomeService homeService;

    public Response save(String homeId, Scene scene){
        Home home = homeService.findById(homeId);
        Set<Scene> scenes = home.getScenes();
        if(verifySingleScene(scenes , scene.getId())) {
            scenes.add(scene);
        }else{
            LOGGER.warn(scene.getId() + " already exist.");
            String[] params = { scene.getId() };
            throw new ApiException("http.conflict", HttpStatus.CONFLICT, params);
        }
        return homeService.update(home);
    }

    public Response delete(String homeId, String sceneId){
        Home home = homeService.findById(homeId);
        Scene scene = findInHome(home, sceneId);
        home.getScenes().remove(scene);
        return homeService.update(home);
    }

    public Scene findById(String homeId, String sceneId){
        return findInHome(homeService.findById(homeId), sceneId);
    }

    public Set<Scene> findAll(String homeId){
        return homeService.findById(homeId).getScenes();
    }

    public Response addAction(String homeId, String sceneId, Action action){
        Home home = homeService.findById(homeId);
        Scene scene = findInHome(home, sceneId);
        Set<Action> actions = scene.getActions();
        if(verifySingleAction(actions , action.getDeviceId())) {
                actions.add(action);
        }else{
            LOGGER.warn("Action for " + action.getDeviceId() + " already exist.");
            String[] params = { action.getDeviceId() };
            throw new ApiException("http.conflict", HttpStatus.CONFLICT, params);
        }
        home.getScenes().add(scene);
        return homeService.update(home);
    }

    public Response deleteAction(String homeId, String sceneId, Action action){
        Home home = homeService.findById(homeId);
        Scene scene = findInHome(home, sceneId);
        scene.getActions().remove(action);
        home.getScenes().add(scene);
        return homeService.update(home);
    }

    private Scene findInHome(Home home, String sceneId){
        Optional<Scene> scene = home.getScenes()
                .stream()
                .filter(e -> sceneId.equals(e.getId()))
                .findFirst();
        return scene.orElse(null);
    }

    private boolean verifySingleScene(Set<Scene> scenes, String sceneId){
        return scenes.stream().noneMatch(scene -> scene.getId().equals(sceneId));
    }

    private boolean verifySingleAction(Set<Action> actions, String deviceId){
        return actions.stream().noneMatch(action -> action.getDeviceId().equals(deviceId));
    }
}
