package com.yuanshuai.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.yuanshuai.idea.config.ShowDocState;
import com.yuanshuai.idea.utils.NotificationUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GenerateApiAction extends AnAction {

    private String api;
    private String key;
    private String token;

    private Project project;

    private ShowDocState showDocState;

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        this.project = anActionEvent.getProject();
        this.showDocState = ShowDocState.getInstance(this.project);
        this.buildParams();

    }

    private void buildParams()
    {
        this.api = this.showDocState.getApi();
        System.out.println("api:" + this.api);
        if (StringUtil.isEmpty(this.api)) {
            this.api = Messages.showInputDialog("Input Api", "Input Api", Messages.getWarningIcon());
            if (StringUtil.isEmpty(this.api)) {
                NotificationUtil.warnNotify("Api Is Empty", this.project);
                return;
            }
            this.showDocState.setApi(this.api);
        }

        this.key = this.showDocState.getKey();
        System.out.println("key:" + this.key);
        if (StringUtil.isEmpty(this.key)) {
            this.key = Messages.showInputDialog("Input Key", "Input Key", Messages.getWarningIcon());
            if (StringUtil.isEmpty(this.key)) {
                NotificationUtil.warnNotify("Key Is Empty", this.project);
                return;
            }
            this.showDocState.setKey(this.key);
        }

        this.token = this.showDocState.getToken();
        System.out.println("token:" + this.token);
        if (StringUtil.isEmpty(this.token)) {
            this.token = Messages.showInputDialog("Input Token", "Input Token", Messages.getWarningIcon());
            if (StringUtil.isEmpty(this.token)) {
                NotificationUtil.warnNotify("Token Is Empty", this.project);
                return;
            }
            this.showDocState.setToken(this.token);
        }
    }
}
