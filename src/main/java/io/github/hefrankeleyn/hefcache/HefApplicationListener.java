package io.github.hefrankeleyn.hefcache;

import jakarta.annotation.Resource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date 2024/6/30
 * @Author lifei
 */
@Component
public class HefApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Resource
    private List<HefPlugin> hefPluginList;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 等价于ApplicationRunner
        if (event instanceof ApplicationReadyEvent) {
            for (HefPlugin hefPlugin : hefPluginList) {
                hefPlugin.init();
                hefPlugin.startup();
            }
        } else if (event instanceof ContextClosedEvent) {
            for (HefPlugin hefPlugin : hefPluginList) {
                hefPlugin.shutdown();
            }
        }
    }
}
