package com.yuanshuai.idea.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@State(name = "ShowDocConfig", storages = {@Storage(StoragePathMacros.WORKSPACE_FILE)})
public class ShowDocState implements PersistentStateComponent<Element> {

    private String api;
    private String key;
    private String token;

    public ShowDocState() {
    }

    public static ShowDocState getInstance(@NotNull Project project)
    {
        return ServiceManager.getService(project, ShowDocState.class);
    }

    @Override
    public @Nullable Element getState() {
        Element element = new Element("ShowDocConfig");
        element.setAttribute("api", this.getApi());
        element.setAttribute("key", this.getKey());
        element.setAttribute("token", this.getToken());
        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        this.api = element.getAttributeValue("api");
        this.key = element.getAttributeValue("key");
        this.token = element.getAttributeValue("token");
    }

    public String getApi() {
        return Objects.isNull(api) ? "" : api;
    }

    public String getKey() {
        return Objects.isNull(key) ? "" : key;
    }

    public String getToken() {
        return Objects.isNull(token) ? "" : token;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
