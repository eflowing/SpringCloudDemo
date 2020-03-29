package eflowing.example.cloud.service.restinfo.service;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

@RestController
@RequestMapping("ws/rest")
public class RestInfoService {
    private final ApplicationContext applicationContext;

    public RestInfoService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @ResponseBody
    @GetMapping("list/all")
    public String list() {

        List<RestModel> list = new ArrayList<>();
        Map<String, RestModel> map = new HashMap<>();
        List<String> urlList = new ArrayList<>();
        ServletRequestAttributes sa = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        Assert.state(sa != null, "get servletRequestAttributes fail!");
        Map<String, HandlerMapping> requestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HandlerMapping.class, true, false);
        for (HandlerMapping handlerMapping : requestMappings.values()) {
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping rmhm = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();
                for (RequestMappingInfo rmi : handlerMethods.keySet()) {
                    PatternsRequestCondition prc = rmi.getPatternsCondition();
                    Set<String> patterns = prc.getPatterns();
                    HandlerMethod handlerMethod = handlerMethods.get(rmi);
                    for (String pp : patterns) {
                        Class<?> clazz = handlerMethod.getBeanType();
                        Method method = handlerMethod.getMethod();
                        RestModel restModel = new RestModel();
                        restModel.setUrl(pp);
                        restModel.setClazz(clazz.toString());
                        restModel.setMethod(method.toString());
                        PreAuthorize preAuth = method.getAnnotation(PreAuthorize.class);
                        restModel.setAuthorize(null == preAuth ? null: preAuth.value());
                        map.put(pp, restModel);
                        urlList.add(pp);
                    }
                }
            }
        }
        String[] urls = new String[urlList.size()];
        urls = urlList.toArray(urls);
        Arrays.sort(urls);
        for (String url : urls) {
            list.add(map.get(url));
        }
        String ret = JSON.toJSONString(list);
        System.out.println(ret);
        return ret;
    }

    @Data
    public static class RestModel {
        private String url;
        private String clazz;
        private String method;
        private String authorize;
    }
}
