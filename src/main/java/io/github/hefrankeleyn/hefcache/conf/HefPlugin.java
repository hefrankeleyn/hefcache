package io.github.hefrankeleyn.hefcache.conf;

public interface HefPlugin {

    void init();
    void startup();
    void shutdown();
}
