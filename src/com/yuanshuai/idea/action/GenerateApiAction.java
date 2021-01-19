package com.yuanshuai.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.yuanshuai.idea.config.ShowDocState;
import org.jetbrains.annotations.NotNull;

public class GenerateApiAction extends AnAction {

    private String api;
    private String key;
    private String token;

    private Project project;

    private ShowDocState showDocState;

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        this.project = anActionEvent.getProject();
        this.showDocState = ShowDocState.getInstance(project);

        this.api = this.showDocState.getApi();
        if (this.api.equals("")) {
            Messages.showErrorDialog("请先输入API", "缺少参数");
        }
    }

}
