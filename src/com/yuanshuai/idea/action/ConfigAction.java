package com.yuanshuai.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.yuanshuai.idea.dialog.ConfigDialogWrapper;
import org.jetbrains.annotations.NotNull;

public class ConfigAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        ConfigDialogWrapper configDialog = new ConfigDialogWrapper("这是个什么玩意儿");
        configDialog.setVisible(true);
    }
}
