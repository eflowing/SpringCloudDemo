package eflowing.example.cloud.service.redis.api;

import eflowing.example.cloud.service.redis.cacheentity.Entity1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cache/demo")
@ResponseBody
public class CacheService {
    @Autowired
    private Entity1 ids;

    @GetMapping("get")
    public long getId() {
        return ids.getValue();
    }

    @GetMapping("reset")
    public long init() {
        return ids.setValue(0);
    }

    @GetMapping("incr")
    public long incr() {
        return ids.setValue(ids.getValue() + 1);
    }

    @GetMapping("decr")
    public long decr() {
        return ids.setValue(ids.getValue() - 1);
    }

    @GetMapping("getuser")
    public String getUser(@RequestParam("id") int id) {
        return ids.getUser(id);
    }

    @GetMapping("setuser")
    public String setUser(@RequestParam("id") int id, @RequestParam("name") String name) {
        return ids.setUser(id, name);
    }

    @GetMapping("getperson")
    @ResponseBody
    public String getPerson(){
        return ids.getPerson();
    }

    @GetMapping("setperson")
    public String setPerson(String name){
        return ids.setPerson(name);
    }
}
